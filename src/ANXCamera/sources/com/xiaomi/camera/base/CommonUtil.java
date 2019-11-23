package com.xiaomi.camera.base;

import android.content.Context;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Build;
import android.util.Log;
import com.xiaomi.protocol.ICustomCaptureResult;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public final class CommonUtil {
    private static final String CAPTURE_RESULT_EXTRA_CLASS = "android.hardware.camera2.impl.CaptureResultExtras";
    private static final String PHYSICAL_CAPTURE_RESULT_CLASS = "android.hardware.camera2.impl.PhysicalCaptureResultInfo";
    private static final String TAG = "CommonUtil";

    private CommonUtil() {
    }

    public static Object getCameraMetaDataCopy(Object obj) {
        try {
            Class<?> cls = Class.forName("android.hardware.camera2.impl.CameraMetadataNative");
            return cls.getDeclaredConstructor(new Class[]{cls}).newInstance(new Object[]{obj});
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            Log.e(TAG, "getCameraMetaDataCopy: failed", e);
            return null;
        }
    }

    private static boolean saveCameraCalibrationToFile(Context context, byte[] bArr, String str) {
        boolean z = false;
        if (!(bArr == null || context == null)) {
            FileOutputStream fileOutputStream = null;
            try {
                FileOutputStream openFileOutput = context.openFileOutput(str, 0);
                try {
                    openFileOutput.write(bArr);
                    z = true;
                    try {
                        openFileOutput.flush();
                        openFileOutput.close();
                    } catch (Exception e) {
                        Log.w(TAG, "saveCameraCalibrationToFile: failed!", e);
                    }
                } catch (FileNotFoundException e2) {
                    e = e2;
                    fileOutputStream = openFileOutput;
                    Log.w(TAG, "saveCameraCalibrationToFile: FileNotFoundException", e);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    return z;
                } catch (IOException e3) {
                    e = e3;
                    fileOutputStream = openFileOutput;
                    try {
                        Log.w(TAG, "saveCameraCalibrationToFile: IOException", e);
                        fileOutputStream.flush();
                        fileOutputStream.close();
                        return z;
                    } catch (Throwable th) {
                        th = th;
                        try {
                            fileOutputStream.flush();
                            fileOutputStream.close();
                        } catch (Exception e4) {
                            Log.w(TAG, "saveCameraCalibrationToFile: failed!", e4);
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    fileOutputStream = openFileOutput;
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    throw th;
                }
            } catch (FileNotFoundException e5) {
                e = e5;
                Log.w(TAG, "saveCameraCalibrationToFile: FileNotFoundException", e);
                fileOutputStream.flush();
                fileOutputStream.close();
                return z;
            } catch (IOException e6) {
                e = e6;
                Log.w(TAG, "saveCameraCalibrationToFile: IOException", e);
                fileOutputStream.flush();
                fileOutputStream.close();
                return z;
            }
        }
        return z;
    }

    public static boolean saveCameraCalibrationToFile(Context context, byte[] bArr, boolean z) {
        return saveCameraCalibrationToFile(context, bArr, z ? "front_dual_camera_caldata.bin" : "back_dual_camera_caldata.bin");
    }

    public static TotalCaptureResult toTotalCaptureResult(ICustomCaptureResult iCustomCaptureResult, int i) {
        Constructor<?> constructor;
        Object obj;
        try {
            int sequenceId = iCustomCaptureResult.getSequenceId();
            long frameNumber = iCustomCaptureResult.getFrameNumber();
            Log.d(TAG, "toTotalCaptureResult: " + i + "|" + sequenceId + "|" + frameNumber);
            Class<?> cls = Class.forName(CAPTURE_RESULT_EXTRA_CLASS);
            if (Build.VERSION.SDK_INT >= 29) {
                constructor = cls.getDeclaredConstructor(new Class[]{Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Long.TYPE, Integer.TYPE, Integer.TYPE, String.class});
                obj = constructor.newInstance(new Object[]{Integer.valueOf(sequenceId), 0, 0, 0, Long.valueOf(frameNumber), 0, 0, null});
            } else {
                constructor = cls.getDeclaredConstructor(new Class[]{Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Long.TYPE, Integer.TYPE, Integer.TYPE});
                obj = constructor.newInstance(new Object[]{Integer.valueOf(sequenceId), 0, 0, 0, Long.valueOf(frameNumber), 0, 0});
            }
            Constructor<?>[] declaredConstructors = TotalCaptureResult.class.getDeclaredConstructors();
            int length = declaredConstructors.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    break;
                }
                Constructor<?> constructor2 = declaredConstructors[i2];
                if (constructor2.getParameters().length > 2) {
                    constructor = constructor2;
                    break;
                }
                i2++;
            }
            Object cameraMetaDataCopy = getCameraMetaDataCopy(iCustomCaptureResult.getResults());
            if (cameraMetaDataCopy == null) {
                Log.e(TAG, "null native metadata", new RuntimeException());
                return null;
            } else if (Build.VERSION.SDK_INT < 28) {
                return (TotalCaptureResult) constructor.newInstance(new Object[]{cameraMetaDataCopy, iCustomCaptureResult.getRequest(), obj, null, Integer.valueOf(i)});
            } else {
                return (TotalCaptureResult) constructor.newInstance(new Object[]{cameraMetaDataCopy, iCustomCaptureResult.getRequest(), obj, null, Integer.valueOf(i), Array.newInstance(Class.forName(PHYSICAL_CAPTURE_RESULT_CLASS), 0)});
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "null capture result!", new RuntimeException());
            return null;
        }
    }
}
