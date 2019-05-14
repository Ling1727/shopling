package com.example.shopling.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.shopling.R;
import com.example.shopling.model.Comment;

import java.util.List;

/**
 * Created by hasee on 2019/5/10.
 */

public class CommentRVAdapter extends RecyclerView.Adapter<CommentRVAdapter.ViewHolder>implements View.OnClickListener{
    private List<Comment> commentList;
    private Context context;

    public CommentRVAdapter(Context context,List<Comment> commentList){
        this.context=context;
        this.commentList=commentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_commodity_page_1_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("test4",commentList.get(position).toString());
        RequestOptions options=new RequestOptions()
                .placeholder(R.drawable.test001)
                .error(R.drawable.error);
        holder.tvName.setText(commentList.get(position).getUser_id());
        Glide.with(context).load(commentList.get(position).getTouxiang()).apply(options).into(holder.ivTouXiang);
        holder.ivPicture.setVisibility(commentList.get(position).getPicture().equals("")?View.GONE:View.VISIBLE);
        Glide.with(context).load(commentList.get(position).getPicture()).apply(options).into(holder.ivPicture);
        holder.tvPingJia.setText(commentList.get(position).getComment());
        holder.tvTime.setText(commentList.get(position).getTime().substring(0,11));
        holder.tvLike.setText(String.valueOf(commentList.get(position).getLike()));
       // holder.tvComment.setText(commentList.get(position).getTime());
        holder.ratingBar.setRating(commentList.get(position).getNumber());
        holder.ivComment.setOnClickListener(this);
        holder.ivLike.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivComment:
                break;
            case R.id.ivLike:
                break;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvName,tvTime,tvPingJia,tvLike,tvComment;
        private RatingBar ratingBar;
        private ImageView ivTouXiang,ivLike,ivComment,ivPicture;
        public ViewHolder(View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tvName);
            ratingBar=itemView.findViewById(R.id.ratingBar);
            ivTouXiang=itemView.findViewById(R.id.ivTouXiang);
            tvTime=itemView.findViewById(R.id.tvTime);
            tvPingJia=itemView.findViewById(R.id.tvPingJia);
            ivComment=itemView.findViewById(R.id.ivComment);
            ivLike=itemView.findViewById(R.id.ivLike);
            ivPicture=itemView.findViewById(R.id.ivPicture);
            tvLike=itemView.findViewById(R.id.tvLike);
            tvComment=itemView.findViewById(R.id.tvComment);
        }
    }
}
