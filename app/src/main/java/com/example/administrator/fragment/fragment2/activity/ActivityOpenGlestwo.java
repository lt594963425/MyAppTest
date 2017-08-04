package com.example.administrator.fragment.fragment2.activity;

import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.example.administrator.base.BaseActivity;
import com.example.administrator.openGles.OpenGLRenderer;

import static android.opengl.GLSurfaceView.RENDERMODE_WHEN_DIRTY;

/**
 *它使用OpenGL显示了一个灰色的屏幕。
 * Created by LiuTao on 2017/8/3 0003.
 *
 */

public class ActivityOpenGlestwo extends BaseActivity {

    //private MyGLESSureface myGLESSureface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GLSurfaceView view = new GLSurfaceView(this);
        view.setRenderer(new OpenGLRenderer());
        view. setRenderMode(RENDERMODE_WHEN_DIRTY);
        setContentView(view);
    }

//  class MyGLESSureface extends GLSurfaceView {
//        public MyGLESSureface(Context context) {
//            super(context);
//            //当使用OpenGLES 2.0时，你必须在GLSurfaceView构造器中调用另外一个函数，它说明了你将要使用2.0版的API：
//            // 创建一个OpenGL ES 2.0 context
//
//
//            //另一个可以添加的你的GLSurfaceView实现的可选的操作是设置render模式为只在绘制数据发生改变时才绘制view。使用GLSurfaceView.RENDERMODE_WHEN_DIRTY：
//            // 只有在绘制数据改变时才绘制view  此设置会阻止绘制GLSurfaceView的帧，直到你调用了requestRender()，这样会非常高效。
//
//        }
//
//        public MyGLESSureface(Context context, AttributeSet attrs) {
//            super(context, attrs);
//        }
//    }
}
