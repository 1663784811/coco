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
import com.cyyaw.coco.activity.AddEquipmentActivity;
import com.cyyaw.coco.activity.PrintPreviewActivity;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private List<String> dataList;

    private Context context;

    public RecyclerViewAdapter(Context context,List<String> dataList) {
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.equipment_item, parent, false);
        Button btn = view.findViewById(R.id.goPrintButton);
        btn.setOnClickListener(this::goPrintButton);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    private void goPrintButton(View v) {
        Intent intent = new Intent(context, PrintPreviewActivity.class);
        context.startActivity(intent);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_text);
        }
    }





}
