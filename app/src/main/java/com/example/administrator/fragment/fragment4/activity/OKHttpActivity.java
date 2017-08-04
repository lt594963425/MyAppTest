package com.example.administrator.fragment.fragment4.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.R;
import com.example.administrator.base.BaseActivity;

import java.io.IOException;

import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by 刘涛 on 2017/6/30 0030.
 */

public class OKHttpActivity extends BaseActivity implements View.OnClickListener {
    public static final String URL_GET = "http://apis.juhe.cn/mobile/get?phone=13812345678&key=daf8fa858c330b22e342c882bcbac622";
    public static final String URL_POST = "http://apis.juhe.cn/mobile/get ";

    private Button btnGet;
    private Button btnPost;
    private TextView tvOK;
    private TextView tvError;
    private OkHttpClient client;
private EditText etInput;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    String error = (String) msg.obj;
                    tvError.setText(error);

                    break;
                case 1:
                    String result = (String) msg.obj;
                    tvOK.setText(result);

                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);
        ButterKnife.bind(this);
        setTitle("网络请求");
        setBarBack();
        btnGet = (Button) findViewById(R.id.btn_get);
        btnPost = (Button) findViewById(R.id.btn_post);
        tvOK = (TextView) findViewById(R.id.tv_OK);
        tvError = (TextView) findViewById(R.id.tv_error);
        etInput = (EditText) findViewById(R.id.et_input);
        btnGet.setOnClickListener(this);
        btnPost.setOnClickListener(this);
        client = new OkHttpClient();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_get:
                //默认情况下Request就是使用GET方式,所以不需要指定请求的方式
                Request get_request = new Request.Builder()
                        .url(URL_GET)// 指定请求的地址
                        .build();
                // 同步执行网络请求
                // client.newCall(get_request).execute()
                // 异步执行网络请求
                client.newCall(get_request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Message msg = new Message();
                        msg.what = 0;
                        msg.obj = e.toString();
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        ResponseBody body = response.body();
                        String string = body.string();// 把返回的结果转换为String类型
                        Message msg = new Message();
                        msg.what = 1;
                        msg.obj = string;
                        handler.sendMessage(msg);
//                        body.bytes();// 把返回的结果转换为byte数组
//                        body.byteStream();// 把返回的结果转换为流
                    }
                });

                break;
            case R.id.btn_post:
                String phone = etInput.getText().toString().trim();
                RequestBody body = new FormBody.Builder()
                        .add("phone", phone)// 构造请求的参数
                        .add("key", "daf8fa858c330b22e342c882bcbac622")// 构造请求的参数
                        .build();
                Request post_request = new Request.Builder()
                        .url(URL_POST)// 指定请求的地址
                        .post(body)// 指定请求的方式为POST
                        .build();
                client.newCall(post_request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Message msg = new Message();
                        msg.what = 0;
                        msg.obj = e.toString();
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        ResponseBody body = response.body();
                        String string = body.string();// 把返回的结果转换为String类型
                        Message msg = new Message();
                        msg.what = 1;
                        msg.obj = string;
                        handler.sendMessage(msg);
                    }
                });
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
