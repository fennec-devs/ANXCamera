package com.xiaomi.mediaprocess;

import android.util.Log;
import java.util.Map;

public class MediaEffect {
    private static String TAG = "MediaEffect";

    public static long CreateEffect(EffectType effectType) {
        long CreateEffectJni = CreateEffectJni(effectType.ordinal());
        String str = TAG;
        Log.d(str, "create effect, type id: " + effectType + ", effect id:" + CreateEffectJni);
        return CreateEffectJni;
    }

    public static native long CreateEffectJni(int i);

    public static void DestoryEffect(long j) {
        DestoryEffectJni(j);
        String str = TAG;
        Log.d(str, "destory effect id: " + j);
    }

    public static native void DestoryEffectJni(long j);

    public static void SetCoverCallback(long j, EffectCoverNotifier effectCoverNotifier) {
        Log.d(TAG, "set EffectCoverCallback");
        SetCoverCallbackJni(j, effectCoverNotifier);
    }

    public static native void SetCoverCallbackJni(long j, EffectCoverNotifier effectCoverNotifier);

    public static boolean SetParamsForAudioTrack(long j, Map<String, String> map) {
        String[] strArr;
        String str = TAG;
        Log.d(str, "set param for audio track  id: " + j);
        int i = 0;
        if (map == null || map.size() == 0) {
            Log.d(TAG, "Param Map: <null, null>");
            strArr = new String[0];
        } else {
            strArr = new String[(map.size() * 2)];
            for (String next : map.keySet()) {
                String str2 = TAG;
                Log.d(str2, "Param Map: <" + next + ", " + map.get(next) + ">");
                int i2 = 2 * i;
                strArr[i2] = next.toLowerCase();
                strArr[i2 + 1] = map.get(next);
                i++;
            }
        }
        return SetParamsForAudioTrackJni(j, strArr);
    }

    public static native boolean SetParamsForAudioTrackJni(long j, String[] strArr);

    public static boolean SetParamsForEffect(EffectType effectType, long j, Map<String, String> map) {
        String[] strArr;
        String str = TAG;
        Log.d(str, "set param for effect, effect type: " + effectType + ", effect id: " + j);
        int i = 0;
        if (map == null || map.size() == 0) {
            Log.d(TAG, "Param Map: <null, null>");
            strArr = new String[0];
        } else {
            strArr = new String[(map.size() * 2)];
            for (String next : map.keySet()) {
                String str2 = TAG;
                Log.d(str2, "Param Map: <" + next + ", " + map.get(next) + ">");
                int i2 = 2 * i;
                strArr[i2] = next.toLowerCase();
                strArr[i2 + 1] = map.get(next);
                i++;
            }
        }
        return SetParamsForEffectJni(effectType.ordinal(), j, strArr);
    }

    public static native boolean SetParamsForEffectJni(int i, long j, String[] strArr);
}
