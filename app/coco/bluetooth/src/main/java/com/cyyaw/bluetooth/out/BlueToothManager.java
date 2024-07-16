package com.cyyaw.bluetooth.out;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import com.cyyaw.bluetooth.receiver.BlueToothReceiver;
import com.cyyaw.bluetooth.device.BlueTooth;
import com.cyyaw.bluetooth.device.BlueToothConnect;
import com.cyyaw.bluetooth.device.BluetoothClassic;
import com.cyyaw.bluetooth.entity.BluetoothEntity;
import com.cyyaw.bluetooth.receiver.BlueToothStatusListener;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 蓝牙管理器
 */
public class BlueToothManager {

    private static BlueToothManager blueToothManager;
    // 线程池
    private static final Executor threadPool = Executors.newCachedThreadPool();

    /**
     * 回调
     */
    private BlueToothCallBack toothCallBack;
    protected final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private Context cxt;
    /**
     * 蓝牙列表
     */
    private ConcurrentHashMap<String, BluetoothEntity> bluetoothMap = new ConcurrentHashMap<>();
    /**
     * 已连接蓝牙
     */
    private ConcurrentHashMap<String, BlueToothConnect> connectMap = new ConcurrentHashMap<>();


    public static BlueToothManager getInstance() {

        blueToothManager.bluetoothAdapter.isEnabled();





        return blueToothManager;
    }

    /**
     * 初始化
     */
    public static void init(Context cxt) {
        if (blueToothManager == null) {
            synchronized (BlueToothManager.class) {
                if (blueToothManager == null) {
                    blueToothManager = new BlueToothManager();
                    blueToothManager.cxt = cxt;
                    // 接收广播
                    new BlueToothReceiver(cxt, new BlueToothStatusListener());
                }
            }
        }
    }

    /**
     * 设置回调
     */
    public void setCallBack(BlueToothCallBack callBack) {
        this.toothCallBack = callBack;
    }

    /**
     * 搜索蓝牙
     */
    public void discoveryBlueTooth() {
        // 清空蓝牙列表
        bluetoothMap.clear();
        if (ActivityCompat.checkSelfPermission(cxt, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            toothCallBack.error();
            return;
        }
        bluetoothAdapter.startDiscovery();
    }


    /**
     * 获取蓝牙
     */
    public Map<String, BluetoothEntity> getBluetoothMap() {
        return bluetoothMap;
    }

    /**
     * 获取已连接蓝牙
     */
    public ConcurrentHashMap<String, BlueToothConnect> getConnectMap() {
        return connectMap;
    }


    /**
     * 连接蓝牙
     */
    public void connectBlueTooth(String address, BlueToothConnectCallBack callBack) {
        BluetoothEntity bte = bluetoothMap.get(address);
        if (null != bte) {
            BlueToothConnect blueTooth = connectMap.get(address);
            if (null == blueTooth) {
                // 根据类型判断
                BluetoothClassic bt = new BluetoothClassic(cxt);
                bt.setCallBack(callBack);
                bt.connectBlueTooth(bte.getDev());
                blueTooth = new BlueToothConnect();
                blueTooth.setBlueTooth(bt);
                blueTooth.setAddress(address);
                connectMap.put(address, blueTooth);
            } else if (!blueTooth.isConnect()) {
                BlueTooth bt = blueTooth.getBlueTooth();
                bt.setCallBack(callBack);
                bt.connectBlueTooth(bte.getDev());
            } else {
                BlueTooth bt = blueTooth.getBlueTooth();
                bt.setCallBack(callBack);
            }
        } else {
            toothCallBack.error();
        }
    }

    /**
     * 关闭蓝牙
     */
    public void closeConnect(String address) {
        BlueToothConnect blueTooth = connectMap.get(address);
        if (null != blueTooth) {
            if (blueTooth.isConnect()) {
                BlueTooth toothBlueTooth = blueTooth.getBlueTooth();
                toothBlueTooth.closeConnectBlueTooth();
            }
            connectMap.remove(address);
        }
    }

    /**
     * 发送数据
     */
    public void sendData(String address, byte[] data) {
        BlueToothConnect blueTooth = connectMap.get(address);
        if (null != blueTooth) {
            if (blueTooth.isConnect()) {
                // 写数据
                blueTooth.getBlueTooth().writeData(data);
            } else {
                toothCallBack.error();
            }
        } else {
            toothCallBack.error();
        }
    }

    /**
     * 获取回调
     */
    public BlueToothCallBack getToothCallBack() {
        return toothCallBack;
    }
}

