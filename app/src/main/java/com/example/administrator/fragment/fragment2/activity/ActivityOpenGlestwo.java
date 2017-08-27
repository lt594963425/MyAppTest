package com.example.administrator.fragment.fragment2.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.administrator.R;
import com.example.administrator.base.BaseActivity;
import com.example.administrator.openGles.ARCamera;
import com.example.administrator.openGles.MyGLSurfaceView;
import com.example.administrator.utils.ToastUtils;

import java.util.Random;

import static com.example.administrator.fragment.fragment2.activity.ActivityOpenGles.REQUEST_CAMERA_PERMISSIONS_CODE;

/**
 * 它使用OpenGL显示了一个灰色的屏幕。
 * Created by LiuTao on 2017/8/3 0003.
 */

public class ActivityOpenGlestwo extends BaseActivity {
    private Camera camera;
    //private MyGLESSureface myGLESSureface;
    private ARCamera arCamera;
    Random rnd = new Random();
    private GLSurfaceView glView;
    private SurfaceView surfaceView;
    private FrameLayout cameraLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setTheme(android.R.style.Theme_Translucent_NoTitleBar);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_open_gles_two);
        surfaceView = findView(R.id.glSurface);
        cameraLayout = (FrameLayout) findViewById(R.id.camera_layout);

        glView = new MyGLSurfaceView(this);
        glView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        glView.setZOrderMediaOverlay(true);
        glView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast("飞机");
            }
        });
        cameraLayout.addView(glView);
        requestCameraPermission();
    }

    public void requestCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                this.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            this.requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSIONS_CODE);
        } else {
            initARCameraView();
        }
    }

    //初始化摄像头
    public void initARCameraView() {
        reloadSurfaceView();

        if (arCamera == null) {
            arCamera = new ARCamera(this, surfaceView);
        }
        if (arCamera.getParent() != null) {
            ((ViewGroup) arCamera.getParent()).removeView(arCamera);
        }
        cameraLayout.addView(arCamera);
        arCamera.setKeepScreenOn(true);
        initCamera();
    }

    private void initCamera() {
        int numCams = Camera.getNumberOfCameras();
        if (numCams > 0) {
            try {
                camera = Camera.open();
                camera.startPreview();
                arCamera.setCamera(camera);
            } catch (RuntimeException ex) {
                Toast.makeText(this, "Camera not found", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void reloadSurfaceView() {
        if (glView.getParent() != null) {
            ((ViewGroup) glView.getParent()).removeView(surfaceView);
        }

        cameraLayout.addView(surfaceView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        glView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        glView.onResume();
//        android.provider.Settings.System.putInt(this.getContentResolver(),
//                android.provider.Settings.System.SCREEN_BRIGHTNESS, rnd.nextInt(256));

    }
}
