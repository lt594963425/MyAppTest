package com.example.administrator.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.media.RatingCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.administrator.R;
import java.lang.reflect.Field;



/**
 * 标题居中
 * Created by 刘涛 on 2017/6/13 0013.
 */

public class XToolbar extends Toolbar {
    private TextView mTitleTextView;
    private CharSequence mTitle;
    private int mTitleColor;
    private int mTitleAppearance;

    public XToolbar(Context context) {
        this(context, null);
    }

    public XToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        resolveAttribute(context, attrs, defStyleAttr);
    }

    private void resolveAttribute(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        final TintTypedArray a = TintTypedArray.obtainStyledAttributes(context, attrs,
                R.styleable.Toolbar, defStyleAttr, 0);
        mTitleAppearance = a.getResourceId(R.styleable.Toolbar_titleTextAppearance, 0);
        a.recycle();
    }

    @Override
    public CharSequence getTitle() {
        return mTitle;
    }

    @Override
    public void setTitle(CharSequence title) {
        if (!TextUtils.isEmpty(title)) {
            if (mTitleTextView == null) {
                final Context context = getContext();
                mTitleTextView = new TextView(context);
                mTitleTextView.setSingleLine();
                mTitleTextView.setEllipsize(TextUtils.TruncateAt.END);
                if (mTitleAppearance != 0)
                    mTitleTextView.setTextAppearance(context, mTitleAppearance);
                if (mTitleColor != 0) mTitleTextView.setTextColor(mTitleColor);
            }
            if (mTitleTextView.getParent() != this) addCenterView(mTitleTextView);
            mTitleTextView.setText(title);
        } else if (mTitleTextView != null && mTitleTextView.getParent() == this) removeView(mTitleTextView);
        mTitle = title;
    }

    private void addCenterView(View v) {
        final ViewGroup.LayoutParams vlp = v.getLayoutParams();
        final LayoutParams lp;
        if (vlp == null) {
            lp = generateDefaultLayoutParams();
        } else if (!checkLayoutParams(vlp)) {
            lp = generateLayoutParams(vlp);
        } else {
            lp = (LayoutParams) vlp;
        }
        addView(v, lp);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        LayoutParams lp = new LayoutParams(getContext(), attrs);
        lp.gravity = Gravity.CENTER;
        return lp;
    }

    @Override
    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        LayoutParams lp;
        if (p instanceof LayoutParams) {
            lp = new LayoutParams((LayoutParams) p);
        } else if (p instanceof ActionBar.LayoutParams) {
            lp = new LayoutParams((ActionBar.LayoutParams) p);
        } else if (p instanceof MarginLayoutParams) {
            lp = new LayoutParams((MarginLayoutParams) p);
        } else {
            lp = new LayoutParams(p);
        }
        lp.gravity = Gravity.CENTER;
        return lp;
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        return lp;
    }

    @Override
    public void setTitleTextAppearance(Context context, @RatingCompat.Style int resId) {
        mTitleAppearance = resId;
        if (mTitleTextView != null) mTitleTextView.setTextAppearance(context, resId);
    }

    @Override
    public void setTitleTextColor(@ColorInt int color) {
        mTitleColor = color;
        if (mTitleTextView != null) mTitleTextView.setTextColor(color);
    }

    @Override
    public void setNavigationIcon(@Nullable Drawable icon) {
        super.setNavigationIcon(icon);
        setGravityCenter();
    }

    public void setGravityCenter() {
        post(new Runnable() {
            @Override
            public void run() {
                setCenter("mNavButtonView");
//                setCenter("mMenuView");
            }

        });
    }

    private void setCenter(String fieldName) {
        try {
            Field field = getClass().getSuperclass().getDeclaredField(fieldName);//反射得到父类Field
            field.setAccessible(true);
            Object obj = field.get(this);//拿到对应的Object
            if (obj == null) return;
            if (obj instanceof View) {
                View view = (View) obj;
                ViewGroup.LayoutParams lp = view.getLayoutParams();//拿到LayoutParams
                if (lp instanceof ActionBar.LayoutParams) {
                    ActionBar.LayoutParams params = (ActionBar.LayoutParams) lp;
                    params.gravity = Gravity.CENTER;//设置居中
                    view.setLayoutParams(lp);
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
