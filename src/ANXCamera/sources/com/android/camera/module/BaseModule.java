package com.android.camera.module;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.params.MeteringRectangle;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.CallSuper;
import android.support.annotation.StringRes;
import android.support.annotation.WorkerThread;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import com.android.camera.Camera;
import com.android.camera.CameraAppImpl;
import com.android.camera.CameraErrorCallbackImpl;
import com.android.camera.CameraSettings;
import com.android.camera.CameraSize;
import com.android.camera.FileCompat;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.LocalParallelService;
import com.android.camera.MutexModeManager;
import com.android.camera.R;
import com.android.camera.SensorStateManager;
import com.android.camera.ThermalDetector;
import com.android.camera.Util;
import com.android.camera.constant.AutoFocus;
import com.android.camera.constant.GlobalConstant;
import com.android.camera.constant.UpdateConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.global.DataItemGlobal;
import com.android.camera.data.provider.DataProvider;
import com.android.camera.effect.EffectController;
import com.android.camera.effect.draw_mode.DrawExtTexAttribute;
import com.android.camera.fragment.beauty.BeautyValues;
import com.android.camera.lib.compatibility.related.vibrator.ViberatorContext;
import com.android.camera.log.Log;
import com.android.camera.module.loader.ActionHideLensDirtyDetectHint;
import com.android.camera.module.loader.ActionUpdateLensDirtyDetect;
import com.android.camera.module.loader.StartControl;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera.parallel.AlgoConnector;
import com.android.camera.preferences.SettingsOverrider;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera.statistic.MistatsWrapper;
import com.android.camera.storage.ImageSaver;
import com.android.camera.storage.Storage;
import com.android.camera.ui.FocusView;
import com.android.camera2.Camera2Proxy;
import com.android.camera2.CameraCapabilities;
import com.android.camera2.CaptureResultParser;
import com.mi.config.a;
import com.mi.config.b;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class BaseModule implements Module, FocusView.ExposureViewListener, Camera2Proxy.CameraMetaDataCallback, ModeProtocol.EvChangedProtocol, ModeProtocol.ZoomProtocol, ModeProtocol.CameraModuleSpecial, MutexModeManager.MutexCallBack, ModeProtocol.LensProtocol, Consumer<int[]> {
    protected static final int BACK_PRESSED_TIME_INTERVAL = 3000;
    public static final int[] CAMERA_MODES = {0, 2, 4, 6};
    public static final int CAMERA_MODE_IMAGE_CAPTURE = 2;
    public static final int CAMERA_MODE_NORMAL = 0;
    public static final int CAMERA_MODE_SCAN_QR_CODE = 6;
    public static final int CAMERA_MODE_VIDEO_CAPTURE = 4;
    protected static final boolean DEBUG = Util.isDebugOsBuild();
    public static final int DOCUMENT_BLUR_DETECT_HINT_DURATION_3S = 3000;
    public static final int LENS_DIRTY_DETECT_HINT_DURATION_3S = 3000;
    public static final int LENS_DIRTY_DETECT_HINT_DURATION_8S = 8000;
    protected static final int LENS_DIRTY_DETECT_TIMEOUT = 15000;
    protected static final int SCREEN_DELAY = 60000;
    protected static final int SCREEN_DELAY_KEYGUARD = 30000;
    public static final int SHUTTER_DOWN_FROM_BUTTON = 2;
    public static final int SHUTTER_DOWN_FROM_HARD_KEY = 1;
    public static final int SHUTTER_DOWN_FROM_UNKNOWN = 0;
    public static final int SHUTTER_DOWN_FROM_UNLOCK_OR_LONG_CLICK_OR_AUDIO = 3;
    private static final String TAG = "BaseModule";
    private boolean isShowPreviewDebugInfo;
    protected boolean isZooming;
    protected boolean m3ALockSupported;
    protected boolean mAELockOnlySupported;
    protected Camera mActivity;
    protected int mActualCameraId;
    protected boolean mAeLockSupported;
    protected CameraSize mAlgorithmPreviewSize;
    protected boolean mAwbLockSupported;
    protected CameraSize mBinningPictureSize;
    protected int mBogusCameraId;
    protected Camera2Proxy mCamera2Device;
    protected CameraCapabilities mCameraCapabilities;
    protected boolean mCameraDisabled;
    protected int mCameraDisplayOrientation;
    protected boolean mCameraHardwareError;
    private int mCameraState = 0;
    protected boolean mContinuousFocusSupported;
    protected float mDeviceRotation = -1.0f;
    protected Rect mDisplayRect;
    protected int mDisplayRotation = -1;
    protected CameraErrorCallbackImpl mErrorCallback;
    protected int mEvState;
    protected int mEvValue;
    private int mEvValueForTrack;
    protected volatile boolean mFallbackProcessed;
    protected String mFlashAutoModeState;
    protected boolean mFocusAreaSupported;
    protected boolean mFocusOrAELockSupported;
    protected Handler mHandler;
    protected boolean mIgnoreFocusChanged;
    private volatile boolean mIgnoreTouchEvent;
    private AtomicBoolean mIsCreated = new AtomicBoolean(false);
    private AtomicBoolean mIsDeparted = new AtomicBoolean(false);
    private AtomicBoolean mIsFrameAvailable = new AtomicBoolean(false);
    protected volatile int mIsSatFallback = 0;
    protected long mLastBackPressedTime;
    protected volatile int mLastSatFallbackRequestId = -1;
    private float mLastZoomRatio = 1.0f;
    private Disposable mLensDirtyDetectDisposable;
    private Disposable mLensDirtyDetectHintDisposable;
    protected CameraCapabilities mMacroCameraCapabilities;
    protected CameraSize mMacroPitureSize;
    protected ModeProtocol.MainContentProtocol mMainProtocol;
    protected long mMainThreadId = Thread.currentThread().getId();
    protected int mMaxFaceCount;
    private float mMaxZoomRatio = 1.0f;
    protected boolean mMeteringAreaSupported;
    private float mMinZoomRatio = 1.0f;
    protected int mModuleIndex;
    protected MutexModeManager mMutexModePicker;
    protected boolean mObjectTrackingStarted;
    protected boolean mOpenCameraFail;
    protected int mOrientation = 0;
    protected int mOrientationCompensation = 0;
    protected int mOutputPictureFormat;
    protected CameraSize mOutputPictureSize;
    protected volatile boolean mPaused;
    protected int mPendingScreenSlideKeyCode;
    protected CameraSize mPictureSize;
    protected CameraSize mPreviewSize;
    private boolean mRestoring;
    protected CameraSize mSensorRawImageSize;
    protected SettingsOverrider mSettingsOverrider = new SettingsOverrider();
    protected CameraCapabilities mStandaloneMacroCameraCapabilities;
    protected CameraSize mSubPictureSize;
    protected long mSurfaceCreatedTimestamp;
    protected CameraCapabilities mTeleCameraCapabilities;
    protected CameraSize mTelePictureSize;
    private int mThermalLevel = 0;
    protected int mTriggerMode = 10;
    protected int mUIStyle = -1;
    protected CameraCapabilities mUltraCameraCapabilities;
    protected CameraCapabilities mUltraTeleCameraCapabilities;
    protected CameraSize mUltraTelePictureSize;
    protected CameraSize mUltraWidePictureSize;
    protected String mUnInterruptableReason;
    private Disposable mUpdateWorkThreadDisposable;
    /* access modifiers changed from: private */
    public ObservableEmitter<int[]> mUpdateWorkThreadEmitter;
    protected boolean mUseLegacyFlashMode;
    protected CameraCapabilities mWideCameraCapabilities;
    protected CameraSize mWidePictureSize;
    private float mZoomRatio = 1.0f;
    private float mZoomScaled;
    protected boolean mZoomSupported;

    @Retention(RetentionPolicy.SOURCE)
    public @interface CameraMode {
    }

    private void detectLensDirty(CaptureResult captureResult) {
        CameraCapabilities cameraCapabilities = this.mCameraCapabilities;
        if ((cameraCapabilities == null || cameraCapabilities.getMiAlgoASDVersion() < 2.0f) && CaptureResultParser.isLensDirtyDetected(captureResult)) {
            showLensDirtyTip();
        }
    }

    protected static String getColorEffectKey() {
        return b.dl() ? "pref_camera_shader_coloreffect_key" : CameraSettings.KEY_COLOR_EFFECT;
    }

    public static int getPreferencesLocalId(int i, int i2) {
        return i + i2;
    }

    private void setCreated(boolean z) {
        this.mIsCreated.set(z);
    }

    private void setIgnoreTouchEvent(boolean z) {
        this.mIgnoreTouchEvent = z;
    }

    private void showDebug(final CaptureResult captureResult, final boolean z) {
        if (Util.isShowAfRegionView() && z) {
            this.mMainProtocol.setAfRegionView((MeteringRectangle[]) captureResult.get(CaptureResult.CONTROL_AF_REGIONS), getActiveArraySize(), getDeviceBasedZoomRatio());
        }
        if (Util.isShowDebugInfoView()) {
            this.mActivity.runOnUiThread(new Runnable() {
                public void run() {
                    if (z) {
                        BaseModule baseModule = BaseModule.this;
                        baseModule.mActivity.showDebugInfo(Util.getDebugInformation(captureResult, baseModule.getDebugInfo()));
                        return;
                    }
                    BaseModule.this.mActivity.showDebugInfo("");
                }
            });
        }
    }

    private void switchCameraLens(boolean z, boolean z2, boolean z3) {
        DataItemGlobal dataItemGlobal = (DataItemGlobal) DataRepository.provider().dataGlobal();
        int currentMode = dataItemGlobal.getCurrentMode();
        dataItemGlobal.setCameraId(0);
        this.mActivity.onModeSelected(StartControl.create(currentMode).setStartDelay(0).setResetType(z3 ? 7 : 5).setViewConfigType(2).setNeedReConfigureData(z).setNeedBlurAnimation(z2));
    }

    private void updateHistogramStats(CaptureResult captureResult) {
        if (this.mModuleIndex == 180) {
            int[] histogramStats = CaptureResultParser.getHistogramStats(captureResult);
            if (histogramStats != null) {
                int[] iArr = new int[256];
                for (int i = 0; i < 256; i++) {
                    iArr[i] = histogramStats[i * 3];
                }
                ModeProtocol.MainContentProtocol mainContentProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
                if (mainContentProtocol != null) {
                    mainContentProtocol.updateHistogramStats(iArr);
                }
            }
        }
    }

    public void accept(@UpdateConstant.UpdateType int[] iArr) throws Exception {
        Log.e(TAG, "accept " + join(iArr) + ". " + this);
        if (this.mUpdateWorkThreadDisposable.isDisposed()) {
            Log.e(TAG, "mUpdateWorkThreadDisposable isDisposed. " + this + " " + this.mUpdateWorkThreadDisposable);
        } else if (isDeviceAlive()) {
            Log.e(TAG, "begin to consumePreference..");
            consumePreference(iArr);
            if (!isAlive() || this.mActivity.getCameraScreenNail().getSurfaceCreatedTimestamp() != this.mSurfaceCreatedTimestamp || isRepeatingRequestInProgress()) {
                Log.d(TAG, "skip resumePreview on accept. isAlive = " + isAlive() + " isRequestInProgress = " + isRepeatingRequestInProgress());
                return;
            }
            int resumePreview = this.mCamera2Device.resumePreview();
            if (shouldCheckSatFallbackState()) {
                int length = iArr.length;
                int i = 0;
                while (i < length) {
                    if (24 != iArr[i]) {
                        i++;
                    } else if (Math.abs(this.mLastZoomRatio - this.mCamera2Device.getZoomRatio()) > 0.001f && maySwitchCameraLens(this.mLastZoomRatio, this.mCamera2Device.getZoomRatio())) {
                        this.mLastZoomRatio = this.mCamera2Device.getZoomRatio();
                        this.mIsSatFallback = 1;
                        this.mFallbackProcessed = false;
                        this.mLastSatFallbackRequestId = resumePreview;
                        this.mHandler.removeMessages(60);
                        this.mHandler.sendEmptyMessageDelayed(60, 1500);
                        Log.d(TAG, "lastFallbackRequestId = " + this.mLastSatFallbackRequestId);
                        return;
                    } else {
                        return;
                    }
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean addZoom(float f2) {
        return onZoomingActionUpdate(HybridZoomingSystem.add(this.mZoomRatio, f2), 1);
    }

    /* access modifiers changed from: protected */
    public void applyZoomRatio() {
        if (this.mCamera2Device != null) {
            float deviceBasedZoomRatio = getDeviceBasedZoomRatio();
            Log.d(TAG, "applyZoomRatio(): apply zoom ratio to device = " + deviceBasedZoomRatio);
            this.mCamera2Device.setZoomRatio(deviceBasedZoomRatio);
        }
    }

    public boolean canIgnoreFocusChanged() {
        return this.mIgnoreFocusChanged;
    }

    /* access modifiers changed from: protected */
    public void changeConflictPreference() {
        if (CameraSettings.isStereoModeOn()) {
            List<String> supportedSettingKeys = getSupportedSettingKeys();
            if (supportedSettingKeys != null) {
                DataProvider.ProviderEditor editor = DataRepository.dataItemConfig().editor();
                for (String next : supportedSettingKeys) {
                    if (CameraSettings.isSwitchOn(next)) {
                        editor.remove(next);
                    }
                }
                editor.apply();
            }
        }
    }

    public void checkActivityOrientation() {
        if (isDeviceAlive() && this.mDisplayRotation != Util.getDisplayRotation(this.mActivity)) {
            checkDisplayOrientation();
        }
    }

    public void checkDisplayOrientation() {
        this.mDisplayRotation = Util.getDisplayRotation(this.mActivity);
        this.mCameraDisplayOrientation = Util.getDisplayOrientation(this.mDisplayRotation, this.mBogusCameraId);
        Log.v(TAG, "checkDisplayOrientation: " + this.mDisplayRotation + " | " + this.mCameraDisplayOrientation);
        if (this.mActivity.getCameraScreenNail() != null) {
            this.mActivity.getCameraScreenNail().setDisplayOrientation(this.mDisplayRotation);
        }
    }

    /* access modifiers changed from: protected */
    public void consumePreference(@UpdateConstant.UpdateType int... iArr) {
    }

    /* access modifiers changed from: protected */
    public boolean currentIsMainThread() {
        return this.mMainThreadId == Thread.currentThread().getId();
    }

    public void enableCameraControls(boolean z) {
        Log.d(TAG, "enableCameraControls: enable = " + z + ", caller: " + Util.getCallers(1));
        setIgnoreTouchEvent(z ^ true);
    }

    public void enterMutexMode(int i) {
    }

    public void exitMutexMode(int i) {
    }

    public void fillFeatureControl(StartControl startControl) {
    }

    public View findViewById(int i) {
        return this.mActivity.findViewById(i);
    }

    /* access modifiers changed from: protected */
    public void focusCenter() {
    }

    /* access modifiers changed from: protected */
    public Rect getActiveArraySize() {
        return this.mCameraCapabilities.getActiveArraySize();
    }

    public Camera getActivity() {
        return this.mActivity;
    }

    public int getActualCameraId() {
        return this.mActualCameraId;
    }

    public int getBogusCameraId() {
        return this.mBogusCameraId;
    }

    public CameraCapabilities getCameraCapabilities() {
        return this.mCameraCapabilities;
    }

    public Camera2Proxy getCameraDevice() {
        return this.mCamera2Device;
    }

    /* access modifiers changed from: protected */
    public int getCameraRotation() {
        return 0;
    }

    /* access modifiers changed from: protected */
    public int getCameraState() {
        return this.mCameraState;
    }

    /* access modifiers changed from: protected */
    public Rect getCropRegion() {
        return HybridZoomingSystem.toCropRegion(getDeviceBasedZoomRatio(), getActiveArraySize());
    }

    /* access modifiers changed from: protected */
    public String getDebugInfo() {
        return null;
    }

    public final float getDeviceBasedZoomRatio() {
        float f2;
        int i = this.mModuleIndex;
        if (i == 182) {
            return 2.0f;
        }
        float f3 = this.mZoomRatio;
        if (!HybridZoomingSystem.IS_3_OR_MORE_SAT) {
            return f3;
        }
        int i2 = this.mActualCameraId;
        if (i != 165) {
            if (i == 167) {
                return f3;
            }
            if (!(i == 169 || i == 183)) {
                if (i != 173) {
                    if (i != 174) {
                        switch (i) {
                            case 161:
                            case 162:
                            case 163:
                                break;
                            default:
                                return f3;
                        }
                    }
                } else if (!CameraSettings.isSuperNightUWOpen(i)) {
                    return f3;
                }
            }
        }
        if (i2 != Camera2DataContainer.getInstance().getUltraWideCameraId()) {
            return i2 == Camera2DataContainer.getInstance().getAuxCameraId() ? HybridZoomingSystem.clamp(this.mZoomRatio / 2.0f, 1.0f, this.mCameraCapabilities.getMaxZoomRatio()) : f3;
        }
        String rb = DataRepository.dataItemFeature().rb();
        if (!CameraSettings.isMacroModeEnabled(this.mModuleIndex) || HybridZoomingSystem.sDefaultMacroOpticalZoomRatio == 0.6f) {
            f2 = HybridZoomingSystem.clamp(this.mZoomRatio / 0.6f, 1.0f, this.mCameraCapabilities.getMaxZoomRatio());
        } else if (!CameraSettings.isMacroModeEnabled(this.mModuleIndex) || rb == null) {
            return f3;
        } else {
            f2 = HybridZoomingSystem.clamp(this.mZoomRatio * Float.valueOf(rb).floatValue(), getMinZoomRatio() * Float.valueOf(rb).floatValue(), getMaxZoomRatio() * Float.valueOf(rb).floatValue());
        }
        return f2;
    }

    public Handler getHandler() {
        return this.mHandler;
    }

    /* access modifiers changed from: protected */
    public CameraSize getJpegThumbnailSize() {
        return Util.getOptimalJpegThumbnailSize(this.mCameraCapabilities.getSupportedThumbnailSizes(), ((double) this.mPreviewSize.getWidth()) / ((double) this.mPreviewSize.getHeight()));
    }

    public float getMaxZoomRatio() {
        return this.mMaxZoomRatio;
    }

    public float getMinZoomRatio() {
        return this.mMinZoomRatio;
    }

    public int getModuleIndex() {
        return this.mModuleIndex;
    }

    public MutexModeManager getMutexModePicker() {
        return this.mMutexModePicker;
    }

    /* access modifiers changed from: protected */
    public abstract int getOperatingMode();

    public CameraSize getPreviewSize() {
        return this.mPreviewSize;
    }

    public Resources getResources() {
        return this.mActivity.getResources();
    }

    /* access modifiers changed from: protected */
    public int getScreenDelay() {
        Camera camera = this.mActivity;
        return (camera == null || camera.startFromKeyguard()) ? 30000 : 60000;
    }

    public String getString(int i) {
        return CameraAppImpl.getAndroidContext().getString(i);
    }

    public List<String> getSupportedSettingKeys() {
        return new ArrayList();
    }

    /* access modifiers changed from: protected */
    public int getTriggerMode() {
        return this.mTriggerMode;
    }

    public String getUnInterruptableReason() {
        return this.mUnInterruptableReason;
    }

    public Window getWindow() {
        return this.mActivity.getWindow();
    }

    public float getZoomRatio() {
        return this.mZoomRatio;
    }

    /* access modifiers changed from: protected */
    public void handlePendingScreenSlide() {
        if (this.mPendingScreenSlideKeyCode > 0 && this.mActivity != null) {
            Log.d(TAG, "process pending screen slide: " + this.mPendingScreenSlideKeyCode);
            this.mActivity.handleScreenSlideKeyEvent(this.mPendingScreenSlideKeyCode, (KeyEvent) null);
            this.mPendingScreenSlideKeyCode = 0;
        }
    }

    /* access modifiers changed from: protected */
    public boolean handleVolumeKeyEvent(boolean z, boolean z2, int i, boolean z3) {
        String str;
        if (!isAlive()) {
            return true;
        }
        if (z3) {
            str = getString(R.string.pref_camera_volumekey_function_entryvalue_shutter);
        } else {
            int i2 = this.mModuleIndex;
            str = (i2 == 174 || i2 == 183 || i2 == 179) ? DataRepository.dataItemGlobal().getString("pref_live_volumekey_function_key", getString(R.string.pref_live_volumekey_function_default)) : ModuleManager.isVideoCategory(getModuleIndex()) ? getModuleIndex() == 180 ? DataRepository.dataItemGlobal().getString(CameraSettings.KEY_VOLUME_PRO_VIDEO_FUNCTION, getString(R.string.pref_pro_video_volumekey_function_default)) : DataRepository.dataItemGlobal().getString("pref_video_volumekey_function_key", getString(R.string.pref_video_volumekey_function_default)) : DataRepository.dataItemGlobal().getString("pref_camera_volumekey_function_key", getString(R.string.pref_camera_volumekey_function_default));
        }
        if (str.equals(getString(R.string.pref_camera_volumekey_function_entryvalue_shutter)) || str.equals(getString(R.string.pref_camera_volumekey_function_entryvalue_timer))) {
            performKeyClicked(20, str, i, z2);
            return true;
        } else if (ModuleManager.isPanoramaModule() || ModuleManager.isWideSelfieModule() || !str.equals(getString(R.string.pref_camera_volumekey_function_entryvalue_zoom)) || !isZoomSupported()) {
            return false;
        } else {
            if (z2) {
                onZoomingActionStart(1);
                if (!isZoomEnabled()) {
                    return true;
                }
                boolean addZoom = z ? addZoom(0.1f) : addZoom(-0.1f);
                if (i == 0 && addZoom) {
                    CameraStatUtils.trackZoomAdjusted("volume", false);
                }
            } else {
                onZoomingActionEnd(1);
            }
            return true;
        }
    }

    /* access modifiers changed from: protected */
    public boolean hasCameraException() {
        return this.mCameraDisabled || this.mOpenCameraFail || this.mCameraHardwareError || !this.mActivity.couldShowErrorDialog();
    }

    /* access modifiers changed from: protected */
    public void hideTipMessage(@StringRes int i) {
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (bottomPopupTips == null) {
            return;
        }
        if (i <= 0 || bottomPopupTips.containTips(i)) {
            bottomPopupTips.directlyHideTips();
        }
    }

    public void initByCapability(CameraCapabilities cameraCapabilities) {
        if (cameraCapabilities == null) {
            Log.w(TAG, "init by capability(capability == null)");
            return;
        }
        this.mAeLockSupported = cameraCapabilities.isAELockSupported();
        this.mAwbLockSupported = cameraCapabilities.isAWBLockSupported();
        this.mFocusAreaSupported = cameraCapabilities.isAFRegionSupported();
        this.mMeteringAreaSupported = cameraCapabilities.isAERegionSupported();
        a dataItemFeature = DataRepository.dataItemFeature();
        boolean z = true;
        this.mAELockOnlySupported = dataItemFeature.Hb() && !this.mFocusAreaSupported && this.mMeteringAreaSupported && this.mAeLockSupported;
        this.mFocusOrAELockSupported = this.mFocusAreaSupported || this.mAELockOnlySupported;
        boolean Ic = dataItemFeature.Ic();
        if (!CameraSettings.isAEAFLockSupport() || (!Ic && !isBackCamera() && !this.mAELockOnlySupported)) {
            z = false;
        }
        this.m3ALockSupported = z;
    }

    /* access modifiers changed from: protected */
    public void initializeCapabilities() {
        this.mCameraCapabilities.setOperatingMode(getOperatingMode());
        initByCapability(this.mCameraCapabilities);
        this.mMaxFaceCount = this.mCameraCapabilities.getMaxFaceCount();
        initializeZoomRangeFromCapabilities();
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:102:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x003f, code lost:
        setMinZoomRatio(1.0f);
        setMaxZoomRatio(r6.mCameraCapabilities.getMaxZoomRatio());
     */
    public void initializeZoomRangeFromCapabilities() {
        if (!this.mZoomSupported) {
            return;
        }
        if (isBackCamera()) {
            int i = this.mModuleIndex;
            if (i != 165) {
                if (i != 167) {
                    if (i != 169) {
                        if (i != 180) {
                            if (i != 183) {
                                switch (i) {
                                    case 161:
                                        break;
                                    case 162:
                                        if (!DataRepository.dataItemGlobal().isNormalIntent() && this.mCameraCapabilities.isSupportLightTripartite()) {
                                            setMinZoomRatio(1.0f);
                                            setMaxZoomRatio(Math.min(6.0f, this.mCameraCapabilities.getMaxZoomRatio()));
                                            return;
                                        } else if (!HybridZoomingSystem.IS_3_OR_MORE_SAT) {
                                            setMinZoomRatio(1.0f);
                                            if (CameraSettings.isUltraWideConfigOpen(this.mModuleIndex) || CameraSettings.isMacroModeEnabled(this.mModuleIndex)) {
                                                setMaxZoomRatio(2.0f);
                                                return;
                                            } else {
                                                setMaxZoomRatio(this.mCameraCapabilities.getMaxZoomRatio());
                                                return;
                                            }
                                        } else if (CameraSettings.isMacroModeEnabled(this.mModuleIndex)) {
                                            setMinZoomRatio(HybridZoomingSystem.getMinimumMacroOpticalZoomRatio());
                                            setMaxZoomRatio(HybridZoomingSystem.getMaximumMacroOpticalZoomRatio());
                                            return;
                                        } else if (DataRepository.dataItemFeature().De()) {
                                            setMinZoomRatio(HybridZoomingSystem.getMinimumOpticalZoomRatio(this.mModuleIndex));
                                            setMaxZoomRatio(this.mCameraCapabilities.getMaxZoomRatio());
                                            return;
                                        } else {
                                            setMinZoomRatio(HybridZoomingSystem.getMinimumOpticalZoomRatio(this.mModuleIndex));
                                            setMaxZoomRatio(Math.min(6.0f, this.mCameraCapabilities.getMaxZoomRatio()));
                                            return;
                                        }
                                    case 163:
                                        break;
                                    default:
                                        switch (i) {
                                            case 172:
                                                if (isUltraWideBackCamera()) {
                                                    setMinZoomRatio(1.0f);
                                                    setMaxZoomRatio(2.0f);
                                                    return;
                                                }
                                                setMinZoomRatio(1.0f);
                                                setMaxZoomRatio(this.mCameraCapabilities.getMaxZoomRatio());
                                                return;
                                            case 173:
                                                if (!CameraSettings.isSuperNightUWOpen(i)) {
                                                    setMinZoomRatio(1.0f);
                                                    setMaxZoomRatio(this.mCameraCapabilities.getMaxZoomRatio());
                                                    return;
                                                }
                                                break;
                                            case 174:
                                                break;
                                            case 175:
                                                if (DataRepository.dataItemFeature().cd()) {
                                                    setMinZoomRatio(1.0f);
                                                    setMaxZoomRatio(2.0f);
                                                    return;
                                                }
                                                break;
                                        }
                                }
                            }
                            if (!HybridZoomingSystem.IS_3_OR_MORE_SAT) {
                                setMinZoomRatio(1.0f);
                                if (CameraSettings.isUltraWideConfigOpen(this.mModuleIndex) || CameraSettings.isMacroModeEnabled(this.mModuleIndex)) {
                                    setMaxZoomRatio(2.0f);
                                    return;
                                } else {
                                    setMaxZoomRatio(this.mCameraCapabilities.getMaxZoomRatio());
                                    return;
                                }
                            } else if (CameraSettings.isMacroModeEnabled(this.mModuleIndex)) {
                                setMinZoomRatio(HybridZoomingSystem.getMinimumMacroOpticalZoomRatio());
                                setMaxZoomRatio(HybridZoomingSystem.getMaximumMacroOpticalZoomRatio());
                                return;
                            } else {
                                setMinZoomRatio(HybridZoomingSystem.getMinimumOpticalZoomRatio(this.mModuleIndex));
                                setMaxZoomRatio(Math.min(6.0f, this.mCameraCapabilities.getMaxZoomRatio()));
                                return;
                            }
                        }
                    } else if (HybridZoomingSystem.IS_3_OR_MORE_SAT) {
                        setMinZoomRatio(HybridZoomingSystem.getMinimumOpticalZoomRatio(i));
                        setMaxZoomRatio(Math.min(6.0f, this.mCameraCapabilities.getMaxZoomRatio()));
                        return;
                    } else {
                        setMinZoomRatio(1.0f);
                        if (CameraSettings.isUltraWideConfigOpen(this.mModuleIndex)) {
                            setMaxZoomRatio(2.0f);
                            return;
                        } else {
                            setMaxZoomRatio(this.mCameraCapabilities.getMaxZoomRatio());
                            return;
                        }
                    }
                }
                setMinZoomRatio(1.0f);
                if (DataRepository.dataItemFeature().cd() && DataRepository.dataItemRunning().getComponentUltraPixel().isRearSwitchOn()) {
                    setMaxZoomRatio(2.0f);
                    return;
                } else if (CameraSettings.isUltraWideConfigOpen(this.mModuleIndex) || CameraSettings.isMacroModeEnabled(this.mModuleIndex) || "macro".equals(DataRepository.dataItemConfig().getManuallyDualLens().getComponentValue(this.mModuleIndex))) {
                    setMaxZoomRatio(2.0f);
                    return;
                } else if (this.mModuleIndex == 180) {
                    setMaxZoomRatio(Math.min(6.0f, this.mCameraCapabilities.getMaxZoomRatio()));
                    return;
                } else {
                    setMaxZoomRatio(this.mCameraCapabilities.getMaxZoomRatio());
                    return;
                }
            }
            if (!DataRepository.dataItemGlobal().isNormalIntent() && this.mCameraCapabilities.isSupportLightTripartite()) {
                setMinZoomRatio(1.0f);
                setMaxZoomRatio(this.mCameraCapabilities.getMaxZoomRatio());
            } else if (!HybridZoomingSystem.IS_3_OR_MORE_SAT) {
                setMinZoomRatio(1.0f);
                if (CameraSettings.isUltraWideConfigOpen(this.mModuleIndex) || CameraSettings.isMacroModeEnabled(this.mModuleIndex)) {
                    setMaxZoomRatio(2.0f);
                } else {
                    setMaxZoomRatio(this.mCameraCapabilities.getMaxZoomRatio());
                }
            } else if (CameraSettings.isMacroModeEnabled(this.mModuleIndex)) {
                setMinZoomRatio(HybridZoomingSystem.getMinimumMacroOpticalZoomRatio());
                setMaxZoomRatio(HybridZoomingSystem.getMaximumMacroOpticalZoomRatio());
            } else {
                setMinZoomRatio(HybridZoomingSystem.getMinimumOpticalZoomRatio(this.mModuleIndex));
                setMaxZoomRatio(this.mCameraCapabilities.getMaxZoomRatio());
            }
        } else {
            setMinZoomRatio(1.0f);
            setMaxZoomRatio(this.mCameraCapabilities.getMaxZoomRatio());
        }
    }

    /* access modifiers changed from: protected */
    public void initializeZoomRatio() {
        if (!HybridZoomingSystem.IS_3_OR_MORE_SAT || !isBackCamera()) {
            Log.d(TAG, "resetZoomRatio(): set zoom ratio to 1.0");
            setZoomRatio(1.0f);
            return;
        }
        int i = this.mModuleIndex;
        if (!(i == 165 || i == 167)) {
            if (i != 169) {
                if (!(i == 177 || i == 180)) {
                    if (i != 183) {
                        if (i != 173) {
                            if (i != 174) {
                                switch (i) {
                                    case 161:
                                    case 162:
                                        break;
                                    case 163:
                                        break;
                                    default:
                                        Log.d(TAG, "resetZoomRatio(): set zoom ratio to 1.0");
                                        setZoomRatio(1.0f);
                                        return;
                                }
                            }
                        } else if (!CameraSettings.isSuperNightUWOpen(i)) {
                            String zoomRatioHistory = HybridZoomingSystem.getZoomRatioHistory(this.mModuleIndex, Float.toString(1.0f));
                            Log.d(TAG, "resetZoomRatio(): set zoom ratio to " + zoomRatioHistory);
                            setZoomRatio(HybridZoomingSystem.toFloat(zoomRatioHistory, 1.0f));
                            return;
                        }
                    }
                }
            }
            if (!CameraSettings.isUltraWideConfigOpen(this.mModuleIndex)) {
                if (CameraSettings.isAutoZoomEnabled(this.mModuleIndex)) {
                    Log.d(TAG, "resetZoomRatio(): set zoom ratio to 0.6");
                    setZoomRatio(0.6f);
                    return;
                } else if (CameraSettings.isSuperEISEnabled(this.mModuleIndex) && isUltraWideBackCamera()) {
                    Log.d(TAG, "resetZoomRatio(): set zoom ratio to 0.6");
                    setZoomRatio(0.6f);
                    return;
                } else if (CameraSettings.isMacroModeEnabled(this.mModuleIndex)) {
                    Log.d(TAG, "resetZoomRatio(): set zoom ratio to " + HybridZoomingSystem.sDefaultMacroOpticalZoomRatio);
                    setZoomRatio(HybridZoomingSystem.sDefaultMacroOpticalZoomRatio);
                    return;
                } else {
                    String zoomRatioHistory2 = HybridZoomingSystem.getZoomRatioHistory(this.mModuleIndex, Float.toString(1.0f));
                    Log.d(TAG, "resetZoomRatio(): set zoom ratio to " + zoomRatioHistory2);
                    setZoomRatio(HybridZoomingSystem.toFloat(zoomRatioHistory2, 1.0f));
                    return;
                }
            } else {
                return;
            }
        }
        if (CameraSettings.isMacroModeEnabled(this.mModuleIndex)) {
            String zoomRatioHistory3 = HybridZoomingSystem.getZoomRatioHistory(this.mModuleIndex, Float.toString(HybridZoomingSystem.sDefaultMacroOpticalZoomRatio));
            Log.d(TAG, "resetZoomRatio(): set zoom ratio to " + zoomRatioHistory3);
            setZoomRatio(HybridZoomingSystem.toFloat(zoomRatioHistory3, HybridZoomingSystem.sDefaultMacroOpticalZoomRatio));
        } else if (!CameraSettings.isUltraPixelRearOn()) {
            String zoomRatioHistory4 = HybridZoomingSystem.getZoomRatioHistory(this.mModuleIndex, Float.toString(1.0f));
            Log.d(TAG, "resetZoomRatio(): set zoom ratio to " + zoomRatioHistory4);
            setZoomRatio(HybridZoomingSystem.toFloat(zoomRatioHistory4, 1.0f));
        } else {
            Log.d(TAG, "resetZoomRatio(): set zoom ratio to 1.0");
            setZoomRatio(1.0f);
        }
    }

    public boolean isAlive() {
        return isCreated() && !isDeparted();
    }

    /* access modifiers changed from: protected */
    public final boolean isAuxCamera() {
        return this.mActualCameraId == Camera2DataContainer.getInstance().getAuxCameraId();
    }

    /* access modifiers changed from: protected */
    public final boolean isBackCamera() {
        return this.mBogusCameraId == 0;
    }

    public boolean isBlockSnap() {
        return isDoingAction();
    }

    /* access modifiers changed from: protected */
    public final boolean isBokehFrontCamera() {
        return this.mActualCameraId == Camera2DataContainer.getInstance().getBokehFrontCameraId();
    }

    /* access modifiers changed from: protected */
    public final boolean isBokehUltraWideBackCamera() {
        return this.mActualCameraId == Camera2DataContainer.getInstance().getUltraWideBokehCameraId();
    }

    /* access modifiers changed from: protected */
    public boolean isCameraSwitchingDuringZoomingAllowed() {
        return CameraSettings.isSuperNightUWOpen(this.mModuleIndex) && HybridZoomingSystem.IS_3_OR_MORE_SAT && !CameraSettings.isMacroModeEnabled(this.mModuleIndex) && isBackCamera();
    }

    public boolean isCaptureIntent() {
        return false;
    }

    public boolean isCreated() {
        return this.mIsCreated.get();
    }

    public boolean isDeparted() {
        return this.mIsDeparted.get();
    }

    public boolean isDeviceAlive() {
        boolean z = this.mCamera2Device != null && isAlive();
        if (!z) {
            Object[] objArr = new Object[3];
            objArr[0] = this.mCamera2Device != null ? "valid" : "invalid";
            objArr[1] = isCreated() ? "created" : "destroyed";
            objArr[2] = isDeparted() ? "departed" : "alive";
            String format = String.format("device: %s module: %s|%s", objArr);
            if (DEBUG) {
                new RuntimeException(format).printStackTrace();
            } else {
                Log.e(TAG, Util.getCallers(1) + "|" + format);
            }
        }
        return z;
    }

    /* access modifiers changed from: protected */
    public final boolean isDualCamera() {
        return this.mActualCameraId == Camera2DataContainer.getInstance().getSATCameraId() || this.mActualCameraId == Camera2DataContainer.getInstance().getBokehCameraId();
    }

    /* access modifiers changed from: protected */
    public final boolean isDualFrontCamera() {
        return this.mActualCameraId == Camera2DataContainer.getInstance().getSATFrontCameraId() || this.mActualCameraId == Camera2DataContainer.getInstance().getBokehFrontCameraId();
    }

    public boolean isFrameAvailable() {
        return this.mIsFrameAvailable.get();
    }

    public final boolean isFrontCamera() {
        return this.mBogusCameraId == 1;
    }

    public boolean isIgnoreTouchEvent() {
        return this.mIgnoreTouchEvent;
    }

    public boolean isInDisplayRect(int i, int i2) {
        Rect rect = this.mDisplayRect;
        if (rect == null) {
            return false;
        }
        return rect.contains(i, i2);
    }

    public boolean isInTapableRect(int i, int i2) {
        if (this.mDisplayRect == null) {
            return false;
        }
        return Util.getTapableRectWithEdgeSlop(judgeTapableRectByUiStyle(), this.mDisplayRect, this.mModuleIndex, this.mActivity).contains(i, i2);
    }

    public boolean isKeptBitmapTexture() {
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean isMainBackCamera() {
        return this.mActualCameraId == Camera2DataContainer.getInstance().getMainBackCameraId();
    }

    public boolean isMeteringAreaOnly() {
        return false;
    }

    public boolean isMimojiMode() {
        return this.mModuleIndex == 177;
    }

    public boolean isNeedHapticFeedback() {
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean isNeedMute() {
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean isParallelSessionEnable() {
        return false;
    }

    /* access modifiers changed from: protected */
    public final boolean isPictureUseDualFrontCamera() {
        return this.mActualCameraId == Camera2DataContainer.getInstance().getBokehFrontCameraId() && !DataRepository.dataItemFeature().Mb();
    }

    public boolean isPortraitMode() {
        return this.mModuleIndex == 171;
    }

    public boolean isPostProcessing() {
        return false;
    }

    public boolean isRecording() {
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean isRepeatingRequestInProgress() {
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean isRestoring() {
        return this.mRestoring;
    }

    public boolean isSelectingCapturedResult() {
        return false;
    }

    public boolean isShot2GalleryOrEnableParallel() {
        return false;
    }

    public boolean isShowAeAfLockIndicator() {
        return false;
    }

    public boolean isShowCaptureButton() {
        return false;
    }

    /* access modifiers changed from: protected */
    public final boolean isSingleCamera() {
        return this.mActualCameraId == Camera2DataContainer.getInstance().getMainBackCameraId() || this.mActualCameraId == Camera2DataContainer.getInstance().getFrontCameraId();
    }

    /* access modifiers changed from: protected */
    public boolean isSquareModeChange() {
        return ModuleManager.isSquareModule() != (this.mActivity.getCameraScreenNail().getRenderTargetRatio() == 2);
    }

    /* access modifiers changed from: protected */
    public final boolean isStandaloneMacroCamera() {
        return this.mActualCameraId == Camera2DataContainer.getInstance().getStandaloneMacroCameraId();
    }

    public boolean isSupportAELockOnly() {
        return this.mAELockOnlySupported;
    }

    public boolean isSupportFocusShoot() {
        return false;
    }

    /* access modifiers changed from: protected */
    public final boolean isTextureExpired() {
        return this.mActivity.getCameraScreenNail().getSurfaceCreatedTimestamp() != this.mSurfaceCreatedTimestamp;
    }

    public boolean isThermalThreshold() {
        return false;
    }

    /* access modifiers changed from: protected */
    public final boolean isUltraTeleCamera() {
        return this.mActualCameraId == Camera2DataContainer.getInstance().getUltraTeleCameraId();
    }

    /* access modifiers changed from: protected */
    public final boolean isUltraWideBackCamera() {
        return this.mActualCameraId == Camera2DataContainer.getInstance().getUltraWideCameraId();
    }

    /* access modifiers changed from: protected */
    public boolean isZoomEnabled() {
        return true;
    }

    public boolean isZoomRatioBetweenUltraAndWide() {
        float f2 = this.mZoomRatio;
        return f2 >= 0.6f && f2 < 1.0f;
    }

    public boolean isZoomRatioBetweenUltraAndWide(float f2) {
        return f2 >= 0.6f && f2 < 1.0f;
    }

    /* access modifiers changed from: protected */
    public boolean isZoomSupported() {
        return this.mZoomSupported;
    }

    /* access modifiers changed from: protected */
    public boolean isZslPreferred() {
        return false;
    }

    public String join(int[] iArr) {
        StringBuilder sb = new StringBuilder();
        for (int append : iArr) {
            sb.append(append);
            sb.append(",");
        }
        return sb.toString();
    }

    /* access modifiers changed from: protected */
    public boolean judgeTapableRectByUiStyle() {
        return !isRecording();
    }

    /* access modifiers changed from: protected */
    public void mapTapCoordinate(Object obj) {
        if (obj instanceof Point) {
            Point point = (Point) obj;
            int i = point.x;
            Rect rect = this.mDisplayRect;
            point.x = i - rect.left;
            point.y -= rect.top;
        } else if (obj instanceof RectF) {
            RectF rectF = (RectF) obj;
            float f2 = rectF.left;
            Rect rect2 = this.mDisplayRect;
            int i2 = rect2.left;
            rectF.left = f2 - ((float) i2);
            rectF.right -= (float) i2;
            float f3 = rectF.top;
            int i3 = rect2.top;
            rectF.top = f3 - ((float) i3);
            rectF.bottom -= (float) i3;
        }
    }

    /* access modifiers changed from: protected */
    public boolean maySwitchCameraLens(float f2, float f3) {
        if (f2 < f3) {
            if (f2 < 1.0f && f3 >= 1.0f) {
                return true;
            }
            if (f2 >= 2.0f || f3 < 2.0f) {
                return HybridZoomingSystem.IS_4_OR_MORE_SAT && f2 < 3.7f && f3 >= 3.7f;
            }
            return true;
        } else if (f2 <= f3) {
            return false;
        } else {
            if (HybridZoomingSystem.IS_4_OR_MORE_SAT && f2 >= 3.7f && f3 < 3.7f) {
                return true;
            }
            if (f2 < 2.0f || f3 >= 2.0f) {
                return f2 >= 1.0f && f3 < 1.0f;
            }
            return true;
        }
    }

    public void notifyDualZoom(boolean z) {
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        if (camera2Proxy != null && HybridZoomingSystem.IS_2_SAT) {
            camera2Proxy.setOpticalZoomToTele(z);
            this.mCamera2Device.resumePreview();
        }
    }

    public void notifyError() {
        this.mCameraHardwareError = true;
        setCameraState(0);
        if (!this.mActivity.isActivityPaused()) {
            onCameraException();
            return;
        }
        this.mActivity.releaseAll(true, true);
        this.mActivity.finish();
    }

    public void notifyZooming(boolean z) {
        this.isZooming = z;
    }

    public void onActivityResult(int i, int i2, Intent intent) {
    }

    public boolean onBackPressed() {
        return false;
    }

    @CallSuper
    public void onBroadcastReceived(Context context, Intent intent) {
        Camera camera = this.mActivity;
        if (camera != null && !camera.isFinishing() && intent != null) {
            String action = intent.getAction();
            Log.v(TAG, "onReceive: action=" + action);
            if ("android.intent.action.MEDIA_MOUNTED".equals(action)) {
                Log.d(TAG, "SD card available");
                Storage.initStorage(context);
                this.mActivity.getScreenHint().updateHint();
            } else if ("android.intent.action.MEDIA_UNMOUNTED".equals(action)) {
                Log.d(TAG, "SD card unavailable");
                FileCompat.updateSDPath();
                this.mActivity.getScreenHint().updateHint();
                this.mActivity.getThumbnailUpdater().getLastThumbnail();
            } else if ("android.intent.action.MEDIA_SCANNER_STARTED".equals(action)) {
                Log.d(TAG, "media scanner started");
            } else if ("android.intent.action.MEDIA_SCANNER_FINISHED".equals(action)) {
                this.mActivity.getThumbnailUpdater().getLastThumbnail();
                Log.d(TAG, "media scanner finisheded");
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onCameraException() {
        Log.e(TAG, "onCameraException: " + this.mModuleIndex + " | " + this.mBogusCameraId);
        if (currentIsMainThread()) {
            if ((this.mOpenCameraFail || this.mCameraHardwareError) && !this.mActivity.isActivityPaused() && this.mActivity.couldShowErrorDialog()) {
                Util.showErrorAndFinish(this.mActivity, CameraSettings.updateOpenCameraFailTimes() > 1 ? R.string.cannot_connect_camera_twice : R.string.cannot_connect_camera_once, false);
                this.mActivity.showErrorDialog();
            }
            if (this.mCameraDisabled && this.mActivity.couldShowErrorDialog()) {
                Util.showErrorAndFinish(this.mActivity, R.string.camera_disabled, false);
                this.mActivity.showErrorDialog();
            }
        } else {
            sendOpenFailMessage();
        }
        enableCameraControls(false);
    }

    /* access modifiers changed from: protected */
    public void onCameraOpened() {
        initializeCapabilities();
        initializeZoomRatio();
        this.mCamera2Device.setMetaDataCallback(this);
        if (isFrontCamera() && this.mActivity.isScreenSlideOff()) {
            this.mCamera2Device.setAELock(true);
        }
        updateLensDirtyDetect(false);
        this.mMainProtocol.updateZoomRatio(this.mMinZoomRatio, this.mMaxZoomRatio);
    }

    /* access modifiers changed from: protected */
    public void onCapabilityChanged(CameraCapabilities cameraCapabilities) {
        initByCapability(cameraCapabilities);
        Log.d(TAG, "onCapabilityChanged: mFocusAreaSupported = " + this.mFocusAreaSupported + ", mAELockOnlySupported = " + this.mAELockOnlySupported);
    }

    public void onCreate(int i, int i2) {
        Log.d(TAG, "onCreate moduleIndex->" + i + ", cameraId->" + i2 + " " + this);
        this.mModuleIndex = i;
        this.mBogusCameraId = i2;
        this.mErrorCallback = new CameraErrorCallbackImpl(this.mActivity);
        this.mMainProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
        this.mMutexModePicker = new MutexModeManager(this);
        this.mUpdateWorkThreadDisposable = Observable.create(new ObservableOnSubscribe<int[]>() {
            public void subscribe(ObservableEmitter<int[]> observableEmitter) throws Exception {
                ObservableEmitter unused = BaseModule.this.mUpdateWorkThreadEmitter = observableEmitter.serialize();
            }
        }).observeOn(GlobalConstant.sCameraSetupScheduler).subscribe(this);
        Log.d(TAG, "create disposable " + this + " " + this.mUpdateWorkThreadDisposable);
        if (getModuleIndex() == 163 && DataRepository.dataItemFeature().Pc() && CameraSettings.isLensDirtyDetectEnabled() && DataRepository.dataItemGlobal().getBoolean("pref_lens_dirty_tip", getResources().getBoolean(R.bool.pref_lens_dirty_tip_default))) {
            this.mLensDirtyDetectDisposable = Completable.complete().delay(15000, TimeUnit.MILLISECONDS, GlobalConstant.sCameraSetupScheduler).doOnComplete(new ActionUpdateLensDirtyDetect(this, true)).subscribe();
        }
        setCreated(true);
        this.mIsDeparted.set(false);
        this.isShowPreviewDebugInfo = Util.isShowPreviewDebugInfo();
    }

    @CallSuper
    public void onDestroy() {
        Log.d(TAG, "onDestroy: E");
        Camera camera = this.mActivity;
        if (camera != null) {
            camera.getSensorStateManager().setSensorStateListener((SensorStateManager.SensorStateListener) null);
        }
        setCreated(false);
        ImageSaver imageSaver = this.mActivity.getImageSaver();
        if (imageSaver != null) {
            imageSaver.onModuleDestroy();
        }
        if (isParallelSessionEnable()) {
            LocalParallelService.LocalBinder localBinder = AlgoConnector.getInstance().getLocalBinder();
            if (localBinder != null) {
                localBinder.stopPostProcessor(this.mActivity.hashCode());
            }
        }
        Log.d(TAG, "onDestroy: X");
    }

    public void onEvChanged(int i, int i2) {
        if (isAlive()) {
            this.mEvValue = i;
            this.mEvState = i2;
            if (i2 == 1 || i2 == 3) {
                this.mEvValueForTrack = i;
                CameraSettings.writeExposure(i);
                updateStatusBar(1);
                ViberatorContext.getInstance(getActivity().getApplicationContext()).performEVChange();
            }
            updatePreferenceInWorkThread(12);
        }
    }

    public void onFocusAreaChanged(int i, int i2) {
    }

    public boolean onGestureTrack(RectF rectF, boolean z) {
        return true;
    }

    public void onGradienterSwitched(boolean z) {
        this.mActivity.getSensorStateManager().setGradienterEnabled(z);
        this.mActivity.getSensorStateManager().register();
        updatePreferenceTrampoline(2, 5);
    }

    public void onHostStopAndNotifyActionStop() {
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0022  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0041  */
    public boolean onInterceptZoomingEvent(float f2, float f3, int i) {
        boolean z;
        int i2;
        if (isCameraSwitchingDuringZoomingAllowed()) {
            if (!CameraSettings.isSupportedOpticalZoom()) {
                int i3 = this.mModuleIndex;
                if (i3 == 163 || i3 == 165) {
                    z = false;
                    i2 = (f3 > 1.0f ? 1 : (f3 == 1.0f ? 0 : -1));
                    if (i2 != 0) {
                        CameraSettings.setVideoQuality8K(this.mModuleIndex, false);
                    }
                    if (!HybridZoomingSystem.IS_4_SAT) {
                        int i4 = (f2 > 1.0f ? 1 : (f2 == 1.0f ? 0 : -1));
                        if (i4 < 0 || f2 >= 2.0f || f3 >= 1.0f) {
                            int i5 = (f2 > 1.0f ? 1 : (f2 == 1.0f ? 0 : -1));
                            if (i5 < 0 && i2 >= 0 && f3 < 2.0f) {
                                switchCameraLens(z, false, false);
                                return true;
                            } else if (i4 < 0 || f2 >= 2.0f || f3 < 2.0f) {
                                int i6 = (f2 > 2.0f ? 1 : (f2 == 2.0f ? 0 : -1));
                                if (i6 < 0 || i2 < 0 || f3 >= 2.0f) {
                                    if (i5 < 0 && f3 >= 2.0f) {
                                        switchCameraLens(z, false, false);
                                        return true;
                                    } else if (i6 >= 0 && f3 < 1.0f) {
                                        switchCameraLens(z, false, false);
                                        return true;
                                    }
                                } else if (CameraSettings.isSuperNightUWOpen(this.mModuleIndex)) {
                                    return false;
                                } else {
                                    switchCameraLens(z, false, false);
                                    return true;
                                }
                            } else if (CameraSettings.isSuperNightUWOpen(this.mModuleIndex)) {
                                return false;
                            } else {
                                switchCameraLens(z, false, false);
                                return true;
                            }
                        } else {
                            switchCameraLens(z, false, false);
                            return true;
                        }
                    } else if (f2 >= 1.0f && f3 < 1.0f) {
                        switchCameraLens(z, false, false);
                        return true;
                    } else if (f2 < 1.0f && i2 >= 0) {
                        switchCameraLens(z, false, false);
                        return true;
                    }
                }
            }
            z = true;
            i2 = (f3 > 1.0f ? 1 : (f3 == 1.0f ? 0 : -1));
            if (i2 != 0) {
            }
            if (!HybridZoomingSystem.IS_4_SAT) {
            }
        }
        return false;
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 701 && i != 700) {
            return false;
        }
        if (!isUnInterruptable()) {
            this.mPendingScreenSlideKeyCode = 0;
            this.mActivity.handleScreenSlideKeyEvent(i, keyEvent);
            return true;
        } else if (i != 701 || !this.mActivity.getCameraIntentManager().isFromScreenSlide().booleanValue() || this.mActivity.isModeSwitched()) {
            this.mPendingScreenSlideKeyCode = i;
            Log.d(TAG, "pending screen slide: " + i + ", reason: " + getUnInterruptableReason());
            return false;
        } else {
            this.mActivity.moveTaskToBack(false);
            this.mActivity.overridePendingTransition(R.anim.anim_screen_slide_fade_in, R.anim.anim_screen_slide_fade_out);
            return true;
        }
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (i == 82 && !this.mActivity.startFromSecureKeyguard()) {
            openSettingActivity();
        }
        if (i == 24 || i == 25 || i == 87 || i == 88) {
            if (handleVolumeKeyEvent(i == 24 || i == 88, false, keyEvent.getRepeatCount(), keyEvent.getDevice().isExternal())) {
                return true;
            }
        }
        return false;
    }

    public void onLongPress(float f2, float f3) {
    }

    public void onMeteringAreaChanged(int i, int i2) {
    }

    public void onNewIntent() {
    }

    public void onNewUriArrived(Uri uri, String str) {
    }

    public void onOrientationChanged(int i, int i2, int i3) {
        if (i != -1) {
            this.mOrientation = i;
            EffectController.getInstance().setOrientation(Util.getShootOrientation(this.mActivity, this.mOrientation));
            checkActivityOrientation();
            if (this.mOrientationCompensation != i2) {
                this.mOrientationCompensation = i2;
            }
        }
    }

    @CallSuper
    public void onPause() {
        Log.d(TAG, "onPause");
        this.mPaused = true;
        this.mPendingScreenSlideKeyCode = 0;
        this.mUpdateWorkThreadDisposable.dispose();
        Disposable disposable = this.mLensDirtyDetectDisposable;
        if (disposable != null) {
            disposable.dispose();
        }
        Disposable disposable2 = this.mLensDirtyDetectHintDisposable;
        if (disposable2 != null) {
            disposable2.dispose();
        }
        Camera camera = this.mActivity;
        if (camera != null) {
            camera.hideLensDirtyDetectedHint();
        }
    }

    public void onPreviewLayoutChanged(Rect rect) {
    }

    @CallSuper
    public void onPreviewMetaDataUpdate(CaptureResult captureResult) {
        detectLensDirty(captureResult);
        if (this.isShowPreviewDebugInfo) {
            showDebug(captureResult, !isDoingAction() && this.mModuleIndex != 166);
        }
        updateHistogramStats(captureResult);
    }

    public void onPreviewPixelsRead(byte[] bArr, int i, int i2) {
    }

    public void onPreviewSizeChanged(int i, int i2) {
    }

    @CallSuper
    public void onResume() {
        Log.d(TAG, "onResume");
        this.mPaused = false;
    }

    public void onSaveInstanceState(Bundle bundle) {
        bundle.putInt(Util.KEY_KILLED_MODULE_INDEX, ModuleManager.getActiveModuleIndex());
    }

    public boolean onScale(float f2, float f3, float f4) {
        float f5;
        Log.d(TAG, "onScale(): scale = " + f4);
        if (isZoomEnabled()) {
            this.mZoomScaled += (f4 - 1.0f) / 4.0f;
            if (!HybridZoomingSystem.IS_4_OR_MORE_SAT || !(getModuleIndex() == 163 || getModuleIndex() == 165)) {
                f5 = Math.min(this.mMaxZoomRatio, 10.0f);
            } else {
                float f6 = this.mZoomRatio;
                f5 = f6 < 1.0f ? Math.min(this.mMaxZoomRatio, 2.0f) : f6 < 5.0f ? Math.min(this.mMaxZoomRatio, 5.0f) : f6 < 10.0f ? Math.min(this.mMaxZoomRatio, 10.0f) : Math.min(this.mMaxZoomRatio, 30.0f);
            }
            float f7 = f5 * this.mZoomScaled;
            Log.d(TAG, "onScale(): delta = " + f7 + ", mZoomRatio = " + this.mZoomRatio);
            if (Math.abs(f7) >= 0.01f && onZoomingActionUpdate(this.mZoomRatio + f7, 2)) {
                this.mZoomScaled = 0.0f;
                return true;
            }
        }
        return false;
    }

    public boolean onScaleBegin(float f2, float f3) {
        this.mZoomScaled = 0.0f;
        updateSATZooming(true);
        onZoomingActionStart(2);
        return true;
    }

    public void onScaleEnd() {
        Log.d(TAG, "onScaleEnd()");
        updateSATZooming(false);
        onZoomingActionEnd(2);
    }

    public void onSharedPreferenceChanged() {
    }

    public void onShineChanged(int i) {
    }

    public void onSingleTapUp(int i, int i2, boolean z) {
    }

    public void onStop() {
    }

    public void onSurfaceTextureReleased() {
    }

    public void onSurfaceTextureUpdated(DrawExtTexAttribute drawExtTexAttribute) {
    }

    public void onSwitchLens(boolean z, boolean z2) {
        switchCameraLens(z, true, z2);
    }

    /* access modifiers changed from: protected */
    public void onThermalConstrained() {
    }

    public void onUserInteraction() {
    }

    public void onWindowFocusChanged(boolean z) {
        if (z) {
            this.mIgnoreFocusChanged = false;
        }
    }

    public void onZoomRatioChanged(float f2, int i) {
        onZoomingActionUpdate(f2, i);
    }

    public void onZoomSwitchCamera() {
        if (isAlive() && CameraSettings.isSupportedOpticalZoom() && CameraSettings.isZoomByCameraSwitchingSupported()) {
            this.mActivity.getCameraScreenNail().disableSwitchAnimationOnce();
        }
    }

    /* access modifiers changed from: protected */
    public void onZoomingActionEnd(int i) {
    }

    /* access modifiers changed from: protected */
    public void onZoomingActionStart(int i) {
    }

    /* access modifiers changed from: protected */
    public boolean onZoomingActionUpdate(float f2, int i) {
        if (!isDeviceAlive()) {
            return false;
        }
        String simpleName = getClass().getSimpleName();
        Log.d(simpleName, "onZoomingActionUpdate(): newValue = " + f2 + ", minValue = " + this.mMinZoomRatio + ", maxValue = " + this.mMaxZoomRatio);
        float f3 = this.mZoomRatio;
        float clamp = HybridZoomingSystem.clamp(f2, this.mMinZoomRatio, this.mMaxZoomRatio);
        if (f3 == clamp) {
            return false;
        }
        String simpleName2 = getClass().getSimpleName();
        Log.d(simpleName2, "onZoomingActionUpdate(): changed from " + f3 + " to " + clamp);
        setZoomRatio(clamp);
        if (onInterceptZoomingEvent(f3, clamp, i)) {
            return false;
        }
        int i2 = (f3 > 1.0f ? 1 : (f3 == 1.0f ? 0 : -1));
        if (i2 <= 0 || clamp <= 1.0f) {
            updatePreferenceTrampoline(11, 30, 34, 42, 20);
            if (this.mUltraCameraCapabilities == null) {
                this.mUltraCameraCapabilities = Camera2DataContainer.getInstance().getCapabilities(Camera2DataContainer.getInstance().getUltraWideCameraId());
                CameraCapabilities cameraCapabilities = this.mUltraCameraCapabilities;
                if (cameraCapabilities != null) {
                    cameraCapabilities.setOperatingMode(getOperatingMode());
                }
            }
            onCapabilityChanged(clamp < 1.0f ? this.mUltraCameraCapabilities : this.mCameraCapabilities);
        }
        if (HybridZoomingSystem.IS_3_OR_MORE_SAT) {
            updatePreferenceInWorkThread(24, 46, 47);
        } else {
            updatePreferenceInWorkThread(24);
        }
        ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
        if (dualController == null || !dualController.isZoomVisible() || CameraSettings.isMacroModeEnabled(this.mModuleIndex)) {
            updateStatusBar(2);
        } else {
            dualController.updateSlideAndZoomRatio(i);
        }
        if (HybridZoomingSystem.IS_3_OR_MORE_SAT) {
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            if (bottomPopupTips != null) {
                if (f3 < 1.0f || clamp >= 1.0f) {
                    if (i2 < 0 && clamp >= 1.0f && bottomPopupTips.containTips(R.string.ultra_wide_open_tip_sat)) {
                        bottomPopupTips.directlyHideTips();
                    }
                } else if ((isCameraSwitchingDuringZoomingAllowed() || this.mActualCameraId == Camera2DataContainer.getInstance().getSATCameraId()) && CameraSettings.shouldShowUltraWideSatTip(this.mModuleIndex)) {
                    bottomPopupTips.showTips(13, (int) R.string.ultra_wide_open_tip_sat, 2);
                }
            }
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void openCamera() {
    }

    /* access modifiers changed from: protected */
    public abstract void openSettingActivity();

    /* access modifiers changed from: protected */
    public void performKeyClicked(int i, String str, int i2, boolean z) {
    }

    /* access modifiers changed from: protected */
    public final void playCameraSound(int i) {
        Camera camera = this.mActivity;
        if (camera != null && !camera.isActivityPaused() && !isNeedMute() && CameraSettings.isCameraSoundOpen()) {
            this.mActivity.playCameraSound(i);
        }
    }

    public void preTransferOrientation(int i, int i2) {
        if (i == -1) {
            i = 0;
        }
        this.mOrientation = i;
        this.mOrientationCompensation = i2;
    }

    public void registerProtocol() {
        Log.d(TAG, "registerProtocol");
        ModeCoordinatorImpl.getInstance().attachProtocol(170, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(200, this);
    }

    public void requestRender() {
    }

    public void resetAiSceneInDocumentModeOn() {
    }

    public void resetEvValue() {
        if (isDeviceAlive()) {
            this.mCamera2Device.setExposureCompensation(0);
            this.mCamera2Device.setAWBLock(false);
            CameraSettings.resetExposure();
            updatePreferenceInWorkThread(new int[0]);
        }
    }

    @CallSuper
    public void resetMutexModeManually() {
        this.mMutexModePicker.resetMutexMode();
    }

    public final void restartModule() {
        if (!this.mActivity.isActivityPaused()) {
            this.mActivity.onModeSelected(StartControl.create(this.mModuleIndex).setViewConfigType(3).setNeedReConfigureData(false).setNeedBlurAnimation(true).setNeedReConfigureCamera(true));
        }
    }

    /* access modifiers changed from: protected */
    @CallSuper
    public void restoreBottom() {
        ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
        ModeProtocol.BackStack backStack = (ModeProtocol.BackStack) ModeCoordinatorImpl.getInstance().getAttachProtocol(171);
        if (backStack != null) {
            backStack.handleBackStackFromShutter();
        }
        if (baseDelegate.getActiveFragment(R.id.bottom_action) != 241) {
            baseDelegate.delegateEvent(7);
        }
    }

    /* access modifiers changed from: protected */
    public boolean retryOnceIfCameraError(Handler handler) {
        if (DataRepository.dataItemGlobal().isRetriedIfCameraError() || this.mActivity.isActivityPaused()) {
            return false;
        }
        Log.e(TAG, "onCameraException: retry1");
        DataRepository.dataItemGlobal().setRetriedIfCameraError(true);
        handler.post(new Runnable() {
            public void run() {
                BaseModule baseModule = BaseModule.this;
                baseModule.mActivity.onModeSelected(StartControl.create(baseModule.mModuleIndex).setViewConfigType(1).setNeedBlurAnimation(false).setNeedReConfigureCamera(true));
            }
        });
        return true;
    }

    /* access modifiers changed from: protected */
    public void sendOpenFailMessage() {
    }

    /* access modifiers changed from: protected */
    public void setAWBMode(String str) {
        if (isDeviceAlive()) {
            this.mCamera2Device.setAWBLock(false);
            if (str.equals("manual")) {
                if (b.isMTKPlatform()) {
                    this.mCamera2Device.setAWBMode(10);
                } else {
                    this.mCamera2Device.setAWBMode(0);
                }
                this.mCamera2Device.setCustomAWB(CameraSettings.getCustomWB());
                return;
            }
            int parseInt = Util.parseInt(str, 1);
            if (Util.isSupported(parseInt, this.mCameraCapabilities.getSupportedAWBModes())) {
                this.mCamera2Device.setAWBMode(parseInt);
            } else {
                this.mCamera2Device.setAWBMode(1);
            }
        }
    }

    public void setActivity(Camera camera) {
        this.mActivity = camera;
    }

    public void setCameraDevice(Camera2Proxy camera2Proxy) {
        this.mCamera2Device = camera2Proxy;
        this.mCameraCapabilities = camera2Proxy.getCapabilities();
        this.mZoomSupported = this.mCameraCapabilities.isZoomSupported();
        this.mActualCameraId = camera2Proxy.getId();
        if (HybridZoomingSystem.IS_3_OR_MORE_SAT && !DataRepository.dataItemFeature()._c()) {
            this.mUltraCameraCapabilities = Camera2DataContainer.getInstance().getCapabilities(Camera2DataContainer.getInstance().getUltraWideCameraId());
            CameraCapabilities cameraCapabilities = this.mUltraCameraCapabilities;
            if (cameraCapabilities != null) {
                cameraCapabilities.setOperatingMode(getOperatingMode());
            }
        }
        if (DataRepository.dataItemFeature().ue()) {
            this.mStandaloneMacroCameraCapabilities = Camera2DataContainer.getInstance().getCapabilities(Camera2DataContainer.getInstance().getStandaloneMacroCameraId());
            CameraCapabilities cameraCapabilities2 = this.mStandaloneMacroCameraCapabilities;
            if (cameraCapabilities2 != null) {
                cameraCapabilities2.setOperatingMode(getOperatingMode());
            }
        }
    }

    public void setCameraId(int i) {
        this.mBogusCameraId = i;
    }

    /* access modifiers changed from: protected */
    public void setCameraState(int i) {
        Log.d(TAG, "setCameraState: " + i);
        this.mCameraState = i;
    }

    /* access modifiers changed from: protected */
    public void setColorEffect(String str) {
        if (isDeviceAlive()) {
            int parseInt = Util.parseInt(str, 0);
            if (Util.isSupported(parseInt, this.mCameraCapabilities.getSupportedColorEffects())) {
                Log.d(TAG, "colorEffect: " + str);
                this.mCamera2Device.setColorEffect(parseInt);
            }
        }
    }

    public void setDeparted() {
        Log.d(TAG, "setDeparted");
        Disposable disposable = this.mUpdateWorkThreadDisposable;
        if (disposable != null) {
            disposable.dispose();
        }
        Disposable disposable2 = this.mLensDirtyDetectDisposable;
        if (disposable2 != null) {
            disposable2.dispose();
        }
        Disposable disposable3 = this.mLensDirtyDetectHintDisposable;
        if (disposable3 != null) {
            disposable3.dispose();
        }
        Camera camera = this.mActivity;
        if (camera != null) {
            camera.hideLensDirtyDetectedHint();
        }
        this.mIsDeparted.set(true);
        this.mIsFrameAvailable.set(false);
    }

    public void setDisplayRectAndUIStyle(Rect rect, int i) {
        this.mDisplayRect = rect;
        this.mUIStyle = i;
    }

    /* access modifiers changed from: protected */
    @WorkerThread
    public final void setEvValue() {
        if (isAlive()) {
            int i = this.mEvState;
            if (i == 2) {
                this.mCamera2Device.setAWBLock(false);
            } else if (i == 1 || i == 3) {
                this.mCamera2Device.setExposureCompensation(this.mEvValue);
                if (this.mEvState != 1) {
                    return;
                }
                if (this.mEvValue != 0) {
                    this.mCamera2Device.setAWBLock(true);
                } else {
                    this.mCamera2Device.setAWBLock(false);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void setFlashMode(String str) {
        if (isDeviceAlive()) {
            Log.d(TAG, "flashMode: " + str);
            int parseInt = Util.parseInt(str, 0);
            if ((ThermalDetector.getInstance().thermalCloseFlash() && DataRepository.dataItemConfig().getComponentFlash().isHardwareSupported()) || (isFrontCamera() && this.mActivity.isScreenSlideOff())) {
                parseInt = 0;
            }
            this.mUseLegacyFlashMode = DataRepository.dataItemFeature().Ge();
            this.mCamera2Device.setUseLegacyFlashMode(this.mUseLegacyFlashMode);
            this.mCamera2Device.setFlashMode(parseInt);
        }
    }

    /* access modifiers changed from: protected */
    public void setFocusMode(String str) {
        if (isDeviceAlive()) {
            int convertToFocusMode = AutoFocus.convertToFocusMode(str);
            if (Util.isSupported(convertToFocusMode, this.mCameraCapabilities.getSupportedFocusModes())) {
                this.mCamera2Device.setFocusMode(convertToFocusMode);
            }
        }
    }

    public void setFrameAvailable(boolean z) {
        this.mIsFrameAvailable.set(z);
    }

    public void setMaxZoomRatio(float f2) {
        Log.d(TAG, "setMaxZoomRatio(): " + f2);
        this.mMaxZoomRatio = f2;
    }

    public void setMinZoomRatio(float f2) {
        Log.d(TAG, "setMinZoomRatio(): " + f2);
        this.mMinZoomRatio = f2;
    }

    public void setModuleIndex(int i) {
        this.mModuleIndex = i;
    }

    public void setRestoring(boolean z) {
        this.mRestoring = z;
    }

    public void setThermalLevel(int i) {
        this.mThermalLevel = i;
    }

    /* access modifiers changed from: protected */
    public void setTriggerMode(int i) {
        this.mTriggerMode = i;
    }

    /* access modifiers changed from: protected */
    @WorkerThread
    public void setZoomRatio(float f2) {
        Log.d(TAG, "setZoomRatio(): " + f2);
        this.mZoomRatio = f2;
        CameraSettings.writeZoom(f2);
        HybridZoomingSystem.setZoomRatioHistory(this.mModuleIndex, String.valueOf(f2));
    }

    /* access modifiers changed from: protected */
    public boolean shouldCheckSatFallbackState() {
        return false;
    }

    public boolean shouldReleaseLater() {
        return false;
    }

    public void showLensDirtyTip() {
        Disposable disposable = this.mLensDirtyDetectDisposable;
        if (disposable != null) {
            disposable.dispose();
            this.mLensDirtyDetectDisposable = null;
            Completable.complete().observeOn(GlobalConstant.sCameraSetupScheduler).doOnComplete(new ActionUpdateLensDirtyDetect(this, false)).subscribe();
            if (CameraSettings.shouldShowLensDirtyDetectHint()) {
                this.mActivity.showLensDirtyDetectedHint();
                this.mLensDirtyDetectHintDisposable = Completable.complete().delay(8000, TimeUnit.MILLISECONDS, GlobalConstant.sCameraSetupScheduler).doOnComplete(new ActionHideLensDirtyDetectHint(this)).subscribe();
            }
        }
    }

    public void showOrHideChip(boolean z) {
    }

    public void showQRCodeResult() {
    }

    public final void thermalConstrained() {
        if (!this.mActivity.isActivityPaused()) {
            Handler handler = this.mHandler;
            if (handler != null) {
                handler.post(new K(this));
            }
        }
    }

    /* access modifiers changed from: protected */
    public void trackGeneralInfo(Map map, int i, boolean z, BeautyValues beautyValues, boolean z2, int i2) {
        HashMap hashMap = map == null ? new HashMap() : map;
        hashMap.put(MistatsConstants.Manual.PARAM_EV, Integer.valueOf(this.mEvValueForTrack));
        hashMap.put(MistatsConstants.BaseEvent.COUNT, String.valueOf(i));
        hashMap.put(MistatsConstants.AlgoAttr.PARAM_AI_SCENE, Integer.valueOf(i2));
        CameraStatUtils.trackGeneralInfo(hashMap, z, z2, this.mModuleIndex, getTriggerMode(), isFrontCamera(), getActualCameraId(), beautyValues, this.mMutexModePicker, this.mFlashAutoModeState);
    }

    /* access modifiers changed from: protected */
    public void trackModeCustomInfo(Map map, boolean z, BeautyValues beautyValues, int i) {
    }

    /* access modifiers changed from: protected */
    public void trackPictureTaken(MistatsWrapper.PictureTakenParameter pictureTakenParameter) {
        if (pictureTakenParameter != null) {
            CameraStatUtils.trackMacroModeTaken(this.mModuleIndex);
            HashMap hashMap = new HashMap();
            int i = this.mModuleIndex;
            if (i == 163) {
                CameraStatUtils.trackMoonMode(hashMap, pictureTakenParameter.isEnteringMoon, pictureTakenParameter.isSelectMoonMode);
                CameraStatUtils.trackSuperNightInCaptureMode(hashMap, pictureTakenParameter.isSuperNightInCaptureMode);
            } else if (i == 171) {
                BeautyValues beautyValues = pictureTakenParameter.beautyValues;
                if (beautyValues != null) {
                    hashMap.put(MistatsConstants.BeautyAttr.PARAM_BEAUTY_LEVEL, Integer.valueOf(beautyValues.mBeautySkinSmooth));
                }
                CameraStatUtils.trackCapturePortrait(hashMap);
            }
            trackModeCustomInfo(hashMap, pictureTakenParameter.burst, pictureTakenParameter.beautyValues, pictureTakenParameter.takenNum);
        }
    }

    public void tryRemoveCountDownMessage() {
    }

    public void unRegisterModulePersistProtocol() {
        Log.d(TAG, "unRegisterModulePersist");
    }

    public void unRegisterProtocol() {
        Log.d(TAG, "unRegisterProtocol");
        ModeCoordinatorImpl.getInstance().detachProtocol(170, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(200, this);
    }

    /* access modifiers changed from: protected */
    public void updateAntiBanding(String str) {
        if (isDeviceAlive()) {
            int parseInt = Util.parseInt(str, 3);
            if (Util.isSupported(parseInt, this.mCameraCapabilities.getSupportedAntiBandingModes())) {
                Log.d(TAG, "antiBanding: " + str);
                this.mCamera2Device.setAntiBanding(parseInt);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void updateBackSoftLightPreference() {
        setFlashMode("0");
    }

    /* access modifiers changed from: protected */
    public void updateCameraScreenNailSize(int i, int i2) {
        String simpleName = getClass().getSimpleName();
        Log.d(simpleName, "updateCameraScreenNailSize: " + i + "x" + i2);
        this.mActivity.getCameraScreenNail().setPreviewSize(i, i2);
        this.mMainProtocol.setPreviewSize(i, i2);
    }

    /* access modifiers changed from: protected */
    public final void updateExposureMeteringMode() {
        if (isDeviceAlive()) {
            this.mCamera2Device.setExposureMeteringMode(CameraSettings.getExposureMeteringMode());
        }
    }

    /* access modifiers changed from: protected */
    public void updateFlashPreference() {
    }

    /* access modifiers changed from: protected */
    public void updateHDRPreference() {
    }

    public void updateLensDirtyDetect(boolean z) {
        if (this.mCamera2Device == null) {
            Log.e(TAG, "updateLensDirtyDetect: mCamera2Device is null...");
            return;
        }
        boolean isLensDirtyDetectEnabled = CameraSettings.isLensDirtyDetectEnabled();
        if (!isLensDirtyDetectEnabled) {
            Disposable disposable = this.mLensDirtyDetectDisposable;
            if (disposable != null) {
                disposable.dispose();
                this.mLensDirtyDetectDisposable = null;
            }
        }
        this.mCamera2Device.setLensDirtyDetect(isLensDirtyDetectEnabled);
        if (z && isFrameAvailable() && !isDoingAction() && !isRecording()) {
            this.mCamera2Device.resumePreview();
        }
    }

    /* access modifiers changed from: protected */
    public void updateModuleRelated() {
        this.mCamera2Device.setModuleParameter(this.mModuleIndex, this.mBogusCameraId);
    }

    @WorkerThread
    public final void updatePreferenceInWorkThread(@UpdateConstant.UpdateType int... iArr) {
        Disposable disposable = this.mUpdateWorkThreadDisposable;
        if (disposable == null || disposable.isDisposed()) {
            Log.d(TAG, "the mUpdateWorkThreadDisposable is not available." + this.mUpdateWorkThreadDisposable + ". " + this);
            return;
        }
        Log.e(TAG, "types:" + join(iArr) + ", " + this);
        this.mUpdateWorkThreadEmitter.onNext(iArr);
    }

    public final void updatePreferenceTrampoline(@UpdateConstant.UpdateType int... iArr) {
        consumePreference(iArr);
    }

    public void updatePreviewSurface() {
    }

    public void updateSATZooming(boolean z) {
    }

    public void updateScreenSlide(boolean z) {
        if (z) {
            Camera2Proxy camera2Proxy = this.mCamera2Device;
            if (camera2Proxy != null) {
                camera2Proxy.setAELock(false);
                int i = this.mModuleIndex;
                if (i == 163 || i == 171 || i == 165) {
                    updatePreferenceInWorkThread(10, 36);
                    return;
                }
                updatePreferenceInWorkThread(10);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void updateStatusBar(int i) {
        ((ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)).alertUpdateValue(i);
    }

    public void updateThermalLevel() {
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        if (camera2Proxy != null) {
            camera2Proxy.setThermalLevel(this.mThermalLevel);
        }
    }

    /* access modifiers changed from: protected */
    public void updateTipMessage(int i, @StringRes int i2, int i3) {
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (bottomPopupTips != null) {
            bottomPopupTips.showTips(i, i2, i3);
        }
    }

    /* access modifiers changed from: protected */
    public void updateZoomRatioToggleButtonState(boolean z) {
        ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
        if (dualController != null) {
            dualController.setRecordingOrPausing(z);
            if (z) {
                dualController.hideZoomButton();
            } else {
                dualController.showZoomButton();
                dualController.setImmersiveModeEnabled(false);
            }
        }
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert == null) {
            return;
        }
        if (z) {
            topAlert.alertUpdateValue(2);
        } else {
            topAlert.clearAlertStatus();
        }
    }
}
