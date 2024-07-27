package com.cyyaw.coco.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.cyyaw.coco.MyApplication;
import com.cyyaw.coco.R;
import com.cyyaw.cui.fragment.CuiButtonFragment;
import com.cyyaw.cui.fragment.CuiNavBarFragment;
import com.cyyaw.cui.fragment.CuiCellFragment;
import com.cyyaw.cui.fragment.CuiCellGroupFragment;
import com.cyyaw.cui.view.CuiButton;

public class SettingActivity extends AppCompatActivity {


    public static void openActivity(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();


        ft.add(R.id.header_bar, new CuiNavBarFragment("设置", new CuiNavBarFragment.UiNavBarFragmentCallBack() {
            @Override
            public boolean clickBack(View v) {
                return false;
            }
        }, true, false));

        CuiCellGroupFragment group = new CuiCellGroupFragment();
        group.addCell(new CuiCellFragment("账号与安全", (View v) -> {
            MyApplication.toast("sssssssss");
        }));
        group.addCell(new CuiCellFragment("隐私设置", (View v) -> {
            MyApplication.toast("sssssssss");
        }));
        ft.add(R.id.settingContainer, group);

        CuiCellGroupFragment group2 = new CuiCellGroupFragment();
        group2.addCell(new CuiCellFragment("通知设置"));

        ft.add(R.id.settingContainer, group2);

        ft.commit();

//        findViewById(R.id.logOutBtn).setOnClickListener((View v) -> {
//            // 清除token
//            MyApplication.saveToken("");
//            // 跳转登录页面
//            LoginActivity.openActivity(SettingActivity.this);
//            finish();
//        });


    }


}