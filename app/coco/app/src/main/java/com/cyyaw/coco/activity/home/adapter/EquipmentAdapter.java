package com.cyyaw.coco.activity.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cyyaw.coco.R;
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
    public void updateData(EquipmentEntity equipment) {
        String address = equipment.getAddress();
        boolean h = false;
        for (int i = 0; i < dataList.size(); i++) {
            String ad = dataList.get(i).getAddress();
            if (ad.equals(address)) {
                h = true;
                dataList.set(i, equipment);
                notifyDataSetChanged();
                break;
            }
        }
        if (!h) {
            dataList.add(equipment);
            notifyItemRangeInserted(dataList.size() - 1, 1);
        }
    }

    /**
     * 设置数据
     */
    public void setData(List<EquipmentEntity> equipmentList) {
        dataList.clear();
        if (null != equipmentList) {
            for (int i = 0; i < equipmentList.size(); i++) {
                dataList.add(equipmentList.get(i));
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
            btn.setOnClickListener((View v) -> {
                PrintPreviewActivity.openActivity(context, address);
            });
        }
    }


}
