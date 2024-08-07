package com.cyyaw.webrtc.rtc.session;

import android.app.Activity;
import android.media.AsyncPlayer;
import android.media.AudioManager;
import android.net.Uri;


import com.cyyaw.webrtc.R;
import com.cyyaw.webrtc.WebRtcConfig;
import com.cyyaw.webrtc.net.SocketManager;

import java.util.List;

/**
 * 电话通信事件
 */
public class CallEventImpl implements CallEvent {

    private static final String TAG = "VoipEvent";


    private final AsyncPlayer ringPlayer = new AsyncPlayer(null);

    @Override
    public void sendAskRoom(int roomSize) {
        SocketManager.getInstance().sendAskRoom(roomSize);
    }

    @Override
    public void sendInvite(String room, List<String> userIds, boolean audioOnly) {
        SocketManager.getInstance().sendInvite(room, userIds, audioOnly);
    }

    @Override
    public void sendRefuse(String room, String inviteId, int refuseType) {
        SocketManager.getInstance().sendRefuse(room, inviteId, refuseType);
    }

    @Override
    public void sendTransAudio(String toId) {
        SocketManager.getInstance().sendTransAudio(toId);
    }

    @Override
    public void sendDisConnect(String room, String toId, boolean isCrashed) {
        SocketManager.getInstance().sendDisconnect(room, toId);
    }

    @Override
    public void sendCancel(String mRoomId, List<String> toIds) {
        SocketManager.getInstance().sendCancel(mRoomId, toIds);
    }


    @Override
    public void sendJoin(String room) {
        SocketManager.getInstance().sendJoin(room);
    }

    @Override
    public void sendRingBack(String targetId, String room) {
        SocketManager.getInstance().sendRingBack(targetId, room);
    }

    @Override
    public void sendLeave(String room, String userId) {
        SocketManager.getInstance().sendLeave(room, userId);
    }


    @Override
    public void sendOffer(String userId, String sdp) {
        SocketManager.getInstance().sendOffer(userId, sdp);
    }

    @Override
    public void sendAnswer(String userId, String sdp) {
        SocketManager.getInstance().sendAnswer(userId, sdp);

    }

    @Override
    public void sendIceCandidate(String userId, String id, int label, String candidate) {
        SocketManager.getInstance().sendIceCandidate(userId, id, label, candidate);
    }

    //==============================================================================
    @Override
    public void shouldStartRing(boolean isComing) {
        Activity activity = WebRtcConfig.getActivity();
        Uri uri;
        if (isComing) {
            uri = Uri.parse("android.resource://" + activity.getPackageName() + "/" + R.raw.incoming_call_ring);
        } else {
            uri = Uri.parse("android.resource://" + activity.getPackageName() + "/" + R.raw.wr_ringback);
        }
        ringPlayer.play(activity, uri, true, AudioManager.STREAM_RING);
    }

    @Override
    public void shouldStopRing() {
        ringPlayer.stop();
    }
}
