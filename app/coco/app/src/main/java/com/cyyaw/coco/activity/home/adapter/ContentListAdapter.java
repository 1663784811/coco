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

public class ContentListAdapter extends RecyclerView.Adapter<ContentListAdapter.ViewHolder> {
    private List<ContentEntity> dataList = new ArrayList<>();

    private Context context;

    public ContentListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.content_item, parent, false));
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
//        String address = content.getAddress();
//        boolean h = false;
//        for (int i = 0; i < dataList.size(); i++) {
//            String ad = dataList.get(i).getAddress();
//            if (ad.equals(address)) {
//                h = true;
//                dataList.set(i, bluetooth);
//                notifyDataSetChanged();
//                break;
//            }
//        }
//        if (!h) {
//            dataList.add(bluetooth);
//            notifyItemRangeInserted(dataList.size() - 1, 1);
//        }
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
