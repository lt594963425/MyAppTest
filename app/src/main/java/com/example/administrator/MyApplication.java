package com.example.administrator;

import android.content.Context;

import com.mob.MobApplication;

/**
 * Created by Fox on 2016/3/4.
 */
public class MyApplication extends MobApplication {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
    public static Context getContext(){
        return context;
    }
}
