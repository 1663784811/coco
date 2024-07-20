package com.cyyaw.cui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.cyyaw.coco.R;

/**
 * 头部导航
 */
public class CuiNavBarFragment extends Fragment {

    private String title;
    private UiNavBarFragmentCallBack callBack;

    // 显示返回按钮
    private boolean showBackBtn;
    // 显示更多按钮
    private boolean showMoreBtn;


    public CuiNavBarFragment() {
    }


    public CuiNavBarFragment(String title) {
        this(title, null, false, false);
    }

    public CuiNavBarFragment(String title, UiNavBarFragmentCallBack callBack, boolean showBackBtn, boolean showMoreBtn) {
        this.title = title;
        this.callBack = callBack;
        this.showBackBtn = showBackBtn;
        this.showMoreBtn = showMoreBtn;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cui_fragment_nav_bar, container, false);
        TextView navTitle = view.findViewById(R.id.navTitle);
        if (null != title) {
            navTitle.setText(title);
        }
        View navBackBtn = view.findViewById(R.id.navBackBtn);
        View navMoreBtn = view.findViewById(R.id.navMoreBtn);
        if (!showBackBtn) {
            navBackBtn.setVisibility(View.GONE);
        }
        if (!showMoreBtn) {
            navMoreBtn.setVisibility(View.GONE);
        }
        if (null != callBack) {
            navBackBtn.setOnClickListener((View v) -> {
                boolean b = callBack.clickBack();
                if (!b) {
                    getActivity().finish();
                }
            });
            navMoreBtn.setOnClickListener((View v) -> {
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
        default boolean clickBack() {
            return false;
        }

        /**
         * 点击返回
         */
        default boolean clickMore() {
            return false;
        }
    }


}