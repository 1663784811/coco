package com.cyyaw.coco.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cyyaw.coco.R;
import com.cyyaw.coco.activity.adapter.BluetoothListAdapter;
import com.cyyaw.coco.common.BaseAppCompatActivity;
import com.cyyaw.coco.common.BroadcastData;
import com.cyyaw.coco.common.BroadcastEnum;
import com.cyyaw.coco.common.permission.PermissionsCode;

public class PrintPreviewActivity extends BaseAppCompatActivity {
    private final String TAG = PrintPreviewActivity.class.getName();


    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_preview);
        // ========================
        // 接收数据
        Intent inx = getIntent();
        BroadcastData bluetooth = inx.getSerializableExtra("data", BroadcastData.class);
        bluetooth.setCode(BroadcastEnum.BLUETOOTH_CLASSIC_CONNECT.getCode());
        // 连接蓝牙
        requestPermissionsFn(PermissionsCode.BLUETOOTH_CONNECT, () -> {
            Toast.makeText(PrintPreviewActivity.this, "有连接权限", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setAction(BroadcastEnum.BLUETOOTH_BR);
            intent.putExtra("data", bluetooth);
            sendBroadcast(intent);
        });


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
        });

        /**
         * 搜索蓝牙
         */
        searchBluetoothBtn.setOnClickListener((View v) -> {
            requestPermissionsFn(PermissionsCode.BLUETOOTH_REQUEST_ENABLE, () -> {
                // 搜索蓝牙
                BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
                BluetoothLeScanner bluetoothLeScanner = defaultAdapter.getBluetoothLeScanner();
                bluetoothLeScanner.startScan(new ScanCallback() {
                    @Override
                    public void onScanResult(int callbackType, ScanResult result) {
                        super.onScanResult(callbackType, result);
                        bluetoothListAdapter.setBlueTooth(result);
                    }
                });
            });
        });

        /**
         * 点击连接
         */
        connectBtn.setOnClickListener((View v) -> {


        });
    }


    @Override
    public Activity getActivity() {
        return this;
    }

}

