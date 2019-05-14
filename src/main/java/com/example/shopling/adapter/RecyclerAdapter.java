package com.example.shopling.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.shopling.R;
import com.example.shopling.model.CommodityOne;
import java.util.List;
import java.util.Map;


/**
 * Created by hasee on 2019/4/5.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.VH> {
    private List<Map<String,String>> imageURL;
    private String[] titles;
    private Context context;
    private List<List<CommodityOne>> listList;

    public RecyclerAdapter(Context context,List<List<CommodityOne>> listList){
        super();
        this.context=context;
        this.listList=listList;
    }
    public RecyclerAdapter(Context context,List<Map<String,String>> imageURL,String[] titles) {
        super();
        this.context=context;
        this.imageURL=imageURL;
        this.titles=titles;
    }

    @NonNull
    @Override
    public RecyclerAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.fragment_home_item_0,parent,false);
        VH vh=new VH(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.test001)
                .error(R.drawable.error);
        //holder.tvTitle.setText(titles[position]);
        //Log.d("test0",listList.get(position).get(0).getPicture().get(0).getPath()+"/0.jpg");
        Glide.with(context).load(listList.isEmpty()?"":listList.get(position).get(0).getPicture().get(0).getPath()).apply(options).into(holder.iv00);
        Glide.with(context).load(listList.isEmpty()?"":listList.get(position).get(1).getPicture().get(0).getPath()).apply(options).into(holder.iv10);
        Glide.with(context).load(listList.isEmpty()?"":listList.get(position).get(2).getPicture().get(0).getPath()).apply(options).into(holder.iv11);
        Glide.with(context).load(listList.isEmpty()?"":listList.get(position).get(3).getPicture().get(0).getPath()).apply(options).into(holder.iv20);
        Glide.with(context).load(listList.isEmpty()?"":listList.get(position).get(4).getPicture().get(0).getPath()).apply(options).into(holder.iv21);
        Glide.with(context).load(listList.isEmpty()?"":listList.get(position).get(5).getPicture().get(0).getPath()).apply(options).into(holder.iv30);
        Glide.with(context).load(listList.isEmpty()?"":listList.get(position).get(6).getPicture().get(0).getPath()).apply(options).into(holder.iv31);
        Glide.with(context).load(listList.isEmpty()?"":listList.get(position).get(7).getPicture().get(0).getPath()).apply(options).into(holder.iv40);
        Glide.with(context).load(listList.isEmpty()?"":listList.get(position).get(8).getPicture().get(0).getPath()).apply(options).into(holder.iv50);
        Glide.with(context).load(listList.isEmpty()?"":listList.get(position).get(9).getPicture().get(0).getPath()).apply(options).into(holder.iv60);
        Glide.with(context).load(listList.isEmpty()?"":listList.get(position).get(10).getPicture().get(0).getPath()).apply(options).into(holder.iv70);
    }

    @Override
    public int getItemCount() {
        return 3;
    }



    static class VH extends RecyclerView.ViewHolder{
        private TextView tvTitle,tv00,tv10,tv20,tv21,tv30,tv31,tv40,tv41,tv50,tv51,tv60,tv61,tv70,tv71;
        private ImageView iv00,iv10,iv11,iv20,iv21,iv30,iv31,iv40,iv50,iv60,iv70;
        public VH(View itemView) {
            super(itemView);
            tvTitle=itemView.findViewById(R.id.tvTitle);
            tv00=itemView.findViewById(R.id.tv00);
            tv10=itemView.findViewById(R.id.tv10);
            tv20=itemView.findViewById(R.id.tv20);
            tv21=itemView.findViewById(R.id.tv21);
            tv30=itemView.findViewById(R.id.tv30);
            tv31=itemView.findViewById(R.id.tv31);
            tv40=itemView.findViewById(R.id.tv40);
            tv41=itemView.findViewById(R.id.tv41);
            tv50=itemView.findViewById(R.id.tv50);
            tv51=itemView.findViewById(R.id.tv51);
            tv60=itemView.findViewById(R.id.tv60);
            tv61=itemView.findViewById(R.id.tv61);
            tv70=itemView.findViewById(R.id.tv70);
            tv71=itemView.findViewById(R.id.tv71);

            iv00=itemView.findViewById(R.id.iv00);
            iv10=itemView.findViewById(R.id.iv10);
            iv11=itemView.findViewById(R.id.iv11);
            iv20=itemView.findViewById(R.id.iv20);
            iv21=itemView.findViewById(R.id.iv21);
            iv30=itemView.findViewById(R.id.iv30);
            iv31=itemView.findViewById(R.id.iv31);
            iv40=itemView.findViewById(R.id.iv40);
            iv50=itemView.findViewById(R.id.iv50);
            iv60=itemView.findViewById(R.id.iv60);
            iv70=itemView.findViewById(R.id.iv70);
        }
    }
}
