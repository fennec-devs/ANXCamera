package com.android.camera.module.impl.component;

import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.ss.android.vesdk.TERecorder;

public class LiveBeautyChangeImpl implements ModeProtocol.OnFaceBeautyChangedProtocol {
    private TERecorder mRecorder;

    public LiveBeautyChangeImpl(TERecorder tERecorder) {
        this.mRecorder = tERecorder;
    }

    public void onBeautyChanged(boolean z) {
        this.mRecorder.setBeautyFace(3, FileUtils.BEAUTY_12_DIR);
        this.mRecorder.setBeautyFaceIntensity(0.35f, 0.35f);
    }

    public void registerProtocol() {
        ModeCoordinatorImpl.getInstance().attachProtocol(199, this);
    }

    public void unRegisterProtocol() {
        ModeCoordinatorImpl.getInstance().detachProtocol(199, this);
    }
}
