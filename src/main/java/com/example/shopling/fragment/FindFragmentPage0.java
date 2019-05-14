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
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.shopling.R;

/**
 * Created by hasee on 2019/4/24.
 */

public class FindFragmentPage0 extends Fragment implements View.OnClickListener{
    private View view;
    private Button btFindLogin;
    private RelativeLayout rlFind0,rlFind1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_find_page_0,container,false);
        initView();
        initData();
        initControl();
        return view;
    }

    private void initView() {
        btFindLogin=view.findViewById(R.id.btFindLogin);
        rlFind0=view.findViewById(R.id.rlFind0);
        rlFind1=view.findViewById(R.id.rlFind1);
    }

    private void initData() {
    }

    private void initControl() {
        btFindLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btFindLogin:
                getActivity().startActivity(new Intent("android.intent.action.shopLing.Login"));
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences sp=getActivity().getSharedPreferences("set",0);
        if(sp.getBoolean("isLogin",false)){
            rlFind1.setVisibility(View.VISIBLE);
            rlFind0.setVisibility(View.GONE);
        }else{
            rlFind0.setVisibility(View.VISIBLE);
            rlFind1.setVisibility(View.GONE);
        }
    }
}
