package com.cyyaw.coco.activity.home.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class MyPagerAdapter extends FragmentStateAdapter {


    private Context context;
    private List<Fragment> fragmentList;


    public MyPagerAdapter(@NonNull FragmentActivity context, List<Fragment> fragmentList) {
        super(context);
        this.context = context;
        this.fragmentList = fragmentList;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentList.size();
    }
}
