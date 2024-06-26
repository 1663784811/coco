package com.cyyaw.bluetooth.device;


/**
 * 蓝牙状态回调
 */
public interface BlueToothStatusCallBack {


    /**
     * 打开成功
     */
    void connectSuccess(String address);

    /**
     * 读取到的数据
     */
    void readData(String address, byte[] data);


}
