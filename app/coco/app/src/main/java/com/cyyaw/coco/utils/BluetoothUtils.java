package com.cyyaw.coco.utils;

import android.content.Intent;

import com.cyyaw.coco.common.BaseAppCompatActivity;
import com.cyyaw.coco.common.BroadcastData;
import com.cyyaw.coco.common.BroadcastEnum;
import com.cyyaw.coco.common.permission.PermissionsCode;
import com.cyyaw.coco.entity.BluetoothEntity;

public class BluetoothUtils {

    private BluetoothUtils() {
    }

    /**
     * 搜索蓝牙
     */
    public static void search(BaseAppCompatActivity context) {
        context.requestPermissionsFn(PermissionsCode.BLUETOOTH_SCAN, () -> {
            // 搜索蓝牙
            Intent intent = new Intent();
            intent.setAction(BroadcastEnum.BLUETOOTH_BR);
            BroadcastData<String> broadcastData = new BroadcastData();
            broadcastData.setCode(BroadcastEnum.BLUETOOTH_SEARCH.getCode());
            broadcastData.setData(BroadcastEnum.BLUETOOTH_SEARCH.getNote());
            intent.putExtra("data", broadcastData);
            context.sendBroadcast(intent);
        });
    }

    /**
     * 断开连接
     */
    public static void link(BaseAppCompatActivity context, BluetoothEntity bluetooth) {
        context.requestPermissionsFn(PermissionsCode.BLUETOOTH_CONNECT, () -> {
            BroadcastData broadcastData = new BroadcastData();
            Intent intent = new Intent();
            intent.setAction(BroadcastEnum.BLUETOOTH_BR);
            broadcastData.setCode(BroadcastEnum.BLUETOOTH_CONNECT.getCode());
            broadcastData.setData(bluetooth);
            intent.putExtra("data", broadcastData);
            context.sendBroadcast(intent);
        });
    }

    /**
     * 断开连接
     */
    public static void unLink(BaseAppCompatActivity context) {
        BroadcastData broadcastData = new BroadcastData();
        Intent intent = new Intent();
        intent.setAction(BroadcastEnum.BLUETOOTH_BR);
        broadcastData.setCode(BroadcastEnum.BLUETOOTH_UN_CONNECT.getCode());
        intent.putExtra("data", broadcastData);
        context.sendBroadcast(intent);
    }
}
