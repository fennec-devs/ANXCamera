package com.ss.android.medialib.config;

import android.support.annotation.RestrictTo;
import com.ss.android.medialib.VideoSdkCore;
import com.ss.android.medialib.util.SharedPrefUtil;
import java.util.Map;

public class PerformanceConfig {
    public static final int DISABLE = 2;
    public static final int ENABLE = 1;
    public static final String KEY_USE_OPENSL = "use_opensl";
    private static final String TAG = "PerformanceConfig";
    public static final int UNDEFINED = 0;
    private static int useOpenSL = 2;

    private static void fromMap(Map<String, Integer> map) {
        int config = getConfig(map, KEY_USE_OPENSL);
        if (config == 0) {
            config = 2;
        }
        useOpenSL = config;
    }

    private static int getConfig(Map<String, Integer> map, String str) {
        if (map == null) {
            return 0;
        }
        Integer num = map.get(str);
        if (num == null) {
            return 0;
        }
        return num.intValue();
    }

    public static boolean isUseOpenSL() {
        return useOpenSL == 1;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static void restoreFromCache() {
        if (VideoSdkCore.APPLICATION_CONTEXT != null) {
            fromMap(SharedPrefUtil.getPerformanceConfig(VideoSdkCore.APPLICATION_CONTEXT));
            return;
        }
        throw new IllegalStateException("Must call VideoSdkCore.init() before.");
    }

    public static void updateConfig(Map<String, Integer> map) {
        fromMap(map);
        SharedPrefUtil.setPerformanceConfig(VideoSdkCore.APPLICATION_CONTEXT, map);
    }
}
