package com.android.camera.module.impl;

import com.android.camera.ActivityBase;
import com.android.camera.module.impl.component.BackStackImpl;
import com.android.camera.module.impl.component.BeautyChangeImpl;
import com.android.camera.module.impl.component.BeautyRecordingImpl;
import com.android.camera.module.impl.component.CameraClickObservableImpl;
import com.android.camera.module.impl.component.ConfigChangeImpl;
import com.android.camera.module.impl.component.LiveConfigChangeTTImpl;
import com.android.camera.module.impl.component.LiveSubVVImpl;
import com.android.camera.module.impl.component.LiveVideoEditorTTImpl;
import com.android.camera.module.impl.component.ManuallyValueChangeImpl;
import com.android.camera.module.impl.component.MimojiAvatarEngineImpl;
import com.android.camera.module.impl.component.RecordingStateChangeImpl;
import com.android.camera.protocol.ModeProtocol;
import java.util.ArrayList;
import java.util.List;

public class ImplFactory {
    private List<ModeProtocol.BaseProtocol> mAdditionalProtocolList;
    private List<ModeProtocol.BaseProtocol> mBaseProtocolList;
    private List<ModeProtocol.BaseProtocol> mPersistentProtocolList;
    private boolean mReleased;

    private void detach(List<ModeProtocol.BaseProtocol> list) {
        if (!this.mReleased && list != null) {
            for (ModeProtocol.BaseProtocol unRegisterProtocol : list) {
                unRegisterProtocol.unRegisterProtocol();
            }
            list.clear();
        }
    }

    private void initTypes(ActivityBase activityBase, List<ModeProtocol.BaseProtocol> list, int... iArr) {
        ModeProtocol.BaseProtocol baseProtocol;
        if (!this.mReleased) {
            for (int i : iArr) {
                switch (i) {
                    case 164:
                        baseProtocol = ConfigChangeImpl.create(activityBase);
                        break;
                    case 171:
                        baseProtocol = BackStackImpl.create(activityBase);
                        break;
                    case 173:
                        baseProtocol = BeautyRecordingImpl.create();
                        break;
                    case 174:
                        baseProtocol = ManuallyValueChangeImpl.create(activityBase);
                        break;
                    case 199:
                        baseProtocol = BeautyChangeImpl.create(activityBase);
                        break;
                    case 201:
                        baseProtocol = LiveConfigChangeTTImpl.create(activityBase);
                        break;
                    case 209:
                        baseProtocol = LiveVideoEditorTTImpl.create(activityBase);
                        break;
                    case 212:
                        baseProtocol = RecordingStateChangeImpl.create(activityBase);
                        break;
                    case 217:
                        baseProtocol = MimojiAvatarEngineImpl.create(activityBase);
                        break;
                    case 227:
                        baseProtocol = CameraClickObservableImpl.create();
                        break;
                    case 228:
                        baseProtocol = LiveSubVVImpl.create(activityBase);
                        break;
                    default:
                        throw new RuntimeException("unknown protocol type");
                }
                baseProtocol.registerProtocol();
                list.add(baseProtocol);
            }
        }
    }

    public void detachAdditional() {
        detach(this.mAdditionalProtocolList);
    }

    public void detachBase() {
        detach(this.mBaseProtocolList);
    }

    public void detachModulePersistent() {
        detach(this.mPersistentProtocolList);
    }

    public void initAdditional(ActivityBase activityBase, int... iArr) {
        if (this.mAdditionalProtocolList == null) {
            this.mAdditionalProtocolList = new ArrayList();
        }
        initTypes(activityBase, this.mAdditionalProtocolList, iArr);
    }

    public void initBase(ActivityBase activityBase, int... iArr) {
        if (this.mBaseProtocolList == null) {
            this.mBaseProtocolList = new ArrayList();
        }
        initTypes(activityBase, this.mBaseProtocolList, iArr);
    }

    public void initModulePersistent(ActivityBase activityBase, int... iArr) {
        if (this.mPersistentProtocolList == null) {
            this.mPersistentProtocolList = new ArrayList();
        }
        initTypes(activityBase, this.mPersistentProtocolList, iArr);
    }

    @Deprecated
    public void release() {
        if (!this.mReleased) {
            detachAdditional();
            detachModulePersistent();
            detachBase();
            this.mReleased = true;
        }
    }
}
