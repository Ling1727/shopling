package com.example.shopling.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.shopling.R;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by hasee on 2019/4/19.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener{
    private ImageView ivRegisterBack;
    private EditText editText,editText1;
    private Button btRegister;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        ivRegisterBack=findViewById(R.id.ivRegisterBack);
        editText=findViewById(R.id.editText);
        editText1=findViewById(R.id.editText1);
        btRegister=findViewById(R.id.btRegister);
        ivRegisterBack.setOnClickListener(this);
        btRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivRegisterBack:
                this.finish();
                break;
            case R.id.btRegister:
                if(editText.getText().toString().equals("")||editText1.getText().toString().equals("")){
                    Toast.makeText(RegisterActivity.this,"用户名或密码为空",Toast.LENGTH_LONG).show();
                }else{
                    new MyAsyncTask().execute(editText.getText().toString(),editText1.getText().toString());
                }
                break;
        }
    }

    class MyAsyncTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            String result=null;
            OkHttpClient okHttpClient=new OkHttpClient();
            final Request request=new Request.Builder().url("http://111.230.32.36:8084/ShopLing/UserServlet?type=register&user="+strings[0]+"&password="+strings[1]).get().build();
            Call call=okHttpClient.newCall(request);
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
                Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("id", editText.getText().toString());
                intent.putExtra("password", editText1.getText().toString());
                //通过intent对象返回结果，必须要调用一个setResult方法，
                //setResult(resultCode, data);第一个参数表示结果返回码，一般只要大于1就可以
                setResult(2, intent);
                finish();
            }else if(s.equals("false")){
                Toast.makeText(RegisterActivity.this,"用户名已存在",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(RegisterActivity.this,"网络连接出错",Toast.LENGTH_LONG).show();
            }
        }
    }
}
