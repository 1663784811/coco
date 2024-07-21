package com.cyyaw.bluetooth.out;


import com.cyyaw.bluetooth.entity.BtStatus;

/**
 * 蓝牙连接状态回调
 */
public interface BlueToothConnectCallBack {


    /**
     * 状态回调
     */
    void statusCallBack(String address, BtStatus status);

    /**
     * 读取到的数据
     */
    void readData(String address, byte[] data);


}
