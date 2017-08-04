package com.example.administrator.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 15111429213 on 2016/11/3.
 * --------日期 ---------维护人------------ 变更内容 --------
 * 2016/11/3		刘涛 				新增Person类
 * 2016/11/3		刘涛 				增加sex属性
 */
public class RefreshListView extends ListView implements AbsListView.OnScrollListener {

    //定义接口的成员变量
    private onRefreshListener onefreshlistener;
    private int downY;//按下时的偏移量
    private String TAG;
    private View headView; //头布局对象
    private ImageView ivArrow;
    private ProgressBar mProgressBar;
    private TextView tvState;
    private TextView tvLastUpdateTime;
    //头布局
    private int headViewHeight;
    //动画
    private RotateAnimation upAnimation;
    private RotateAnimation downAnimation;
    private static final int XIALA_SHUA = 1;
    private static final int SHUAING = 2;
    private static final int FANG_KAI = 3;
    private int stateArrow = XIALA_SHUA;
    private boolean isLoadingMore = false;
    private View footerView;
    private int footerViewHeight;

    public RefreshListView(Context context) {
        super(context);
        initHeaderView();
        initFooterView();
        setOnScrollListener(this);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeaderView();
        initFooterView();
        setOnScrollListener(this);
    }

    /**
     * 初始化脚布局
     */
    private void initFooterView() {
        footerView = View.inflate(getContext(), R.layout.list_addfoot, null);

        // 设置脚布局的paddingTop为自己高度的负数
        footerView.measure(0, 0);
        footerViewHeight = footerView.getMeasuredHeight();
        footerView.setPadding(0, -footerViewHeight, 0, 0);

        this.addFooterView(footerView);
    }

    private void initHeaderView() {
        //找控件
        headView = View.inflate(getContext(), R.layout.list_addbefore, null);
        ivArrow = (ImageView) headView.findViewById(R.id.iv_listview_header_arrow);
        mProgressBar = (ProgressBar) headView.findViewById(R.id.pb_listview_header);
        tvState = (TextView) headView.findViewById(R.id.tv_listview_header_state);
        tvLastUpdateTime = (TextView) headView.findViewById(R.id.tv_listview_header_last_update_time);
        tvLastUpdateTime.setText("最后刷新时间: " + getCurrentTime());//获取最新时间
        //测量控件的高度和宽度
        headView.measure(0, 0);
        // headViewHeight = headView.getHeight();//得到头布局的高度,得到的结果是0
        headViewHeight = headView.getMeasuredHeight();
        Log.i(TAG, "RefreshListView,initAnimation," + headViewHeight);
        headView.setPadding(0, -headViewHeight, 0, 0);//设置头布局的位置
        this.addHeaderView(headView);
        initAnimation();
    }

    /**
     * 初始化动画
     */
    private void initAnimation() {
        //下拉动画
        upAnimation = new RotateAnimation(
                0, -180,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        upAnimation.setDuration(500);
        upAnimation.setFillAfter(true); // 让控件停止在动画结束的状态下
        //下拉刷新动画
        downAnimation = new RotateAnimation(
                -180, -360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        downAnimation.setDuration(500);
        downAnimation.setFillAfter(true); // 让控件停止在动画结束的状态下
    }

    /**
     * 触摸事件
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: //按下
                downY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE://移动
                int moveY = (int) ev.getY();
                int datelY = moveY - downY;
                int firstVisiblePosition = getFirstVisiblePosition();//得到第一个索引得位置2
                int newposition = -headViewHeight + datelY;
                if (newposition > 50) {
                    newposition = 50;
                }
                if (newposition > -headViewHeight && firstVisiblePosition == 0) {
                    if (newposition < 0 && stateArrow == FANG_KAI) {//下拉刷新
                        stateArrow = XIALA_SHUA;
                        refreshHeaderViewState();
                    } else if (newposition > 0 && stateArrow == XIALA_SHUA) {//松开刷新
                        stateArrow = FANG_KAI;
                        refreshHeaderViewState();
                    }
                    Log.i(TAG, "RefreshListView,onTouchEvent,下拉高度：" + newposition);
                    headView.setPadding(0, newposition, 0, 0);//设置头布局的位置
                }

                break;
            case MotionEvent.ACTION_UP://抬起
                // 判断当前的状态是哪一种
                if (stateArrow == XIALA_SHUA) { // 当前是在下拉刷新状态下松开了, 什么都不做, 把头布局隐藏就可以.
                    headView.setPadding(0, -headViewHeight, 0, 0);
                } else if (stateArrow == FANG_KAI) { // 当前的状态属于释放刷新, 并且松开了. 应该把头布局正常显示, 进入正在刷新中状态.
                    headView.setPadding(0, 0, 0, 0);
                    stateArrow = SHUAING;
                    refreshHeaderViewState();
                    // 调用用户的监听事件.
                    if (onefreshlistener != null) {
                        onefreshlistener.onPullDownRefresh();
                    }
                }
                break;
        }

        return super.onTouchEvent(ev);

    }

    /**
     * 根据当前的状态currentState来刷新头布局的状态.
     */
    private void refreshHeaderViewState() {
        switch (stateArrow) {
            case XIALA_SHUA: // 下拉刷新
                ivArrow.startAnimation(downAnimation);
                tvState.setText("下拉刷新");
                break;
            case FANG_KAI: // 松开刷新
                ivArrow.startAnimation(upAnimation);
                tvState.setText("松开刷新");
                break;
            case SHUAING: // 正在刷新中
                ivArrow.clearAnimation(); // 把自己身上的动画清除掉
                ivArrow.setVisibility(View.INVISIBLE);
                mProgressBar.setVisibility(View.VISIBLE);
                tvState.setText("正在刷新..");
                break;
            default:
                break;
        }
    }

    public void setOnefreshlistener(onRefreshListener onefreshlistener) {
        this.onefreshlistener = onefreshlistener;
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // 当前的状态是停止, 并且屏幕上显示的最后一个条目的索引是ListView中总条目个数 -1;
//		System.out.println("scrollState: " + scrollState + ", last: " + getLastVisiblePosition() + ", count: " + getCount());
        if ((scrollState == OnScrollListener.SCROLL_STATE_IDLE    // 当前是停滞或者是快速滑动时
                || scrollState == OnScrollListener.SCROLL_STATE_FLING)
                && getLastVisiblePosition() == (getCount() - 1)
                && !isLoadingMore) {
            System.out.println("滑动到底部, 可以加载更多数据了.");

            isLoadingMore = true;
            footerView.setPadding(0, 0, 0, 0);
            setSelection(getCount()); // 滑动到最底部

            if (onefreshlistener != null) {
                onefreshlistener.onLoadingMore();
            }
        }
    }


    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    public interface onRefreshListener {
        /**
         * 下拉时调用此方法
         */
        public void onPullDownRefresh();

        /**
         * 当加载更多时调用此方法
         */
        public void onLoadingMore();
    }

    /**
     * 刷新完成, 用户调用此方法, 把对应的头布局恢复为默认状态
     */
    public void onRefreshFinish() {
        if (isLoadingMore) { // 当前属于加载更多中
            // 隐藏脚布局
            footerView.setPadding(0, -footerViewHeight, 0, 0);
            isLoadingMore = false;
        } else {
            // 下拉刷新操作
            // 隐藏头布局
            headView.setPadding(0, -headViewHeight, 0, 0);
            stateArrow = XIALA_SHUA;
            mProgressBar.setVisibility(View.INVISIBLE);
            ivArrow.setVisibility(View.VISIBLE);
            tvState.setText("下拉刷新");
            tvLastUpdateTime.setText("上次刷新时间: " + getCurrentTime());
        }
    }

    /**
     * 获取最新的时间
     *
     * @return 1990-09-09 09:09:09
     */
    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
}
