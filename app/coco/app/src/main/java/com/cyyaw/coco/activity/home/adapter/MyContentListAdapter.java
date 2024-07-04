package com.cyyaw.coco.activity.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cyyaw.coco.R;
import com.cyyaw.coco.entity.ContentEntity;

import java.util.ArrayList;
import java.util.List;

public class MyContentListAdapter extends RecyclerView.Adapter<MyContentListAdapter.ViewHolder> {
    private List<ContentEntity> dataList = new ArrayList<>();

    private Context context;

    public MyContentListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.ui_my_content, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    /**
     * 更新数据
     */
    public void updateData(ContentEntity content) {

    }

    public void addData(ContentEntity content) {
        dataList.add(content);
        notifyItemRangeInserted(dataList.size() - 1, 1);
    }


    public void setDataList(List<ContentEntity> dataList) {
        this.dataList = dataList;
        notifyItemRangeInserted(0, dataList.size());
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
        }

        public void setData(ContentEntity content) {
//            Button btn = view.findViewById(R.id.goPrintButton);

        }
    }


}
