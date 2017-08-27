package com.example.administrator.fragment;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.R;
import com.example.administrator.base.BaseFragment;
import com.example.administrator.fragment.fragment2.activity.ActivityBaiDuMap;
import com.example.administrator.fragment.fragment2.activity.ActivityGoTo;
import com.example.administrator.fragment.fragment2.activity.ActivityOpenGles;
import com.example.administrator.fragment.fragment2.activity.ActivityOpenGlestwo;
import com.example.administrator.view.MyView;


/**
 * Fragment2
 * Created by liu_tao on 16/5/23.
 */
public class Fragment2 extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "---MainActivity";
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private SensorManager mSensorManager;

    private Sensor accelerometer; // 加速度传感器
    private Sensor magnetic; // 地磁场传感器

    private TextView azimuthAngle;

    private float[] accelerometerValues = new float[3];
    private float[] magneticFieldValues = new float[3];
    private View view;
    private MyView myView;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_two, null);
        btn1 = (Button) view.findViewById(R.id.btn_baidumap);
        btn2 = (Button) view.findViewById(R.id.btn_retrofit);
        btn3 = (Button) view.findViewById(R.id.btn_rxandroid);
        btn4 = (Button) view.findViewById(R.id.btn_OpenGLES);

        initSensor();
        return view;
    }

    private void initSensor() {
        // 实例化传感器管理者
        mSensorManager = (SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);
        // 初始化加速度传感器
        accelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        // 初始化地磁场传感器
        magnetic = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        azimuthAngle = (TextView) view.findViewById(R.id.azimuth_angle_value);
        calculateOrientation();
    }

    @Override
    protected void initData() {
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
    }

    @Override
    public void onResume() {

        // 注册监听
        mSensorManager.registerListener(new MySensorEventListener(),
                accelerometer, Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(new MySensorEventListener(), magnetic,
                Sensor.TYPE_MAGNETIC_FIELD);
        super.onResume();
    }
    @Override
    public void onStop() {
        // unregister listener
        mSensorManager.unregisterListener(new MySensorEventListener());
        super.onStop();
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
    // 计算方向
    private void calculateOrientation() {
        float[] values = new float[3];
        float[] R = new float[9];
        SensorManager.getRotationMatrix(R, null, accelerometerValues,
                magneticFieldValues);
        SensorManager.getOrientation(R, values);
        values[0] = (float) Math.toDegrees(values[0]);

        Log.i(TAG, values[0] + "");
        if (values[0] >= -5 && values[0] < 5) {
            azimuthAngle.setText("↑正北");
        } else if (values[0] >= 5 && values[0] < 85) {
            // Log.i(TAG, "东北");
            azimuthAngle.setText("↑东北");
        } else if (values[0] >= 85 && values[0] <= 95) {
            // Log.i(TAG, "正东");
            azimuthAngle.setText("↑正东");
        } else if (values[0] >= 95 && values[0] < 175) {
            // Log.i(TAG, "东南");
            azimuthAngle.setText("↑东南");
        } else if ((values[0] >= 175 && values[0] <= 180)
                || (values[0]) >= -180 && values[0] < -175) {
            // Log.i(TAG, "正南");
            azimuthAngle.setText("↑正南");
        } else if (values[0] >= -175 && values[0] < -95) {
            // Log.i(TAG, "西南");
            azimuthAngle.setText("↑西南");
        } else if (values[0] >= -95 && values[0] < -85) {
            // Log.i(TAG, "正西");
            azimuthAngle.setText("↑正西");
        } else if (values[0] >= -85 && values[0] < -5) {
            // Log.i(TAG, "西北");
            azimuthAngle.setText("↑西北");
        }
    }

    class MySensorEventListener implements SensorEventListener {
        @Override
        public void onSensorChanged(SensorEvent event) {
            // TODO Auto-generated method stub
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                accelerometerValues = event.values;
            }
            if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                magneticFieldValues = event.values;
            }
            calculateOrientation();
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // TODO Auto-generated method stub

        }

    }
}