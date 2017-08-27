package com.example.administrator.view;

/**
 * Created by LiuTao on 2017/8/12 0012.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

/**
 * 自定义的小球
 */

public class MyView extends View {
    public MyView(Context context) {
        super(context);
        initView();
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    //创建画笔
    Paint paint = new Paint();
    //设置一个圆心的点
    Point point = new Point();
    int width;
    int height;

    int currentX;
    int currentY;

    //初始化数据
    void initView() {
        //对画笔的基本设置
        //设置抗锯齿
        paint.setAntiAlias(true);
        //设置防抖动
        paint.setDither(true);
        //设置颜色
        paint.setColor(Color.RED);
        //设置透明的
        paint.setAlpha(128);
        //设置线条粗细
        paint.setStrokeWidth(10);
        width = getResources().getDisplayMetrics().widthPixels;
        height = getResources().getDisplayMetrics().heightPixels;
        currentX = width / 2;
        currentY = height / 2;
        point.set(width / 2, height / 2);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制多条直线
        canvas.drawLines(new float[]{0, height / 2, width
                , height / 2, width / 2, 0, width / 2, height,}, paint);
        paint.setStyle(Paint.Style.FILL);//
        //绘制一个实心圆（默认情况）
        canvas.drawCircle(currentX, currentY, 50, paint);
        //画一个空心圆也设置画笔
        paint.setStyle(Paint.Style.STROKE);//
        canvas.drawCircle(width / 2, height / 2, width / 2, paint);
    }
    //当屏幕方向发生改变时，调用这个方法对小球进行重绘
    public   void initdata(int x, int y) {
        currentX = currentX-x;
        currentY = currentY-y;
        invalidate();//重绘
    }
}
