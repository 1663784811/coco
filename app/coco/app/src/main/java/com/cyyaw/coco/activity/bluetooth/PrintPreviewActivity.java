package com.cyyaw.coco.activity.bluetooth;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cyyaw.bluetooth.out.BlueToothManager;
import com.cyyaw.coco.MyApplication;
import com.cyyaw.coco.R;
import com.cyyaw.coco.common.BaseAppCompatActivity;
import com.cyyaw.coco.common.view.PrintBitMapImageView;
import com.cyyaw.coco.entity.BluetoothEntity;
import com.cyyaw.coco.utils.ActivityUtils;

import java.util.List;

/**
 * 打印机
 */
public class PrintPreviewActivity extends BaseAppCompatActivity {


    private View nowPrintBtn;
    private PrintBitMapImageView printPager;
    private TextView blueToothName;
    private TextView blueToothStatus;




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
                }
            });

        });
        connectBlueTooth();
    }

    // 连接蓝牙
    private void connectBlueTooth() {
        ActivityUtils.blueToothPermissions(this, () -> {
            blueToothStatus.setText("正在连接...");
            BlueToothManager.getInstance().connectBlueTooth("ssssssss");
        });
    }

    @Override
    public Activity getActivity() {
        return this;
    }


//    public void socketNotify(int state, Object obj) {
//        switch (state) {
//            case BtStatus.CONNECTED:
//                blueToothStatus.setText("已连接");
//                break;
//            case BtStatus.DISCONNECTED:
//                blueToothStatus.setText("断开连接");
//                break;
//            case BtStatus.MSG:
//
//                break;
//            default:
//        }
//
//    }

}

