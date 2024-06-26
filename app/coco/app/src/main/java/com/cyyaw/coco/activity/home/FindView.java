package com.cyyaw.coco.activity.home;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.cyyaw.coco.R;
import com.cyyaw.coco.activity.home.adapter.HomeBluetoothListAdapter;
import com.cyyaw.coco.activity.home.adapter.StaggeredGridLayoutManagerNonScrollable;
import com.cyyaw.coco.broadcast.BlueToothReceiver;
import com.cyyaw.coco.common.BaseAppCompatActivity;
import com.cyyaw.coco.common.BroadcastEnum;
import com.cyyaw.coco.entity.BluetoothEntity;
import com.cyyaw.bluetooth.BluetoothClassicService;
import com.cyyaw.coco.utils.ActivityUtils;

public class FindView extends LinearLayout {


    private static final String TAG = FindView.class.getName();

    private BaseAppCompatActivity context;

    private HomeBluetoothListAdapter homeBluetoothListAdapter;

    BlueToothReceiver br;


    public FindView(BaseAppCompatActivity context) {
        super(context);
        this.context = context;
        initView(null);
    }

    public FindView(BaseAppCompatActivity context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView(null);
    }

    public FindView(BaseAppCompatActivity context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView(null);
    }


    private void initView(AttributeSet attrs) {
        // =============== 加载布局
        View viewHome = LayoutInflater.from(context).inflate(R.layout.activity_main_find, this, true);
        // =============== 设置适配器
        RecyclerView recyclerView = viewHome.findViewById(R.id.equipmentList);

        // 瀑布流
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManagerNonScrollable(2, StaggeredGridLayoutManager.VERTICAL);
//        LinearLayoutManager layoutManager = new LinearLayoutManagerNonScrollable(context);


        recyclerView.setLayoutManager(layoutManager);
        homeBluetoothListAdapter = new HomeBluetoothListAdapter(context);
        recyclerView.setAdapter(homeBluetoothListAdapter);


        //
        for (int i = 0; i < 50; i++) {
            BluetoothEntity bluetooth = new BluetoothEntity();
            bluetooth.setName("sss:" + i);
            bluetooth.setAddress("ddd" + i);
            bluetooth.setType(0);
            bluetooth.setRssi(0);
            homeBluetoothListAdapter.updateData(bluetooth);
        }


        // 开启服务
        Intent intent = new Intent(context, BluetoothClassicService.class);
        intent.putExtra("clazz", BroadcastEnum.ACTIVITY_HOME);
        context.startService(intent);


        // 注册广播
//        br = new BlueToothReceiver(context, this);


        ActivityUtils.blueToothPermissions(context, () -> {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            BluetoothAdapter.getDefaultAdapter().startDiscovery();
        });
    }


//    public void foundDev(BluetoothDevice dev, short rssi) {
//        BluetoothEntity bluetooth = new BluetoothEntity();
//        bluetooth.setName(dev.getName());
//        bluetooth.setAddress(dev.getAddress());
//        bluetooth.setType(dev.getType());
//        bluetooth.setRssi(rssi);
//        homeBluetoothListAdapter.updateData(bluetooth);
//        MyApplication.blueTooth.put(dev.getAddress(), dev);
//    }

}
