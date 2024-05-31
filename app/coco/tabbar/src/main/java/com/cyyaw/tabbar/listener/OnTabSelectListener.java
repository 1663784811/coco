package com.cyyaw.tabbar.listener;

public interface OnTabSelectListener {
    /**
     * 选择TAB
     */
    void onTabSelect(int position);

    /**
     * 重新选择
     */
    void onTabReselect(int position);
}