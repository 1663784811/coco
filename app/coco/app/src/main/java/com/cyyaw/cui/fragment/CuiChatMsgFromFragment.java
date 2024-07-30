package com.cyyaw.cui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.cyyaw.coco.R;


public class CuiChatMsgFromFragment extends Fragment {

    private CuiChatMsgFromCallBack callBack;

    private String userId;
    private String userName;
    private String face;
    private String data;

    public CuiChatMsgFromFragment(String userId, String userName, String face, String data) {
        this.userId = userId;
        this.userName = userName;
        this.face = face;
        this.data = data;
    }

    public CuiChatMsgFromFragment(CuiChatMsgFromCallBack callBack) {
        this.callBack = callBack;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cui_chat_msg_from, container, false);
        if (null != face) {
            ImageView imageView = view.findViewById(R.id.faceImageView);
            Glide.with(view.getContext()).load(face).into(imageView);
        }
        if (data == null) {
            TextView contentText = view.findViewById(R.id.contentText);
            contentText.setText(data);
        }
        return view;
    }


    /**
     * 方法回调
     */
    public interface CuiChatMsgFromCallBack {


    }


}