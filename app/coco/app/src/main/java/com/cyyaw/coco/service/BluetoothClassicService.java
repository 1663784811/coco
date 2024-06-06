package com.cyyaw.coco.service;


import android.Manifest;
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
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.cyyaw.coco.common.BroadcastData;
import com.cyyaw.coco.common.BroadcastEnum;
import com.cyyaw.coco.entity.BluetoothEntity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * 经典蓝牙
 */
public class BluetoothClassicService extends BlueToothAbstract {
    private static final String TAG = BluetoothClassicService.class.getName();
    private BluetoothSocket mmSocket;
    private BluetoothDevice mmDevice;

    // ========================================================

    @Override
    public void connectBlueTooth(BluetoothEntity bluetooth) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        bluetoothAdapter.cancelDiscovery();
        String ad = bluetoothAdapter.getAddress();
        String address = bluetooth.getAddress();
        mmDevice = blueToothList.get(address);
        // 停止扫描
        mmSocket = null;
        try {
            mmSocket = mmDevice.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
        } catch (Exception e) {
            Log.e(TAG, "Socket's create() method failed", e);
        }
        if (null != mmSocket) {
            new Thread(() -> {
                try {
                    // 连接
                    mmSocket.connect();
                    // 连接成功
//                    InputStream mmInStream = mmSocket.getInputStream();
//                    // 缓冲区大小
//                    byte[] mmBuffer = new byte[1024];
//                    // 读取几个字节
//                    int numBytes = 0;
//                    // 读取数据
//                    while (true) {
//                        try {
//                            numBytes = mmInStream.read(mmBuffer);
//                            // 处理读取的数据
//                        } catch (IOException e) {
//                            Log.d(TAG, "Input stream was disconnected", e);
//                            break;
//                        }
//                    }
                } catch (IOException e) {
                    try {
                        mmSocket.close();
                    } catch (IOException ex) {
                        Log.e(TAG, "Could not close the client socket", ex);
                    }
                    mmSocket = null;
                }
            }).start();
        } else {
            Toast.makeText(this, "连接失败", Toast.LENGTH_SHORT).show();
        }
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



