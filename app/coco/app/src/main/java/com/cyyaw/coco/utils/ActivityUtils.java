package com.cyyaw.coco.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;

import com.cyyaw.coco.common.BaseAppCompatActivity;
import com.cyyaw.coco.common.permission.PermissionsCode;

import java.io.Serializable;

public class ActivityUtils {

    private ActivityUtils() {
    }


    public static <T extends Serializable> T getParameter(Activity activity, @NonNull Class<T> clazz) {
        Intent inx = activity.getIntent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return inx.getSerializableExtra("data", clazz);
        }
        return null;
    }

    public static void startActivity(Context context, @NonNull Class clazz, Serializable obj) {
        Intent intent = new Intent(context, clazz);
        intent.putExtra("data", obj);
        context.startActivity(intent);
    }

    /**
     * 申请蓝牙权限
     */
    public static void blueToothPermissions(BaseAppCompatActivity baseAppCompatActivity, PermissionsCode.PermissionsSuccessCallback successCallback) {
        baseAppCompatActivity.requestPermissionsFn(PermissionsCode.BLUETOOTH_REQUEST_ENABLE, () -> {
            baseAppCompatActivity.requestPermissionsFn(PermissionsCode.BLUETOOTH_CONNECT, () -> {
                baseAppCompatActivity.requestPermissionsFn(PermissionsCode.BLUETOOTH_SCAN, successCallback);
            });
        });
    }

}
