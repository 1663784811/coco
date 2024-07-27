package com.cyyaw.coco.activity.home.adapter;

import android.annotation.SuppressLint;
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
import com.cyyaw.coco.activity.MessageActivity;
import com.cyyaw.coco.activity.PersonCenterActivity;
import com.cyyaw.coco.dao.table.MsgEntity;
import com.cyyaw.coco.dao.table.MsgEntity;

import java.util.ArrayList;
import java.util.List;

public class MsgListAdapter extends RecyclerView.Adapter<MsgListAdapter.ViewHolder> {

    private static final String TAG = MsgListAdapter.class.getName();

    private final List<MsgEntity> dataList = new ArrayList<>();

    private Context context;

    public MsgListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_msg_item, parent, false));
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
    public void updateData(MsgEntity friendsEntity) {
        dataList.add(friendsEntity);
        notifyItemRangeInserted(dataList.size() - 1, 1);
    }


    /**
     *
     */
    @SuppressLint("NotifyDataSetChanged")
    public void setDataList(List<MsgEntity> friendsList) {
        dataList.clear();
        for (int i = 0; i < friendsList.size(); i++) {
            dataList.add(friendsList.get(i));
        }
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
        }

        public void setData(MsgEntity friendsEntity) {
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
                MessageActivity.openActivity(context, friendsEntity.getTid(), friendsEntity.getNickName(), friendsEntity.getFace());
            });
        }
    }


}
