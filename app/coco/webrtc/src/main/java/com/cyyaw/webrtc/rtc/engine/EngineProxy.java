package com.cyyaw.webrtc.rtc.engine;

import android.util.Log;
import android.view.View;

import java.util.List;

public class EngineProxy implements Engine {
    private static final String TAG = "AVEngine";
    private final Engine iEngine;
    private static volatile EngineProxy instance;

    private EngineProxy(Engine engine) {
        iEngine = engine;
    }

    public static EngineProxy createEngine(Engine engine) {
        if (null == instance) {
            synchronized (EngineProxy.class) {
                if (null == instance) {
                    instance = new EngineProxy(engine);
                }
            }
        }

        return instance;
    }

    @Override
    public void init(EngineCallback callback) {
        if (iEngine == null) {
            return;
        }
        iEngine.init(callback);
    }

    @Override
    public void joinRoom(List<String> userIds) {
        if (iEngine == null) {
            return;
        }
        iEngine.joinRoom(userIds);
    }

    @Override
    public void userIn(String userId) {
        if (iEngine == null) {
            return;
        }
        iEngine.userIn(userId);
    }

    @Override
    public void receiveOffer(String userId, String description) {
        if (iEngine == null) {
            return;
        }
        iEngine.receiveOffer(userId, description);
    }

    @Override
    public void receiveAnswer(String userId, String sdp) {
        if (iEngine == null) {
            return;
        }
        iEngine.receiveAnswer(userId, sdp);
    }

    @Override
    public void receiveIceCandidate(String userId, String id, int label, String candidate) {
        if (iEngine == null) {
            return;
        }
        iEngine.receiveIceCandidate(userId, id, label, candidate);
    }


    @Override
    public void leaveRoom(String userId) {
        Log.d(TAG, "leaveRoom iEngine = " + iEngine);
        if (iEngine == null) {
            return;
        }
        iEngine.leaveRoom(userId);
    }

    @Override
    public View setupLocalPreview(boolean isOverlay) {
        if (iEngine == null) {
            return null;
        }
        return iEngine.setupLocalPreview(isOverlay);
    }

    @Override
    public void stopPreview() {
        if (iEngine == null) {
            return;
        }
        iEngine.stopPreview();
    }

    @Override
    public View setupRemoteVideo(String userId, boolean isO) {
        if (iEngine == null) {
            return null;
        }
        return iEngine.setupRemoteVideo(userId, isO);
    }

    @Override
    public void switchCamera() {
        if (iEngine == null) {
            return;
        }
        iEngine.switchCamera();
    }

    @Override
    public boolean muteAudio(boolean enable) {
        if (iEngine == null) {
            return false;
        }
        return iEngine.muteAudio(enable);
    }

    @Override
    public boolean toggleSpeaker(boolean enable) {
        if (iEngine == null) {
            return false;
        }
        return iEngine.toggleSpeaker(enable);
    }

    @Override
    public boolean toggleHeadset(boolean isHeadset) {
        if (iEngine == null) {
            return false;
        }
        return iEngine.toggleHeadset(isHeadset);
    }

    @Override
    public void release() {
        if (iEngine == null) {
            return;
        }
        Log.d(TAG, "release");
        iEngine.release();
    }

}
