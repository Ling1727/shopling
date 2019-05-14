package com.example.shopling.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.shopling.R;
import com.example.shopling.model.User;

import org.litepal.LitePal;

/**
 * Created by hasee on 2019/3/28.
 */

public class MeFragment extends Fragment implements View.OnClickListener{
    private View view;
    private ImageView ivMeHead,ivSet,ivMeInformation,ivMe001,ivMe002,ivMe003,ivMe004;
    private TextView tvLogin,tvName;
    private User user;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_me,container,false);
        initView();
        initData();
        initControl();
        return view;
    }

    private void initView() {
        ivMeHead=view.findViewById(R.id.ivMeHead);
        tvLogin=view.findViewById(R.id.tvLogin);
        tvName=view.findViewById(R.id.tvName);
        ivSet=view.findViewById(R.id.ivSet);
        ivMeInformation=view.findViewById(R.id.ivMeInformation);
        ivMe001=view.findViewById(R.id.ivMe001);
        ivMe002=view.findViewById(R.id.ivMe002);
        ivMe003=view.findViewById(R.id.ivMe003);
        ivMe004=view.findViewById(R.id.ivMe004);

    }
    private void initData() {
        user= LitePal.findFirst(User.class);
    }
    private void initControl() {
        tvLogin.setOnClickListener(this);
        ivSet.setOnClickListener(this);
        ivMeInformation.setOnClickListener(this);
        ivMe001.setOnClickListener(this);
        ivMe002.setOnClickListener(this);
        ivMe003.setOnClickListener(this);
        ivMe004.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.tvLogin:
                startActivity(new Intent("android.intent.action.shopLing.Login"));
                break;
            case R.id.ivSet:
                startActivity(new Intent("android.intent.action.shopLing.Set"));
                break;
            //消息
            case R.id.ivMeInformation:
                break;
            case R.id.ivMe001:
                Intent intent=new Intent("android.intent.action.shopLing.MyOrderForm");
                intent.putExtra("page",1);
                startActivity(intent);
                break;
            case R.id.ivMe002:
                Intent intent1=new Intent("android.intent.action.shopLing.MyOrderForm");
                intent1.putExtra("page",2);
                startActivity(intent1);
                break;
            case R.id.ivMe003:
                break;
            case R.id.ivMe004:
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences sp=getActivity().getSharedPreferences("set",0);
        if(sp.getBoolean("isLogin",false)){
            user= LitePal.findFirst(User.class);
            tvLogin.setVisibility(View.GONE);
            tvName.setVisibility(View.VISIBLE);
            tvName.setText(user.getName());
            RequestOptions options=new RequestOptions()
                    .placeholder(R.drawable.test001)
                    .error(R.drawable.error);
            Glide.with(getActivity()).load(user.getHead_portrait()).apply(options).into(ivMeHead);
        }else{
            tvLogin.setVisibility(View.VISIBLE);
            tvName.setVisibility(View.GONE);
        }
    }
}
