package com.cyyaw.cui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.cyyaw.coco.R;


public class CuiNavBarFragment extends Fragment {

    private String title;
    private UiNavBarFragmentCallBack callBack;


    public CuiNavBarFragment() {
    }


    public CuiNavBarFragment(String title) {
        this(title, null);
    }

    public CuiNavBarFragment(String title, UiNavBarFragmentCallBack callBack) {
        this.title = title;
        this.callBack = callBack;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cui_fragment_nav_bar, container, false);
        TextView navTitle = view.findViewById(R.id.navTitle);
        if (null != title) {
            navTitle.setText(title);
        }
        if (null != callBack) {
            view.findViewById(R.id.navBackBtn).setOnClickListener((View v) -> {
                callBack.clickBack();
            });
            view.findViewById(R.id.navMoreBtn).setOnClickListener((View v) -> {
                callBack.clickMore();
            });

        }
        return view;
    }


    /**
     * 方法回调
     */
    public interface UiNavBarFragmentCallBack {

        /**
         * 点击返回
         */
        default void clickBack() {
        }

        /**
         * 点击返回
         */
        default void clickMore() {
        }
    }


}