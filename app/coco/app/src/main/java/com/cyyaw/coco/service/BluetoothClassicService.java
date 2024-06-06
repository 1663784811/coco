package com.cyyaw.coco.service;


import android.annotation.SuppressLint;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.cyyaw.coco.common.BroadcastData;
import com.cyyaw.coco.common.BroadcastEnum;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * 经典蓝牙
 */
@SuppressLint("MissingPermission")
@RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
public class BluetoothClassicService extends Service implements BlueTooth {
    private static final String TAG = BluetoothClassicService.class.getName();

    private final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private BluetoothSocket mmSocket;
    private BluetoothDevice mmDevice;


    private BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // 搜索到的蓝牙
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                // 通知页面

            } else {
                // 接收广播:  连接蓝牙
                BroadcastData data = intent.getSerializableExtra("data", BroadcastData.class);
                if (BroadcastEnum.BLUETOOTH_CLASSIC_SEARCH.getCode().equals(data.getCode())) {
                    // 开始搜索蓝牙
                    searchBlueTooth();
                } else if (BroadcastEnum.BLUETOOTH_CLASSIC_CONNECT.getCode().equals(data.getCode())) {
                    // 连接蓝牙
//                    Object data1 = data.getData();
                    String address = "";
                    connectBlueTooth(address);
                }
            }
        }
    };


    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter intentFilter = new IntentFilter();
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
            bluetoothAdapter.startDiscovery();
        }
    }

    @Override
    public boolean connectBlueTooth(String address) {
        // 停止扫描
        String ad = bluetoothAdapter.getAddress();
        BluetoothSocket tmp = null;
        mmSocket = null;
        try {
            tmp = mmDevice.createRfcommSocketToServiceRecord(UUID.fromString(ad));
        } catch (Exception e) {
            Log.e(TAG, "Socket's create() method failed", e);
        }
        mmSocket = tmp;
        new Thread(() -> {
            bluetoothAdapter.cancelDiscovery();
            try {
                // 连接
                mmSocket.connect();
                // 连接成功
                InputStream mmInStream = mmSocket.getInputStream();
                // 缓冲区大小
                byte[] mmBuffer = new byte[1024];
                // 读取几个字节
                int numBytes = 0;
                // 读取数据
                while (true) {
                    try {
                        numBytes = mmInStream.read(mmBuffer);
                        // 处理读取的数据
                    } catch (IOException e) {
                        Log.d(TAG, "Input stream was disconnected", e);
                        break;
                    }
                }
            } catch (IOException e) {
                try {
                    mmSocket.close();
                } catch (IOException ex) {
                    Log.e(TAG, "Could not close the client socket", ex);
                }
                mmSocket = null;
            }
        }).start();
        return false;
    }

    @Override
    public void writeData(byte[] bytes) {
        try {
            mmSocket.getOutputStream().write(bytes);
        } catch (IOException e) {
            Log.e(TAG, "Error occurred when sending data", e);
        }
    }


}



