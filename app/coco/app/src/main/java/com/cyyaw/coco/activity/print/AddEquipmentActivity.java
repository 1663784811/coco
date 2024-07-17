package com.cyyaw.coco.activity.print;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.cyyaw.bluetooth.entity.BluetoothEntity;
import com.cyyaw.bluetooth.out.BlueToothCallBack;
import com.cyyaw.bluetooth.out.BlueToothManager;
import com.cyyaw.coco.R;
import com.cyyaw.coco.common.BaseAppCompatActivity;
import com.cyyaw.coco.common.permission.PermissionsCode;
import com.cyyaw.cui.fragment.CuiNavBarFragment;
import com.cyyaw.cui.fragment.CuiPopupFragment;

/**
 * 添加设备
 */
public class AddEquipmentActivity extends BaseAppCompatActivity {

    private final String TAG = AddEquipmentActivity.class.getName();

    private BluetoothAdapter bluetoothAdapter;


    public static void openActivity(Context context) {
        Intent intent = new Intent(context, AddEquipmentActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_equipment);

        CuiNavBarFragment nav = new CuiNavBarFragment("添加设备");
        getSupportFragmentManager().beginTransaction().add(R.id.header_title, nav).commit();

        CuiPopupFragment popup = new CuiPopupFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.add_equipment, popup).commit();


        // 打开蓝牙、搜索
        requestPermissionsFn(PermissionsCode.BLUETOOTH_CONNECT, () -> {
            requestPermissionsFn(PermissionsCode.BLUETOOTH_REQUEST_ENABLE, () -> {
                // 搜索蓝牙
                scanLeDevice();
            });
        });


    }


    private void scanLeDevice() {

        BlueToothManager.setCallBack(new BlueToothCallBack() {
            @Override
            public void error() {

            }

            @Override
            public void foundBluetooth(BluetoothEntity bluetooth) {

            }
        });

        BlueToothManager.discoveryBlueTooth();
    }

}