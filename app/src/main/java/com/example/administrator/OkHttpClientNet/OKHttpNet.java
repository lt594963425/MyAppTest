package com.example.administrator.OkHttpClientNet;

import android.util.Log;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 *
 * Created by LiuTao on 2017/7/24 0024.
 */

public class OKHttpNet {
    private static final String TAG = "OKHttpNet";
    private static String s = null;
    private static final OkHttpClient client = new OkHttpClient();
    public static String OkHttpNetGet(String url) {
        Request requst = new Request.Builder()
                .url(url)
                .get()//http://192.169.6.119/login/login/login/tel/15974255013/pwd/123456/code/wrty
                .build();
        client.newCall(requst).enqueue(new Callback() {

            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                s = e.toString();
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) {
                try {
                    s = response.body().string().trim();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
        Log.e(TAG,"结果OKHttpNet,s"+s);
        return s;
    }

}
