package com.example.shopling.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.shopling.R;
import com.example.shopling.model.CommodityTwo;
import java.util.List;


/**
 * Created by hasee on 2019/4/5.
 */

public class RecyclerAdapter1 extends RecyclerView.Adapter <RecyclerAdapter1.ViewHolder>{
    private Context context;
    private List<CommodityTwo> listList;
    public RecyclerAdapter1(Context context,List<CommodityTwo> listList){
        this.context=context;
        this.listList=listList;
    }
    @NonNull
    @Override
    public RecyclerAdapter1.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.fragment_home_item_1,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.test001)
                .error(R.drawable.error);
        holder.tvIntroduce.setText(listList.get(position).getIntro());
        holder.tvMoney.setText(listList.get(position).getPrice()+"¥");
        Glide.with(context).load(listList.get(position).getPicture().get(0).getPath()).apply(options).into(holder.ivShow);
        holder.rlNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent("android.intent.action.shopLing.Commodity");
                intent.putExtra("number",listList.get(position).getNumber());
                Log.d("test1",listList.get(position).getNumber()+"fail");
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return listList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvMoney,tvIntroduce;
        private ImageView ivShow;
        private RelativeLayout rlNext;
        public ViewHolder(View itemView) {
            super(itemView);
            tvMoney=itemView.findViewById(R.id.tvMoney);
            tvIntroduce=itemView.findViewById(R.id.tvIntroduce);
            ivShow=itemView.findViewById(R.id.ivShow);
            rlNext=itemView.findViewById(R.id.rlNext);
        }
    }
}
