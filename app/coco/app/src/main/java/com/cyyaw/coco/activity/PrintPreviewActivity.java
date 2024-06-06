package com.cyyaw.coco.activity;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

import com.cyyaw.coco.R;
import com.cyyaw.coco.activity.adapter.BluetoothListAdapter;
import com.cyyaw.coco.common.BaseAppCompatActivity;
import com.cyyaw.coco.common.BroadcastData;
import com.cyyaw.coco.common.BroadcastEnum;
import com.cyyaw.coco.common.permission.PermissionsCode;
import com.cyyaw.coco.entity.BluetoothEntity;
import com.cyyaw.coco.service.BluetoothBleService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
public class PrintPreviewActivity extends BaseAppCompatActivity {
    private final String TAG = PrintPreviewActivity.class.getName();
    private final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    private BluetoothGatt bluetoothGatt;



    /**
     * 读写特性
     */
    private void displayGattServices(List<BluetoothGattService> gattServices) {
        if (gattServices == null) return;
//        String uuid = null;
//        String unknownServiceString = getResources().getString(R.string.unknown_service);
//        String unknownCharaString = getResources().getString(R.string.unknown_characteristic);
//        ArrayList<HashMap<String, String>> gattServiceData = new ArrayList<HashMap<String, String>>();
//        ArrayList<ArrayList<HashMap<String, String>>> gattCharacteristicData = new ArrayList<ArrayList<HashMap<String, String>>>();
//        mGattCharacteristics = new ArrayList<ArrayList<BluetoothGattCharacteristic>>();
//
//        // Loops through available GATT Services.
//        for (BluetoothGattService gattService : gattServices) {
//
//            HashMap<String, String> currentServiceData = new HashMap<String, String>();
//            uuid = gattService.getUuid().toString();
//            currentServiceData.put(LIST_NAME, SampleGattAttributes.lookup(uuid, unknownServiceString));
//            currentServiceData.put(LIST_UUID, uuid);
//            gattServiceData.add(currentServiceData);
//            ArrayList<HashMap<String, String>> gattCharacteristicGroupData = new ArrayList<HashMap<String, String>>();
//            List<BluetoothGattCharacteristic> gattCharacteristics = gattService.getCharacteristics();
//            ArrayList<BluetoothGattCharacteristic> charas = new ArrayList<BluetoothGattCharacteristic>();
//            // Loops through available Characteristics.
//            for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
//                charas.add(gattCharacteristic);
//                HashMap<String, String> currentCharaData = new HashMap<String, String>();
//                uuid = gattCharacteristic.getUuid().toString();
//                currentCharaData.put(LIST_NAME, SampleGattAttributes.lookup(uuid, unknownCharaString));
//                currentCharaData.put(LIST_UUID, uuid);
//                gattCharacteristicGroupData.add(currentCharaData);
//            }
//            mGattCharacteristics.add(charas);
//            gattCharacteristicData.add(gattCharacteristicGroupData);
//        }
    }


    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_preview);
        // ========================
        // 接收数据
        Intent inx = getIntent();
        BroadcastData bluetooth = inx.getSerializableExtra("data", BroadcastData.class);
        bluetooth.setCode(BroadcastEnum.BLUETOOTH_CLASSIC_CONNECT.getCode());
        // 连接蓝牙
        Intent intent = new Intent();
        intent.setAction(BroadcastEnum.BLUETOOTH_BR);
        intent.putExtra("data", bluetooth);
        sendBroadcast(intent);

        // ========================
        View nowPrintBtn = findViewById(R.id.nowPrintBtn);
        View connectBtn = findViewById(R.id.connectBtn);
        View searchBluetoothBtn = findViewById(R.id.searchBluetoothBtn);

        RecyclerView bluetoothList = findViewById(R.id.bluetoothList);
        BluetoothListAdapter bluetoothListAdapter = new BluetoothListAdapter(this, null);
        bluetoothList.setLayoutManager(new LinearLayoutManager(this));
        bluetoothList.setAdapter(bluetoothListAdapter);

        nowPrintBtn.setOnClickListener((View v) -> {
            // 第一步: 获取打印像素数据

            // 第二步: 发送数据
//            bluetoothGatt
        });

        /**
         * 搜索蓝牙
         */
        searchBluetoothBtn.setOnClickListener((View v) -> {
            requestPermissionsFn(PermissionsCode.BLUETOOTH_REQUEST_ENABLE, () -> {
                // 搜索蓝牙
                BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
                BluetoothLeScanner bluetoothLeScanner = defaultAdapter.getBluetoothLeScanner();
                bluetoothLeScanner.startScan(new ScanCallback() {
                    @Override
                    public void onScanResult(int callbackType, ScanResult result) {
                        super.onScanResult(callbackType, result);
                        bluetoothListAdapter.setBlueTooth(result);
                    }
                });
            });
        });

        /**
         * 点击连接
         */
        connectBtn.setOnClickListener((View v) -> {


        });
    }


    @Override
    public Activity getActivity() {
        return this;
    }

}

