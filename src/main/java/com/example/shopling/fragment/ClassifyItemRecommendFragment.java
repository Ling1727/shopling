package com.example.shopling.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.example.shopling.R;
import com.example.shopling.adapter.LVClassifyItemAdapter;
import com.example.shopling.view.SlideshowView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hasee on 2019/4/14.
 */

public class ClassifyItemRecommendFragment extends Fragment {
    private View view;
    private RelativeLayout rlClassify0;
    private SlideshowView slideshowView;
    private RecyclerView rlvClassify1,rlvClassify0;
    private LVClassifyItemAdapter lvClassifyItemAdapter0,lvClassifyItemAdapter1;
    private List<Map<String,String>> data0;
    private List<Map<String,String>> data1;
    private GridLayoutManager gridLayoutManager0,gridLayoutManager1;
    private String[] strings1=new String[]{"笔记本","空调","手机","服饰","休闲零食","生鲜","图书","面膜","投资金",
            "瑞表","牛奶","纸巾"};
    private String[] strings0=new String[]{"妆容发布","不止5折","家电家装季"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_classify_item_recommend,container,false);
        initView();
        initdata();
        initControl();
        return view;
    }

    private void initView() {
        rlClassify0=view.findViewById(R.id.rlClassify0);
        rlvClassify1=view.findViewById(R.id.rlvClassify1);
        rlvClassify0=view.findViewById(R.id.rlvClassify0);
    }

    private void initdata() {
        data0=new ArrayList<>();
        for(int i=0;i<strings0.length;i++){
            Map<String,String> map=new HashMap<>();
            map.put("text",strings0[i]);
            map.put("ivPath","http://111.230.32.36:8084/ShopLing/image/1.jpg");
            data0.add(map);
        }
        data1=new ArrayList<>();
        for(int i=0;i<strings1.length;i++){
            Map<String,String> map=new HashMap<>();
            map.put("text",strings1[i]);
            map.put("ivPath","http://111.230.32.36:8084/ShopLing/image/1.jpg");
            data1.add(map);
        }
        slideshowView=new SlideshowView(getActivity());
        lvClassifyItemAdapter0=new LVClassifyItemAdapter(getActivity(),data0);
        lvClassifyItemAdapter1=new LVClassifyItemAdapter(getActivity(),data1);
        gridLayoutManager0=new GridLayoutManager(getActivity(),3,LinearLayoutManager.VERTICAL,true);
        gridLayoutManager1=new GridLayoutManager(getActivity(),3,LinearLayoutManager.VERTICAL,true);
    }

    private void initControl() {
        rlClassify0.addView(slideshowView);
        //linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rlvClassify0.setLayoutManager(gridLayoutManager0);
        rlvClassify0.setAdapter(lvClassifyItemAdapter0);
        rlvClassify1.setLayoutManager(gridLayoutManager1);
        rlvClassify1.setAdapter(lvClassifyItemAdapter1);
    }


    @Override
    public void onStart() {
        super.onStart();
        slideshowView.start();
        slideshowView.setRun(true);
        Log.d("test","onStart");
    }

    @Override
    public void onStop() {
        super.onStop();
        slideshowView.setRun(false);
        Log.d("test","onStop");
    }
}
