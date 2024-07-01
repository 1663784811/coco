package com.cyyaw.cui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.cyyaw.coco.R;


public class CuiChatInputIconFragment extends Fragment {

    private CuiChatInputIconCallBack callBack;

    private int icon;
    private String text;


    public CuiChatInputIconFragment(int icon, String text) {
        this(icon, text, null);
    }


    public CuiChatInputIconFragment(int icon, String text, CuiChatInputIconCallBack callBack) {
        this.icon = icon;
        this.text = text;
        this.callBack = callBack;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cui_chat_input_icon, container, false);
        ImageView img = view.findViewById(R.id.cui_chat_input_icon);
        img.setImageResource(icon);
        TextView tx = view.findViewById(R.id.cui_chat_input_text);
        tx.setText(text);
        return view;
    }


    /**
     * 方法回调
     */
    public interface CuiChatInputIconCallBack {

        /**
         * 点击图标
         */
        default void clickIcon() {
        }
    }


}