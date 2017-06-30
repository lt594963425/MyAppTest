package com.example.administrator.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by 15111429213 on 2016/11/8.
 * --------日期 ---------维护人------------ 变更内容 --------
 * 2016/11/8			刘涛 				新增Person类
 * 2016/11/8			刘涛 				增加sex属性
 */
public class MyLinnerLayout extends LinearLayout {
    private DragLayout dragLayout;
    public MyLinnerLayout(Context context) {
        super(context);
    }

    public MyLinnerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinnerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 事件拦截
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        //如果是打开状态 拦截 不往下传递
        if (dragLayout!=null &&dragLayout.getStatue() != DragLayout.State.Close) {
            return true;
        }
        //是关闭状态 往下传递
        else {
            return super.onInterceptTouchEvent(ev);
        }
    }

    /**
     * 触摸事件
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(dragLayout != null && dragLayout.getStatue() != DragLayout.State.Close){
            // 如果手指抬起, 执行关闭动画
            if(event.getAction() == MotionEvent.ACTION_UP){
                dragLayout.close();
            }
            return true;
        } else {
            return super.onTouchEvent(event);
        }
    }

    public void setDragLayout(DragLayout dragLayout) {
        this.dragLayout = dragLayout;
    }
}
