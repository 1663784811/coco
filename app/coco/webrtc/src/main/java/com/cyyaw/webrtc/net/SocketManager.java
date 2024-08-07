package com.cyyaw.webrtc.net;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cyyaw.webrtc.MsgCallBack;
import com.cyyaw.webrtc.WebRtcConfig;
import com.cyyaw.webrtc.activity.PhoneCallActivity;
import com.cyyaw.webrtc.net.socket.MsgData;
import com.cyyaw.webrtc.net.socket.SocketConnect;
import com.cyyaw.webrtc.rtc.CallEngineKit;
import com.cyyaw.webrtc.rtc.session.CallSession;
import com.cyyaw.webrtc.rtc.session.EnumType;
import com.cyyaw.webrtc.utils.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 网络管理
 */
public class SocketManager implements SocketReceiveDataEvent, SocketSenDataEvent {
    private final static String TAG = SocketManager.class.getName();
    private final Handler handler = new Handler(Looper.getMainLooper());
    private static final SocketManager socketManager = new SocketManager();

    private SocketConnect socketConnect;
    private String myId;

    private StatusCallBack statusCallBack;
    private MsgCallBack applicationCallBack;
    private String fromId;
    private MsgCallBack msgCallBack;

    public static SocketManager getInstance() {
        return socketManager;
    }


    public void connect(SocketConnect socketConnect) {
        socketManager.socketConnect = socketConnect;
    }

    public void setUserId(String userId) {
        myId = userId;
    }

    /**
     * 设置状态回调
     */
    public void setStatusCallBack(StatusCallBack statusCallBack) {
        this.statusCallBack = statusCallBack;
    }

    /**
     * 设置应用聊天回调
     */
    public void setApplicationChatCallBack(MsgCallBack msgCallBack) {
        this.msgCallBack = msgCallBack;
    }

    /**
     * 设置框聊天回调
     */
    public void setMsgChatCallBack(String fromId, MsgCallBack msgCallBack) {
        this.fromId = fromId;
        this.msgCallBack = msgCallBack;
    }

    // ======================================================================================     发送数据
    public void sendAskRoom(int roomSize) {
        if (socketConnect != null) {
            Map<String, Object> map = new HashMap<>();
            map.put("eventName", "__create");
            Map<String, Object> childMap = new HashMap<>();
            childMap.put("roomSize", roomSize);
            childMap.put("userID", myId);
            map.put("data", childMap);
            JSONObject object = new JSONObject(map);
            String data = object.toString();
            socketConnect.sendWebrtcData("", data);
        }
    }

    public void sendInvite(String room, List<String> users, boolean audioOnly) {
        if (socketConnect != null) {
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
            String data = object.toString();
            socketConnect.sendWebrtcData("", data);
        }
    }

    public void sendLeave(String room, String targetId) {
        if (socketConnect != null) {
            Map<String, Object> map = new HashMap<>();
            map.put("eventName", "__leave");
            Map<String, Object> childMap = new HashMap<>();
            childMap.put("room", room);
            childMap.put("fromID", myId);
            childMap.put("userID", targetId);
            map.put("data", childMap);
            org.json.JSONObject object = new org.json.JSONObject(map);
            String data = object.toString();
            socketConnect.sendWebrtcData(targetId, data);
        }
    }

    public void sendRingBack(String targetId, String room) {
        if (socketConnect != null) {
            Map<String, Object> map = new HashMap<>();
            map.put("eventName", "__ring");
            Map<String, Object> childMap = new HashMap<>();
            childMap.put("fromID", myId);
            childMap.put("toID", targetId);
            childMap.put("room", room);
            map.put("data", childMap);
            org.json.JSONObject object = new org.json.JSONObject(map);
            String data = object.toString();
            socketConnect.sendWebrtcData(targetId, data);
        }
    }

    public void sendRefuse(String room, String targetId, int refuseType) {
        if (socketConnect != null) {
            Map<String, Object> map = new HashMap<>();
            map.put("eventName", "__reject");
            Map<String, Object> childMap = new HashMap<>();
            childMap.put("room", room);
            childMap.put("toID", targetId);
            childMap.put("fromID", myId);
            childMap.put("refuseType", String.valueOf(refuseType));
            map.put("data", childMap);
            org.json.JSONObject object = new org.json.JSONObject(map);
            String data = object.toString();
            socketConnect.sendWebrtcData(targetId, data);
        }
    }

    public void sendCancel(String mRoomId, List<String> userIds) {
        if (socketConnect != null) {
            Map<String, Object> map = new HashMap<>();
            map.put("eventName", "__cancel");
            Map<String, Object> childMap = new HashMap<>();
            childMap.put("inviteID", myId);
            childMap.put("room", mRoomId);
            String join = StringUtils.listToString(userIds);
            childMap.put("userList", join);
            map.put("data", childMap);
            org.json.JSONObject object = new org.json.JSONObject(map);
            String data = object.toString();
            socketConnect.sendWebrtcData("", data);
        }
    }

    public void sendJoin(String room) {
        if (socketConnect != null) {
            Map<String, Object> map = new HashMap<>();
            map.put("eventName", "__join");
            Map<String, String> childMap = new HashMap<>();
            childMap.put("room", room);
            childMap.put("userID", myId);
            map.put("data", childMap);
            org.json.JSONObject object = new org.json.JSONObject(map);
            String data = object.toString();
            socketConnect.sendWebrtcData("", data);
        }
    }

    public void sendOffer(String targetId, String sdp) {
        if (socketConnect != null) {
            Map<String, Object> map = new HashMap<>();
            Map<String, Object> childMap = new HashMap<>();
            childMap.put("sdp", sdp);
            childMap.put("userID", targetId);
            childMap.put("fromID", myId);
            map.put("data", childMap);
            map.put("eventName", "__offer");
            org.json.JSONObject object = new org.json.JSONObject(map);
            String data = object.toString();
            socketConnect.sendWebrtcData(targetId, data);
        }
    }

    public void sendAnswer(String targetId, String sdp) {
        if (socketConnect != null) {
            Map<String, Object> map = new HashMap<>();
            Map<String, Object> childMap = new HashMap<>();
            childMap.put("sdp", sdp);
            childMap.put("fromID", myId);
            childMap.put("userID", targetId);
            map.put("data", childMap);
            map.put("eventName", "__answer");
            org.json.JSONObject object = new org.json.JSONObject(map);
            String data = object.toString();
            socketConnect.sendWebrtcData(targetId, data);
        }
    }

    public void sendIceCandidate(String targetId, String id, int label, String candidate) {
        if (socketConnect != null) {
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
            String data = object.toString();
            socketConnect.sendWebrtcData(targetId, data);
        }
    }

    public void sendTransAudio(String targetId) {
        if (socketConnect != null) {
            Map<String, Object> map = new HashMap<>();
            Map<String, Object> childMap = new HashMap<>();
            childMap.put("fromID", myId);
            childMap.put("userID", targetId);
            map.put("data", childMap);
            map.put("eventName", "__audio");
            org.json.JSONObject object = new org.json.JSONObject(map);
            String data = object.toString();
            socketConnect.sendWebrtcData(targetId, data);
        }
    }

    public void sendDisconnect(String room, String targetId) {
        if (socketConnect != null) {
            Map<String, Object> map = new HashMap<>();
            Map<String, Object> childMap = new HashMap<>();
            childMap.put("fromID", myId);
            childMap.put("userID", targetId);
            childMap.put("room", room);
            map.put("data", childMap);
            map.put("eventName", "__disconnect");
            org.json.JSONObject object = new org.json.JSONObject(map);
            String data = object.toString();
            socketConnect.sendWebrtcData(targetId, data);
        }
    }

    /**
     * 发送聊天消息
     */
    public void sendChatMsg(String toUserid, String data) {
        handler.post(() -> {
            if (socketConnect != null) {
                MsgData msgData = new MsgData();
                msgData.setType("chat");
                msgData.setData(data);
                msgData.setFrom(myId);
                msgData.setTo(toUserid);
                String dataMsg = JSONObject.toJSONString(msgData);
                socketConnect.sendChatData(toUserid, dataMsg);
            }
        });
    }

    // ========================================================================================    接收数据

    @Override
    public void onReceiveWebRtc(String message) {
        Map map = JSON.parseObject(message, Map.class);
        String eventName = (String) map.get("eventName");
        if (eventName == null) return;
        if (eventName.equals("__login_success")) {
            // 登录成功
            loginSuccess(map);
        } else if (eventName.equals("__invite")) {
            // 被邀请
            onReceiveInvite(map);
        } else if (eventName.equals("__cancel")) {
            // 取消拨出
            onCancel(map);
        } else if (eventName.equals("__ring")) {
            onReceiveRing(map);
        } else if (eventName.equals("__peers")) {
            // 进入房间
            onReceivePeers(map);
        } else if (eventName.equals("__new_peer")) {
            // 新人入房间
            onNewPeer(map);
        } else if (eventName.equals("__reject")) {
            // 拒绝接听
            onReject(map);
        } else if (eventName.equals("__offer")) {
            // offer
            onOffer(map);
        } else if (eventName.equals("__answer")) {
            // answer
            onAnswer(map);
        } else if (eventName.equals("__ice_candidate")) {
            onIceCandidate(map);
        }
        // 离开房间
        if (eventName.equals("__leave")) {
            onLeave(map);
        }
        // 切换到语音
        if (eventName.equals("__audio")) {
            onTransAudio(map);
        }
        // 意外断开
        if (eventName.equals("__disconnect")) {
            onDisConnect(map);
        }
    }

    @Override
    public void onReceiveChat(String fromId, String toId, String data) {

        if (null != fromId && msgCallBack != null) {
            msgCallBack.receiveMsg(fromId, data);
        }

        if (null != applicationCallBack) {
            applicationCallBack.receiveMsg(fromId, data);
        }

    }

    @Override
    public void onOpenCallBack() {
        Log.i(TAG, "socket is open!");

    }


    public void loginSuccess(Map map) {
        Map data = (Map) map.get("data");
        if (data != null) {
            String userID = (String) data.get("userID");
            String avatar = (String) data.get("avatar");
            myId = userID;
        }
    }

    public void onReceiveInvite(Map map) {
        Map data = (Map) map.get("data");
        if (data != null) {
            // 房间号
            String room = (String) data.get("room");
            boolean audioOnly = (boolean) data.get("audioOnly");
            // 对方id
            String inviteID = (String) data.get("inviteID");
            String userList = (String) data.get("userList");
            Intent intent = new Intent();
            intent.putExtra("room", room);
            intent.putExtra("audioOnly", audioOnly);
            intent.putExtra("inviteId", inviteID);
            intent.putExtra("userList", userList);
            boolean b = CallEngineKit.Instance().startInCall(WebRtcConfig.getContext(), room, inviteID, audioOnly);
            if (b) {
                WebRtcConfig.startActivity(intent, PhoneCallActivity.class);
            }
        }
    }

    public void onCancel(Map map) {
        Map data = (Map) map.get("data");
        if (data != null) {
            String inviteID = (String) data.get("inviteID");
            String userList = (String) data.get("userList");
            handler.post(() -> {
                CallSession currentSession = CallEngineKit.Instance().getCurrentSession();
                if (currentSession != null) {
                    currentSession.onCancel(inviteID);
                }
            });
        }
    }

    public void onReceiveRing(Map map) {
        Map data = (Map) map.get("data");
        if (data != null) {
            String fromId = (String) data.get("fromID");
            handler.post(() -> {
                CallSession currentSession = CallEngineKit.Instance().getCurrentSession();
                if (currentSession != null) {
                    currentSession.onRingBack(fromId);
                }
            });
        }
    }

    // 加入房间
    public void onReceivePeers(Map map) {
        Map data = (Map) map.get("data");
        if (data != null) {
            String connections = (String) data.get("connections");
            int roomSize = (int) data.get("roomSize");
            String room = (String) data.get("room");
            handler.post(() -> {
                //自己进入了房间，然后开始发送offer
                CallSession currentSession = CallEngineKit.Instance().getCurrentSession();
                if (currentSession != null) {
                    currentSession.onJoinHome(myId, connections, roomSize, room);
                }
            });
        }
    }

    public void onNewPeer(Map map) {
        Map data = (Map) map.get("data");
        if (data != null) {
            String userID = (String) data.get("userID");
            handler.post(() -> {
                CallSession currentSession = CallEngineKit.Instance().getCurrentSession();
                if (currentSession != null) {
                    currentSession.newPeer(userID);
                }
            });
        }
    }

    public void onReject(Map map) {
        Map data = (Map) map.get("data");
        if (data != null) {
            String fromID = (String) data.get("fromID");
            int rejectType = Integer.parseInt(String.valueOf(data.get("refuseType")));
            handler.post(() -> {
                CallSession currentSession = CallEngineKit.Instance().getCurrentSession();
                if (currentSession != null) {
                    currentSession.onRefuse(fromID, rejectType);
                }
            });
        }
    }

    public void onOffer(Map map) {
        Map data = (Map) map.get("data");
        if (data != null) {
            String sdp = (String) data.get("sdp");
            String userID = (String) data.get("fromID");
            handler.post(() -> {
                CallSession currentSession = CallEngineKit.Instance().getCurrentSession();
                if (currentSession != null) {
                    currentSession.onReceiveOffer(userID, sdp);
                }
            });
        }
    }

    public void onAnswer(Map map) {
        Map data = (Map) map.get("data");
        if (data != null) {
            String sdp = (String) data.get("sdp");
            String userID = (String) data.get("fromID");
            handler.post(() -> {
                CallSession currentSession = CallEngineKit.Instance().getCurrentSession();
                if (currentSession != null) {
                    currentSession.onReceiverAnswer(userID, sdp);
                }
            });
        }
    }

    public void onIceCandidate(Map map) {
        Map data = (Map) map.get("data");
        if (data != null) {
            String userID = (String) data.get("fromID");
            String id = (String) data.get("id");
            int label = (int) data.get("label");
            String candidate = (String) data.get("candidate");
            handler.post(() -> {
                CallSession currentSession = CallEngineKit.Instance().getCurrentSession();
                if (currentSession != null) {
                    currentSession.onRemoteIceCandidate(userID, id, label, candidate);
                }
            });
        }
    }

    public void onLeave(Map map) {
        Map data = (Map) map.get("data");
        if (data != null) {
            String fromID = (String) data.get("fromID");
            handler.post(() -> {
                CallSession currentSession = CallEngineKit.Instance().getCurrentSession();
                if (currentSession != null) {
                    currentSession.onLeave(fromID);
                }
            });
        }
    }

    @Override
    public void logout(String str) {
        Log.i(TAG, "logout:" + str);
    }


    public void onTransAudio(Map map) {
        Map data = (Map) map.get("data");
        if (data != null) {
            String userID = (String) data.get("userID");
            String avatar = (String) data.get("avatar");
            handler.post(() -> {
                CallSession currentSession = CallEngineKit.Instance().getCurrentSession();
                if (currentSession != null) {
                    currentSession.onTransAudio(userID);
                }
            });
        }
    }

    public void onDisConnect(Map map) {
        Map data = (Map) map.get("data");
        if (data != null) {
            String fromId = (String) data.get("fromID");
            handler.post(() -> {
                CallSession currentSession = CallEngineKit.Instance().getCurrentSession();
                if (currentSession != null) {
                    currentSession.onDisConnect(EnumType.CallEndReason.RemoteSignalError);
                }
            });
        }


    }

    @Override
    public void onConnectError() {
        handler.post(() -> {
            CallSession currentSession = CallEngineKit.Instance().getCurrentSession();
            if (currentSession != null) {
                currentSession.onDisConnect(EnumType.CallEndReason.SignalError);
            }
        });
    }


    // ========================  接收webRtc 数据


}
