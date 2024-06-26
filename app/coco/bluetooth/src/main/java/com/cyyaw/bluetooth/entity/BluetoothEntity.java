package com.cyyaw.bluetooth.entity;

import android.bluetooth.BluetoothDevice;

import java.io.Serializable;

/**
 * 蓝牙实体
 */
public class BluetoothEntity implements Serializable {

    /**
     * 类型
     */
    private Integer type;

    /**
     * 信号强度
     */
    private int rssi;

    /**
     * 蓝牙驱动
     */
    private BluetoothDevice dev;


    public BluetoothDevice getDev() {
        return dev;
    }

    public void setDev(BluetoothDevice dev) {
        this.dev = dev;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }
}
