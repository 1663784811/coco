package com.cyyaw.coco.activity.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cyyaw.coco.R;
import com.cyyaw.coco.entity.FriendsEntity;

import java.util.ArrayList;
import java.util.List;

public class FriendsListAdapter extends RecyclerView.Adapter<FriendsListAdapter.ViewHolder> {
    private List<FriendsEntity> dataList = new ArrayList<>();

    private Context context;

    public FriendsListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_friends_item, parent, false));
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
    public void updateData(FriendsEntity friendsEntity) {
//        String address = friendsEntity.getAddress();
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


    public void setDataList(List<FriendsEntity> dataList) {
        this.dataList = dataList;
        notifyItemRangeInserted(0, dataList.size());
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
        }

        public void setData(FriendsEntity friendsEntity) {

        }
    }


}
