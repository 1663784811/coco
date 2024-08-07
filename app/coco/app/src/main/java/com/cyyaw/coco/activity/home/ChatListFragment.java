package com.cyyaw.coco.activity.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.cyyaw.coco.R;
import com.cyyaw.coco.activity.SearchActivity;
import com.cyyaw.coco.activity.home.adapter.MyViewPage2Adapter;
import com.cyyaw.cui.fragment.CuiNavBarFragment;
import com.cyyaw.cui.fragment.CuiTextFragment;
import com.cyyaw.cui.window.CuiPopWindow;

import java.util.ArrayList;
import java.util.List;


/**
 * 消息页面
 */
public class ChatListFragment extends Fragment {


    private CuiPopWindow window;
    private ChatListMsgFragment chatListMsgFragment;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main_chat, container, false);
        window = CuiPopWindow.getWindow(getContext(), R.layout.activity_main_chat_menu);
        CuiNavBarFragment nav = new CuiNavBarFragment(null, new CuiNavBarFragment.UiNavBarFragmentCallBack() {
            @Override
            public void clickMore(View v) {
                window.show(v, 0, 0);
            }
        }, false, true);

        CuiTextFragment friends = new CuiTextFragment("好友");
        CuiTextFragment msg = new CuiTextFragment("消息");


        nav.addCenterFragment(friends);
        nav.addCenterFragment(msg);
        getChildFragmentManager().beginTransaction().add(R.id.header_title, nav).commit();


        ViewPager2 vp = view.findViewById(R.id.char_ViewPager);
        List<Fragment> pageData = new ArrayList<>();
        pageData.add(new ChatListFriendsFragment());
        chatListMsgFragment = new ChatListMsgFragment();
        pageData.add(chatListMsgFragment);
        MyViewPage2Adapter adapter = new MyViewPage2Adapter(getActivity(), pageData);
        vp.setAdapter(adapter);
        vp.setCurrentItem(0);

        window.getView().findViewById(R.id.addFriends).setOnClickListener((View v) -> {
            SearchActivity.openActivity(getContext());
        });


        return view;
    }


    /**
     * 接收到消息
     */
    public void receiveMsg(String fromId, String msg) {
        chatListMsgFragment.receiveMsg(fromId, msg);
    }
}
