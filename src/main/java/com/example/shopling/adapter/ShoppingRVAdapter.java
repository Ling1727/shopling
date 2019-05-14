package com.example.shopling.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.shopling.R;
import com.example.shopling.model.ShoppingTrolley;
import com.example.shopling.model.User;
import net.sf.json.JSONArray;
import org.litepal.LitePal;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by hasee on 2019/4/25.
 */

public class ShoppingRVAdapter extends RecyclerView.Adapter <ShoppingRVAdapter.ViewHolder>{
    private Context context;
    private List<ShoppingTrolley> shoppingTrolleyList;
    private double sum=0.00;
    private double[] sums;
    private OnCheckChangeListener onCheckChangeListener;
    private OnRefreshLienster onRefreshLienster;
    private List<ViewHolder> viewHolderList;
    public ShoppingRVAdapter(Context context,List<ShoppingTrolley> shoppingTrolleyList){
        this.context=context;
        this.shoppingTrolleyList=shoppingTrolleyList;
        viewHolderList=new ArrayList<>();
    }
    public void mNotifyDataSetChanged(int size){
        sums=new double[size];
        for(int i=0;i<size;i++){
            sums[i]=0.00;
        }
        viewHolderList.clear();
        this.notifyDataSetChanged();
        for(int i=0;i<viewHolderList.size();i++){
            viewHolderList.get(i).cbShop2.setChecked(false);
        }
    }
    @NonNull
    @Override
    public ShoppingRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.fragment_shopping_item,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public int getItemCount() {
        return shoppingTrolleyList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull final ShoppingRVAdapter.ViewHolder holder, final int position) {
        if(!viewHolderList.contains(holder)){
            viewHolderList.add(holder);
        }
        RequestOptions options=new RequestOptions()
                .placeholder(R.drawable.test001)
                .error(R.drawable.error);
        Glide.with(context).load(shoppingTrolleyList.get(position).getCommodity().getPicture().getPath()).apply(options).into(holder.ivShoppingCar);
        holder.tvShop0.setText(shoppingTrolleyList.get(position).getCommodity().getIntro());
        holder.tvMoney.setText(String.valueOf(shoppingTrolleyList.get(position).getCommodity().getPrice()));
        holder.tvMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.tvNumber.getText().toString().equals("1")){
                    Toast.makeText(context, "至少添加一件", Toast.LENGTH_SHORT).show();
                }else{
                    int a=Integer.parseInt(holder.tvNumber.getText().toString())-1;
                    holder.tvNumber.setText(String.valueOf(a));
                    viewHolderList.get(position).cbShop2.setChecked(true);
                    sums[position]=a*shoppingTrolleyList.get(position).getCommodity().getPrice();
                    boolean isAll=true;
                    for(ViewHolder viewHolder:viewHolderList){
                        if(!viewHolder.cbShop2.isChecked()){
                            isAll=false;
                            break;
                        }
                    }
                    onCheckChangeListener.OnCheckChange(getSum(),isAll);
                }
            }
        });

        holder.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a=Integer.parseInt(holder.tvNumber.getText().toString())+1;
                holder.tvNumber.setText(String.valueOf(a));
                viewHolderList.get(position).cbShop2.setChecked(true);
                sums[position]=a*shoppingTrolleyList.get(position).getCommodity().getPrice();
                boolean isAll=true;
                for(ViewHolder viewHolder:viewHolderList){
                    if(!viewHolder.cbShop2.isChecked()){
                        isAll=false;
                        break;
                    }
                }
                onCheckChangeListener.OnCheckChange(getSum(),isAll);
            }
        });
        holder.cbShop2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int a=Integer.parseInt(holder.tvNumber.getText().toString());
                sums[position]=isChecked?a*shoppingTrolleyList.get(position).getCommodity().getPrice():0.00;
                boolean isAll=true;
                for(ViewHolder viewHolder:viewHolderList){
                    if(!viewHolder.cbShop2.isChecked()){
                        isAll=false;
                        break;
                    }
                }
                onCheckChangeListener.OnCheckChange(getSum(),isAll);
            }
        });
    }

    public double getSum(){
        sum=0.00;
        for(int i=0;i<sums.length;i++){
            sum+=sums[i];
        }
        return sum;
    }

    public void setCheck(boolean isCheck){
        Log.d("test2","...."+viewHolderList.size());
        if(isCheck){
            for(ViewHolder viewHolder:viewHolderList){
                viewHolder.cbShop2.setChecked(true);
            }
        }else{
            boolean isAll=true;
            for(ViewHolder viewHolder:viewHolderList){
                if(!viewHolder.cbShop2.isChecked()){
                    isAll=false;
                    break;
                }
            }
            if (isAll){
                for(ViewHolder viewHolder:viewHolderList){
                    viewHolder.cbShop2.setChecked(false);
                }
            }
        }
    }

    public void delete(){
        List<String> numberList=new ArrayList<>();;
        for(int i=0;i<viewHolderList.size();i++){
            if(viewHolderList.get(i).cbShop2.isChecked()){
                numberList.add(shoppingTrolleyList.get(i).getCommodity_number()+"");
            }
        }
        if(numberList.size()!=0){
            JSONArray jsonArray=JSONArray.fromObject(numberList);
            String json=jsonArray.toString();
            new MyAsyncTask().execute(json);
        }else {
            Toast.makeText(context, "你还没有选中商品", Toast.LENGTH_SHORT).show();
        }
    }

    public interface OnCheckChangeListener{
        public void OnCheckChange(double sum,boolean isAll);
    }

    public void setOnCheckChangeListener(OnCheckChangeListener onCheckChangeListener){
        this.onCheckChangeListener=onCheckChangeListener;
    }

    public interface OnRefreshLienster{
        public void onRefresh(List<ViewHolder> viewHolderList);
    }

    public void setOnRefreshLienster(OnRefreshLienster onRefreshLienster){
        this.onRefreshLienster=onRefreshLienster;
    }

    public List<ViewHolder> getViewHolderList() {
        return viewHolderList;
    }

    public void setViewHolderList(List<ViewHolder> viewHolderList) {
        this.viewHolderList = viewHolderList;
    }

     public static class ViewHolder extends RecyclerView.ViewHolder{
         public TextView tvShop0,tvMoney,tvMinus,tvNumber,tvAdd;
         public ImageView ivShoppingCar;
         public CheckBox cbShop2;
        private ViewHolder(View itemView) {
            super(itemView);
            tvShop0=itemView.findViewById(R.id.tvShop0);
            ivShoppingCar=itemView.findViewById(R.id.ivShoppingCar);
            tvMoney=itemView.findViewById(R.id.tvMoney);
            tvMinus=itemView.findViewById(R.id.tvMinus);
            tvNumber=itemView.findViewById(R.id.tvNumber);
            tvAdd=itemView.findViewById(R.id.tvAdd);
            cbShop2=itemView.findViewById(R.id.cbShop2);
        }
    }

    class MyAsyncTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            String result="";
            User user= LitePal.findFirst(User.class);
            String path="http://111.230.32.36:8084/ShopLing/UserServlet";
            //1:创建OKHttpClient对象
            //设置超时时间
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .build();
            FormBody formBody = new FormBody.Builder()
                    .add("type", "shoppingTrolleyDelete")
                    .add("json", strings[0])
                    .add("user_id", user.getNameId())
                    .build();
            Request request = new Request.Builder()
                    .post(formBody)
                    .url(path)
                    .build();
            //调用okHttpClient对象实现CallBack方法
            Call call = okHttpClient.newCall(request);
            try {
                result=call.execute().body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            switch (s){
                case "ok":
                    Toast.makeText(context, "已删除选中商品", Toast.LENGTH_SHORT).show();
                    onRefreshLienster.onRefresh(viewHolderList);
                    break;
                case "flase":
                    Toast.makeText(context, "已删除部分商品", Toast.LENGTH_SHORT).show();
                    break;
                case "":
                    Toast.makeText(context, "网络连接出错", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
