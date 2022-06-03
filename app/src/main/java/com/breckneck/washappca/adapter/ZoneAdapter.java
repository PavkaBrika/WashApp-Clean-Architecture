package com.breckneck.washappca.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.breckneck.washapp.domain.model.ZoneApp;
import com.breckneck.washappca.R;


import java.util.List;

public class ZoneAdapter extends RecyclerView.Adapter<ZoneAdapter.zoneViewHolder> {

    private final LayoutInflater inflater;
    private final List<ZoneApp> zoneAppList;



    public interface OnZoneClickListener {

        void onZoneClick(ZoneApp zoneApp, int position);
    }

    private final OnZoneClickListener onZoneClickListener;

    public ZoneAdapter(Context context, List<ZoneApp> zoneAppList, OnZoneClickListener onZoneClickListener) {
        this.zoneAppList = zoneAppList;
        this.inflater = LayoutInflater.from(context);
        this.onZoneClickListener = onZoneClickListener;
    }

    @NonNull
    @Override
    public ZoneAdapter.zoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.zone_list, parent, false);
        return new zoneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ZoneAdapter.zoneViewHolder holder, int position) {
        ZoneApp zoneApp = zoneAppList.get(position);
        holder.nameView.setText(zoneApp.getZoneName());
        holder.picView.setImageResource(zoneApp.getPicture());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onZoneClickListener.onZoneClick(zoneApp, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return zoneAppList.size();
    }

    public class zoneViewHolder extends RecyclerView.ViewHolder {
        final TextView nameView;
        final ImageView picView;
        public zoneViewHolder(View view) {
            super(view);
            nameView = view.findViewById(R.id.zonename);
            picView = view.findViewById(R.id.zonepic);
        }
    }
}
