package com.android.camera.panorama;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.media.Image;
import android.text.format.DateFormat;
import com.android.camera.log.Log;
import com.android.camera.panorama.MorphoSensorFusion;
import java.io.FileDescriptor;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Locale;

public class MorphoPanoramaGP3 {
    public static final int DIRECTION_AUTO = 0;
    public static final int DIRECTION_HORIZONTAL = 2;
    public static final int DIRECTION_HORIZONTAL_LEFT = 5;
    public static final int DIRECTION_HORIZONTAL_RIGHT = 6;
    public static final int DIRECTION_INVALID = -1;
    public static final int DIRECTION_VERTICAL = 1;
    public static final int DIRECTION_VERTICAL_DOWN = 4;
    public static final int DIRECTION_VERTICAL_UP = 3;
    public static final int END_MODE_CANCEL = 2;
    public static final int END_MODE_MAKE_360 = 0;
    public static final int END_MODE_NOT_MAKE_360 = 1;
    public static final int END_STATUS_CANCEL = 2;
    public static final int END_STATUS_MAKE_360 = 0;
    public static final int END_STATUS_NOT_MAKE_360 = 1;
    public static final int ERROR_INVALID_DIR = -1073741823;
    public static final int FAST_SPEED_THRESHOLD = 4500;
    private static double GAIN_COEF = 1.0d;
    public static final int MODE_PANORAMA = 0;
    public static final int MODE_SCANNER = 1;
    private static final int POINT_INFO_SIZE = 2;
    private static final int POINT_X_OFFSET = 0;
    private static final int POINT_Y_OFFSET = 1;
    private static final int RECT_BOTTOM_OFFSET = 3;
    private static final int RECT_INFO_SIZE = 4;
    private static final int RECT_LEFT_OFFSET = 0;
    private static final int RECT_RIGHT_OFFSET = 2;
    private static final int RECT_TOP_OFFSET = 1;
    public static final String TAG = "MorphoPanoramaGP3";
    private static final boolean USE_STANDARD_DEVIATION = true;
    private long mAttachCount;
    private boolean mAttachEnabled;
    private long mAttachFirstNanoTime;
    private long mAttachLastNanoTime;
    private String mFolderPathInputImages;
    private final GravityParam mGravity;
    private int[] mIndexBase;
    private String mInputImageFormat;
    private ArrayList<Long> mIntervalArray;
    private long mNative;
    private long mNativeOutputInfo;
    private boolean mSaveInputImages;

    public static class GalleryInfoData {
        public int crop_height;
        public int crop_left;
        public int crop_top;
        public int crop_width;
        public int whole_height;
        public int whole_width;

        public String toString() {
            return "GalleryInfoData{whole_width=" + this.whole_width + ", whole_height=" + this.whole_height + ", crop_left=" + this.crop_left + ", crop_top=" + this.crop_top + ", crop_width=" + this.crop_width + ", crop_height=" + this.crop_height + '}';
        }
    }

    public static class GravityParam {
        public double x = 0.0d;
        public double y = 0.0d;
        public double z = 0.0d;

        public GravityParam copyInstance() {
            GravityParam gravityParam = new GravityParam();
            gravityParam.x = this.x;
            gravityParam.y = this.y;
            gravityParam.z = this.z;
            return gravityParam;
        }
    }

    public static class InitParam {
        public double aovx;
        public double aovy;
        public int direction;
        public double goal_angle;
        public String input_format;
        public int input_height;
        public int input_width;
        public String output_format;
        public int output_rotation;
    }

    static {
        try {
            System.loadLibrary("morpho_panorama_gp3");
        } catch (UnsatisfiedLinkError e) {
            Log.e(TAG, "can't loadLibrary, " + e.getMessage());
        }
    }

    public MorphoPanoramaGP3() {
        this.mNative = 0;
        this.mNativeOutputInfo = 0;
        this.mSaveInputImages = false;
        this.mIntervalArray = null;
        this.mAttachEnabled = false;
        this.mGravity = new GravityParam();
        this.mIndexBase = new int[4];
        this.mAttachFirstNanoTime = 0;
        this.mAttachLastNanoTime = 0;
        this.mIntervalArray = new ArrayList<>();
    }

    private static String createName(long j) {
        return DateFormat.format("yyyy-MM-dd_kk-mm-ss", j).toString();
    }

    private native long createNativeObject();

    private native long createNativeOutputInfoObject();

    private native void deleteNativeObject(long j);

    private native void deleteNativeOutputInfoObject(long j);

    private static int getFD(FileDescriptor fileDescriptor) {
        try {
            Field declaredField = FileDescriptor.class.getDeclaredField("descriptor");
            declaredField.setAccessible(true);
            return declaredField.getInt(fileDescriptor);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            return -1;
        }
    }

    public static double getGain(Image image) {
        String imageFormat = PanoramaGP3ImageFormat.getImageFormat(image);
        double[] dArr = new double[1];
        int nativeGetGain = nativeGetGain(image.getPlanes()[0].getBuffer(), image.getPlanes()[1].getBuffer(), image.getPlanes()[2].getBuffer(), image.getPlanes()[0].getRowStride(), image.getPlanes()[1].getRowStride(), image.getPlanes()[2].getRowStride(), image.getPlanes()[0].getPixelStride(), image.getPlanes()[1].getPixelStride(), image.getPlanes()[2].getPixelStride(), imageFormat, image.getWidth(), image.getHeight(), dArr);
        if (nativeGetGain != 0) {
            Log.e(TAG, "nativeGetGain error. ret=" + nativeGetGain);
            return 1.0d;
        }
        dArr[0] = GAIN_COEF * dArr[0];
        return dArr[0];
    }

    public static String getVersion() {
        return nativeGetVersion();
    }

    private native int nativeAttach(long j, byte[] bArr);

    private native int nativeAttachYuv(long j, ByteBuffer byteBuffer, ByteBuffer byteBuffer2, ByteBuffer byteBuffer3, int i, int i2, int i3, int i4, int i5, int i6, String str, double[] dArr);

    private native int nativeCreateOutputImage(long j, int i, int i2, int i3, int i4);

    private native int nativeEnd(long j, int i, double d);

    private native int nativeFinish(long j);

    private native int nativeGetClippingRect(long j, int[] iArr);

    private native int nativeGetDirection(long j, int[] iArr);

    private native int nativeGetEndStatus(long j);

    private static native int nativeGetGain(ByteBuffer byteBuffer, ByteBuffer byteBuffer2, ByteBuffer byteBuffer3, int i, int i2, int i3, int i4, int i5, int i6, String str, int i7, int i8, double[] dArr);

    private native int nativeGetImage(long j, byte[] bArr, int i, int i2, int i3, int i4);

    private native int nativeGetOutputImage(long j, byte[] bArr, int i, int i2, int i3, int i4);

    private native int nativeGetOutputSize(long j, int[] iArr);

    private native int nativeGetRotatedSmallImage(long j, byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6);

    private static native String nativeGetVersion();

    private native int nativeInitialize(long j, InitParam initParam, long j2);

    private native int nativePreparePanorama360(long j, int i, int i2, String str, String str2, boolean z, GalleryInfoData galleryInfoData);

    private static native int nativeRenderByteArray(byte[] bArr, ByteBuffer byteBuffer, ByteBuffer byteBuffer2, ByteBuffer byteBuffer3, int i, int i2, int i3, int i4, int i5, int i6, String str, int i7, int i8);

    private static native int nativeRenderByteArrayRaw(byte[] bArr, byte[] bArr2, String str, int i, int i2);

    private static native int nativeRenderByteBuffer(ByteBuffer byteBuffer, ByteBuffer byteBuffer2, ByteBuffer byteBuffer3, ByteBuffer byteBuffer4, int i, int i2, int i3, int i4, int i5, int i6, String str, int i7, int i8);

    private static native int nativeRenderByteBufferRaw(ByteBuffer byteBuffer, byte[] bArr, String str, int i, int i2);

    private native int nativeSaveLog(long j, String str, String str2);

    private static native int nativeSaveNotPanorama(ByteBuffer byteBuffer, ByteBuffer byteBuffer2, ByteBuffer byteBuffer3, ByteBuffer byteBuffer4, int i, int i2, int i3, int i4, int i5, int i6, String str, int i7, int i8, String str2, int i9);

    private native int nativeSavePanorama360(long j, int i, int i2, String str, int i3, int i4, String str2, String str3, boolean z, GalleryInfoData galleryInfoData, boolean z2);

    private native int nativeSavePanorama360Delay(long j, String str, int i, boolean z, int i2, boolean z2);

    private native int nativeSaveYuv(long j, ByteBuffer byteBuffer, ByteBuffer byteBuffer2, ByteBuffer byteBuffer3, int i, int i2, int i3, int i4, int i5, int i6, String str);

    private native int nativeSetAovGain(long j, double d);

    private native int nativeSetCalcseamPixnum(long j, int i);

    private native int nativeSetDistortionCorrectionParam(long j, double d, double d2, double d3, double d4);

    private native int nativeSetDrawThreshold(long j, double d);

    private native int nativeSetGyroscopeData(long j, MorphoSensorFusion.SensorData[] sensorDataArr);

    private native int nativeSetImageFormat(long j, String str);

    private native int nativeSetInitialRotationByGravity(long j, double d, double d2, double d3);

    private native int nativeSetMotionDetectionMode(long j, int i);

    private native int nativeSetNoiseReductionParam(long j, int i);

    private native int nativeSetPreviewImage(long j, int i, int i2);

    private native int nativeSetProjectionMode(long j, int i);

    private native int nativeSetRotationRatio(long j, double d);

    private native int nativeSetRotationVector(long j, double[] dArr);

    private native int nativeSetSeamsearchRatio(long j, double d);

    private native int nativeSetSensorUseMode(long j, int i);

    private native int nativeSetShrinkRatio(long j, double d);

    private native int nativeSetUnsharpStrength(long j, int i);

    private native int nativeSetUseDeform(long j, int i);

    private native int nativeSetUseLuminanceCorrection(long j, int i);

    private native int nativeSetZrotationCoeff(long j, double d);

    private native int nativeStart(long j, int i, int i2);

    private native int nativeUpdatePreviewImage(long j, Bitmap bitmap);

    private static native int nativeYuv2Bitmap8888(byte[] bArr, int i, int i2, Bitmap bitmap, int i3, int i4);

    public static int saveNotPanorama(CaptureImage captureImage, FileDescriptor fileDescriptor) {
        int fd = getFD(fileDescriptor);
        if (fd >= 0) {
            return saveNotPanorama_sub(captureImage, (String) null, fd);
        }
        return -2147483632;
    }

    public static int saveNotPanorama(CaptureImage captureImage, String str) {
        return saveNotPanorama_sub(captureImage, str, -1);
    }

    public static int saveNotPanorama_sub(CaptureImage captureImage, String str, int i) {
        int i2;
        Image image = captureImage.image();
        if (image != null) {
            String imageFormat = captureImage.getImageFormat();
            int width = captureImage.getWidth();
            int height = captureImage.getHeight();
            i2 = nativeSaveNotPanorama((ByteBuffer) null, image.getPlanes()[0].getBuffer(), image.getPlanes()[1].getBuffer(), image.getPlanes()[2].getBuffer(), image.getPlanes()[0].getRowStride(), image.getPlanes()[1].getRowStride(), image.getPlanes()[2].getRowStride(), image.getPlanes()[0].getPixelStride(), image.getPlanes()[1].getPixelStride(), image.getPlanes()[2].getPixelStride(), imageFormat, width, height, str, i);
        } else {
            Camera1Image camera1Image = (Camera1Image) captureImage;
            String imageFormat2 = camera1Image.getImageFormat();
            int width2 = camera1Image.getWidth();
            int height2 = camera1Image.getHeight();
            byte[] raw = camera1Image.raw();
            ByteBuffer allocateBuffer = NativeMemoryAllocator.allocateBuffer(raw.length);
            allocateBuffer.put(raw);
            allocateBuffer.clear();
            int nativeSaveNotPanorama = nativeSaveNotPanorama(allocateBuffer, (ByteBuffer) null, (ByteBuffer) null, (ByteBuffer) null, 0, 0, 0, 0, 0, 0, imageFormat2, width2, height2, str, i);
            NativeMemoryAllocator.freeBuffer(allocateBuffer);
            i2 = nativeSaveNotPanorama;
        }
        if (i2 != 0) {
            Log.e(TAG, "nativeSaveNotPanorama error. ret=" + i2);
        }
        return i2;
    }

    public static int yuv2Bitmap8888(byte[] bArr, int i, int i2, Bitmap bitmap, int i3) {
        return nativeYuv2Bitmap8888(bArr, i, i2, bitmap, i3, 1);
    }

    public static int yvu2Bitmap8888(byte[] bArr, int i, int i2, Bitmap bitmap, int i3) {
        return nativeYuv2Bitmap8888(bArr, i, i2, bitmap, i3, 0);
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x00f3  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0112  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x015b  */
    public int attach(ByteBuffer byteBuffer, ByteBuffer byteBuffer2, ByteBuffer byteBuffer3, int i, int i2, int i3, int i4, int i5, int i6, SensorInfoManager sensorInfoManager, double[] dArr, Context context) {
        String str;
        long j;
        int i7;
        int i8;
        SensorInfoManager sensorInfoManager2 = sensorInfoManager;
        if (this.mNative == 0) {
            return -2147483646;
        }
        if (this.mAttachCount == 0) {
            this.mAttachFirstNanoTime = System.nanoTime();
        }
        long nanoTime = System.nanoTime();
        String str2 = null;
        if (this.mSaveInputImages) {
            if (sensorInfoManager2 != null) {
                String createName = createName(sensorInfoManager2.timeMillis);
                String format = String.format(Locale.US, "%s/%06d_", new Object[]{this.mFolderPathInputImages, Long.valueOf(this.mAttachCount)});
                str2 = format + createName + String.format(Locale.US, "_sg%05d_sr%05d_sa%05d.yuv", new Object[]{Integer.valueOf(sensorInfoManager2.g_ix + this.mIndexBase[0]), Integer.valueOf(sensorInfoManager2.r_ix + this.mIndexBase[3]), Integer.valueOf(sensorInfoManager2.a_ix + this.mIndexBase[1])});
                int[] iArr = this.mIndexBase;
                iArr[0] = iArr[0] + sensorInfoManager2.sensorData[0].size();
                int[] iArr2 = this.mIndexBase;
                iArr2[3] = iArr2[3] + sensorInfoManager2.sensorData[3].size();
                int[] iArr3 = this.mIndexBase;
                iArr3[1] = iArr3[1] + sensorInfoManager2.sensorData[1].size();
            } else {
                String createName2 = createName(System.currentTimeMillis());
                String format2 = String.format(Locale.US, "%s/%06d_", new Object[]{this.mFolderPathInputImages, Long.valueOf(this.mAttachCount)});
                str = format2 + createName2 + ".yuv";
                if (!this.mAttachEnabled) {
                    j = nanoTime;
                    i7 = 1;
                    i8 = nativeAttachYuv(this.mNative, byteBuffer, byteBuffer2, byteBuffer3, i, i2, i3, i4, i5, i6, str, dArr);
                } else {
                    j = nanoTime;
                    i7 = 1;
                    if (str != null) {
                        i8 = nativeSaveYuv(this.mNative, byteBuffer, byteBuffer2, byteBuffer3, i, i2, i3, i4, i5, i6, str);
                    } else {
                        i8 = 0;
                    }
                }
                long nanoTime2 = System.nanoTime();
                Locale locale = Locale.US;
                Object[] objArr = new Object[i7];
                objArr[0] = Long.valueOf(nanoTime2 - j);
                Log.v(TAG, String.format(locale, "Performance.JNI %1$,3d nsec", objArr));
                this.mAttachCount++;
                if (this.mAttachCount > 1) {
                    this.mIntervalArray.add(Long.valueOf(nanoTime2 - this.mAttachLastNanoTime));
                }
                this.mAttachLastNanoTime = nanoTime2;
                return i8;
            }
        }
        str = str2;
        if (!this.mAttachEnabled) {
        }
        long nanoTime22 = System.nanoTime();
        Locale locale2 = Locale.US;
        Object[] objArr2 = new Object[i7];
        objArr2[0] = Long.valueOf(nanoTime22 - j);
        Log.v(TAG, String.format(locale2, "Performance.JNI %1$,3d nsec", objArr2));
        this.mAttachCount++;
        if (this.mAttachCount > 1) {
        }
        this.mAttachLastNanoTime = nanoTime22;
        return i8;
    }

    public int attach(byte[] bArr) {
        if (this.mNative == 0) {
            return -2147483646;
        }
        int nativeAttach = nativeAttach(this.mNative, bArr);
        if (this.mSaveInputImages) {
            String.format(Locale.US, "%06d", new Object[]{Long.valueOf(this.mAttachCount)});
        }
        this.mAttachCount++;
        return nativeAttach;
    }

    public int createNativeOutputInfo() {
        this.mNativeOutputInfo = createNativeOutputInfoObject();
        return this.mNativeOutputInfo != 0 ? 0 : -2147483646;
    }

    public int createOutputImage(Rect rect) {
        if (this.mNative == 0) {
            return -2147483646;
        }
        return nativeCreateOutputImage(this.mNative, rect.left, rect.top, rect.right, rect.bottom);
    }

    public int deleteNativeOutputInfo() {
        deleteNativeOutputInfoObject(this.mNativeOutputInfo);
        this.mNativeOutputInfo = 0;
        return 0;
    }

    public int deleteObject() {
        if (this.mNative == 0) {
            return 0;
        }
        deleteNativeObject(this.mNative);
        this.mNative = 0;
        return 0;
    }

    public void disableSaveInputImages() {
        this.mSaveInputImages = false;
    }

    public void enableSaveInputImages(String str) {
        this.mSaveInputImages = true;
        this.mFolderPathInputImages = str;
    }

    public int end(int i, double d) {
        if (this.mNative == 0) {
            return 0;
        }
        return nativeEnd(this.mNative, i, d);
    }

    public int finish(boolean z) {
        if (this.mNative == 0) {
            return -2147483646;
        }
        int nativeFinish = nativeFinish(this.mNative);
        if (!z) {
            return nativeFinish;
        }
        deleteNativeObject(this.mNative);
        this.mNative = 0;
        return nativeFinish;
    }

    public long getAttachCount() {
        return this.mAttachCount;
    }

    public int getClippingRect(Rect rect) {
        int i;
        int[] iArr = new int[4];
        if (this.mNative != 0) {
            i = nativeGetClippingRect(this.mNative, iArr);
            if (i == 0) {
                rect.set(iArr[0], iArr[1], iArr[2], iArr[3]);
            }
        } else {
            i = -2147483646;
        }
        if (i != 0) {
            rect.set(0, 0, 0, 0);
        }
        return i;
    }

    public int getDirection() {
        int[] iArr = {-1};
        if (this.mNative != 0) {
            int nativeGetDirection = nativeGetDirection(this.mNative, iArr);
            if (nativeGetDirection != 0) {
                Log.e(TAG, "MorphoPanoramaGP3.getDirection error. ret=" + nativeGetDirection);
            }
        }
        return iArr[0];
    }

    public int getEndStatus() {
        if (this.mNative != 0) {
            return nativeGetEndStatus(this.mNative);
        }
        return -1;
    }

    public int getImage(byte[] bArr, Rect rect) {
        if (this.mNative == 0) {
            return -2147483646;
        }
        return nativeGetImage(this.mNative, bArr, rect.left, rect.top, rect.right, rect.bottom);
    }

    public String getInputFolderPath() {
        return this.mFolderPathInputImages;
    }

    public String getInputImageFormat() {
        return this.mInputImageFormat;
    }

    public GravityParam getLastGravity() {
        return this.mGravity.copyInstance();
    }

    public int getOutputImage(byte[] bArr, Rect rect) {
        if (this.mNative == 0) {
            return -2147483646;
        }
        return nativeGetOutputImage(this.mNative, bArr, rect.left, rect.top, rect.right, rect.bottom);
    }

    public int getOutputImageSize(int[] iArr) {
        if (this.mNative != 0) {
            return nativeGetOutputSize(this.mNative, iArr);
        }
        return -2147483646;
    }

    public int getRotatedSmallImage(byte[] bArr, Rect rect, int i, int i2) {
        if (this.mNative == 0) {
            return -2147483646;
        }
        return nativeGetRotatedSmallImage(this.mNative, bArr, rect.left, rect.top, rect.right, rect.bottom, i, i2);
    }

    public int initialize(InitParam initParam) {
        if (this.mNativeOutputInfo == 0) {
            return -2147483646;
        }
        this.mNative = createNativeObject();
        if (this.mNative == 0) {
            return -2147483644;
        }
        return nativeInitialize(this.mNative, initParam, this.mNativeOutputInfo);
    }

    public int inputSave(Image image) {
        if (this.mNative == 0) {
            return -2147483646;
        }
        long currentTimeMillis = System.currentTimeMillis();
        String str = null;
        if (this.mSaveInputImages) {
            str = String.format(Locale.US, "%s/%06d.yuv", new Object[]{this.mFolderPathInputImages, Long.valueOf(this.mAttachCount)});
        }
        long j = this.mNative;
        ByteBuffer buffer = image.getPlanes()[0].getBuffer();
        ByteBuffer buffer2 = image.getPlanes()[1].getBuffer();
        ByteBuffer buffer3 = image.getPlanes()[2].getBuffer();
        int rowStride = image.getPlanes()[0].getRowStride();
        int rowStride2 = image.getPlanes()[1].getRowStride();
        int rowStride3 = image.getPlanes()[2].getRowStride();
        int pixelStride = image.getPlanes()[0].getPixelStride();
        int pixelStride2 = image.getPlanes()[1].getPixelStride();
        int pixelStride3 = image.getPlanes()[2].getPixelStride();
        int nativeSaveYuv = nativeSaveYuv(j, buffer, buffer2, buffer3, rowStride, rowStride2, rowStride3, pixelStride, pixelStride2, pixelStride3, str);
        long currentTimeMillis2 = System.currentTimeMillis();
        Log.v(TAG, "Performance.JNI " + (currentTimeMillis2 - currentTimeMillis) + " msec");
        this.mAttachCount = this.mAttachCount + 1;
        return nativeSaveYuv;
    }

    public int preparePanorama360(int i, int i2, String str, String str2, boolean z, GalleryInfoData galleryInfoData) {
        if (this.mNative == 0) {
            return -2147483646;
        }
        return nativePreparePanorama360(this.mNative, i, i2, str, str2, z, galleryInfoData);
    }

    public int saveLog(String str, String str2) {
        if (this.mNative != 0) {
            return nativeSaveLog(this.mNative, str, str2);
        }
        return -2147483646;
    }

    public int savePanorama360(int i, int i2, FileDescriptor fileDescriptor, int i3, String str, String str2, boolean z, GalleryInfoData galleryInfoData, boolean z2) {
        if (this.mNative == 0) {
            return -2147483646;
        }
        int fd = getFD(fileDescriptor);
        if (fd >= 0) {
            return nativeSavePanorama360(this.mNative, i, i2, (String) null, fd, i3, str, str2, z, galleryInfoData, z2);
        }
        return -2147483632;
    }

    public int savePanorama360(int i, int i2, String str, int i3, String str2, String str3, boolean z, GalleryInfoData galleryInfoData, boolean z2) {
        if (this.mNative != 0) {
            return nativeSavePanorama360(this.mNative, i, i2, str, 0, i3, str2, str3, z, galleryInfoData, z2);
        }
        return -2147483646;
    }

    public int savePanorama360Delay(FileDescriptor fileDescriptor, boolean z, int i, boolean z2) {
        if (this.mNativeOutputInfo == 0) {
            return -2147483646;
        }
        int fd = getFD(fileDescriptor);
        if (fd < 0) {
            return -2147483632;
        }
        return nativeSavePanorama360Delay(this.mNativeOutputInfo, (String) null, fd, z, i, z2);
    }

    public int savePanorama360Delay(String str, boolean z, int i, boolean z2) {
        if (this.mNativeOutputInfo == 0) {
            return -2147483646;
        }
        return nativeSavePanorama360Delay(this.mNativeOutputInfo, str, 0, z, i, z2);
    }

    public int setAovGain(double d) {
        if (this.mNative != 0) {
            return nativeSetAovGain(this.mNative, d);
        }
        return -2147483646;
    }

    public void setAttachEnabled(boolean z) {
        this.mAttachEnabled = z;
    }

    public int setCalcseamPixnum(int i) {
        if (this.mNative != 0) {
            return nativeSetCalcseamPixnum(this.mNative, i);
        }
        return -2147483646;
    }

    public int setDistortionCorrectionParam(double d, double d2, double d3, double d4) {
        if (this.mNative != 0) {
            return nativeSetDistortionCorrectionParam(this.mNative, d, d2, d3, d4);
        }
        return -2147483646;
    }

    public int setDrawThreshold(double d) {
        if (this.mNative != 0) {
            return nativeSetDrawThreshold(this.mNative, d);
        }
        return -2147483646;
    }

    public int setGyroscopeData(MorphoSensorFusion.SensorData[] sensorDataArr) {
        if (this.mNative != 0) {
            return nativeSetGyroscopeData(this.mNative, sensorDataArr);
        }
        return -2147483646;
    }

    public int setInitialRotationByGravity(double d, double d2, double d3) {
        if (this.mNative == 0) {
            return -2147483646;
        }
        int nativeSetInitialRotationByGravity = nativeSetInitialRotationByGravity(this.mNative, d, d2, d3);
        this.mGravity.x = d;
        this.mGravity.y = d2;
        this.mGravity.z = d3;
        return nativeSetInitialRotationByGravity;
    }

    public int setInputImageFormat(String str) {
        this.mInputImageFormat = "";
        if (this.mNative == 0) {
            return -2147483646;
        }
        int nativeSetImageFormat = nativeSetImageFormat(this.mNative, str);
        if (nativeSetImageFormat != 0) {
            return nativeSetImageFormat;
        }
        this.mInputImageFormat = str;
        return nativeSetImageFormat;
    }

    public int setMotionDetectionMode(int i) {
        if (this.mNative != 0) {
            return nativeSetMotionDetectionMode(this.mNative, i);
        }
        return -2147483646;
    }

    public int setNoiseReductionParam(int i) {
        if (this.mNative != 0) {
            return nativeSetNoiseReductionParam(this.mNative, i);
        }
        return -2147483646;
    }

    public int setPreviewImage(int i, int i2) {
        if (this.mNative == 0) {
            return -2147483646;
        }
        return nativeSetPreviewImage(this.mNative, i, i2);
    }

    public int setProjectionMode(int i) {
        if (this.mNative != 0) {
            return nativeSetProjectionMode(this.mNative, i);
        }
        return -2147483646;
    }

    public int setRotationRatio(double d) {
        if (this.mNative != 0) {
            return nativeSetRotationRatio(this.mNative, d);
        }
        return -2147483646;
    }

    public int setRotationVector(double[] dArr) {
        if (this.mNative != 0) {
            return nativeSetRotationVector(this.mNative, dArr);
        }
        return -2147483646;
    }

    public int setSeamsearchRatio(double d) {
        if (this.mNative != 0) {
            return nativeSetSeamsearchRatio(this.mNative, d);
        }
        return -2147483646;
    }

    public int setSensorUseMode(int i) {
        if (this.mNative != 0) {
            return nativeSetSensorUseMode(this.mNative, i);
        }
        return -2147483646;
    }

    public int setShrinkRatio(double d) {
        if (this.mNative != 0) {
            return nativeSetShrinkRatio(this.mNative, d);
        }
        return -2147483646;
    }

    public int setUnsharpStrength(int i) {
        if (this.mNative != 0) {
            return nativeSetUnsharpStrength(this.mNative, i);
        }
        return -2147483646;
    }

    public int setUseDeform(boolean z) {
        if (this.mNative != 0) {
            return nativeSetUseDeform(this.mNative, z ? 1 : 0);
        }
        return -2147483646;
    }

    public int setUseLuminanceCorrection(boolean z) {
        if (this.mNative != 0) {
            return nativeSetUseLuminanceCorrection(this.mNative, z ? 1 : 0);
        }
        return -2147483646;
    }

    public int setZrotationCoeff(double d) {
        if (this.mNative != 0) {
            return nativeSetZrotationCoeff(this.mNative, d);
        }
        return -2147483646;
    }

    public int start(int i, int i2) {
        if (this.mNative == 0) {
            return -2147483646;
        }
        int nativeStart = nativeStart(this.mNative, i, i2);
        this.mAttachCount = 0;
        return nativeStart;
    }

    public int updatePreviewImage(Bitmap bitmap) {
        if (this.mNative == 0) {
            return -2147483646;
        }
        return nativeUpdatePreviewImage(this.mNative, bitmap);
    }
}
