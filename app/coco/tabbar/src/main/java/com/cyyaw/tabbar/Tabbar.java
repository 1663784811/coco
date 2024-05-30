package com.cyyaw.tabbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;


public class Tabbar extends LinearLayout {


    public Tabbar(Context context) {
        super(context);
        initView(context);
    }

    public Tabbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public Tabbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public Tabbar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }



    public void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.tx_layout_tabbar, this, true);
    }






}