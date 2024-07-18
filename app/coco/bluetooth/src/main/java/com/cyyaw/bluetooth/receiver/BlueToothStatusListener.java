package com.cyyaw.bluetooth.receiver;

import android.bluetooth.BluetoothDevice;

import com.cyyaw.bluetooth.entity.BtEntity;
import com.cyyaw.bluetooth.out.BlueToothCallBack;
import com.cyyaw.bluetooth.out.BlueToothManager;

public class BlueToothStatusListener implements BlueToothReceiver.BlueToothListener {


    @Override
    public void foundBluetooth(BtEntity bluetooth) {
        BluetoothDevice dev = bluetooth.getDev();
        BlueToothManager.getBluetoothMap().put(dev.getAddress(), bluetooth);
        BlueToothCallBack toothCallBack = BlueToothManager.getToothCallBack();
        if (null != toothCallBack) {
            toothCallBack.foundBluetooth(bluetooth);
        }
    }


}
