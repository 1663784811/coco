package com.cyyaw.tabbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CustomTitleBar extends RelativeLayout {
    private int mBgColor = Color.BLUE;
    private int mTextColor = Color.WHITE;
    private String mTitleText = "";

//    private ImageView btn_left;
//    private ImageView btn_right;
    private TextView tvTitle;
    private RelativeLayout relativeLayout;

    public CustomTitleBar(Context context) {
        super(context);
        initView(context);
    }

    public CustomTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTypeValue(context,attrs);
        initView(context);
    }

    public void initTypeValue(Context context , AttributeSet attrs){
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.CusTitleBar);
        mBgColor = a.getColor(R.styleable.CusTitleBar_bg_color, Color.YELLOW);
        mTitleText = a.getString(R.styleable.CusTitleBar_title_text);
        mTextColor = a.getColor(R.styleable.CusTitleBar_text_color,Color.RED);
        a.recycle();
    }

    public void initView(Context context){
        LayoutInflater.from(context).inflate(R.layout.layout_custom_titlebar,this,true);

//        btn_left = findViewById(R.id.btn_left);
//        btn_right = findViewById(R.id.btn_right);
        tvTitle = findViewById(R.id.tv_title);
        relativeLayout = findViewById(R.id.layout_titlebar_root);

        relativeLayout.setBackgroundColor(mBgColor);
        tvTitle.setTextColor(mTextColor);
        tvTitle.setText(mTitleText);
    }



}