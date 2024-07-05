package com.cyyaw.webrtc.net;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.cyyaw.webrtc.activity.VideoActivity;
import com.cyyaw.webrtc.WebRtcConfig;
import com.cyyaw.webrtc.net.socket.SocketConnect;
import com.cyyaw.webrtc.rtc.CallEngineKit;
import com.cyyaw.webrtc.rtc.engine.EnumType;
import com.cyyaw.webrtc.rtc.session.CallSession;

import java.util.List;

/**
 * 网线管理
 */
public class SocketManager implements SocketReceiveDataEvent, SocketSenDataEvent {
    private final static String TAG = SocketManager.class.getName();

    private SocketConnect socketConnect;
    private String myId;

    private final Handler handler = new Handler(Looper.getMainLooper());

    private static final SocketManager socketManager = new SocketManager();

    public static SocketManager getInstance() {
        return socketManager;
    }


    public void connect(SocketConnect socketConnect) {
        socketManager.socketConnect = socketConnect;
    }

    // ======================================================================================     发送数据
    public void sendCreateRoom(String room, int roomSize) {
        if (socketConnect != null) {
            socketConnect.sendCreateRoom(room, roomSize, myId);
        }
    }

    public void sendInvite(String room, List<String> users, boolean audioOnly) {
        if (socketConnect != null) {
            socketConnect.sendInvite(room, myId, users, audioOnly);
        }
    }

    public void sendLeave(String room, String userId) {
        if (socketConnect != null) {
            socketConnect.sendLeave(myId, room, userId);
        }
    }

    public void sendRingBack(String targetId, String room) {
        if (socketConnect != null) {
            socketConnect.sendRing(myId, targetId, room);
        }
    }

    public void sendRefuse(String room, String inviteId, int refuseType) {
        if (socketConnect != null) {
            socketConnect.sendRefuse(room, inviteId, myId, refuseType);
        }
    }

    public void sendCancel(String mRoomId, List<String> userIds) {
        if (socketConnect != null) {
            socketConnect.sendCancel(mRoomId, myId, userIds);
        }
    }

    public void sendJoin(String room) {
        if (socketConnect != null) {
            socketConnect.sendJoin(room, myId);
        }
    }

    public void sendMeetingInvite(String userList) {

    }

    public void sendOffer(String userId, String sdp) {
        if (socketConnect != null) {
            socketConnect.sendOffer(myId, userId, sdp);
        }
    }

    public void sendAnswer(String userId, String sdp) {
        if (socketConnect != null) {
            socketConnect.sendAnswer(myId, userId, sdp);
        }
    }

    public void sendIceCandidate(String userId, String id, int label, String candidate) {
        if (socketConnect != null) {
            socketConnect.sendIceCandidate(myId, userId, id, label, candidate);
        }
    }

    public void sendTransAudio(String userId) {
        if (socketConnect != null) {
            socketConnect.sendTransAudio(myId, userId);
        }
    }

    public void sendDisconnect(String room, String userId) {
        if (socketConnect != null) {
            socketConnect.sendDisconnect(room, myId, userId);
        }
    }


    // ========================================================================================    接收数据

    @Override
    public void onOpenCallBack() {
        Log.i(TAG, "socket is open!");

    }

    @Override
    public void loginSuccess(String userId, String avatar) {
        Log.i(TAG, "loginSuccess:" + userId);
        myId = userId;
    }

    @Override
    public void onInvite(String room, boolean audioOnly, String inviteId, String userList) {
        Intent intent = new Intent();
        intent.putExtra("room", room);
        intent.putExtra("audioOnly", audioOnly);
        intent.putExtra("inviteId", inviteId);
        intent.putExtra("userList", userList);
        boolean b = CallEngineKit.Instance().startInCall(WebRtcConfig.getContext(), room, inviteId, audioOnly);
        if (b) {
            WebRtcConfig.startActivity(intent, VideoActivity.class);
        }
    }

    @Override
    public void onCancel(String inviteId) {
        handler.post(() -> {
            CallSession currentSession = CallEngineKit.Instance().getCurrentSession();
            if (currentSession != null) {
                currentSession.onCancel(inviteId);
            }
        });

    }

    @Override
    public void onRing(String fromId) {
        handler.post(() -> {
            CallSession currentSession = CallEngineKit.Instance().getCurrentSession();
            if (currentSession != null) {
                currentSession.onRingBack(fromId);
            }
        });


    }

    @Override  // 加入房间
    public void onPeers(String myId, String connections, int roomSize) {
        handler.post(() -> {
            //自己进入了房间，然后开始发送offer
            CallSession currentSession = CallEngineKit.Instance().getCurrentSession();
            if (currentSession != null) {
                currentSession.onJoinHome(myId, connections, roomSize);
            }
        });

    }

    @Override
    public void onNewPeer(String userId) {
        handler.post(() -> {
            CallSession currentSession = CallEngineKit.Instance().getCurrentSession();
            if (currentSession != null) {
                currentSession.newPeer(userId);
            }
        });

    }

    @Override
    public void onReject(String userId, int type) {
        handler.post(() -> {
            CallSession currentSession = CallEngineKit.Instance().getCurrentSession();
            if (currentSession != null) {
                currentSession.onRefuse(userId, type);
            }
        });

    }

    @Override
    public void onOffer(String userId, String sdp) {
        handler.post(() -> {
            CallSession currentSession = CallEngineKit.Instance().getCurrentSession();
            if (currentSession != null) {
                currentSession.onReceiveOffer(userId, sdp);
            }
        });


    }

    @Override
    public void onAnswer(String userId, String sdp) {
        handler.post(() -> {
            CallSession currentSession = CallEngineKit.Instance().getCurrentSession();
            if (currentSession != null) {
                currentSession.onReceiverAnswer(userId, sdp);
            }
        });

    }

    @Override
    public void onIceCandidate(String userId, String id, int label, String candidate) {
        handler.post(() -> {
            CallSession currentSession = CallEngineKit.Instance().getCurrentSession();
            if (currentSession != null) {
                currentSession.onRemoteIceCandidate(userId, id, label, candidate);
            }
        });

    }

    @Override
    public void onLeave(String userId) {
        handler.post(() -> {
            CallSession currentSession = CallEngineKit.Instance().getCurrentSession();
            if (currentSession != null) {
                currentSession.onLeave(userId);
            }
        });
    }

    @Override
    public void logout(String str) {
        Log.i(TAG, "logout:" + str);
    }

    @Override
    public void onTransAudio(String userId) {
        handler.post(() -> {
            CallSession currentSession = CallEngineKit.Instance().getCurrentSession();
            if (currentSession != null) {
                currentSession.onTransAudio(userId);
            }
        });
    }

    @Override
    public void onDisConnect(String userId) {
        handler.post(() -> {
            CallSession currentSession = CallEngineKit.Instance().getCurrentSession();
            if (currentSession != null) {
                currentSession.onDisConnect(userId, EnumType.CallEndReason.RemoteSignalError);
            }
        });
    }


    public void setUserId(String userId) {
        myId = userId;
    }
}
