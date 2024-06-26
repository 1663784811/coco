package com.cyyaw.coco.activity.home.adapter;

import androidx.recyclerview.widget.StaggeredGridLayoutManager;


/**
 * 瀑布流
 */
public class StaggeredGridLayoutManagerNonScrollable extends StaggeredGridLayoutManager {


    public StaggeredGridLayoutManagerNonScrollable(int spanCount, int orientation) {
        super(spanCount, orientation);
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



