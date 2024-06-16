package com.cyyaw.coco.activity.bluetooth;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import androidx.core.app.ActivityCompat;

import com.cyyaw.coco.MyApplication;
import com.cyyaw.coco.R;
import com.cyyaw.coco.activity.bluetooth.bt.BtBase;
import com.cyyaw.coco.activity.bluetooth.bt.BtClient;
import com.cyyaw.coco.broadcast.BlueToothReceiver;
import com.cyyaw.coco.common.BaseAppCompatActivity;
import com.cyyaw.coco.common.view.PrintBitMapImageView;
import com.cyyaw.coco.entity.BluetoothEntity;
import com.cyyaw.coco.utils.ActivityUtils;
import com.cyyaw.coco.utils.BluetoothUtils;

import java.util.List;

public class PrintPreviewActivity extends BaseAppCompatActivity implements BtBase.Listener {
    private final String TAG = PrintPreviewActivity.class.getName();

    private final BtClient mClient = new BtClient(this, this);

    // =============
    private View nowPrintBtn;
    private PrintBitMapImageView printPager;
    private TextView blueToothName;
    private TextView blueToothStatus;
    // ========================

    /**
     * 选择的蓝牙
     */
    private BluetoothEntity bluetooth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_preview);
        // 接收数据
        bluetooth = ActivityUtils.getParameter(this, BluetoothEntity.class);
        // ========================
        nowPrintBtn = findViewById(R.id.nowPrintBtn);
        printPager = findViewById(R.id.printPager);
        blueToothName = findViewById(R.id.blueToothName);
        blueToothStatus = findViewById(R.id.blueToothStatus);
        // ========================
        blueToothName.setText("蓝牙: " + bluetooth.getName());

        printPager.setWordData("白云机场综保南区开园3周年，跨境电商进出口货值超300亿元");
        nowPrintBtn.setOnClickListener((View v) -> {
            // 第一步: 获取打印像素数据
            List<byte[]> printImageData = printPager.getPrintImageData();
            // 第二步: 发送数据
            MyApplication.toast(printImageData.size() + "行数据");
            int size = printImageData.size();

            MyApplication.run(() -> {
                for (int i = 0; i < size; i++) {
                    byte[] bytes = printImageData.get(i);
                    mClient.sendMsg(bytes);
                }
            });

        });
        connectBlueTooth();
    }

    // 连接蓝牙
    private void connectBlueTooth() {
        ActivityUtils.blueToothPermissions(this, () -> {
            blueToothStatus.setText("正在连接...");
            mClient.connect(MyApplication.blueTooth.get(bluetooth.getAddress()));
        });
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            // 将每个字节转换为两个十六进制字符
            String hex = Integer.toHexString(0xFF & b);
            if (hex.length() == 1) {
                hex = "0" + hex; // 如果只有一个字符，前面补0
            }
            hexString.append(" " + hex);
        }
        return hexString.toString();
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mClient.close();
    }

    @Override
    public void socketNotify(int state, Object obj) {
        switch (state) {
            case BtBase.Listener.CONNECTED:
                blueToothStatus.setText("已连接");
                break;
            case BtBase.Listener.DISCONNECTED:
                blueToothStatus.setText("断开连接");
                break;
            case BtBase.Listener.MSG:

                break;
            default:
        }

    }

}

