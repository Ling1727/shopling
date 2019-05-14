package com.example.shopling.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.shopling.R;

import java.util.List;
import java.util.Map;

/**
 * Created by hasee on 2019/4/15.
 */

public class LVClassifyItemAdapter extends RecyclerView.Adapter<LVClassifyItemAdapter.ViewHolder>{
    private List<Map<String,String>> data;
    private Context context;
    public LVClassifyItemAdapter(Context context,List<Map<String,String>> data) {
        this.context=context;
        this.data=data;
    }

    @NonNull
    @Override
    public LVClassifyItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.fragment_classify_item_recommend_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LVClassifyItemAdapter.ViewHolder holder, int position) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.test001)
                .error(R.drawable.error);
        Log.d("test0",data.get(position).get("ivPath"));
        Glide.with(context).load(data.get(position).get("ivPath")).apply(options).into(holder.ivClassifyItem);
        holder.tvClassifyItem.setText(data.get(position).get("text"));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView ivClassifyItem;
        public TextView tvClassifyItem;
        public ViewHolder(View itemView) {
            super(itemView);
            ivClassifyItem=itemView.findViewById(R.id.ivClassifyItem);
            tvClassifyItem=itemView.findViewById(R.id.tvClassifyItem);
        }
    }
}
