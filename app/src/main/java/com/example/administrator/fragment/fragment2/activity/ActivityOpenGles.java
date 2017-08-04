package com.example.administrator.fragment.fragment2.activity;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.TextureView;

import com.example.administrator.R;
import com.example.administrator.base.BaseActivity;

import java.io.IOException;

/**
 *
 * Created by LiuTao on 2017/8/3 0003.
 */

public class ActivityOpenGles extends BaseActivity implements TextureView.SurfaceTextureListener {
    private TextureView myTexture;
    private Camera mCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myTexture = new TextureView(this);
        myTexture.setSurfaceTextureListener(this);
        setContentView(myTexture);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.open_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfacetexture, int width, int height) {
        mCamera = Camera.open();
//        Camera.Size previewSize = mCamera.getParameters().getPreviewSize();
//        width = myTexture.getMeasuredWidth();
//        height = myTexture.getMeasuredHeight();
//        myTexture.setLayoutParams(new FrameLayout.LayoutParams(width
//                , height, Gravity.CENTER));
        try {
            mCamera.setPreviewTexture(surfacetexture);

        } catch (IOException e) {
            e.printStackTrace();
        }
        mCamera.startPreview();//准备u
        myTexture.setAlpha(1.0f);
        myTexture.setRotation(90.0f);
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        mCamera.stopPreview();
        mCamera.release();
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }
}
