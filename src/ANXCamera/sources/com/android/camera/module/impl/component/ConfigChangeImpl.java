package com.android.camera.module.impl.component;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import com.android.camera.ActivityBase;
import com.android.camera.BasePreferenceActivity;
import com.android.camera.CameraAppImpl;
import com.android.camera.CameraIntentManager;
import com.android.camera.CameraPreferenceActivity;
import com.android.camera.CameraSettings;
import com.android.camera.CameraSize;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.MutexModeManager;
import com.android.camera.R;
import com.android.camera.ThermalDetector;
import com.android.camera.ToastUtils;
import com.android.camera.constant.BeautyConstant;
import com.android.camera.constant.EyeLightConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.config.ComponentConfigAi;
import com.android.camera.data.data.config.ComponentConfigBeauty;
import com.android.camera.data.data.config.ComponentConfigFilter;
import com.android.camera.data.data.config.ComponentConfigFlash;
import com.android.camera.data.data.config.ComponentConfigHdr;
import com.android.camera.data.data.config.ComponentConfigMeter;
import com.android.camera.data.data.config.ComponentConfigRatio;
import com.android.camera.data.data.config.ComponentConfigRaw;
import com.android.camera.data.data.config.ComponentConfigSlowMotion;
import com.android.camera.data.data.config.ComponentConfigSlowMotionQuality;
import com.android.camera.data.data.config.ComponentConfigVideoQuality;
import com.android.camera.data.data.config.ComponentRunningMacroMode;
import com.android.camera.data.data.config.ComponentRunningUltraPixel;
import com.android.camera.data.data.config.DataItemConfig;
import com.android.camera.data.data.config.SupportedConfigFactory;
import com.android.camera.data.data.global.DataItemGlobal;
import com.android.camera.data.data.runing.ComponentRunningEyeLight;
import com.android.camera.data.data.runing.ComponentRunningLiveShot;
import com.android.camera.data.data.runing.ComponentRunningShine;
import com.android.camera.data.data.runing.ComponentRunningTiltValue;
import com.android.camera.data.data.runing.ComponentRunningTimer;
import com.android.camera.data.data.runing.DataItemRunning;
import com.android.camera.data.observeable.VMProcessing;
import com.android.camera.effect.EffectController;
import com.android.camera.effect.FilterInfo;
import com.android.camera.fragment.beauty.BeautyValues;
import com.android.camera.fragment.manually.ManuallyListener;
import com.android.camera.fragment.vv.VVItem;
import com.android.camera.log.Log;
import com.android.camera.module.BaseModule;
import com.android.camera.module.Camera2Module;
import com.android.camera.module.ModuleManager;
import com.android.camera.module.VideoModule;
import com.android.camera.module.loader.StartControl;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStat;
import com.android.camera.statistic.CameraStatUtil;
import com.android.camera2.Camera2Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Consumer;

public class ConfigChangeImpl implements ModeProtocol.ConfigChanges {
    private static final String TAG = ConfigChangeImpl.class.getSimpleName();
    private ActivityBase mActivity;
    private int[] mRecordingClosedElements;

    public ConfigChangeImpl(ActivityBase activityBase) {
        this.mActivity = activityBase;
    }

    private void applyConfig(int i, int i2) {
        switch (i) {
            case 195:
                configPortraitSwitch(i2);
                return;
            case 196:
                showOrHideShine();
                return;
            case 199:
                configFocusPeakSwitch(i2);
                return;
            case 201:
                configAiSceneSwitch(i2);
                return;
            case 203:
                showOrHideLighting(true);
                return;
            case 204:
                configFPS960();
                return;
            case 205:
                configSwitchUltraWide();
                return;
            case 206:
                configLiveShotSwitch(i2);
                return;
            case 207:
                configSwitchUltraWideBokeh();
                return;
            case 209:
                configSwitchUltraPixel(i2);
                return;
            case 210:
                configRatio(false);
                return;
            case 212:
                showOrHideShine();
                return;
            case 213:
                configSlowQuality();
                return;
            case 214:
                configVideoQuality();
                return;
            case 215:
                configUltraPixelPortrait(i2);
                return;
            case 216:
                configVV();
                return;
            case 217:
                configVVBack();
                return;
            case 218:
                configSuperEIS();
                return;
            case 219:
                configReferenceLineSwitch(i2);
                return;
            case 220:
                configVideoSubtitle();
                return;
            case 225:
                showSetting();
                return;
            case 226:
                configTimerSwitch();
                return;
            case 228:
                configTiltSwitch(i2);
                return;
            case 229:
                configGradienterSwitch(i2);
                return;
            case 230:
                configHHTSwitch(i2);
                return;
            case 231:
                configMagicFocusSwitch();
                return;
            case 233:
                configVideoFast();
                return;
            case 234:
                beautyMutexHandle();
                configScene(i2);
                return;
            case 235:
                configGroupSwitch(i2);
                return;
            case 236:
                configMagicMirrorSwitch(i2);
                return;
            case 237:
                configRawSwitch(i2);
                return;
            case 238:
                configGenderAgeSwitch(i2);
                return;
            case 239:
                showOrHideShine();
                return;
            case 240:
                configDualWaterMarkSwitch();
                return;
            case 241:
                configSuperResolutionSwitch(i2);
                return;
            case 243:
                configVideoBokehSwitch(i2);
                return;
            case 246:
                configMoon(true);
                return;
            case 247:
                configMoonNight();
                return;
            case 248:
                configSilhouette();
                return;
            case 249:
                configMoonBacklight();
                return;
            case 251:
                configMiMovie(i2);
                return;
            case 252:
                configSwitchHandGesture();
                return;
            case 253:
                configAutoZoom();
                return;
            case 255:
                configMacroMode();
                return;
            default:
                return;
        }
    }

    private void beautyMutexHandle() {
    }

    private void changeMode(int i) {
        DataRepository.dataItemGlobal().setCurrentMode(i);
        this.mActivity.onModeSelected(StartControl.create(i).setViewConfigType(2).setNeedBlurAnimation(true).setNeedReConfigureCamera(true));
    }

    private boolean closeVideoFast() {
        DataItemGlobal dataItemGlobal = (DataItemGlobal) DataRepository.provider().dataGlobal();
        if (dataItemGlobal.getCurrentMode() != 169) {
            return false;
        }
        DataItemRunning dataItemRunning = DataRepository.dataItemRunning();
        dataItemGlobal.setCurrentMode(162);
        dataItemRunning.switchOff("pref_video_speed_fast_key");
        return true;
    }

    private void configAiSceneSwitch(int i) {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            int moduleIndex = baseModule.get().getModuleIndex();
            boolean aiSceneOpen = CameraSettings.getAiSceneOpen(moduleIndex);
            String str = TAG;
            Log.d(str, "configAiSceneSwitch: " + (!aiSceneOpen));
            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            switch (i) {
                case 1:
                    if (!aiSceneOpen) {
                        topAlert.alertSwitchHint(1, (int) R.string.pref_camera_front_ai_scene_entry_on);
                        CameraSettings.setAiSceneOpen(moduleIndex, true);
                        CameraStatUtil.trackPreferenceChange("pref_camera_ai_scene_mode_key", "on");
                    } else {
                        topAlert.alertSwitchHint(1, (int) R.string.pref_camera_front_ai_scene_entry_off);
                        CameraSettings.setAiSceneOpen(moduleIndex, false);
                        CameraStatUtil.trackPreferenceChange("pref_camera_ai_scene_mode_key", "off");
                        BaseModule baseModule2 = baseModule.get();
                        if (baseModule2 != null && (baseModule2 instanceof Camera2Module)) {
                            ((Camera2Module) baseModule2).closeMoonMode(0, 8);
                        }
                    }
                    topAlert.updateConfigItem(201);
                    if (CameraSettings.isGroupShotOn()) {
                        ((ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164)).configGroupSwitch(4);
                        topAlert.refreshExtraMenu();
                        break;
                    }
                    break;
                case 3:
                    CameraSettings.setAiSceneOpen(moduleIndex, false);
                    topAlert.updateConfigItem(201);
                    break;
            }
            baseModule.get().updatePreferenceTrampoline(36);
            baseModule.get().getCameraDevice().resumePreview();
            if (i == 1 && CameraSettings.isUltraPixelOn()) {
                configSwitchUltraPixel(3);
            }
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            if (bottomPopupTips != null) {
                bottomPopupTips.reConfigQrCodeTip();
            }
            if (i == 1 && CameraSettings.isUltraPixelPortraitFrontOn()) {
                configUltraPixelPortrait(3);
            }
        }
    }

    private void configAutoZoom() {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            if (topAlert != null) {
                int moduleIndex = baseModule.get().getModuleIndex();
                boolean isAutoZoomEnabled = CameraSettings.isAutoZoomEnabled(moduleIndex);
                HybridZoomingSystem.clearZoomRatioHistory();
                if (isAutoZoomEnabled) {
                    CameraSettings.setAutoZoomEnabled(moduleIndex, false);
                    topAlert.updateConfigItem(253);
                } else {
                    CameraSettings.setAutoZoomEnabled(moduleIndex, true);
                    topAlert.updateConfigItem(253);
                    switchOffElementsSilent(216);
                    closeVideoFast();
                    resetBeautyLevel();
                    CameraSettings.setSuperEISEnabled(moduleIndex, false);
                    CameraSettings.setSubtitleEnabled(moduleIndex, false);
                }
                ComponentRunningMacroMode componentRunningMacroMode = DataRepository.dataItemRunning().getComponentRunningMacroMode();
                if (componentRunningMacroMode.isSwitchOn(moduleIndex)) {
                    componentRunningMacroMode.setSwitchOff(moduleIndex);
                }
                this.mActivity.onModeSelected(StartControl.create(162).setViewConfigType(2).setResetType(7).setNeedBlurAnimation(true).setNeedReConfigureData(false).setNeedReConfigureCamera(true));
                ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
                if (CameraSettings.isAutoZoomEnabled(162)) {
                    topAlert.alertSwitchHint(2, (int) R.string.autozoom_hint);
                } else {
                    topAlert.alertSwitchHint(2, (int) R.string.autozoom_disabled_hint);
                }
                bottomPopupTips.updateLeftTipImage();
                bottomPopupTips.updateTipImage();
            }
        }
    }

    private void configMacroMode() {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            int moduleIndex = baseModule.get().getModuleIndex();
            boolean z = false;
            boolean z2 = 169 == moduleIndex;
            boolean z3 = !CameraSettings.isMacroModeEnabled(moduleIndex);
            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            if (z3 && z2) {
                DataRepository.dataItemGlobal().setCurrentMode(162);
            }
            CameraSettings.setAutoZoomEnabled(moduleIndex, false);
            CameraSettings.setSuperEISEnabled(moduleIndex, false);
            if (z3 && moduleIndex == 162) {
                resetBeautyLevel();
            }
            if (!DataRepository.dataItemFeature().ii() || z3) {
                HybridZoomingSystem.clearZoomRatioHistory();
            }
            ComponentRunningMacroMode componentRunningMacroMode = DataRepository.dataItemRunning().getComponentRunningMacroMode();
            Boolean.valueOf(false);
            if (z3) {
                componentRunningMacroMode.setSwitchOn(moduleIndex);
                Boolean.valueOf(true);
            } else {
                componentRunningMacroMode.setSwitchOff(moduleIndex);
                Boolean.valueOf(false);
            }
            if (moduleIndex == 172) {
                CameraStatUtil.trackSlowMotionMacroCount(z3);
            }
            if (DataRepository.dataItemFeature().hH()) {
                ModeProtocol.StandaloneMacroProtocol standaloneMacroProtocol = (ModeProtocol.StandaloneMacroProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(202);
                if (standaloneMacroProtocol != null) {
                    standaloneMacroProtocol.onSwitchStandaloneMacro(true);
                }
            } else {
                ModeProtocol.UltraWideProtocol ultraWideProtocol = (ModeProtocol.UltraWideProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(200);
                if (ultraWideProtocol != null) {
                    ultraWideProtocol.onSwitchUltraWide(false);
                }
            }
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
            ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
            if (z3) {
                if (bottomPopupTips != null) {
                    bottomPopupTips.directHideTipImage();
                    bottomPopupTips.directShowOrHideLeftTipImage(false);
                }
                if (dualController != null && !DataRepository.dataItemFeature().ii()) {
                    dualController.hideZoomButton();
                    return;
                }
                return;
            }
            if (miBeautyProtocol != null) {
                z = miBeautyProtocol.isBeautyPanelShow();
            }
            if (bottomPopupTips != null && !z) {
                bottomPopupTips.reInitTipImage();
            }
            if (dualController != null && !z && !DataRepository.dataItemFeature().ii()) {
                if (!CameraSettings.isUltraWideConfigOpen(moduleIndex) && (moduleIndex != 172 || !DataRepository.dataItemFeature().ik())) {
                    dualController.showZoomButton();
                }
                if (topAlert != null) {
                    topAlert.clearAlertStatus();
                }
            }
        }
    }

    private void configMoon(boolean z) {
        getBaseModule().ifPresent(new Consumer(z) {
            private final /* synthetic */ boolean f$0;

            {
                this.f$0 = r1;
            }

            public final void accept(Object obj) {
                ConfigChangeImpl.lambda$configMoon$2(this.f$0, (BaseModule) obj);
            }
        });
    }

    private void configMoonBacklight() {
        getBaseModule().ifPresent($$Lambda$ConfigChangeImpl$QcDA9iuVr6DI3HNAErAAatd2skk.INSTANCE);
    }

    private void configMoonNight() {
        getBaseModule().ifPresent(new Consumer() {
            public final void accept(Object obj) {
                ConfigChangeImpl.lambda$configMoonNight$3(ConfigChangeImpl.this, (BaseModule) obj);
            }
        });
    }

    private void configReferenceLineSwitch(int i) {
        boolean z = !DataRepository.dataItemGlobal().getBoolean(CameraSettings.KEY_REFERENCE_LINE, false);
        DataRepository.dataItemGlobal().putBoolean(CameraSettings.KEY_REFERENCE_LINE, z).apply();
        if (1 == i) {
            trackReferenceLineChanged(z);
        }
        if (getBaseModule().isPresent()) {
            ModeProtocol.MainContentProtocol mainContentProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
            if (mainContentProtocol != null) {
                mainContentProtocol.updateReferenceLineSwitched(z);
            }
        }
    }

    private void configSilhouette() {
        getBaseModule().ifPresent($$Lambda$ConfigChangeImpl$DKF9ElWpX7maWa9hH1iRRwwFgJ0.INSTANCE);
    }

    private void configSlowQuality() {
        DataItemConfig dataItemConfig = DataRepository.dataItemConfig();
        ComponentConfigSlowMotionQuality componentConfigSlowMotionQuality = dataItemConfig.getComponentConfigSlowMotionQuality();
        int currentMode = ((DataItemGlobal) DataRepository.provider().dataGlobal()).getCurrentMode();
        String nextValue = componentConfigSlowMotionQuality.getNextValue(currentMode);
        CameraStatUtil.trackSlowMotionQuality("pref_video_new_slow_motion_key", dataItemConfig.getComponentConfigSlowMotion().getComponentValue(currentMode), nextValue);
        componentConfigSlowMotionQuality.setComponentValue(currentMode, nextValue);
        this.mActivity.onModeSelected(StartControl.create(currentMode).setViewConfigType(2).setResetType(7).setNeedReConfigureData(false).setNeedBlurAnimation(true).setNeedReConfigureCamera(true));
    }

    private void configSuperEIS() {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule != null) {
            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            if (topAlert != null) {
                int moduleIndex = baseModule.get().getModuleIndex();
                boolean isSuperEISEnabled = CameraSettings.isSuperEISEnabled(moduleIndex);
                HybridZoomingSystem.clearZoomRatioHistory();
                if (isSuperEISEnabled) {
                    CameraSettings.setSuperEISEnabled(moduleIndex, false);
                    topAlert.updateConfigItem(218);
                } else {
                    CameraSettings.setSuperEISEnabled(moduleIndex, true);
                    topAlert.updateConfigItem(218);
                    switchOffElementsSilent(216);
                    closeVideoFast();
                    resetBeautyLevel();
                    ComponentRunningMacroMode componentRunningMacroMode = DataRepository.dataItemRunning().getComponentRunningMacroMode();
                    if (componentRunningMacroMode.isSwitchOn(moduleIndex)) {
                        componentRunningMacroMode.setSwitchOff(moduleIndex);
                    }
                    CameraSettings.setAutoZoomEnabled(moduleIndex, false);
                }
                trackSuperEISChanged(!isSuperEISEnabled);
                this.mActivity.onModeSelected(StartControl.create(162).setViewConfigType(2).setNeedBlurAnimation(true).setNeedReConfigureData(false).setNeedReConfigureCamera(true));
                ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
                if (isSuperEISEnabled) {
                    topAlert.alertTopHint(8, (int) R.string.super_eis_disabled_hint);
                    topAlert.alertSwitchHint(1, (int) R.string.super_eis_disabled_hint);
                }
                bottomPopupTips.updateLeftTipImage();
                bottomPopupTips.updateTipImage();
            }
        }
    }

    private void configSwitchHandGesture() {
        if (DataRepository.dataItemRunning().supportHandGesture()) {
            Optional<BaseModule> baseModule = getBaseModule();
            if (baseModule.isPresent()) {
                BaseModule baseModule2 = baseModule.get();
                if (baseModule2 != null) {
                    boolean z = !CameraSettings.isHandGestureOpen();
                    CameraSettings.setHandGestureStatus(z);
                    ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
                    if (z) {
                        bottomPopupTips.showTips(16, (int) R.string.hand_gesture_open_tip, 2);
                    }
                    ((Camera2Module) baseModule2).onHanGestureSwitched(z);
                }
            }
        }
    }

    private void configSwitchUltraWide() {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            int moduleIndex = baseModule.get().getModuleIndex();
            boolean z = !CameraSettings.isUltraWideConfigOpen(moduleIndex);
            if (CameraSettings.isUltraPixelOn()) {
                CameraSettings.switchOffUltraPixel();
            }
            HybridZoomingSystem.clearZoomRatioHistory();
            CameraSettings.setUltraWideConfig(moduleIndex, z);
            ModeProtocol.UltraWideProtocol ultraWideProtocol = (ModeProtocol.UltraWideProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(200);
            if (ultraWideProtocol != null) {
                ultraWideProtocol.onSwitchUltraWide(true);
            }
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            if (bottomPopupTips != null) {
                if (!z) {
                    bottomPopupTips.showTips(13, (int) R.string.ultra_wide_close_tip, 6);
                } else if (CameraSettings.shouldShowUltraWideStickyTip(moduleIndex)) {
                    bottomPopupTips.showTips(13, R.string.ultra_wide_open_tip, 4, 500);
                } else {
                    bottomPopupTips.showTips(13, R.string.ultra_wide_open_tip, 7, 500);
                }
                bottomPopupTips.reConfigQrCodeTip();
            }
        }
    }

    private void configSwitchUltraWideBokeh() {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null && this.mActivity != null) {
            Optional<BaseModule> baseModule = getBaseModule();
            if (baseModule.isPresent()) {
                if (DataRepository.dataItemRunning().isSwitchOn("pref_ultra_wide_bokeh_enabled")) {
                    DataRepository.dataItemRunning().switchOff("pref_ultra_wide_bokeh_enabled");
                    topAlert.alertSwitchHint(2, (int) R.string.ultra_wide_bokeh_close_tip);
                } else {
                    DataRepository.dataItemRunning().switchOn("pref_ultra_wide_bokeh_enabled");
                    topAlert.alertSwitchHint(2, (int) R.string.ultra_wide_bokeh_open_tip);
                }
                baseModule.get().restartModule();
            }
        }
    }

    private void configVV() {
        boolean z;
        ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
        if (baseDelegate.getActiveFragment(R.id.bottom_action) != 65523) {
            int moduleIndex = getBaseModule().get().getModuleIndex();
            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            if (topAlert != null) {
                topAlert.clearVideoUltraClear();
            }
            if (moduleIndex == 169) {
                closeVideoFast();
            } else if (CameraSettings.isFaceBeautyOn(moduleIndex, (BeautyValues) null)) {
                resetBeautyLevel();
            } else if (CameraSettings.isSuperEISEnabled(moduleIndex)) {
                CameraSettings.setSuperEISEnabled(moduleIndex, false);
            } else if (CameraSettings.isSubtitleEnabled(moduleIndex)) {
                CameraSettings.setSubtitleEnabled(moduleIndex, false);
            } else if (CameraSettings.isAutoZoomEnabled(moduleIndex)) {
                CameraSettings.setAutoZoomEnabled(moduleIndex, false);
            } else if (CameraSettings.isMacroModeEnabled(moduleIndex)) {
                DataRepository.dataItemRunning().getComponentRunningMacroMode().setSwitchOff(moduleIndex);
            } else {
                z = false;
                baseDelegate.delegateEvent(15);
            }
            z = true;
            baseDelegate.delegateEvent(15);
        } else {
            baseDelegate.delegateEvent(15);
            reCheckVideoUltraClearTip();
            z = false;
        }
        ModeProtocol.TopAlert topAlert2 = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert2 != null) {
            topAlert2.updateConfigItem(216);
            if (z) {
                changeMode(162);
            }
        }
    }

    private void configVVBack() {
        ModeProtocol.LiveVVProcess liveVVProcess = (ModeProtocol.LiveVVProcess) ModeCoordinatorImpl.getInstance().getAttachProtocol(230);
        if (liveVVProcess != null) {
            liveVVProcess.showExitConfirm();
        }
    }

    private void configVideoBokehSwitch(int i) {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            DataItemConfig dataItemConfig = DataRepository.dataItemConfig();
            boolean isSwitchOn = dataItemConfig.isSwitchOn("pref_video_bokeh_key");
            String str = TAG;
            Log.d(str, "configVideoBokehSwitch: switchOn = " + (!isSwitchOn));
            if (!isSwitchOn) {
                dataItemConfig.switchOn("pref_video_bokeh_key");
                CameraStatUtil.trackPreferenceChange("pref_video_bokeh_key", "on");
            } else {
                dataItemConfig.switchOff("pref_video_bokeh_key");
                CameraStatUtil.trackPreferenceChange("pref_video_bokeh_key", "off");
            }
            topAlert.updateConfigItem(243);
            this.mActivity.onModeSelected(StartControl.create(baseModule.get().getModuleIndex()).setViewConfigType(2).setNeedBlurAnimation(true).setNeedReConfigureCamera(true).setNeedReConfigureData(false));
            topAlert.alertSwitchHint(2, !isSwitchOn ? R.string.pref_camera_video_bokeh_on : R.string.pref_camera_video_bokeh_off);
        }
    }

    private void configVideoSubtitle() {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            if (topAlert != null) {
                int moduleIndex = baseModule.get().getModuleIndex();
                if (CameraSettings.isSubtitleEnabled(moduleIndex)) {
                    CameraSettings.setSubtitleEnabled(moduleIndex, false);
                    topAlert.updateConfigItem(220);
                } else {
                    CameraSettings.setSubtitleEnabled(moduleIndex, true);
                    topAlert.updateConfigItem(220);
                    switchOffElementsSilent(216);
                    closeVideoFast();
                    CameraSettings.setAutoZoomEnabled(moduleIndex, false);
                }
                ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
                if (CameraSettings.isSubtitleEnabled(162)) {
                    updateTipMessage(4, R.string.hint_subtitle, 2);
                    ((ModeProtocol.SubtitleRecording) ModeCoordinatorImpl.getInstance().getAttachProtocol(231)).initPermission();
                } else {
                    hideTipMessage(R.string.hint_subtitle);
                }
                bottomPopupTips.updateLeftTipImage();
                bottomPopupTips.updateTipImage();
                this.mActivity.onModeSelected(StartControl.create(162).setViewConfigType(2).setResetType(7).setNeedBlurAnimation(true).setNeedReConfigureData(false).setNeedReConfigureCamera(true));
            }
        }
    }

    private void conflictWithFlashAndHdr() {
        DataItemRunning dataItemRunning = DataRepository.dataItemRunning();
        dataItemRunning.switchOff("pref_camera_hand_night_key");
        dataItemRunning.switchOff("pref_camera_groupshot_mode_key");
        dataItemRunning.switchOff("pref_camera_super_resolution_key");
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        int activeModuleIndex = ModuleManager.getActiveModuleIndex();
        if (CameraSettings.shouldShowUltraWideStickyTip(activeModuleIndex) && bottomPopupTips.getCurrentBottomTipType() == 13) {
            return;
        }
        if (CameraSettings.shouldShowUltraWideStickyTip(activeModuleIndex) && bottomPopupTips.getCurrentBottomTipType() == 17) {
            bottomPopupTips.showTips(13, (int) R.string.ultra_wide_open_tip, 4);
        } else if (!CameraSettings.isMacroModeEnabled(activeModuleIndex) || bottomPopupTips.getCurrentBottomTipType() != 18) {
            bottomPopupTips.directlyHideTips();
        }
    }

    public static ConfigChangeImpl create(ActivityBase activityBase) {
        return new ConfigChangeImpl(activityBase);
    }

    private Optional<BaseModule> getBaseModule() {
        return this.mActivity == null ? Optional.empty() : Optional.ofNullable((BaseModule) this.mActivity.getCurrentModule());
    }

    private boolean getState(int i, String str) {
        if (i == 2) {
            return DataRepository.dataItemRunning().isSwitchOn(str);
        }
        if (i != 4) {
            return DataRepository.dataItemRunning().triggerSwitchAndGet(str);
        }
        DataRepository.dataItemRunning().switchOff(str);
        return false;
    }

    private void hideTipMessage(@StringRes int i) {
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (i <= 0 || bottomPopupTips.containTips(i)) {
            bottomPopupTips.directlyHideTips();
        }
    }

    private boolean is4KQuality(int i, int i2) {
        return i >= 3840 && i2 >= 2160;
    }

    private boolean isAlive() {
        return this.mActivity != null;
    }

    private boolean isBeautyPanelShow() {
        ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
        if (miBeautyProtocol != null) {
            return miBeautyProtocol.isBeautyPanelShow();
        }
        return false;
    }

    private boolean isChangeManuallyParameters() {
        DataItemConfig dataItemConfig = DataRepository.dataItemConfig();
        return dataItemConfig.getmComponentManuallyWB().isModified(167) || dataItemConfig.getmComponentManuallyET().isModified(167) || dataItemConfig.getmComponentManuallyISO().isModified(167) || dataItemConfig.getManuallyFocus().isModified(167) || dataItemConfig.getComponentConfigMeter().isModified(167);
    }

    static /* synthetic */ void lambda$closeMutexElement$14(int[] iArr, BaseModule baseModule) {
        baseModule.updatePreferenceTrampoline(iArr);
        baseModule.getCameraDevice().resumePreview();
    }

    public static /* synthetic */ void lambda$configBackSoftLightSwitch$11(ConfigChangeImpl configChangeImpl, BaseModule baseModule) {
        if (172 != baseModule.getModuleIndex()) {
            configChangeImpl.conflictWithFlashAndHdr();
        }
        baseModule.updatePreferenceInWorkThread(11, 58);
    }

    static /* synthetic */ void lambda$configMoon$2(boolean z, BaseModule baseModule) {
        if (baseModule instanceof Camera2Module) {
            String str = TAG;
            Log.d(str, "(moon_mode) config moon:" + z);
            ((Camera2Module) baseModule).updateMoon(z);
        }
    }

    static /* synthetic */ void lambda$configMoonBacklight$0(BaseModule baseModule) {
        if (baseModule instanceof Camera2Module) {
            ((Camera2Module) baseModule).updateBacklight();
        }
    }

    public static /* synthetic */ void lambda$configMoonNight$3(ConfigChangeImpl configChangeImpl, BaseModule baseModule) {
        if (baseModule instanceof Camera2Module) {
            configChangeImpl.configMoon(false);
            Log.d(TAG, "(moon_mode) config moon night");
            ((Camera2Module) baseModule).updateMoonNight();
        }
    }

    static /* synthetic */ void lambda$configSilhouette$1(BaseModule baseModule) {
        if (baseModule instanceof Camera2Module) {
            ((Camera2Module) baseModule).updateSilhouette();
        }
    }

    static /* synthetic */ void lambda$updateAiScene$17(boolean z, BaseModule baseModule) {
        if ((baseModule instanceof Camera2Module) && z) {
            ((Camera2Module) baseModule).closeMoonMode(0, 8);
        }
    }

    public static void preload() {
        Log.i(TAG, "preload");
    }

    private void resetBeautyLevel() {
        ComponentRunningShine componentRunningShine = DataRepository.dataItemRunning().getComponentRunningShine();
        if (componentRunningShine.supportBeautyLevel()) {
            CameraSettings.setFaceBeautyLevel(0);
        } else if (componentRunningShine.supportSmoothLevel()) {
            CameraSettings.setFaceBeautySmoothLevel(0);
        }
    }

    private void trackFocusPeakChanged(boolean z) {
        CameraStatUtil.trackConfigChange(CameraStat.KEY_MANUAL_FOCUS_PEAK_CHANGED, CameraStat.PARAM_FOCUS_PEAK, z, false, false);
    }

    private void trackGenderAgeChanged(boolean z) {
        CameraStatUtil.trackConfigChange(CameraStat.KEY_GENDER_AGE_CHANGED, CameraStat.PARAM_GENDER_AGE, z, false, true);
    }

    private void trackGotoSettings() {
        BaseModule baseModule = (BaseModule) this.mActivity.getCurrentModule();
        if (baseModule != null) {
            CameraStatUtil.trackGotoSettings(baseModule.getModuleIndex());
        }
    }

    private void trackGradienterChanged(boolean z) {
        CameraStatUtil.trackConfigChange(CameraStat.KEY_GRADIENT_CHANGED, CameraStat.PARAM_GRADIENTER, z, true, false);
    }

    private void trackGroupChanged(boolean z) {
        CameraStatUtil.trackConfigChange(CameraStat.KEY_GROUP_SHOT_CHANGED, CameraStat.PARAM_GROUP_SHOT, z, false, true);
    }

    private void trackHHTChanged(boolean z) {
        CameraStatUtil.trackConfigChange(CameraStat.KEY_HHT_CHANGED, CameraStat.PARAM_HHT, z, true, false);
    }

    private void trackMagicMirrorChanged(boolean z) {
        CameraStatUtil.trackConfigChange(CameraStat.KEY_MAGIC_MIRROR_CHANGED, CameraStat.PARAM_MAGIC_MIRROR, z, false, true);
    }

    private void trackReferenceLineChanged(boolean z) {
        CameraStatUtil.trackConfigChange(CameraStat.KEY_REFERENCE_LINE_CHANGED, CameraStat.PARAM_REFERENCE_LINE, z, true, false);
    }

    private void trackSuperEISChanged(boolean z) {
        CameraStatUtil.trackConfigChange(CameraStat.KEY_SUPER_EIS_CHANGED, CameraStat.PARAM_SUPER_EIS, z, false, false);
    }

    private void trackSuperResolutionChanged(boolean z) {
        CameraStatUtil.trackConfigChange(CameraStat.KEY_SUPER_RESOLUTION_CHANGED, CameraStat.PARAM_SUPER_RESOLUTION, z, false, false);
    }

    private void trackUltraPixelPortraitChanged(boolean z) {
        CameraStatUtil.trackConfigChange(CameraStat.KEY_ULTRAPIXEL_PORTRAIT_CHANGED, CameraStat.PARAM_ULTRAPIXEL_PORTRAIT, z, false, false);
    }

    private void updateAiScene(boolean z) {
        DataRepository.dataItemGlobal().getCurrentMode();
        ComponentConfigAi componentConfigAi = DataRepository.dataItemConfig().getComponentConfigAi();
        if (!componentConfigAi.isEmpty() && componentConfigAi.isClosed() != z) {
            componentConfigAi.setClosed(z);
            getBaseModule().ifPresent(new Consumer(z) {
                private final /* synthetic */ boolean f$0;

                {
                    this.f$0 = r1;
                }

                public final void accept(Object obj) {
                    ConfigChangeImpl.lambda$updateAiScene$17(this.f$0, (BaseModule) obj);
                }
            });
            ((ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)).updateConfigItem(201);
        }
    }

    private void updateAutoZoom(boolean z) {
    }

    private void updateComponentBeauty(boolean z) {
        int currentMode = DataRepository.dataItemGlobal().getCurrentMode();
        ComponentConfigBeauty componentConfigBeauty = DataRepository.dataItemConfig().getComponentConfigBeauty();
        if (!componentConfigBeauty.isEmpty() && componentConfigBeauty.isClosed(currentMode) != z) {
            componentConfigBeauty.setClosed(z, currentMode);
            if (z) {
                ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
                if (miBeautyProtocol != null && miBeautyProtocol.isBeautyPanelShow()) {
                    miBeautyProtocol.dismiss(2);
                }
            }
            ModeProtocol.OnFaceBeautyChangedProtocol onFaceBeautyChangedProtocol = (ModeProtocol.OnFaceBeautyChangedProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(199);
            if (onFaceBeautyChangedProtocol != null) {
                onFaceBeautyChangedProtocol.onBeautyChanged(true);
            }
        }
    }

    private void updateComponentFilter(boolean z) {
        String str = TAG;
        Log.d(str, "updateComponentFilter: close = " + z);
        ComponentConfigFilter componentConfigFilter = DataRepository.dataItemRunning().getComponentConfigFilter();
        int currentMode = DataRepository.dataItemGlobal().getCurrentMode();
        if (!componentConfigFilter.isEmpty() && componentConfigFilter.isClosed(currentMode) != z) {
            componentConfigFilter.setClosed(z, currentMode);
            ((ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)).updateConfigItem(212);
            if (z) {
                ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
                if (miBeautyProtocol != null && miBeautyProtocol.isBeautyPanelShow()) {
                    miBeautyProtocol.dismiss(2);
                }
            }
        }
    }

    private void updateComponentFlash(String str, boolean z) {
        ComponentConfigFlash componentFlash = DataRepository.dataItemConfig().getComponentFlash();
        int currentMode = DataRepository.dataItemGlobal().getCurrentMode();
        if (!componentFlash.isEmpty() && componentFlash.isClosed() != z) {
            if (!z || !componentFlash.getComponentValue(currentMode).equals("2") || !SupportedConfigFactory.CLOSE_BY_BURST_SHOOT.equals(str)) {
                componentFlash.setClosed(z);
                ((ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)).updateConfigItem(193);
            }
        }
    }

    private void updateComponentHdr(boolean z) {
        ComponentConfigHdr componentHdr = DataRepository.dataItemConfig().getComponentHdr();
        DataRepository.dataItemGlobal().getCurrentMode();
        if (!componentHdr.isEmpty() && componentHdr.isClosed() != z) {
            componentHdr.setClosed(z);
            ((ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)).updateConfigItem(194);
        }
    }

    private void updateComponentShine(boolean z) {
        ComponentRunningShine componentRunningShine = DataRepository.dataItemRunning().getComponentRunningShine();
        if (!componentRunningShine.isEmpty() && componentRunningShine.isClosed() != z) {
            componentRunningShine.setClosed(z);
        }
    }

    private void updateEyeLight(boolean z) {
        ComponentRunningEyeLight componentRunningEyeLight = DataRepository.dataItemRunning().getComponentRunningEyeLight();
        if (componentRunningEyeLight.isClosed() != z) {
            componentRunningEyeLight.setClosed(z);
            String eyeLightType = CameraSettings.getEyeLightType();
            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            if (topAlert != null && bottomPopupTips != null) {
                if (!"-1".equals(eyeLightType)) {
                    topAlert.alertTopHint(0, (int) R.string.eye_light);
                    bottomPopupTips.showTips(10, EyeLightConstant.getString(eyeLightType), 4);
                    return;
                }
                topAlert.alertTopHint(8, (int) R.string.eye_light);
                bottomPopupTips.directlyHideTips();
            }
        }
    }

    private void updateFlashModeAndRefreshUI(BaseModule baseModule, String str) {
        int moduleIndex = baseModule.getModuleIndex();
        String str2 = TAG;
        Log.d(str2, "updateFlashModeAndRefreshUI flashMode = " + str);
        if (!TextUtils.isEmpty(str)) {
            CameraSettings.setFlashMode(moduleIndex, str);
        }
        if ("0".equals(str)) {
            if (CameraSettings.isFrontCamera()) {
                ToastUtils.showToast((Context) this.mActivity, (int) R.string.close_front_flash_toast);
            } else {
                ToastUtils.showToast((Context) this.mActivity, (int) R.string.close_back_flash_toast);
            }
        }
        if (!baseModule.isDoingAction() || "0".equals(str)) {
            baseModule.updatePreferenceInWorkThread(10);
        } else {
            baseModule.updatePreferenceTrampoline(10);
        }
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null) {
            topAlert.updateConfigItem(193);
        }
    }

    private void updateLiveShot(boolean z) {
        if (DataRepository.dataItemFeature().gz()) {
            int currentMode = DataRepository.dataItemGlobal().getCurrentMode();
            if (currentMode == 163 || currentMode == 165) {
                ComponentRunningLiveShot componentRunningLiveShot = DataRepository.dataItemRunning().getComponentRunningLiveShot();
                if (componentRunningLiveShot.isClosed() != z) {
                    componentRunningLiveShot.setClosed(z);
                    ((ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)).updateConfigItem(206);
                }
            }
        }
    }

    private void updateRaw(boolean z) {
        int currentMode = DataRepository.dataItemGlobal().getCurrentMode();
        ComponentConfigRaw componentConfigRaw = DataRepository.dataItemConfig().getComponentConfigRaw();
        if (!componentConfigRaw.isEmpty() && componentConfigRaw.isClosed(currentMode) != z) {
            componentConfigRaw.setClosed(z, currentMode);
        }
    }

    private void updateTipMessage(int i, @StringRes int i2, int i3) {
        ((ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175)).showTips(i, i2, i3);
    }

    private void updateUltraPixel(boolean z) {
        ComponentRunningUltraPixel componentUltraPixel = DataRepository.dataItemRunning().getComponentUltraPixel();
        if (!componentUltraPixel.isEmpty() && componentUltraPixel.isClosed() != z) {
            componentUltraPixel.setClosed(z);
        }
    }

    public void closeMutexElement(String str, int... iArr) {
        int[] iArr2 = new int[iArr.length];
        this.mRecordingClosedElements = iArr;
        for (int i = 0; i < iArr.length; i++) {
            switch (iArr[i]) {
                case 193:
                    updateComponentFlash(str, true);
                    iArr2[i] = 10;
                    break;
                case 194:
                    updateComponentHdr(true);
                    iArr2[i] = 11;
                    break;
                case 196:
                    updateComponentFilter(true);
                    iArr2[i] = 2;
                    break;
                case 201:
                    updateAiScene(true);
                    iArr2[i] = 36;
                    break;
                case 206:
                    updateLiveShot(true);
                    iArr2[i] = 49;
                    break;
                case 209:
                    updateUltraPixel(true);
                    iArr2[i] = 50;
                    break;
                case 212:
                    updateComponentShine(true);
                    iArr2[i] = 2;
                    break;
                case 237:
                    updateRaw(true);
                    iArr2[i] = 44;
                    break;
                case 239:
                    updateComponentBeauty(true);
                    iArr2[i] = 13;
                    break;
                case 253:
                    updateAutoZoom(true);
                    iArr2[i] = 51;
                    break;
                case 254:
                    updateEyeLight(true);
                    iArr2[i] = 45;
                    break;
                default:
                    throw new RuntimeException("unknown mutex element");
            }
        }
        getBaseModule().ifPresent(new Consumer(iArr2) {
            private final /* synthetic */ int[] f$0;

            {
                this.f$0 = r1;
            }

            public final void accept(Object obj) {
                ConfigChangeImpl.lambda$closeMutexElement$14(this.f$0, (BaseModule) obj);
            }
        });
    }

    public void configBackSoftLightSwitch(String str) {
        getBaseModule().ifPresent(new Consumer() {
            public final void accept(Object obj) {
                ConfigChangeImpl.lambda$configBackSoftLightSwitch$11(ConfigChangeImpl.this, (BaseModule) obj);
            }
        });
    }

    public void configBeautySwitch(int i) {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            int moduleIndex = baseModule.get().getModuleIndex();
            boolean z = moduleIndex == 162 || moduleIndex == 169;
            if (moduleIndex == 163 || moduleIndex == 165 || moduleIndex == 171 || moduleIndex == 161 || z) {
                ComponentConfigBeauty componentConfigBeauty = DataRepository.dataItemConfig().getComponentConfigBeauty();
                String nextValue = componentConfigBeauty.getNextValue(moduleIndex);
                boolean z2 = (!BeautyConstant.LEVEL_CLOSE.equals(componentConfigBeauty.getComponentValue(moduleIndex))) ^ (!BeautyConstant.LEVEL_CLOSE.equals(nextValue));
                componentConfigBeauty.setComponentValue(moduleIndex, nextValue);
                CameraStatUtil.trackBeautySwitchChanged(moduleIndex, nextValue);
                if (z2 && z) {
                    if (moduleIndex != 162) {
                        DataRepository.dataItemRunning().switchOff("pref_video_speed_fast_key");
                        CameraSettings.setAutoZoomEnabled(moduleIndex, false);
                        ((DataItemGlobal) DataRepository.provider().dataGlobal()).setCurrentMode(162);
                        DataRepository.getInstance().backUp().removeOtherVideoMode();
                        CameraStatUtil.trackVideoModeChanged("normal");
                    }
                    this.mActivity.onModeSelected(StartControl.create(162).setViewConfigType(2).setNeedBlurAnimation(true).setNeedReConfigureData(false).setNeedReConfigureCamera(true));
                } else if (!z2 || moduleIndex != 161) {
                    baseModule.get().updatePreferenceInWorkThread(13);
                } else {
                    this.mActivity.onModeSelected(StartControl.create(161).setViewConfigType(2).setNeedBlurAnimation(true).setNeedReConfigureData(false).setNeedReConfigureCamera(true));
                }
            }
        }
    }

    public void configBokeh(String str) {
        if (str.equals("on")) {
            updateTipMessage(4, R.string.bokeh_use_hint, 2);
        } else {
            hideTipMessage(R.string.bokeh_use_hint);
        }
        getBaseModule().ifPresent($$Lambda$ConfigChangeImpl$E7bu29Z5UMJsIrmnjXN4zScgdbw.INSTANCE);
    }

    public void configDualWaterMarkSwitch() {
        boolean isDualCameraWaterMarkOpen = CameraSettings.isDualCameraWaterMarkOpen();
        CameraStatUtil.trackDualWaterMarkChanged(!isDualCameraWaterMarkOpen);
        if (isDualCameraWaterMarkOpen) {
            hideTipMessage(R.string.hunt_dual_water_mark);
            CameraSettings.setDualCameraWaterMarkOpen(false);
        } else {
            updateTipMessage(4, R.string.hunt_dual_water_mark, 2);
            CameraSettings.setDualCameraWaterMarkOpen(true);
        }
        getBaseModule().ifPresent($$Lambda$ConfigChangeImpl$C2cxkmElsP_TRRKyWTxTNvIC2mc.INSTANCE);
    }

    public void configFPS960() {
        ComponentConfigSlowMotion componentConfigSlowMotion = DataRepository.dataItemConfig().getComponentConfigSlowMotion();
        String nextValue = componentConfigSlowMotion.getNextValue(172);
        componentConfigSlowMotion.setComponentValue(172, nextValue);
        this.mActivity.onModeSelected(StartControl.create(172).setViewConfigType(2).setNeedBlurAnimation(true).setNeedReConfigureData(false).setNeedReConfigureCamera(true));
        HashMap hashMap = new HashMap();
        hashMap.put(CameraStat.NEW_SLOW_MOTION_SWITCH_FPS, CameraStatUtil.slowMotionConfigToName(nextValue));
        CameraStat.recordCountEvent(CameraStat.CATEGORY_COUNTER, CameraStat.KEY_NEW_SLOW_MOTION, hashMap);
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (bottomPopupTips == null) {
            return;
        }
        if (ComponentConfigSlowMotion.DATA_CONFIG_NEW_SLOW_MOTION_960.equals(nextValue)) {
            bottomPopupTips.showTips(9, (int) R.string.fps960_toast, 4);
        } else {
            bottomPopupTips.hideTipImage();
        }
    }

    public void configFlash(String str) {
        if (!ModuleManager.isVideoNewSlowMotion()) {
            conflictWithFlashAndHdr();
        }
        getBaseModule().ifPresent($$Lambda$ConfigChangeImpl$uU5NlSthpXgrK7CLXW2ujx2dUfM.INSTANCE);
    }

    public void configFocusPeakSwitch(int i) {
        boolean state = getState(i, "pref_camera_peak_key");
        if (1 == i) {
            trackFocusPeakChanged(state);
        }
        if (DataRepository.dataItemConfig().getManuallyFocus().disableUpdate()) {
            EffectController.getInstance().setDrawPeaking(false);
        } else if (!state) {
            EffectController.getInstance().setDrawPeaking(state);
        } else if ("manual".equals(CameraSettings.getFocusMode())) {
            EffectController.getInstance().setDrawPeaking(state);
        }
    }

    public void configGenderAgeSwitch(int i) {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            boolean state = getState(i, "pref_camera_show_gender_age_key");
            if (1 == i) {
                trackGenderAgeChanged(state);
            }
            ((ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166)).setShowGenderAndAge(state);
            baseModule.get().updatePreferenceInWorkThread(38);
            if (state) {
                Camera2Proxy cameraDevice = baseModule.get().getCameraDevice();
                if (cameraDevice != null) {
                    String string = CameraAppImpl.getAndroidContext().getResources().getString(R.string.face_age_info);
                    cameraDevice.setFaceWaterMarkEnable(true);
                    cameraDevice.setFaceWaterMarkFormat(string);
                    return;
                }
                return;
            }
            Camera2Proxy cameraDevice2 = baseModule.get().getCameraDevice();
            if (cameraDevice2 != null) {
                cameraDevice2.setFaceWaterMarkEnable(false);
            }
        }
    }

    public void configGradienterSwitch(int i) {
        boolean state = getState(i, "pref_camera_gradienter_key");
        if (1 == i) {
            trackGradienterChanged(state);
        }
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            ((Camera2Module) baseModule.get()).onGradienterSwitched(state);
            ModeProtocol.MainContentProtocol mainContentProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
            if (mainContentProtocol != null) {
                mainContentProtocol.updateGradienterSwitched(state);
            }
            ((Camera2Module) baseModule.get()).showOrHideChip(!state);
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            if (bottomPopupTips != null) {
                bottomPopupTips.reConfigQrCodeTip();
            }
        }
    }

    public void configGroupSwitch(int i) {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            boolean state = getState(i, "pref_camera_groupshot_mode_key");
            if (1 == i) {
                trackGroupChanged(state);
            }
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            Camera2Module camera2Module = (Camera2Module) baseModule.get();
            camera2Module.showOrHideChip(!state);
            boolean isBeautyPanelShow = isBeautyPanelShow();
            if (state) {
                if (!isBeautyPanelShow) {
                    updateTipMessage(17, R.string.hint_groupshot, 2);
                }
                if (CameraSettings.shouldShowUltraWideStickyTip(camera2Module.getModuleIndex()) && !isBeautyPanelShow) {
                    bottomPopupTips.showTips(13, R.string.ultra_wide_open_tip, 4, 5000);
                }
                closeMutexElement(SupportedConfigFactory.CLOSE_BY_GROUP, 193, 194, 196, 201, 254);
            } else {
                restoreAllMutexElement(SupportedConfigFactory.CLOSE_BY_GROUP);
                hideTipMessage(R.string.hint_groupshot);
                if (CameraSettings.shouldShowUltraWideStickyTip(camera2Module.getModuleIndex()) && !isBeautyPanelShow) {
                    bottomPopupTips.directlyShowTips(13, R.string.ultra_wide_open_tip);
                }
            }
            camera2Module.onSharedPreferenceChanged();
            baseModule.get().updatePreferenceInWorkThread(42, 34, 30);
            bottomPopupTips.reConfigQrCodeTip();
        }
    }

    public void configHHTSwitch(int i) {
        boolean state = getState(i, "pref_camera_hand_night_key");
        if (1 == i) {
            trackHHTChanged(state);
        }
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            MutexModeManager mutexModePicker = baseModule.get().getMutexModePicker();
            if (state) {
                updateTipMessage(4, R.string.hine_hht, 2);
                closeMutexElement(SupportedConfigFactory.CLOSE_BY_HHT, 193, 194);
                mutexModePicker.setMutexModeMandatory(3);
                return;
            }
            hideTipMessage(R.string.hine_hht);
            mutexModePicker.clearMandatoryFlag();
            baseModule.get().resetMutexModeManually();
            restoreAllMutexElement(SupportedConfigFactory.CLOSE_BY_HHT);
        }
    }

    public void configHdr(String str) {
        conflictWithFlashAndHdr();
        getBaseModule().ifPresent($$Lambda$ConfigChangeImpl$2EZUIPoWqRY_kHeXtXEAYGJGmQ.INSTANCE);
        if ("off" != str && CameraSettings.isUltraPixelRearOn()) {
            configSwitchUltraPixel(3);
        }
        if ("off" != str && CameraSettings.isUltraPixelPortraitFrontOn()) {
            configUltraPixelPortrait(3);
        }
    }

    public void configLiveShotSwitch(int i) {
        if (isAlive()) {
            Optional<BaseModule> baseModule = getBaseModule();
            if (baseModule.isPresent()) {
                BaseModule baseModule2 = baseModule.get();
                if (baseModule2.isFrameAvailable()) {
                    if ((baseModule2.getModuleIndex() == 163 || baseModule2.getModuleIndex() == 165) && DataRepository.dataItemFeature().gz()) {
                        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                        if (topAlert != null) {
                            Camera2Module camera2Module = (Camera2Module) baseModule2;
                            if (i != 1) {
                                switch (i) {
                                    case 3:
                                    case 4:
                                        if (CameraSettings.isLiveShotOn()) {
                                            CameraSettings.setLiveShotOn(false);
                                            camera2Module.stopLiveShot(false);
                                            break;
                                        }
                                        break;
                                }
                            } else {
                                boolean isLiveShotOn = CameraSettings.isLiveShotOn();
                                CameraSettings.setLiveShotOn(!isLiveShotOn);
                                if (isLiveShotOn) {
                                    camera2Module.stopLiveShot(false);
                                    if (CameraSettings.isUltraPixelOn() || !DataRepository.dataItemConfig().getComponentConfigRatio().isSquareModule()) {
                                        topAlert.alertSwitchHint(1, (int) R.string.camera_liveshot_off_tip);
                                    } else {
                                        topAlert.alertSwitchHint(2, (int) R.string.camera_liveshot_off_tip);
                                        DataRepository.dataItemGlobal().setCurrentMode(165);
                                        this.mActivity.onModeSelected(StartControl.create(165).setViewConfigType(2).setNeedBlurAnimation(true).setNeedReConfigureCamera(true).setNeedReConfigureData(false));
                                    }
                                } else if (CameraSettings.isUltraPixelOn()) {
                                    Log.d(TAG, "Ignore #startLiveShot in ultra pixel photography mode");
                                } else if (camera2Module.getModuleIndex() == 165) {
                                    configRatio(true);
                                } else {
                                    camera2Module.startLiveShot();
                                    topAlert.alertSwitchHint(1, (int) R.string.camera_liveshot_on_tip);
                                }
                            }
                            topAlert.updateConfigItem(206);
                        }
                    }
                }
            }
        }
    }

    public void configLiveVV(VVItem vVItem, boolean z, boolean z2) {
        ((VMProcessing) DataRepository.dataItemObservable().get(VMProcessing.class)).reset();
        if (z) {
            ((ModeProtocol.LiveVVChooser) ModeCoordinatorImpl.getInstance().getAttachProtocol(229)).hide();
            ((ModeProtocol.LiveVVProcess) ModeCoordinatorImpl.getInstance().getAttachProtocol(230)).prepare(vVItem);
            DataRepository.dataItemLive().setCurrentVVItem(vVItem);
            changeMode(179);
            return;
        }
        if (z2) {
            configVV();
        } else {
            int i = 0;
            VVItem currentVVItem = DataRepository.dataItemLive().getCurrentVVItem();
            if (currentVVItem != null) {
                i = currentVVItem.index;
            }
            ((ModeProtocol.LiveVVProcess) ModeCoordinatorImpl.getInstance().getAttachProtocol(230)).quit();
            ((ModeProtocol.LiveVVChooser) ModeCoordinatorImpl.getInstance().getAttachProtocol(229)).show(i);
        }
        changeMode(162);
    }

    public void configMagicFocusSwitch() {
        trackMagicMirrorChanged(DataRepository.dataItemRunning().triggerSwitchAndGet("pref_camera_ubifocus_key"));
    }

    public void configMagicMirrorSwitch(int i) {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            boolean state = getState(i, "pref_camera_magic_mirror_key");
            if (1 == i) {
                trackMagicMirrorChanged(state);
            }
            ((ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166)).setShowMagicMirror(state);
            baseModule.get().updatePreferenceInWorkThread(39);
            if (state) {
                Camera2Proxy cameraDevice = baseModule.get().getCameraDevice();
                if (cameraDevice != null) {
                    String string = CameraAppImpl.getAndroidContext().getResources().getString(R.string.face_score_info);
                    cameraDevice.setFaceWaterMarkEnable(true);
                    cameraDevice.setFaceWaterMarkFormat(string);
                    return;
                }
                return;
            }
            Camera2Proxy cameraDevice2 = baseModule.get().getCameraDevice();
            if (cameraDevice2 != null) {
                cameraDevice2.setFaceWaterMarkEnable(false);
            }
        }
    }

    public void configMeter(String str) {
        reCheckParameterResetTip(false);
        getBaseModule().ifPresent($$Lambda$ConfigChangeImpl$EN1GiuB2wlLMsL6_MklXgSFfxk.INSTANCE);
    }

    public void configMiMovie(int i) {
        if (isAlive()) {
            Optional<BaseModule> baseModule = getBaseModule();
            if (baseModule.isPresent()) {
                BaseModule baseModule2 = baseModule.get();
                if (baseModule2.isFrameAvailable()) {
                    int moduleIndex = baseModule2.getModuleIndex();
                    boolean z = !CameraSettings.isMiMovieOpen(moduleIndex);
                    CameraSettings.setMiMovieOpen(moduleIndex, z);
                    CameraStatUtil.trackPreferenceChange("is_mimovie", z ? "on" : "off");
                    configRatio(true);
                    ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                    if (topAlert != null) {
                        topAlert.updateConfigItem(251);
                    }
                }
            }
        }
    }

    public void configPortraitSwitch(int i) {
        getBaseModule().ifPresent($$Lambda$ConfigChangeImpl$FRiRPwa4BZPdNoi5jo92p4UvKA.INSTANCE);
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    public void configRatio(boolean z) {
        String str;
        boolean z2;
        if (isAlive()) {
            Optional<BaseModule> baseModule = getBaseModule();
            if (baseModule.isPresent()) {
                BaseModule baseModule2 = baseModule.get();
                if (baseModule2.isFrameAvailable()) {
                    ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                    int moduleIndex = baseModule2.getModuleIndex();
                    ComponentConfigRatio componentConfigRatio = DataRepository.dataItemConfig().getComponentConfigRatio();
                    if (z) {
                        str = componentConfigRatio.getDefaultValue(moduleIndex);
                    } else {
                        String nextValue = componentConfigRatio.getNextValue(moduleIndex);
                        CameraStatUtil.trackPictureSize(moduleIndex, nextValue);
                        if (DataRepository.dataItemConfig().reConfigMovieIfRatioChanged(moduleIndex, nextValue) && topAlert != null) {
                            topAlert.updateConfigItem(251);
                        }
                        str = nextValue;
                    }
                    if (CameraSettings.isMiMovieOpen(moduleIndex)) {
                        str = ComponentConfigRatio.RATIO_16X9;
                        this.mRecordingClosedElements = DataRepository.dataItemRunning().getRecordingClosedElements();
                        restoreAllMutexElement(SupportedConfigFactory.CLOSE_BY_RATIO);
                        z = true;
                    }
                    char c = 65535;
                    int i = 2;
                    switch (str.hashCode()) {
                        case 50858:
                            if (str.equals(ComponentConfigRatio.RATIO_1X1)) {
                                c = 6;
                                break;
                            }
                            break;
                        case 53743:
                            if (str.equals(ComponentConfigRatio.RATIO_4X3)) {
                                c = 0;
                                break;
                            }
                            break;
                        case 1515430:
                            if (str.equals(ComponentConfigRatio.RATIO_16X9)) {
                                c = 1;
                                break;
                            }
                            break;
                        case 1517352:
                            if (str.equals(ComponentConfigRatio.RATIO_FULL_18X9)) {
                                c = 2;
                                break;
                            }
                            break;
                        case 1518313:
                            if (str.equals(ComponentConfigRatio.RATIO_FULL_19X9)) {
                                c = 3;
                                break;
                            }
                            break;
                        case 1539455:
                            if (str.equals(ComponentConfigRatio.RATIO_FULL_20X9)) {
                                c = 5;
                                break;
                            }
                            break;
                        case 1456894192:
                            if (str.equals(ComponentConfigRatio.RATIO_FULL_195X9)) {
                                c = 4;
                                break;
                            }
                            break;
                    }
                    int i2 = 165;
                    switch (c) {
                        case 0:
                            if (moduleIndex == 165 || moduleIndex == 163) {
                                updateLiveShot(false);
                                z2 = false;
                                i2 = 163;
                                break;
                            }
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                            if (moduleIndex == 165 || moduleIndex == 163) {
                                moduleIndex = 163;
                            }
                            i2 = moduleIndex;
                            break;
                        case 6:
                            componentConfigRatio.setComponentValue(moduleIndex, componentConfigRatio.getDefaultValue(moduleIndex));
                            if (moduleIndex == 165 || moduleIndex == 163) {
                                updateLiveShot(true);
                                break;
                            }
                        default:
                            i2 = moduleIndex;
                            z2 = false;
                            break;
                    }
                    z2 = true;
                    if (z2 && CameraSettings.isUltraPixelOn()) {
                        switchOffElementsSilent(209);
                    }
                    if (!z) {
                        componentConfigRatio.setComponentValue(i2, str);
                    }
                    DataRepository.dataItemGlobal().setCurrentMode(i2);
                    ActivityBase activityBase = this.mActivity;
                    StartControl viewConfigType = StartControl.create(i2).setViewConfigType(2);
                    if (!z) {
                        i = 7;
                    }
                    activityBase.onModeSelected(viewConfigType.setResetType(i).setNeedReConfigureData(false).setNeedBlurAnimation(true).setNeedReConfigureCamera(true));
                }
            }
        }
    }

    public void configRawSwitch(int i) {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            ComponentConfigRaw componentConfigRaw = DataRepository.dataItemConfig().getComponentConfigRaw();
            int moduleIndex = baseModule.get().getModuleIndex();
            boolean isSwitchOn = componentConfigRaw.isSwitchOn(moduleIndex);
            String str = TAG;
            Log.d(str, "configRawSwitch: " + (!isSwitchOn));
            switch (i) {
                case 1:
                    if (isSwitchOn) {
                        componentConfigRaw.setRaw(moduleIndex, false);
                        baseModule.get().restartModule();
                        CameraStatUtil.trackPreferenceChange("pref_camera_raw_key", "off");
                        reCheckRaw();
                        return;
                    }
                    componentConfigRaw.setRaw(moduleIndex, true);
                    if (DataRepository.dataItemFeature().hR()) {
                        closeMutexElement(SupportedConfigFactory.CLOSE_BY_RAW, 209);
                    }
                    baseModule.get().restartModule();
                    CameraStatUtil.trackPreferenceChange("pref_camera_raw_key", "on");
                    reCheckRaw();
                    return;
                default:
                    return;
            }
        }
    }

    public void configRotationChange(int i, int i2) {
        ModeProtocol.MainContentProtocol mainContentProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        boolean z = true;
        if (i2 != 0) {
            if (i2 != 90) {
                if (i2 == 180) {
                    if (mainContentProtocol != null) {
                        mainContentProtocol.updateLyingDirectHint(false, false);
                    }
                    if (topAlert != null) {
                        topAlert.updateLyingDirectHint(false, false);
                    }
                    if (bottomPopupTips != null) {
                        if (i != 1) {
                            z = false;
                        }
                        bottomPopupTips.updateLyingDirectHint(z, false);
                        return;
                    }
                    return;
                } else if (i2 != 270) {
                    return;
                }
            }
            if (topAlert != null) {
                topAlert.updateLyingDirectHint(false, false);
            }
            if (bottomPopupTips != null) {
                bottomPopupTips.updateLyingDirectHint(false, false);
            }
            if (mainContentProtocol != null) {
                if (i != 1) {
                    z = false;
                }
                mainContentProtocol.updateLyingDirectHint(z, false);
                return;
            }
            return;
        }
        if (mainContentProtocol != null) {
            mainContentProtocol.updateLyingDirectHint(false, false);
        }
        if (bottomPopupTips != null) {
            bottomPopupTips.updateLyingDirectHint(false, false);
        }
        if (topAlert != null) {
            if (i != 1) {
                z = false;
            }
            topAlert.updateLyingDirectHint(z, false);
        }
    }

    public void configScene(int i) {
        final Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            boolean state = getState(i, "pref_camera_scenemode_setting_key");
            ModeProtocol.ManuallyAdjust manuallyAdjust = (ModeProtocol.ManuallyAdjust) ModeCoordinatorImpl.getInstance().getAttachProtocol(181);
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
            if (state) {
                bottomPopupTips.hideTipImage();
                if (miBeautyProtocol != null && miBeautyProtocol.isBeautyPanelShow()) {
                    miBeautyProtocol.dismiss(2);
                }
                manuallyAdjust.setManuallyVisible(2, 1, new ManuallyListener() {
                    public void onManuallyDataChanged(ComponentData componentData, String str, String str2, boolean z, int i) {
                        ((BaseModule) baseModule.get()).onSharedPreferenceChanged();
                        ((BaseModule) baseModule.get()).updatePreferenceInWorkThread(4);
                    }
                });
            } else {
                bottomPopupTips.reInitTipImage();
                manuallyAdjust.setManuallyVisible(2, i == 1 ? 4 : 3, (ManuallyListener) null);
            }
            baseModule.get().onSharedPreferenceChanged();
            baseModule.get().updatePreferenceInWorkThread(4);
        }
    }

    public void configSuperResolutionSwitch(int i) {
        boolean state = getState(i, "pref_camera_super_resolution_key");
        if (1 == i) {
            trackSuperResolutionChanged(state);
        }
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            MutexModeManager mutexModePicker = baseModule.get().getMutexModePicker();
            if (state) {
                closeMutexElement(SupportedConfigFactory.CLOSE_BY_SUPER_RESOLUTION, 193, 194);
                mutexModePicker.setMutexModeMandatory(10);
                return;
            }
            mutexModePicker.clearMandatoryFlag();
            baseModule.get().resetMutexModeManually();
            restoreAllMutexElement(SupportedConfigFactory.CLOSE_BY_SUPER_RESOLUTION);
        }
    }

    public void configSwitchUltraPixel(int i) {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null && this.mActivity != null) {
            Optional<BaseModule> baseModule = getBaseModule();
            if (baseModule.isPresent()) {
                BaseModule baseModule2 = baseModule.get();
                int moduleIndex = baseModule2.getModuleIndex();
                boolean isUltraPixelOn = CameraSettings.isUltraPixelOn();
                boolean z = !isUltraPixelOn;
                ComponentRunningUltraPixel componentUltraPixel = DataRepository.dataItemRunning().getComponentUltraPixel();
                String currentSupportUltraPixel = componentUltraPixel.getCurrentSupportUltraPixel();
                boolean z2 = false;
                if (i == 1) {
                    if (CameraSettings.isUltraWideConfigOpen(moduleIndex)) {
                        CameraSettings.setUltraWideConfig(moduleIndex, false);
                        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
                        bottomPopupTips.updateLeftTipImage();
                        bottomPopupTips.directlyHideTips();
                    }
                    if (z) {
                        char c = 65535;
                        int hashCode = currentSupportUltraPixel.hashCode();
                        if (hashCode != -1379357773) {
                            switch (hashCode) {
                                case -70725169:
                                    if (currentSupportUltraPixel.equals(ComponentRunningUltraPixel.ULTRA_PIXEL_ON_REAR_48M)) {
                                        c = 0;
                                        break;
                                    }
                                    break;
                                case -70725168:
                                    if (currentSupportUltraPixel.equals(ComponentRunningUltraPixel.ULTRA_PIXEL_ON_REAR_108M)) {
                                        c = 2;
                                        break;
                                    }
                                    break;
                            }
                        } else if (currentSupportUltraPixel.equals(ComponentRunningUltraPixel.ULTRA_PIXEL_ON_FRONT_32M)) {
                            c = 1;
                        }
                        switch (c) {
                            case 0:
                                int[] iArr = {194, 239, 201, 206};
                                if (DataRepository.dataItemFeature().hR()) {
                                    iArr = Arrays.copyOf(iArr, iArr.length + 1);
                                    iArr[iArr.length - 1] = 237;
                                }
                                closeMutexElement(SupportedConfigFactory.CLOSE_BY_ULTRA_PIXEL, iArr);
                                break;
                            case 1:
                                closeMutexElement(SupportedConfigFactory.CLOSE_BY_ULTRA_PIXEL, 196, 201, 206);
                                break;
                            case 2:
                                if (DataRepository.dataItemFeature().hR()) {
                                    closeMutexElement(SupportedConfigFactory.CLOSE_BY_ULTRA_PIXEL, 237);
                                    break;
                                }
                                break;
                        }
                        DataRepository.dataItemRunning().setRecordingClosedElements(this.mRecordingClosedElements);
                        CameraSettings.switchOnUltraPixel(currentSupportUltraPixel);
                    } else {
                        this.mRecordingClosedElements = DataRepository.dataItemRunning().getRecordingClosedElements();
                        restoreAllMutexElement(SupportedConfigFactory.CLOSE_BY_ULTRA_PIXEL);
                        CameraSettings.switchOffUltraPixel();
                    }
                    if (baseModule2.getModuleIndex() == 165) {
                        changeMode(163);
                    } else if ((!z || DataRepository.dataItemRunning().getUiStyle() != 3) && (!isUltraPixelOn || DataRepository.dataItemRunning().getLastUiStyle() != 3)) {
                        baseModule2.restartModule();
                    } else {
                        changeMode(baseModule2.getModuleIndex());
                    }
                    if (z) {
                        topAlert.alertTopHint(0, componentUltraPixel.getUltraPixelOpenTip());
                    } else {
                        topAlert.alertTopHint(8, componentUltraPixel.getUltraPixelCloseTip());
                        topAlert.alertSwitchHint(1, componentUltraPixel.getUltraPixelCloseTip());
                    }
                } else if (i == 3 && isUltraPixelOn) {
                    this.mRecordingClosedElements = DataRepository.dataItemRunning().getRecordingClosedElements();
                    if (this.mRecordingClosedElements != null) {
                        restoreAllMutexElement(SupportedConfigFactory.CLOSE_BY_ULTRA_PIXEL);
                    }
                    CameraSettings.switchOffUltraPixel();
                    if (DataRepository.dataItemRunning().getLastUiStyle() == 3) {
                        changeMode(baseModule2.getModuleIndex());
                    } else {
                        baseModule2.restartModule();
                    }
                    topAlert.alertTopHint(8, componentUltraPixel.getUltraPixelCloseTip());
                }
                ModeProtocol.BottomPopupTips bottomPopupTips2 = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
                ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
                ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
                if (z) {
                    if (ComponentRunningUltraPixel.ULTRA_PIXEL_ON_REAR_48M.equals(currentSupportUltraPixel) && bottomPopupTips2 != null) {
                        bottomPopupTips2.directHideTipImage();
                        bottomPopupTips2.directShowOrHideLeftTipImage(false);
                        bottomPopupTips2.hideQrCodeTip();
                    }
                    if (dualController != null) {
                        dualController.hideZoomButton();
                        return;
                    }
                    return;
                }
                if (miBeautyProtocol != null) {
                    z2 = miBeautyProtocol.isBeautyPanelShow();
                }
                if (bottomPopupTips2 != null && !z2) {
                    bottomPopupTips2.reInitTipImage();
                }
                if (dualController != null && !z2) {
                    if (moduleIndex != 167) {
                        dualController.showZoomButton();
                    }
                    if (topAlert != null) {
                        topAlert.clearAlertStatus();
                    }
                }
            }
        }
    }

    public void configTiltSwitch(int i) {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            DataItemRunning dataItemRunning = DataRepository.dataItemRunning();
            boolean isSwitchOn = dataItemRunning.isSwitchOn("pref_camera_tilt_shift_mode");
            ComponentRunningTiltValue componentRunningTiltValue = dataItemRunning.getComponentRunningTiltValue();
            switch (i) {
                case 1:
                    boolean z = true;
                    if (!isSwitchOn) {
                        CameraStatUtil.trackTiltShiftChanged(ComponentRunningTiltValue.TILT_CIRCLE);
                        dataItemRunning.switchOn("pref_camera_tilt_shift_mode");
                        componentRunningTiltValue.setComponentValue(160, ComponentRunningTiltValue.TILT_CIRCLE);
                        isSwitchOn = true;
                    } else if (ComponentRunningTiltValue.TILT_CIRCLE.equals(componentRunningTiltValue.getComponentValue(160))) {
                        CameraStatUtil.trackTiltShiftChanged(ComponentRunningTiltValue.TILT_PARALLEL);
                        componentRunningTiltValue.setComponentValue(160, ComponentRunningTiltValue.TILT_PARALLEL);
                    } else {
                        CameraStatUtil.trackTiltShiftChanged("off");
                        dataItemRunning.switchOff("pref_camera_tilt_shift_mode");
                        isSwitchOn = false;
                    }
                    Camera2Module camera2Module = (Camera2Module) baseModule.get();
                    if (isSwitchOn) {
                        z = false;
                    }
                    camera2Module.showOrHideChip(z);
                    break;
                case 3:
                    dataItemRunning.switchOff("pref_camera_tilt_shift_mode");
                    isSwitchOn = false;
                    break;
            }
            ((Camera2Module) baseModule.get()).onTiltShiftSwitched(isSwitchOn);
            EffectController.getInstance().setDrawTilt(isSwitchOn);
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            if (bottomPopupTips != null) {
                bottomPopupTips.reConfigQrCodeTip();
            }
        }
    }

    public void configTimerSwitch() {
        ComponentRunningTimer componentRunningTimer = DataRepository.dataItemRunning().getComponentRunningTimer();
        String nextValue = componentRunningTimer.getNextValue();
        CameraStatUtil.trackTimerChanged(nextValue);
        componentRunningTimer.setComponentValue(160, nextValue);
    }

    public void configUltraPixelPortrait(int i) {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            DataItemRunning dataItemRunning = DataRepository.dataItemRunning();
            boolean isSwitchOn = dataItemRunning.isSwitchOn("pref_camera_ultra_pixel_portrait_mode_key");
            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            if (i == 1) {
                if (isSwitchOn) {
                    dataItemRunning.switchOff("pref_camera_ultra_pixel_portrait_mode_key");
                    topAlert.alertTopHint(8, (int) R.string.ultra_pixel_portrait_hint);
                    this.mRecordingClosedElements = DataRepository.dataItemRunning().getRecordingClosedElements();
                    restoreAllMutexElement(SupportedConfigFactory.CLOSE_BY_ULTRA_PIXEL_PORTRAIT);
                } else {
                    dataItemRunning.switchOn("pref_camera_ultra_pixel_portrait_mode_key");
                    closeMutexElement(SupportedConfigFactory.CLOSE_BY_ULTRA_PIXEL_PORTRAIT, 194, 196, 201, 239, 254);
                    dataItemRunning.setRecordingClosedElements(this.mRecordingClosedElements);
                    ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
                    if (miBeautyProtocol != null && miBeautyProtocol.isBeautyPanelShow()) {
                        miBeautyProtocol.dismiss(2);
                    }
                    topAlert.alertTopHint(0, (int) R.string.ultra_pixel_portrait_hint);
                }
                trackUltraPixelPortraitChanged(!isSwitchOn);
            } else if (i == 3 && isSwitchOn) {
                topAlert.alertTopHint(8, (int) R.string.ultra_pixel_portrait_hint);
                this.mRecordingClosedElements = DataRepository.dataItemRunning().getRecordingClosedElements();
                if (this.mRecordingClosedElements != null) {
                    restoreAllMutexElement(SupportedConfigFactory.CLOSE_BY_ULTRA_PIXEL_PORTRAIT);
                }
                dataItemRunning.switchOff("pref_camera_ultra_pixel_portrait_mode_key");
            }
            baseModule.get().updatePreferenceTrampoline(57);
            baseModule.get().getCameraDevice().resumePreview();
            ((ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175)).updateTipImage();
            topAlert.updateConfigItem(215);
        }
    }

    public void configVideoFast() {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            DataItemRunning dataItemRunning = DataRepository.dataItemRunning();
            int moduleIndex = baseModule.get().getModuleIndex();
            if (moduleIndex != 169) {
                CameraStatUtil.trackVideoModeChanged(CameraSettings.VIDEO_SPEED_FAST);
                switchOffElementsSilent(216);
                CameraSettings.setAutoZoomEnabled(moduleIndex, false);
                CameraSettings.setSuperEISEnabled(moduleIndex, false);
                resetBeautyLevel();
                if (CameraSettings.isMacroModeEnabled(moduleIndex)) {
                    DataRepository.dataItemRunning().getComponentRunningMacroMode().setSwitchOff(moduleIndex);
                }
                CameraSettings.setSubtitleEnabled(moduleIndex, false);
                changeMode(169);
                updateTipMessage(4, R.string.hint_fast_motion, 2);
                return;
            }
            hideTipMessage(R.string.hint_fast_motion);
            dataItemRunning.switchOff("pref_video_speed_fast_key");
            CameraStatUtil.trackVideoModeChanged("normal");
            DataRepository.dataItemGlobal().setCurrentMode(162);
            changeMode(162);
        }
    }

    public void configVideoQuality() {
        switchOffElementsSilent(216);
        ComponentConfigVideoQuality componentConfigVideoQuality = DataRepository.dataItemConfig().getComponentConfigVideoQuality();
        int currentMode = ((DataItemGlobal) DataRepository.provider().dataGlobal()).getCurrentMode();
        String nextValue = componentConfigVideoQuality.getNextValue(currentMode);
        CameraStatUtil.trackVideoQuality("pref_video_quality_key", CameraSettings.isFrontCamera(), nextValue);
        componentConfigVideoQuality.setComponentValue(160, nextValue);
        changeMode(currentMode);
    }

    public void onConfigChanged(int i) {
        if (isAlive()) {
            if (SupportedConfigFactory.isMutexConfig(i)) {
                DataItemRunning dataItemRunning = DataRepository.dataItemRunning();
                boolean z = false;
                int i2 = 176;
                for (int i3 : SupportedConfigFactory.MUTEX_MENU_CONFIGS) {
                    if (!(i3 == i || ((i == 209 && i3 == 229) || (i == 229 && i3 == 209)))) {
                        if (i3 != 203) {
                            if (i3 != 206) {
                                if (i3 != 209) {
                                    if (!dataItemRunning.isSwitchOn(SupportedConfigFactory.getConfigKey(i3))) {
                                    }
                                } else if (CameraSettings.isUltraPixelOn()) {
                                    z = true;
                                }
                            } else if (CameraSettings.isLiveShotOn()) {
                                if (i == 209) {
                                    i2 = 176;
                                }
                            }
                            i2 = i3;
                        } else if (((ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162)).isShowLightingView()) {
                            showOrHideLighting(false);
                        }
                    }
                }
                if (!z) {
                    if (i2 != 176) {
                        applyConfig(i2, 3);
                    }
                    applyConfig(i, 1);
                    return;
                }
                applyConfig(i, 1);
                if (i2 != 176) {
                    applyConfig(i2, 3);
                    return;
                }
                return;
            }
            applyConfig(i, 1);
        }
    }

    public void onThermalNotification(int i) {
        if (isAlive()) {
            Optional<BaseModule> baseModule = getBaseModule();
            if (!baseModule.isPresent()) {
                Log.w(TAG, "onThermalNotification current module is null");
                return;
            }
            BaseModule baseModule2 = baseModule.get();
            if (!baseModule2.isFrameAvailable() || baseModule2.isSelectingCapturedResult()) {
                Log.w(TAG, "onThermalNotification current module has not ready");
                return;
            }
            ComponentConfigFlash componentFlash = DataRepository.dataItemConfig().getComponentFlash();
            if (componentFlash.isEmpty() || !componentFlash.isHardwareSupported()) {
                Log.w(TAG, "onThermalNotification don't support hardware flash");
                return;
            }
            String str = "";
            if (ThermalDetector.thermalConstrained(i)) {
                Log.w(TAG, "thermalConstrained");
                str = "0";
            } else if (baseModule2.isThermalThreshold() && ((i == 2 && CameraSettings.isFrontCamera()) || i == 3)) {
                Log.w(TAG, "recording time is up to thermal threshold");
                str = "0";
            }
            updateFlashModeAndRefreshUI(baseModule2, str);
        }
    }

    public void reCheckBeauty() {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null && this.mActivity != null) {
            Optional<BaseModule> baseModule = getBaseModule();
            if (baseModule.isPresent()) {
                int moduleIndex = baseModule.get().getModuleIndex();
                if (moduleIndex == 162 && CameraSettings.isFaceBeautyOn(moduleIndex, (BeautyValues) null)) {
                    topAlert.alertTopHint(0, R.string.video_beauty_tip, 3000);
                }
            }
        }
    }

    public void reCheckEyeLight() {
        String eyeLightType = CameraSettings.getEyeLightType();
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (topAlert != null && bottomPopupTips != null && !"-1".equals(eyeLightType)) {
            topAlert.alertTopHint(0, (int) R.string.eye_light);
        }
    }

    public void reCheckFocusPeakConfig() {
        if (isAlive()) {
            Optional<BaseModule> baseModule = getBaseModule();
            if (baseModule.isPresent() && baseModule.get().isCreated() && DataRepository.dataItemRunning().isSwitchOn("pref_camera_peak_key")) {
                Log.d(TAG, "reCheckFocusPeakConfig: configFocusPeakSwitch");
                configFocusPeakSwitch(2);
            }
        }
    }

    public void reCheckFrontBokenTip() {
        if (DataRepository.dataItemFeature().hy() && ((ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)) != null) {
            Optional<BaseModule> baseModule = getBaseModule();
            if (baseModule.isPresent() && "on".equals(DataRepository.dataItemConfig().getComponentBokeh().getComponentValue(baseModule.get().getModuleIndex()))) {
                updateTipMessage(4, R.string.bokeh_use_hint, 2);
            }
        }
    }

    public void reCheckHandGesture() {
        if (getBaseModule().isPresent() && CameraSettings.isHandGestureOpen()) {
            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            if (topAlert != null) {
                topAlert.alertTopHint(0, (int) R.string.hand_gesture_tip);
            }
        }
    }

    public void reCheckLighting() {
        String componentValue = DataRepository.dataItemRunning().getComponentRunningLighting().getComponentValue(171);
        if (!componentValue.equals("0")) {
            ModeProtocol.ActionProcessing actionProcessing = (ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162);
            if (!actionProcessing.isShowLightingView()) {
                actionProcessing.showOrHideLightingView();
            }
            setLighting(false, "0", componentValue, false);
        }
    }

    public void reCheckLiveShot() {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            BaseModule baseModule2 = baseModule.get();
            if ((baseModule2.getModuleIndex() == 163 || baseModule2.getModuleIndex() == 165) && DataRepository.dataItemFeature().gz()) {
                ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                if (topAlert != null && CameraSettings.isLiveShotOn()) {
                    topAlert.alertSwitchHint(1, (int) R.string.camera_liveshot_on_tip);
                }
            }
        }
    }

    public void reCheckMacroMode() {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null) {
            Optional<BaseModule> baseModule = getBaseModule();
            if (baseModule.isPresent()) {
                BaseModule baseModule2 = baseModule.get();
                if ((baseModule2.getModuleIndex() == 163 || baseModule2.getModuleIndex() == 162 || baseModule2.getModuleIndex() == 165 || baseModule2.getModuleIndex() == 172) && !topAlert.isExtraMenuShowing() && CameraSettings.isMacroModeEnabled(baseModule2.getModuleIndex())) {
                    topAlert.alertTopHint(0, (int) R.string.macro_mode);
                }
            }
        }
    }

    public void reCheckMiMovie() {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            int moduleIndex = baseModule.get().getModuleIndex();
            if (moduleIndex == 171) {
                boolean isMiMovieOpen = CameraSettings.isMiMovieOpen(moduleIndex);
                baseModule.get().updatePreferenceInWorkThread(60);
                if (isMiMovieOpen) {
                    if (topAlert != null) {
                        topAlert.alertTopHint(0, R.string.config_name_mimovie, 3000);
                    }
                    if (bottomPopupTips != null) {
                        bottomPopupTips.showTips(21, (int) R.string.hint_mimovie, 6);
                    }
                }
            }
        }
    }

    public void reCheckMutexConfigs(int i) {
        if (isAlive()) {
            Optional<BaseModule> baseModule = getBaseModule();
            if (baseModule.isPresent() && baseModule.get().isCreated()) {
                DataItemRunning dataItemRunning = DataRepository.dataItemRunning();
                for (int i2 : SupportedConfigFactory.MUTEX_MENU_CONFIGS) {
                    if (i2 != 203) {
                        if (i2 != 209 && dataItemRunning.isSwitchOn(SupportedConfigFactory.getConfigKey(i2))) {
                            applyConfig(i2, 2);
                        }
                    } else if (dataItemRunning.getComponentRunningLighting().isSwitchOn(i)) {
                        reCheckLighting();
                    }
                }
            }
        }
    }

    public void reCheckParameterResetTip(boolean z) {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent() && baseModule.get().getModuleIndex() == 167) {
            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            if (topAlert != null && !topAlert.isExtraMenuShowing()) {
                if (!isChangeManuallyParameters()) {
                    topAlert.alertParameterResetTip(z, 8, R.string.reset_manually_parameter_hint);
                } else {
                    topAlert.alertParameterResetTip(z, 0, R.string.reset_manually_parameter_hint);
                }
            }
        }
    }

    public void reCheckRaw() {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null && this.mActivity != null) {
            Optional<BaseModule> baseModule = getBaseModule();
            if (baseModule.isPresent()) {
                int moduleIndex = baseModule.get().getModuleIndex();
                if (moduleIndex != 167 || topAlert.isExtraMenuShowing()) {
                    return;
                }
                if (DataRepository.dataItemConfig().getComponentConfigRaw().isSwitchOn(moduleIndex)) {
                    topAlert.alertVideoUltraClear(0, R.string.manually_raw_hint);
                } else {
                    topAlert.alertVideoUltraClear(8, R.string.manually_raw_hint);
                }
            }
        }
    }

    public void reCheckSubtitleMode() {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null) {
            Optional<BaseModule> baseModule = getBaseModule();
            if (baseModule.isPresent() && CameraSettings.isSubtitleEnabled(baseModule.get().getModuleIndex())) {
                topAlert.alertSubtitleHint(0, R.string.pref_video_subtitle);
            }
        }
    }

    public void reCheckSuperEIS() {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null && this.mActivity != null && getBaseModule().isPresent() && CameraSettings.isSuperEISEnabled(getBaseModule().get().getModuleIndex())) {
            topAlert.alertTopHint(0, (int) R.string.super_eis);
        }
    }

    public void reCheckUltraPixel() {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null && this.mActivity != null && getBaseModule().isPresent() && CameraSettings.isUltraPixelOn()) {
            topAlert.alertTopHint(0, DataRepository.dataItemRunning().getComponentUltraPixel().getUltraPixelOpenTip());
        }
    }

    public void reCheckUltraPixelPortrait() {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null && this.mActivity != null && getBaseModule().isPresent() && CameraSettings.isUltraPixelPortraitFrontOn()) {
            topAlert.alertTopHint(0, (int) R.string.ultra_pixel_portrait_hint);
        }
    }

    public void reCheckVideoUltraClearTip() {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            int moduleIndex = baseModule.get().getModuleIndex();
            if (moduleIndex == 162 || moduleIndex == 169) {
                CameraSize videoSize = ((VideoModule) baseModule.get()).getVideoSize();
                if (is4KQuality(videoSize.width, videoSize.height)) {
                    ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
                    if (baseDelegate == null || baseDelegate.getActiveFragment(R.id.bottom_action) != 65523) {
                        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                        if (topAlert != null && !topAlert.isExtraMenuShowing()) {
                            topAlert.alertVideoUltraClear(0, R.string.video_ultra_clear_tip);
                        }
                    }
                }
            }
        }
    }

    public void registerProtocol() {
        ModeCoordinatorImpl.getInstance().attachProtocol(164, this);
    }

    public void resetMeter() {
        ComponentConfigMeter componentConfigMeter = DataRepository.dataItemConfig().getComponentConfigMeter();
        if (componentConfigMeter.isModified(167)) {
            componentConfigMeter.reset(167);
            ((ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)).updateConfigItem(214);
            getBaseModule().ifPresent($$Lambda$ConfigChangeImpl$estzckYU8GOF6HnunJn2v0EYS8.INSTANCE);
        }
    }

    public void restoreAllMutexElement(String str) {
        if (this.mRecordingClosedElements != null) {
            int[] iArr = new int[this.mRecordingClosedElements.length];
            for (int i = 0; i < this.mRecordingClosedElements.length; i++) {
                switch (this.mRecordingClosedElements[i]) {
                    case 193:
                        updateComponentFlash((String) null, false);
                        iArr[i] = 10;
                        break;
                    case 194:
                        updateComponentHdr(false);
                        iArr[i] = 11;
                        break;
                    case 196:
                        updateComponentFilter(false);
                        iArr[i] = 2;
                        break;
                    case 201:
                        updateAiScene(false);
                        iArr[i] = 36;
                        break;
                    case 206:
                        updateLiveShot(false);
                        if (str == SupportedConfigFactory.CLOSE_BY_ULTRA_PIXEL) {
                            iArr[i] = 50;
                            break;
                        } else {
                            iArr[i] = 49;
                            break;
                        }
                    case 209:
                        updateUltraPixel(false);
                        iArr[i] = 50;
                        break;
                    case 212:
                        updateComponentShine(false);
                        iArr[i] = 2;
                        break;
                    case 237:
                        updateRaw(false);
                        iArr[i] = 44;
                        break;
                    case 239:
                        updateComponentBeauty(false);
                        iArr[i] = 13;
                        break;
                    case 253:
                        updateAutoZoom(false);
                        iArr[i] = 51;
                        break;
                    case 254:
                        updateEyeLight(false);
                        iArr[i] = 45;
                        break;
                    default:
                        throw new RuntimeException("unknown mutex element");
                }
            }
            this.mRecordingClosedElements = null;
            getBaseModule().ifPresent(new Consumer(iArr) {
                private final /* synthetic */ int[] f$0;

                {
                    this.f$0 = r1;
                }

                public final void accept(Object obj) {
                    ((BaseModule) obj).updatePreferenceInWorkThread(this.f$0);
                }
            });
        }
    }

    public void restoreMutexFlash(String str) {
        if (DataRepository.dataItemConfig().getComponentFlash().isClosed()) {
            updateComponentFlash(str, false);
            getBaseModule().ifPresent($$Lambda$ConfigChangeImpl$dwnyNY8Bd5Qy3HBC0mcPWR3H1Us.INSTANCE);
        }
    }

    public void setEyeLight(String str) {
        CameraSettings.setEyeLight(str);
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (bottomPopupTips != null) {
            bottomPopupTips.showTips(10, EyeLightConstant.getString(str), 4);
        }
        getBaseModule().ifPresent($$Lambda$ConfigChangeImpl$CprdW42ggnMBizEcx26gcfsCRsU.INSTANCE);
    }

    public void setFilter(int i) {
        EffectController.getInstance().setInvertFlag(0);
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (CameraSettings.isGroupShotOn()) {
            ((ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164)).configGroupSwitch(4);
            topAlert.refreshExtraMenu();
        }
        ModeProtocol.FilterProtocol filterProtocol = (ModeProtocol.FilterProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(165);
        String str = TAG;
        Log.d(str, "setFilter: filterId = " + i + ", FilterProtocol = " + filterProtocol);
        if (filterProtocol != null) {
            filterProtocol.onFilterChanged(FilterInfo.getCategory(i), FilterInfo.getIndex(i));
        }
        topAlert.updateConfigItem(212);
        if (CameraSettings.isUltraPixelFront32MPOn()) {
            configSwitchUltraPixel(3);
        }
    }

    public void setLighting(boolean z, String str, String str2, boolean z2) {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        ModeProtocol.VerticalProtocol verticalProtocol = (ModeProtocol.VerticalProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(198);
        if (str.equals("0") || str2.equals("0")) {
            topAlert.updateConfigItem(203);
            boolean equals = str2.equals("0");
            ModeProtocol.MainContentProtocol mainContentProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
            ModeProtocol.ActionProcessing actionProcessing = (ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162);
            if (equals) {
                if (!z) {
                    topAlert.alertLightingTitle(true);
                }
                mainContentProtocol.lightingCancel();
            } else {
                topAlert.alertLightingTitle(false);
                mainContentProtocol.lightingStart();
                actionProcessing.setLightingViewStatus(true);
            }
        }
        bottomPopupTips.setLightingPattern(str2);
        verticalProtocol.setLightingPattern(str2);
        if (str2 == "0") {
            topAlert.alertLightingHint(-1);
            verticalProtocol.alertLightingHint(-1);
        }
        getBaseModule().ifPresent($$Lambda$ConfigChangeImpl$pvRwBtYc3akLAIcvXmdBqw1jwWo.INSTANCE);
        if (z2) {
            CameraStatUtil.trackLightingChanged(171, str2);
        }
    }

    public void showCloseTip(int i, boolean z) {
        ((ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175)).showCloseTip(i, z);
    }

    public void showOrHideFilter() {
        if (((ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160)) != null) {
            ModeProtocol.ActionProcessing actionProcessing = (ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162);
            boolean isShowLightingView = actionProcessing.isShowLightingView();
            boolean showOrHideFilterView = actionProcessing.showOrHideFilterView();
            ModeProtocol.BokehFNumberController bokehFNumberController = (ModeProtocol.BokehFNumberController) ModeCoordinatorImpl.getInstance().getAttachProtocol(210);
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            if (showOrHideFilterView && isShowLightingView) {
                String componentValue = DataRepository.dataItemRunning().getComponentRunningLighting().getComponentValue(171);
                DataRepository.dataItemRunning().getComponentRunningLighting().setComponentValue(171, "0");
                setLighting(true, componentValue, "0", false);
                if (bottomPopupTips != null) {
                    bottomPopupTips.reInitTipImage();
                }
            }
            if (showOrHideFilterView && bokehFNumberController != null && DataRepository.dataItemGlobal().getCurrentMode() == 171) {
                bokehFNumberController.showFNumberPanel(true);
            }
            ModeProtocol.BottomPopupTips bottomPopupTips2 = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
            if (bottomPopupTips2 != null) {
                if (showOrHideFilterView) {
                    bottomPopupTips2.updateLeftTipImage();
                } else if (miBeautyProtocol == null || !miBeautyProtocol.isBeautyPanelShow()) {
                    bottomPopupTips2.updateLeftTipImage();
                }
                bottomPopupTips2.reConfigQrCodeTip();
            }
        }
    }

    public void showOrHideLighting(boolean z) {
        beautyMutexHandle();
        ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
        if (baseDelegate != null) {
            boolean showOrHideLightingView = ((ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162)).showOrHideLightingView();
            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            ModeProtocol.BokehFNumberController bokehFNumberController = (ModeProtocol.BokehFNumberController) ModeCoordinatorImpl.getInstance().getAttachProtocol(210);
            if (showOrHideLightingView) {
                reCheckLighting();
                Optional<BaseModule> baseModule = getBaseModule();
                if (baseModule.isPresent()) {
                    DataRepository.dataItemRunning().getComponentConfigFilter().reset(baseModule.get().getModuleIndex());
                    setFilter(FilterInfo.FILTER_ID_NONE);
                    topAlert.alertLightingTitle(true);
                    if (bokehFNumberController != null) {
                        bokehFNumberController.hideFNumberPanel(true, true);
                    }
                    bottomPopupTips.directHideTipImage();
                    topAlert.refreshExtraMenu();
                } else {
                    return;
                }
            } else {
                String componentValue = DataRepository.dataItemRunning().getComponentRunningLighting().getComponentValue(171);
                DataRepository.dataItemRunning().getComponentRunningLighting().setComponentValue(171, "0");
                setLighting(true, componentValue, "0", false);
                bottomPopupTips.reInitTipImage();
                topAlert.alertLightingTitle(false);
                if (bokehFNumberController != null) {
                    bokehFNumberController.showFNumberPanel(true);
                }
            }
            if (baseDelegate.getActiveFragment(R.id.bottom_action) == 251) {
                baseDelegate.delegateEvent(7);
            }
            if (z) {
                CameraStat.recordCountEvent(CameraStat.CATEGORY_COUNTER, CameraStat.KEY_LIGHTING_BUTTON);
            }
            ModeProtocol.BottomPopupTips bottomPopupTips2 = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            if (bottomPopupTips2 == null) {
                return;
            }
            if (showOrHideLightingView) {
                bottomPopupTips2.showCloseTip(2, true);
            } else {
                bottomPopupTips2.updateLeftTipImage();
            }
        }
    }

    public void showOrHideShine() {
        boolean z;
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            int moduleIndex = baseModule.get().getModuleIndex();
            boolean z2 = true;
            if (moduleIndex == 162) {
                z = false;
            } else if (moduleIndex != 169) {
                ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
                if (miBeautyProtocol != null && miBeautyProtocol.isBeautyPanelShow()) {
                    z2 = false;
                }
                if (z2) {
                    ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
                    if (bottomPopupTips != null) {
                        bottomPopupTips.directlyHideTips();
                        bottomPopupTips.setPortraitHintVisible(8);
                        bottomPopupTips.hideTipImage();
                        bottomPopupTips.hideLeftTipImage();
                        bottomPopupTips.hideSpeedTipImage();
                        bottomPopupTips.hideCenterTipImage();
                        bottomPopupTips.directHideLyingDirectHint();
                        bottomPopupTips.reConfigQrCodeTip();
                    }
                    ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
                    if (dualController != null) {
                        dualController.hideZoomButton();
                        if (moduleIndex != 171) {
                            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                            if (topAlert != null) {
                                topAlert.alertUpdateValue(2);
                            }
                        }
                    }
                    if (moduleIndex == 163) {
                        ModeProtocol.ManuallyAdjust manuallyAdjust = (ModeProtocol.ManuallyAdjust) ModeCoordinatorImpl.getInstance().getAttachProtocol(181);
                        if (manuallyAdjust != null) {
                            manuallyAdjust.setManuallyVisible(0, 4, (ManuallyListener) null);
                        }
                    } else if (moduleIndex == 167) {
                        ModeProtocol.ManuallyAdjust manuallyAdjust2 = (ModeProtocol.ManuallyAdjust) ModeCoordinatorImpl.getInstance().getAttachProtocol(181);
                        if (manuallyAdjust2 != null) {
                            manuallyAdjust2.setManuallyLayoutVisible(false);
                        }
                    } else if (moduleIndex == 171) {
                        ModeProtocol.BokehFNumberController bokehFNumberController = (ModeProtocol.BokehFNumberController) ModeCoordinatorImpl.getInstance().getAttachProtocol(210);
                        if (bokehFNumberController != null && bokehFNumberController.isFNumberVisible()) {
                            bokehFNumberController.hideFNumberPanel(false, false);
                        }
                    }
                    ((ModeProtocol.BottomMenuProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(197)).expandShineBottomMenu(DataRepository.dataItemRunning().getComponentRunningShine());
                    if (miBeautyProtocol != null) {
                        miBeautyProtocol.show();
                        return;
                    }
                    ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
                    if (baseDelegate != null) {
                        baseDelegate.delegateEvent(2);
                        return;
                    }
                    return;
                }
                miBeautyProtocol.dismiss(2);
                return;
            } else {
                closeVideoFast();
                z = true;
            }
            boolean z3 = !CameraSettings.isFaceBeautyOn(moduleIndex, (BeautyValues) null);
            ComponentRunningShine componentRunningShine = DataRepository.dataItemRunning().getComponentRunningShine();
            if (z3) {
                DataRepository.dataItemConfig().getComponentConfigBeauty().setClosed(false, moduleIndex);
                switchOffElementsSilent(216);
                if (CameraSettings.isAutoZoomEnabled(moduleIndex)) {
                    HybridZoomingSystem.clearZoomRatioHistory();
                    CameraSettings.setAutoZoomEnabled(moduleIndex, false);
                    ModeProtocol.BottomPopupTips bottomPopupTips2 = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
                    bottomPopupTips2.updateLeftTipImage();
                    bottomPopupTips2.updateTipImage();
                    ((ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)).hideSwitchHint();
                }
                if (CameraSettings.isSuperEISEnabled(moduleIndex)) {
                    HybridZoomingSystem.clearZoomRatioHistory();
                    CameraSettings.setSuperEISEnabled(moduleIndex, false);
                    ModeProtocol.BottomPopupTips bottomPopupTips3 = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
                    bottomPopupTips3.updateLeftTipImage();
                    bottomPopupTips3.updateTipImage();
                    ((ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)).hideSwitchHint();
                }
                ComponentRunningMacroMode componentRunningMacroMode = DataRepository.dataItemRunning().getComponentRunningMacroMode();
                if (componentRunningMacroMode.isSwitchOn(moduleIndex)) {
                    HybridZoomingSystem.clearZoomRatioHistory();
                    componentRunningMacroMode.setSwitchOff(moduleIndex);
                }
                if (componentRunningShine.supportBeautyLevel()) {
                    CameraSettings.setFaceBeautyLevel(3);
                } else if (componentRunningShine.supportSmoothLevel()) {
                    CameraSettings.setFaceBeautySmoothLevel(40);
                }
            } else if (componentRunningShine.supportBeautyLevel()) {
                CameraSettings.setFaceBeautyLevel(0);
            } else if (componentRunningShine.supportSmoothLevel()) {
                CameraSettings.setFaceBeautySmoothLevel(0);
            }
            if (z) {
                changeMode(162);
                return;
            }
            ModeProtocol.OnFaceBeautyChangedProtocol onFaceBeautyChangedProtocol = (ModeProtocol.OnFaceBeautyChangedProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(199);
            if (onFaceBeautyChangedProtocol != null) {
                onFaceBeautyChangedProtocol.onBeautyChanged(false);
            }
        }
    }

    public void showSetting() {
        ActivityBase activityBase = this.mActivity;
        if (activityBase != null) {
            switchOffElementsSilent(216);
            Intent intent = new Intent();
            intent.setClass(activityBase, CameraPreferenceActivity.class);
            intent.putExtra(BasePreferenceActivity.FROM_WHERE, DataRepository.dataItemGlobal().getCurrentMode());
            if (DataRepository.dataItemGlobal().getIntentType() == 1) {
                intent.putExtra(CameraPreferenceActivity.IS_IMAGE_CAPTURE_INTENT, true);
            }
            intent.putExtra(":miui:starting_window_label", activityBase.getResources().getString(R.string.pref_camera_settings_category));
            if (activityBase.startFromKeyguard()) {
                intent.putExtra(CameraIntentManager.EXTRA_START_WHEN_LOCKED, true);
            }
            activityBase.getIntent().removeExtra(CameraIntentManager.EXTRAS_CAMERA_FACING);
            activityBase.startActivity(intent);
            activityBase.setJumpFlag(2);
            trackGotoSettings();
        }
    }

    public void switchOffElementsSilent(int... iArr) {
        for (int i : iArr) {
            if (i == 209) {
                this.mRecordingClosedElements = DataRepository.dataItemRunning().getRecordingClosedElements();
                if (this.mRecordingClosedElements != null) {
                    restoreAllMutexElement(SupportedConfigFactory.CLOSE_BY_ULTRA_PIXEL);
                }
                CameraSettings.switchOffUltraPixel();
            } else if (i == 216) {
                ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
                if (baseDelegate != null && baseDelegate.getActiveFragment(R.id.bottom_action) == 65523) {
                    baseDelegate.delegateEvent(15);
                }
            }
        }
    }

    public void unRegisterProtocol() {
        this.mActivity = null;
        ModeCoordinatorImpl.getInstance().detachProtocol(164, this);
    }
}
