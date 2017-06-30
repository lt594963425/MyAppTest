package com.example.administrator.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Alpha on 2016/4/16.
 */
public interface LocationAPI {
    // 指定url的时候一定是以"/"开始
    @GET("/mobile/get")
    public Call<LocationModel> getLocation(@Query("phone") String num, @Query("key") String key);

}
