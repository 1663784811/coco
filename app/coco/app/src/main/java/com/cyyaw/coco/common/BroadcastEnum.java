package com.cyyaw.coco.common;

public enum BroadcastEnum {


    BLUETOOTH_BLE_SEARCH("search", "搜索低功耗蓝牙")
    ,BLUETOOTH_BLE_CONNECT("connect","连接低功耗蓝牙")
    ,BLUETOOTH_CLASSIC_SEARCH("search","搜索经典蓝牙")
    ,BLUETOOTH_CLASSIC_CONNECT("connect","连接经典蓝牙")
    ;
    ;
    // 低功耗蓝牙
    public static final String BLUETOOTH_BLE = "com.cyyaw.coco.service.BluetoothBleService";
    // 经典蓝牙
    public static final String BLUETOOTH_CLASSIC = "com.cyyaw.coco.service.BluetoothClassicService";

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
