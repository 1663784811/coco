package com.cyyaw.coco.common;

public enum BroadcastEnum {

    BLUETOOTH_SEARCH("search","搜索蓝牙")
    ,BLUETOOTH_OBJ("BLUETOOTH_OBJ","蓝牙对象")
    ,BLUETOOTH_UN_CONNECT("BLUETOOTH_UN_CONNECT","断开蓝牙连接")
    ,BLUETOOTH_CONNECT("BLUETOOTH_CONNECT","连接蓝牙")
    ;

    public static final String BLUETOOTH_BR = "com.cyyaw.coco.service.BlueToothAbstract";
    // 低功耗蓝牙
    public static final String BLUETOOTH_BLE = "com.cyyaw.coco.service.BluetoothBleService";
    // 经典蓝牙
    public static final String BLUETOOTH_CLASSIC = "com.cyyaw.coco.service.BluetoothClassicService";


    // 蓝牙接收页面
    public static final String ACTIVITY_BLUETOOTH = "com.cyyaw.coco.activity.BluetoothActivity";

    // 首页
    public static final String ACTIVITY_HOME = "com.cyyaw.coco.activity.home.HomeView";



    public static final String STATUS_OK = "ok";
    public static final String STATUS_SERVICE_INIT = "serviceInit";






    // 操作
    private String code;

    // 提示
    private String note;

    BroadcastEnum(String code, String note) {
        this.code = code;
        this.note = note;
    }

    public String getCode() {
        return code;
    }

    public String getNote() {
        return note;
    }
}
