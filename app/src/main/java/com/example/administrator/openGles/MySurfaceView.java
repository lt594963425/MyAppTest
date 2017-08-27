package com.example.administrator.openGles;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.List;

/**
 * Created by LiuTao on 2017/8/15 0015.
 */

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback{
    private static final String TAG = "MySurfaceView";

    private SurfaceHolder mSurfaceHolder;
    private Camera mCamera;

    public MySurfaceView(Context context) {
        super(context);
        init();
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        //mCamera.setDisplayOrientation(90);
        mCamera.startPreview();
    }

    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
        try {

            mCamera = Camera.open();
            Camera.Parameters cameraParams = mCamera.getParameters();
            List<Camera.Size> supportPreviewSizes = cameraParams.getSupportedPictureSizes();
            cameraParams.setPreviewSize(supportPreviewSizes.get(0).width, supportPreviewSizes.get(0).height);

            if (mCamera != null) {
                mCamera.setPreviewDisplay(mSurfaceHolder);
            }
        } catch (Exception exception) {
            Log.i(TAG, "SufaceView Create failed!");
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
        // Surface will be destroyed when replaced with a new screen
        // Always make sure to release the Camera instance
        mCamera.stopPreview();
        mCamera.release();
        mCamera = null;

    }
}
