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
    @Override
    public void sendWebrtcData(String to, String msg) {
        if (null != client) {
            MqttMessage mqtt = new MqttMessage();
            mqtt.setQos(qos);
            mqtt.setPayload(msg.getBytes());
            try {
                client.publish(topic + ".webrtc." + to, mqtt);
            } catch (MqttException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void sendChatData(String to, String msg) {
        if (null != client) {
            MqttMessage mqtt = new MqttMessage();
            mqtt.setQos(qos);
            mqtt.setPayload(msg.getBytes());
            try {
                client.publish(topic + ".chat." + to, mqtt);
            } catch (MqttException e) {
                throw new RuntimeException(e);
            }
        }
    }
    // =============================================================
}
