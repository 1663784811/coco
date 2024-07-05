package com.cyyaw.webrtc.net.socket;

import android.annotation.SuppressLint;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.cyyaw.webrtc.StatusCallBack;
import com.cyyaw.webrtc.net.SocketManager;
import com.cyyaw.webrtc.net.SocketReceiveDataEvent;

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
        try {
            Thread.sleep(10000);
            reconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        try {
            send(data);
        } catch (Exception e) {
            if (null != statusCallBack) {
                statusCallBack.netWorkStatus(StatusCallBack.NetStatus.MSGERROR, "发送消息错误");
            }
        }
    }
    // ---------------------------------------处理接收消息-------------------------------------

    private void handleMessage(String message) {
        Map map = JSON.parseObject(message, Map.class);
        String eventName = (String) map.get("eventName");
        if (eventName == null) return;
        // 登录成功
        if (eventName.equals("__login_success")) {
            handleLogin(map);
            return;
        }
        // 被邀请
        if (eventName.equals("__invite")) {
            handleInvite(map);
            return;
        }
        // 取消拨出
        if (eventName.equals("__cancel")) {
            handleCancel(map);
            return;
        }
        // 响铃
        if (eventName.equals("__ring")) {
            handleRing(map);
            return;
        }
        // 进入房间
        if (eventName.equals("__peers")) {
            handlePeers(map);
            return;
        }
        // 新人入房间
        if (eventName.equals("__new_peer")) {
            handleNewPeer(map);
            return;
        }
        // 拒绝接听
        if (eventName.equals("__reject")) {
            handleReject(map);
            return;
        }
        // offer
        if (eventName.equals("__offer")) {
            handleOffer(map);
            return;
        }
        // answer
        if (eventName.equals("__answer")) {
            handleAnswer(map);
            return;
        }
        // ice-candidate
        if (eventName.equals("__ice_candidate")) {
            handleIceCandidate(map);
        }
        // 离开房间
        if (eventName.equals("__leave")) {
            handleLeave(map);
        }
        // 切换到语音
        if (eventName.equals("__audio")) {
            handleTransAudio(map);
        }
        // 意外断开
        if (eventName.equals("__disconnect")) {
            handleDisConnect(map);
        }


    }

    private void handleDisConnect(Map map) {
        Map data = (Map) map.get("data");
        if (data != null) {
            String fromId = (String) data.get("fromID");
            this.receiveEvent.onDisConnect(fromId);
        }
    }

    private void handleTransAudio(Map map) {
        Map data = (Map) map.get("data");
        if (data != null) {
            String fromId = (String) data.get("fromID");
            this.receiveEvent.onTransAudio(fromId);
        }
    }

    private void handleLogin(Map map) {
        Map data = (Map) map.get("data");
        if (data != null) {
            String userID = (String) data.get("userID");
            String avatar = (String) data.get("avatar");
            this.receiveEvent.loginSuccess(userID, avatar);
        }


    }

    private void handleIceCandidate(Map map) {
        Map data = (Map) map.get("data");
        if (data != null) {
            String userID = (String) data.get("fromID");
            String id = (String) data.get("id");
            int label = (int) data.get("label");
            String candidate = (String) data.get("candidate");
            this.receiveEvent.onIceCandidate(userID, id, label, candidate);
        }
    }

    private void handleAnswer(Map map) {
        Map data = (Map) map.get("data");
        if (data != null) {
            String sdp = (String) data.get("sdp");
            String userID = (String) data.get("fromID");
            this.receiveEvent.onAnswer(userID, sdp);
        }
    }

    private void handleOffer(Map map) {
        Map data = (Map) map.get("data");
        if (data != null) {
            String sdp = (String) data.get("sdp");
            String userID = (String) data.get("fromID");
            this.receiveEvent.onOffer(userID, sdp);
        }
    }

    private void handleReject(Map map) {
        Map data = (Map) map.get("data");
        if (data != null) {
            String fromID = (String) data.get("fromID");
            int rejectType = Integer.parseInt(String.valueOf(data.get("refuseType")));
            this.receiveEvent.onReject(fromID, rejectType);
        }
    }

    private void handlePeers(Map map) {
        Map data = (Map) map.get("data");
        if (data != null) {
            String connections = (String) data.get("connections");
            int roomSize = (int) data.get("roomSize");
            String room = (String) data.get("room");
            this.receiveEvent.onPeers(room, connections, roomSize);
        }
    }

    private void handleNewPeer(Map map) {
        Map data = (Map) map.get("data");
        if (data != null) {
            String userID = (String) data.get("userID");
            this.receiveEvent.onNewPeer(userID);
        }
    }

    private void handleRing(Map map) {
        Map data = (Map) map.get("data");
        if (data != null) {
            String fromId = (String) data.get("fromID");
            this.receiveEvent.onReceiveRing(fromId);
        }
    }

    private void handleCancel(Map map) {
        Map data = (Map) map.get("data");
        if (data != null) {
            String inviteID = (String) data.get("inviteID");
            String userList = (String) data.get("userList");
            this.receiveEvent.onCancel(inviteID);
        }
    }

    private void handleInvite(Map map) {
        Map data = (Map) map.get("data");
        if (data != null) {
            // 房间号
            String room = (String) data.get("room");
            boolean audioOnly = (boolean) data.get("audioOnly");
            // 对方id
            String inviteID = (String) data.get("inviteID");
            String userList = (String) data.get("userList");
            this.receiveEvent.onReceiveInvite(room, audioOnly, inviteID, userList);
        }
    }

    private void handleLeave(Map map) {
        Map data = (Map) map.get("data");
        if (data != null) {
            String fromID = (String) data.get("fromID");
            this.receiveEvent.onLeave(fromID);
        }
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
        String join = listToString(users);
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
        String join = listToString(users);
        childMap.put("userList", join);
        map.put("data", childMap);
        JSONObject object = new JSONObject(map);
        final String jsonString = object.toString();
        sendData(jsonString);
    }

    // 发送响铃通知
    public void sendRing(String myId, String toId, String room) {
        Map<String, Object> map = new HashMap<>();
        map.put("eventName", "__ring");
        Map<String, Object> childMap = new HashMap<>();
        childMap.put("fromID", myId);
        childMap.put("toID", toId);
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
    public void sendRefuse(String room, String inviteID, String myId, int refuseType) {
        Map<String, Object> map = new HashMap<>();
        map.put("eventName", "__reject");

        Map<String, Object> childMap = new HashMap<>();
        childMap.put("room", room);
        childMap.put("toID", inviteID);
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

    public static String listToString(List<String> mList) {
        final String SEPARATOR = ",";
        StringBuilder sb = new StringBuilder();
        String convertedListStr;
        if (null != mList && mList.size() > 0) {
            for (String item : mList) {
                sb.append(item);
                sb.append(SEPARATOR);
            }
            convertedListStr = sb.toString();
            convertedListStr = convertedListStr.substring(0, convertedListStr.length() - SEPARATOR.length());
            return convertedListStr;
        } else return "";
    }
}
