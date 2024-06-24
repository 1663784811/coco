package com.cyyaw.webrtc.net.socket;

import java.util.List;

/**
 * Mqtt 网络
 */
public class MqttSocket implements SocketConnect {
    private final static String TAG = MqttSocket.class.getName();



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
