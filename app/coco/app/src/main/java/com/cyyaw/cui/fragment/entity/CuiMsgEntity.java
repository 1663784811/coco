package com.cyyaw.cui.fragment.entity;

public class CuiMsgEntity {

    // 头像
    private String face;
    // 名称
    private String userName;
    // 消息
    private String msg;
    // 未读消息数量
    private Integer number;


    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
