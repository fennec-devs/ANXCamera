package com.android.camera.data.data.config;

import android.text.TextUtils;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.data.provider.DataProvider;
import com.android.camera.log.Log;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import java.util.ArrayList;
import java.util.List;

public class ComponentManuallyDualLens extends ComponentData {
    public static final String LENS_MACRO = "macro";
    public static final String LENS_STANDALONE = "Standalone";
    public static final String LENS_TELE = "tele";
    public static final String LENS_ULTRA = "ultra";
    public static final String LENS_WIDE = "wide";
    private static final String TAG = "DualLens";

    public ComponentManuallyDualLens(DataItemConfig dataItemConfig) {
        super(dataItemConfig);
    }

    private int indexOf(String str) {
        List<ComponentDataItem> items = getItems();
        if (items == null || TextUtils.isEmpty(str)) {
            return -1;
        }
        for (int i = 0; i < items.size(); i++) {
            if (TextUtils.equals(items.get(i).mValue, str)) {
                return i;
            }
        }
        return -1;
    }

    private List<ComponentDataItem> initItems() {
        ArrayList arrayList = new ArrayList();
        if (DataRepository.dataItemFeature().isSupportUltraWide()) {
            arrayList.add(new ComponentDataItem(-1, -1, (int) R.string.pref_camera_zoom_mode_entry_ultra, LENS_ULTRA));
        }
        arrayList.add(new ComponentDataItem(-1, -1, (int) R.string.pref_camera_zoom_mode_entry_wide, LENS_WIDE));
        if (CameraSettings.isSupportedOpticalZoom()) {
            if (DataRepository.dataItemFeature().hW()) {
                arrayList.add(new ComponentDataItem(-1, -1, (int) R.string.pref_camera_zoom_mode_entry_tele_2X, LENS_TELE));
            } else if (Camera2DataContainer.getInstance().getAuxCameraId() >= 0) {
                arrayList.add(new ComponentDataItem(-1, -1, (int) R.string.pref_camera_zoom_mode_entry_tele, LENS_TELE));
            }
        }
        if (DataRepository.dataItemFeature().hW()) {
            arrayList.add(new ComponentDataItem(-1, -1, (int) R.string.pref_camera_zoom_mode_entry_tele_standalone, LENS_STANDALONE));
        }
        if (DataRepository.dataItemFeature().hH() && DataRepository.dataItemFeature().hL()) {
            arrayList.add(new ComponentDataItem(-1, -1, (int) R.string.pref_camera_zoom_mode_entry_macro, "macro"));
        }
        return arrayList;
    }

    /* access modifiers changed from: protected */
    public boolean checkValueValid(String str) {
        for (ComponentDataItem componentDataItem : getItems()) {
            if (TextUtils.equals(str, componentDataItem.mValue)) {
                return true;
            }
        }
        Log.d(TAG, "checkValueValid: invalid value: " + str);
        return false;
    }

    public String getDefaultValue(int i) {
        return LENS_WIDE;
    }

    public int getDisplayTitleString() {
        return R.string.pref_camera_zoom_mode_title;
    }

    public List<ComponentDataItem> getItems() {
        if (this.mItems == null) {
            this.mItems = initItems();
        }
        return this.mItems;
    }

    public String getKey(int i) {
        return i != 167 ? CameraSettings.KEY_CAMERA_ZOOM_MODE : CameraSettings.KEY_CAMERA_MANUALLY_LENS;
    }

    public String next(String str, int i) {
        int indexOf = indexOf(str);
        List<ComponentDataItem> items = getItems();
        if (items == null) {
            return LENS_WIDE;
        }
        return items.get(indexOf == items.size() + -1 ? 0 : indexOf + 1).mValue;
    }

    /* access modifiers changed from: protected */
    public void resetComponentValue(int i) {
        this.mItems = initItems();
        setComponentValue(i, getDefaultValue(i));
    }

    public void resetLensType(ComponentConfigUltraWide componentConfigUltraWide, DataProvider.ProviderEditor providerEditor) {
        String componentValue = getComponentValue(167);
        if (!LENS_WIDE.equals(componentValue)) {
            providerEditor.putString(getKey(167), LENS_WIDE);
            if (LENS_ULTRA.equals(componentValue)) {
                providerEditor.putString(componentConfigUltraWide.getKey(167), "OFF");
            }
        }
    }
}
