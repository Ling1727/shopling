package com.example.shopling.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.shopling.R;
import com.example.shopling.model.Commodity;

import java.util.List;

/**
 * Created by hasee on 2019/4/27.
 */

public class OrderFormRVAdapter extends RecyclerView.Adapter<OrderFormRVAdapter.ViewHolder>{
    private Context context;
    private List<Commodity> commodityList;

    public OrderFormRVAdapter(Context context,List<Commodity> commodityList){
        this.context = context;
        this.commodityList=commodityList;
    }

    @NonNull
    @Override
    public OrderFormRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_order_form_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderFormRVAdapter.ViewHolder holder, int position) {
        RequestOptions options=new RequestOptions()
                .placeholder(R.drawable.test001)
                .error(R.drawable.error);
        holder.tvStoreName.setText(commodityList.get(position).getStore());
        holder.tvIntroItem.setText(commodityList.get(position).getIntro());
        Glide.with(context).load(commodityList.get(position).getPicture().getPath()).apply(options).into(holder.ivShopItem);
        Log.d("test3",commodityList.get(position).getPrice()+"");
        Log.d("test3",commodityList.get(position).getNumber()+"");
        holder.tvMoneyItem.setText("¥"+commodityList.get(position).getPrice()*commodityList.get(position).getNumber());
        holder.tvNumberItem.setText("x"+commodityList.get(position).getNumber());
        holder.rlDistribution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return commodityList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvStoreName,tvIntroItem,tvMoneyItem,tvNumberItem;
        private ImageView ivShopItem;
        private RelativeLayout rlDistribution;
        private EditText edLeaveWord;
        public ViewHolder(View itemView) {
            super(itemView);
            tvStoreName=itemView.findViewById(R.id.tvStoreName);
            tvIntroItem=itemView.findViewById(R.id.tvIntroItem);
            ivShopItem=itemView.findViewById(R.id.ivShopItem);
            tvMoneyItem=itemView.findViewById(R.id.tvMoneyItem);
            tvNumberItem=itemView.findViewById(R.id.tvNumberItem);
            rlDistribution=itemView.findViewById(R.id.rlDistribution);
            edLeaveWord=itemView.findViewById(R.id.edLeaveWord);
        }
    }
}
