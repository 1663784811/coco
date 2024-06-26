package com.cyyaw.bluetooth.device;


/**
 *
 */
public enum BtStatus {

    CONNECTTING("正在连接..."), SUCCESS("连接成功"), FAIL("连接失败");

    private String note;

    BtStatus(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }
}
