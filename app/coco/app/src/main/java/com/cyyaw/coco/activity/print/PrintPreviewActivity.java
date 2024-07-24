package com.cyyaw.coco.activity.print;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cyyaw.bluetooth.entity.BtStatus;
import com.cyyaw.bluetooth.out.BlueToothConnectCallBack;
import com.cyyaw.bluetooth.out.BlueToothManager;
import com.cyyaw.coco.MyApplication;
import com.cyyaw.coco.R;
import com.cyyaw.coco.common.BaseAppCompatActivity;
import com.cyyaw.coco.common.view.PrintBitMapImageView;
import com.cyyaw.coco.utils.ActivityUtils;
import com.cyyaw.cui.fragment.CuiNavBarFragment;
import com.cyyaw.cui.view.CuiButton;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 打印机
 */
public class PrintPreviewActivity extends BaseAppCompatActivity implements BlueToothConnectCallBack {

    private static final String TAG = PrintPreviewActivity.class.getName();

    private static final String addressKey = "keyAddress";
    private static final String printDataKey = "printDataKey";

    private CuiButton nowPrintBtn;
    private PrintBitMapImageView printPager;
    private TextView blueToothName;
    private TextView blueToothStatus;

    private String blueToothAddress;
    private String printData;


    public static void openActivity(Context context, String address, String data) {
        Intent intent = new Intent(context, PrintPreviewActivity.class);
        intent.putExtra(addressKey, address);
        intent.putExtra(printDataKey, data);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_preview);
        // ===
        CuiNavBarFragment nav = new CuiNavBarFragment("打印预览", null, true, false);
        getSupportFragmentManager().beginTransaction().add(R.id.header_title, nav).commit();

        // 接收数据
        blueToothAddress = getIntent().getStringExtra(addressKey);
        printData = getIntent().getStringExtra(printDataKey);
        // ========================
        nowPrintBtn = findViewById(R.id.nowPrintBtn);
        printPager = findViewById(R.id.printPager);
        blueToothName = findViewById(R.id.blueToothName);
        blueToothStatus = findViewById(R.id.blueToothStatus);
        // ========================

        blueToothName.setText("蓝牙: " + blueToothAddress);
        printPager.setWordData(printData);
        nowPrintBtn.setOnClick((View v) -> {
            // 第一步: 获取打印像素数据
            List<byte[]> printImageData = printPager.getPrintImageData();
            // 第二步: 发送数据
            MyApplication.toast(printImageData.size() + "行数据");
            int size = printImageData.size();
            MyApplication.run(() -> {
                for (int i = 0; i < size; i++) {
                    BlueToothManager.sendData(blueToothAddress, printImageData.get(i));
                    Log.e(TAG, "正在发送, 共" + size + " 发送:" + i);
                }
                nowPrintBtn.setDisabled(false);
                Log.e(TAG, "statusCallBack: 结束 1111111111111111111");
            });
        });

        AtomicInteger i = new AtomicInteger();
        Button testBtn = findViewById(R.id.testBtn);
        testBtn.setOnClickListener((View v) -> {
            i.getAndIncrement();
            if (i.get() % 2 == 0) {
                nowPrintBtn.setDisabled(false);
            } else {
                nowPrintBtn.setDisabled(true);
            }
        });

        connectBlueTooth();
    }

    /**
     * 连接蓝牙
     */
    private void connectBlueTooth() {
        ActivityUtils.blueToothPermissions(this, () -> {
            BlueToothManager.connectCallBack(PrintPreviewActivity.this);
            BlueToothManager.connectBlueTooth(blueToothAddress);
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        BlueToothManager.connectCallBack(null);
    }

    @Override
    public void statusCallBack(String address, BtStatus status) {
        MyApplication.post(() -> {
            blueToothStatus.setText(status.getNote());
            if (status.equals(BtStatus.SUCCESS)) {
                nowPrintBtn.setDisabled(false);
            } else if (status.equals(BtStatus.FAIL)) {
                nowPrintBtn.setDisabled(true);
            } else if (status.equals(BtStatus.SENDDTAING)) {
                nowPrintBtn.setDisabled(true);
            } else if (status.equals(BtStatus.SENDDTASUCCESS)) {
                nowPrintBtn.setDisabled(false);
            }
        });
    }

    @Override
    public void readData(String address, byte[] data) {

    }

}

