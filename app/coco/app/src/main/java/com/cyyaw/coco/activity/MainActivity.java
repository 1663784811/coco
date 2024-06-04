package com.cyyaw.coco.activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.ViewPager;

import com.cyyaw.coco.R;
import com.cyyaw.coco.activity.home.ChatListView;
import com.cyyaw.coco.activity.home.HomeView;
import com.cyyaw.coco.activity.home.adapter.MyPagerAdapter;
import com.cyyaw.coco.activity.home.MyView;
import com.cyyaw.coco.common.BaseAppCompatActivity;
import com.cyyaw.tabbar.CommonTabLayout;
import com.cyyaw.tabbar.listener.CustomTabEntity;
import com.cyyaw.tabbar.listener.OnTabSelectListener;
import com.cyyaw.tabbar.listener.TabBarItemEntity;

import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
public class MainActivity extends BaseAppCompatActivity {

    private final String TAG = MainActivity.class.getName();


    // =====================================   tabbar
    private final List<CustomTabEntity> tabBarData = new ArrayList<>();
    // =====================================

    @Override
    public Activity getActivity() {
        return this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // =====================================   tabbar
        tabBarData.add(new TabBarItemEntity("首页", R.mipmap.tab_home_unselect, R.mipmap.tab_home_select));
        tabBarData.add(new TabBarItemEntity("消息", R.mipmap.tab_speech_unselect, R.mipmap.tab_speech_select));
        tabBarData.add(new TabBarItemEntity("我的", R.mipmap.tab_contact_unselect, R.mipmap.tab_contact_select));
        CommonTabLayout tabBar = findViewById(R.id.app_tabBar);
        tabBar.setTabData(tabBarData);
        // 初始化ViewPage
        List<View> pageData = new ArrayList<>();
        pageData.add(new HomeView(this));
        pageData.add(new ChatListView(this));
        pageData.add(new MyView(this));

        ViewPager appTabBarViewPager = findViewById(R.id.app_tabBar_ViewPager);
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(this, pageData);
        appTabBarViewPager.setAdapter(myPagerAdapter);
        appTabBarViewPager.setCurrentItem(tabBar.getCurrentTab());

        // ======= 事件设置
        tabBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                appTabBarViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
        appTabBarViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                tabBar.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        // =====================================


//        startService(new Intent(this, BlueToothService.class));

    }


}