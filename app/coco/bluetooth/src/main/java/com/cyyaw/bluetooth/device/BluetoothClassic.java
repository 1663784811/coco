package com.cyyaw.bluetooth.device;


import android.Manifest;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import com.cyyaw.bluetooth.out.BlueToothConnectCallBack;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * 经典蓝牙
 */
public class BluetoothClassic implements BlueTooth {
    private static final String TAG = BluetoothClassic.class.getName();

    static final UUID BLUETOOTH_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private Context context;
    private BlueToothConnectCallBack callBack;

    private volatile BluetoothSocket mmSocket;

    private volatile InputStream mmInStream;
    private volatile OutputStream mmOutputStream;

    // ========================================================


    public BluetoothClassic(Context context) {
        this.context = context;
    }

    @Override
    public void connectBlueTooth(BluetoothDevice bluetooth) {
        String address = bluetooth.getAddress();
        if (callBack != null) {
            callBack.statusCallBack(address, BtStatus.CONNECTTING);
        }
        // 停止扫描
        try {
            //加密传输，Android系统强制配对，弹窗显示配对码
            //mmSocket = mmDevice.createRfcommSocketToServiceRecord(BLUETOOTH_UUID);
            //明文传输(不安全)，无需配对
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mmSocket = bluetooth.createInsecureRfcommSocketToServiceRecord(BLUETOOTH_UUID);
            new Thread(() -> {
                try {
                    // 连接
                    mmSocket.connect();
                    // 连接成功
                    mmInStream = mmSocket.getInputStream();
                    mmOutputStream = mmSocket.getOutputStream();
                    if (callBack != null) {
                        callBack.statusCallBack(address, BtStatus.SUCCESS);
                    }
                    // 缓冲区大小
                    byte[] mmBuffer = new byte[1024];
                    // 读取几个字节
                    // 读取数据
                    while (true) {
                        int numBytes = mmInStream.read(mmBuffer);
                        if (null != callBack) {
                            byte[] rest = new byte[numBytes];
                            System.arraycopy(mmBuffer, 0, rest, 0, numBytes);
                            callBack.readData(address, rest);
                        }
                    }
                } catch (IOException e) {
                    if (callBack != null) {
                        callBack.statusCallBack(address, BtStatus.FAIL);
                    }
                    closeConnectBlueTooth();
                }
            }).start();
        } catch (Exception e) {
            if (callBack != null) {
                callBack.statusCallBack(address, BtStatus.FAIL);
            }
        }
    }

    @Override
    public void writeData(byte[] bytes) {
        try {
            mmOutputStream.write(bytes);
            mmOutputStream.flush();
        } catch (IOException e) {
            if (callBack != null) {
                // callBack.sendFail(address);
            }
        }
    }

    @Override
    public void closeConnectBlueTooth() {
        if (mmSocket != null) {
            if (null != mmOutputStream) {
                try {
                    mmOutputStream.close();
                } catch (IOException e) {
                }
            }
            if (null != mmInStream) {
                try {
                    mmInStream.close();
                } catch (IOException e) {
                }
            }
            try {
                mmSocket.close();
            } catch (IOException ex) {
            }
            mmSocket = null;
        }
    }

    public void setCallBack(BlueToothConnectCallBack callBack) {
        this.callBack = callBack;
    }
}



