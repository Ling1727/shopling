package com.example.shopling.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.shopling.R;

/**
 * Created by hasee on 2019/4/8.
 */

public class LVClassifyAdapter extends BaseAdapter {
    private Context context;
    private String[] strings;
    private int mSelect=0;
    public LVClassifyAdapter(Context context,String[] strings ) {
        super();
        this.context=context;
        this.strings=strings;
    }

    public void changeSelected(int positon){ //刷新方法
        if(positon != mSelect){
            mSelect = positon;
            notifyDataSetChanged();
        }
    }
    @Override
    public int getCount() {
        return strings.length;
    }

    @Override
    public Object getItem(int position) {
        return "第"+position+"项";
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView=LayoutInflater.from(context).inflate(R.layout.feagment_classify_item_0,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.tvClassify=convertView.findViewById(R.id.tvClassify);
            viewHolder.viewClassify=convertView.findViewById(R.id.viewClassify);
            viewHolder.rlClassify=convertView.findViewById(R.id.rlClassify);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder) convertView.getTag();
        }
        viewHolder.tvClassify.setText(strings[position]);
        if(mSelect==position){
            viewHolder.tvClassify.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            viewHolder.tvClassify.setTextSize(18);
            viewHolder.viewClassify.setVisibility(View.VISIBLE);
            viewHolder.rlClassify.setBackgroundResource(R.color.white);
        }else{
            viewHolder.tvClassify.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            viewHolder.tvClassify.setTextSize(15);
            viewHolder.viewClassify.setVisibility(View.INVISIBLE);
            viewHolder.rlClassify.setBackgroundResource(R.color.color1);
        }
        return convertView;
    }

    public void setIndex(int index) {
        this.mSelect = index;
    }

    class ViewHolder{
        private TextView tvClassify;
        private View viewClassify;
        private RelativeLayout rlClassify;
    }
}
