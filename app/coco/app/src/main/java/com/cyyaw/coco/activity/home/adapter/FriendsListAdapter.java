package com.cyyaw.coco.activity.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cyyaw.coco.R;
import com.cyyaw.coco.dao.table.FriendsEntity;

import java.util.ArrayList;
import java.util.List;

public class FriendsListAdapter extends RecyclerView.Adapter<FriendsListAdapter.ViewHolder> {
    private final List<FriendsEntity> dataList = new ArrayList<>();

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


    public void setDataList(List<FriendsEntity> friendsList) {
        dataList.clear();
        for (int i = 0; i < friendsList.size(); i++) {
            dataList.add(friendsList.get(i));
        }
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
            ImageView face = view.findViewById(R.id.face);
            Glide.with(view.getContext()).load(friendsEntity.getFace()).into(face);
            TextView userName = view.findViewById(R.id.userName);
            userName.setText(friendsEntity.getNickName());
            TextView message = view.findViewById(R.id.message);
            message.setText("...");
            /**
             * 点击
             */
            friendsItem.setOnClickListener((View v) -> {
                listenerFriends.click(v, friendsEntity);
            });
        }
    }


    public interface ListenerFriends {

        void click(View v, FriendsEntity friendsEntity);

    }

}
