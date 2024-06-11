package com.cyyaw.coco.activity.bluetooth;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.cyyaw.coco.R;
import com.cyyaw.coco.common.BaseAppCompatActivity;
import com.cyyaw.coco.common.permission.PermissionsCode;

import java.util.Set;

/**
 * 添加设备
 */
public class AddEquipmentActivity extends BaseAppCompatActivity {


    private final String TAG = AddEquipmentActivity.class.getName();

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothLeScanner bluetoothLeScanner;

    private Set<BluetoothDevice> bluetoothDeviceSet;


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


    @SuppressLint("MissingPermission")
    private void scanLeDevice() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();

        bluetoothLeScanner.startScan(new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                super.onScanResult(callbackType, result);
                BluetoothDevice device = result.getDevice();
                String name = device.getName();
                String address = device.getAddress();
                Log.i(TAG, "onScanResult: " + name + "         " + address);
            }
        });
    }


    @Override
    public Activity getActivity() {
        return this;
    }
}