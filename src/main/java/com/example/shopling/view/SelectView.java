package com.example.shopling.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import com.example.shopling.R;

/**
 * Created by hasee on 2019/4/24.
 */

public class SelectView extends View implements View.OnClickListener{
    private boolean isSelect=false;
    private OnClickListener onClickListener;
    private Paint paint;
    private int width,height;
    public SelectView(Context context) {
        super(context);
        initView();
    }

    public SelectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SelectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void initView(){
        this.setOnClickListener(this);
        this.setBackgroundResource(isSelect? R.drawable.circular_select:R.drawable.circular_unselect);
        //实例化画笔对象
        paint = new Paint();
        //给画笔设置颜色
        paint.setColor(Color.parseColor("#F5F5F5"));
        //设置画笔属性
        paint.setStyle(Paint.Style.FILL);//画笔属性是实心圆
        //paint.setStyle(Paint.Style.STROKE);//画笔属性是空心圆
        paint.setStrokeWidth(2);//设置画笔粗细
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width=getMeasuredWidth();
        height=getMeasuredHeight();
        //7:2
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(isSelect){
            canvas.drawCircle(width-70, height/ 2, height/3, paint);
        }else{
            canvas.drawCircle(70, height/ 2, height/3, paint);
        }
    }

    @Override
    public void onClick(View v) {
        isSelect=isSelect?false:true;
        if(isSelect){
            this.setBackgroundResource( R.drawable.circular_select);
            paint.setColor(Color.RED);
        }else{
            this.setBackgroundResource(R.drawable.circular_unselect);
            paint.setColor(Color.parseColor("#F5F5F5"));
        }
        onClickListener.onClick(isSelect);
    }

    public interface OnClickListener{
        public void onClick(boolean isSelect);
    }

    public void setOnClickListener(OnClickListener onClickListener){
        this.onClickListener=onClickListener;
    }
}
