package com.cyyaw.coco.activity.bluetooth.bt;

import android.Manifest;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import com.cyyaw.coco.MyApplication;


/**
 * 客户端，与服务端建立长连接
 */
public class BtClient extends BtBase {
    public BtClient(Context context, Listener listener) {
        super(context, listener);
    }

    /**
     * 与远端设备建立长连接
     * @param dev 远端设备
     */
    public void connect(BluetoothDevice dev) {
        close();
        try {
//             final BluetoothSocket socket = dev.createRfcommSocketToServiceRecord(SPP_UUID); //加密传输，Android系统强制配对，弹窗显示配对码
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            final BluetoothSocket socket = dev.createInsecureRfcommSocketToServiceRecord(SPP_UUID); //明文传输(不安全)，无需配对
            // 开启子线程
            MyApplication.run(new Runnable() {
                @Override
                public void run() {
                    loopRead(socket); //循环读取
                }
            });
        } catch (Throwable e) {
            close();
        }
    }
}