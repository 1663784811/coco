package com.cyyaw.bluetooth.entity;


/**
 *
 */
public enum BtStatus {

    CONNECTTING("正在连接..."),
    SUCCESS("连接成功"),
    FAIL("连接失败"),
    SENDDTAING("正在发送数据..."),
    SENDDTAFAIL("发送数据失败"),
    SENDDTASUCCESS("发送数据成功");

    private String note;

    BtStatus(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }
}
