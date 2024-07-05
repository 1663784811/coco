package com.cyyaw.webrtc.fragment.singlecall;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cyyaw.webrtc.R;
import com.cyyaw.webrtc.fragment.MediaOperationCallback;
import com.cyyaw.webrtc.rtc.CallEngineKit;
import com.cyyaw.webrtc.rtc.engine.EnumType;
import com.cyyaw.webrtc.rtc.session.CallSession;

import org.webrtc.SurfaceViewRenderer;

/**
 * 视频通话控制界面
 */
public class FragmentVideo extends SingleCallFragment {

    private static final String TAG = "FragmentVideo";

    // 呼出切换语音
    private ImageView outgoingAudioOnlyImageView;
    // 呼入切换语音
    private ImageView incomingAudioOnlyImageView;
    // 连接切换语音
    private ImageView connectedAudioOnlyImageView;


    // 连接挂断
    private ImageView connectedHangupImageView;
    // 切换的摄像头
    private ImageView switchCameraImageView;
    // 渲染器
    private FrameLayout fullscreenRenderer;
    // 小渲染器
    private FrameLayout pipRenderer;

    private LinearLayout inviteeInfoContainer;

    // 本地图像
    private SurfaceViewRenderer localSurfaceView;
    // 远程图像
    private SurfaceViewRenderer remoteSurfaceView;

    private boolean isFromFloatingView;

    public FragmentVideo(MediaOperationCallback mediaOperationCallback, boolean isOutgoing, boolean isFromFloatingView) {
        super(mediaOperationCallback, isOutgoing);
        this.isFromFloatingView = isFromFloatingView;
    }


    @Override
    int getLayout() {
        return R.layout.fragment_video;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        fullscreenRenderer = view.findViewById(R.id.fullscreen_video_view);
        pipRenderer = view.findViewById(R.id.pip_video_view);
        inviteeInfoContainer = view.findViewById(R.id.inviteeInfoContainer);
        outgoingAudioOnlyImageView = view.findViewById(R.id.outgoingAudioOnlyImageView);
        incomingAudioOnlyImageView = view.findViewById(R.id.incomingAudioOnlyImageView);
        connectedAudioOnlyImageView = view.findViewById(R.id.connectedAudioOnlyImageView);
        connectedHangupImageView = view.findViewById(R.id.connectedHangupImageView);
        switchCameraImageView = view.findViewById(R.id.switchCameraImageView);

        // 挂断
        connectedHangupImageView.setOnClickListener((View v) -> {
            finishCall();
        });
        // 切换的摄像头
        switchCameraImageView.setOnClickListener((View v) -> {
            CallSession session = CallEngineKit.Instance().getCurrentSession();
            session.switchCamera();
        });
        // 点击小渲染器
        pipRenderer.setOnClickListener((View v) -> {
            boolean isFullScreenRemote = fullscreenRenderer.getChildAt(0) == remoteSurfaceView;
            fullscreenRenderer.removeAllViews();
            pipRenderer.removeAllViews();
            if (isFullScreenRemote) {
                remoteSurfaceView.setZOrderMediaOverlay(true);
                pipRenderer.addView(remoteSurfaceView);
                localSurfaceView.setZOrderMediaOverlay(false);
                fullscreenRenderer.addView(localSurfaceView);
            } else {
                localSurfaceView.setZOrderMediaOverlay(true);
                pipRenderer.addView(localSurfaceView);
                remoteSurfaceView.setZOrderMediaOverlay(false);
                fullscreenRenderer.addView(remoteSurfaceView);
            }
        });
        // 切换语音
        outgoingAudioOnlyImageView.setOnClickListener((View v) -> {
            changeAudio();
        });
        incomingAudioOnlyImageView.setOnClickListener((View v) -> {
            changeAudio();
        });
        connectedAudioOnlyImageView.setOnClickListener((View v) -> {
            changeAudio();
        });
    }


    @Override
    public void init() {
        super.init();
        CallSession session = CallEngineKit.Instance().getCurrentSession();
        if (session != null) {
            currentState = session.getState();
        }
        if (session == null || EnumType.CallState.Idle == session.getState()) {
            if (mediaOperationCallback != null) {
                mediaOperationCallback.finish();
            }
        } else if (EnumType.CallState.Connected == session.getState()) {
            incomingActionContainer.setVisibility(View.GONE);
            outgoingActionContainer.setVisibility(View.GONE);
            connectedActionContainer.setVisibility(View.VISIBLE);
            inviteeInfoContainer.setVisibility(View.GONE);
            minimizeImageView.setVisibility(View.VISIBLE);
            startRefreshTime();
        } else {
            if (isOutgoing) {
                incomingActionContainer.setVisibility(View.GONE);
                outgoingActionContainer.setVisibility(View.VISIBLE);
                connectedActionContainer.setVisibility(View.GONE);
                descTextView.setText(R.string.av_waiting);
            } else {
                incomingActionContainer.setVisibility(View.VISIBLE);
                outgoingActionContainer.setVisibility(View.GONE);
                connectedActionContainer.setVisibility(View.GONE);
                descTextView.setText(R.string.av_video_invite);
                if (currentState == EnumType.CallState.Incoming) {
                    View surfaceView = CallEngineKit.Instance().getCurrentSession().setupLocalVideo(false);
                    Log.d(TAG, "init surfaceView != null is " + (surfaceView != null) + "; isOutgoing = " + isOutgoing + "; currentState = " + currentState);
                    if (surfaceView != null) {
                        localSurfaceView = (SurfaceViewRenderer) surfaceView;
                        localSurfaceView.setZOrderMediaOverlay(false);
                        fullscreenRenderer.addView(localSurfaceView);
                    }
                }
            }
        }
        if (isFromFloatingView) {
            didCreateLocalVideoTrack();
            if (session != null) {
                didReceiveRemoteVideoTrack(session.mTargetId);
            }
        }
    }

    @Override
    public void didChangeState(EnumType.CallState state) {
        currentState = state;
        Log.d(TAG, "didChangeState, state = " + state);
        runOnUiThread(() -> {
            if (state == EnumType.CallState.Connected) {

                incomingActionContainer.setVisibility(View.GONE);
                outgoingActionContainer.setVisibility(View.GONE);
                connectedActionContainer.setVisibility(View.VISIBLE);
                inviteeInfoContainer.setVisibility(View.GONE);
                descTextView.setVisibility(View.GONE);
                minimizeImageView.setVisibility(View.VISIBLE);
                // 开启计时器
                startRefreshTime();
            } else {
                // do nothing now
            }
        });
    }


    @Override
    public void didChangeMode(boolean isAudio) {
        runOnUiThread(() -> mediaOperationCallback.switchAudio());
    }


    /**
     * 创建本地 画面预览
     */
    @Override
    public void didCreateLocalVideoTrack() {
        if (localSurfaceView == null) {
            View surfaceView = CallEngineKit.Instance().getCurrentSession().setupLocalVideo(true);
            if (surfaceView != null) {
                localSurfaceView = (SurfaceViewRenderer) surfaceView;
            } else {
                if (mediaOperationCallback != null) mediaOperationCallback.finish();
                return;
            }
        } else {
            localSurfaceView.setZOrderMediaOverlay(true);
        }
        Log.d(TAG, "didCreateLocalVideoTrack localSurfaceView != null is " + (localSurfaceView != null) + "; remoteSurfaceView == null = " + (remoteSurfaceView == null));
        if (localSurfaceView.getParent() != null) {
            ((ViewGroup) localSurfaceView.getParent()).removeView(localSurfaceView);
        }
        if (isOutgoing && remoteSurfaceView == null) {
            if (fullscreenRenderer != null && fullscreenRenderer.getChildCount() != 0) {
                fullscreenRenderer.removeAllViews();
            }
            fullscreenRenderer.addView(localSurfaceView);
        } else {
            if (pipRenderer.getChildCount() != 0) {
                pipRenderer.removeAllViews();
            }
            pipRenderer.addView(localSurfaceView);
        }
    }


    @Override
    public void didReceiveRemoteVideoTrack(String userId) {
        pipRenderer.setVisibility(View.VISIBLE);
        if (localSurfaceView != null) {
            localSurfaceView.setZOrderMediaOverlay(true);
            if (isOutgoing) {
                if (localSurfaceView.getParent() != null) {
                    ((ViewGroup) localSurfaceView.getParent()).removeView(localSurfaceView);
                }
                pipRenderer.addView(localSurfaceView);
            }
        }


        View surfaceView = CallEngineKit.Instance().getCurrentSession().setupRemoteVideo(userId, false);
        Log.d(TAG, "didReceiveRemoteVideoTrack,surfaceView = " + surfaceView);
        if (surfaceView != null) {
            fullscreenRenderer.setVisibility(View.VISIBLE);
            remoteSurfaceView = (SurfaceViewRenderer) surfaceView;
            fullscreenRenderer.removeAllViews();
            if (remoteSurfaceView.getParent() != null) {
                ((ViewGroup) remoteSurfaceView.getParent()).removeView(remoteSurfaceView);
            }
            fullscreenRenderer.addView(remoteSurfaceView);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fullscreenRenderer.removeAllViews();
        pipRenderer.removeAllViews();
    }
}