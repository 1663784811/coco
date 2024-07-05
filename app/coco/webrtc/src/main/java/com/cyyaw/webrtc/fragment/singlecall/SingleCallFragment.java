package com.cyyaw.webrtc.fragment.singlecall;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
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
import com.cyyaw.webrtc.fragment.MediaOperationCallback;
import com.cyyaw.webrtc.rtc.CallEngineKit;
import com.cyyaw.webrtc.rtc.engine.EnumType;
import com.cyyaw.webrtc.rtc.session.CallSession;
import com.cyyaw.webrtc.rtc.session.CallSessionCallback;


public abstract class SingleCallFragment extends Fragment implements CallSessionCallback {
    private static final String TAG = "SingleCallFragment";
    // 最小化按钮
    protected ImageView minimizeImageView;
    // 用户头像
    protected ImageView portraitImageView;
    // 用户昵称
    protected TextView nameTextView;
    // 状态提示用语
    protected TextView descTextView;
    // 通话时长
    protected Chronometer durationTextView;
    // 呼出挂断 ( 取消通话 )
    protected ImageView outgoingHangupImageView;
    // 呼入挂断
    private ImageView incomingHangupImageView;
    // 接听按钮
    protected ImageView acceptImageView;
    // 时间文字
    protected TextView tvStatus;
    // 呼出模块
    protected View outgoingActionContainer;
    // 呼入模块
    protected View incomingActionContainer;
    // 连接模块
    protected View connectedActionContainer;

    protected View lytParent;
    //是否呼出
    boolean isOutgoing;
    // 回调
    protected MediaOperationCallback mediaOperationCallback;

    boolean endWithNoAnswerFlag = false;
    boolean isConnectionClosed = false;
    // 30秒
    public static final long OUTGOING_WAITING_TIME = 10 * 1000;

    // 状态
    protected EnumType.CallState currentState;
    private final Handler handler = new Handler(Looper.getMainLooper());


    public SingleCallFragment(MediaOperationCallback mediaOperationCallback, boolean isOutgoing) {
        this.mediaOperationCallback = mediaOperationCallback;
        this.isOutgoing = isOutgoing;
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
    public void onDetach() {
        super.onDetach();
        mediaOperationCallback = null;
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
        // === 点击结束
        outgoingHangupImageView.setOnClickListener((View v) -> {
            finishCall();
        });
        incomingHangupImageView.setOnClickListener((View v) -> {
            finishCall();
        });
        // 最小化
        minimizeImageView.setOnClickListener((View v) -> {
            if (mediaOperationCallback != null) {
                mediaOperationCallback.showFloatingView();
            }
        });
        // 接听
        acceptImageView.setOnClickListener((View v) -> {
            CallSession session = CallEngineKit.Instance().getCurrentSession();
            if (session != null) {
                if (session.getState() == EnumType.CallState.Incoming) {
                    session.joinHome(session.getRoomId());
                } else {
                    session.sendRefuse();
                }
            }
        });
        if (isOutgoing) {  // 呼出计时
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
            if (mediaOperationCallback != null) {
                mediaOperationCallback.finish();
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
        if (mediaOperationCallback != null) {
            CallEngineKit.Instance().endCall();
        }
    }

    private void refreshMessage(Boolean isForCallTime) {
        // 刷新消息; demo中没有消息，不用处理这儿快逻辑
    }

    public void startRefreshTime() {
        CallSession session = CallEngineKit.Instance().getCurrentSession();
        if (session == null) return;
        if (durationTextView != null) {
            durationTextView.setVisibility(View.VISIBLE);
            durationTextView.setBase(SystemClock.elapsedRealtime() - (System.currentTimeMillis() - session.getStartTime()));
            durationTextView.start();
        }
    }

    void runOnUiThread(Runnable runnable) {
        if (mediaOperationCallback != null) {
            handler.post(runnable);
        }
    }


    private final Runnable waitingRunnable = new Runnable() {
        @Override
        public void run() {
            // 未接听自动挂断
            if (currentState != EnumType.CallState.Connected) {
                endWithNoAnswerFlag = true;
                if (mediaOperationCallback != null) {
                    CallEngineKit.Instance().endCall();
                }
            }
        }
    };


    /**
     * ============================================    公共操作
     */

    /**
     * 通话结束
     */
    protected void finishCall() {
        CallSession session = CallEngineKit.Instance().getCurrentSession();
        if (session != null) {
            Log.d(TAG, "endCall");
            CallEngineKit.Instance().endCall();
        }
        if (mediaOperationCallback != null) mediaOperationCallback.finish();
    }

    /**
     * 切换语音
     */
    protected void changeAudio() {
        CallSession session = CallEngineKit.Instance().getCurrentSession();
        if (session != null) {
            session.switchToAudio();
        }
    }

}
