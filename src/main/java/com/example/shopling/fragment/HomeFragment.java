package com.example.shopling.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.example.shopling.R;
import com.example.shopling.adapter.RecyclerAdapter;
import com.example.shopling.adapter.RecyclerAdapter1;
import com.example.shopling.model.CommodityOne;
import com.example.shopling.model.CommodityTwo;
import com.example.shopling.model.Picture;
import com.example.shopling.view.LineGridView;
import com.example.shopling.view.SlideshowView;
import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.litepal.LitePal;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by hasee on 2019/3/28.
 */

public class HomeFragment extends Fragment implements View.OnClickListener{
    private View view;
    private RelativeLayout rlSlideshowView,rlHome0,rlHome1,rlHome2,rlHome3,rlLogin;
    private SlideshowView slideshowView;
    private SimpleAdapter simpleAdapter;
    private List<Map<String,Object>> simpleList=new ArrayList<>();
    private LineGridView gv0;
    private RecyclerView recycler0,recycler1;
    private ScrollView svHome;
    private ImageView ivHome0,ivHome1;
    private TextView tvHome0,tvHome1,tvLogin;
    private SuperSwipeRefreshLayout superSwipeRefreshLayout;
    private RecyclerAdapter1 recyclerAdapter1;
    private List<CommodityOne> commodityOneList;
    private List<List<CommodityOne>> listList;
    private List<CommodityTwo> listListTwo;
    private RecyclerAdapter recyclerAdapter;
    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 1:superSwipeRefreshLayout.setRefreshing(false);
                    break;
                case 2:recyclerAdapter.notifyDataSetChanged();
                    break;
                case 3:
                    rlHome3.setVisibility(View.INVISIBLE);
                    recyclerAdapter1.notifyDataSetChanged();
                    break;
                case 4:
                    Toast.makeText(getActivity(), "到底了，商品数目有限！", Toast.LENGTH_SHORT).show();
                    break;
            }
            return false;
        }
    });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_home,container,false);
        initView();
        initData();
        initControl();
        return view;
    }

    private void initView() {
        rlSlideshowView=view.findViewById(R.id.rlSlideshowView);
        gv0=view.findViewById(R.id.gv0);
        recycler0=view.findViewById(R.id.recycler0);
        recycler1=view.findViewById(R.id.recycler1);
        rlHome0=view.findViewById(R.id.rlHome0);
        rlHome1=view.findViewById(R.id.rlHome1);
        rlHome2=view.findViewById(R.id.rlHome2);
        rlHome3=view.findViewById(R.id.rlHome3);
        rlLogin=view.findViewById(R.id.rlLogin);
        svHome=view.findViewById(R.id.svHome);
        ivHome0=view.findViewById(R.id.ivHome0);
        ivHome1=view.findViewById(R.id.ivHome1);
        tvHome0=view.findViewById(R.id.tvHome0);
        tvHome1=view.findViewById(R.id.tvHome1);
        tvLogin=view.findViewById(R.id.tvLogin);
        superSwipeRefreshLayout=view.findViewById(R.id.superSwipeRefreshLayout);
    }

    private void initData() {
        rlHome0.getBackground().mutate().setAlpha(128);
        rlHome1.getBackground().mutate().setAlpha(128);
        rlHome2.getBackground().mutate().setAlpha(0);
        rlLogin.getBackground().mutate().setAlpha(160);
        slideshowView=new SlideshowView(getActivity());
        String[] gv0String=new String[]{"超市","旅行","服饰","生鲜","到家","超市","旅行","服饰","生鲜","到家"};
        int[] gv0Int=new int[]{R.drawable.menu_carttoon,R.drawable.menu_collect,R.drawable.menu_cosplay,R.drawable.menu_cyc,R.drawable.menu_game,
                R.drawable.menu_jewelry,R.drawable.menu_model,R.drawable.menu_oldage,R.drawable.menu_snack,R.drawable.menu_stationery,};
        for(int i=0;i<10;i++){
            Map<String,Object> map=new HashMap<>();
            map.put("text",gv0String[i]);
            map.put("image",gv0Int[i]);
            simpleList.add(map);
        }
        simpleAdapter=new SimpleAdapter(getActivity(),simpleList,R.layout.grid_view,new String[]{"image","text"},new int[]{R.id.iv0,R.id.tv0});

        listListTwo=new ArrayList<>();
        List<CommodityTwo> commodityTwoList=LitePal.findAll(CommodityTwo.class);
        listListTwo.addAll(commodityTwoList);
        recyclerAdapter1=new RecyclerAdapter1(getActivity(),listListTwo);

        listList=new ArrayList<>();
        recyclerAdapter=new RecyclerAdapter(getActivity(),listList);

    }

    private void initControl() {
        rlSlideshowView.addView(slideshowView);
        gv0.setAdapter(simpleAdapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        //linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycler0.setLayoutManager(linearLayoutManager);
        recycler0.setAdapter(recyclerAdapter);
        loadData();
        StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        //staggeredGridLayoutManager.setOrientation(StaggeredGridLayoutManager.HORIZONTAL);
        recycler1.setLayoutManager(staggeredGridLayoutManager);
        recycler1.setAdapter(recyclerAdapter1);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            svHome.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if(160<=scrollY&&scrollY<=360){
                        rlHome2.getBackground().mutate().setAlpha((int)(((float)scrollY-160)/200*256));
                    }else if(scrollY<160){
                        rlHome2.getBackground().mutate().setAlpha(0);
                    }else{
                        rlHome2.getBackground().mutate().setAlpha(255);
                    }
                    if(260<scrollY){
                        rlHome0.getBackground().mutate().setAlpha(0);
                        rlHome1.getBackground().mutate().setAlpha(0);
                        ivHome0.setImageResource(R.drawable.scan_black);
                        ivHome1.setImageResource(R.drawable.information);
                        tvHome0.setTextColor(Color.BLACK);
                        tvHome1.setTextColor(Color.BLACK);
                    }else{
                        rlHome0.getBackground().mutate().setAlpha(128);
                        rlHome1.getBackground().mutate().setAlpha(128);
                        ivHome0.setImageResource(R.drawable.scan_white0);
                        ivHome1.setImageResource(R.drawable.information_white);
                        tvHome0.setTextColor(Color.WHITE);
                        tvHome1.setTextColor(Color.WHITE);
                    }
                    /*Log.d("test0",rlHome3.getVisibility()+"...."+View.GONE+"...."+svHome.getChildAt(0).getMeasuredHeight()
                            +"...."+svHome.getScrollY()+"...."+svHome.getHeight());*/
                    if(rlHome3.getVisibility()==View.INVISIBLE&&svHome.getChildAt(0).getMeasuredHeight()==(svHome.getScrollY()+svHome.getHeight())){
                        rlHome3.setVisibility(View.VISIBLE);
                        loadData1();
                    }
                }
            });
        }
        superSwipeRefreshLayout.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
            //开始刷新
            @Override
            public void onRefresh() {
                loadData();
            }
            //下拉距离
            @Override
            public void onPullDistance(int i) {
                if(i>0){
                    rlHome2.setVisibility(View.INVISIBLE);
                }else{
                    rlHome2.setVisibility(View.VISIBLE);
                }

            }
            //下拉过程中，下拉的距离是否足够出发刷新
            @Override
            public void onPullEnable(boolean b) {

            }
        });
        //设置监听
        tvLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvLogin:
                startActivity(new Intent("android.intent.action.shopLing.Login"));
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        slideshowView.start();
        slideshowView.setRun(true);

        SharedPreferences sp=getActivity().getSharedPreferences("set",0);
        rlLogin.setVisibility(sp.getBoolean("isLogin",false)?View.INVISIBLE:View.VISIBLE);
    }


    @Override
    public void onStop() {
        super.onStop();
        slideshowView.setRun(false);
    }


    //异步get
    public void loadData(){
        OkHttpClient okHttpClient=new OkHttpClient();
        final Request request=new Request.Builder().url("http://111.230.32.36:8084/ShopLing/CommodityServlet?index=one&num=0").get().build();
        Call call=okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "请求数据失败", Toast.LENGTH_SHORT).show();
                        handler.sendEmptyMessage(1);
                    }
                });
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response)  {
                Log.d("test0","请求数据成功");
                try {
                    String string=response.body().string();
                    JSONArray index = JSONArray.fromObject(string);
                    commodityOneList= LitePal.findAll(CommodityOne.class);
                    List<Picture> pictureList=LitePal.findAll(Picture.class);
                    //JSONArray index = json.optJSONArray(null);
                    for(int i=0;i<index.size();i++){
                        JSONObject obj = (JSONObject) index.opt(i);
                        CommodityOne commodityOne=new CommodityOne();
                        //commodityOne.setComment(obj.getString("comment"));
                        commodityOne.setIntro(obj.getString("intro"));
                        commodityOne.setNumber(obj.getInt("number"));
                        //commodityOne.setPicture(obj.getString("picture"));
                        commodityOne.setPrice(obj.getDouble("price"));
                        commodityOne.setStore(obj.getString("store"));
                        if(commodityOneList.size()>=33){
                            commodityOne.update(i+1);
                        }else{
                            commodityOne.save();
                        }
                        JSONArray pictures = obj.getJSONArray("picture");
                        for(int j=0;j<pictures.size();j++){
                            JSONObject pic = (JSONObject) pictures.opt(j);
                            boolean ishave=false;
                            for(int z=0;z<pictureList.size();z++){
                                if(pictureList.get(z).getPath().equals(pic.getString("path"))){
                                    ishave=true;
                                    break;
                                }
                            }
                            if(!ishave){
                                Picture picture=new Picture();
                                picture.setCommodity_number(pic.getInt("commodity_number"));
                                picture.setPath(pic.getString("path"));
                                picture.save();
                            }
                        }
                    }
                    long[] ids = new long[] {1,2,3,4,5,6,7,8,9,10,11};
                    commodityOneList= LitePal.findAll(CommodityOne.class,ids);
                    Log.d("test0",commodityOneList.size()+"");
                    listList.add(commodityOneList);
                    long[] ids1 = new long[] {12,13,14,15,16,17,18,19,20,21,22};
                    commodityOneList= LitePal.findAll(CommodityOne.class,ids1);
                    listList.add(commodityOneList);
                    long[] ids2 = new long[] {23,24,25,26,27,28,29,30,31,32,33};
                    commodityOneList= LitePal.findAll(CommodityOne.class,ids2);
                    listList.add(commodityOneList);
                    handler.sendEmptyMessage(2);
                    handler.sendEmptyMessage(1);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    //异步get
    public void loadData1(){
        List<CommodityTwo> commodityTwoList=LitePal.findAll(CommodityTwo.class);
        int num=commodityTwoList.size();
        OkHttpClient okHttpClient=new OkHttpClient();
        final Request request=new Request.Builder().url("http://111.230.32.36:8084/ShopLing/CommodityServlet?index=two&num="+num).get().build();
        Call call=okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "请求数据失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                Log.d("test0","请求数据成功....1");
                String string= null;
                try {
                    string = response.body().string();
                    if(string.equals("")){
                        handler.sendEmptyMessage(4);
                    }else{
                        JSONArray index = JSONArray.fromObject(string);
                        List<Picture> pictureList=LitePal.findAll(Picture.class);
                        //JSONArray index = json.optJSONArray(null);
                        for(int i=0;i<index.size();i++){
                            JSONObject obj = (JSONObject) index.opt(i);
                            CommodityTwo commodityTwo=new CommodityTwo();
                            //commodityTwo.setComment(obj.getString("comment"));
                            commodityTwo.setIntro(obj.getString("intro"));
                            commodityTwo.setNumber(obj.getInt("number"));
                            //commodityTwo.setPicture(obj.getString("picture"));
                            commodityTwo.setPrice(obj.getDouble("price"));
                            commodityTwo.setStore(obj.getString("store"));
                            commodityTwo.save();
                            JSONArray pictures = obj.getJSONArray("picture");
                            for(int j=0;j<pictures.size();j++){
                                JSONObject pic = (JSONObject) pictures.opt(j);
                                boolean ishave=false;
                                for(int z=0;z<pictureList.size();z++){
                                    if(pictureList.get(z).getPath().equals(pic.getString("path"))){
                                        ishave=true;
                                        break;
                                    }
                                }
                                if(!ishave){
                                    Picture picture=new Picture();
                                    picture.setCommodity_number(pic.getInt("commodity_number"));
                                    picture.setPath(pic.getString("path"));
                                    picture.save();
                                }
                            }
                        }

                        List<CommodityTwo> commodityTwoList=LitePal.findAll(CommodityTwo.class);
                        listListTwo.clear();
                        listListTwo.addAll(commodityTwoList);

                    }
                    handler.sendEmptyMessage(3);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        LitePal.deleteAll(CommodityTwo.class);
        super.onDestroyView();
    }
}

