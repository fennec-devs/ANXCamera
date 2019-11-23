package com.ss.android.vesdk.utils;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import java.io.File;

public class VETextUtils {
    public static String emptyIfNull(@Nullable String str) {
        return str == null ? "" : str;
    }

    public static String nullIfEmpty(@Nullable String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return str;
    }

    public static String parsePathSimpleName(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        String[] split = str.split(File.separator);
        return split.length > 0 ? split[split.length - 1] : "";
    }
}
