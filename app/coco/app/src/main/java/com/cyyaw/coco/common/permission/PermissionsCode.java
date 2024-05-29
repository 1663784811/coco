package com.cyyaw.coco.common.permission;


import android.Manifest;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.view.accessibility.AccessibilityManager;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

/**
 * 受权码
 */
@RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
public enum PermissionsCode {

    READ_CONTACTS(Manifest.permission.READ_CONTACTS, 111, "读取联系人信息", null, false)
    ,SYSTEM_ALERT_WINDOW(Manifest.permission.SYSTEM_ALERT_WINDOW, 222,"开启浮窗", Settings.ACTION_MANAGE_OVERLAY_PERMISSION, false)

    ,BIND_ACCESSIBILITY_SERVICE(Manifest.permission.BIND_ACCESSIBILITY_SERVICE, 333,"无障碍服务AccessibilityService", Settings.ACTION_ACCESSIBILITY_SETTINGS, false)

    ,CAMERA(Manifest.permission.CAMERA, 444,"摄像头", null, false)
    ,READ_MEDIA_AUDIO(Manifest.permission.READ_MEDIA_AUDIO, 555,"音频", null, false)
    ,READ_MEDIA_IMAGES(Manifest.permission.READ_MEDIA_IMAGES, 666,"图片", null, false)
    ,READ_MEDIA_VIDEO(Manifest.permission.READ_MEDIA_VIDEO, 777,"视频", null, false)


    ,READ_EXTERNAL_STORAGE(Manifest.permission.READ_EXTERNAL_STORAGE, 888,"读文件", Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, true)
    ,WRITE_EXTERNAL_STORAGE(Manifest.permission.WRITE_EXTERNAL_STORAGE, 999,"文件写入", Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, true)


    ,ACCESS_FINE_LOCATION(Manifest.permission.ACCESS_FINE_LOCATION, 1000,"精确定位GPS", null, false)
    ,ACCESS_COARSE_LOCATION(Manifest.permission.ACCESS_COARSE_LOCATION, 1100,"粗略定位CellID或WiFi", null, false)
    ,ACCESS_BACKGROUND_LOCATION(Manifest.permission.ACCESS_BACKGROUND_LOCATION, 1200,"后台定位权限", null, false)

    ,BLUETOOTH_CONNECT(Manifest.permission.BLUETOOTH_CONNECT, 1300,"蓝牙连接", null, false)


    ;

    /**
     * 系统权限
     */
    private String permissions;
    /**
     * 自定议受权码
     */
    private int code;

    /**
     * 说明
     */
    private String note;

    /**
     * 是否需要打开系统Activity 不需要为则NULL
     */
    private String sysActivity;

    /**
     * 是否需要包名
     */
    private Boolean needPackage;

    /**
     * 受权成功回调
     */
    private PermissionsSuccessCallback successCallback;

    /**
     * 受权失败回调
     */
    private PermissionsErrorCallback errorCallback;

    /**
     * 其它
     */
//    private Object other;
    // ===========================================================================================

    PermissionsCode(String permissions, int code, String note, String sysActivity, Boolean needPackage) {
        this.permissions = permissions;
        this.code = code;
        this.note = note;
        this.sysActivity = sysActivity;
        this.needPackage = needPackage;
    }


    // ===========================================================================================
    public static PermissionsCode getPermissionsCode(String permissions) {
        PermissionsCode[] values = PermissionsCode.values();
        for (int i = 0; i < values.length; i++) {
            if (values[i].getPermissions().equals(permissions)) {
                return values[i];
            }
        }
        return null;
    }
    // ===========================================================================================


    public Boolean getNeedPackage() {
        return needPackage;
    }

    public void setNeedPackage(Boolean needPackage) {
        this.needPackage = needPackage;
    }

    public String getSysActivity() {
        return sysActivity;
    }

    public String getPermissions() {
        return permissions;
    }

    public int getCode() {
        return code;
    }

    public String getNote() {
        return note;
    }

    public PermissionsSuccessCallback getSuccessCallback() {
        return successCallback;
    }

    public PermissionsErrorCallback getErrorCallback() {
        return errorCallback;
    }

    public void setSuccessCallback(PermissionsSuccessCallback successCallback) {
        this.successCallback = successCallback;
    }

    public void setErrorCallback(PermissionsErrorCallback errorCallback) {
        this.errorCallback = errorCallback;
    }

    /**
     * 受权成功
     */
    public static interface PermissionsSuccessCallback {
        void run();
    }

    /**
     * 受权失败
     */
    public static interface PermissionsErrorCallback {
        void run();
    }


    /**
     * 判断是否已经受权
     */
    public static boolean alreadyPermissions(Context context, PermissionsCode permissionsCode) {
        String permissions = permissionsCode.getPermissions();
        // 正常
        if (permissions.equals(Manifest.permission.SYSTEM_ALERT_WINDOW) && Settings.canDrawOverlays(context)) {
            // 浮窗
            return true;
        } else if (permissions.equals(Manifest.permission.BIND_ACCESSIBILITY_SERVICE) && isAccessibilityServiceEnabled(context)) {
            // 无障碍服务AccessibilityService
            return true;
        } else if (permissions.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE) || permissions.equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                return Environment.isExternalStorageManager();
            } else {
                return false;
            }
        } else {
            return ContextCompat.checkSelfPermission(context, permissions) == PackageManager.PERMISSION_GRANTED;
        }
    }

    /**
     * 判断是否有无障碍权限
     */
    private static boolean isAccessibilityServiceEnabled(Context context) {
        AccessibilityManager accessibilityManager = (AccessibilityManager)context.getSystemService(Context.ACCESSIBILITY_SERVICE);
        if (accessibilityManager != null) {
            for (AccessibilityServiceInfo serviceInfo : accessibilityManager.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK)) {
                String id = serviceInfo.getId();
                String packageName = context.getPackageName();
                if (id.indexOf(packageName) == 0) {
                    return true;
                }
            }
        }
        return false;
    }


}
