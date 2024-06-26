package com.cyyaw.bluetooth.device;

import android.bluetooth.BluetoothDevice;

/**
 * 低功耗蓝牙
 */
public class BluetoothBle implements BlueTooth {


    @Override
    public void connectBlueTooth(BluetoothDevice bluetooth) {

    }

    @Override
    public void closeConnectBlueTooth() {

    }

    @Override
    public void writeData(byte[] data) {

    }



//    public static final String TAG = "BluetoothLeService";
//    private final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//
//    private BluetoothGatt bluetoothGatt;
//
//    private final BluetoothGattCallback bluetoothGattCallback = new BluetoothGattCallback() {
//        /**
//         * 连接状态变化回调
//         * 1.连接成功
//         * 2.断开连接
//         */
//        @Override
//        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
//            if (newState == BluetoothProfile.STATE_CONNECTED) {
//                // 连接成功
//                // broadcastUpdate();
//                // 查询服务
//                bluetoothGatt.discoverServices();
//            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
//                // 断开连接
//                //broadcastUpdate();
//            }
//        }
//
//        /**
//         * 当设备报告其可用服务时，系统会调用此函数
//         */
//        @Override
//        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
//            if (status == BluetoothGatt.GATT_SUCCESS) {
//                // broadcastUpdate();
//            }
//        }
//
//        /**
//         *  接收结果  ( 特征读取 )
//         */
//        @Override
//        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
//            if (status == BluetoothGatt.GATT_SUCCESS) {
//                broadcastUpdate(characteristic);
//                characteristic.getValue();
//
//            }
//        }
//
//        /**
//         * 特征启用通知后，如果远程设备上的特征发生变化，则会触发  ( 特征发生变化 )
//         */
//        @Override
//        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
//            super.onCharacteristicChanged(gatt, characteristic);
//            characteristic.getValue();
//            broadcastUpdate(characteristic);
//        }
//    };
//
//
//    /**
//     * 广播更新
//     */
//    private void broadcastUpdate(final BluetoothGattCharacteristic characteristic) {
//        final Intent intent = new Intent();
//        if ("".equals(characteristic.getUuid())) {
//            int flag = characteristic.getProperties();
//            int format = -1;
//            if ((flag & 0x01) != 0) {
//                format = BluetoothGattCharacteristic.FORMAT_UINT16;
//                Log.d(TAG, "Heart rate format UINT16.");
//            } else {
//                format = BluetoothGattCharacteristic.FORMAT_UINT8;
//                Log.d(TAG, "Heart rate format UINT8.");
//            }
//            final int heartRate = characteristic.getIntValue(format, 1);
//            Log.d(TAG, String.format("Received heart rate: %d", heartRate));
//            // intent.putExtra(EXTRA_DATA, String.valueOf(heartRate));
//        } else {
//            // For all other profiles, writes the data formatted in HEX.
//            final byte[] data = characteristic.getValue();
//            if (data != null && data.length > 0) {
//                final StringBuilder stringBuilder = new StringBuilder(data.length);
//                for (byte byteChar : data) stringBuilder.append(String.format("%02X ", byteChar));
//                // intent.putExtra(EXTRA_DATA, new String(data) + "\n" + stringBuilder.toString());
//            }
//        }
//        sendBroadcast(intent);
//    }
//
//    public List<BluetoothGattService> getSupportedGattServices() {
//        if (bluetoothGatt == null) return null;
//        return bluetoothGatt.getServices();
//    }
//
//
//    /**
//     * 读取特性
//     */
//    public void readCharacteristic(BluetoothGattCharacteristic characteristic) {
//        if (bluetoothGatt == null) {
//            Log.w(TAG, "BluetoothGatt not initialized");
//            return;
//        }
//        bluetoothGatt.readCharacteristic(characteristic);
//        // bluetoothGatt.writeCharacteristic();
//    }
//
//
//    /**
//     * 接收广播
//     */
//    private BroadcastReceiver br = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            // 接收广播: 搜索蓝牙、 连接蓝牙
//
//        }
//    };
//
//
//    /**
//     *
//     */
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        // 注册广播接收器
//        LocalBroadcastManager.getInstance(this).registerReceiver(br, new IntentFilter(BroadcastEnum.BLUETOOTH_BLE));
//    }
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        //return new LocalBinder(this);
//        return null;
//    }
//
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        if (bluetoothGatt != null) {
//            bluetoothGatt.close();
//            bluetoothGatt = null;
//        }
//
//    }
//
//    // =========================
//    @SuppressLint("MissingPermission")
//    @Override
//    public void searchBlueTooth() {
//        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
//        BluetoothLeScanner bluetoothLeScanner = defaultAdapter.getBluetoothLeScanner();
////        bluetoothLeScanner.startScan(scanCallback);
//    }
//
//    /**
//     * 连接蓝牙
//     */
//    @SuppressLint("MissingPermission")
//    public void connectBlueTooth(BluetoothEntity bluetooth) {
//        try {
//            String address = bluetooth.getAddress();
//            BluetoothDevice remoteDevice = bluetoothAdapter.getRemoteDevice(address);
//            bluetoothGatt = remoteDevice.connectGatt(this, true, bluetoothGattCallback);
//        } catch (IllegalArgumentException exception) {
//
//        }
//    }
//
//    @Override
//    public void writeData(byte[] data) {
//
//    }
//
//    @Override
//    public void closeConnectBlueTooth() {
//
//    }


}