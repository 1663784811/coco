package com.cyyaw.coco.entity;


import com.cyyaw.cui.fragment.CuiMsgListItemFragment;

public class MsgUserEntity {


    private String tid;

    private CuiMsgListItemFragment fragment;

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public CuiMsgListItemFragment getFragment() {
        return fragment;
    }

    public void setFragment(CuiMsgListItemFragment fragment) {
        this.fragment = fragment;
    }
}
