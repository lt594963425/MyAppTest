package com.example.administrator.retrofit;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.R;
import com.example.administrator.base.BaseActivity;


/**
 * ActivityRetrofit
 * Created by 刘涛 on 2017/6/30 0030.
 */

public class ActivityRetrofit extends BaseActivity implements View.OnClickListener {
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
        setBarBack();

    }

    @Override
    public void onClick(View v) {
        String phone = et.getText().toString().trim();
        retrofit retrofit = new retrofit(phone);
        retrofit.retrofitNet(BASE_URL, new retrofit.RetrofitListener() {
            @Override
            public void retrofitSucces(String s) {
                tvOk.setText(s);
            }
            @Override
            public void retrofitFailure(String s) {
                tvError.setText(s);
            }
        });
    }


}
