package com.example.shopling.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.shopling.R;
import com.example.shopling.adapter.LocationRVAdapter;
import com.example.shopling.model.CommodityOne;
import com.example.shopling.model.Location;
import com.example.shopling.model.Picture;
import com.example.shopling.model.User;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.litepal.LitePal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by hasee on 2019/4/24.
 */

public class LocationActivity extends BaseActivity implements View.OnClickListener{
    private Button btLoction,btAdd,btRefresh;
    private RecyclerView rvLocation;
    private List<Location> locations;
    private LocationRVAdapter locationRVAdapter;
    private ImageView ivLocationBack;
    private RelativeLayout rlLocation;
    private ProgressBar pbLocation;
    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    pbLocation.setVisibility(View.INVISIBLE);
                    btRefresh.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    pbLocation.setVisibility(View.INVISIBLE);
                    if(locations.isEmpty()){
                        rlLocation.setVisibility(View.VISIBLE);
                        btAdd.setVisibility(View.INVISIBLE);
                    }else{
                        rlLocation.setVisibility(View.INVISIBLE);
                        btAdd.setVisibility(View.VISIBLE);
                        locationRVAdapter.notifyDataSetChanged();
                    }

                    break;
            }
            return false;
        }
    });
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        initView();
        initData();
        initControl();
    }

    private void initView() {
        btLoction=findViewById(R.id.btLoction);
        rvLocation=findViewById(R.id.rvLocation);
        btAdd=findViewById(R.id.btAdd);
        ivLocationBack=findViewById(R.id.ivLocationBack);
        rlLocation=findViewById(R.id.rlLocation);
        btRefresh=findViewById(R.id.btRefresh);
        pbLocation=findViewById(R.id.pbLocation);
    }

    private void initData() {
        locations= new ArrayList<>();
    }

    private void initControl() {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        rvLocation.setLayoutManager(linearLayoutManager);
        locationRVAdapter=new LocationRVAdapter(LocationActivity.this,locations);
        rvLocation.setAdapter(locationRVAdapter);

        btLoction.setOnClickListener(this);
        ivLocationBack.setOnClickListener(this);
        btAdd.setOnClickListener(this);
        btRefresh.setOnClickListener(this);

        if(locations.isEmpty()){
            rlLocation.setVisibility(View.VISIBLE);
            btAdd.setVisibility(View.INVISIBLE);
        }else{
            rlLocation.setVisibility(View.INVISIBLE);
            btAdd.setVisibility(View.VISIBLE);
        }
        loadData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivLocationBack:
                finish();
                break;
            case R.id.btLoction:
                Intent intent=new Intent("android.intent.action.shopLing.LocationSet" );
                startActivityForResult(intent,1);
                break;
            case R.id.btAdd:
                Intent intent1=new Intent("android.intent.action.shopLing.LocationSet" );
                startActivityForResult(intent1,1);
                break;
            case R.id.btRefresh:
                loadData();
                break;
        }
    }

    public void loadData(){
        locations.clear();
        pbLocation.setVisibility(View.VISIBLE);
        btRefresh.setVisibility(View.INVISIBLE);
        rlLocation.setVisibility(View.INVISIBLE);
        User user= LitePal.findFirst(User.class);
        OkHttpClient okHttpClient=new OkHttpClient();
        final Request request=new Request.Builder().url("http://111.230.32.36:8084/ShopLing/UserServlet?type=locationGet&user_id="+user.getNameId()).get().build();
        Call call=okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                 handler.sendEmptyMessage(1);
                 Log.d("test1","请求数据失败");
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response)  {
                Log.d("test1","请求数据成功");
                try {
                    String string=response.body().string();
                    Log.d("test1",!string.equals("")+"");
                    if(!string.equals("")){
                        JSONArray index = JSONArray.fromObject(string);
                        for(int i=0;i<index.size();i++) {
                            JSONObject obj = (JSONObject) index.opt(i);
                            Location location=new Location();
                            location.setName(obj.getString("name"));
                            location.setCell_phone_number(obj.getString("cell_phone_number"));
                            location.setArea(obj.getString("area"));
                            location.setAddress(obj.getString("address"));
                            location.setIsDefault(obj.getInt("isDefault"));
                            locations.add(location);
                        }
                    }
                    handler.sendEmptyMessage(2);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1&&resultCode==2){
            loadData();
        }
    }
}
