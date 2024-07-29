package com.cyyaw.cui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

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

        view.findViewById(R.id.cui_face);


        return view;
    }

    public CuiMsgEntity getData() {
        return this.data;
    }

    /**
     * 更新数据
     */
    public void updateData(CuiMsgEntity data) {


    }

}
