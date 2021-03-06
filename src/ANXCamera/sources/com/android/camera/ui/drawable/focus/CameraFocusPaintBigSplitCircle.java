package com.android.camera.ui.drawable.focus;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import com.android.camera.Util;
import com.android.camera.ui.drawable.CameraPaintBase;

public class CameraFocusPaintBigSplitCircle extends CameraPaintBase {
    private static final int SPLIT_HEIGHT = 10;
    private Paint mDownPaint;
    private Paint mUpPaint;

    public CameraFocusPaintBigSplitCircle(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public void draw(Canvas canvas) {
        this.mUpPaint.setAlpha(this.mCurrentAlpha);
        canvas.save();
        float strokeWidth = this.mUpPaint.getStrokeWidth();
        float f2 = this.mBaseRadius * this.mCurrentWidthPercent;
        float f3 = this.mMiddleX;
        float f4 = this.mMiddleY;
        canvas.clipRect((f3 - f2) - strokeWidth, (f4 - f2) - strokeWidth, f3 + f2 + strokeWidth, f4 - 5.0f);
        canvas.drawCircle(this.mMiddleX, this.mMiddleY, f2, this.mUpPaint);
        canvas.restore();
        this.mDownPaint.setAlpha(this.mCurrentAlpha);
        canvas.save();
        float strokeWidth2 = this.mDownPaint.getStrokeWidth();
        float f5 = this.mMiddleX;
        float f6 = this.mMiddleY;
        canvas.clipRect((f5 - f2) - strokeWidth2, 5.0f + f6, f5 + f2 + strokeWidth2, f6 + f2 + strokeWidth2);
        canvas.drawCircle(this.mMiddleX, this.mMiddleY, f2, this.mDownPaint);
        canvas.restore();
    }

    /* access modifiers changed from: protected */
    public void initPaint(Context context) {
        this.mUpPaint = new Paint();
        this.mUpPaint.setAntiAlias(true);
        this.mUpPaint.setStrokeWidth((float) Util.dpToPixel(1.0f));
        this.mUpPaint.setStyle(Paint.Style.STROKE);
        this.mUpPaint.setColor(-1);
        this.mDownPaint = new Paint();
        this.mDownPaint.setAntiAlias(true);
        this.mDownPaint.setStrokeWidth((float) Util.dpToPixel(1.0f));
        this.mDownPaint.setStyle(Paint.Style.STROKE);
        this.mDownPaint.setColor(Color.rgb(255, 204, 0));
    }
}
