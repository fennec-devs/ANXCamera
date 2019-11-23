package com.ss.android.ttve.utils;

import com.ss.android.vesdk.VELogUtil;
import java.lang.reflect.Method;

public class ReflectUtil {
    private static final String TAG = "ReflectUtil";

    private static Method findMethod(Class<? extends Object> cls, String str, Object[] objArr) {
        for (Method method : cls.getDeclaredMethods()) {
            if (method.getName().equals(str) && matches(method.getParameterTypes(), objArr)) {
                return method;
            }
        }
        Class<? super Object> superclass = cls.getSuperclass();
        if (superclass != null) {
            return findMethod(superclass, str, objArr);
        }
        return null;
    }

    public static <T> T invoke(Object obj, String str, Object[] objArr) {
        try {
            Method findMethod = findMethod(obj.getClass(), str, objArr);
            findMethod.setAccessible(true);
            return findMethod.invoke(obj, objArr);
        } catch (Exception e) {
            VELogUtil.w(TAG, "couldn't invoke " + str + " on " + obj + ", " + e);
            return null;
        }
    }

    public static <T> T invokeStatic(String str, String str2, Object[] objArr) {
        try {
            Method findMethod = findMethod(Class.forName(str), str2, objArr);
            findMethod.setAccessible(true);
            return findMethod.invoke((Object) null, objArr);
        } catch (Exception e) {
            VELogUtil.w(TAG, "couldn't invoke " + str2 + ", " + e);
            return null;
        }
    }

    private static boolean matches(Class<?>[] clsArr, Object[] objArr) {
        if (clsArr == null || clsArr.length == 0) {
            return objArr == null || objArr.length == 0;
        }
        if (objArr == null || clsArr.length != objArr.length) {
            return false;
        }
        for (int i = 0; i < clsArr.length; i++) {
            if (objArr[i] != null && !clsArr[i].isAssignableFrom(objArr[i].getClass())) {
                return false;
            }
        }
        return true;
    }
}
