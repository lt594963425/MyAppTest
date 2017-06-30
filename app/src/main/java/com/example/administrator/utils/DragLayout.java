package com.example.administrator.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.nineoldandroids.view.ViewHelper;

/**
 * Created by 15111429213 on 2016/11/6.
 * --------日期 ---------维护人------------ 变更内容 --------
 * 2016/11/6		刘涛 				新增Person类
 * 2016/11/6		刘涛 				增加sex属性
 */
public class DragLayout extends FrameLayout {
    private ViewDragHelper dragHelper;
    private String TAG = "DragLayout";
    private ViewGroup menuContent;
    private ViewGroup mainContent;
    private int getWith;
    private int getHigt;
    private int mRange;
    private int newleft;

    public DragLayout(Context context) {
        this(context, null);
    }

    public DragLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //1，创建视图帮助类
        dragHelper = ViewDragHelper.create(this, 1.0f, callback);
    }
    public static  enum State{
         Close,Open,Draging
     }
    private State statue = State.Close;

    public State getStatue() {
        return statue;
    }

    public void setStatue(State statue) {
        this.statue = statue;
    }

    /**
     * 定义接口回掉，由用户处理
     * 1，打开状态 open
     * 2,关闭状态 close
     * 3,拖拽状态
     * 4,百分比
     * 5，根据percent的值判断关闭和开启状态
     */
    public interface onDragChangedListener{
        void onOpen();
        void onClose();
        void onGraging(float percent);
    }
    //定义接口变量
    private onDragChangedListener dragChangedListener;

    public onDragChangedListener getDragChangedListener() {
        return dragChangedListener;
    }

    public void setDragChangedListener(onDragChangedListener dragChangedListener) {
        this.dragChangedListener = dragChangedListener;
    }

    //图形辅助类
    ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {

        //1 决定那个孩子是否能被拖拽
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
        Log.i(TAG,"DragLayout,tryCaptureView,"+child);
            return true;
        }

        //2 修正子孩子的位置，返回的值决定孩子水平能移动到的位置
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            int odlleft = mainContent.getLeft();
            Log.i(TAG, "DragLayout,clampViewPositionHorizontal,left:" + left + "dx:" + dx + "odlleft:" + odlleft);

            if (child == mainContent) {
                left = getLeft(left);
            }
            return left;
        }

        //3 决定水平拖拽的范围,返回大于0的值
        @Override
        public int getViewHorizontalDragRange(View child) {
            return mRange;
        }

        /**被拖拽时位置的改变
         * 被拖拽子孩子位置改变的时候把左面板移动的距离 传递给主面板
         * 同时 播放伴随动画
         */
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            Log.i(TAG, "DragLayout,onViewPositionChanged,left:" + left + ",dx:" + dx);
            //如果是左面板的位置改变，把位置的变化量传递给主面板，同时侧面板也在缓慢移动
            if (changedView == menuContent) {
                //把左面板放回原来的位置
                menuContent.layout(0, 0, getWith, 0 + getHigt);

                //左边的位置加上左面板的增量dx
                newleft = mainContent.getLeft() + dx;
                newleft = getLeft(newleft);
                //修正主面板的位置
                mainContent.layout(newleft, 0, getWith + newleft, 0 + getHigt);
            }
            dragAnimation();
            invalidate();
        }

        /**
         *子孩子被释放的时候，就是松手的时候处理的事件
         * @param releasedChild
         * @param xvel
         * @param yvel
         */
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            //打开,1，是主面板滑动额速度为0时，左边的距离大于mRang/2 向左滑动，
            //    2，速度为正时，即向左边滑动
            if (xvel == 0 && mainContent.getLeft() > mRange / 2) {
                open();
                // dragHelper.smoothSlideViewTo(mainContent,mRange,0);
                // mainContent.layout(mRange,0,mRange+getWith,getHigt);
            } else if (xvel > 0) {
                open();
                // dragHelper.smoothSlideViewTo(mainContent,mRange,0);
                //mainContent.layout(mRange,0,mRange+getWith,getHigt);
            } else { //关闭
                close();
                //dragHelper.smoothSlideViewTo(mainContent,0,0);
                // mainContent.layout(0,0,getWith,getHigt);
            }

            invalidate();
        }

        /**
         * 拖拽状态改变的时候
         * @param state
         */
        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
        }


    };
private   float percent;
    /**
     * 拖拽时的伴随动画
     */
    private void dragAnimation() {
        //0.0-1.0
        percent = mainContent.getLeft()*1.0f/mRange;
        State newstatue= statue;
        statue = changeState(percent);
        Log.i(TAG,"DragLayout,dragAnimation,percent:"+percent);
        if(dragChangedListener!= null){
            dragChangedListener.onGraging(percent);
        }
        if (newstatue !=statue&&dragChangedListener != null) {
            if (statue == State.Open) {
                dragChangedListener.onOpen();
            } else if (statue == State.Close) {
                dragChangedListener.onClose();
            }
        }
        //左侧面板的伴随动画
        viewAnimation(percent);
    }

    private State changeState(float percent) {

        if(percent == 0){         //关闭状态
            return   statue = State.Close;


        }else  if (percent == 1) {//打开状态
           return statue = State.Open;

        }
        return State.Draging;
    }

    private void viewAnimation(float percent) {
        ViewHelper.setScaleX(menuContent,evaluate(percent,0.5f,1.0f));
        ViewHelper.setScaleY(menuContent,evaluate(percent,0.5f,1.0f));
        ViewHelper.setTranslationX(menuContent,evaluate(percent,-getWith/2,0f));
        //透明度动画
        ViewHelper.setAlpha(menuContent,evaluate(percent,0.2f,1.0f));
        //过渡色动画
        getBackground().setColorFilter((Integer) evaluateColor(percent, Color.BLACK,Color.TRANSPARENT), PorterDuff.Mode.SRC_OVER);
//        menuContent.setScaleX(percent/2+0.5f);
//        menuContent.setScaleY(percent/2+0.5f);
//        menuContent.setTranslationX(0);
    }

    /**
     * 估值器
     * @param fraction
     * @param startValue
     * @param endValue
     * @return
     */

    public Float evaluate(float fraction, Number startValue, Number endValue) {
        float startFloat = startValue.floatValue();
        return Float.valueOf(startFloat + fraction * (endValue.floatValue() - startFloat));
    }

    /**
     *颜色估值器
     * @param fraction
     * @param startValue
     * @param endValue
     * @return
     */
    public Object evaluateColor(float fraction, Object startValue, Object endValue) {
        int startInt = (Integer) startValue;
        int startA = (startInt >> 24) & 0xff;
        int startR = (startInt >> 16) & 0xff;
        int startG = (startInt >> 8) & 0xff;
        int startB = startInt & 0xff;

        int endInt = (Integer) endValue;
        int endA = (endInt >> 24) & 0xff;
        int endR = (endInt >> 16) & 0xff;
        int endG = (endInt >> 8) & 0xff;
        int endB = endInt & 0xff;

        return (int)((startA + (int)(fraction * (endA - startA))) << 24) |
                (int)((startR + (int)(fraction * (endR - startR))) << 16) |
                (int)((startG + (int)(fraction * (endG - startG))) << 8) |
                (int)((startB + (int)(fraction * (endB - startB))));
    }

    /**
     * 关闭左侧面板
     */
    public void close() {
        boolean smooth = dragHelper.smoothSlideViewTo(mainContent, 0, 0);
        if (smooth) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    /**
     * 打开左侧面板
     */
    public void open() {
        boolean smooth = dragHelper.smoothSlideViewTo(mainContent, mRange, 0);
        if (smooth) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    /**
     * 维持动画额持续调用
     */
    @Override
    public void computeScroll() {
        super.computeScroll();
        if (dragHelper.continueSettling(true)) {//是否需要继续画下一帧
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    /**
     * 位置修正值
     *
     * @param left
     * @return
     */
    private int getLeft(int left) {
        if (left > mRange) {
            return mRange;
        } else if (left < 0) {
            left = 0;
            return 0;
        }
        return left;
    }

    //当屏幕尺寸变化的时候调用
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        getWith = getMeasuredWidth();
        getHigt = getMeasuredHeight();
        mRange = (int) (getWith * 0.75f); //限定能拉动到左边的距离
    }

    //拦截事件
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
    // 由 ViewDragHelper 判断触摸事件是否该拦截
        return dragHelper.shouldInterceptTouchEvent(ev);
    }

    ;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //处理转交事件
            dragHelper.processTouchEvent(event);
            return true;


    }

    /**
     * 找控件的子孩子，View被添加到界面上的时候被调用
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() < 2) {
            throw new IllegalArgumentException("子孩子的数量少于2");
        } else if (!(getChildAt(0) instanceof ViewGroup) && !(getChildAt(1) instanceof ViewGroup)) {
            throw new IllegalArgumentException("子孩子类型不是ViewGroup");
        }
        menuContent = (ViewGroup) getChildAt(0);
        mainContent = (ViewGroup) getChildAt(1);
    }
}
