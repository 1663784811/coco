package com.cyyaw.coco.common;

import android.content.Intent;
import android.net.Uri;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.cyyaw.coco.common.permission.PermissionsCode;


/**
 * 处理权限受权问题
 */
public abstract class BaseAppCompatActivity extends AppCompatActivity {

    /**
     * 特殊受权
     */
    private String otherPermissions;

    /**
     * 需要跳转其它Active才能受权的
     */
    protected ActivityResultLauncher<Intent> activityResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), (ActivityResult result) -> {
        // 申请权限
        if (null != otherPermissions) {
            PermissionsCode permissionsCode = PermissionsCode.getPermissionsCode(otherPermissions);
            otherPermissions = null;
            if (permissionsCode != null) {
                ActivityCompat.requestPermissions(this, new String[]{permissionsCode.getPermissions()}, permissionsCode.getCode());
            }
        }
    });

    // ============================================================================
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissions.length > 0) {
            PermissionsCode permissionsCode = PermissionsCode.getPermissionsCode(permissions[0]);
            if (null != permissionsCode) {
                if (PermissionsCode.alreadyPermissions(this, permissionsCode)) {
                    PermissionsCode.PermissionsSuccessCallback successCallback = permissionsCode.getSuccessCallback();
                    if (null != successCallback) {
                        successCallback.run();
                    }
                } else {
                    PermissionsCode.PermissionsErrorCallback errorCallback = permissionsCode.getErrorCallback();
                    if (null != errorCallback) {
                        errorCallback.run();
                    }
                }
            }
        }
    }


    /**
     * 受权处理
     */
    public void requestPermissionsFn(PermissionsCode permissionsCode, PermissionsCode.PermissionsSuccessCallback successCallback) {
        requestPermissionsFn(permissionsCode, successCallback, () -> {
        });
    }

    public void requestPermissionsFn(PermissionsCode permissionsCode, PermissionsCode.PermissionsSuccessCallback successCallback, PermissionsCode.PermissionsErrorCallback errorCallback) {
        otherPermissions = null;
        // 检查是否已经有这个权限了
        if (PermissionsCode.alreadyPermissions(this, permissionsCode)) {
            successCallback.run();
        } else {
            String sysActivity = permissionsCode.getSysActivity();
            if (sysActivity != null) {
                // 特殊
                otherPermissions = permissionsCode.getPermissions();
                permissionsCode.setSuccessCallback(successCallback);
                permissionsCode.setErrorCallback(errorCallback);
                Intent intent = new Intent(sysActivity);
                if (permissionsCode.getNeedPackage()) {
                    intent.setData(Uri.parse("package:" + this.getPackageName()));
                }
                activityResult.launch(intent);
            } else if (permissionsCode.getPermissions().equals(PermissionsCode.BLUETOOTH_REQUEST_ENABLE.getPermissions())) {
                // 蓝牙
                otherPermissions = permissionsCode.getPermissions();
                permissionsCode.setSuccessCallback(successCallback);
                permissionsCode.setErrorCallback(errorCallback);
                Intent intent = new Intent(otherPermissions);
                activityResult.launch(intent);
            } else {
                permissionsCode.setSuccessCallback(successCallback);
                permissionsCode.setErrorCallback(errorCallback);
                ActivityCompat.requestPermissions(this, new String[]{permissionsCode.getPermissions()}, permissionsCode.getCode());
            }
        }
    }


}


