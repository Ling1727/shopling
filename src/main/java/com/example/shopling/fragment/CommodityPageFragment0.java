package com.example.shopling.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.shopling.R;
import com.example.shopling.model.CommodityOne;
import com.example.shopling.model.CommodityTwo;
import com.example.shopling.model.Picture;
import com.example.shopling.view.PictureShowView;
import org.litepal.LitePal;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by hasee on 2019/4/23.
 */

public class CommodityPageFragment0 extends Fragment {
    private View view;
    private RelativeLayout rlCommodity;
    private List<Picture> pictureList;
    private List<CommodityTwo> commodityTwoList;
    private String[] pictures;
    private int number;
    private ScrollView svCommodity;
    private TextView tvCommodityMoney;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_commodity_page_0,container,false);
        initView();
        initData();
        initControl();
        return view;
    }

    private void initView() {
        rlCommodity=view.findViewById(R.id.rlCommodity);
        svCommodity=view.findViewById(R.id.svCommodity);
        tvCommodityMoney=view.findViewById(R.id.tvCommodityMoney);
    }

    private void initData() {
        number=getArguments().getInt("number");
        pictureList=LitePal.select("path").where("commodity_number=?",number+"").find(Picture.class);
        commodityTwoList=LitePal.where("number=?",number+"").find(CommodityTwo.class);
        pictures=new String[pictureList.size()];
        for(int i=0;i<pictureList.size();i++){
            pictures[i]=pictureList.get(i).getPath();
        }
    }

    private void initControl() {
        tvCommodityMoney.setText("¥"+commodityTwoList.get(0).getPrice());
        DisplayMetrics outMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        int widthPixels = outMetrics.widthPixels;
        rlCommodity.addView(new PictureShowView(getActivity(),pictures,widthPixels));

    }


    public void loadData(){
        OkHttpClient okHttpClient=new OkHttpClient();
        final Request request=new Request.Builder().url("http://111.230.32.36:8084/ShopLing/CommodityServlet?index=three&num="+number).get().build();
        Call call=okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("test1","fail");
            }
            @Override
            public void onResponse(Call call, Response response)  {
                try {
                    InputStream is = response.body().byteStream();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
