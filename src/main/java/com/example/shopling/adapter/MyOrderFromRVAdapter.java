package com.example.shopling.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.shopling.R;
import com.example.shopling.model.ShoppingTrolley;

import java.util.List;

/**
 * Created by hasee on 2019/5/5.
 */

public class MyOrderFromRVAdapter extends RecyclerView.Adapter<MyOrderFromRVAdapter.ViewHolder>{
private Context context;
private List<ShoppingTrolley> shoppingTrolleyList;
    public MyOrderFromRVAdapter(Context context,List shoppingTrolleyList){
        this.context=context;
        this.shoppingTrolleyList=shoppingTrolleyList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_my_order_from_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        RequestOptions options=new RequestOptions()
                .placeholder(R.drawable.test001)
                .error(R.drawable.error);
        holder.tvStore.setText(shoppingTrolleyList.get(position).getCommodity().getStore());
        holder.tvJianJie.setText(shoppingTrolleyList.get(position).getCommodity().getIntro());
        Glide.with(context).load(shoppingTrolleyList.get(position).getCommodity().getPicture().getPath()).apply(options).into(holder.ivTuPian);
        if(shoppingTrolleyList.get(position).getState().equals("103")){
            holder.tvNumber0.setText("共"+shoppingTrolleyList.get(position).getNumber()+"件商品 待发货");
            holder.btPay.setVisibility(View.INVISIBLE);
            holder.tvDaiFuKuan.setText("代发货");
            holder.tvMoney.setVisibility(View.INVISIBLE);
        }else{
            holder.tvNumber0.setText("共"+shoppingTrolleyList.get(position).getNumber()+"件商品 需付款：¥");
            holder.tvMoney.setText(String.valueOf(shoppingTrolleyList.get(position).getCommodity().getPrice()*shoppingTrolleyList.get(position).getNumber()));
            holder.btPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent("android.intent.action.shopLing.Pay");
                    intent.putExtra("money","¥"+String.valueOf(shoppingTrolleyList.get(position).getCommodity().getPrice()*shoppingTrolleyList.get(position).getNumber()));
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return shoppingTrolleyList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvStore,tvJianJie,tvNumber0,tvMoney,tvDaiFuKuan;
        private ImageView ivTuPian;
        private RelativeLayout rlXiangQing;
        private Button btPay;
        public ViewHolder(View itemView) {
            super(itemView);
            tvStore=itemView.findViewById(R.id.tvStore);
            tvJianJie=itemView.findViewById(R.id.tvJianJie);
            ivTuPian=itemView.findViewById(R.id.ivTuPian);
            tvNumber0=itemView.findViewById(R.id.tvNumber0);
            tvMoney=itemView.findViewById(R.id.tvMoney);
            btPay=itemView.findViewById(R.id.btPay);
            rlXiangQing=itemView.findViewById(R.id.rlXiangQing);
            tvDaiFuKuan=itemView.findViewById(R.id.tvDaiFuKuan);
        }
    }
}
