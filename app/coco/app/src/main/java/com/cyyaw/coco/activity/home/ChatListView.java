package com.cyyaw.coco.activity.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.cyyaw.coco.R;
import com.cyyaw.coco.activity.home.adapter.MyPagerAdapter;
import com.cyyaw.cui.fragment.CuiNavBarFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * 消息页面
 */
public class ChatListView extends Fragment {

    private FragmentActivity context;


    public ChatListView(FragmentActivity context) {
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main_chat, container, false);

        CuiNavBarFragment nav = new CuiNavBarFragment("关注");
        getChildFragmentManager().beginTransaction().add(R.id.header_title, nav).commit();

        ViewPager vp = view.findViewById(R.id.char_ViewPager);
        List<Fragment> pageData = new ArrayList<>();

        pageData.add(new ChatListFriendsView(context));
        pageData.add(new ChatListMsgView(context));

        MyPagerAdapter adapter = new MyPagerAdapter(pageData, getChildFragmentManager());
        vp.setAdapter(adapter);
        vp.setCurrentItem(0);
        return view;
    }


}
