package com.example.shopling.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.shopling.R;
import com.example.shopling.adapter.ShoppingRVAdapter;
import com.example.shopling.model.Commodity;
import com.example.shopling.model.Picture;
import com.example.shopling.model.ShoppingTrolley;
import com.example.shopling.model.User;
import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.litepal.LitePal;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by hasee on 2019/3/28.
 */

public class ShoppingFragment extends Fragment implements View.OnClickListener{
    private View view;
    private RelativeLayout rlShopLogin,rlShop0,rlShop1,rlShop2,rlDelete,rlTuiJian;
    private Button btShopLogin,bt0,bt1,btSettlement;
    private SuperSwipeRefreshLayout ssrlShop;
    private List<ShoppingTrolley> shoppingTrolleyList;
    private TextView tvHint,tvShopSet,tvAll,tvDelete;
    private ProgressBar pbShop;
    private RecyclerView rvShopcar;
    private ShoppingRVAdapter shoppingRVAdapter;
    private CheckBox cbAll,cbAll1;
    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 1:ssrlShop.setRefreshing(false);
                    break;
                case 2:
                    Log.d("test1",shoppingTrolleyList.size()+"");
                    if(!shoppingTrolleyList.isEmpty()){
                        rlShop0.setVisibility(View.INVISIBLE);
                        shoppingRVAdapter.mNotifyDataSetChanged(shoppingTrolleyList.size());
                        rlShop2.setVisibility(View.VISIBLE);
                        tvShopSet.setVisibility(View.VISIBLE);
                    }else{
                        rlShop2.setVisibility(View.INVISIBLE);
                        tvShopSet.setVisibility(View.INVISIBLE);
                    }
                    tvHint.setVisibility(View.INVISIBLE);
                    ssrlShop.setRefreshing(false);
                    pbShop.setVisibility(View.INVISIBLE);
                    rlShop1.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    rlShop1.setVisibility(View.INVISIBLE);
                    tvHint.setVisibility(View.VISIBLE);
                    pbShop.setVisibility(View.INVISIBLE);
                    ssrlShop.setRefreshing(false);
                    rlShop2.setVisibility(View.INVISIBLE);
                    tvShopSet.setVisibility(View.INVISIBLE);
                    break;
            }
            return false;
        }
    });



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_shopping,container,false);
        initView();
        initData();
        initControl();
        return view;
    }

    private void initView() {
        rlShopLogin=view.findViewById(R.id.rlShopLogin);
        btShopLogin=view.findViewById(R.id.btShopLogin);
        bt0=view.findViewById(R.id.bt0);
        bt1=view.findViewById(R.id.bt1);
        ssrlShop=view.findViewById(R.id.ssrlShop);
        rlShop0=view.findViewById(R.id.rlShop0);
        tvHint=view.findViewById(R.id.tvHint);
        rlShop1=view.findViewById(R.id.rlShop1);
        pbShop=view.findViewById(R.id.pbShop);
        rvShopcar=view.findViewById(R.id.rvShopcar);
        rlShop2=view.findViewById(R.id.rlShop2);
        tvShopSet=view.findViewById(R.id.tvShopSet);
        cbAll=view.findViewById(R.id.cbAll);
        tvAll=view.findViewById(R.id.tvAll);
        rlTuiJian=view.findViewById(R.id.rlTuiJian);
        tvDelete=view.findViewById(R.id.tvDelete);
        rlDelete=view.findViewById(R.id.rlDelete);
        cbAll1=view.findViewById(R.id.cbAll1);
        btSettlement=view.findViewById(R.id.btSettlement);
        rlTuiJian=view.findViewById(R.id.rlTuiJian);

    }
    private void initData() {
        shoppingTrolleyList=new ArrayList<>();
        shoppingRVAdapter=new ShoppingRVAdapter(getActivity(),shoppingTrolleyList);
    }

    private void initControl() {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        rvShopcar.setLayoutManager(linearLayoutManager);
        rvShopcar.setAdapter(shoppingRVAdapter);
        ssrlShop.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                if (getActivity().getSharedPreferences("set",0).getBoolean("isLogin",false)){
                    LoadData();
                }else {
                    handler.sendEmptyMessage(1);
                }
            }

            @Override
            public void onPullDistance(int i) {

            }

            @Override
            public void onPullEnable(boolean b) {

            }
        });

        shoppingRVAdapter.setOnCheckChangeListener(new ShoppingRVAdapter.OnCheckChangeListener() {

            @Override
            public void OnCheckChange(double sum,boolean isAll) {
                tvAll.setText("合计：¥"+sum);
                cbAll.setChecked(isAll);
                cbAll1.setChecked(isAll);
            }
        });

        shoppingRVAdapter.setOnRefreshLienster(new ShoppingRVAdapter.OnRefreshLienster() {
            @Override
            public void onRefresh(List<ShoppingRVAdapter.ViewHolder> viewHolderList) {
                viewHolderList.clear();
                LoadData();
            }
        });

        cbAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                shoppingRVAdapter.setCheck(isChecked);
            }
        });

        cbAll1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                shoppingRVAdapter.setCheck(isChecked);
            }
        });


        btShopLogin.setOnClickListener(this);
        bt0.setOnClickListener(this);
        bt1.setOnClickListener(this);
        tvShopSet.setOnClickListener(this);
        tvDelete.setOnClickListener(this);
        btSettlement.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.btShopLogin:
                startActivity(new Intent("android.intent.action.shopLing.Login"));
                break;
                //g逛逛秒杀
            case R.id.bt0:
                break;
                //看看关注
            case R.id.bt1:
                break;
            case R.id.tvShopSet:
                if(tvShopSet.getText().toString().equals("编辑")){
                    rlDelete.setVisibility(View.VISIBLE);
                    tvShopSet.setText("完成");
                    rlTuiJian.setVisibility(View.INVISIBLE);
                    for (int i=0;i<shoppingRVAdapter.getViewHolderList().size();i++){
                        (shoppingRVAdapter.getViewHolderList().get(i)).cbShop2.setChecked(false);
                    }
                }else{
                    shopSet();
                }
                break;
            case R.id.tvDelete:
                shoppingRVAdapter.delete();
                break;
            case R.id.btSettlement:
                if(tvAll.getText().toString().equals("合计：¥0.0")){
                    Toast.makeText(getActivity(), "你还没有选择商品", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent=new Intent("android.intent.action.shopLing.OrderForm");
                    int a=0;
                    for(int i=0;i<shoppingTrolleyList.size();i++){
                        if((shoppingRVAdapter.getViewHolderList().get(i)).cbShop2.isChecked()){
                            intent.putExtra("commodity_number"+a,shoppingTrolleyList.get(i).getCommodity_number()+"");
                            intent.putExtra("number"+a,(shoppingRVAdapter.getViewHolderList().get(i)).tvNumber.getText());
                            a++;
                        }
                    }
                    intent.putExtra("money",tvAll.getText().toString().substring(3));
                    intent.putExtra("geshu",a);
                    startActivity(intent);
                }
                break;
        }
    }
    public void shopSet(){
        rlDelete.setVisibility(View.INVISIBLE);
        tvShopSet.setText("编辑");
        rlTuiJian.setVisibility(View.VISIBLE);
        for (int i=0;i<shoppingRVAdapter.getViewHolderList().size();i++){
            (shoppingRVAdapter.getViewHolderList().get(i)).cbShop2.setChecked(false);
        }
    }

    /*http://111.230.32.36:8084/ShopLing/UserServlet?type=shoppingTrolleySet&user_id=ling123&commodity_number=1010&state=001&nunmber_0=1
    http://111.230.32.36:8084/ShopLing/UserServlet?type=shoppingTrolleyGet&user_id=ling123&state=001*/

    public void LoadData(){
        pbShop.setVisibility(View.VISIBLE);
        shoppingTrolleyList.clear();
        String result="";
        User user= LitePal.findFirst(User.class);
        String path="http://111.230.32.36:8084/ShopLing/UserServlet";
        //1:创建OKHttpClient对象
        //设置超时时间
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
        FormBody formBody = new FormBody.Builder()
                .add("type", "shoppingTrolleyGet")
                .add("state", "001")
                .add("user_id", user.getNameId())
                .build();
        Request request = new Request.Builder()
                .post(formBody)
                .url(path)
                .build();
        //调用okHttpClient对象实现CallBack方法
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("test1","请求失败");
                handler.sendEmptyMessage(3);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("test1","请求成功");
                try {
                    String string=response.body().string();
                    if(!string.equals("")){
                        JSONArray index = JSONArray.fromObject(string);
                        for(int i=0;i<index.size();i++) {
                            JSONObject obj = (JSONObject) index.opt(i);
                            ShoppingTrolley shoppingTrolley=new ShoppingTrolley();
                            shoppingTrolley.setUser_id(obj.getString("user_id"));
                            shoppingTrolley.setCommodity_number(obj.getInt("commodity_number"));
                            shoppingTrolley.setNumber(obj.getInt("number"));
                            shoppingTrolley.setState(obj.getString("state"));

                                Commodity commodity=new Commodity();
                                JSONObject jsonObject=obj.getJSONObject("commodity");
                                commodity.setNumber(jsonObject.getInt("number"));
                                commodity.setIntro(jsonObject.getString("intro"));
                                commodity.setStore(jsonObject.getString("store"));
                                commodity.setPrice(jsonObject.getDouble("price"));
                                    JSONArray p =jsonObject.getJSONArray("picture");
                                    JSONObject p2= (JSONObject) p.opt(0);
                                    Picture picture=new Picture();
                                    picture.setPath(p2.getString("path"));
                                    picture.setCommodity_number(p2.getInt("commodity_number"));
                                commodity.setPicture(picture);
                            shoppingTrolley.setCommodity(commodity);

                            shoppingTrolleyList.add(shoppingTrolley);
                        }
                    }
                    handler.sendEmptyMessage(2);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public TextView getTvShopSet() {
        return tvShopSet;
    }

    public void setTvShopSet(TextView tvShopSet) {
        this.tvShopSet = tvShopSet;
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences sp=getActivity().getSharedPreferences("set",0);
        if(sp.getBoolean("isLogin",false)){
            rlShopLogin.setVisibility(View.GONE);
            rvShopcar.setVisibility(View.VISIBLE);
            LoadData();
        }else{
            rlShopLogin.setVisibility(View.VISIBLE);
            shoppingTrolleyList.clear();
            shoppingRVAdapter.notifyDataSetChanged();
            rlDelete.setVisibility(View.INVISIBLE);
            tvShopSet.setText("编辑");
            rlTuiJian.setVisibility(View.VISIBLE);
            tvShopSet.setVisibility(View.INVISIBLE);
            rlShop2.setVisibility(View.INVISIBLE);
            rlShop0.setVisibility(View.VISIBLE);
        }
        Log.d("test2","onStart");
    }



    @Override
    public void onResume() {
        super.onResume();
        Log.d("test2","onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("test2","onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("test2","onStop");
    }

}
