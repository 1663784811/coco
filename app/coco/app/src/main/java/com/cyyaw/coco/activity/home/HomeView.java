package com.cyyaw.coco.activity.home;

import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cyyaw.coco.R;
import com.cyyaw.coco.activity.home.adapter.ContentListAdapter;
import com.cyyaw.coco.broadcast.BlueToothReceiver;
import com.cyyaw.coco.common.BaseAppCompatActivity;
import com.cyyaw.coco.entity.ContentEntity;

import java.util.ArrayList;
import java.util.List;

public class HomeView extends LinearLayout {

    private static final String TAG = HomeView.class.getName();

    private BaseAppCompatActivity context;

    private ContentListAdapter contentListAdapter;

    BlueToothReceiver br;


    public HomeView(BaseAppCompatActivity context) {
        super(context);
        this.context = context;
        initView(null);
    }

    public HomeView(BaseAppCompatActivity context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView(null);
    }

    public HomeView(BaseAppCompatActivity context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView(null);
    }


    private void initView(AttributeSet attrs) {
        // =============== 加载布局
        View viewHome = LayoutInflater.from(context).inflate(R.layout.activity_main_home, this, true);
        // =============== 设置适配器
        RecyclerView recyclerView = viewHome.findViewById(R.id.contentList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        contentListAdapter = new ContentListAdapter(context);
        recyclerView.setAdapter(contentListAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // 判断是否滚动到顶部
                if (!recyclerView.canScrollVertically(-1)) {
                    Log.d(TAG, "Scrolled to top");
                }

                // 判断是否滚动到底部
                if (!recyclerView.canScrollVertically(1)) {
                    Log.d(TAG, "Scrolled to bottom");
                }
            }
        });


        List<ContentEntity> dataList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            ContentEntity contentEntity = new ContentEntity();
            contentEntity.setName("sss");
            dataList.add(contentEntity);
        }
        contentListAdapter.setDataList(dataList);

//        Button gitMeACallBtn = viewHome.findViewById(R.id.gitMeACallBtn);
//        gitMeACallBtn.setOnClickListener((View v) -> {
//            // 打开面
//            String[] per = new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA};
////            Permissions.request(context, per, integer -> {
////                Log.d(TAG, "Permissions.request integer = " + integer);
////                if (integer != 0) {
////                    ActivityUtils.startActivity(context, VideoActivity.class, null);
////                }
////            });
//        });
    }


}
