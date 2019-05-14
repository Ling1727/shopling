package com.example.shopling.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.shopling.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasee on 2019/4/23.
 */

public class PictureShowView extends ViewPager {
    private String[] pictures;
    private Context context;
    private List<ImageView> imageViewList;
    int width;

    public PictureShowView(@NonNull Context context,String[] pictures,int width) {
        super(context);
        this.context=context;
        this.pictures=pictures;
        this.width=width;
        initView();
    }

    public PictureShowView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(width, heightMeasureSpec); //设置测量尺寸,将高和宽放进去
    }

    public void initView(){
        imageViewList=new ArrayList<>();
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.test001)
                .error(R.drawable.error);
        for(int i=0;i<pictures.length;i++){
            ImageView imageView=new ImageView(context);
            ViewGroup.LayoutParams layoutParams=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(layoutParams);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(context).load(pictures[i]).apply(options).into(imageView);
            imageViewList.add(imageView);
        }
        this.setAdapter(new ViewPagerAdapter());

    }

    class ViewPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return imageViewList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view==object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(imageViewList.get(position),0);
            return imageViewList.get(position);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(imageViewList.get(position));
        }
    }
}
