package com.example.administrator.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;


/**
 * Created by 刘涛 on 2017/6/13 0013.
 */
public class FocusTextview extends TextView {


    public FocusTextview(Context con) {
        super(con);
    }

    public FocusTextview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FocusTextview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean isFocused() {
        return true;
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction,
                                  Rect previouslyFocusedRect) {
    }
}
