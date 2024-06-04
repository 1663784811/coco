package com.cyyaw.coco.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothProfile;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class BluetoothLeService extends Service {

    public static final String TAG = "BluetoothLeService";

    private final Binder binder = new LocalBinder();

    private final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    private BluetoothGatt bluetoothGatt;

    public final static String ACTION_GATT_CONNECTED = "com.cyyaw.coco.service.BluetoothLeService";
    public final static String ACTION_GATT_DISCONNECTED = "com.example.bluetooth.le.ACTION_GATT_DISCONNECTED";


    private static final int STATE_DISCONNECTED = 0;
    private static final int STATE_CONNECTED = 2;


    private final BluetoothGattCallback bluetoothGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                // successfully connected to the GATT Server
                // connectionState = STATE_CONNECTED;
                broadcastUpdate(ACTION_GATT_CONNECTED);
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                // disconnected from the GATT Server
                // connectionState = STATE_DISCONNECTED;
                //
                broadcastUpdate(ACTION_GATT_DISCONNECTED);
            }
        }
    };

    public class LocalBinder extends Binder {
        public BluetoothLeService getService() {
            return BluetoothLeService.this;
        }
    }



    /**
     * 连接蓝牙
     */
    @SuppressLint("MissingPermission")
    public boolean connect(final String address) {
        try {
            BluetoothDevice remoteDevice = bluetoothAdapter.getRemoteDevice(address);
            bluetoothGatt = remoteDevice.connectGatt(this, true, bluetoothGattCallback);
        } catch (IllegalArgumentException exception) {
            return false;
        }
        return true;
    }


    /**
     * 更新广播
     */
    private void broadcastUpdate(final String action) {
        final Intent intent = new Intent(action);
        sendBroadcast(intent);
    }

    /**
     *
     */
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        close();
        return super.onUnbind(intent);
    }

    @SuppressLint("MissingPermission")
    private void close() {
        if (bluetoothGatt != null) {
            bluetoothGatt.close();
            bluetoothGatt = null;
        }
    }

}