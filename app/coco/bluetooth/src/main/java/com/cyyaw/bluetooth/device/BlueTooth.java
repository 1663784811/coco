package com.cyyaw.bluetooth.device;


import android.bluetooth.BluetoothDevice;

public interface BlueTooth {

    /**
     * 连接蓝牙
     */
    void connectBlueTooth(BluetoothDevice bluetooth);

    /**
     * 断开连接
     */
    void closeConnectBlueTooth();

    /**
     * 发送数据
     */
    void writeData(byte data[]);

}
