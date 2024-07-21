package com.cyyaw.bluetooth.out;

import com.cyyaw.bluetooth.entity.BtEntity;

/**
 * 发现蓝牙回调
 */
public interface BlueToothFindCallBack {


    /**
     * 错误
     */
    void error();

    /**
     * 发现蓝牙
     */
    void foundBluetooth(BtEntity bluetooth);

}
