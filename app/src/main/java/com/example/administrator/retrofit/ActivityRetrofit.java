package com.example.administrator.retrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.fragmenttabhost.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 刘涛 on 2017/6/30 0030.
 */

public class ActivityRetrofit extends AppCompatActivity implements View.OnClickListener {
    private static final String BASE_URL = "http://apis.juhe.cn";

    private Button button;
    private TextView tvOk;
    private TextView tvError;
    private EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        et = (EditText) findViewById(R.id.et_reinput);
        button = (Button) findViewById(R.id.button);
        tvOk = (TextView) findViewById(R.id.tv_ok);
        tvError = (TextView) findViewById(R.id.tv_error);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String phone =et.getText().toString().trim();
        // 构建 Retrofit实例
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // 构建接口的实例
        LocationAPI locationAPI = retrofit.create(LocationAPI.class);
        Call<LocationModel> call = locationAPI.getLocation(phone, "daf8fa858c330b22e342c882bcbac622");
        // call.execute();// 同步执行网络请求
        call.enqueue(new Callback<LocationModel>() {// 异步执行网络请求,而且可以直接操作UI线程
            @Override
            public void onResponse(Call<LocationModel> call, Response<LocationModel> response) {
                LocationModel body = response.body();
                tvOk.setText(body.getResult().getCity());
            }

            @Override
            public void onFailure(Call<LocationModel> call, Throwable t) {
                tvError.setText(t.toString());
            }
        });
    }

}
