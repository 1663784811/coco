package com.cyyaw.coco.activity.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.cyyaw.coco.R;
import com.cyyaw.coco.activity.home.adapter.MyPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChatListView extends Fragment {

    private FragmentActivity context;


    public ChatListView(FragmentActivity context) {
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main_chat, container, false);
        ViewPager2 vp = view.findViewById(R.id.char_ViewPager);
        List<Fragment> pageData = new ArrayList<>();

        pageData.add(new ChatListFriendsView(context));
        pageData.add(new ChatListMsgView(context));

        MyPagerAdapter adapter = new MyPagerAdapter(context, pageData);
        vp.setAdapter(adapter);
        vp.setCurrentItem(0);

        return view;
    }


}
