package com.cyyaw.coco.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.cyyaw.coco.R;
import com.cyyaw.coco.common.BaseAppCompatActivity;
import com.cyyaw.coco.common.permission.PermissionsCode;

/**
 * 添加设备
 */
@RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
public class AddEquipmentActivity extends BaseAppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_equipment);


        // 打开蓝牙、搜索
        requestPermissionsFn(PermissionsCode.BLUETOOTH_CONNECT, () -> {
            requestPermissionsFn(PermissionsCode.BLUETOOTH_REQUEST_ENABLE, () -> {
                Toast.makeText(this, "蓝牙已经打开", Toast.LENGTH_SHORT).show();
            });
//            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//            if (bluetoothAdapter == null) {
//                // 设备不支持蓝牙
//                Toast.makeText(this, "设备不支持蓝牙", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            if (!bluetoothAdapter.isEnabled()) {
//                // 蓝牙未打开，请求打开蓝牙
//                requestPermissionsFn(PermissionsCode.BLUETOOTH_REQUEST_ENABLE, () -> {
//                    Toast.makeText(this, "蓝牙已经打开", Toast.LENGTH_SHORT).show();
//                });
//            } else {
//                // 蓝牙已经打开，执行后续操作
//                Toast.makeText(this, "蓝牙已经打开", Toast.LENGTH_SHORT).show();
//            }
        });


    }


    @Override
    public Activity getActivity() {
        return this;
    }
}