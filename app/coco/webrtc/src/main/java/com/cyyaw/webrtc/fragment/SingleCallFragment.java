package com.cyyaw.webrtc.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cyyaw.webrtc.R;
import com.cyyaw.webrtc.VideoActivity;
import com.cyyaw.webrtc.WebRtcConfig;
import com.cyyaw.webrtc.rtc.SkyEngineKit;
import com.cyyaw.webrtc.rtc.engine.EnumType;
import com.cyyaw.webrtc.rtc.session.CallSession;


public abstract class SingleCallFragment extends Fragment {
    private static final String TAG = "SingleCallFragment";
    protected ImageView minimizeImageView;
    protected ImageView portraitImageView;   // 用户头像
    protected TextView nameTextView;         // 用户昵称
    protected TextView descTextView;         // 状态提示用语
    protected Chronometer durationTextView;  // 通话时长
    protected ImageView outgoingHangupImageView;
    protected ImageView incomingHangupImageView;
    protected ImageView acceptImageView;
    protected TextView tvStatus;
    protected View outgoingActionContainer;
    protected View incomingActionContainer;
    protected View connectedActionContainer;
    protected View lytParent;
    //是否呼出
    boolean isOutgoing = false;


    protected MediaOperationCallback callSingleActivity;


    boolean endWithNoAnswerFlag = false;
    boolean isConnectionClosed = false;
    public static final long OUTGOING_WAITING_TIME = 30 * 1000;
    protected EnumType.CallState currentState;
    private final Handler handler = new Handler(Looper.getMainLooper());


    public SingleCallFragment(MediaOperationCallback mediaOperationCallback) {
        this.callSingleActivity = mediaOperationCallback;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        initView(view);
        init();
        return view;
    }

    @Override
    public void onDestroyView() {
        if (durationTextView != null) durationTextView.stop();
        refreshMessage(true);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(waitingRunnable);
    }


    abstract int getLayout();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (callSingleActivity != null) {
            isOutgoing = callSingleActivity.isOutgoing();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callSingleActivity = null;
    }


    public void initView(View view) {
        lytParent = view.findViewById(R.id.lytParent);
        minimizeImageView = view.findViewById(R.id.minimizeImageView);
        portraitImageView = view.findViewById(R.id.portraitImageView);
        nameTextView = view.findViewById(R.id.nameTextView);
        descTextView = view.findViewById(R.id.descTextView);
        durationTextView = view.findViewById(R.id.durationTextView);
        outgoingHangupImageView = view.findViewById(R.id.outgoingHangupImageView);
        incomingHangupImageView = view.findViewById(R.id.incomingHangupImageView);
        acceptImageView = view.findViewById(R.id.acceptImageView);
        tvStatus = view.findViewById(R.id.tvStatus);
        outgoingActionContainer = view.findViewById(R.id.outgoingActionContainer);
        incomingActionContainer = view.findViewById(R.id.incomingActionContainer);
        connectedActionContainer = view.findViewById(R.id.connectedActionContainer);
        durationTextView.setVisibility(View.GONE);
        if (isOutgoing) {
            handler.postDelayed(waitingRunnable, OUTGOING_WAITING_TIME);
        }
    }

    public void init() {
    }

    // ======================================界面回调================================
    public void didCallEndWithReason(EnumType.CallEndReason callEndReason) {
        tvStatus.setText(callEndReason.getNote());
        incomingActionContainer.setVisibility(View.GONE);
        outgoingActionContainer.setVisibility(View.GONE);
        if (connectedActionContainer != null) connectedActionContainer.setVisibility(View.GONE);
        refreshMessage(false);
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (callSingleActivity != null) {
                callSingleActivity.finish();
            }

        }, 1500);
    }

    public void didChangeState(EnumType.CallState state) {
        if (state == EnumType.CallState.Connected) {
            handler.removeCallbacks(waitingRunnable);
        }

    }

    public void didChangeMode(boolean isAudio) {

    }

    public void didCreateLocalVideoTrack() {
    }

    public void didReceiveRemoteVideoTrack(String userId) {
    }

    public void didUserLeave(String userId) {
    }

    public void didError(String error) {
    }

    public void didDisconnected(String error) {
        isConnectionClosed = true;
        if (callSingleActivity != null) {
            SkyEngineKit.Instance().endCall();
        }
    }

    private void refreshMessage(Boolean isForCallTime) {
        if (callSingleActivity == null) {
            return;
        }
        // 刷新消息; demo中没有消息，不用处理这儿快逻辑
    }

    public void startRefreshTime() {
        CallSession session = SkyEngineKit.Instance().getCurrentSession();
        if (session == null) return;
        if (durationTextView != null) {
            durationTextView.setVisibility(View.VISIBLE);
            durationTextView.setBase(SystemClock.elapsedRealtime() - (System.currentTimeMillis() - session.getStartTime()));
            durationTextView.start();
        }
    }

    void runOnUiThread(Runnable runnable) {
        if (callSingleActivity != null) {
            handler.post(runnable);
        }
    }


    private final Runnable waitingRunnable = new Runnable() {
        @Override
        public void run() {
            if (currentState != EnumType.CallState.Connected) {
                endWithNoAnswerFlag = true;
                if (callSingleActivity != null) {
                    SkyEngineKit.Instance().endCall();
                }
            }
        }
    };


}
