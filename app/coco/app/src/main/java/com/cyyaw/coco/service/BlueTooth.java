package com.cyyaw.coco.service;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanCallback;

import java.util.List;

public interface BlueTooth {

    /**
     * 搜索蓝牙
     */
    void searchBlueTooth();

    /**
     * 连接蓝牙
     */
    boolean connectBlueTooth(final String address);


    /**
     * 查找已配对设备
     */
//    List<BluetoothDevice> BondedDevices();


    /**
     * 发送数据
     */
    void writeData(byte data[]);

}
