package com.cyyaw.coco.activity.home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cyyaw.coco.R;
import com.cyyaw.coco.activity.AddContentActivity;
import com.cyyaw.coco.activity.home.adapter.ContentListAdapter;
import com.cyyaw.coco.dao.ContentDao;
import com.cyyaw.coco.entity.ContentEntity;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getName();

    private Context context;

    private ContentListAdapter contentListAdapter;


    public HomeFragment(Context context) {
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main_home, container, false);
        View addContentBtn = view.findViewById(R.id.addContentBtn);
        addContentBtn.setOnClickListener((View v) -> {
            AddContentActivity.openActivity(context);
        });
        // =============== 设置适配器
        RecyclerView recyclerView = view.findViewById(R.id.contentList);
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
            contentEntity.setUserName("sss");
            dataList.add(contentEntity);
        }
        contentListAdapter.setDataList(dataList);
        ContentDao.loadContent(contentListAdapter, 1);
        return view;
    }


}
