package com.cyyaw.webrtc.fragment.multicall;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cyyaw.webrtc.R;
import com.cyyaw.webrtc.fragment.MediaOperationCallback;
import com.cyyaw.webrtc.rtc.CallEngineKit;
import com.cyyaw.webrtc.rtc.engine.EnumType;
import com.cyyaw.webrtc.rtc.session.CallSession;
import com.cyyaw.webrtc.rtc.session.CallSessionCallback;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 多人聊天场景
 */
public class FragmentMeeting extends Fragment implements CallSessionCallback {


    private MediaOperationCallback callback;

    private FrameLayout multi_video_view;
    private int mScreenWidth;

    private LinkedHashMap<String, View> videoViews = new LinkedHashMap<>();

    public FragmentMeeting(MediaOperationCallback callback) {
        this.callback = callback;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meeting, container, false);

        multi_video_view = view.findViewById(R.id.multi_video_view);

        return view;
    }

    @Override
    public void onDestroyView() {
        multi_video_view.removeAllViews();
        super.onDestroyView();

    }


    @Override
    public void didCallEndWithReason(EnumType.CallEndReason var1) {

    }

    @Override
    public void didChangeState(EnumType.CallState var1) {

    }

    @Override
    public void didChangeMode(boolean isAudioOnly) {

    }

    @Override
    public void didCreateLocalVideoTrack() {
        View surfaceView = CallEngineKit.Instance().getCurrentSession().setupLocalVideo(true);
        if (surfaceView != null) {
            CallSession callSession = CallEngineKit.Instance().getCurrentSession();
            videoViews.put(callSession.mMyId, surfaceView);
            multi_video_view.addView(surfaceView);
            refreshView();
        }

    }

    @Override
    public void didReceiveRemoteVideoTrack(String userId) {
        View surfaceView = CallEngineKit.Instance().getCurrentSession().setupRemoteVideo(userId, true);
        if (surfaceView != null) {
            videoViews.put(userId, surfaceView);
            multi_video_view.addView(surfaceView);
            refreshView();
        }
    }

    @Override
    public void didUserLeave(String userId) {
        for (Map.Entry<String, View> entry : videoViews.entrySet()) {
            String key = entry.getKey();
            if (key.equals(userId)) {
                View value = entry.getValue();
                multi_video_view.removeView(value);
            }
        }
        videoViews.remove(userId);
        refreshView();
    }

    @Override
    public void didError(String error) {

    }

    @Override
    public void didDisconnected(String userId) {

    }


    /**
     * 重置View
     */
    private void refreshView() {
        int size = videoViews.size();
        int count = 0;
        for (Map.Entry<String, View> entry : videoViews.entrySet()) {
            View value = entry.getValue();
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.height = getWidth(size);
            layoutParams.width = getWidth(size);
            layoutParams.leftMargin = getX(size, count);
            layoutParams.topMargin = getY(size, count);
            value.setLayoutParams(layoutParams);
            count++;
        }

    }


    private int getWidth(int size) {
        if (size <= 4) {
            return mScreenWidth / 2;
        } else if (size <= 9) {
            return mScreenWidth / 3;
        }
        return mScreenWidth / 3;
    }

    private int getX(int size, int index) {
        if (size <= 4) {
            if (size == 3 && index == 2) {
                return mScreenWidth / 4;
            }
            return (index % 2) * mScreenWidth / 2;
        } else if (size <= 9) {
            if (size == 5) {
                if (index == 3) {
                    return mScreenWidth / 6;
                }
                if (index == 4) {
                    return mScreenWidth / 2;
                }
            }

            if (size == 7 && index == 6) {
                return mScreenWidth / 3;
            }

            if (size == 8) {
                if (index == 6) {
                    return mScreenWidth / 6;
                }
                if (index == 7) {
                    return mScreenWidth / 2;
                }
            }
            return (index % 3) * mScreenWidth / 3;
        }
        return 0;
    }

    private int getY(int size, int index) {
        if (size < 3) {
            return mScreenWidth / 4;
        } else if (size < 5) {
            if (index < 2) {
                return 0;
            } else {
                return mScreenWidth / 2;
            }
        } else if (size < 7) {
            if (index < 3) {
                return mScreenWidth / 2 - (mScreenWidth / 3);
            } else {
                return mScreenWidth / 2;
            }
        } else if (size <= 9) {
            if (index < 3) {
                return 0;
            } else if (index < 6) {
                return mScreenWidth / 3;
            } else {
                return mScreenWidth / 3 * 2;
            }

        }
        return 0;
    }


}
