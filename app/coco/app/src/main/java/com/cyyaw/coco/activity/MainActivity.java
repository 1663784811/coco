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

public class MainActivity extends BaseAppCompatActivity {

    private final String TAG = Class.class.getName();

    private ViewPager viewPager;
    private MyPagerAdapter pagerAdapter;
    private String[] data = {"Page 1", "Page 2", "Page 3"};


    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        new Tabbar(this);


        viewPager = findViewById(R.id.view_pager);
        pagerAdapter = new MyPagerAdapter(this, data);


        viewPager.setAdapter(pagerAdapter);

        viewPager.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                System.out.println("sss: " + scrollX + "   Y" + scrollY);
            }
        });

        Tabbar tabbar = findViewById(R.id.tabbar);






    }


}