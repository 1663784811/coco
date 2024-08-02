package com.cyyaw.webrtc.net.socket;

import android.annotation.SuppressLint;
import android.util.Log;

import com.cyyaw.webrtc.WebRtcConfig;
import com.cyyaw.webrtc.net.SocketManager;
import com.cyyaw.webrtc.net.SocketReceiveDataEvent;
import com.cyyaw.webrtc.net.StatusCallBack;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

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

    @Override
    public void sendWebrtcData(String to, String msg) {

    }

    @Override
    public void sendChatData(String to, String msg) {

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
