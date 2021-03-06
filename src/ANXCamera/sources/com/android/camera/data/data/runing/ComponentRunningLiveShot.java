package com.android.camera.data.data.runing;

import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import java.util.List;

public class ComponentRunningLiveShot extends ComponentData {
    private static final String TAG = "ComponentRunningLiveShot";
    private boolean mIsClosed;

    public ComponentRunningLiveShot(DataItemRunning dataItemRunning) {
        super(dataItemRunning);
    }

    public String getDefaultValue(int i) {
        throw new UnsupportedOperationException(TAG + "#getDefaultValue() not supported");
    }

    public int getDisplayTitleString() {
        throw new UnsupportedOperationException(TAG + "#getDisplayTitleString() not supported");
    }

    public List<ComponentDataItem> getItems() {
        throw new UnsupportedOperationException(TAG + "#getItems() not supported");
    }

    public String getKey(int i) {
        throw new UnsupportedOperationException(TAG + "#getKey() not supported");
    }

    public boolean isClosed() {
        return this.mIsClosed;
    }

    public boolean isLiveShotOn() {
        if (isClosed()) {
            return false;
        }
        return this.mParentDataItem.getBoolean("pref_live_shot_enabled", false);
    }

    public void setClosed(boolean z) {
        this.mIsClosed = z;
    }

    public void setLiveShotOn(boolean z) {
        setClosed(false);
        this.mParentDataItem.putBoolean("pref_live_shot_enabled", z);
    }
}
