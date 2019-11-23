package com.xiaomi.camera.base;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureResult;
import android.os.Parcelable;
import com.android.camera.log.Log;
import com.xiaomi.protocol.ICustomCaptureResult;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class CameraDeviceUtil {
    private static final String TAG = CameraDeviceUtil.class.getSimpleName();

    private CameraDeviceUtil() {
    }

    public static int getCameraCombinationMode(int i) {
        if (i == 40) {
            return 18;
        }
        switch (i) {
            case 0:
                return 1;
            case 1:
                return 17;
            case 2:
                return 2;
            case 3:
                return 513;
            case 4:
                return 769;
            default:
                switch (i) {
                    case 20:
                        return 2;
                    case 21:
                        return 3;
                    default:
                        switch (i) {
                            case 60:
                                return 513;
                            case 61:
                                return 769;
                            case 62:
                                return 514;
                            case 63:
                                return 770;
                            default:
                                switch (i) {
                                    case 80:
                                        return 515;
                                    case 81:
                                        return 771;
                                    default:
                                        return 0;
                                }
                        }
                }
        }
    }

    public static ICustomCaptureResult getCustomCaptureResult(CaptureResult captureResult) {
        try {
            Method method = captureResult.getClass().getMethod("getNativeCopy", new Class[0]);
            method.setAccessible(true);
            Parcelable parcelable = (Parcelable) method.invoke(captureResult, new Object[0]);
            String str = TAG;
            Log.d(str, "getCustomCaptureResult: cameraMetadataNative =" + parcelable);
            ICustomCaptureResult iCustomCaptureResult = new ICustomCaptureResult();
            iCustomCaptureResult.setFrameNumber(captureResult.getFrameNumber());
            iCustomCaptureResult.setRequest(captureResult.getRequest());
            iCustomCaptureResult.setSequenceId(captureResult.getSequenceId());
            iCustomCaptureResult.setResults(parcelable);
            Long l = (Long) captureResult.get(CaptureResult.SENSOR_TIMESTAMP);
            if (l != null) {
                iCustomCaptureResult.setTimeStamp(l.longValue());
            }
            String str2 = TAG;
            Log.d(str2, "getCustomCaptureResult: " + iCustomCaptureResult);
            return iCustomCaptureResult;
        } catch (Exception e) {
            Log.e(TAG, "getCustomCaptureResult: getCustomCaptureResult", e);
            return null;
        }
    }

    public static void prepareCalibrationDataForAlgo(Context context, String str) {
        try {
            CameraCharacteristics cameraCharacteristics = ((CameraManager) context.getSystemService("camera")).getCameraCharacteristics(str);
            Integer num = (Integer) cameraCharacteristics.get(CameraCharacteristics.LENS_FACING);
            if (num != null) {
                boolean z = num.intValue() == 0;
                try {
                    byte[] bArr = (byte[]) cameraCharacteristics.get((CameraCharacteristics.Key) Class.forName("android.hardware.camera2.CameraCharacteristics$Key").getDeclaredConstructor(new Class[]{String.class, Class.class}).newInstance(new Object[]{"com.xiaomi.camera.algoup.dualCalibrationData", byte[].class}));
                    if (bArr != null) {
                        CommonUtil.saveCameraCalibrationToFile(context, bArr, z);
                    }
                } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
                    Log.e(TAG, "prepareCalibrationDataForAlgo: call reflect method failed!", e);
                    throw new RuntimeException("getCameraCharacteristics's dualCalibrationData failed");
                }
            }
        } catch (CameraAccessException e2) {
            Log.e(TAG, "prepareCalibrationDataForAlgo: get getCameraCharacteristics failed!", e2);
        }
    }
}
