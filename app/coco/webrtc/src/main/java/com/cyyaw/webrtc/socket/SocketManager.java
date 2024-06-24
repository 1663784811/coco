package com.cyyaw.webrtc.socket;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.cyyaw.webrtc.VideoActivity;
import com.cyyaw.webrtc.WebRtcConfig;
import com.cyyaw.webrtc.rtc.SkyEngineKit;
import com.cyyaw.webrtc.rtc.engine.EnumType;
import com.cyyaw.webrtc.rtc.session.CallSession;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.SecureRandom;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

/**
 * 网线管理
 */
public class SocketManager implements SocketReceiveDataEvent, SocketSenDataEvent {
    private final static String TAG = SocketManager.class.getName();
    private MyWebSocket webSocket;
    private int userState;
    private String myId;

    private final Handler handler = new Handler(Looper.getMainLooper());

    private SocketManager() {

    }

    private static class Holder {
        private static final SocketManager socketManager = new SocketManager();
    }

    public static SocketManager getInstance() {
        return Holder.socketManager;
    }


    /**
     * 连接websocket
     */
    public void connect(String url, String userId, int device) {
        if (webSocket == null || !webSocket.isOpen()) {
            URI uri;
            try {
                String urls = url + "/" + userId + "/" + device;
                uri = new URI(urls);
            } catch (URISyntaxException e) {
                e.printStackTrace();
                return;
            }
            webSocket = new MyWebSocket(uri, this);
            // 设置wss
            if (url.startsWith("wss")) {
                try {
                    SSLContext sslContext = SSLContext.getInstance("TLS");
                    if (sslContext != null) {
                        sslContext.init(null, new TrustManager[]{new MyWebSocket.TrustManagerTest()}, new SecureRandom());
                    }

                    SSLSocketFactory factory = null;
                    if (sslContext != null) {
                        factory = sslContext.getSocketFactory();
                    }

                    if (factory != null) {
                        webSocket.setSocket(factory.createSocket());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // 开始connect
            webSocket.connect();
        }
    }

    public void unConnect() {
        if (webSocket != null) {
            webSocket.setConnectFlag(false);
            webSocket.close();
            webSocket = null;
        }

    }

    @Override
    public void onOpenCallBack() {
        Log.i(TAG, "socket is open!");

    }

    @Override
    public void loginSuccess(String userId, String avatar) {
        Log.i(TAG, "loginSuccess:" + userId);
        myId = userId;
        userState = 1;
    }


    // ======================================================================================     发送数据
    public void sendCreateRoom(String room, int roomSize) {
        if (webSocket != null) {
            webSocket.createRoom(room, roomSize, myId);
        }
    }

    public void sendInvite(String room, List<String> users, boolean audioOnly) {
        if (webSocket != null) {
            webSocket.sendInvite(room, myId, users, audioOnly);
        }
    }

    public void sendLeave(String room, String userId) {
        if (webSocket != null) {
            webSocket.sendLeave(myId, room, userId);
        }
    }

    public void sendRingBack(String targetId, String room) {
        if (webSocket != null) {
            webSocket.sendRing(myId, targetId, room);
        }
    }

    public void sendRefuse(String room, String inviteId, int refuseType) {
        if (webSocket != null) {
            webSocket.sendRefuse(room, inviteId, myId, refuseType);
        }
    }

    public void sendCancel(String mRoomId, List<String> userIds) {
        if (webSocket != null) {
            webSocket.sendCancel(mRoomId, myId, userIds);
        }
    }

    public void sendJoin(String room) {
        if (webSocket != null) {
            webSocket.sendJoin(room, myId);
        }
    }

    public void sendMeetingInvite(String userList) {

    }

    public void sendOffer(String userId, String sdp) {
        if (webSocket != null) {
            webSocket.sendOffer(myId, userId, sdp);
        }
    }

    public void sendAnswer(String userId, String sdp) {
        if (webSocket != null) {
            webSocket.sendAnswer(myId, userId, sdp);
        }
    }

    public void sendIceCandidate(String userId, String id, int label, String candidate) {
        if (webSocket != null) {
            webSocket.sendIceCandidate(myId, userId, id, label, candidate);
        }
    }

    public void sendTransAudio(String userId) {
        if (webSocket != null) {
            webSocket.sendTransAudio(myId, userId);
        }
    }

    public void sendDisconnect(String room, String userId) {
        if (webSocket != null) {
            webSocket.sendDisconnect(room, myId, userId);
        }
    }




    // ========================================================================================    接收数据
    @Override
    public void onInvite(String room, boolean audioOnly, String inviteId, String userList) {
        Intent intent = new Intent();
        intent.putExtra("room", room);
        intent.putExtra("audioOnly", audioOnly);
        intent.putExtra("inviteId", inviteId);
        intent.putExtra("userList", userList);
        WebRtcConfig.startActivity(intent, VideoActivity.class);
    }

    @Override
    public void onCancel(String inviteId) {
        handler.post(() -> {
            CallSession currentSession = SkyEngineKit.Instance().getCurrentSession();
            if (currentSession != null) {
                currentSession.onCancel(inviteId);
            }
        });

    }

    @Override
    public void onRing(String fromId) {
        handler.post(() -> {
            CallSession currentSession = SkyEngineKit.Instance().getCurrentSession();
            if (currentSession != null) {
                currentSession.onRingBack(fromId);
            }
        });


    }

    @Override  // 加入房间
    public void onPeers(String myId, String connections, int roomSize) {
        handler.post(() -> {
            //自己进入了房间，然后开始发送offer
            CallSession currentSession = SkyEngineKit.Instance().getCurrentSession();
            if (currentSession != null) {
                currentSession.onJoinHome(myId, connections, roomSize);
            }
        });

    }

    @Override
    public void onNewPeer(String userId) {
        handler.post(() -> {
            CallSession currentSession = SkyEngineKit.Instance().getCurrentSession();
            if (currentSession != null) {
                currentSession.newPeer(userId);
            }
        });

    }

    @Override
    public void onReject(String userId, int type) {
        handler.post(() -> {
            CallSession currentSession = SkyEngineKit.Instance().getCurrentSession();
            if (currentSession != null) {
                currentSession.onRefuse(userId, type);
            }
        });

    }

    @Override
    public void onOffer(String userId, String sdp) {
        handler.post(() -> {
            CallSession currentSession = SkyEngineKit.Instance().getCurrentSession();
            if (currentSession != null) {
                currentSession.onReceiveOffer(userId, sdp);
            }
        });


    }

    @Override
    public void onAnswer(String userId, String sdp) {
        handler.post(() -> {
            CallSession currentSession = SkyEngineKit.Instance().getCurrentSession();
            if (currentSession != null) {
                currentSession.onReceiverAnswer(userId, sdp);
            }
        });

    }

    @Override
    public void onIceCandidate(String userId, String id, int label, String candidate) {
        handler.post(() -> {
            CallSession currentSession = SkyEngineKit.Instance().getCurrentSession();
            if (currentSession != null) {
                currentSession.onRemoteIceCandidate(userId, id, label, candidate);
            }
        });

    }

    @Override
    public void onLeave(String userId) {
        handler.post(() -> {
            CallSession currentSession = SkyEngineKit.Instance().getCurrentSession();
            if (currentSession != null) {
                currentSession.onLeave(userId);
            }
        });
    }

    @Override
    public void logout(String str) {
        Log.i(TAG, "logout:" + str);
        userState = 0;
    }

    @Override
    public void onTransAudio(String userId) {
        handler.post(() -> {
            CallSession currentSession = SkyEngineKit.Instance().getCurrentSession();
            if (currentSession != null) {
                currentSession.onTransAudio(userId);
            }
        });
    }

    @Override
    public void onDisConnect(String userId) {
        handler.post(() -> {
            CallSession currentSession = SkyEngineKit.Instance().getCurrentSession();
            if (currentSession != null) {
                currentSession.onDisConnect(userId, EnumType.CallEndReason.RemoteSignalError);
            }
        });
    }

    @Override
    public void reConnect() {
        handler.post(() -> {
            webSocket.reconnect();
        });
    }


}
