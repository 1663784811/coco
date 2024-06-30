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

    private ListenerFriends listenerFriends;

    private Context context;

    public FriendsListAdapter(Context context, ListenerFriends listener) {
        this.context = context;
        this.listenerFriends = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_friends_item, parent, false), listenerFriends);
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
        dataList.add(friendsEntity);
        notifyItemRangeInserted(dataList.size() - 1, 1);
    }


    public void setDataList(List<FriendsEntity> dataList) {
        this.dataList = dataList;
        notifyItemRangeInserted(0, dataList.size());
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private ListenerFriends listenerFriends;

        public ViewHolder(View view, ListenerFriends listenerFriends) {
            super(view);
            this.view = view;
            this.listenerFriends = listenerFriends;
        }

        public void setData(FriendsEntity friendsEntity) {
            View friendsItem = view.findViewById(R.id.friendsItem);

            friendsItem.setOnClickListener((View v) -> {
                listenerFriends.click(v, friendsEntity);
            });
        }
    }


    public interface ListenerFriends {

        void click(View v, FriendsEntity friendsEntity);

    }

}
