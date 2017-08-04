package com.example.administrator.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.administrator.fragment.fragment2.activity.ActivityOpenGles;
import com.example.administrator.fragment.fragment2.activity.ActivityOpenGlestwo;
import com.example.administrator.R;
import com.example.administrator.base.BaseFragment;
import com.example.administrator.fragment.fragment4.activity.ActivityBaiDuMap;
import com.example.administrator.fragment.fragment4.activity.ActivityGoTo;


/**
 * Fragment2
 * Created by liu_tao on 16/5/23.
 */
public class Fragment2 extends BaseFragment implements View.OnClickListener {
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two, null);
        btn1 = (Button) view.findViewById(R.id.btn_baidumap);
        btn2 = (Button) view.findViewById(R.id.btn_retrofit);
        btn3 = (Button) view.findViewById(R.id.btn_rxandroid);
        btn4 = (Button) view.findViewById(R.id.btn_OpenGLES);
        return view;
    }

    @Override
    protected void initData() {
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_baidumap:
                openActivity(ActivityBaiDuMap.class);
                break;
            case R.id.btn_retrofit:
                openActivity(ActivityGoTo.class);
                break;
            case R.id.btn_rxandroid:
                openActivity(ActivityOpenGles.class);
                break;
            case R.id.btn_OpenGLES:
                openActivity(ActivityOpenGlestwo.class);
                break;
        }
    }

}