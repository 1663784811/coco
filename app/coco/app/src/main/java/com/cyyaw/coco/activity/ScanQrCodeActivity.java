package com.cyyaw.coco.activity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;

import com.cyyaw.coco.R;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.IOException;
import java.util.concurrent.ExecutionException;


/**
 * 扫二维码
 */
public class ScanQrCodeActivity extends AppCompatActivity {


    public static void openActivity(Context context) {
        Intent intent = new Intent(context, ScanQrCodeActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr_code);
    }


    @Override
    protected void onResume() {
        super.onResume();
        SurfaceView surfaceView = findViewById(R.id.scanner_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
//        if (hasSurface) {
//            initCamera(surfaceHolder);
//        } else {
//            surfaceHolder.addCallback(this);
//            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
//        }
//        decodeFormats = null;
//        characterSet = null;
//
//        playBeep = true;
//        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
//        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
//            playBeep = false;
//        }
//        initBeepSound();
//        vibrate = true;
    }


    @Override
    protected void onPause() {
        super.onPause();

    }


    /**
     * 初始化摄像头
     */
    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
//            CameraManager.get().openDriver(surfaceHolder);
        } catch (Exception ioe) {

        }


        ListenableFuture<ProcessCameraProvider> camerax = ProcessCameraProvider.getInstance(this);
        camerax.addListener(() -> {
            try {
                ProcessCameraProvider processCameraProvider = camerax.get();
                Preview.Builder builder = new Preview.Builder();
                Preview preview = builder.build();

                CameraSelector cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;



                processCameraProvider.unbindAll();

                processCameraProvider.bindToLifecycle( this, cameraSelector, preview);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }, ContextCompat.getMainExecutor(this));


    }

}