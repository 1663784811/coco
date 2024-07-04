package com.cyyaw.coco.activity.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.cyyaw.coco.R;
import com.cyyaw.coco.entity.ContentEntity;

import java.util.List;

public class ContentEntityArrayAdapter extends ArrayAdapter<ContentEntity> {

    private List<ContentEntity> data;


    public ContentEntityArrayAdapter(@NonNull Context context, List<ContentEntity> contentEntityList) {
        super(context, R.layout.ui_my_content, contentEntityList);
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
//        Planet planet = getItem(position);
//
//        ViewHolder viewHolder;
        View result = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.ui_my_content, parent, false);
//            viewHolder.planetName = convertView.findViewById(R.id.planet_name);
//            viewHolder.moonCount = convertView.findViewById(R.id.moon_count);
//            viewHolder.planetImage = convertView.findViewById(R.id.imageView);
            result = convertView;
//            convertView.setTag(viewHolder);
        } else {
            result = convertView;
        }
//        assert planet != null;
//        viewHolder.planetName.setText(planet.getPlanetName());
//        viewHolder.moonCount.setText(planet.getMoonCount());
//        viewHolder.planetImage.setImageResource(planet.getPlanetImage());
        return result;
    }


}
