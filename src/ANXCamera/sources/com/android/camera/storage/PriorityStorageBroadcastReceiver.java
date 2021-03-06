package com.android.camera.storage;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import com.android.camera.CameraAppImpl;
import com.android.camera.R;

public class PriorityStorageBroadcastReceiver extends BroadcastReceiver {
    public static boolean isPriorityStorage() {
        Context androidContext = CameraAppImpl.getAndroidContext();
        int componentEnabledSetting = androidContext.getPackageManager().getComponentEnabledSetting(new ComponentName(androidContext, PriorityStorageBroadcastReceiver.class));
        return componentEnabledSetting == 0 ? androidContext.getResources().getBoolean(R.bool.priority_storage) : componentEnabledSetting == 1;
    }

    public static void setPriorityStorage(boolean z) {
        Context androidContext = CameraAppImpl.getAndroidContext();
        androidContext.getPackageManager().setComponentEnabledSetting(new ComponentName(androidContext, PriorityStorageBroadcastReceiver.class), z ? 1 : 2, 1);
    }

    public void onReceive(Context context, Intent intent) {
    }
}
