package com.cyyaw.webrtc.rtc.engine;

public class WebRtcDevice {

    // id
    private String videoTrackId;
    // 宽
    private int videoWidth;
    // 高
    private int videoHeight;
    // FPS
    private int videoFps;
    // id
    private String audioTrackId;


    public String getAudioTrackId() {
        return audioTrackId;
    }

    public void setAudioTrackId(String audioTrackId) {
        this.audioTrackId = audioTrackId;
    }

    public String getVideoTrackId() {
        return videoTrackId;
    }

    public void setVideoTrackId(String videoTrackId) {
        this.videoTrackId = videoTrackId;
    }

    public int getVideoFps() {
        return videoFps;
    }

    public void setVideoFps(int videoFps) {
        this.videoFps = videoFps;
    }

    public int getVideoWidth() {
        return videoWidth;
    }

    public void setVideoWidth(int videoWidth) {
        this.videoWidth = videoWidth;
    }

    public int getVideoHeight() {
        return videoHeight;
    }

    public void setVideoHeight(int videoHeight) {
        this.videoHeight = videoHeight;
    }
}
