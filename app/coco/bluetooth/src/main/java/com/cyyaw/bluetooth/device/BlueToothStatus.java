package com.cyyaw.bluetooth.device;

import com.cyyaw.bluetooth.device.BlueToothConnect;
import com.cyyaw.bluetooth.device.BlueToothStatusCallBack;
import com.cyyaw.bluetooth.out.BlueToothCallBack;
import com.cyyaw.bluetooth.out.BlueToothManager;

public class BlueToothStatus implements BlueToothStatusCallBack {


    @Override
    public void connectSuccess(String address) {
        BlueToothConnect blueTooth = BlueToothManager.getInstance().getConnectMap().get(address);
        if (null != blueTooth) {
            blueTooth.setConnect(true);
        }
    }

    @Override
    public void readData(String address, byte[] data) {
        BlueToothCallBack toothCallBack = BlueToothManager.getInstance().getToothCallBack();
        if (null != toothCallBack) {
            toothCallBack.readData(address, data);
        }
    }

}
