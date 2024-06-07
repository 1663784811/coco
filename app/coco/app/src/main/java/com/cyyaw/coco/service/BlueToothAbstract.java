package com.cyyaw.coco.service;

import android.Manifest;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.cyyaw.coco.common.BroadcastData;
import com.cyyaw.coco.common.BroadcastEnum;
import com.cyyaw.coco.entity.BluetoothEntity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class BlueToothAbstract extends Service implements BlueTooth {

    private static final String TAG = BlueToothAbstract.class.getName();

    protected final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    protected Map<String, BluetoothDevice> blueToothList = new ConcurrentHashMap();


    private BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // 搜索到的蓝牙
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                device.getType();
                // 通知页面
            } else {
                // 接收广播:  连接蓝牙
                BroadcastData data = intent.getSerializableExtra("data", BroadcastData.class);
                if (BroadcastEnum.BLUETOOTH_SEARCH.getCode().equals(data.getCode())) {
                    // 开始搜索蓝牙
                    searchBlueTooth();
                } else if (BroadcastEnum.BLUETOOTH_CONNECT.getCode().equals(data.getCode())) {
                    // 连接蓝牙
                    BluetoothEntity bluetooth = (BluetoothEntity) data.getData();
                    connectBlueTooth(bluetooth);
                } else if (BroadcastEnum.BLUETOOTH_UN_CONNECT.getCode().equals(data.getCode())) {
                    // 断开蓝牙连接
                    closeConnectBlueTooth();
                }
            }
        }
    };

    protected ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            BluetoothDevice device = result.getDevice();
            blueToothList.put(device.getAddress(), device);
            BluetoothEntity bluetoothEntity = new BluetoothEntity();
            bluetoothEntity.setName(device.getName());
            bluetoothEntity.setAddress(device.getAddress());
            bluetoothEntity.setType(device.getType());
            bluetoothEntity.setRssi(result.getRssi());
            Intent inx = new Intent();
            inx.setAction(BroadcastEnum.ACTIVITY_BLUETOOTH);
            BroadcastData<BluetoothEntity> broadcastData = new BroadcastData();
            broadcastData.setCode(BroadcastEnum.BLUETOOTH_SEARCH.getCode());
            broadcastData.setData(bluetoothEntity);
            inx.putExtra("data", broadcastData);
            sendBroadcast(inx);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BroadcastEnum.BLUETOOTH_BR);
        intentFilter.addAction(BroadcastEnum.BLUETOOTH_CLASSIC);
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        ContextCompat.registerReceiver(this, br, intentFilter, ContextCompat.RECEIVER_EXPORTED);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (BroadcastEnum.ACTIVITY_HOME.equals(intent.getStringExtra("clazz"))) {
            Intent inx = new Intent();
            inx.setAction(BroadcastEnum.ACTIVITY_HOME);
            BroadcastData<String> broadcastData = new BroadcastData();
            broadcastData.setCode(BroadcastEnum.STATUS_SERVICE_INIT);
            broadcastData.setData("service 初始化完成");
            inx.putExtra("data", broadcastData);
            sendBroadcast(inx);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(br);
    }


    // ========================================================
    @Override
    public void searchBlueTooth() {
        // 搜索蓝牙
        if (bluetoothAdapter.isEnabled()) {
            BluetoothLeScanner bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            bluetoothLeScanner.startScan(scanCallback);
        }
    }

    @Override
    public void connectBlueTooth(BluetoothEntity bluetooth) {
        BluetoothLeScanner bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        bluetoothLeScanner.stopScan(scanCallback);
    }


}
