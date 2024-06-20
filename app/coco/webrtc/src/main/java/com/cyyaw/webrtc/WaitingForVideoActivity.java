package com.cyyaw.webrtc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cyyaw.webrtc.rtc.ProxyVideoSink;
import com.cyyaw.webrtc.rtc.RTCEngine;

import org.webrtc.EglBase;
import org.webrtc.RendererCommon;
import org.webrtc.SurfaceViewRenderer;

public class WaitingForVideoActivity extends AppCompatActivity {


    private RTCEngine mRtcEngine;

    private SurfaceViewRenderer mPipView;

    private final ProxyVideoSink localProxyVideoSink = new ProxyVideoSink();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // 添加Activity到堆栈
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_video);
        // ======================================================= 大视窗
        // 小视窗
        mPipView = findViewById(R.id.pip_surface_render);
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
        //
        localProxyVideoSink.setTarget(mPipView);
        //
        mRtcEngine = new RTCEngine(getApplicationContext(), eglBase, localProxyVideoSink);

    }


}
