package com.cyyaw.bluetooth.device;



/**
 *
 */
public class BlueToothConnect {

    private String address;

    private BlueTooth blueTooth;

    private boolean isConnect;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BlueTooth getBlueTooth() {
        return blueTooth;
    }

    public void setBlueTooth(BlueTooth blueTooth) {
        this.blueTooth = blueTooth;
    }

    public boolean isConnect() {
        return isConnect;
    }

    public void setConnect(boolean connect) {
        isConnect = connect;
    }
}
