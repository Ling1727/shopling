package com.example.shopling.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shopling.R;
import com.example.shopling.adapter.MyOrderFromFPAdapter;
import com.example.shopling.fragment.MyOdrderFormFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasee on 2019/5/4.
 */

public class MyOrderForm extends BaseAppCompatActivity implements View.OnClickListener{
    private ViewPager vpOrder;
    private List<Fragment> fragmentList;
    private TextView tvAll,tvObligation,tvDropShipping,tvFinish,tvCancel;
    private View view0,view1,view2,view3,view4;
    private List<TextView> textViewList;
    private List<View> viewList;
    private ImageView ivBack0;
    private String[] strings=new String[]{"000","102","103","104","105"};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_form);
        initView();
        initData();
        initControl();
    }

    private void initView() {
        vpOrder=findViewById(R.id.vpOrder);
        tvAll=findViewById(R.id.tvAll);
        tvObligation=findViewById(R.id.tvObligation);
        tvDropShipping=findViewById(R.id.tvDropShipping);
        tvFinish=findViewById(R.id.tvFinish);
        tvCancel=findViewById(R.id.tvCancel);
        view0=findViewById(R.id.view0);
        view1=findViewById(R.id.view1);
        view2=findViewById(R.id.view2);
        view3=findViewById(R.id.view3);
        view4=findViewById(R.id.view4);
        ivBack0=findViewById(R.id.ivBack0);
    }


    private void initData() {
        fragmentList=new ArrayList<>();
        for(int i=0;i<5;i++){
            MyOdrderFormFragment myOdrderFormFragment=new MyOdrderFormFragment();
            Bundle bundle=new Bundle();
            bundle.putString("state",strings[i]);
            myOdrderFormFragment.setArguments(bundle);
            fragmentList.add(myOdrderFormFragment);
        }

        textViewList=new ArrayList<>();
        textViewList.add(tvAll);
        textViewList.add(tvObligation);
        textViewList.add(tvDropShipping);
        textViewList.add(tvFinish);
        textViewList.add(tvCancel);

        viewList=new ArrayList<>();
        viewList.add(view0);
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        viewList.add(view4);

    }
    private void initControl() {
        vpOrder.setAdapter(new MyOrderFromFPAdapter(getSupportFragmentManager(),fragmentList));
        vpOrder.setOffscreenPageLimit(4);
        vpOrder.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onPageSelected(int position) {
                for(int i=0;i<fragmentList.size();i++){
                    if(i==position){
                        viewList.get(i).setVisibility(View.VISIBLE);
                        textViewList.get(i).setTextColor(getColor(R.color.black));
                    }else{
                        viewList.get(i).setVisibility(View.INVISIBLE);
                        textViewList.get(i).setTextColor(getColor(R.color.color3));
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tvAll.setOnClickListener(this);
        tvObligation.setOnClickListener(this);
        tvDropShipping.setOnClickListener(this);
        tvFinish.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        ivBack0.setOnClickListener(this);
        vpOrder.setCurrentItem(getIntent().getIntExtra("page",0));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvAll:
                vpOrder.setCurrentItem(0);
                break;
            case R.id.tvObligation:
                vpOrder.setCurrentItem(1);
                break;
            case R.id.tvDropShipping:
                vpOrder.setCurrentItem(2);
                break;
            case R.id.tvFinish:
                vpOrder.setCurrentItem(3);
                break;
            case R.id.tvCancel:
                vpOrder.setCurrentItem(4);
                break;
            case R.id.ivBack0:
                finish();
                break;
        }
    }
}
