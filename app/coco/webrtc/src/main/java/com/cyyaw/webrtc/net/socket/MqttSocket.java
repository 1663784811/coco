package com.cyyaw.webrtc.net.socket;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cyyaw.webrtc.net.SocketReceiveDataEvent;
import com.cyyaw.webrtc.utils.StringUtils;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Log.e(TAG, "messageArrived: ");
        String msg = new String(message.getPayload());
        MsgData msgData = JSON.parseObject(msg).toJavaObject(MsgData.class);
        String data = msgData.getData();
        String type = msgData.getType();
        if ("webrtc".equals(type)) {
            receiveEvent.onReceiveWebRtc(data);
        } else if ("chat".equals(type)) {
            String to = msgData.getTo();
            String from = msgData.getFrom();
            receiveEvent.onReceiveChat(from, to, data);
        }
    }

    /**
     * 消息发送完成后被调用
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        Log.e(TAG, "deliveryComplete: ");
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

    /**
     * ------------------------------发送消息----------------------------------------
     */
    @Override
    public void sendAskRoom(int roomSize, String myId) {
        Map<String, Object> map = new HashMap<>();
        map.put("eventName", "__create");
        Map<String, Object> childMap = new HashMap<>();
        childMap.put("roomSize", roomSize);
        childMap.put("userID", myId);
        map.put("data", childMap);
        org.json.JSONObject object = new org.json.JSONObject(map);
        String jsonString = object.toString();

        MsgData msgData = new MsgData();
        msgData.setType("webrtc");
        msgData.setData(jsonString);
        msgData.setFrom(myId);
        msgData.setTo("");
        sendData(JSONObject.toJSONString(msgData));
    }

    @Override
    public void sendInvite(String room, String myId, List<String> users, boolean audioOnly) {
        Map<String, Object> map = new HashMap<>();
        map.put("eventName", "__invite");
        Map<String, Object> childMap = new HashMap<>();
        childMap.put("room", room);
        childMap.put("audioOnly", audioOnly);
        childMap.put("inviteID", myId);
        String join = StringUtils.listToString(users);
        childMap.put("userList", join);
        map.put("data", childMap);
        org.json.JSONObject object = new org.json.JSONObject(map);
        String jsonString = object.toString();

        MsgData msgData = new MsgData();
        msgData.setType("webrtc");
        msgData.setData(jsonString);
        msgData.setFrom(myId);
        msgData.setTo("");
        sendData(JSONObject.toJSONString(msgData));
    }

    @Override
    public void sendLeave(String myId, String room, String targetId) {
        Map<String, Object> map = new HashMap<>();
        map.put("eventName", "__leave");
        Map<String, Object> childMap = new HashMap<>();
        childMap.put("room", room);
        childMap.put("fromID", myId);
        childMap.put("userID", targetId);
        map.put("data", childMap);
        org.json.JSONObject object = new org.json.JSONObject(map);
        String jsonString = object.toString();

        MsgData msgData = new MsgData();
        msgData.setType("webrtc");
        msgData.setData(jsonString);
        msgData.setFrom(myId);
        msgData.setTo(targetId);
        sendData(JSONObject.toJSONString(msgData));
    }

    @Override
    public void sendRing(String myId, String targetId, String room) {
        Map<String, Object> map = new HashMap<>();
        map.put("eventName", "__ring");
        Map<String, Object> childMap = new HashMap<>();
        childMap.put("fromID", myId);
        childMap.put("toID", targetId);
        childMap.put("room", room);
        map.put("data", childMap);
        org.json.JSONObject object = new org.json.JSONObject(map);
        final String jsonString = object.toString();

        MsgData msgData = new MsgData();
        msgData.setType("webrtc");
        msgData.setData(jsonString);
        msgData.setFrom(myId);
        msgData.setTo(targetId);
        sendData(JSONObject.toJSONString(msgData));
    }

    @Override
    public void sendRefuse(String room, String targetId, String myId, int refuseType) {
        Map<String, Object> map = new HashMap<>();
        map.put("eventName", "__reject");
        Map<String, Object> childMap = new HashMap<>();
        childMap.put("room", room);
        childMap.put("toID", targetId);
        childMap.put("fromID", myId);
        childMap.put("refuseType", String.valueOf(refuseType));
        map.put("data", childMap);
        org.json.JSONObject object = new org.json.JSONObject(map);
        final String jsonString = object.toString();

        MsgData msgData = new MsgData();
        msgData.setType("webrtc");
        msgData.setData(jsonString);
        msgData.setFrom(myId);
        msgData.setTo(targetId);
        sendData(JSONObject.toJSONString(msgData));
    }

    @Override
    public void sendCancel(String mRoomId, String myId, List<String> userIds) {
        Map<String, Object> map = new HashMap<>();
        map.put("eventName", "__cancel");
        Map<String, Object> childMap = new HashMap<>();
        childMap.put("inviteID", myId);
        childMap.put("room", mRoomId);
        String join = StringUtils.listToString(userIds);
        childMap.put("userList", join);
        map.put("data", childMap);
        org.json.JSONObject object = new org.json.JSONObject(map);
        String jsonString = object.toString();

        MsgData msgData = new MsgData();
        msgData.setType("webrtc");
        msgData.setData(jsonString);
        msgData.setFrom(myId);
        msgData.setTo("");
        sendData(JSONObject.toJSONString(msgData));
    }

    @Override
    public void sendJoin(String room, String myId) {
        Map<String, Object> map = new HashMap<>();
        map.put("eventName", "__join");
        Map<String, String> childMap = new HashMap<>();
        childMap.put("room", room);
        childMap.put("userID", myId);
        map.put("data", childMap);
        org.json.JSONObject object = new org.json.JSONObject(map);
        String jsonString = object.toString();

        MsgData msgData = new MsgData();
        msgData.setType("webrtc");
        msgData.setData(jsonString);
        msgData.setFrom(myId);
        msgData.setTo("");
        sendData(JSONObject.toJSONString(msgData));
    }

    @Override
    public void sendOffer(String myId, String targetId, String sdp) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> childMap = new HashMap<>();
        childMap.put("sdp", sdp);
        childMap.put("userID", targetId);
        childMap.put("fromID", myId);
        map.put("data", childMap);
        map.put("eventName", "__offer");
        org.json.JSONObject object = new org.json.JSONObject(map);
        String jsonString = object.toString();

        MsgData msgData = new MsgData();
        msgData.setType("webrtc");
        msgData.setData(jsonString);
        msgData.setFrom(myId);
        msgData.setTo(targetId);
        sendData(JSONObject.toJSONString(msgData));
    }

    @Override
    public void sendAnswer(String myId, String targetId, String sdp) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> childMap = new HashMap<>();
        childMap.put("sdp", sdp);
        childMap.put("fromID", myId);
        childMap.put("userID", targetId);
        map.put("data", childMap);
        map.put("eventName", "__answer");
        org.json.JSONObject object = new org.json.JSONObject(map);
        String jsonString = object.toString();

        MsgData msgData = new MsgData();
        msgData.setType("webrtc");
        msgData.setData(jsonString);
        msgData.setFrom(myId);
        msgData.setTo(targetId);
        sendData(JSONObject.toJSONString(msgData));
    }

    @Override
    public void sendIceCandidate(String myId, String targetId, String id, int label, String candidate) {
        Map<String, Object> map = new HashMap<>();
        map.put("eventName", "__ice_candidate");
        Map<String, Object> childMap = new HashMap<>();
        childMap.put("userID", targetId);
        childMap.put("fromID", myId);
        childMap.put("id", id);
        childMap.put("label", label);
        childMap.put("candidate", candidate);
        map.put("data", childMap);
        org.json.JSONObject object = new org.json.JSONObject(map);
        String jsonString = object.toString();

        MsgData msgData = new MsgData();
        msgData.setType("webrtc");
        msgData.setData(jsonString);
        msgData.setFrom(myId);
        msgData.setTo(targetId);
        sendData(JSONObject.toJSONString(msgData));
    }

    @Override
    public void sendTransAudio(String myId, String targetId) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> childMap = new HashMap<>();
        childMap.put("fromID", myId);
        childMap.put("userID", targetId);
        map.put("data", childMap);
        map.put("eventName", "__audio");
        org.json.JSONObject object = new org.json.JSONObject(map);
        final String jsonString = object.toString();

        MsgData msgData = new MsgData();
        msgData.setType("webrtc");
        msgData.setData(jsonString);
        msgData.setFrom(myId);
        msgData.setTo(targetId);
        sendData(JSONObject.toJSONString(msgData));
    }

    @Override
    public void sendDisconnect(String room, String myId, String targetId) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> childMap = new HashMap<>();
        childMap.put("fromID", myId);
        childMap.put("userID", targetId);
        childMap.put("room", room);
        map.put("data", childMap);
        map.put("eventName", "__disconnect");
        org.json.JSONObject object = new org.json.JSONObject(map);
        String jsonString = object.toString();

        MsgData msgData = new MsgData();
        msgData.setType("webrtc");
        msgData.setData(jsonString);
        msgData.setFrom(myId);
        msgData.setTo(targetId);
        sendData(JSONObject.toJSONString(msgData));
    }

    @Override
    public void sendChatMsg(String sendUserId, String toUserid, String data) {
        MsgData msgData = new MsgData();
        msgData.setType("chat");
        msgData.setData(data);
        msgData.setFrom(sendUserId);
        msgData.setTo(toUserid);
        sendData(JSONObject.toJSONString(msgData));
    }


}
