package com.cyyaw.bluetooth.out;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import com.cyyaw.bluetooth.device.BlueTooth;
import com.cyyaw.bluetooth.entity.BlueToothConnect;
import com.cyyaw.bluetooth.device.BluetoothClassic;
import com.cyyaw.bluetooth.entity.BtStatus;
import com.cyyaw.bluetooth.entity.BtEntity;
import com.cyyaw.bluetooth.receiver.BlueToothReceiver;
import com.cyyaw.bluetooth.receiver.BlueToothStatusListener;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 蓝牙管理器
 */
public class BlueToothManager implements BlueToothConnectCallBack {

    private static volatile BlueToothManager blueToothManager;
    // 线程池
    private static final Executor threadPool = Executors.newCachedThreadPool();
    protected final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private Context cxt;
    /**
     * ==========================  回调
     */
    private BlueToothFindCallBack findCallBack;
    private static BlueToothConnectCallBack connectCallBack;
    /**
     * 蓝牙列表
     */
    private ConcurrentHashMap<String, BtEntity> bluetoothMap = new ConcurrentHashMap<>();
    /**
     * 已连接蓝牙
     */
    private ConcurrentHashMap<String, BlueToothConnect> connectMap = new ConcurrentHashMap<>();

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
    public static void setCallBack(BlueToothFindCallBack callBack) {
        if (blueToothManager != null) {
            blueToothManager.findCallBack = callBack;
        }
    }

    public static void connectCallBack(BlueToothConnectCallBack callBack) {
        if (blueToothManager != null) {
            blueToothManager.connectCallBack = callBack;
        }
    }

    /**
     * 搜索蓝牙
     */
    public static void discoveryBlueTooth() {
        threadPool.execute(() -> {
            // 清空蓝牙列表
            if (null != blueToothManager) {
                blueToothManager.bluetoothMap.clear();
                if (ActivityCompat.checkSelfPermission(blueToothManager.cxt, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                    BlueToothFindCallBack tb = getToothCallBack();
                    if (null != tb) {
                        tb.error();
                    }
                    return;
                }
                blueToothManager.bluetoothAdapter.startDiscovery();
            }
        });
    }


    /**
     * 获取蓝牙
     */
    public static Map<String, BtEntity> getBluetoothMap() {
        if (null != blueToothManager) {
            return blueToothManager.bluetoothMap;
        }
        return null;
    }


    /**
     * 获取蓝牙
     */
    public static BtEntity getBluetooth(String address) {
        Map<String, BtEntity> map = getBluetoothMap();
        if (null != map) {
            return map.get(address);
        }
        return null;
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
    public static void connectBlueTooth(String address) {
        if (null != blueToothManager) {
            BtEntity bte = blueToothManager.bluetoothMap.get(address);
            if (null != bte) {
                BlueToothConnect blueTooth = blueToothManager.connectMap.get(address);
                if (null == blueTooth) {
                    // 根据类型判断
                    BluetoothClassic bt = new BluetoothClassic(blueToothManager.cxt);
                    bt.setCallBack(blueToothManager);
                    bt.connectBlueTooth(bte.getDev());
                    blueTooth = new BlueToothConnect();
                    blueTooth.setBlueTooth(bt);
                    blueTooth.setAddress(address);
                    blueToothManager.connectMap.put(address, blueTooth);
                } else if (!blueTooth.isConnect()) {
                    BlueTooth bt = blueTooth.getBlueTooth();
                    bt.setCallBack(blueToothManager);
                    bt.connectBlueTooth(bte.getDev());
                } else {
                    blueToothManager.statusCallBack(address, BtStatus.SUCCESS);
                }
            } else {
                BlueToothFindCallBack toothCallBack = getToothCallBack();
                if (null != toothCallBack) {
                    toothCallBack.error();
                }
            }
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
    public static void sendData(String address, byte[] data) {
        if (null != blueToothManager) {
            BlueToothConnect blueTooth = blueToothManager.connectMap.get(address);
            if (null != blueTooth) {
                if (blueTooth.isConnect()) {
                    // 写数据
                    blueTooth.getBlueTooth().writeData(data);
                } else {
                    BlueToothFindCallBack tb = getToothCallBack();
                    if (null != tb) {
                        tb.error();
                    }
                }
            } else {
                BlueToothFindCallBack tb = getToothCallBack();
                if (null != tb) {
                    tb.error();
                }
            }
        }
    }

    /**
     * 获取回调
     */
    public static BlueToothFindCallBack getToothCallBack() {
        if (null != blueToothManager) {
            return blueToothManager.findCallBack;
        }
        return null;
    }

    @Override
    public void statusCallBack(String address, BtStatus status) {
        if (status == BtStatus.SUCCESS) {
            BlueToothConnect blueTooth = connectMap.get(address);
            blueTooth.setConnect(true);
        } else if (status == BtStatus.FAIL) {
            BlueToothConnect blueTooth = connectMap.get(address);
            blueTooth.setConnect(false);
        }
        if (null != connectCallBack) {
            connectCallBack.statusCallBack(address, status);
        }
    }

    @Override
    public void readData(String address, byte[] data) {
        if (null != connectCallBack) {
            connectCallBack.readData(address, data);
        }
    }
}

