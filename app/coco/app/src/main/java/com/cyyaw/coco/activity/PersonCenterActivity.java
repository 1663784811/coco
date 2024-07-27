package com.cyyaw.coco.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.cyyaw.coco.MyApplication;
import com.cyyaw.coco.R;
import com.cyyaw.coco.dao.FriendsDao;
import com.cyyaw.coco.utils.ActivityUtils;
import com.cyyaw.cui.fragment.CuiCellFragment;
import com.cyyaw.cui.fragment.CuiCellGroupFragment;
import com.cyyaw.cui.fragment.CuiNavBarFragment;


/**
 * 个人主页
 */
public class PersonCenterActivity extends AppCompatActivity {

    private static final String USERID = "userId";
    private static final String USERNAME = "userName";
    private static final String FACE = "face";


    public static void openActivity(Context context, String userId, String userName, String face) {
        Intent intent = new Intent(context, PersonCenterActivity.class);
        intent.putExtra(USERID, userId);
        intent.putExtra(USERNAME, userName);
        intent.putExtra(FACE, face);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_center);
        Intent intent = getIntent();
        String userId = intent.getStringExtra(USERID);
        String userName = intent.getStringExtra(USERNAME);
        String face = intent.getStringExtra(FACE);


        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.header_bar, new CuiNavBarFragment());
        ft.commit();

        findViewById(R.id.privateMessageBtn).setOnClickListener((View v) -> {
            MessageActivity.openActivity(PersonCenterActivity.this, userId, userName, face);
        });


        findViewById(R.id.delMyFriendsBtn).setOnClickListener((View v) -> {
            FriendsDao.delFriendsBtn(userId, () -> {
                finish();
            });
        });
    }


}