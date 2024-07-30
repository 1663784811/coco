package com.cyyaw.webrtc.net.socket;

import android.annotation.SuppressLint;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.cyyaw.webrtc.net.StatusCallBack;
import com.cyyaw.webrtc.WebRtcConfig;
import com.cyyaw.webrtc.net.SocketManager;
import com.cyyaw.webrtc.net.SocketReceiveDataEvent;
import com.cyyaw.webrtc.utils.StringUtils;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.X509TrustManager;

/**
 *
 */
public class MyWebSocket extends WebSocketClient implements SocketConnect {

    private final static String TAG = MyWebSocket.class.getName();

    private static MyWebSocket myWebSocket = null;
    private static final String url = "ws://192.168.0.103:3000/ws";

    /**
     * 接收回调
     */
    private final SocketReceiveDataEvent receiveEvent;

    private final StatusCallBack statusCallBack;


    private MyWebSocket(URI serverUri, SocketReceiveDataEvent receiveEvent, StatusCallBack statusCallBack) {
        super(serverUri);
        this.receiveEvent = receiveEvent;
        this.statusCallBack = statusCallBack;
    }

    public static SocketConnect init(String appId, String token, SocketManager instance, StatusCallBack statusCallBack) {
        if (myWebSocket == null || !myWebSocket.isOpen()) {
            URI uri;
            try {
                String urls = url + "/" + appId + "/" + token;
                uri = new URI(urls);
            } catch (URISyntaxException e) {
                e.printStackTrace();
                return null;
            }
            myWebSocket = new MyWebSocket(uri, instance, statusCallBack);
            // 设置wss
//            if (url.startsWith("wss")) {
//                try {
//                    SSLContext sslContext = SSLContext.getInstance("TLS");
//                    if (sslContext != null) {
//                        sslContext.init(null, new TrustManager[]{new MyWebSocket.TrustManagerTest()}, new SecureRandom());
//                    }
//                    SSLSocketFactory factory = null;
//                    if (sslContext != null) {
//                        factory = sslContext.getSocketFactory();
//                    }
//
//                    if (factory != null) {
//                        myWebSocket.setSocket(factory.createSocket());
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
            // 开始connect
            myWebSocket.connect();
        }
        return myWebSocket;
    }


    /**
     * 关闭连接
     */
    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.e("关闭连接,10秒后尝试重新连接...", "onClose:" + reason + "remote:" + remote);
        this.receiveEvent.logout("onClose");
        if (null != statusCallBack) {
            statusCallBack.netWorkStatus(StatusCallBack.NetStatus.CLOSE, "关闭连接");
        }
        WebRtcConfig.run(() -> {
            try {
                Thread.sleep(30000);
                reconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        this.receiveEvent.onConnectError();
    }

    /**
     * 错误
     */
    @Override
    public void onError(Exception ex) {
        Log.e("连接错误", "onError:" + ex.toString());
        this.receiveEvent.logout("onError");
        if (null != statusCallBack) {
            statusCallBack.netWorkStatus(StatusCallBack.NetStatus.ERROR, "连接错误:" + ex.toString() + "3秒后尝试重连...");
        }
        this.receiveEvent.onConnectError();
    }

    /**
     * 打开连接
     */
    @Override
    public void onOpen(ServerHandshake handshake) {
        Log.e("打开连接", "onOpen");
        this.receiveEvent.onOpenCallBack();
        if (null != statusCallBack) {
            statusCallBack.netWorkStatus(StatusCallBack.NetStatus.CONNECT, "打开连接");
        }
    }

    /**
     * 收到消息
     */
    @Override
    public void onMessage(String message) {
        Log.d(TAG, "收到消息 Receive <<<====  " + message);
        handleMessage(message);
    }

    /**
     * 发送消息
     */
    private void sendData(String data) {
        Log.d(TAG, "发送消息 send ====>>>  " + data);
        if (isOpen()) {
            try {
                send(data);
            } catch (Exception e) {
                if (null != statusCallBack) {
                    statusCallBack.netWorkStatus(StatusCallBack.NetStatus.MSGERROR, "发送消息错误");
                }
            }
        } else {
            this.receiveEvent.onConnectError();
        }
    }
    // ---------------------------------------处理接收消息-------------------------------------

    private void handleMessage(String message) {
        receiveEvent.onReceiveWebRtc(message);
    }

    /**
     * ------------------------------发送消息----------------------------------------
     */
    public void sendAskRoom(int roomSize, String myId) {
        Map<String, Object> map = new HashMap<>();
        map.put("eventName", "__create");
        Map<String, Object> childMap = new HashMap<>();
        childMap.put("roomSize", roomSize);
        childMap.put("userID", myId);
        map.put("data", childMap);
        JSONObject object = new JSONObject(map);
        final String jsonString = object.toString();
        sendData(jsonString);
    }

    // 发送邀请
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
        JSONObject object = new JSONObject(map);
        final String jsonString = object.toString();
        sendData(jsonString);
    }

    // 取消邀请
    public void sendCancel(String mRoomId, String useId, List<String> users) {
        Map<String, Object> map = new HashMap<>();
        map.put("eventName", "__cancel");
        Map<String, Object> childMap = new HashMap<>();
        childMap.put("inviteID", useId);
        childMap.put("room", mRoomId);
        String join = StringUtils.listToString(users);
        childMap.put("userList", join);
        map.put("data", childMap);
        JSONObject object = new JSONObject(map);
        final String jsonString = object.toString();
        sendData(jsonString);
    }

    // 发送响铃通知
    public void sendRing(String myId, String targetId, String room) {
        Map<String, Object> map = new HashMap<>();
        map.put("eventName", "__ring");
        Map<String, Object> childMap = new HashMap<>();
        childMap.put("fromID", myId);
        childMap.put("toID", targetId);
        childMap.put("room", room);
        map.put("data", childMap);
        JSONObject object = new JSONObject(map);
        final String jsonString = object.toString();
        sendData(jsonString);
    }

    //加入房间
    public void sendJoin(String room, String myId) {
        Map<String, Object> map = new HashMap<>();
        map.put("eventName", "__join");
        Map<String, String> childMap = new HashMap<>();
        childMap.put("room", room);
        childMap.put("userID", myId);
        map.put("data", childMap);
        JSONObject object = new JSONObject(map);
        final String jsonString = object.toString();
        sendData(jsonString);
    }

    // 拒接接听
    public void sendRefuse(String room, String targetId, String myId, int refuseType) {
        Map<String, Object> map = new HashMap<>();
        map.put("eventName", "__reject");

        Map<String, Object> childMap = new HashMap<>();
        childMap.put("room", room);
        childMap.put("toID", targetId);
        childMap.put("fromID", myId);
        childMap.put("refuseType", String.valueOf(refuseType));

        map.put("data", childMap);
        JSONObject object = new JSONObject(map);
        final String jsonString = object.toString();
        sendData(jsonString);
    }

    // 离开房间
    public void sendLeave(String myId, String room, String userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("eventName", "__leave");
        Map<String, Object> childMap = new HashMap<>();
        childMap.put("room", room);
        childMap.put("fromID", myId);
        childMap.put("userID", userId);
        map.put("data", childMap);
        JSONObject object = new JSONObject(map);
        final String jsonString = object.toString();
        sendData(jsonString);
    }

    // send offer
    public void sendOffer(String myId, String userId, String sdp) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> childMap = new HashMap<>();
        childMap.put("sdp", sdp);
        childMap.put("userID", userId);
        childMap.put("fromID", myId);
        map.put("data", childMap);
        map.put("eventName", "__offer");
        JSONObject object = new JSONObject(map);
        final String jsonString = object.toString();
        sendData(jsonString);
    }

    // send answer
    public void sendAnswer(String myId, String userId, String sdp) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> childMap = new HashMap<>();
        childMap.put("sdp", sdp);
        childMap.put("fromID", myId);
        childMap.put("userID", userId);
        map.put("data", childMap);
        map.put("eventName", "__answer");
        JSONObject object = new JSONObject(map);
        final String jsonString = object.toString();
        sendData(jsonString);
    }

    // send ice-candidate
    public void sendIceCandidate(String myId, String userId, String id, int label, String candidate) {
        Map<String, Object> map = new HashMap<>();
        map.put("eventName", "__ice_candidate");
        Map<String, Object> childMap = new HashMap<>();
        childMap.put("userID", userId);
        childMap.put("fromID", myId);
        childMap.put("id", id);
        childMap.put("label", label);
        childMap.put("candidate", candidate);
        map.put("data", childMap);
        JSONObject object = new JSONObject(map);
        final String jsonString = object.toString();
        sendData(jsonString);
    }

    // 切换到语音
    public void sendTransAudio(String myId, String userId) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> childMap = new HashMap<>();
        childMap.put("fromID", myId);
        childMap.put("userID", userId);
        map.put("data", childMap);
        map.put("eventName", "__audio");
        JSONObject object = new JSONObject(map);
        final String jsonString = object.toString();
        sendData(jsonString);
    }

    // 断开重连
    public void sendDisconnect(String room, String myId, String userId) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> childMap = new HashMap<>();
        childMap.put("fromID", myId);
        childMap.put("userID", userId);
        childMap.put("room", room);
        map.put("data", childMap);
        map.put("eventName", "__disconnect");
        JSONObject object = new JSONObject(map);
        final String jsonString = object.toString();
        sendData(jsonString);
    }

    @Override
    public void sendChatMsg(String myId, String toUserid, String data) {

    }


    // 忽略证书
    public static class TrustManagerTest implements X509TrustManager {

        @SuppressLint("TrustAllX509TrustManager")
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @SuppressLint("TrustAllX509TrustManager")
        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

}
