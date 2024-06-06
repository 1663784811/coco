package com.cyyaw.coco.service;


import android.annotation.SuppressLint;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
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
public class BluetoothClassicService extends BlueToothAbstract {
    private static final String TAG = BluetoothClassicService.class.getName();
    private BluetoothSocket mmSocket;
    private BluetoothDevice mmDevice;

    // ========================================================

    @Override
    public void connectBlueTooth(String address) {
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



