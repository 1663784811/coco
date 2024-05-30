package com.cyyaw.coco.activity.home;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;

import com.cyyaw.coco.R;

public class HomeView  extends View {
    public HomeView(Context context) {
        super(context);
    }

    public HomeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HomeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public HomeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }



    private void init(Context context, AttributeSet attrs) {

        //加载布局
        View inflate = LayoutInflater.from(context).inflate(R.layout.activity_main_home, null);



    }

}
