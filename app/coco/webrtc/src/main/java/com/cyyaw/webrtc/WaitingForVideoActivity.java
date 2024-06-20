package com.cyyaw.webrtc;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cyyaw.webrtc.rtc.ProxyVideoSink;
import com.cyyaw.webrtc.rtc.RTCEngine;
import com.cyyaw.webrtc.rtc.RTCPeer;
import com.cyyaw.webrtc.socket.AppRTCClient;

import org.webrtc.EglBase;
import org.webrtc.IceCandidate;
import org.webrtc.RTCStatsReport;
import org.webrtc.RendererCommon;
import org.webrtc.SessionDescription;
import org.webrtc.SurfaceViewRenderer;

public class WaitingForVideoActivity extends AppCompatActivity implements AppRTCClient.SignalingEvents, RTCPeer.PeerConnectionEvents {


    private RTCEngine mRtcEngine;

    private SurfaceViewRenderer mPipView;
    private TextView callStatsView;

    private final ProxyVideoSink localProxyVideoSink = new ProxyVideoSink();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // 添加Activity到堆栈
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_video);
        // ======================================================= 大视窗
        // 小视窗
        mPipView = findViewById(R.id.pip_surface_render);
        callStatsView = findViewById(R.id.callStats);
        // 信息窗
        final EglBase eglBase = EglBase.create();
        // 设置类型
        // ============== pip
        mPipView.init(eglBase.getEglBaseContext(), new RendererCommon.RendererEvents() {
            @Override
            public void onFirstFrameRendered() {

            }

            @Override
            public void onFrameResolutionChanged(int videoWidth, int videoHeight, int rotation) {

            }
        });
        mPipView.setScalingType(RendererCommon.ScalingType.SCALE_ASPECT_FIT);
        // 设置事件
        //mPipView.setOnClickListener(v -> setSwappedFeeds(!isSwappedFeeds));
        // 代理模式
        localProxyVideoSink.setTarget(mPipView);
        //
        mRtcEngine = new RTCEngine(getApplicationContext(), eglBase, localProxyVideoSink);

    }


    @Override
    public void onLocalDescription(SessionDescription sdp) {

    }

    @Override
    public void onIceCandidate(IceCandidate candidate) {

    }

    @Override
    public void onIceCandidatesRemoved(IceCandidate[] candidates) {

    }

    @Override
    public void onIceConnected() {

    }

    @Override
    public void onIceDisconnected() {

    }

    @Override
    public void onConnected() {

    }

    @Override
    public void onDisconnected() {

    }

    @Override
    public void onPeerConnectionError(String description) {

    }

    @Override
    public void onPeerConnectionStatsReady(RTCStatsReport report) {
//        Log.d(TAG, "onPeerConnectionStatsReady: " + report);
//        String statsReport = statsReportUtil.getStatsReport(report);

        // 信息
        runOnUiThread(() -> callStatsView.setText("ssssssssssssssss"));
    }

    @Override
    public void onConnectedToRoom(AppRTCClient.SignalingParameters params) {

    }

    @Override
    public void onRemoteDescription(SessionDescription sdp) {

    }

    @Override
    public void onRemoteIceCandidate(IceCandidate candidate) {

    }

    @Override
    public void onRemoteIceCandidatesRemoved(IceCandidate[] candidates) {

    }

    @Override
    public void onChannelClose() {

    }

    @Override
    public void onChannelError(String description) {

    }
}
