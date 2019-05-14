package com.example.shopling.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.shopling.R;
import com.example.shopling.model.User;

import org.litepal.LitePal;

/**
 * Created by hasee on 2019/4/24.
 */

public class SetActivity extends BaseActivity implements View.OnClickListener{
    private RelativeLayout rlSet0,rlSet1,rlSet2,rlSet3,rlSet4;
    private ImageView ivSetBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        initView();
        initData();
        initControl();
    }

    private void initView() {
        rlSet0=findViewById(R.id.rlSet0);
        rlSet1=findViewById(R.id.rlSet1);
        rlSet2=findViewById(R.id.rlSet2);
        rlSet3=findViewById(R.id.rlSet3);
        rlSet4=findViewById(R.id.rlSet4);
        ivSetBack=findViewById(R.id.ivSetBack);
    }
    private void initData() {
    }
    private void initControl() {
        rlSet0.setOnClickListener(this);
        rlSet1.setOnClickListener(this);
        rlSet2.setOnClickListener(this);
        rlSet3.setOnClickListener(this);
        rlSet4.setOnClickListener(this);
        ivSetBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivSetBack:
                finish();
                break;
            case R.id.rlSet0:
                break;
            case R.id.rlSet1:
                if(getSharedPreferences("set",0).getBoolean("isLogin",false)){
                    startActivity(new Intent("android.intent.action.shopLing.Location"));
                }else{
                    startActivity(new Intent("android.intent.action.shopLing.Login"));
                }
                break;
            case R.id.rlSet2:
                break;
            case R.id.rlSet3:
                break;
            case R.id.rlSet4:
                SharedPreferences sp=getSharedPreferences("set",0);
                sp.edit().putBoolean("isLogin",false).commit();
                LitePal.deleteAll(User.class);
                finish();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(getSharedPreferences("set",0).getBoolean("isLogin",false)){
            rlSet4.setVisibility(View.VISIBLE);
        }else{
            rlSet4.setVisibility(View.GONE);
        }
    }
}
