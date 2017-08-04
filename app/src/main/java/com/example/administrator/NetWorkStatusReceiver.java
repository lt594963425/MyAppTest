package com.example.administrator;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

import com.example.administrator.base.BaseActivity;
import com.example.administrator.utils.NetWorkUtils;

/**
 * Created by LiuTao on 2017/7/28 0028.
 */

public class NetWorkStatusReceiver extends BroadcastReceiver {

    public NetWorkStatusReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            Toast.makeText(context, "network changed", Toast.LENGTH_LONG).show();
            BaseActivity.isNetWorkConnected = NetWorkUtils.getAPNType(context)>0;
        }
    }
}