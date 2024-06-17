package com.cyyaw.coco.activity.home;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cyyaw.coco.MyApplication;
import com.cyyaw.coco.R;
import com.cyyaw.coco.activity.home.adapter.ContentListAdapter;
import com.cyyaw.coco.activity.home.adapter.HomeBluetoothListAdapter;
import com.cyyaw.coco.activity.home.adapter.LinearLayoutManagerNonScrollable;
import com.cyyaw.coco.broadcast.BlueToothReceiver;
import com.cyyaw.coco.common.BaseAppCompatActivity;
import com.cyyaw.coco.common.BroadcastEnum;
import com.cyyaw.coco.entity.BluetoothEntity;
import com.cyyaw.coco.entity.ContentEntity;
import com.cyyaw.coco.service.BluetoothClassicService;
import com.cyyaw.coco.utils.ActivityUtils;

import java.util.ArrayList;
import java.util.List;

public class HomeView extends LinearLayout {

    private static final String TAG = HomeView.class.getName();

    private BaseAppCompatActivity context;

    private ContentListAdapter contentListAdapter;

    BlueToothReceiver br;


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
        RecyclerView recyclerView = viewHome.findViewById(R.id.contentList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        contentListAdapter = new ContentListAdapter(context);
        recyclerView.setAdapter(contentListAdapter);

        List<ContentEntity> dataList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            ContentEntity contentEntity = new ContentEntity();
            contentEntity.setName("sss");
            dataList.add(contentEntity);
        }
        contentListAdapter.setDataList(dataList);
    }


}
