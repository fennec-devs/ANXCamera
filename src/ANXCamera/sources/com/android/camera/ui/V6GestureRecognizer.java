package com.android.camera.ui;

import android.graphics.Point;
import android.graphics.RectF;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import com.android.camera.ActivityBase;
import com.android.camera.Camera;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.data.data.config.SupportedConfigFactory;
import com.android.camera.effect.EffectController;
import com.android.camera.log.Log;
import com.android.camera.module.Module;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStat;
import com.android.camera.statistic.CameraStatUtil;
import com.android.camera.ui.zoom.ScaleGestureDetector;
import java.lang.ref.WeakReference;

public class V6GestureRecognizer {
    public static final int GESTURE_EFFECT_CROP_VIEW = 6;
    public static final int GESTURE_EV_ADJUST = 7;
    public static final int GESTURE_HON = 100;
    public static final int GESTURE_NONE = 0;
    public static final int GESTURE_TRACK = 10;
    public static final int GESTURE_VER = 200;
    public static final int GESTURE_ZOOM = 9;
    public static final int SINGLE_MAX_GESTURE = 100;
    private static final String TAG = "CameraGestureRecognizer";
    private static V6GestureRecognizer sV6GestureRecognizer;
    /* access modifiers changed from: private */
    public final int MIN_DETECT_DISTANCE;
    private WeakReference<Camera> mActivityRef;
    private final CameraGestureDetector mCameraGestureDetector;
    /* access modifiers changed from: private */
    public Module mCurrentModule;
    /* access modifiers changed from: private */
    public float mDistanceX;
    /* access modifiers changed from: private */
    public float mDistanceY;
    private int mEdgeGesture = 0;
    /* access modifiers changed from: private */
    public int mGesture = 0;
    private final GestureDetector mGestureDetector;
    /* access modifiers changed from: private */
    public boolean mInScaling;
    private final ScaleGestureDetector mScaleDetector;
    private boolean mScaleDetectorEnable = true;
    /* access modifiers changed from: private */
    public int mScrollDirection;
    /* access modifiers changed from: private */
    public boolean mScrolled;
    private boolean mTouchDown;

    private class CameraGestureDetector {
        private Point mStartPoint = new Point();

        public CameraGestureDetector() {
        }

        public void onTouchEvent(MotionEvent motionEvent) {
            float f;
            float f2;
            float f3;
            float f4;
            int action = motionEvent.getAction() & 255;
            if (action == 0) {
                this.mStartPoint.set((int) motionEvent.getX(), (int) motionEvent.getY());
            } else if (action == 2) {
                Log.v(Log.GESTURE_TAG, "CameraGestureDetector ACTION_MOVE mGesture=" + V6GestureRecognizer.this.mGesture);
                int i = 100;
                if (V6GestureRecognizer.this.mGesture / 100 == 0) {
                    Point access$300 = V6GestureRecognizer.this.getMoveVector(this.mStartPoint.x, this.mStartPoint.y, (int) motionEvent.getX(), (int) motionEvent.getY());
                    StringBuilder sb = new StringBuilder();
                    sb.append("mGesture=");
                    sb.append(V6GestureRecognizer.this.mGesture);
                    sb.append(" orientation=");
                    sb.append(Math.abs(access$300.x) > Math.abs(access$300.y) ? SupportedConfigFactory.CLOSE_BY_VIDEO : "v");
                    sb.append(" dx=");
                    sb.append(access$300.x);
                    sb.append(" dy=");
                    sb.append(access$300.y);
                    Log.v(V6GestureRecognizer.TAG, sb.toString());
                    if (V6GestureRecognizer.this.MIN_DETECT_DISTANCE <= (access$300.x * access$300.x) + (access$300.y * access$300.y)) {
                        V6GestureRecognizer v6GestureRecognizer = V6GestureRecognizer.this;
                        if (Math.abs(access$300.x) <= Math.abs(access$300.y)) {
                            i = 200;
                        }
                        V6GestureRecognizer.access$212(v6GestureRecognizer, i);
                    }
                }
                Log.v(Log.GESTURE_TAG, "CameraGestureDetector ACTION_MOVE end mGesture=" + V6GestureRecognizer.this.mGesture);
            } else if (action == 6 && motionEvent.getPointerCount() == 2 && V6GestureRecognizer.this.couldNotifyGesture(false)) {
                if (motionEvent.getX(0) < motionEvent.getX(1)) {
                    f2 = motionEvent.getX(0);
                    f = motionEvent.getX(1);
                } else {
                    f2 = motionEvent.getX(1);
                    f = motionEvent.getX(0);
                }
                if (motionEvent.getY(0) < motionEvent.getY(1)) {
                    f4 = motionEvent.getY(0);
                    f3 = motionEvent.getY(1);
                } else {
                    f4 = motionEvent.getY(1);
                    f3 = motionEvent.getY(0);
                }
                if (V6GestureRecognizer.this.couldNotifyGesture(false)) {
                    V6GestureRecognizer.access$212(V6GestureRecognizer.this, 10);
                    V6GestureRecognizer.this.mCurrentModule.onGestureTrack(new RectF(f2, f4, f, f3), true);
                }
            }
        }
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private boolean mHandleConfirmTap;

        private MyGestureListener() {
        }

        private boolean handleSingleTap(MotionEvent motionEvent) {
            if (!V6GestureRecognizer.this.couldNotifyGesture(false)) {
                return false;
            }
            V6GestureRecognizer.this.mCurrentModule.onSingleTapUp((int) motionEvent.getX(), (int) motionEvent.getY(), false);
            return true;
        }

        public boolean onDoubleTap(MotionEvent motionEvent) {
            int i = 0;
            if (!this.mHandleConfirmTap) {
                return false;
            }
            int invertFlag = EffectController.getInstance().getInvertFlag();
            EffectController instance = EffectController.getInstance();
            if (invertFlag == 0) {
                i = 1;
            }
            instance.setInvertFlag(i);
            return true;
        }

        public void onLongPress(MotionEvent motionEvent) {
            Log.v(V6GestureRecognizer.TAG, "onLongPress");
            if (V6GestureRecognizer.this.couldNotifyGesture(false)) {
                V6GestureRecognizer.this.mCurrentModule.onLongPress(motionEvent.getX(), motionEvent.getY());
            }
        }

        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            if (V6GestureRecognizer.this.mInScaling || V6GestureRecognizer.this.mScrolled) {
                return false;
            }
            Log.d(V6GestureRecognizer.TAG, "onScroll: " + motionEvent.getX() + "|" + motionEvent.getY() + "|" + motionEvent2.getX() + "|" + motionEvent2.getY() + "|distanceX:" + f + "|distanceY:" + f2);
            V6GestureRecognizer.access$916(V6GestureRecognizer.this, -f2);
            if (Math.abs(V6GestureRecognizer.this.mDistanceY) > ((float) Util.dpToPixel(80.0f)) || V6GestureRecognizer.this.getCurrentGesture() == 7 || V6GestureRecognizer.this.getCurrentGesture() == 6) {
                return false;
            }
            V6GestureRecognizer.access$1016(V6GestureRecognizer.this, -f);
            if (V6GestureRecognizer.this.mScrollDirection == 0 && ((float) V6GestureRecognizer.this.MIN_DETECT_DISTANCE) <= (V6GestureRecognizer.this.mDistanceX * V6GestureRecognizer.this.mDistanceX) + (V6GestureRecognizer.this.mDistanceY * V6GestureRecognizer.this.mDistanceY)) {
                int unused = V6GestureRecognizer.this.mScrollDirection = Math.abs(V6GestureRecognizer.this.mDistanceX) > Math.abs(V6GestureRecognizer.this.mDistanceY) ? 100 : 200;
            }
            if (V6GestureRecognizer.this.mScrollDirection != 100) {
                return false;
            }
            int i = -1;
            float f3 = (float) 35;
            if (V6GestureRecognizer.this.mDistanceX > ((float) Util.dpToPixel(f3)) && V6GestureRecognizer.this.mDistanceY < ((float) Util.dpToPixel(f3))) {
                i = 3;
                boolean unused2 = V6GestureRecognizer.this.mScrolled = true;
            } else if (V6GestureRecognizer.this.mDistanceX < ((float) (-Util.dpToPixel(f3))) && V6GestureRecognizer.this.mDistanceY > ((float) (-Util.dpToPixel(f3)))) {
                i = 5;
                boolean unused3 = V6GestureRecognizer.this.mScrolled = true;
            }
            ModeProtocol.HandlerSwitcher handlerSwitcher = (ModeProtocol.HandlerSwitcher) ModeCoordinatorImpl.getInstance().getAttachProtocol(183);
            if (handlerSwitcher != null && handlerSwitcher.onHandleSwitcher(i)) {
                return true;
            }
            ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
            if (miBeautyProtocol != null && miBeautyProtocol.isBeautyPanelShow()) {
                return true;
            }
            ModeProtocol.ModeChangeController modeChangeController = (ModeProtocol.ModeChangeController) ModeCoordinatorImpl.getInstance().getAttachProtocol(179);
            if (modeChangeController == null || !modeChangeController.canSwipeChangeMode()) {
                return false;
            }
            modeChangeController.changeModeByGravity(i, 0);
            return true;
        }

        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            ModeProtocol.MainContentProtocol mainContentProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
            if (mainContentProtocol == null || !mainContentProtocol.isEffectViewVisible() || !this.mHandleConfirmTap) {
                return false;
            }
            return handleSingleTap(motionEvent);
        }

        public boolean onSingleTapUp(MotionEvent motionEvent) {
            Log.v(V6GestureRecognizer.TAG, "onSingleTapUp");
            ModeProtocol.MainContentProtocol mainContentProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
            if (mainContentProtocol == null || !mainContentProtocol.isEffectViewVisible()) {
                return handleSingleTap(motionEvent);
            }
            this.mHandleConfirmTap = V6GestureRecognizer.this.couldNotifyGesture(false);
            return false;
        }
    }

    private class MyScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        private boolean mZoomScaled;

        private MyScaleListener() {
        }

        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            if (!V6GestureRecognizer.this.isCurrentModuleAlive()) {
                return false;
            }
            if (!V6GestureRecognizer.this.couldNotifyGesture(false) && V6GestureRecognizer.this.getCurrentGesture() != 9) {
                return false;
            }
            V6GestureRecognizer.this.setGesture(9);
            boolean onScale = V6GestureRecognizer.this.mCurrentModule.onScale(scaleGestureDetector.getFocusX(), scaleGestureDetector.getFocusY(), scaleGestureDetector.getScaleFactor());
            if (!this.mZoomScaled) {
                this.mZoomScaled = onScale;
            }
            return onScale;
        }

        public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
            if (!V6GestureRecognizer.this.isCurrentModuleAlive() || V6GestureRecognizer.this.mCurrentModule.isIgnoreTouchEvent()) {
                return false;
            }
            this.mZoomScaled = false;
            HybridZoomingSystem.setZoomingSourceIdentity(V6GestureRecognizer.this.mCurrentModule.hashCode());
            return V6GestureRecognizer.this.mCurrentModule.onScaleBegin(scaleGestureDetector.getFocusX(), scaleGestureDetector.getFocusY());
        }

        public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
            if (this.mZoomScaled) {
                this.mZoomScaled = false;
            }
            if (V6GestureRecognizer.this.isCurrentModuleAlive()) {
                CameraStatUtil.trackZoomAdjusted(CameraStat.ZOOM_MODE_GESTURE, V6GestureRecognizer.this.mCurrentModule.isRecording());
                V6GestureRecognizer.this.mCurrentModule.onScaleEnd();
            }
        }
    }

    private V6GestureRecognizer(ActivityBase activityBase) {
        Camera camera = (Camera) activityBase;
        this.mActivityRef = new WeakReference<>(camera);
        this.MIN_DETECT_DISTANCE = ViewConfiguration.get(camera).getScaledTouchSlop() * ViewConfiguration.get(camera).getScaledTouchSlop();
        this.mGestureDetector = new GestureDetector(camera, new MyGestureListener(), (Handler) null, true);
        this.mScaleDetector = new ScaleGestureDetector(camera, new MyScaleListener());
        this.mCameraGestureDetector = new CameraGestureDetector();
    }

    static /* synthetic */ float access$1016(V6GestureRecognizer v6GestureRecognizer, float f) {
        float f2 = v6GestureRecognizer.mDistanceX + f;
        v6GestureRecognizer.mDistanceX = f2;
        return f2;
    }

    static /* synthetic */ int access$212(V6GestureRecognizer v6GestureRecognizer, int i) {
        int i2 = v6GestureRecognizer.mGesture + i;
        v6GestureRecognizer.mGesture = i2;
        return i2;
    }

    static /* synthetic */ float access$916(V6GestureRecognizer v6GestureRecognizer, float f) {
        float f2 = v6GestureRecognizer.mDistanceY + f;
        v6GestureRecognizer.mDistanceY = f2;
        return f2;
    }

    private boolean checkControlView(MotionEvent motionEvent) {
        ModeProtocol.MainContentProtocol mainContentProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
        if (mainContentProtocol != null && !mainContentProtocol.isAutoZoomViewEnabled()) {
            if (mainContentProtocol.isEffectViewVisible()) {
                mainContentProtocol.onViewTouchEvent(R.id.v6_effect_crop_view, motionEvent);
                if (mainContentProtocol.isEffectViewMoved()) {
                    if (isGestureDetecting()) {
                        this.mGesture += 6;
                    }
                } else if (!mainContentProtocol.isEffectViewMoved() && getCurrentGesture() == 6) {
                    setGesture(0);
                }
            }
            if (mainContentProtocol.isIndicatorVisible(2)) {
                boolean isEvAdjusted = mainContentProtocol.isEvAdjusted(false);
                mainContentProtocol.onViewTouchEvent(R.id.v6_focus_view, motionEvent);
                if (mainContentProtocol.isEvAdjusted(false)) {
                    if (isGestureDetecting()) {
                        this.mGesture += 7;
                    }
                } else if (!isEvAdjusted && getCurrentGesture() == 7) {
                    setGesture(0);
                }
            }
        }
        return !isGestureDetecting();
    }

    /* access modifiers changed from: private */
    public boolean couldNotifyGesture(boolean z) {
        return isGestureDetecting(z) && isCurrentModuleAlive();
    }

    private Camera getActivity() {
        if (this.mActivityRef != null) {
            return (Camera) this.mActivityRef.get();
        }
        return null;
    }

    public static synchronized V6GestureRecognizer getInstance(ActivityBase activityBase) {
        V6GestureRecognizer v6GestureRecognizer;
        synchronized (V6GestureRecognizer.class) {
            if (sV6GestureRecognizer == null || activityBase != sV6GestureRecognizer.getActivity()) {
                sV6GestureRecognizer = new V6GestureRecognizer(activityBase);
            }
            v6GestureRecognizer = sV6GestureRecognizer;
        }
        return v6GestureRecognizer;
    }

    /* access modifiers changed from: private */
    public Point getMoveVector(int i, int i2, int i3, int i4) {
        Point point = new Point();
        point.x = i - i3;
        point.y = i2 - i4;
        return point;
    }

    /* access modifiers changed from: private */
    public boolean isCurrentModuleAlive() {
        return this.mCurrentModule != null && this.mCurrentModule.isCreated() && !this.mCurrentModule.isDeparted();
    }

    private boolean isGestureDetecting(boolean z) {
        return (z ? this.mEdgeGesture : this.mGesture) % 100 == 0;
    }

    public static void onDestroy(ActivityBase activityBase) {
        if (sV6GestureRecognizer != null && sV6GestureRecognizer.getActivity() == activityBase) {
            sV6GestureRecognizer = null;
        }
    }

    public int getCurrentGesture() {
        return this.mGesture % 100;
    }

    public int getGestureOrientation() {
        return (this.mGesture / 100) * 100;
    }

    public boolean isGestureDetecting() {
        return this.mGesture % 100 == 0;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        Log.v(TAG, "onTouchEvent mGesture=" + this.mGesture + " action=" + motionEvent.getAction());
        if (motionEvent.getActionMasked() == 0) {
            this.mGesture = 0;
        }
        if (motionEvent.getActionMasked() == 0) {
            this.mTouchDown = true;
            this.mInScaling = false;
        } else if (!this.mTouchDown) {
            return false;
        } else {
            if (motionEvent.getActionMasked() == 3 || motionEvent.getActionMasked() == 1) {
                this.mTouchDown = false;
            } else if (!this.mInScaling && motionEvent.getPointerCount() > 1) {
                this.mInScaling = true;
            }
        }
        if (motionEvent.getActionMasked() == 0 || motionEvent.getActionMasked() == 5) {
            this.mScrolled = false;
            this.mScrollDirection = 0;
            this.mDistanceX = 0.0f;
            this.mDistanceY = 0.0f;
        }
        Log.v(TAG, "set to detector");
        if (this.mScaleDetectorEnable) {
            this.mScaleDetector.onTouchEvent(motionEvent);
        }
        this.mCameraGestureDetector.onTouchEvent(motionEvent);
        checkControlView(motionEvent);
        this.mGestureDetector.onTouchEvent(motionEvent);
        boolean z = !isGestureDetecting();
        if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
            this.mGesture = 0;
            this.mInScaling = false;
            this.mScrolled = false;
            this.mScrollDirection = 0;
            this.mDistanceX = 0.0f;
            this.mDistanceY = 0.0f;
        }
        return z;
    }

    public void setCurrentModule(Module module) {
        this.mCurrentModule = module;
    }

    public void setGesture(int i) {
        this.mGesture = ((this.mGesture / 100) * 100) + i;
    }

    public void setScaleDetectorEnable(boolean z) {
        this.mScaleDetectorEnable = z;
    }
}
