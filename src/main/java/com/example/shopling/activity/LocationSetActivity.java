package com.example.shopling.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.shopling.R;
import com.example.shopling.adapter.LocationRVAdapter;
import com.example.shopling.model.Location;
import com.example.shopling.model.User;
import com.example.shopling.view.SelectView;

import org.litepal.LitePal;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by hasee on 2019/4/24.
 */

public class LocationSetActivity extends BaseActivity implements View.OnClickListener{
    private ImageView ivLocationSetBack;
    private EditText ed0,ed2,ed3,ed1;
    private Button btSave;
    private SelectView selectView;
    private int isDefault=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_set);
        initView();
        initData();
        initControl();
    }

    private void initView() {
        ivLocationSetBack=findViewById(R.id.ivLocationSetBack);
        ed0=findViewById(R.id.ed0);
        ed2=findViewById(R.id.ed2);
        ed3=findViewById(R.id.ed3);
        ed1=findViewById(R.id.ed1);
        btSave=findViewById(R.id.btSave);
        selectView=findViewById(R.id.selectView);
    }

    private void initData() {
    }

    private void initControl() {
        selectView.setOnClickListener(new SelectView.OnClickListener() {
            @Override
            public void onClick(boolean isSelect) {
                isDefault=isSelect?1:0;
            }
        });
        ivLocationSetBack.setOnClickListener(this);
        btSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivLocationSetBack:
                finish();
                break;
            case R.id.btSave:
                if(ed0.getText().toString().equals("")){
                    Toast.makeText(this, "请填写收货名", Toast.LENGTH_SHORT).show();
                }else if(ed1.getText().toString().equals("")){
                    Toast.makeText(this, "请填写手机号码", Toast.LENGTH_SHORT).show();
                }else if(ed2.getText().toString().equals("")){
                    Toast.makeText(this, "请填写所在地区", Toast.LENGTH_SHORT).show();
                }else if(ed3.getText().toString().equals("")){
                    Toast.makeText(this, "请填写详细地址", Toast.LENGTH_SHORT).show();
                }else{
                    new MyAsyncTask().execute(ed0.getText().toString(),ed1.getText().toString(),ed2.getText().toString(),ed3.getText().toString());
                }
                break;
        }
    }


    class MyAsyncTask extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... strings) {
            String result="";
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
                    .add("type", "locationSet")
                    .add("name", ed0.getText().toString())
                    .add("cell_phone_number", ed1.getText().toString())
                    .add("area", ed2.getText().toString())
                    .add("address",ed3.getText().toString())
                    .add("isDefault", isDefault+"")
                    .add("user_id", user.getNameId())
                    .build();
            Request request = new Request.Builder()
                    .post(formBody)
                    .url(path)
                    .build();
            //调用okHttpClient对象实现CallBack方法
            Call call = okHttpClient.newCall(request);
            try {

                Response response=call.execute();
                result=response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.equals("succeed")){
                Toast.makeText(LocationSetActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
                setResult(2);
                finish();
            }else if(s.equals("false")){
                Toast.makeText(LocationSetActivity.this,"保存出错",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(LocationSetActivity.this,"网络连接出错",Toast.LENGTH_LONG).show();
            }
        }
    }

    /*public void post(){
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
                .add("type", "locationSet")
                .add("name", ed0.getText().toString())
                .add("cell_phone_number", ed1.getText().toString())
                .add("area", ed2.getText().toString())
                .add("address",ed3.getText().toString())
                .add("isDefault", isDefault+"")
                .add("user_id", user.getNameId())
                .build();
        Request request = new Request.Builder()
                .post(formBody)
                .url(path)
                .build();
        //调用okHttpClient对象实现CallBack方法
        Log.d("test1",".........");
        Call call = okHttpClient.newCall(request);
        Log.d("test1",".........");
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("test1","请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("test1","请求成功");
                String string = response.body().string();
                Log.d("test1",string);
            }
        });
    }*/
}
