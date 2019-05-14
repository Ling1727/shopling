package com.example.shopling.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.shopling.R;
import com.example.shopling.adapter.MyOrderFromRVAdapter;
import com.example.shopling.model.Commodity;
import com.example.shopling.model.Location;
import com.example.shopling.model.Picture;
import com.example.shopling.model.ShoppingTrolley;
import com.example.shopling.model.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.litepal.LitePal;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by hasee on 2019/5/4.
 */

public class MyOdrderFormFragment extends Fragment {
    private View view;
    private RecyclerView rvOrder;
    private List<ShoppingTrolley> shoppingTrolleyList;
    private MyOrderFromRVAdapter myOrderFromRVAdapter;
    private TextView tvHint;

    @SuppressLint("HandlerLeak")
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    break;
                case 1:
                    if(shoppingTrolleyList.isEmpty()){
                        tvHint.setVisibility(View.VISIBLE);
                    }else{
                        tvHint.setVisibility(View.INVISIBLE);
                    }
                    myOrderFromRVAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_my_order_from,container,false);
        initView();
        initData();
        initControl();
        return view;
    }

    private void initView() {
        rvOrder=view.findViewById(R.id.rvOrder);
        tvHint=view.findViewById(R.id.tvHint);
    }
    private void initData() {
        shoppingTrolleyList=new ArrayList<>();
        myOrderFromRVAdapter=new MyOrderFromRVAdapter(getActivity(),shoppingTrolleyList);
    }
    private void initControl() {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        rvOrder.setLayoutManager(linearLayoutManager);
        rvOrder.setAdapter(myOrderFromRVAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        loadData();
    }

    public void loadData(){
        shoppingTrolleyList.clear();
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
                .add("type", "shoppingTrolleyGet")
                .add("state", getArguments().getString("state"))
                .add("user_id", user.getNameId())
                .build();
        Request request = new Request.Builder()
                .post(formBody)
                .url(path)
                .build();
        //调用okHttpClient对象实现CallBack方法
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String string=response.body().string();
                    if(!string.equals("")){
                        JSONArray index = JSONArray.fromObject(string);
                        for(int i=0;i<index.size();i++) {
                            JSONObject obj = (JSONObject) index.opt(i);
                            ShoppingTrolley shoppingTrolley=new ShoppingTrolley();
                            shoppingTrolley.setUser_id(obj.getString("user_id"));
                            shoppingTrolley.setCommodity_number(obj.getInt("commodity_number"));
                            shoppingTrolley.setNumber(obj.getInt("number"));
                            shoppingTrolley.setState(obj.getString("state"));

                            Commodity commodity=new Commodity();
                            JSONObject jsonObject=obj.getJSONObject("commodity");
                            commodity.setNumber(jsonObject.getInt("number"));
                            commodity.setIntro(jsonObject.getString("intro"));
                            commodity.setStore(jsonObject.getString("store"));
                            commodity.setPrice(jsonObject.getDouble("price"));
                            JSONArray p =jsonObject.getJSONArray("picture");
                            JSONObject p2= (JSONObject) p.opt(0);
                            Picture picture=new Picture();
                            picture.setPath(p2.getString("path"));
                            picture.setCommodity_number(p2.getInt("commodity_number"));
                            commodity.setPicture(picture);
                            shoppingTrolley.setCommodity(commodity);

                            shoppingTrolleyList.add(shoppingTrolley);
                        }
                    }
                    mHandler.sendEmptyMessage(1);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
