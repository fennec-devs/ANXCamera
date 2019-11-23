package com.mi.config;

import com.android.camera.data.DataRepository;
import java.util.Collections;
import java.util.Map;
import miui.util.FeatureParser;

@Deprecated
/* compiled from: FeatureParserWrapper */
public class d {
    public static final String th = "camera_supported_asd";
    public static final String tk = "support_zoom_mfnr";
    public static final String wA = "support_camera_shader_effect";
    public static final String wB = "support_camera_burst_shoot";
    public static final String wC = "support_camera_burst_shoot_denoise";
    public static final String wD = "burst_shoot_count";
    public static final String wE = "support_camera_movie_solid";
    public static final String wF = "support_camera_skin_beauty";
    public static final String wG = "support_camera_age_detection";
    public static final String wH = "support_camera_record_location";
    public static final String wI = "support_camera_water_mark";
    public static final String wJ = "support_camera_new_style_time_water_mark";
    public static final String wK = "support_camera_video_pause";
    public static final String wL = "support_camera_boost_brightness";
    public static final String wM = "is_lower_size_effect";
    public static final String wN = "support_camera_aohdr";
    public static final String wO = "support_camera_hfr";
    public static final String wP = "support_chroma_flash";
    public static final String wQ = "support_object_track";
    public static final String wR = "support_camera_4k_quality";
    public static final String wS = "support_camera_ubifocus";
    public static final String wT = "camera_supported_scene";
    public static final String wU = "camera_supported_ai_scene";
    public static final String wV = "support_camera_audio_focus";
    public static final String wW = "is_camera_use_morpho_lib";
    public static final String wX = "is_camera_replace_higher_cost_effect";
    public static final String wY = "support_camera_manual_function";
    public static final String wZ = "support_camera_press_down_capture";
    public static final String wq = "is_xiaomi";
    public static final String wr = "is_hongmi";
    public static final String wt = "is_pad";
    public static final String wu = "vendor";
    public static final String wv = "support_dual_sd_card";
    public static final String ww = "support_edge_handgrip";
    public static final String wx = "camera_continuous_shot_callback_class";
    public static final String wy = "camera_continuous_shot_callback_setter";
    public static final String wz = "fp_nav_event_name_list";
    public static final String xA = "is_front_remosic_sensor";
    public static final String xB = "front_fingerprint_sensor";
    public static final String xC = "cmcc_strategic_phone";
    public static final String xD = "is_need_force_recycle_effect";
    public static final String xE = "is_18x9_ratio_screen";
    public static final String xF = "camera_extra_picture_size";
    public static final String xG = "is_support_tele_asd_night";
    public static final String xH = "is_front_video_quality_1080p";
    public static final String xI = "is_capture_stop_face_detection";
    public static final String xJ = "is_video_snapshot_size_limit";
    public static final String xK = "is_surface_size_limit";
    public static final String xL = "is_hal_does_caf_when_flash_on";
    public static final String xM = "is_new_hdr_param_key_used";
    public static final String xN = "is_hrf_video_capture_support";
    public static final String xO = "is_support_stereo";
    public static final String xP = "is_support_optical_zoom";
    public static final String xQ = "is_support_portrait";
    public static final String xR = "camera_is_support_portrait_front";
    public static final String xS = "is_support_fhd_fhr";
    public static final String xT = "is_rgb888_egl_prefer";
    public static final String xU = "is_legacy_face_beauty";
    public static final String xV = "use_legacy_normal_filter";
    public static final String xW = "camera_hibernation_timeout_in_minutes";
    public static final String xX = "support_front_hht_enhance";
    public static final String xY = "support_screen_light";
    public static final String xZ = "support_parallel_process";
    public static final String xa = "support_camera_torch_capture";
    public static final String xb = "is_camera_freeze_after_hdr_capture";
    public static final String xc = "is_camera_face_detection_need_orientation";
    public static final String xd = "is_camera_hold_blur_background";
    public static final String xe = "support_camera_peaking_mf";
    public static final String xf = "support_camera_gradienter";
    public static final String xg = "is_camera_lower_qrscan_frequency";
    public static final String xh = "is_camera_preview_with_subthread_looper";
    public static final String xi = "camera_reduce_preview_flag";
    public static final String xj = "camera_focus_success_flag";
    public static final String xk = "camera_exposure_compensation_steps_num";
    public static final String xl = "is_camera_app_water_mark";
    public static final String xm = "support_camera_tilt_shift";
    public static final String xn = "support_camera_magic_mirror";
    public static final String xo = "support_camera_quick_snap";
    public static final String xp = "camera_front_count_down_margin";
    public static final String xq = "support_camera_groupshot";
    public static final String xr = "is_full_size_effect";
    public static final String xs = "support_camera_face_info_water_mark";
    public static final String xt = "support_camera_square_mode";
    public static final String xu = "is_camera_use_still_effect_image";
    public static final String xv = "support_front_flash";
    public static final String xw = "support_video_front_flash";
    public static final String xx = "is_camera_isp_rotated";
    public static final String xy = "support_full_size_panorama";
    public static final String xz = "support_hfr_video_pause";
    public static final String ya = "support_psensor_pocket_mode";
    public static final String yb = "support_front_beauty_mfnr";
    public static final String yc = "support_video_hfr_mode";
    public static final String yd = "support_3d_face_beauty";
    public static final String ye = "support_mi_face_beauty";
    public static final String yf = "support_lens_dirty_detect";
    public static final String yg = "enable_algorithm_in_file_suffix";
    public static final String yh = "support_camera_dynamic_light_spot";
    public static final String yi = "support_super_resolution";
    public static final String yj = "support_realtime_manual_exposure_time";
    public static final String yk = "support_picture_watermark";
    public static final String yl = "support_camera_role";
    public static final String ym = "sensor_has_latency";
    private static final Map<String, String> yn = Collections.unmodifiableMap(new FeatureParserWrapper$1());

    private static String R(String str) {
        return yn.get(str);
    }

    public static boolean getBoolean(String str, boolean z) {
        String R = R(str);
        return (R == null || !DataRepository.dataItemFeature().L(R)) ? FeatureParser.getBoolean(str, z) : DataRepository.dataItemFeature().getBoolean(R, z);
    }

    public static Float getFloat(String str, float f) {
        String R = R(str);
        return (R == null || !DataRepository.dataItemFeature().L(R)) ? FeatureParser.getFloat(str, f) : Float.valueOf(DataRepository.dataItemFeature().getFloat(R, f));
    }

    public static int getInteger(String str, int i) {
        String R = R(str);
        return (R == null || !DataRepository.dataItemFeature().L(R)) ? FeatureParser.getInteger(str, i) : DataRepository.dataItemFeature().getInt(R, i);
    }

    public static String getString(String str) {
        String R = R(str);
        return (R == null || !DataRepository.dataItemFeature().L(R)) ? FeatureParser.getString(str) : DataRepository.dataItemFeature().getString(R, "N/A");
    }

    public static String[] getStringArray(String str) {
        return FeatureParser.getStringArray(str);
    }
}
