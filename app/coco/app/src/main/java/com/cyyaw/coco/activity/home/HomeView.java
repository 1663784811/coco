package com.cyyaw.coco.activity.home;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cyyaw.coco.R;

import java.util.ArrayList;
import java.util.List;

public class HomeView extends LinearLayout {

    private Context context;


    public HomeView(Context context) {
        super(context);
        initView(context, null);
    }

    public HomeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, null);
    }

    public HomeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, null);
    }


    private void initView(Context context, AttributeSet attrs) {
        //加载布局
        View viewHome = LayoutInflater.from(context).inflate(R.layout.activity_main_home, this, true);
        RecyclerView recyclerView = viewHome.findViewById(R.id.equipmentList);


        //设置适配器
        List<String> datas = new ArrayList<>();
        datas = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            datas.add("item:" + i);
        }
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(datas);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


    }

}
