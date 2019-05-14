package com.example.shopling.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopling.R;
import com.example.shopling.activity.LocationSetActivity;
import com.example.shopling.adapter.CommentRVAdapter;
import com.example.shopling.model.Comment;
import com.example.shopling.model.Location;
import com.example.shopling.model.User;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
 * Created by hasee on 2019/4/23.
 */

public class CommodityPageFragment1 extends Fragment {
    private View view;
    private RecyclerView rvComment;
    private List<Comment> commentList,comments;
    private CommentRVAdapter commentRVAdapter;
    private TextView tvHint;
    private ScrollView svComment;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_commodity_page_1,container,false);
        initView();
        initData();
        initControl();
        return view;
    }

    private void initView() {
        rvComment=view.findViewById(R.id.rvComment);
        tvHint=view.findViewById(R.id.tvHint);
        svComment=view.findViewById(R.id.svComment);
    }

    private void initData() {
        commentList=new ArrayList<>();
        commentRVAdapter=new CommentRVAdapter(getActivity(),commentList);
    }

    private void initControl() {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        rvComment.setLayoutManager(linearLayoutManager);
        rvComment.setAdapter(commentRVAdapter);
        new MyAsyncTask().execute();
    }

    class MyAsyncTask extends AsyncTask<Void,Void,String> {
        @Override
        protected String doInBackground(Void... voids) {
            String result="";
            String path="http://111.230.32.36:8084/ShopLing/UserServlet";
            //1:创建OKHttpClient对象
            //设置超时时间
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .build();
            FormBody formBody = new FormBody.Builder()
                    .add("type", "commentGet")
                    .add("number",getArguments().getInt("number")+"")
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
            if(s.equals("")){
                Log.d("test4","................");
            }else{
                JSONArray index = JSONArray.fromObject(s);
                for(int i=0;i<index.size();i++) {
                    JSONObject obj = (JSONObject) index.opt(i);
                    Comment comment=new Comment();
                    comment.setUser_id(obj.getString("user_id"));
                    comment.setCommodity_number(obj.getInt("commodity_number"));
                    comment.setComment(obj.getString("comment"));
                    comment.setType(obj.getString("type"));
                    comment.setNumber(obj.getInt("number"));
                    comment.setPicture(obj.getString("picture"));
                    comment.setTime(obj.getString("time"));
                    comment.setTouxiang(obj.getString("touxiang"));
                    comment.setLike(obj.getInt("like"));
                    commentList.add(comment);
                }
                /*comments=LitePal.where("commodity_number=?",getArguments().getInt("number")+"").find(Comment.class);
                commentList.clear();
                commentList.addAll(comments);
                Log.d("test4",commentList.get(0).toString());*/
                if(commentList.isEmpty()){
                    tvHint.setVisibility(View.VISIBLE);
                    svComment.setVisibility(View.INVISIBLE);
                }else{
                    tvHint.setVisibility(View.INVISIBLE);
                    svComment.setVisibility(View.VISIBLE);
                }
                commentRVAdapter.notifyDataSetChanged();
            }
        }
    }
}
