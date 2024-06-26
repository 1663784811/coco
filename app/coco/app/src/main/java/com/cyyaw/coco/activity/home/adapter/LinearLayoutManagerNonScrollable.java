package com.cyyaw.coco.activity.home.adapter;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;


/**
 * 线性布局
 */
public class LinearLayoutManagerNonScrollable extends LinearLayoutManager {

    public LinearLayoutManagerNonScrollable(Context context) {
        super(context);
    }

    @Override
    public boolean canScrollVertically() {
        return false; // 禁用垂直滚动
    }

    @Override
    public boolean canScrollHorizontally() {
        return false; // 禁用水平滚动
    }

}
