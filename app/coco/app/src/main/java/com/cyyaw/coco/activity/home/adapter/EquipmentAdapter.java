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
import com.cyyaw.coco.activity.print.PrintPreviewActivity;
import com.cyyaw.coco.dao.table.EquipmentEntity;
import com.cyyaw.coco.entity.BluetoothEntity;

import java.util.ArrayList;
import java.util.List;


/**
 * 设备适配器
 */
public class EquipmentAdapter extends RecyclerView.Adapter<EquipmentAdapter.ViewHolder> {


    private final List<EquipmentEntity> dataList = new ArrayList<>();

    private final Context context;

    public EquipmentAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.equipment_item, parent, false));
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
    public void updateData(BtEntity bt) {
        String address = bt.getDev().getAddress();
        for (int i = 0; i < dataList.size(); i++) {
            EquipmentEntity equipment = dataList.get(i);
            String ad = equipment.getAddress();
            if (ad.equals(address)) {
                equipment.setStatus(1);
                dataList.set(i, equipment);
                notifyDataSetChanged();
                break;
            }
        }
    }

    /**
     * 设置数据
     */
    public void setData(List<EquipmentEntity> equipmentList) {
        dataList.clear();
        if (null != equipmentList) {
            for (int i = 0; i < equipmentList.size(); i++) {
                EquipmentEntity equipmentEntity = equipmentList.get(i);
                String address = equipmentEntity.getAddress();
                BtEntity bt = BlueToothManager.getBluetooth(address);
                if (null != bt) {
                    equipmentEntity.setStatus(1);
                } else {
                    equipmentEntity.setStatus(0);
                }
                dataList.add(equipmentEntity);
            }
            notifyItemRangeInserted(0, dataList.size());
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
        }

        public void setData(EquipmentEntity equipment) {
            View btn = view.findViewById(R.id.equipmentItem);
            TextView blueToothName = view.findViewById(R.id.blueToothName);
            blueToothName.setText(equipment.getName());
            String address = equipment.getAddress();
            TextView equipmentStatus = view.findViewById(R.id.equipmentStatus);
            Integer status = equipment.getStatus();
            if (status != null && status == 1) {
                equipmentStatus.setText("在线");
            } else {
                equipmentStatus.setText("离线");
            }
            btn.setOnClickListener((View v) -> {
                PrintInputActivity.openActivity(context, address);
            });
        }
    }


}
