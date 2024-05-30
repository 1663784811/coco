package com.cyyaw.coco.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;

import com.cyyaw.coco.R;
import com.cyyaw.coco.common.BaseAppCompatActivity;
import com.cyyaw.tabbar.Tabbar;

import java.util.ArrayList;

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





        viewPager = findViewById(R.id.view_pager);
        pagerAdapter = new MyPagerAdapter(this, new ArrayList<>());
        viewPager.setAdapter(pagerAdapter);
        // 当滑动的时候
        viewPager.setOnScrollChangeListener((View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) -> {
            System.out.println("sss: " + scrollX + "   Y" + scrollY);
        });

        Tabbar tabbar = findViewById(R.id.tabbar);






    }


}