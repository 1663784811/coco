package com.cyyaw.webrtc.fragment;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.cyyaw.webrtc.R;
import com.cyyaw.webrtc.rtc.SkyEngineKit;
import com.cyyaw.webrtc.rtc.engine.EnumType;
import com.cyyaw.webrtc.rtc.session.CallSession;


/**
 * 语音通话控制界面
 */
public class FragmentAudio extends SingleCallFragment {
    private static final String TAG = "FragmentAudio";
    private ImageView muteImageView;
    private ImageView speakerImageView;
    private boolean micEnabled = false; // 静音
    private boolean isSpeakerOn = false; // 扬声器

    @Override
    int getLayout() {
        return R.layout.fragment_audio;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        muteImageView = view.findViewById(R.id.muteImageView);
        speakerImageView = view.findViewById(R.id.speakerImageView);
        minimizeImageView.setVisibility(View.GONE);

        outgoingHangupImageView.setOnClickListener((View v) -> {
            CallSession session = SkyEngineKit.Instance().getCurrentSession();
            if (session != null) {
                SkyEngineKit.Instance().endCall();
            }
        });

        incomingHangupImageView.setOnClickListener((View v) -> {
            CallSession session = SkyEngineKit.Instance().getCurrentSession();
            if (session != null) {
                SkyEngineKit.Instance().endCall();
            }
        });

        minimizeImageView.setOnClickListener((View v) -> {
            if (callSingleActivity != null) {
                callSingleActivity.showFloatingView();
            }
        });

        muteImageView.setOnClickListener((View v) -> {
            CallSession session = SkyEngineKit.Instance().getCurrentSession();
            if (session != null && session.getState() != EnumType.CallState.Idle) {
                if (session.toggleMuteAudio(!micEnabled)) {
                    micEnabled = !micEnabled;
                }
                muteImageView.setSelected(micEnabled);
            }
        });

        acceptImageView.setOnClickListener((View v) -> {
            CallSession session = SkyEngineKit.Instance().getCurrentSession();
            if (session != null)
                Log.d(TAG, "session = " + session + "; session.getState() = " + session.getState());
            if (session != null && session.getState() == EnumType.CallState.Incoming) {
                session.joinHome(session.getRoomId());
            } else if (session != null) {
                session.sendRefuse();
            }
        });

        speakerImageView.setOnClickListener((View v) -> {
            CallSession session = SkyEngineKit.Instance().getCurrentSession();
            if (session != null && session.getState() != EnumType.CallState.Idle) {
                if (session.toggleSpeaker(!isSpeakerOn)) {
                    isSpeakerOn = !isSpeakerOn;
                }
                speakerImageView.setSelected(isSpeakerOn);
            }
        });

    }

    @Override
    public void init() {
        super.init();
        CallSession currentSession = SkyEngineKit.Instance().getCurrentSession();
        currentState = currentSession.getState();
        // 如果已经接通
        if (currentState == EnumType.CallState.Connected) {
            descTextView.setVisibility(View.GONE); // 提示语
            outgoingActionContainer.setVisibility(View.VISIBLE);
            durationTextView.setVisibility(View.VISIBLE);
            minimizeImageView.setVisibility(View.VISIBLE);
            startRefreshTime();
        } else {
            // 如果未接通
            if (isOutgoing) {
                descTextView.setText(R.string.av_waiting);
                outgoingActionContainer.setVisibility(View.VISIBLE);
                incomingActionContainer.setVisibility(View.GONE);
            } else {
                descTextView.setText(R.string.av_audio_invite);
                outgoingActionContainer.setVisibility(View.GONE);
                incomingActionContainer.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void didChangeState(EnumType.CallState state) {
        currentState = state;
        runOnUiThread(() -> {
            if (state == EnumType.CallState.Connected) {
                incomingActionContainer.setVisibility(View.GONE);
                outgoingActionContainer.setVisibility(View.VISIBLE);
                minimizeImageView.setVisibility(View.VISIBLE);
                descTextView.setVisibility(View.GONE);

                startRefreshTime();
            } else {
                // do nothing now
            }
        });
    }
}