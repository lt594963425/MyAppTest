package com.example.administrator.retrofit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by LiuTao on 2017/7/24 0024.
 */

public class retrofit {
    OkHttpClient client = new OkHttpClient();
    String str;
    public retrofit(String str) {
        this.str =str;
    }
    public void retrofitNet(String url,RetrofitListener listener) {

        // 构建 Retrofit实例
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // 构建接口的实例
        LocationAPI locationAPI = retrofit.create(LocationAPI.class);

        Call<LocationModel> call = locationAPI.getLocation(str, "daf8fa858c330b22e342c882bcbac622");
        // call.execute();// 同步执行网络请求
        call.enqueue(new Callback<LocationModel>() {// 异步执行网络请求,而且可以直接操作UI线程
            @Override
            public void onResponse(Call<LocationModel> call, Response<LocationModel> response) {
                LocationModel body = response.body();
                String s =body.getResult().getCity();
                listener.retrofitSucces(s);
            }
            @Override
            public void onFailure(Call<LocationModel> call, Throwable t) {
                String s =t.toString();
                listener.retrofitFailure(s);
            }
        });
    }
    public interface RetrofitListener {
        public void retrofitSucces(String s);
        public void retrofitFailure(String s);
    }
}
