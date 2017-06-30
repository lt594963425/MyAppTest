package com.example.administrator.utils;

import android.view.Gravity;
import android.widget.Toast;

import com.example.administrator.MyApplication;


/**
 * toast封装，还可以改进
 * Created by 刘涛 on 2017/5/27 0027.
 */

public class ToastUtils {
    private static Toast toast;

    public static void showToast(String msg) {
        if (toast == null) {
            toast = Toast.makeText(MyApplication.getContext(), msg, Toast.LENGTH_SHORT);
        }
        toast.setText(msg);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        toast.show();
    }

    public static void showToast(int resId) {
        if (toast == null) {
            toast = Toast.makeText(MyApplication.getContext(), resId, Toast.LENGTH_SHORT);
        }
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setText(resId);
        toast.show();
    }
}
