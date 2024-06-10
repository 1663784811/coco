package com.cyyaw.coco.utils;

import android.content.Context;
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
            context.requestPermissionsFn(PermissionsCode.BLUETOOTH_CONNECT, () -> {
                // 搜索蓝牙
                Intent intent = new Intent();
                intent.setAction(BroadcastEnum.BLUETOOTH_BR);
                BroadcastData<String> broadcastData = new BroadcastData();
                broadcastData.setCode(BroadcastEnum.BLUETOOTH_SEARCH.getCode());
                broadcastData.setData(BroadcastEnum.BLUETOOTH_SEARCH.getNote());
                intent.putExtra("data", broadcastData);
                context.sendBroadcast(intent);
            });
        });
    }

    /**
     * 申请连接
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
     * 连接成功
     */
    public static void connectSuccess(Context context, BluetoothEntity bluetooth) {
        BroadcastData broadcastData = new BroadcastData();
        Intent intent = new Intent();
        intent.setAction(BroadcastEnum.ACTIVITY_BLUETOOTH);
        broadcastData.setCode(BroadcastEnum.BLUETOOTH_CONNECT_SUCCESS.getCode());
        broadcastData.setData(bluetooth);
        intent.putExtra("data", broadcastData);
        context.sendBroadcast(intent);
    }

    /**
     * 连接失败
     */
    public static void connectFail(Context context, BluetoothEntity bluetooth) {
        BroadcastData broadcastData = new BroadcastData();
        Intent intent = new Intent();
        intent.setAction(BroadcastEnum.ACTIVITY_BLUETOOTH);
        broadcastData.setCode(BroadcastEnum.BLUETOOTH_CONNECT_FAIL.getCode());
        broadcastData.setData(bluetooth);
        intent.putExtra("data", broadcastData);
        context.sendBroadcast(intent);
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


    /**
     * 发送数据
     */
    public static void sendData(BaseAppCompatActivity context, byte data[]) {
        BroadcastData broadcastData = new BroadcastData();
        Intent intent = new Intent();
        intent.setAction(BroadcastEnum.BLUETOOTH_BR);
        broadcastData.setCode(BroadcastEnum.BLUETOOTH_SEND_DATA.getCode());
        broadcastData.setData(data);
        intent.putExtra("data", broadcastData);
        context.sendBroadcast(intent);
    }


    /**
     * 发现蓝牙设备
     */
    public static void findDevice(Context context, BluetoothEntity bluetooth) {
        Intent inx = new Intent();
        inx.setAction(BroadcastEnum.ACTIVITY_BLUETOOTH);
        BroadcastData<BluetoothEntity> broadcastData = new BroadcastData();
        broadcastData.setCode(BroadcastEnum.BLUETOOTH_SEARCH.getCode());
        broadcastData.setData(bluetooth);
        inx.putExtra("data", broadcastData);
        context.sendBroadcast(inx);
    }
}
