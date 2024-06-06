package com.cyyaw.coco.activity.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.cyyaw.coco.R;
import com.cyyaw.coco.activity.PrintPreviewActivity;
import com.cyyaw.coco.common.BroadcastData;
import com.cyyaw.coco.common.BroadcastEnum;
import com.cyyaw.coco.entity.BluetoothEntity;

import java.util.ArrayList;
import java.util.List;

public class HomeBluetoothListAdapter extends RecyclerView.Adapter<HomeBluetoothListAdapter.ViewHolder> {
    private List<BluetoothEntity> dataList = new ArrayList<>();

    private Context context;

    public HomeBluetoothListAdapter(Context context) {
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
    public void updateData(BluetoothEntity bluetooth) {
        String address = bluetooth.getAddress();
        boolean h = false;
        for (int i = 0; i < dataList.size(); i++) {
            String ad = dataList.get(i).getAddress();
            if (ad.equals(address)) {
                h = true;
                dataList.set(i, bluetooth);
                notifyDataSetChanged();
                break;
            }
        }
        if (!h) {
            dataList.add(bluetooth);
            notifyItemRangeInserted(dataList.size() - 1, 1);
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
        }

        public void setData(BluetoothEntity bluetoothEntity) {
            Button btn = view.findViewById(R.id.goPrintButton);
            TextView blueToothName = view.findViewById(R.id.blueToothName);
            blueToothName.setText(bluetoothEntity.getName());
            TextView blueToothAddress = view.findViewById(R.id.blueToothAddress);
            blueToothAddress.setText(bluetoothEntity.getAddress());
            TextView blueToothRssi = view.findViewById(R.id.blueToothRssi);
            blueToothRssi.setText(bluetoothEntity.getRssi()+"");

            btn.setOnClickListener((View v) -> {
                Intent intent = new Intent(context, PrintPreviewActivity.class);
                intent.putExtra("data", BroadcastData.getInstance(BroadcastEnum.BLUETOOTH_OBJ, bluetoothEntity));
                context.startActivity(intent);
            });
        }
    }


}
