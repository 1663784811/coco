package com.cyyaw.webrtc.net.socket;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.cyyaw.webrtc.net.SocketReceiveDataEvent;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.List;

/**
 * Mqtt 网络
 */
public class MqttSocket implements MqttCallback, SocketConnect {
    private final static String TAG = MqttSocket.class.getName();

    private static final String broker = "tcp://192.168.0.158:1883";
    private static final String userName = "admin";
    private static final String password = "123456";
    private static final String topic = "mqtt_service";
    private static final int qos = 1;

    private static MqttSocket mqttSocket = null;


    private final SocketReceiveDataEvent receiveEvent;

    private MqttClient client;

    private boolean connectFlag;

    private MqttSocket(SocketReceiveDataEvent receiveEvent) {
        this.receiveEvent = receiveEvent;
    }


    public static SocketConnect init(String appId, String token, String userId, SocketReceiveDataEvent socketManager) {
        if (mqttSocket == null) {
            synchronized (MqttSocket.class) {
                if (mqttSocket == null) {
                    String clientId = appId + "_" + userId;
                    MqttSocket rest = new MqttSocket(socketManager);
                    MqttClient client = null;
                    try {
                        client = new MqttClient(broker, clientId, new MemoryPersistence());
                        // MQTT 连接选项
                        MqttConnectOptions connOpts = new MqttConnectOptions();
                        // 用户名
                        connOpts.setUserName(userName);
                        // 密码
                        connOpts.setPassword(password.toCharArray());
                        // 保留会话
                        connOpts.setCleanSession(true);
                        // 设置回调
                        client.setCallback(rest);
                        // 建立连接
                        client.connect(connOpts);
                        // 订阅
                        client.subscribe(clientId);
                    } catch (MqttException e) {
                        e.printStackTrace();
                        Log.e(TAG, "init: 错误....." + e.getMessage());
                    }
                    rest.client = client;
                    mqttSocket = rest;
                    mqttSocket.connectFlag = true;
                    return mqttSocket;
                }
            }
        } else {


        }
        return null;
    }

    /**
     * 连接失去
     */
    @Override
    public void connectionLost(Throwable cause) {
        // 30秒内重新连接
        mqttSocket.connectFlag = false;
    }

    /**
     * 消息到达
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {

    }

    /**
     * 消息发送完成后被调用
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

    // =============================================================
    private void sendData(String msg) {
        if (null != client) {
            MqttMessage mqtt = new MqttMessage();
            mqtt.setQos(qos);
            mqtt.setPayload(msg.getBytes());
            try {
                client.publish(topic, mqtt);
            } catch (MqttException e) {
                throw new RuntimeException(e);
            }
        }
    }
    // =============================================================


    @Override
    public void sendAskRoom(int roomSize, String myId) {
        MsgData msgData = new MsgData();
        msgData.setType("webrtc");
        msgData.setData(String.valueOf(roomSize));
        msgData.setFrom(myId);
        msgData.setTo("");
        sendData(JSONObject.toJSONString(msgData));
    }

    @Override
    public void sendInvite(String room, String myId, List<String> users, boolean audioOnly) {
        MsgData msgData = new MsgData();
        msgData.setType("webrtc");
        msgData.setData(String.valueOf(users));
        msgData.setFrom(myId);
        msgData.setTo("");
        sendData(JSONObject.toJSONString(msgData));
    }

    @Override
    public void sendLeave(String myId, String room, String userId) {
        MsgData msgData = new MsgData();
        msgData.setType("webrtc");
        msgData.setData(String.valueOf(userId));
        msgData.setFrom(myId);
        msgData.setTo("");
        sendData(JSONObject.toJSONString(msgData));
    }

    @Override
    public void sendRing(String myId, String targetId, String room) {
        MsgData msgData = new MsgData();
        msgData.setType("webrtc");
        msgData.setData(String.valueOf(room));
        msgData.setFrom(myId);
        msgData.setTo("");
        sendData(JSONObject.toJSONString(msgData));
    }

    @Override
    public void sendRefuse(String room, String inviteId, String myId, int refuseType) {
        MsgData msgData = new MsgData();
        msgData.setType("webrtc");
        msgData.setData(String.valueOf(refuseType));
        msgData.setFrom(myId);
        msgData.setTo("");
        sendData(JSONObject.toJSONString(msgData));
    }

    @Override
    public void sendCancel(String mRoomId, String myId, List<String> userIds) {
        MsgData msgData = new MsgData();
        msgData.setType("webrtc");
        msgData.setData(String.valueOf(userIds));
        msgData.setFrom(myId);
        msgData.setTo("");
        sendData(JSONObject.toJSONString(msgData));
    }

    @Override
    public void sendJoin(String room, String myId) {
        MsgData msgData = new MsgData();
        msgData.setType("webrtc");
        msgData.setData(String.valueOf(myId));
        msgData.setFrom(myId);
        msgData.setTo("");
        sendData(JSONObject.toJSONString(msgData));
    }

    @Override
    public void sendOffer(String myId, String userId, String sdp) {
        MsgData msgData = new MsgData();
        msgData.setType("webrtc");
        msgData.setData(String.valueOf(sdp));
        msgData.setFrom(myId);
        msgData.setTo("");
        sendData(JSONObject.toJSONString(msgData));
    }

    @Override
    public void sendAnswer(String myId, String userId, String sdp) {
        MsgData msgData = new MsgData();
        msgData.setType("webrtc");
        msgData.setData(String.valueOf(sdp));
        msgData.setFrom(myId);
        msgData.setTo("");
        sendData(JSONObject.toJSONString(msgData));
    }

    @Override
    public void sendIceCandidate(String myId, String userId, String id, int label, String candidate) {
        MsgData msgData = new MsgData();
        msgData.setType("webrtc");
        msgData.setData(String.valueOf(candidate));
        msgData.setFrom(myId);
        msgData.setTo("");
        sendData(JSONObject.toJSONString(msgData));
    }

    @Override
    public void sendTransAudio(String myId, String userId) {
        MsgData msgData = new MsgData();
        msgData.setType("webrtc");
        msgData.setData(String.valueOf(userId));
        msgData.setFrom(myId);
        msgData.setTo("");
        sendData(JSONObject.toJSONString(msgData));
    }

    @Override
    public void sendDisconnect(String room, String myId, String userId) {
        MsgData msgData = new MsgData();
        msgData.setType("webrtc");
        msgData.setData(String.valueOf(room));
        msgData.setFrom(myId);
        msgData.setTo("");
        sendData(JSONObject.toJSONString(msgData));
    }


}
