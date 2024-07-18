package com.cyyaw.cui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.cyyaw.coco.R;
import com.cyyaw.cui.fragment.callback.CuiSelectCallBack;
import com.cyyaw.cui.view.CuiPopup;

import java.util.ArrayList;
import java.util.List;


/**
 * 上拉弹出层
 */
public class CuiSelectItemFragment extends Fragment {

    private static final String TAG = CuiSelectItemFragment.class.getName();

    private String name;
    private String data;
    private CuiSelectCallBack callBack;

    private View view;

    public CuiSelectItemFragment(String name, String data, CuiSelectCallBack callBack) {
        this.name = name;
        this.data = data;
        this.callBack = callBack;
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.cui_select_item, container, false);
        if (null != callBack) {
            view.setOnClickListener((View v) -> {
                callBack.select(view, data);
            });
        }
        return view;
    }

}
