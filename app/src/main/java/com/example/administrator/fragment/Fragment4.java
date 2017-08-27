package com.example.administrator.fragment;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.R;
import com.example.administrator.base.BaseFragment;
import com.example.administrator.fragment.fragment4.activity.ActivityImageLoader;
import com.example.administrator.fragment.fragment4.activity.ActivityListViewRefresh;
import com.example.administrator.fragment.fragment4.activity.ActivityOne;
import com.example.administrator.fragment.fragment4.activity.ActivityTextInputLayout;
import com.example.administrator.fragment.fragment4.activity.ActivityTwo;
import com.example.administrator.fragment.fragment4.activity.OKHttpActivity;
import com.example.administrator.fragmentHelp.GlideCircleTransform;
import com.example.administrator.greendao.ActivityGreenDao;
import com.example.administrator.retrofit.ActivityRetrofit;
import com.example.administrator.view.MyView;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Fragment4
 * Created by liu_tao on 16/5/23.
 */
public class Fragment4 extends BaseFragment implements View.OnClickListener {
    String url = "http://www.baidu.com/";

    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9,btn10;
    private MyView myView;
    private SensorManager sensorManager;
    private Sensor sensor;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_four, null);
        CircleImageView proFileImage = (CircleImageView) view.findViewById(R.id.profile_image);
        ImageView circleimage = (ImageView) view.findViewById(R.id.circle_image);
        //加载网络图片
        //  proFileImage.setImageResource(R.drawable.hed);
        String path = "http://img3.imgtn.bdimg.com/it/u=3242709860,2221903223&fm=214&gp=0.jpg";
        Glide.with(getContext())
                .load(path)
                .placeholder(R.drawable.head_1)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .error(R.drawable.hed)
                .into(proFileImage);

        Glide.with(getContext())
                .load(path)
                .transform(new GlideCircleTransform(getContext()))
                .placeholder(R.drawable.head_1)
                .error(R.drawable.hed)
                .into(circleimage);
        ////本地文件
     /*   File file = new File(Environment.getExternalStorageDirectory(), "xiayu.png");
        //加载图片
        Glide.with(this).load(file).into(iv);
        */
        proFileImage.setImageURI(Uri.parse(path));
        btn1 = (Button) view.findViewById(R.id.btn1);
        btn2 = (Button) view.findViewById(R.id.btn2);
        btn3 = (Button) view.findViewById(R.id.btn3);
        btn4 = (Button) view.findViewById(R.id.btn4);
        btn5 = (Button) view.findViewById(R.id.btn5);
        btn6 = (Button) view.findViewById(R.id.btn6);
        btn7 = (Button) view.findViewById(R.id.btn7);
        btn8 = (Button) view.findViewById(R.id.btn8);
        btn9 = (Button) view.findViewById(R.id.btn9);
        btn10 = (Button) view.findViewById(R.id.btn10);
        myView = (MyView)view.findViewById(R.id.second_myView);
        initView();
        return view;
    }

    @Override
    public void setTitle(String title) {
        setTitle("大学");
        super.setTitle(title);
    }

    @Override
    protected void initData() {
    }

    private void initView() {
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btn10.setOnClickListener(this);
        //实例化传感器的管理器
        sensorManager = (SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }
    /***
     * 在onStart中注册传感器的监听事件
     */
    @Override
    public void onStart() {
        super.onStart();

        //注册监听传感状态
        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_FASTEST);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                openActivity(ActivityOne.class);
                break;
            case R.id.btn2:
                openActivity(ActivityTwo.class);
                break;
            case R.id.btn3:
                openActivity(ActivityListViewRefresh.class);
                break;
            case R.id.btn4:
                openActivity(ActivityTwo.class);
                break;
            case R.id.btn5:
                openActivity(ActivityImageLoader.class);
                break;
            case R.id.btn6:
                break;
            case R.id.btn7:
                openActivity(OKHttpActivity.class);
                break;
            case R.id.btn8:
                openActivity(ActivityGreenDao.class);
                break;
            case R.id.btn9:
                openActivity(ActivityRetrofit.class);
                break;
            case R.id.btn10:
                openActivity(ActivityTextInputLayout.class);
                break;

        }
    }
    /**
     * 传感器的监听对象
     */
    SensorEventListener listener = new SensorEventListener() {
        //传感器改变时,一般是通过这个方法里面的参数确定传感器状态的改变
        @Override
        public void onSensorChanged(SensorEvent event) {
            // Log.e("TAG", "-------传感状态发生变化-------");
            //获取传感器的数据
            float[] values = event.values;
            float max = sensor.getMaximumRange();//获取最大值范围
            if (values.length >= 3) {
                float x = values[0];
                float y = values[1];
                float z = values[2];
                if (Math.abs(x) > max / 8) {
                    myView.initdata((int)x,(int)y);
                }
                if (Math.abs(y) > max /8) {
                    myView.initdata((int)x,-(int)y);
                }

            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            //当精准度改变时
        }
    };


    /***
     * 在onDestroy中注销传感器的监听事件
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(listener);
    }
}
