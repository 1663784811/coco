package com.cyyaw.webrtc.rtc;

import android.content.Context;
import android.util.Log;

import com.cyyaw.webrtc.rtc.engine.EnumType;
import com.cyyaw.webrtc.rtc.session.CallSession;
import com.cyyaw.webrtc.rtc.session.CallEvent;


/**
 *
 */
public class CallEngineKit {
    private final static String TAG = "AVEngineKit";


    private static CallEngineKit avEngineKit;
    // 会话
    private CallSession mCurrentCallSession;
    private CallEvent mEvent;
    private boolean isAudioOnly = false;

    public static CallEngineKit Instance() {
        return avEngineKit;
    }

    /**
     * SDK Enter init Engine
     */
    public static void init(CallEvent iSocketEvent) {
        if (avEngineKit == null) {
            avEngineKit = new CallEngineKit();
            avEngineKit.mEvent = iSocketEvent;
        }
    }

    public void sendRefuseOnPermissionDenied(String room, String inviteId) {
        // 未初始化
        if (avEngineKit == null) {
            Log.e(TAG, "startOutCall error,please init first");
            return;
        }
        if (mCurrentCallSession != null) {
            endCall();
        } else {
            avEngineKit.mEvent.sendRefuse(room, inviteId, EnumType.RefuseType.Hangup.ordinal());
        }
    }

    public void sendDisconnected(String room, String toId, boolean isCrashed) {
        // 未初始化
        if (avEngineKit == null) {
            Log.e(TAG, "startOutCall error,please init first");
            return;
        }
        avEngineKit.mEvent.sendDisConnect(room, toId, isCrashed);
    }

    /**
     * 拨打电话
     */
    public boolean startOutCall(Context context, final String targetId, final boolean audioOnly) {
        // 未初始化
        if (avEngineKit == null) {
            Log.e(TAG, "startOutCall error,please init first");
            return false;
        }
        // 忙线中
        if (mCurrentCallSession != null && mCurrentCallSession.getState() != EnumType.CallState.Idle) {
            Log.i(TAG, "startCall error,currentCallSession is exist");
            return false;
        }
        isAudioOnly = audioOnly;
        // 初始化会话
        mCurrentCallSession = new CallSession(context, audioOnly, mEvent);
        mCurrentCallSession.setTargetId(targetId);
        mCurrentCallSession.setIsComing(false);
        mCurrentCallSession.setCallState(EnumType.CallState.Outgoing);
        // 创建房间
        mCurrentCallSession.askRoom(2);
        // 显示画面 TODO 页面还没挂载
        mCurrentCallSession.showVideo();
        // 开始响铃
        mEvent.shouldStartRing(false);
        return true;
    }

    /**
     * 接听电话
     *
     * @param room      房间号
     * @param targetId  对方的ID
     * @param audioOnly 是否语音
     */
    public boolean startInCall(Context context, final String room, final String targetId, final boolean audioOnly) {
        if (avEngineKit == null) {
            Log.e(TAG, "startInCall error,init is not set");
            return false;
        }
        // 忙线中
        if (mCurrentCallSession != null && mCurrentCallSession.getState() != EnumType.CallState.Idle) {
            // 发送->忙线中...
            Log.i(TAG, "发送->忙线中...!");
            mCurrentCallSession.sendBusyRefuse(room, targetId);
            return false;
        }
        this.isAudioOnly = audioOnly;
        // 初始化会话
        mCurrentCallSession = new CallSession(context, audioOnly, mEvent);
        mCurrentCallSession.setMRoomId(room);
        mCurrentCallSession.setTargetId(targetId);
        mCurrentCallSession.setIsComing(true);
        mCurrentCallSession.setCallState(EnumType.CallState.Incoming);
        // 开始响铃
        mCurrentCallSession.shouldStartRing();
        // 响铃并回复
        mCurrentCallSession.sendRingBack(targetId, room);
        return true;
    }

    /**
     * 挂断会话
     */
    public void endCall() {
        Log.d(TAG, "endCall mCurrentCallSession != null is " + (mCurrentCallSession != null));
        if (mCurrentCallSession != null) {
            // 停止响铃
            mCurrentCallSession.shouldStopRing();
            if (mCurrentCallSession.isComing()) {
                if (mCurrentCallSession.getState() == EnumType.CallState.Incoming) {
                    // 接收到邀请，还没同意，发送拒绝
                    mCurrentCallSession.sendRefuse();
                } else {
                    // 已经接通，挂断电话
                    mCurrentCallSession.leave();
                }
            } else {
                if (mCurrentCallSession.getState() == EnumType.CallState.Outgoing) {
                    mCurrentCallSession.sendCancel();
                } else {
                    // 已经接通，挂断电话
                    mCurrentCallSession.leave();
                }
            }
            mCurrentCallSession.setCallState(EnumType.CallState.Idle);
        }
    }

    /**
     * 加入房间
     */
    public void joinRoom(Context context, String room) {
        if (avEngineKit == null) {
            Log.e(TAG, "joinRoom error,init is not set");
            return;
        }
        // 忙线中
        if (mCurrentCallSession != null && mCurrentCallSession.getState() != EnumType.CallState.Idle) {
            Log.e(TAG, "joinRoom error,currentCallSession is exist");
            return;
        }
        mCurrentCallSession = new CallSession(context, false, mEvent);
        mCurrentCallSession.setIsComing(true);
        mCurrentCallSession.joinHome(room);
    }

    public void createAndJoinRoom(Context context, String room) {
        if (avEngineKit == null) {
            Log.e(TAG, "joinRoom error,init is not set");
            return;
        }
        // 忙线中
        if (mCurrentCallSession != null && mCurrentCallSession.getState() != EnumType.CallState.Idle) {
            Log.e(TAG, "joinRoom error,currentCallSession is exist");
            return;
        }
        mCurrentCallSession = new CallSession(context, false, mEvent);
        mCurrentCallSession.setIsComing(false);
        mCurrentCallSession.askRoom(9);
    }

    /**
     * 离开房间
     */
    public void leaveRoom() {
        if (avEngineKit == null) {
            Log.e(TAG, "leaveRoom error,init is not set");
            return;
        }
        if (mCurrentCallSession != null) {
            mCurrentCallSession.leave();
            mCurrentCallSession.setCallState(EnumType.CallState.Idle);
        }
    }

    public boolean isAudioOnly() {
        return isAudioOnly;
    }

    /**
     * 获取对话实例
     */
    public CallSession getCurrentSession() {
        return this.mCurrentCallSession;
    }


}
