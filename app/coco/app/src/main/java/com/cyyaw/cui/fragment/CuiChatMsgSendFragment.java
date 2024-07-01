package com.cyyaw.cui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import com.cyyaw.coco.R;


public class CuiChatMsgSendFragment extends Fragment {

    private CuiChatMsgFromCallBack callBack;


    public CuiChatMsgSendFragment() {
    }

    public CuiChatMsgSendFragment(CuiChatMsgFromCallBack callBack) {
        this.callBack = callBack;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cui_chat_msg_send, container, false);

        return view;
    }


    /**
     * 方法回调
     */
    public interface CuiChatMsgFromCallBack {


    }


}