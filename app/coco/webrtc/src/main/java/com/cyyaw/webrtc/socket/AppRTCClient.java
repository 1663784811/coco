/*
 *  Copyright 2013 The WebRTC Project Authors. All rights reserved.
 *
 *  Use of this source code is governed by a BSD-style license
 *  that can be found in the LICENSE file in the root of the source
 *  tree. An additional intellectual property rights grant can be found
 *  in the file PATENTS.  All contributing project authors may
 *  be found in the AUTHORS file in the root of the source tree.
 */

package com.cyyaw.webrtc.socket;

import org.webrtc.IceCandidate;
import org.webrtc.PeerConnection;
import org.webrtc.SessionDescription;

import java.util.List;


public interface AppRTCClient {

    class RoomConnectionParameters {
        public final String roomId;
        public RoomConnectionParameters(String roomId) {
            this.roomId = roomId;
        }
    }

    /**
     * Asynchronously connect to an AppRTC room URL using supplied connection
     * parameters. Once connection is established onConnectedToRoom()
     * callback with room parameters is invoked.
     */
    void connectToRoom(RoomConnectionParameters connectionParameters);

    /**
     * Send offer SDP to the other participant.
     */
    void sendOfferSdp(final SessionDescription sdp);

    /**
     * Send answer SDP to the other participant.
     */
    void sendAnswerSdp(final SessionDescription sdp);

    /**
     * Send Ice candidate to the other participant.
     */
    void sendLocalIceCandidate(final IceCandidate candidate);

    /**
     * Send removed ICE candidates to the other participant.
     */
    void sendLocalIceCandidateRemovals(final IceCandidate[] candidates);

    /**
     * Disconnect from room.
     */
    void disconnectFromRoom();

    /**
     * Struct holding the signaling parameters of an AppRTC room.
     */
    class SignalingParameters {
        public final List<PeerConnection.IceServer> iceServers;
        public final boolean initiator;
        public final SessionDescription offerSdp;

        public SignalingParameters(List<PeerConnection.IceServer> iceServers, boolean initiator, SessionDescription offerSdp) {
            this.iceServers = iceServers;
            this.initiator = initiator;
            this.offerSdp = offerSdp;
        }
    }

    /**
     * Callback interface for messages delivered on signaling channel.
     *
     * <p>Methods are guaranteed to be invoked on the UI thread of `activity`.
     */
    interface SignalingEvents {
        /**
         * 一旦房间的信号参数响起，回调就会响起提取SignalingParameters。
         */
        void onConnectedToRoom(final SignalingParameters params);

        /**
         * 一旦接收到远程SDP，就会触发回调。
         */
        void onRemoteDescription(final SessionDescription sdp);

        /**
         * 一旦接收到远程Ice候选者，就触发回调。
         */
        void onRemoteIceCandidate(final IceCandidate candidate);

        /**
         * 一旦接收到远程Ice候选删除，就触发回调。
         */
        void onRemoteIceCandidatesRemoved(final IceCandidate[] candidates);

        /**
         * 通道关闭后触发回调。
         */
        void onChannelClose();

        /**
         * 一旦发生通道错误，就会触发回调。
         */
        void onChannelError(final String description);
    }
}
