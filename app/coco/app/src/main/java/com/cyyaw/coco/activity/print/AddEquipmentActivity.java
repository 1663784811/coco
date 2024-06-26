package com.cyyaw.coco.activity.print;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;

import com.cyyaw.coco.R;
import com.cyyaw.coco.common.BaseAppCompatActivity;
import com.cyyaw.coco.common.permission.PermissionsCode;

/**
 * 添加设备
 */
public class AddEquipmentActivity extends BaseAppCompatActivity {

    private final String TAG = AddEquipmentActivity.class.getName();

    private BluetoothAdapter bluetoothAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_equipment);


        // 打开蓝牙、搜索
        requestPermissionsFn(PermissionsCode.BLUETOOTH_CONNECT, () -> {
            requestPermissionsFn(PermissionsCode.BLUETOOTH_REQUEST_ENABLE, () -> {
                // 搜索蓝牙
                scanLeDevice();
            });
        });


    }


    private void scanLeDevice() {



    }


    @Override
    public Activity getActivity() {
        return this;
    }
}