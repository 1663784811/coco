package com.cyyaw.webrtc;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cyyaw.testwebrtc.temp1.utils.StatsReportUtil;
import com.cyyaw.webrtc.rtc.ProxyVideoSink;
import com.cyyaw.webrtc.rtc.RTCEngine;
import com.cyyaw.webrtc.rtc.RTCPeer;
import com.cyyaw.webrtc.socket.AppRTCClient;
import com.cyyaw.webrtc.socket.DirectRTCClient;

import org.webrtc.EglBase;
import org.webrtc.IceCandidate;
import org.webrtc.RTCStatsReport;
import org.webrtc.RendererCommon;
import org.webrtc.SessionDescription;
import org.webrtc.SurfaceViewRenderer;

public class WaitingForVideoActivity extends AppCompatActivity implements AppRTCClient.SignalingEvents, RTCPeer.PeerConnectionEvents{

    private static final String TAG = WaitingForVideoActivity.class.getName();

    private RTCEngine mRtcEngine;

    private SurfaceViewRenderer mFullView;
    private SurfaceViewRenderer mPipView;
    private TextView callStatsView;

    private final ProxyVideoSink remoteProxyRenderer = new ProxyVideoSink();
    private final ProxyVideoSink localProxyVideoSink = new ProxyVideoSink();

    private long callStartedTimeMs;
    private DirectRTCClient mDirectRTCClient;

    private StatsReportUtil statsReportUtil;

    private final Handler mMainHandler = new Handler(Looper.getMainLooper());


    private boolean mIsServer;

    public static final String ARG_ROLE_TYPE = "roleType";
    public static final String ARG_IP_ADDRESS = "ipAddress";
    private String mIpAddress;

    public static final int TYPE_SERVER = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // 添加Activity到堆栈
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_video);

        // 接收参数
        //Intent intent = getIntent();
        //int mRoleType = intent.getIntExtra(ARG_ROLE_TYPE, 0);
        //mIpAddress = intent.getStringExtra(ARG_IP_ADDRESS);
        // mIsServer = mRoleType == TYPE_SERVER;
        mIpAddress = "192.168.0.182";
        mIsServer = false;


        statsReportUtil = new StatsReportUtil();
        final EglBase eglBase = EglBase.create();

        // 小视窗
        mPipView = findViewById(R.id.pip_surface_render);
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
        localProxyVideoSink.setTarget(mPipView);
        mRtcEngine = new RTCEngine(getApplicationContext(), eglBase, localProxyVideoSink);


        // ===================   初始化大窗口
        mFullView = findViewById(R.id.full_surface_render);
        mFullView.init(eglBase.getEglBaseContext(), null);
        mFullView.setScalingType(RendererCommon.ScalingType.SCALE_ASPECT_FILL);
        remoteProxyRenderer.setTarget(mFullView);


        // ===================    初始化信息窗
        callStatsView = findViewById(R.id.callStats);


        // ===================    初始化网络
        // 时间
        callStartedTimeMs = System.currentTimeMillis();
        //
        mDirectRTCClient = new DirectRTCClient(this);
        // 设置连接参数
        AppRTCClient.RoomConnectionParameters parameters = new AppRTCClient.RoomConnectionParameters("0.0.0.0");
        // 连接房间
        mDirectRTCClient.connectToRoom(parameters);

    }


    private void disconnect() {
        remoteProxyRenderer.setTarget(null);
        localProxyVideoSink.setTarget(null);
        if (mDirectRTCClient != null) {
            mDirectRTCClient.disconnectFromRoom();
            mDirectRTCClient = null;
        }
        if (mPipView != null) {
            mPipView.release();
            mPipView = null;
        }
        if (mFullView != null) {
            mFullView.release();
            mFullView = null;
        }
        if (mRtcEngine != null) {
            mRtcEngine.close();
            mRtcEngine = null;
        }
        finish();
    }

    // region -------------------------------socket event-------------------------------------------
    //  连接进入房间
    @Override
    public void onConnectedToRoom(AppRTCClient.SignalingParameters params) {
        // runOnUiThread 运行ui 线程
        runOnUiThread(() -> {
            boolean initiator = params.initiator;
            mRtcEngine.createPeerConnection(this, remoteProxyRenderer);
            mRtcEngine.setVideoCodecType(RTCPeer.VIDEO_CODEC_H264);
            if (initiator) {
                // create offer
                mMainHandler.post(() -> logAndToast("create offer"));
                mRtcEngine.createOffer();
            } else {
                if (params.offerSdp != null) {
                    mRtcEngine.setRemoteDescription(params.offerSdp);
                    // create answer
                    mMainHandler.post(() -> logAndToast("create answer"));
                    mRtcEngine.createAnswer();
                }
            }
        });

    }

    @Override
    public void onRemoteDescription(SessionDescription sdp) {
        final long delta = System.currentTimeMillis() - callStartedTimeMs;
        runOnUiThread(() -> {
            if (mRtcEngine == null) {
                Log.e(TAG, "Received remote SDP for non-initialized peer connection.");
                return;
            }
            mRtcEngine.setRemoteDescription(sdp);
            if (!mIsServer) {
                logAndToast("Creating ANSWER...");
                mRtcEngine.createAnswer();
            }
        });

    }

    @Override
    public void onRemoteIceCandidate(IceCandidate candidate) {
        runOnUiThread(() -> {
            if (mRtcEngine == null) {
                Log.e(TAG, "Received ICE candidate for a non-initialized peer connection.");
                return;
            }
            mRtcEngine.addRemoteIceCandidate(candidate);
        });
    }

    @Override
    public void onRemoteIceCandidatesRemoved(IceCandidate[] candidates) {
        runOnUiThread(() -> {
            if (mRtcEngine == null) {
                Log.e(TAG, "Received ICE candidate removals for a non-initialized peer connection.");
                return;
            }
            mRtcEngine.removeRemoteIceCandidates(candidates);
        });
    }

    @Override
    public void onChannelClose() {
        runOnUiThread(() -> {
            logAndToast("Remote end hung up; dropping PeerConnection");
            disconnect();
        });
    }

    @Override
    public void onChannelError(String description) {
        runOnUiThread(() -> logAndToast(description));

    }

    // endregion

    // region -------------------------------connection event   连接事件---------------------------------------

    @Override
    public void onLocalDescription(SessionDescription desc) {
        final long delta = System.currentTimeMillis() - callStartedTimeMs;
        runOnUiThread(() -> {
            logAndToast("Sending " + desc.type + ", delay=" + delta + "ms");
            if (mIsServer) {
                mDirectRTCClient.sendOfferSdp(desc);
            } else {
                mDirectRTCClient.sendAnswerSdp(desc);
            }
        });

    }

    @Override
    public void onIceCandidate(IceCandidate candidate) {
        runOnUiThread(() -> {
            if (mDirectRTCClient != null) {
                mDirectRTCClient.sendLocalIceCandidate(candidate);
            }

        });

    }

    @Override
    public void onIceCandidatesRemoved(IceCandidate[] candidates) {
        runOnUiThread(() -> {
            if (mDirectRTCClient != null) {
                mDirectRTCClient.sendLocalIceCandidateRemovals(candidates);
            }
        });
    }

    @Override
    public void onIceConnected() {
        final long delta = System.currentTimeMillis() - callStartedTimeMs;
        runOnUiThread(() -> logAndToast("ICE connected, delay=" + delta + "ms"));
    }

    @Override
    public void onIceDisconnected() {
        runOnUiThread(() -> logAndToast("ICE disconnected"));
    }

    @Override
    public void onConnected() {
        final long delta = System.currentTimeMillis() - callStartedTimeMs;
        runOnUiThread(() -> {
            logAndToast("DTLS connected, delay=" + delta + "ms");
            mRtcEngine.enableStatsEvents(true, 1000);
            mRtcEngine.setBitrateRange(500, 2000);
        });

    }

    @Override
    public void onDisconnected() {
        runOnUiThread(() -> {
            logAndToast("DTLS disconnected");
            disconnect();
        });
    }

    @Override
    public void onPeerConnectionError(String description) {
        logAndToast(description);
    }

    @Override
    public void onPeerConnectionStatsReady(RTCStatsReport report) {
        Log.d(TAG, "onPeerConnectionStatsReady: " + report);
        String statsReport = statsReportUtil.getStatsReport(report);

        // 信息
        runOnUiThread(() -> callStatsView.setText(statsReport));
    }



    private Toast logToast;

    private void logAndToast(String msg) {
        Log.d(TAG, msg);
        if (logToast != null) {
            logToast.cancel();
        }
        logToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        logToast.show();
    }
}
