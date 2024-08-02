package com.cyyaw.coco.activity.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cyyaw.bluetooth.entity.BtEntity;
import com.cyyaw.bluetooth.out.BlueToothManager;
import com.cyyaw.coco.R;
import com.cyyaw.coco.activity.print.PrintInputActivity;
import com.cyyaw.coco.dao.table.EquipmentEntity;
import com.cyyaw.coco.dao.table.UserMsgEntity;

import java.util.ArrayList;
import java.util.List;


/**
 * 设备适配器
 */
public class MsgListAdapter extends RecyclerView.Adapter<MsgListAdapter.ViewHolder> {


    private final List<UserMsgEntity> dataList = new ArrayList<>();

    private final Context context;

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
    public void updateData(UserMsgEntity userMsg) {
        String tid = userMsg.getTid();
        for (int i = 0; i < dataList.size(); i++) {
            UserMsgEntity dataObj = dataList.get(i);
            String objTid = dataObj.getTid();
            if (tid.equals(objTid)) {
                notifyDataSetChanged();
                break;
            }
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
        }

        public void setData(UserMsgEntity userMsg) {
//            View btn = view.findViewById(R.id.equipmentItem);
//            TextView blueToothName = view.findViewById(R.id.blueToothName);
//            blueToothName.setText(equipment.getName());
//            String address = equipment.getAddress();
//            TextView equipmentStatus = view.findViewById(R.id.equipmentStatus);
//            Integer status = equipment.getStatus();
//            if (status != null && status == 1) {
//                equipmentStatus.setText("在线");
//            } else {
//                equipmentStatus.setText("离线");
//            }
//            btn.setOnClickListener((View v) -> {
//                PrintInputActivity.openActivity(context, address);
//            });
        }
    }


}
