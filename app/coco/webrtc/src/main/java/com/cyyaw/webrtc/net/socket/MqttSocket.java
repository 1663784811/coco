package com.cyyaw.webrtc.net.socket;

import android.util.Log;

import com.cyyaw.webrtc.net.SocketManager;
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


    private static MqttSocket mqttSocket = null;


    private final SocketReceiveDataEvent receiveEvent;

    private MqttClient client;

    private boolean connectFlag = false;

    private MqttSocket(SocketReceiveDataEvent receiveEvent) {
        this.receiveEvent = receiveEvent;
    }


    public static void init(String appId, String token, SocketReceiveDataEvent socketManager) {
        if (mqttSocket == null) {
            synchronized (MqttSocket.class) {
                if (mqttSocket == null) {
                    MqttSocket rest = new MqttSocket(socketManager);
                    try {
                        MqttClient client = new MqttClient("", appId, new MemoryPersistence());
                        // MQTT 连接选项
                        MqttConnectOptions connOpts = new MqttConnectOptions();
                        // 用户名
                        connOpts.setUserName("emqx_test");
                        // 密码
                        connOpts.setPassword("emqx_test_password".toCharArray());
                        // 保留会话
                        connOpts.setCleanSession(true);
                        // 设置回调
                        client.setCallback(rest);
                        // 建立连接
                        client.connect(connOpts);
                    } catch (MqttException e) {
                        Log.e(TAG, "init: 错误.....");
                    }
                    mqttSocket = rest;
                }
            }
        }
    }

    /**
     * 连接失去
     */
    @Override
    public void connectionLost(Throwable cause) {
        // 30秒内重新连接
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


    @Override
    public void sendCreateRoom(String room, int roomSize, String myId) {


    }

    @Override
    public void sendInvite(String room, String myId, List<String> users, boolean audioOnly) {

    }

    @Override
    public void sendLeave(String myId, String room, String userId) {

    }

    @Override
    public void sendRing(String myId, String targetId, String room) {

    }

    @Override
    public void sendRefuse(String room, String inviteId, String myId, int refuseType) {

    }

    @Override
    public void sendCancel(String mRoomId, String myId, List<String> userIds) {

    }

    @Override
    public void sendJoin(String room, String myId) {

    }

    @Override
    public void sendOffer(String myId, String userId, String sdp) {

    }

    @Override
    public void sendAnswer(String myId, String userId, String sdp) {

    }

    @Override
    public void sendIceCandidate(String myId, String userId, String id, int label, String candidate) {

    }

    @Override
    public void sendTransAudio(String myId, String userId) {

    }

    @Override
    public void sendDisconnect(String room, String myId, String userId) {

    }


}
