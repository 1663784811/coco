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
import com.cyyaw.cui.fragment.callback.CuiClickCallBack;

public class CuiFriendsListItemFragment extends Fragment {

    private View view;
    // 头像
    private String face;
    // 名称
    private String name;

    private CuiClickCallBack callBack;

    public CuiFriendsListItemFragment(String face, String name) {
        this(face, name, null);
    }

    public CuiFriendsListItemFragment(String face, String name, CuiClickCallBack callBack) {
        this.face = face;
        this.name = name;
        this.callBack = callBack;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.cui_friends_item, container, false);
        view.findViewById(R.id.cui_face);
        ImageView cuiFace = view.findViewById(R.id.cuiFace);
        Glide.with(view.getContext()).load(face).into(cuiFace);
        if (null != name) {
            TextView userName = view.findViewById(R.id.userName);
            userName.setText(name);
        }
        if (null != callBack) {
            view.setOnClickListener((View v) -> {
                callBack.clickIcon(v);
            });
        }
        return view;
    }


    public void setClickCallBack(CuiClickCallBack callBack) {
        this.callBack = callBack;
    }

}
