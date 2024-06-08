package com.cyyaw.coco.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.cyyaw.coco.MyApplication;
import com.cyyaw.coco.R;
import com.cyyaw.coco.common.BaseAppCompatActivity;
import com.cyyaw.coco.common.BroadcastData;
import com.cyyaw.coco.common.BroadcastEnum;
import com.cyyaw.coco.common.view.PrintBitMapImageView;
import com.cyyaw.coco.entity.BluetoothEntity;
import com.cyyaw.coco.utils.BluetoothUtils;

import java.util.List;

public class PrintPreviewActivity extends BaseAppCompatActivity {
    private final String TAG = PrintPreviewActivity.class.getName();

    private View nowPrintBtn;
    private View connectBtn;
    private View searchBluetoothBtn;
    private PrintBitMapImageView printPager;
    private TextView blueToothName;
    private TextView blueToothStatus;
    // ========================


    /**
     * 选择的蓝牙
     */
    private BluetoothEntity bluetooth;

    private BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // 通知页面
            } else {
                // 接收广播:  连接蓝牙
                BroadcastData data = intent.getSerializableExtra("data", BroadcastData.class);
                if (null != data) {
                    if (BroadcastEnum.BLUETOOTH_CONNECT_SUCCESS.getCode().equals(data.getCode())) {
                        blueToothStatus.setText("蓝牙连接成功");
                    } else if (BroadcastEnum.BLUETOOTH_CONNECT_FAIL.getCode().equals(data.getCode())) {
                        blueToothStatus.setText("蓝牙连接失败");
                    }
                }
            }
        }
    };


    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_preview);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BroadcastEnum.ACTIVITY_BLUETOOTH);
        ContextCompat.registerReceiver(this, br, intentFilter, ContextCompat.RECEIVER_EXPORTED);
        // ========================
        // 接收数据
        Intent inx = getIntent();
        BroadcastData broadcastData = inx.getSerializableExtra("data", BroadcastData.class);
        bluetooth = (BluetoothEntity) broadcastData.getData();

        // ========================
        nowPrintBtn = findViewById(R.id.nowPrintBtn);
        connectBtn = findViewById(R.id.connectBtn);
        searchBluetoothBtn = findViewById(R.id.searchBluetoothBtn);
        printPager = findViewById(R.id.printPager);
        blueToothName = findViewById(R.id.blueToothName);
        blueToothStatus = findViewById(R.id.blueToothStatus);
        blueToothName.setText("蓝牙: " + bluetooth.getName());

        printPager.setWordData("点击连接点击连接点击连接点击连接点击连接点击连接点击连接点击连接点击连接点击连接点击连接点击连接点击连接点击连接点击连接点击连接点击连接点击连接点击连接点击连接点击连接点击连接点击连接点击连接点击连接点击连接点击连接点击连接点击连接点击连接点击连接点击连接点击连接点击连接点击连接点击连接");

//        RecyclerView bluetoothList = findViewById(R.id.bluetoothList);
//        BluetoothListAdapter bluetoothListAdapter = new BluetoothListAdapter(this, null);
//        bluetoothList.setLayoutManager(new LinearLayoutManager(this));
//        bluetoothList.setAdapter(bluetoothListAdapter);

        nowPrintBtn.setOnClickListener((View v) -> {
            // 第一步: 获取打印像素数据
            List<byte[]> printImageData = printPager.getPrintImageData();
            // 第二步: 发送数据
            MyApplication.toast(printImageData.size() + "行数据");
            int size = printImageData.size();
            for (int i = 0; i < size; i++) {
                BluetoothUtils.sendData(PrintPreviewActivity.this, printImageData.get(i));
            }

        });

        /**
         * 搜索蓝牙
         */
        searchBluetoothBtn.setOnClickListener((View v) -> {

        });

        /**
         * 点击连接
         */
        connectBtn.setOnClickListener((View v) -> {
            blueToothStatus.setText("正在连接...");
            BluetoothUtils.link(this, bluetooth);
        });


    }


    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BluetoothUtils.unLink(this);
    }
}

