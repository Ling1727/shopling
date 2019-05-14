package com.example.shopling.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;


/**
 * Created by hasee on 2019/4/9.
 */

public class ShadeRelativeLayout extends RelativeLayout {
    private Paint paint,paint1;
    public ShadeRelativeLayout(Context context) {
        super(context);
        initData();
    }

    public ShadeRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.d("test0","2");
        setWillNotDraw(false);

    }

    public ShadeRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        initData();
    }

    public void initData(){
        paint = new Paint();
        paint.setColor(Color.GREEN);
        LinearGradient linearGradient = new LinearGradient(0, 0, 0, getMeasuredHeight(),new int[]{ Color.RED,Color.parseColor("#FF6A6A")}, null, LinearGradient.TileMode.CLAMP);
        paint.setShader(linearGradient);
        /*paint1 = new Paint();
        paint1.setColor(Color.GREEN);
        LinearGradient linearGradient1 = new LinearGradient(0, 0, getMeasuredWidth(),0,new int[]{Color.RED, Color.parseColor("#FF6A6A")}, null, LinearGradient.TileMode.CLAMP);
        paint1.setShader(linearGradient1);*/

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("test0",getMeasuredHeight()+"...."+getMeasuredWidth());
        //canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight()*3/5,paint1);
        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),paint);
    }
}
