package com.cyyaw.coco.activity.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cyyaw.coco.R;
import com.cyyaw.coco.activity.home.adapter.HomeBluetoothListAdapter;
import com.cyyaw.coco.activity.home.adapter.LinearLayoutManagerNonScrollable;
import com.cyyaw.coco.common.BaseAppCompatActivity;
import com.cyyaw.coco.common.BroadcastData;
import com.cyyaw.coco.common.BroadcastEnum;
import com.cyyaw.coco.common.permission.PermissionsCode;
import com.cyyaw.coco.entity.BluetoothEntity;
import com.cyyaw.coco.service.BluetoothClassicService;
import com.google.gson.Gson;

import org.json.JSONObject;

@RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
public class HomeView extends LinearLayout {

    private static final String TAG = HomeView.class.getName();

    private BaseAppCompatActivity context;

    private HomeBluetoothListAdapter homeBluetoothListAdapter;

    private BroadcastReceiver br = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // 接收广播: 搜索蓝牙、 连接蓝牙
            BroadcastData data = intent.getSerializableExtra("data", BroadcastData.class);
            if (BroadcastEnum.STATUS_SERVICE_INIT.equals(data.getCode())) {
                openBlueTooth();
            } else if (BroadcastEnum.BLUETOOTH_SEARCH.getCode().equals(data.getCode())) {
                BluetoothEntity bluetoothEntity = (BluetoothEntity) data.getData();
                // 更新列表数据
                homeBluetoothListAdapter.updateData(bluetoothEntity);
            }


            Log.i(TAG, "onReceive: 接收到广播:" + new Gson().toJson(data.getData()) );
        }
    };


    public HomeView(BaseAppCompatActivity context) {
        super(context);
        this.context = context;
        initView(null);
    }

    public HomeView(BaseAppCompatActivity context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView(null);
    }

    public HomeView(BaseAppCompatActivity context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView(null);
    }


    private void initView(AttributeSet attrs) {
        // =============== 加载布局
        View viewHome = LayoutInflater.from(context).inflate(R.layout.activity_main_home, this, true);
        // =============== 设置适配器
        RecyclerView recyclerView = viewHome.findViewById(R.id.equipmentList);
        LinearLayoutManager layoutManager = new LinearLayoutManagerNonScrollable(context);
        recyclerView.setLayoutManager(layoutManager);
        homeBluetoothListAdapter = new HomeBluetoothListAdapter(context);
        recyclerView.setAdapter(homeBluetoothListAdapter);
        // 开启服务
        Intent intent = new Intent(context, BluetoothClassicService.class);
        intent.putExtra("clazz", BroadcastEnum.ACTIVITY_HOME);
        context.startService(intent);
        // 注册广播
        IntentFilter ft = new IntentFilter();
        ft.addAction(BroadcastEnum.ACTIVITY_HOME);
        ft.addAction(BroadcastEnum.ACTIVITY_BLUETOOTH);
        ContextCompat.registerReceiver(context, br, ft, ContextCompat.RECEIVER_EXPORTED);
    }

    private void openBlueTooth() {
        context.requestPermissionsFn(PermissionsCode.BLUETOOTH_CONNECT, () -> {
            context.requestPermissionsFn(PermissionsCode.BLUETOOTH_REQUEST_ENABLE, () -> {
                // 搜索蓝牙
                Intent intent = new Intent();
                intent.setAction(BroadcastEnum.BLUETOOTH_CLASSIC);
                BroadcastData<String> broadcastData = new BroadcastData();
                broadcastData.setCode(BroadcastEnum.BLUETOOTH_SEARCH.getCode());
                broadcastData.setData(BroadcastEnum.BLUETOOTH_SEARCH.getNote());
                intent.putExtra("data", broadcastData);
                context.sendBroadcast(intent);
            });
        });
    }

}
