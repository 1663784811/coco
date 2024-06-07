package com.cyyaw.coco.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cyyaw.coco.R;
import com.cyyaw.coco.activity.adapter.BluetoothListAdapter;
import com.cyyaw.coco.common.BaseAppCompatActivity;
import com.cyyaw.coco.common.BroadcastData;
import com.cyyaw.coco.entity.BluetoothEntity;
import com.cyyaw.coco.utils.BluetoothUtils;

public class PrintPreviewActivity extends BaseAppCompatActivity {
    private final String TAG = PrintPreviewActivity.class.getName();

    /**
     * 选择的蓝牙
     */
    private BluetoothEntity bluetooth;


    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_preview);
        // ========================
        // 接收数据
        Intent inx = getIntent();
        BroadcastData broadcastData = inx.getSerializableExtra("data", BroadcastData.class);
        bluetooth = (BluetoothEntity) broadcastData.getData();

        // ========================
        View nowPrintBtn = findViewById(R.id.nowPrintBtn);
        View connectBtn = findViewById(R.id.connectBtn);
        View searchBluetoothBtn = findViewById(R.id.searchBluetoothBtn);

        RecyclerView bluetoothList = findViewById(R.id.bluetoothList);
        BluetoothListAdapter bluetoothListAdapter = new BluetoothListAdapter(this, null);
        bluetoothList.setLayoutManager(new LinearLayoutManager(this));
        bluetoothList.setAdapter(bluetoothListAdapter);

        nowPrintBtn.setOnClickListener((View v) -> {
            // 第一步: 获取打印像素数据

            // 第二步: 发送数据
//            bluetoothGatt

            BluetoothUtils.sendData(PrintPreviewActivity.this, new byte[10]);
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

