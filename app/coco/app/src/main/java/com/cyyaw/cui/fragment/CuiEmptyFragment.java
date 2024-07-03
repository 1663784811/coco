package com.cyyaw.cui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.cyyaw.coco.R;

import java.util.ArrayList;
import java.util.List;


/**
 * 空页面
 */
public class CuiEmptyFragment extends Fragment {

    private CuiChatInputCallBack callBack;

    private List<CuiChatInputIconFragment> iconList = new ArrayList<>();


    public CuiEmptyFragment() {
    }

    public CuiEmptyFragment(CuiChatInputCallBack callBack) {
        this.callBack = callBack;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cui_fragment_empty, container, false);

        return view;
    }


    public void addIcon(CuiChatInputIconFragment icon) {
        this.iconList.add(icon);
    }

    /**
     * 方法回调
     */
    public interface CuiChatInputCallBack {

        /**
         * 点击图标
         */
        default void clickIcon(View v) {
        }
    }


}