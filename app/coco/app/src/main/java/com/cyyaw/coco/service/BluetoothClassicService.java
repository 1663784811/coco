package com.cyyaw.coco.service;


import android.Manifest;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.cyyaw.coco.MyApplication;
import com.cyyaw.coco.entity.BluetoothEntity;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * 经典蓝牙
 */
public class BluetoothClassicService extends BlueToothAbstract {
    private static final String TAG = BluetoothClassicService.class.getName();

    static final UUID BLUETOOTH_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private volatile BluetoothSocket mmSocket;
    private volatile BluetoothDevice mmDevice;

    // ========================================================

    @Override
    public void connectBlueTooth(BluetoothEntity bluetooth) {
        super.connectBlueTooth(bluetooth);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED)
            return;
        bluetoothAdapter.cancelDiscovery();
        String ad = bluetoothAdapter.getAddress();
        String address = bluetooth.getAddress();
        mmDevice = blueToothList.get(address);
        // 停止扫描
        try {
            //加密传输，Android系统强制配对，弹窗显示配对码
            //mmSocket = mmDevice.createRfcommSocketToServiceRecord(BLUETOOTH_UUID);
            //明文传输(不安全)，无需配对
            mmSocket = mmDevice.createInsecureRfcommSocketToServiceRecord(BLUETOOTH_UUID);
            MyApplication.post(() -> {
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
                            MyApplication.toast("数据：" + numBytes);
                            // 处理读取的数据
                        } catch (IOException e) {
                            Log.d(TAG, "Input stream was disconnected", e);
                            break;
                        }
                    }
                } catch (IOException e) {
                    closeConnectBlueTooth();
                }
            });
        } catch (Exception e) {
            MyApplication.toast("连接蓝牙失败");
        }
    }

    @Override
    public void writeData(byte[] bytes) {
        try {
            mmSocket.getOutputStream().write(bytes);
        } catch (IOException e) {
            MyApplication.toast("向蓝牙写数据失败");
        }
    }

    @Override
    public void closeConnectBlueTooth() {
        if (mmSocket != null) {
            try {
                mmSocket.close();
            } catch (IOException ex) {
            }
            mmSocket = null;
        }
    }


}



