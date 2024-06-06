package com.cyyaw.coco.entity;

import java.io.Serializable;

/**
 * 蓝牙实体
 */
public class BluetoothEntity implements Serializable {

    /**
     * 名称
     */
    private String name;

    /**
     * 地址
     */
    private String address;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 信号强度
     */
    private int rssi;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
