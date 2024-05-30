package com.cyyaw.coco.activity;

import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.cyyaw.coco.R;
import com.cyyaw.coco.activity.home.HomeView;
import com.cyyaw.coco.activity.home.MsgView;
import com.cyyaw.coco.activity.home.MyPagerAdapter;
import com.cyyaw.coco.activity.home.MyView;
import com.cyyaw.coco.common.BaseAppCompatActivity;
import com.cyyaw.tabbar.Tabbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseAppCompatActivity {

    private final String TAG = Class.class.getName();

    private ViewPager viewPager;
    private MyPagerAdapter pagerAdapter;
    private String[] data = {"1", "2", "Page 3"};


    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<View> list = new ArrayList<>();
        list.add(new HomeView(this));
        list.add(new MsgView(this));
        list.add(new MyView(this));

        viewPager = findViewById(R.id.view_pager);
        pagerAdapter = new MyPagerAdapter(this, list);
        viewPager.setAdapter(pagerAdapter);
        // 当滑动的时候
        viewPager.setOnScrollChangeListener((View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) -> {
            System.out.println("sss: " + scrollX + "   Y" + scrollY);
        });

        Tabbar tabbar = findViewById(R.id.tabbar);






    }


}