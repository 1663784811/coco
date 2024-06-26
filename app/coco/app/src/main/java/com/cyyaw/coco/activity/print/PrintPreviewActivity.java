package com.cyyaw.coco.activity.print;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cyyaw.bluetooth.device.BtStatus;
import com.cyyaw.bluetooth.out.BlueToothConnectCallBack;
import com.cyyaw.bluetooth.out.BlueToothManager;
import com.cyyaw.coco.MyApplication;
import com.cyyaw.coco.R;
import com.cyyaw.coco.common.BaseAppCompatActivity;
import com.cyyaw.coco.common.view.PrintBitMapImageView;
import com.cyyaw.coco.utils.ActivityUtils;

import java.util.List;

/**
 * 打印机
 */
public class PrintPreviewActivity extends BaseAppCompatActivity implements BlueToothConnectCallBack {


    private static final String addressKey = "keyAddress";

    private View nowPrintBtn;
    private PrintBitMapImageView printPager;
    private TextView blueToothName;
    private TextView blueToothStatus;

    private String blueToothAddress;


    public static void openActivity(Context context, String address) {
        Intent intent = new Intent(context, PrintPreviewActivity.class);
        intent.putExtra(addressKey, address);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_preview);
        // 接收数据

        // ========================
        nowPrintBtn = findViewById(R.id.nowPrintBtn);
        printPager = findViewById(R.id.printPager);
        blueToothName = findViewById(R.id.blueToothName);
        blueToothStatus = findViewById(R.id.blueToothStatus);
        // ========================
        blueToothAddress = getIntent().getStringExtra(addressKey);
        blueToothName.setText("蓝牙: " + blueToothAddress);
        printPager.setWordData("白云机场综保南区开园3周年，跨境电商进出口货值超300亿元");
        nowPrintBtn.setOnClickListener((View v) -> {
            // 第一步: 获取打印像素数据
            List<byte[]> printImageData = printPager.getPrintImageData();
            // 第二步: 发送数据
            MyApplication.toast(printImageData.size() + "行数据");
            int size = printImageData.size();
            MyApplication.run(() -> {
                for (int i = 0; i < size; i++) {
                    BlueToothManager.getInstance().sendData(blueToothAddress, printImageData.get(i));
                }
            });
        });
        connectBlueTooth();
    }

    // 连接蓝牙
    private void connectBlueTooth() {
        ActivityUtils.blueToothPermissions(this, () -> {
            BlueToothManager.getInstance().connectBlueTooth(blueToothAddress, PrintPreviewActivity.this);
        });
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void statusCallBack(String address, BtStatus status) {
        blueToothStatus.setText(status.getNote());
    }

    @Override
    public void readData(String address, byte[] data) {

    }

}

