package com.cyyaw.coco.activity.home;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.cyyaw.bluetooth.entity.BluetoothEntity;
import com.cyyaw.bluetooth.out.BlueToothCallBack;
import com.cyyaw.bluetooth.out.BlueToothManager;
import com.cyyaw.coco.R;
import com.cyyaw.coco.activity.home.adapter.HomeBluetoothListAdapter;
import com.cyyaw.coco.activity.home.adapter.StaggeredGridLayoutManagerNonScrollable;
import com.cyyaw.coco.common.BaseAppCompatActivity;

public class FindView extends LinearLayout {

    private BaseAppCompatActivity context;

    private HomeBluetoothListAdapter homeBluetoothListAdapter;


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

        BlueToothManager.getInstance().setCallBack(new BlueToothCallBack() {
            @Override
            public void error() {

            }

            @Override
            public void foundBluetooth(BluetoothEntity bluetooth) {
                com.cyyaw.coco.entity.BluetoothEntity bt = new com.cyyaw.coco.entity.BluetoothEntity();
                bt.setName("sss:");
                bt.setAddress(bluetooth.getDev().getAddress());
                bt.setType(0);
                bt.setRssi(0);
                homeBluetoothListAdapter.updateData(bt);
            }

            @Override
            public void readData(String address, byte[] data) {

            }
        });
        BlueToothManager.getInstance().discoveryBlueTooth();
    }
}
