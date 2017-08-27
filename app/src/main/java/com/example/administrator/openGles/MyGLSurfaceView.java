package com.example.administrator.openGles;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

/**
 * Created by LiuTao on 2017/8/15 0015.
 */

public class MyGLSurfaceView extends GLSurfaceView {
    public MyGLSurfaceView(Context context) {
        super(context);
        setEGLConfigChooser(8, 8, 8, 8, 16, 0); // important
        this.setRenderer(new OpenGLRenderer(context)); // Use a custom renderer
    }

    public MyGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
