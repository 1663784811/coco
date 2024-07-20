package com.cyyaw.cui.fragment;

import android.view.View;

import androidx.fragment.app.Fragment;

import com.cyyaw.cui.fragment.callback.CuiSelectCallBack;

/**
 * 选择
 */
public abstract class CuiSelectItem extends Fragment {

    protected String label;
    protected String value;

    protected CuiSelectCallBack callBack;

    protected View view;
    protected boolean select;


    public abstract void select(boolean select);


    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }
}
