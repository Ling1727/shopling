package com.example.shopling.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shopling.R;
import com.example.shopling.adapter.CommodityAdapter;
import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasee on 2019/3/28.
 */

public class FindFragment extends Fragment implements View.OnClickListener{
    private View view;
    private ViewPager vpFind;
    private List<Fragment> fragmentList;
    private String[] strings=new String[]{"精选","9.9元抢","搭配","直播","视频"};
    private TextView tv111,tv112,tv113,tv114,tv115,tv116;
    private View view111,view112,view113,view114,view115,view116;
    private List<TextView> textViewList;
    private List<View> viewList;
    private SuperSwipeRefreshLayout ssrlFind;
    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    ssrlFind.setRefreshing(false);
                    break;
            }
            return false;
        }
    });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_find,container,false);
        initView();
        initData();
        initControl();
        return view;
    }

    private void initView() {
        vpFind=view.findViewById(R.id.vpFind);
        tv111=view.findViewById(R.id.tv111);
        tv112=view.findViewById(R.id.tv112);
        tv113=view.findViewById(R.id.tv113);
        tv114=view.findViewById(R.id.tv114);
        tv115=view.findViewById(R.id.tv115);
        tv116=view.findViewById(R.id.tv116);
        view111=view.findViewById(R.id.view111);
        view112=view.findViewById(R.id.view112);
        view113=view.findViewById(R.id.view113);
        view114=view.findViewById(R.id.view114);
        view115=view.findViewById(R.id.view115);
        view116=view.findViewById(R.id.view116);
        ssrlFind=view.findViewById(R.id.ssrlFind);
    }

    private void initData() {
        textViewList=new ArrayList<>();
        viewList=new ArrayList<>();
        textViewList.add(tv111);
        textViewList.add(tv112);
        textViewList.add(tv113);
        textViewList.add(tv114);
        textViewList.add(tv115);
        textViewList.add(tv116);
        viewList.add(view111);
        viewList.add(view112);
        viewList.add(view113);
        viewList.add(view114);
        viewList.add(view115);
        viewList.add(view116);
        fragmentList=new ArrayList<>();
        FindFragmentPage0 findFragmentPage0=new FindFragmentPage0();
        fragmentList.add(findFragmentPage0);
        for(int i=0;i<5;i++){
            FindFragmentPage1 findFragmentPage1=new FindFragmentPage1();
            Bundle bundle=new Bundle();
            bundle.putString("key",strings[i]);
            findFragmentPage1.setArguments(bundle);
            fragmentList.add(findFragmentPage1);
        }
    }

    private void initControl() {
        vpFind.setAdapter(new CommodityAdapter(getChildFragmentManager(),fragmentList));
        vpFind.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for(int i=0;i<textViewList.size();i++){
                    if(i==position){
                        textViewList.get(i).setTypeface(Typeface.DEFAULT,Typeface.BOLD);
                        viewList.get(i).setVisibility(View.VISIBLE);
                    }else{
                        textViewList.get(i).setTypeface(Typeface.DEFAULT,Typeface.NORMAL);
                        viewList.get(i).setVisibility(View.INVISIBLE);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        ssrlFind.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
            //开始刷新
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            handler.sendEmptyMessage(1);
                        } catch (InterruptedException e) {

                        }
                    }
                }).start();
            }
            //下拉距离
            @Override
            public void onPullDistance(int i) {

            }
            //下拉过程中，下拉的距离是否足够出发刷新
            @Override
            public void onPullEnable(boolean b) {

            }
        });

        tv111.setOnClickListener(this);
        tv112.setOnClickListener(this);
        tv113.setOnClickListener(this);
        tv114.setOnClickListener(this);
        tv115.setOnClickListener(this);
        tv116.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv111:
                vpFind.setCurrentItem(0);
                break;
            case R.id.tv112:
                vpFind.setCurrentItem(1);
                break;
            case R.id.tv113:
                vpFind.setCurrentItem(2);
                break;
            case R.id.tv114:
                vpFind.setCurrentItem(3);
                break;
            case R.id.tv115:
                vpFind.setCurrentItem(4);
                break;
            case R.id.tv116:
                vpFind.setCurrentItem(5);
                break;
        }
    }
}
