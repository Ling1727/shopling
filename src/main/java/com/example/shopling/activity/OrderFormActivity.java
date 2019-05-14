package com.example.shopling.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.shopling.R;
import com.example.shopling.adapter.OrderFormRVAdapter;
import com.example.shopling.model.Commodity;
import com.example.shopling.model.Location;
import com.example.shopling.model.Picture;
import com.example.shopling.model.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.litepal.LitePal;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by hasee on 2019/4/26.
 */

public class OrderFormActivity extends BaseActivity implements View.OnClickListener{
    private TextView tvName,tvCellPhoneNumber,tvIntro,tvPay,tvMoney0,tvMoney,tvHint;
    private ImageView ivBack;
    private RelativeLayout rlOne,rlTwo,rlFour;
    private Button btPay,btRefresh;
    private RecyclerView rvOne;
    private ProgressBar pbLoading;
    private List<Map<String,String>> mapList;
    private List<Commodity> commodityList;
    private List<Location> locationList;
    private String send;
    private OrderFormRVAdapter orderFormRVAdapter;
    private ScrollView svOrderForm;
    private String money;
    @SuppressLint("HandlerLeak")
    private Handler mHandler=new Handler();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_form);
        initView();
        initData();
        initControl();
    }

    private void initView() {
        ivBack=findViewById(R.id.ivBack);
        rlOne=findViewById(R.id.rlOne);
        rlTwo=findViewById(R.id.rlTwo);
        btPay=findViewById(R.id.btPay);
        tvName=findViewById(R.id.tvName);
        tvCellPhoneNumber=findViewById(R.id.tvCellPhoneNumber);
        tvIntro=findViewById(R.id.tvIntro);
        tvPay=findViewById(R.id.tvPay);
        tvMoney0=findViewById(R.id.tvMoney0);
        tvMoney=findViewById(R.id.tvMoney);
        tvHint=findViewById(R.id.tvHint);
        rvOne=findViewById(R.id.rvOne);
        pbLoading=findViewById(R.id.pbLoading);
        btRefresh=findViewById(R.id.btRefresh);
        svOrderForm=findViewById(R.id.svOrderForm);
        rlFour=findViewById(R.id.rlFour);
    }
    private void initData() {
        mapList=new ArrayList<>();
        commodityList=new ArrayList<>();
        locationList=new ArrayList<>();
        Intent intent=getIntent();
        money=intent.getStringExtra("money");
        int a=intent.getIntExtra("geshu",0);
        for(int i=0;i<a;i++){
            Map<String,String> map=new HashMap<>();
            map.put("commodity_number",intent.getStringExtra("commodity_number"+i));
            map.put("number",intent.getStringExtra("number"+i));
            mapList.add(map);
        }
        JSONArray jsonArray=JSONArray.fromObject(mapList);
        send=jsonArray.toString();
        Log.d("test3",send);
    }

    private void initControl() {
        tvMoney.setText(money);
        tvMoney0.setText(money);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(OrderFormActivity.this);
        rvOne.setLayoutManager(linearLayoutManager);
        orderFormRVAdapter=new OrderFormRVAdapter(OrderFormActivity.this,commodityList);
        rvOne.setAdapter(orderFormRVAdapter);


        ivBack.setOnClickListener(this);
        rlOne.setOnClickListener(this);
        rlTwo.setOnClickListener(this);
        btPay.setOnClickListener(this);
        btRefresh.setOnClickListener(this);
        new MyAsyncTask().execute(send);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivBack:
                finish();
                break;
            case R.id.rlOne:
                break;
            case R.id.rlTwo:
                break;
            case R.id.btPay:
                Intent intent=new Intent("android.intent.action.shopLing.Pay");
                intent.putExtra("money",money);
                startActivityForResult(intent,001);
                break;
            case R.id.btRefresh:
                new MyAsyncTask().execute(send);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case 002:
                pay002("102");
                break;
            case 003:
                pay002("103");
                break;
            case 004:

                break;
        }
    }

    public void pay002(final String string){
        User user=LitePal.findFirst(User.class);
        String path="http://111.230.32.36:8084/ShopLing/UserServlet";
        //1:创建OKHttpClient对象
        //设置超时时间
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
        FormBody formBody = new FormBody.Builder()
                .add("type", "pay1")
                .add("json",send)
                .add("user_id", user.getNameId())
                .add("state",string)
                .build();
        Request request = new Request.Builder()
                .post(formBody)
                .url(path)
                .build();
        //调用okHttpClient对象实现CallBack方法
        Call call = okHttpClient.newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                mHandler.sendEmptyMessage(2);
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent=new Intent("android.intent.action.shopLing.MyOrderForm");
                        intent.putExtra("page",string.equals("103")?2:0);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
    }

    class MyAsyncTask extends AsyncTask<String,Void,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pbLoading.setVisibility(View.VISIBLE);
            svOrderForm.setVisibility(View.INVISIBLE);
            rlFour.setVisibility(View.INVISIBLE);
        }

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
                .add("type", "pay")
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
            pbLoading.setVisibility(View.INVISIBLE);
        if(s.equals("")){
            btRefresh.setVisibility(View.VISIBLE);
            Toast.makeText(OrderFormActivity.this, "网络连接出错", Toast.LENGTH_SHORT).show();
        }else{
            rlFour.setVisibility(View.VISIBLE);
            btRefresh.setVisibility(View.INVISIBLE);
            svOrderForm.setVisibility(View.VISIBLE);
            //Log.d("test3",s);
            JSONArray jsonArray=JSONArray.fromObject(s);
            for(int i=0;i<jsonArray.size();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                JSONArray ja=jsonObject.getJSONArray("commodity");
                for(int j=0;j<ja.size();j++){
                    JSONObject obj=ja.getJSONObject(j);
                    Commodity commodity=new Commodity();
                    commodity.setPrice(obj.getDouble("price"));
                    commodity.setIntro(obj.getString("intro"));
                    commodity.setStore(obj.getString("store"));
                    commodity.setNumber(Integer.parseInt(mapList.get(i).get("number")));
                    JSONArray pic=obj.getJSONArray("picture");
                    Picture picture=new Picture();
                    picture.setPath(pic.getJSONObject(0).getString("path"));
                    picture.setCommodity_number(Integer.parseInt(pic.getJSONObject(0).getString("commodity_number")));
                    commodity.setPicture(picture);
                    commodityList.add(commodity);
                }

                if(i==0){
                    JSONArray obj2=jsonObject.getJSONArray("location");
                    JSONObject object=obj2.getJSONObject(0);
                    Location location=new Location();
                    location.setName(object.getString("name"));
                    location.setCell_phone_number(object.getString("cell_phone_number"));
                    location.setArea(object.getString("area"));
                    location.setAddress(object.getString("address"));
                    location.setIsDefault(object.getInt("isDefault"));
                    location.setUser_id(object.getString("user_id"));
                    locationList.add(location);
                }
            }
            if(locationList.isEmpty()){
                rlOne.setVisibility(View.INVISIBLE);
            }else{
                rlOne.setVisibility(View.VISIBLE);
                tvName.setText(locationList.get(0).getName());
                tvCellPhoneNumber.setText(locationList.get(0).getCell_phone_number());
                tvIntro.setText(locationList.get(0).getArea()+locationList.get(0).getAddress());
                orderFormRVAdapter.notifyDataSetChanged();
            }

        }
    }
    }

}
