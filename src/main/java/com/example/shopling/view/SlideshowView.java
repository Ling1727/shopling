package com.example.shopling.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.example.shopling.R;
import java.util.ArrayList;
import java.util.List;

public class SlideshowView extends RelativeLayout {
    private ViewPager viewPager;
    private int[] imageID;
    private List<ImageView> imageViews;
    private LinearLayout linearLayout;
    private List<View> point;
    private View pointView;
    private Boolean isRun=false;
    private Context context;
    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if(msg.what==0){
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
            }
            return false;
        }
    }) ;

    public SlideshowView(Context context) {
        super(context);
        this.context=context;
        LayoutInflater.from(context).inflate(R.layout.view_slideshow,this,true);
        initView();
        initData();
        initControl();
    }

    public SlideshowView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlideshowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private void initView() {
        viewPager=findViewById(R.id.viewPager);
        linearLayout=findViewById(R.id.linearLayout);
    }

    private void initData() {
        imageID=new int[]{R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d,R.drawable.e};
        imageViews=new ArrayList<>();
        point=new ArrayList<>();
        LinearLayout.LayoutParams layoutParams;
        for(int i=0;i<imageID.length;i++){
            ImageView imageView=new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(imageID[i]);
            imageViews.add(imageView);
            pointView=new View(context);
            pointView.setBackgroundResource(R.drawable.dot);
            layoutParams=new LinearLayout.LayoutParams(20,20);
            if(i!=0){
                layoutParams.leftMargin=30;
                pointView.setEnabled(false);
            }else{
                pointView.setEnabled(true);
            }
            pointView.setLayoutParams(layoutParams);
            point.add(pointView);
            linearLayout.addView(pointView);
        }
    }

    private void initControl() {
        viewPager.setAdapter(new MyAdapter(imageViews));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int pos=position%5;
                for(int i=0;i<imageViews.size();i++){
                    if(i==pos){
                        point.get(i).setEnabled(true);
                    }else{
                        point.get(i).setEnabled(false);
                    }
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(Integer.MAX_VALUE/2-3);
    }

    public void start(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRun){
                    try {
                        Thread.sleep(3000);
                        handler.sendEmptyMessage(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    /*// 1. 声明接口对象
    public interface OnSwitchStateUpdateListener{
        // 状态回调, 把当前状态传出去
        void onStateUpdate(boolean state);
    }
    // 2. 添加设置接口对象的方法, 外部进行调用
    public void setOnSwitchStateUpdateListener(
            OnSwitchStateUpdateListener onSwitchStateUpdateListener) {
        this.onSwitchStateUpdateListener = onSwitchStateUpdateListener;
    }
    // 3. 在合适的位置.执行接口的方法
	onSwitchStateUpdateListener.onStateUpdate(state);*/



    public void setRun(Boolean run) {
        isRun = run;
    }


    class MyAdapter extends PagerAdapter{
        List<ImageView> imageViews;
        public MyAdapter( List<ImageView> imageViews) {
            this.imageViews=imageViews;
        }
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            int pos=position%5;
            container.addView(imageViews.get(pos));
            return imageViews.get(pos);
        }
        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view==object;
        }
    }

}
