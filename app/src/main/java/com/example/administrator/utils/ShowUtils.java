package com.example.administrator.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by liuTao on 2017/7/21 0021.
 */

public class ShowUtils {
    //这里DEBUG的作用是，可以在程序完成后设置DEBUG的值为false，程序以后就不会在显示以前的打印信息
    public static  boolean DEBUG = true;

    //各种Log打印
    public static void e(Object o) {
        if (DEBUG)
            Log.e("TAG", "打印：------      " + o.toString());
    }

    public static void e(int i) {
        if (DEBUG)
            Log.e("TAG", "打印：------      " + i);
    }

    public static void e(float i) {
        if (DEBUG)
            Log.e("TAG", "打印：------      " + i);
    }

    public static void e(boolean b) {
        if (DEBUG)
            Log.e("TAG", "打印：------      " + b);
    }

    //各种土司
    public static void ts(Context context, Object object) {
        if (DEBUG)
            Toast.makeText(context, object + "", Toast.LENGTH_SHORT).show();
    }

    public static void tsl(Context context, Object object) {
        if (DEBUG)
            Toast.makeText(context, object + "", Toast.LENGTH_LONG).show();
    }
}
