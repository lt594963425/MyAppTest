package com.example.administrator;

import android.app.Application;
import android.content.Context;

import com.example.administrator.OkHttp.OkHttpUtils;
import com.example.administrator.OkHttp.https.HttpsUtils;
import com.example.administrator.OkHttp.log.LoggerInterceptor;
import com.example.administrator.persistentcookiejar.PersistentCookieJar;
import com.example.administrator.persistentcookiejar.cache.SetCookieCache;
import com.example.administrator.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.example.administrator.utils.ACache;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Fox on 2016/3/4.
 */
public class MyApplication extends Application {
    private static Context context;
    private static ACache mCache;

    @Override
    public void onCreate() {
        super.onCreate();
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);//https
        //持久化cookie
        PersistentCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(),new SharedPrefsCookiePersistor(getApplicationContext()));//coke
        //CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(getApplicationContext()));
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .cookieJar(cookieJar)
                .addInterceptor(new LoggerInterceptor("TAG"))
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);
        context = this;
        mCache = ACache.get(this);
    }

    public static Context getContext() {
        return context;
    }
    /*
 获得缓存类
  */
    public static ACache getAcache(){
        return mCache;
    }


}
