package com.example.shopling.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.shopling.R;
import com.example.shopling.model.Location;

import java.util.List;

/**
 * Created by hasee on 2019/4/24.
 */

public class LocationRVAdapter extends RecyclerView.Adapter<LocationRVAdapter.ViewHolder> {
    private List<Location> locationList;
    private Context context;
    public LocationRVAdapter(Context context,List<Location> locationList){
        this.locationList=locationList;
        this.context=context;
    }
    @NonNull
    @Override
    public LocationRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.activity_location_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LocationRVAdapter.ViewHolder holder, int position) {
        holder.tvLocationName.setText(locationList.get(position).getName());
        holder.tvNumber.setText(locationList.get(position).getCell_phone_number());
        holder.tvLocationDefault.setVisibility(locationList.get(position).getIsDefault()==1?View.VISIBLE:View.INVISIBLE);
        holder.tvLocation.setText(locationList.get(position).getArea()+locationList.get(position).getAddress());
        holder.tvLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvLocationName,tvNumber,tvLocationDefault,tvLocation;
        private ImageView ivWrite;
        public ViewHolder(View itemView) {
            super(itemView);
            tvLocationName=itemView.findViewById(R.id.tvLocationName);
            tvNumber=itemView.findViewById(R.id.tvNumber);
            tvLocationDefault=itemView.findViewById(R.id.tvLocationDefault);
            tvLocation=itemView.findViewById(R.id.tvLocation);
            ivWrite=itemView.findViewById(R.id.ivWrite);
        }
    }
}
