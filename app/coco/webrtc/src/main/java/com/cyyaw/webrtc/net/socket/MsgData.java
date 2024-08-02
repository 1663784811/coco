package com.cyyaw.webrtc.net.socket;


/**
 * 消息数据
 */
public class MsgData {

    /**
     * 消息类型
     */
    private String type;

    /**
     * 内容
     */
    private String data;

    /**
     * 发送消息者
     */
    private String from;

    /**
     * 接收消息者
     */
    private String to;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
