package com.cyyaw.bluetooth.out;

import com.cyyaw.bluetooth.entity.BluetoothEntity;

/**
 * 蓝牙回调
 */
public interface BlueToothCallBack {


    /**
     * 错误
     */
    void error();


    /**
     * 发现蓝牙
     */
    void foundBluetooth(BluetoothEntity bluetooth);

}
