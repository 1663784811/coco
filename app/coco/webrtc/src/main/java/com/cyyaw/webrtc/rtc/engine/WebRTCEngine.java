package com.cyyaw.webrtc.rtc.engine;

import android.content.Context;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.cyyaw.webrtc.rtc.peer.Peer;
import com.cyyaw.webrtc.rtc.peer.PeerEvent;
import com.cyyaw.webrtc.rtc.peer.ProxyVideoSink;

import org.webrtc.AudioSource;
import org.webrtc.AudioTrack;
import org.webrtc.Camera1Enumerator;
import org.webrtc.Camera2Enumerator;
import org.webrtc.CameraEnumerator;
import org.webrtc.CameraVideoCapturer;
import org.webrtc.EglBase;
import org.webrtc.IceCandidate;
import org.webrtc.MediaStream;
import org.webrtc.PeerConnection;
import org.webrtc.PeerConnectionFactory;
import org.webrtc.RendererCommon;
import org.webrtc.SessionDescription;
import org.webrtc.SurfaceTextureHelper;
import org.webrtc.SurfaceViewRenderer;
import org.webrtc.VideoCapturer;
import org.webrtc.VideoSource;
import org.webrtc.VideoTrack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * WebRtc 引擎
 */
public class WebRTCEngine implements IEngine, PeerEvent {
    private final static String TAG = WebRTCEngine.class.getSimpleName();
    private PeerConnectionFactory factory;
    private EglBase mRootEglBase;
    // video
    private VideoTrack localVideoTrack;
    private VideoSource videoSource;
    private VideoCapturer captureAndroid;
    private SurfaceTextureHelper surfaceTextureHelper;
    // audio
    private AudioSource audioSource;
    private AudioTrack localAudioTrack;
    private SurfaceViewRenderer localRenderer;

    private WebRtcDevice webRtcDevice;

    // 对话实例列表
    private final ConcurrentHashMap<String, Peer> peerList = new ConcurrentHashMap<>();
    // 服务器实例列表
    private final List<PeerConnection.IceServer> iceServers = new ArrayList<>();

    private EngineCallback mCallback;

    public boolean mIsAudioOnly;
    private final Context mContext;
    private final AudioManager audioManager;
    private boolean isSpeakerOn = true;

    // 是否正在切换摄像头
    private volatile boolean isSwitch = false;

    public WebRTCEngine(boolean mIsAudioOnly, Context mContext, WebRtcDevice webRtcDevice) {
        this.mIsAudioOnly = mIsAudioOnly;
        this.mContext = mContext;
        this.webRtcDevice = webRtcDevice;
        this.audioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        // 初始化ice地址 STUN 服务器
        this.iceServers.addAll(RtcConfig.getIceServer());
    }

    // -----------------------------------对外方法------------------------------------------

    @Override
    public void init(EngineCallback callback) {
        mCallback = callback;
        if (mRootEglBase == null) {
            mRootEglBase = EglBase.create();
        }
        if (factory == null) {
            factory = RtcConfig.createConnectionFactory(mContext, mRootEglBase);
        }
        // 音频
        audioSource = factory.createAudioSource(RtcConfig.createAudioConstraints());
        localAudioTrack = factory.createAudioTrack(webRtcDevice.getAudioTrackId(), audioSource);
        // 视频
        if (!mIsAudioOnly) {
            captureAndroid = createVideoCapture();
            surfaceTextureHelper = SurfaceTextureHelper.create("CaptureThread", mRootEglBase.getEglBaseContext());
            videoSource = factory.createVideoSource(captureAndroid.isScreencast());
            captureAndroid.initialize(surfaceTextureHelper, mContext, videoSource.getCapturerObserver());
            captureAndroid.startCapture(webRtcDevice.getVideoWidth(), webRtcDevice.getVideoHeight(), webRtcDevice.getVideoFps());
            // 回调
            localVideoTrack = factory.createVideoTrack(webRtcDevice.getVideoTrackId(), videoSource);
        }
    }


    /**
     * 加入房间
     *
     * @param userIds 所有用户ID
     */
    @Override
    public void joinRoom(List<String> userIds) {
        Log.d(TAG, "joinRoom: " + userIds.toString());
        for (String id : userIds) {
            // create Peer  创建新的同伴
            Peer peer = new Peer(factory, iceServers, id, this);
            peer.setOffer(false);
            List<String> mediaStreamLabels = Collections.singletonList("ARDAMS");
            if (localVideoTrack != null) { // 如果视频通道是空则创建
                peer.addVideoTrack(localVideoTrack, mediaStreamLabels);
            }
            if (localAudioTrack != null) {// 如果音频通道是空则创建
                peer.addAudioTrack(localAudioTrack, mediaStreamLabels);
            }
            // 添加列表
            peerList.put(id, peer);
        }
        if (mCallback != null) {
            mCallback.joinRoomSucc();
        }
        if (isHeadphonesPlugged()) {
            toggleHeadset(true);
        } else {
            if (mIsAudioOnly) toggleSpeaker(false);
            else {
                audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
            }
        }
    }

    /**
     * 用户进入
     */
    @Override
    public void userIn(String userId) {
        Log.d(TAG, "userIn:用户进入 " + userId);
        Peer peer = new Peer(factory, iceServers, userId, this);
        peer.setOffer(true);
        List<String> mediaStreamLabels = Collections.singletonList("ARDAMS");
        if (localVideoTrack != null) {
            peer.addVideoTrack(localVideoTrack, mediaStreamLabels);
        }
        if (localAudioTrack != null) {
            peer.addAudioTrack(localAudioTrack, mediaStreamLabels);
        }
        // 添加列表
        peerList.put(userId, peer);
        // createOffer
        peer.createOffer();
    }


    @Override
    public void userReject(String userId, int type) {
        //拒绝接听userId应该是没有添加进peers里去不需要remove
        if (mCallback != null) {
            mCallback.reject(type);
        }
    }

    @Override
    public void disconnected(String userId, EnumType.CallEndReason reason) {
        if (mCallback != null) {
            mCallback.disconnected(reason);
        }
    }

    @Override
    public void receiveOffer(String userId, String description) {
        Peer peer = peerList.get(userId);
        if (peer != null) {
            SessionDescription sdp = new SessionDescription(SessionDescription.Type.OFFER, description);
            peer.setOffer(false);
            peer.setRemoteDescription(sdp);
            peer.createAnswer();
        }


    }

    @Override
    public void receiveAnswer(String userId, String sdp) {
        Log.d(TAG, "receiveAnswer--" + userId);
        Peer peer = peerList.get(userId);
        if (peer != null) {
            SessionDescription sessionDescription = new SessionDescription(SessionDescription.Type.ANSWER, sdp);
            peer.setRemoteDescription(sessionDescription);
        }


    }

    @Override
    public void receiveIceCandidate(String userId, String id, int label, String candidate) {
        Log.d(TAG, "receiveIceCandidate--" + userId);
        Peer peer = peerList.get(userId);
        if (peer != null) {
            IceCandidate iceCandidate = new IceCandidate(id, label, candidate);
            peer.addRemoteIceCandidate(iceCandidate);

        }
    }

    /**
     * 离开房间
     */
    @Override
    public void leaveRoom(String userId) {
        Peer peer = peerList.get(userId);
        if (peer != null) {
            peer.close();
            peerList.remove(userId);
        }
        Log.d(TAG, "leaveRoom peers.size() = " + peerList.size() + "; mCallback = " + mCallback);
        if (peerList.size() <= 1) {
            if (mCallback != null) {
                mCallback.exitRoom();
            }
            if (peerList.size() == 1) {
                for (Map.Entry<String, Peer> set : peerList.entrySet()) {
                    set.getValue().close();
                }
                peerList.clear();
            }
        }
    }

    @Override
    public View setupLocalPreview(boolean isOverlay) {
        if (mRootEglBase == null) {
            return null;
        }
        localRenderer = new SurfaceViewRenderer(mContext);
        localRenderer.init(mRootEglBase.getEglBaseContext(), null);
        localRenderer.setScalingType(RendererCommon.ScalingType.SCALE_ASPECT_FIT);
        localRenderer.setMirror(true);
        localRenderer.setZOrderMediaOverlay(isOverlay);

        ProxyVideoSink localSink = new ProxyVideoSink();
        localSink.setTarget(localRenderer);
        if (localVideoTrack != null) {
            localVideoTrack.addSink(localSink);
        }
        return localRenderer;
    }

    @Override
    public void stopPreview() {
        if (audioSource != null) {
            audioSource.dispose();
            audioSource = null;
        }
        // 释放摄像头
        if (captureAndroid != null) {
            try {
                captureAndroid.stopCapture();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            captureAndroid.dispose();
            captureAndroid = null;
        }
        // 释放画布
        if (surfaceTextureHelper != null) {
            surfaceTextureHelper.dispose();
            surfaceTextureHelper = null;
        }
        if (videoSource != null) {
            videoSource.dispose();
            videoSource = null;
        }
        if (localRenderer != null) {
            localRenderer.release();
            localRenderer = null;
        }
    }

    @Override
    public void startStream() {

    }

    @Override
    public void stopStream() {

    }

    @Override
    public View setupRemoteVideo(String userId, boolean isOverlay) {
        if (TextUtils.isEmpty(userId)) {
            Log.e(TAG, "setupRemoteVideo userId is null ");
            return null;
        }
        Peer peer = peerList.get(userId);
        if (peer == null) return null;

        if (peer.renderer == null) {
            peer.createRender(mRootEglBase, mContext, isOverlay);
        }

        return peer.renderer;

    }

    @Override
    public void stopRemoteVideo() {

    }


    @Override
    public void switchCamera() {
        if (isSwitch) return;
        isSwitch = true;
        if (captureAndroid == null) return;
        if (captureAndroid instanceof CameraVideoCapturer) {
            CameraVideoCapturer cameraVideoCapturer = (CameraVideoCapturer) captureAndroid;
            try {
                cameraVideoCapturer.switchCamera(new CameraVideoCapturer.CameraSwitchHandler() {
                    @Override
                    public void onCameraSwitchDone(boolean isFrontCamera) {
                        isSwitch = false;
                    }

                    @Override
                    public void onCameraSwitchError(String errorDescription) {
                        isSwitch = false;
                    }
                });
            } catch (Exception e) {
                isSwitch = false;
            }
        } else {
            Log.d(TAG, "Will not switch camera, video caputurer is not a camera");
        }
    }

    @Override
    public boolean muteAudio(boolean enable) {
        if (localAudioTrack != null) {
            localAudioTrack.setEnabled(!enable);
            return true;
        }
        return false;
    }

    @Override
    public boolean toggleSpeaker(boolean enable) {
        if (audioManager != null) {
            isSpeakerOn = enable;
            audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
            if (enable) {
                audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, audioManager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL), AudioManager.FX_KEY_CLICK);
                audioManager.setSpeakerphoneOn(true);
            } else {
                //5.0以上
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    //设置mode
                    audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
                } else {
                    //设置mode
                    audioManager.setMode(AudioManager.MODE_IN_CALL);
                }
                //设置音量，解决有些机型切换后没声音或者声音突然变大的问题
                audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, audioManager.getStreamVolume(AudioManager.STREAM_VOICE_CALL), AudioManager.FX_KEY_CLICK);
                audioManager.setSpeakerphoneOn(false);
            }
            return true;
        }
        return false;

    }

    @Override
    public boolean toggleHeadset(boolean isHeadset) {
        if (audioManager != null) {
            if (isHeadset) {
                //5.0以上
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    //设置mode
                    audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
                } else {
                    //设置mode
                    audioManager.setMode(AudioManager.MODE_IN_CALL);
                }
                audioManager.setSpeakerphoneOn(false);
            } else {
                if (mIsAudioOnly) {
                    toggleSpeaker(isSpeakerOn);
                }
            }
        }
        return false;
    }

    private boolean isHeadphonesPlugged() {
        if (audioManager == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            AudioDeviceInfo[] audioDevices = audioManager.getDevices(AudioManager.GET_DEVICES_OUTPUTS);
            for (AudioDeviceInfo deviceInfo : audioDevices) {
                if (deviceInfo.getType() == AudioDeviceInfo.TYPE_WIRED_HEADPHONES || deviceInfo.getType() == AudioDeviceInfo.TYPE_WIRED_HEADSET) {
                    return true;
                }
            }
            return false;
        } else {
            return audioManager.isWiredHeadsetOn();
        }
    }

    @Override
    public void release() {
        if (audioManager != null) {
            audioManager.setMode(AudioManager.MODE_NORMAL);
        }
        // 清空peer
        if (peerList != null) {
            for (Peer peer : peerList.values()) {
                peer.close();
            }
            peerList.clear();
        }
        // 停止预览
        stopPreview();
        if (factory != null) {
            factory.dispose();
            factory = null;
        }
        if (mRootEglBase != null) {
            mRootEglBase.release();
            mRootEglBase = null;
        }


    }

    // -----------------------------其他方法--------------------------------

    /**
     * 创建媒体方式
     */
    private VideoCapturer createVideoCapture() {
        VideoCapturer videoCapturer;
        if (Camera2Enumerator.isSupported(mContext)) {
            videoCapturer = createCameraCapture(new Camera2Enumerator(mContext));
        } else {
            videoCapturer = createCameraCapture(new Camera1Enumerator(true));
        }
        return videoCapturer;
    }

    /**
     * 创建相机媒体流
     */
    private VideoCapturer createCameraCapture(CameraEnumerator enumerator) {
        final String[] deviceNames = enumerator.getDeviceNames();
        // First, try to find front facing camera
        for (String deviceName : deviceNames) {
            if (enumerator.isFrontFacing(deviceName)) {
                VideoCapturer videoCapturer = enumerator.createCapturer(deviceName, null);
                if (videoCapturer != null) {
                    return videoCapturer;
                }
            }
        }
        // Front facing camera not found, try something else
        for (String deviceName : deviceNames) {
            if (!enumerator.isFrontFacing(deviceName)) {
                VideoCapturer videoCapturer = enumerator.createCapturer(deviceName, null);

                if (videoCapturer != null) {
                    return videoCapturer;
                }
            }
        }
        return null;
    }

    //------------------------------------回调---------------------------------------------
    @Override
    public void onSendIceCandidate(String userId, IceCandidate candidate) {
        if (mCallback != null) {
            mCallback.onSendIceCandidate(userId, candidate);
        }

    }

    @Override
    public void onSendOffer(String userId, SessionDescription description) {
        if (mCallback != null) {
            mCallback.onSendOffer(userId, description);
        }
    }

    @Override
    public void onSendAnswer(String userId, SessionDescription description) {
        if (mCallback != null) {
            mCallback.onSendAnswer(userId, description);
        }
    }

    @Override
    public void onRemoteStream(String userId, MediaStream stream) {
        if (mCallback != null) {
            mCallback.onRemoteStream(userId);
        }
    }

    @Override
    public void onRemoveStream(String userId, MediaStream stream) {
        leaveRoom(userId);
    }

    @Override
    public void onDisconnected(String userId) {
        if (mCallback != null) {
            Log.d(TAG, "onDisconnected mCallback != null");
            mCallback.onDisconnected(userId);
        } else {
            Log.d(TAG, "onDisconnected mCallback == null");
        }
    }

}
