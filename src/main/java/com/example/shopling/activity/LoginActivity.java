package com.example.shopling.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.shopling.R;
import com.example.shopling.model.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.litepal.LitePal;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by hasee on 2019/4/15.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener{
    private ImageView ivX,ivWeChatLogin,ivQQLogin;
    private TextView tvService,tvVerifyLogin,tvRegister;
    private Button btLogin;
    private EditText edName,edPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initData();
        initControl();
    }

    public void initView(){
        ivX=findViewById(R.id.ivX);
        tvService=findViewById(R.id.tvService);
        tvVerifyLogin=findViewById(R.id.tvVerifyLogin);
        tvRegister=findViewById(R.id.tvRegister);
        btLogin=findViewById(R.id.btLogin);
        ivWeChatLogin=findViewById(R.id.ivWeChatLogin);
        ivQQLogin=findViewById(R.id.ivQQLogin);
        edName=findViewById(R.id.edName);
        edPassword=findViewById(R.id.edPassword);
    }

    public void initData(){

    }

    public void initControl(){
        ivX.setOnClickListener(this);
        tvService.setOnClickListener(this);
        tvVerifyLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
        btLogin.setOnClickListener(this);
        ivWeChatLogin.setOnClickListener(this);
        ivQQLogin.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivX:
                this.finish();
                break;
            case R.id.tvService:
                break;
            case R.id.tvVerifyLogin:
                break;
            case R.id.btLogin:
                if(edName.getText().toString().equals("")||edPassword.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this,"用户名或密码为空",Toast.LENGTH_LONG).show();
                }else{
                    new MyAsyncTask().execute(edName.getText().toString(),edPassword.getText().toString());
                }
                break;
            case R.id.tvRegister:
                startActivityForResult(new Intent("android.intent.action.shopLing.Regisetr"),1);
                break;
            case R.id.ivWeChatLogin:
                break;
            case R.id.ivQQLogin:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==2){
            new MyAsyncTask().execute(data.getStringExtra("id"),data.getStringExtra("password"));
        }
    }

    class MyAsyncTask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            String result=null;
            OkHttpClient okHttpClient=new OkHttpClient();
            final Request request=new Request.Builder().url("http://111.230.32.36:8084/ShopLing/UserServlet?type=login&user="+strings[0]+"&password="+strings[1]).get().build();
            Call call=okHttpClient.newCall(request);
            try {
                Response response=call.execute();
                result=response.body().string();
                if(!result.equals("")){
                    JSONArray index = JSONArray.fromObject(result);
                    JSONObject data=(JSONObject) index.opt(0);
                    User user=new User();
                    user.setNameId(data.getString("id"));
                    user.setCell_phone_number(data.getInt("cell_phone_number"));
                    user.setPassword(data.getString("password"));
                    user.setName(data.getString("name"));
                    user.setVip(data.getInt("vip"));
                    user.setGender(data.getString("gender"));
                    user.setBirthday(data.getString("birthday"));
                    user.setHead_portrait(data.getString("head_portrait"));
                    user.setMoney(data.getDouble("money"));
                    user.setDate_of_foundation(data.getString("date_of_foundation"));
                    user.save();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(!s.equals("")){
                Toast.makeText(LoginActivity.this,"登陆成功",Toast.LENGTH_LONG).show();
                SharedPreferences sp=getSharedPreferences("set",0);
                SharedPreferences.Editor editor=sp.edit();
                editor.putBoolean("isLogin",true);
                editor.commit();
                finish();
            }else{
                Toast.makeText(LoginActivity.this,"账号或密码错误",Toast.LENGTH_LONG).show();
            }
        }
    }
}
