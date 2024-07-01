package com.cyyaw.cui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.cyyaw.coco.R;


public class CuiChatInputFragment extends Fragment {

    private String title;
    private CuiChatInputFragment callBack;


    public CuiChatInputFragment() {
    }

    public CuiChatInputFragment(String title, CuiChatInputFragment callBack) {
        this.title = title;
        this.callBack = callBack;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cui_chat_input, container, false);

        return view;
    }


    /**
     * 方法回调
     */
    public interface CuiChatInputFragmentCallBack {


    }


}