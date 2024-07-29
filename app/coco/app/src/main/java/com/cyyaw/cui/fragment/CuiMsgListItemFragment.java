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
import com.cyyaw.cui.fragment.entity.CuiMsgEntity;

public class CuiMsgListItemFragment extends Fragment {

    private View view;

    private CuiMsgEntity data;

    public CuiMsgListItemFragment(CuiMsgEntity data) {
        this.data = data;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.cui_msg_list_item, container, false);
        updateData(data);

        return view;
    }

    public CuiMsgEntity getData() {
        return this.data;
    }

    /**
     * 更新数据
     */
    public void updateData(CuiMsgEntity data) {
        String face = data.getFace();
        ImageView cuiFace = view.findViewById(R.id.cuiFace);
        Glide.with(view.getContext()).load(face).into(cuiFace);
        //
        TextView cuiUserName = view.findViewById(R.id.cuiUserName);
        cuiUserName.setText(data.getUserName());
        //
        TextView cuiMessage = view.findViewById(R.id.cuiMessage);
        cuiMessage.setText(cuiMessage.getText());
        
    }

}
