package com.example.shopling.activity;


import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopling.R;
import com.example.shopling.adapter.CommodityAdapter;
import com.example.shopling.fragment.CommodityPageFragment0;
import com.example.shopling.fragment.CommodityPageFragment1;
import com.example.shopling.model.CommodityOne;
import com.example.shopling.model.CommodityTwo;
import com.example.shopling.model.User;

import org.litepal.LitePal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by hasee on 2019/4/22.
 */

public class CommodityActivity extends BaseAppCompatActivity implements View.OnClickListener{
    private ViewPager vpCommodity;
    private List<Fragment> fragmentList;
    private TextView tvCommodity1,tvCommodity2,tvShopCar,tvShop;
    private View viewCommodity11,viewCommodity12;
    private ImageView ivBack,ivSan;
    private RelativeLayout rl000,rl001,rl002;
    private int number;
    private List<CommodityTwo> commodityTwoList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity);
        initView();
        initData();
        initControl();
    }

    private void initView() {
        vpCommodity=findViewById(R.id.vpCommodity);
        tvCommodity1=findViewById(R.id.tvCommodity1);
        tvCommodity2=findViewById(R.id.tvCommodity2);
        viewCommodity11=findViewById(R.id.viewCommodity11);
        viewCommodity12=findViewById(R.id.viewCommodity12);
        ivBack=findViewById(R.id.ivBack);
        ivSan=findViewById(R.id.ivSan);
        tvShopCar=findViewById(R.id.tvShopCar);
        tvShop=findViewById(R.id.tvShop);
        rl000=findViewById(R.id.rl000);
        rl001=findViewById(R.id.rl001);
        rl002=findViewById(R.id.rl002);
    }

    private void initData() {
        number=getIntent().getIntExtra("number",0);
        fragmentList=new ArrayList<>();
        CommodityPageFragment0 commodityPageFragment0=new CommodityPageFragment0();
        CommodityPageFragment1 commodityPageFragment1=new CommodityPageFragment1();
        Bundle bundle=new Bundle();
        bundle.putInt("number",number);
        Bundle bundle1=new Bundle();
        bundle1.putInt("number",number);
        commodityPageFragment0.setArguments(bundle);
        commodityPageFragment1.setArguments(bundle1);
        fragmentList.add(commodityPageFragment0);
        fragmentList.add(commodityPageFragment1);
        commodityTwoList=LitePal.where("number=?",number+"").find(CommodityTwo.class);
    }

    private void initControl() {
        ivBack.getBackground().mutate().setAlpha(128);
        ivSan.getBackground().mutate().setAlpha(128);
        FragmentManager fm=getSupportFragmentManager();
        vpCommodity.setAdapter(new CommodityAdapter(fm,fragmentList));
        vpCommodity.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==0){
                    tvCommodity1.setTextColor(Color.BLACK);
                    tvCommodity2.setTextColor(Color.parseColor("#ff757575"));
                    viewCommodity11.setVisibility(View.VISIBLE);
                    viewCommodity12.setVisibility(View.INVISIBLE);
                }else{
                    tvCommodity1.setTextColor(Color.parseColor("#ff757575"));
                    tvCommodity2.setTextColor(Color.BLACK);
                    viewCommodity11.setVisibility(View.INVISIBLE);
                    viewCommodity12.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        ivBack.setOnClickListener(this);
        ivSan.setOnClickListener(this);
        tvShop.setOnClickListener(this);
        tvShopCar.setOnClickListener(this);
        rl000.setOnClickListener(this);
        rl001.setOnClickListener(this);
        rl002.setOnClickListener(this);
        tvCommodity1.setOnClickListener(this);
        tvCommodity2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivSan:
                break;
            case R.id.ivBack:
                this.finish();
                break;
            case R.id.tvShop:
                if(getSharedPreferences("set",0).getBoolean("isLogin",false)){
                    Intent intent=new Intent("android.intent.action.shopLing.OrderForm");
                    intent.putExtra("commodity_number"+0,commodityTwoList.get(0).getNumber()+"");
                    intent.putExtra("number"+0,"1");
                    intent.putExtra("money",commodityTwoList.get(0).getPrice()+"");
                    intent.putExtra("geshu",1);
                    startActivity(intent);

                }else{
                    startActivity(new Intent("android.intent.action.shopLing.Login"));
                }
                break;
            case R.id.tvShopCar:
                if(getSharedPreferences("set",0).getBoolean("isLogin",false)){
                    new MyAsyncTask().execute(number+"");
                }else{
                    startActivity(new Intent("android.intent.action.shopLing.Login"));
                }
                break;
            case R.id.rl000:
                break;
            case R.id.rl001:
                break;
            case R.id.rl002:
                break;
            case R.id.tvCommodity1:
                vpCommodity.setCurrentItem(0);
                break;
            case R.id.tvCommodity2:
                vpCommodity.setCurrentItem(1);
                break;
        }
    }

    public void LoadData(){

    }
    /*http://111.230.32.36:8084/ShopLing/UserServlet?type=shoppingTrolleySet&user_id=ling123&commodity_number=1010&state=001&nunmber_0=1*/
    class MyAsyncTask extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... strings) {
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
                    .add("type", "shoppingTrolleySet")
                    .add("state", "001")
                    .add("commodity_number", strings[0])
                    .add("nunmber_0", "1")
                    .add("user_id", user.getNameId())
                    .build();
            Request request = new Request.Builder()
                    .post(formBody)
                    .url(path)
                    .build();
            //调用okHttpClient对象实现CallBack方法
            Call call = okHttpClient.newCall(request);
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
                Toast.makeText(CommodityActivity.this,"已加入购物车",Toast.LENGTH_SHORT).show();
            }else if(s.equals("have")){
                Toast.makeText(CommodityActivity.this,"购物车已存在该商品，请问重复添加",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(CommodityActivity.this,"网络连接出错",Toast.LENGTH_SHORT).show();
            }
        }
    }

}
