package com.cyyaw.webrtc.rtc.engine;

public class WebRtcDevice {


    private String videoTrackId; // id
    private int videoWidth;  // 宽
    private int videoHeight;  // 高
    private int videoFps;  // FPS

    private String audioTrackId; // id


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
