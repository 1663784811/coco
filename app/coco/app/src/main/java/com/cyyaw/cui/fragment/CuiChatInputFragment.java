package com.cyyaw.cui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.cyyaw.coco.R;


public class CuiChatInputFragment extends Fragment {

    private CuiChatInputCallBack callBack;


    public CuiChatInputFragment() {
    }

    public CuiChatInputFragment(CuiChatInputCallBack callBack) {
        this.callBack = callBack;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cui_chat_input, container, false);
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.add(R.id.cuiChatIconContainer, new CuiChatInputIconFragment(R.drawable.cui_icon_voice_24, "图片"));
        ft.add(R.id.cuiChatIconContainer, new CuiChatInputIconFragment(R.drawable.cui_icon_voice_24, "图片"));
        ft.add(R.id.cuiChatIconContainer, new CuiChatInputIconFragment(R.drawable.cui_icon_voice_24, "图片"));
        ft.add(R.id.cuiChatIconContainer, new CuiChatInputIconFragment(R.drawable.cui_icon_voice_24, "图片"));
        ft.commit();
        return view;
    }


    /**
     * 方法回调
     */
    public interface CuiChatInputCallBack {

        /**
         * 点击图标
         */
        default void clickIcon() {
        }
    }


}