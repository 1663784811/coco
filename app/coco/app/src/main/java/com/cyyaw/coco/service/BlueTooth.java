package com.cyyaw.coco.service;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanCallback;

import com.cyyaw.coco.entity.BluetoothEntity;

import java.util.List;

public interface BlueTooth {

    /**
     * 搜索蓝牙
     */
    void searchBlueTooth();

    /**
     * 连接蓝牙
     */
    void connectBlueTooth(BluetoothEntity bluetooth);

    /**
     * 查找已配对设备
     */
//    List<BluetoothDevice> BondedDevices();


    /**
     * 发送数据
     */
    void writeData(byte data[]);

    /**
     * 断开连接
     */
    void closeConnectBlueTooth();

}
