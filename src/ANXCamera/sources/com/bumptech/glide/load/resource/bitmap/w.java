package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import com.bumptech.glide.load.engine.bitmap_recycle.d;
import com.bumptech.glide.util.i;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* compiled from: TransformationUtils */
public final class w {
    private static final String TAG = "TransformationUtils";
    public static final int mL = 6;
    private static final Paint mN = new Paint(6);
    private static final int mO = 7;
    private static final Paint mP = new Paint(7);
    private static final Paint mQ = new Paint(7);
    private static final Set<String> mR = new HashSet(Arrays.asList(new String[]{"XT1085", "XT1092", "XT1093", "XT1094", "XT1095", "XT1096", "XT1097", "XT1098", "XT1031", "XT1028", "XT937C", "XT1032", "XT1008", "XT1033", "XT1035", "XT1034", "XT939G", "XT1039", "XT1040", "XT1042", "XT1045", "XT1063", "XT1064", "XT1068", "XT1069", "XT1072", "XT1077", "XT1078", "XT1079"}));
    private static final Lock mS = (mR.contains(Build.MODEL) ? new ReentrantLock() : new a());

    /* compiled from: TransformationUtils */
    private static final class a implements Lock {
        a() {
        }

        public void lock() {
        }

        public void lockInterruptibly() throws InterruptedException {
        }

        @NonNull
        public Condition newCondition() {
            throw new UnsupportedOperationException("Should not be called");
        }

        public boolean tryLock() {
            return true;
        }

        public boolean tryLock(long j, @NonNull TimeUnit timeUnit) throws InterruptedException {
            return true;
        }

        public void unlock() {
        }
    }

    static {
        mQ.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    }

    private w() {
    }

    public static int H(int i) {
        switch (i) {
            case 3:
            case 4:
                return 180;
            case 5:
            case 6:
                return 90;
            case 7:
            case 8:
                return 270;
            default:
                return 0;
        }
    }

    public static boolean I(int i) {
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                return true;
            default:
                return false;
        }
    }

    public static Bitmap a(@NonNull Bitmap bitmap, int i) {
        if (i == 0) {
            return bitmap;
        }
        try {
            Matrix matrix = new Matrix();
            matrix.setRotate((float) i);
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        } catch (Exception e) {
            if (!Log.isLoggable(TAG, 6)) {
                return bitmap;
            }
            Log.e(TAG, "Exception when trying to orient image", e);
            return bitmap;
        }
    }

    public static Bitmap a(@NonNull d dVar, @NonNull Bitmap bitmap, int i) {
        if (!I(i)) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        initializeMatrixForRotation(i, matrix);
        RectF rectF = new RectF(0.0f, 0.0f, (float) bitmap.getWidth(), (float) bitmap.getHeight());
        matrix.mapRect(rectF);
        Bitmap b2 = dVar.b(Math.round(rectF.width()), Math.round(rectF.height()), m(bitmap));
        matrix.postTranslate(-rectF.left, -rectF.top);
        a(bitmap, b2, matrix);
        return b2;
    }

    public static Bitmap a(@NonNull d dVar, @NonNull Bitmap bitmap, int i, int i2) {
        float f;
        float f2;
        if (bitmap.getWidth() == i && bitmap.getHeight() == i2) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        float f3 = 0.0f;
        if (bitmap.getWidth() * i2 > bitmap.getHeight() * i) {
            f2 = ((float) i2) / ((float) bitmap.getHeight());
            f = (((float) i) - (((float) bitmap.getWidth()) * f2)) * 0.5f;
        } else {
            f2 = ((float) i) / ((float) bitmap.getWidth());
            f3 = (((float) i2) - (((float) bitmap.getHeight()) * f2)) * 0.5f;
            f = 0.0f;
        }
        matrix.setScale(f2, f2);
        matrix.postTranslate((float) ((int) (f + 0.5f)), (float) ((int) (f3 + 0.5f)));
        Bitmap b2 = dVar.b(i, i2, m(bitmap));
        a(bitmap, b2);
        a(bitmap, b2, matrix);
        return b2;
    }

    @Deprecated
    public static Bitmap a(@NonNull d dVar, @NonNull Bitmap bitmap, int i, int i2, int i3) {
        return b(dVar, bitmap, i3);
    }

    public static void a(Bitmap bitmap, Bitmap bitmap2) {
        bitmap2.setHasAlpha(bitmap.hasAlpha());
    }

    private static void a(@NonNull Bitmap bitmap, @NonNull Bitmap bitmap2, Matrix matrix) {
        mS.lock();
        try {
            Canvas canvas = new Canvas(bitmap2);
            canvas.drawBitmap(bitmap, matrix, mN);
            a(canvas);
        } finally {
            mS.unlock();
        }
    }

    private static void a(Canvas canvas) {
        canvas.setBitmap((Bitmap) null);
    }

    private static Bitmap b(@NonNull d dVar, @NonNull Bitmap bitmap) {
        Bitmap.Config l = l(bitmap);
        if (l.equals(bitmap.getConfig())) {
            return bitmap;
        }
        Bitmap b2 = dVar.b(bitmap.getWidth(), bitmap.getHeight(), l);
        new Canvas(b2).drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
        return b2;
    }

    /* JADX INFO: finally extract failed */
    public static Bitmap b(@NonNull d dVar, @NonNull Bitmap bitmap, int i) {
        i.a(i > 0, "roundingRadius must be greater than 0.");
        Bitmap.Config l = l(bitmap);
        Bitmap b2 = b(dVar, bitmap);
        Bitmap b3 = dVar.b(b2.getWidth(), b2.getHeight(), l);
        b3.setHasAlpha(true);
        BitmapShader bitmapShader = new BitmapShader(b2, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(bitmapShader);
        RectF rectF = new RectF(0.0f, 0.0f, (float) b3.getWidth(), (float) b3.getHeight());
        mS.lock();
        try {
            Canvas canvas = new Canvas(b3);
            canvas.drawColor(0, PorterDuff.Mode.CLEAR);
            float f = (float) i;
            canvas.drawRoundRect(rectF, f, f, paint);
            a(canvas);
            mS.unlock();
            if (!b2.equals(bitmap)) {
                dVar.d(b2);
            }
            return b3;
        } catch (Throwable th) {
            mS.unlock();
            throw th;
        }
    }

    public static Bitmap b(@NonNull d dVar, @NonNull Bitmap bitmap, int i, int i2) {
        if (bitmap.getWidth() == i && bitmap.getHeight() == i2) {
            if (Log.isLoggable(TAG, 2)) {
                Log.v(TAG, "requested target size matches input, returning input");
            }
            return bitmap;
        }
        float min = Math.min(((float) i) / ((float) bitmap.getWidth()), ((float) i2) / ((float) bitmap.getHeight()));
        int round = Math.round(((float) bitmap.getWidth()) * min);
        int round2 = Math.round(((float) bitmap.getHeight()) * min);
        if (bitmap.getWidth() == round && bitmap.getHeight() == round2) {
            if (Log.isLoggable(TAG, 2)) {
                Log.v(TAG, "adjusted target size matches input, returning input");
            }
            return bitmap;
        }
        Bitmap b2 = dVar.b((int) (((float) bitmap.getWidth()) * min), (int) (((float) bitmap.getHeight()) * min), m(bitmap));
        a(bitmap, b2);
        if (Log.isLoggable(TAG, 2)) {
            Log.v(TAG, "request: " + i + "x" + i2);
            Log.v(TAG, "toFit:   " + bitmap.getWidth() + "x" + bitmap.getHeight());
            Log.v(TAG, "toReuse: " + b2.getWidth() + "x" + b2.getHeight());
            StringBuilder sb = new StringBuilder();
            sb.append("minPct:   ");
            sb.append(min);
            Log.v(TAG, sb.toString());
        }
        Matrix matrix = new Matrix();
        matrix.setScale(min, min);
        a(bitmap, b2, matrix);
        return b2;
    }

    public static Bitmap c(@NonNull d dVar, @NonNull Bitmap bitmap, int i, int i2) {
        if (bitmap.getWidth() > i || bitmap.getHeight() > i2) {
            if (Log.isLoggable(TAG, 2)) {
                Log.v(TAG, "requested target size too big for input, fit centering instead");
            }
            return b(dVar, bitmap, i, i2);
        }
        if (Log.isLoggable(TAG, 2)) {
            Log.v(TAG, "requested target size larger or equal to input, returning input");
        }
        return bitmap;
    }

    /* JADX INFO: finally extract failed */
    public static Bitmap d(@NonNull d dVar, @NonNull Bitmap bitmap, int i, int i2) {
        int min = Math.min(i, i2);
        float f = (float) min;
        float f2 = f / 2.0f;
        float width = (float) bitmap.getWidth();
        float height = (float) bitmap.getHeight();
        float max = Math.max(f / width, f / height);
        float f3 = width * max;
        float f4 = max * height;
        float f5 = (f - f3) / 2.0f;
        float f6 = (f - f4) / 2.0f;
        RectF rectF = new RectF(f5, f6, f3 + f5, f4 + f6);
        Bitmap b2 = b(dVar, bitmap);
        Bitmap b3 = dVar.b(min, min, l(bitmap));
        b3.setHasAlpha(true);
        mS.lock();
        try {
            Canvas canvas = new Canvas(b3);
            canvas.drawCircle(f2, f2, f2, mP);
            canvas.drawBitmap(b2, (Rect) null, rectF, mQ);
            a(canvas);
            mS.unlock();
            if (!b2.equals(bitmap)) {
                dVar.d(b2);
            }
            return b3;
        } catch (Throwable th) {
            mS.unlock();
            throw th;
        }
    }

    public static Lock dd() {
        return mS;
    }

    @VisibleForTesting
    static void initializeMatrixForRotation(int i, Matrix matrix) {
        switch (i) {
            case 2:
                matrix.setScale(-1.0f, 1.0f);
                return;
            case 3:
                matrix.setRotate(180.0f);
                return;
            case 4:
                matrix.setRotate(180.0f);
                matrix.postScale(-1.0f, 1.0f);
                return;
            case 5:
                matrix.setRotate(90.0f);
                matrix.postScale(-1.0f, 1.0f);
                return;
            case 6:
                matrix.setRotate(90.0f);
                return;
            case 7:
                matrix.setRotate(-90.0f);
                matrix.postScale(-1.0f, 1.0f);
                return;
            case 8:
                matrix.setRotate(-90.0f);
                return;
            default:
                return;
        }
    }

    @NonNull
    private static Bitmap.Config l(@NonNull Bitmap bitmap) {
        return (Build.VERSION.SDK_INT < 26 || !Bitmap.Config.RGBA_F16.equals(bitmap.getConfig())) ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGBA_F16;
    }

    @NonNull
    private static Bitmap.Config m(@NonNull Bitmap bitmap) {
        return bitmap.getConfig() != null ? bitmap.getConfig() : Bitmap.Config.ARGB_8888;
    }
}
