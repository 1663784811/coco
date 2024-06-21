package com.cyyaw.webrtc.rtc.render;

import org.webrtc.VideoFrame;
import org.webrtc.VideoSink;


public class ProxyVideoSink implements VideoSink {
    private static final String TAG = "ProxyVideoSink";
    private VideoSink target;

    @Override
    synchronized public void onFrame(VideoFrame frame) {
        if (target == null) {
            return;
        }
        target.onFrame(frame);
    }
    synchronized public void setTarget(VideoSink target) {
        this.target = target;
    }

}