package com.example.administrator.fragment.fragment4.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.R;
import com.example.administrator.base.BaseActivity;
import com.example.administrator.utils.ToastUtils;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;


/**
 * Created by LiuTao on 2017/8/1 0001.
 */

public class ActivityGoTo extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "ActivityGoTo";
    private EditText mInputAddress;

    private TextView mGo;
    private String mAddress;
    private TextView mGoBaiDu;
    private TextView mGoGaoDe;
    private TextView mGoGoolGe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_to);
        ButterKnife.bind(this);
        setBarBack();
        initView();
    }

    private void initView() {
        mGo = findView(R.id.go);
        mInputAddress = findView(R.id.input_address);
        setTitle("去哪儿");
        mGo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mAddress = mInputAddress.getText().toString().trim();

        switch (v.getId()) {
            case R.id.go:
                if (TextUtils.isEmpty(mAddress)) return;
                showGoDialog();
                break;
            case R.id.go_baidu:
                ToastUtils.showToast("go_baidu");
                setGoBaidu(mAddress);
                break;
            case R.id.go_gaode:
                setMiniMap(mAddress);
                ToastUtils.showToast("go_gaode");
                break;
            case R.id.go_goolge:
                setGooGel(mAddress);
                ToastUtils.showToast("go_goolge");
                break;
        }
    }

    private void showGoDialog() {
        Dialog dialog = new Dialog(this, R.style.BottomDialog);
        //填充对话框的布局
        View inflate = LayoutInflater.from(this).inflate(R.layout.dialog_content_normal, null);
        //初始化控件
        //将布局设置给Dialog
        initInflate(inflate);
        dialog.setContentView(inflate);
        dialog.setCancelable(true);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        int mWindowWidth;
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        mWindowWidth = displayMetrics.widthPixels;
        dialog.setContentView(inflate, new ViewGroup.MarginLayoutParams(mWindowWidth,
                ViewGroup.MarginLayoutParams.MATCH_PARENT));
        dialog.show();//显示对话框
    }

    private void initInflate(View inflate) {
        mGoBaiDu = (TextView) inflate.findViewById(R.id.go_baidu);
        mGoGaoDe = (TextView) inflate.findViewById(R.id.go_gaode);
        mGoGoolGe = (TextView) inflate.findViewById(R.id.go_goolge);
        mGoBaiDu.setOnClickListener(this);
        mGoGaoDe.setOnClickListener(this);
        mGoGoolGe.setOnClickListener(this);

    }

    /**
     * 设置添加屏幕的背景透明度
     */
    public void backgroundAlpha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }

    public static boolean isAvilible(Context context, String packageName) {
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

    public void setGoBaidu(String address) {
        Intent intent = null;
        if (isAvilible(this, "com.baidu.BaiduMap")) {//传入指定应用包名

            try {
                intent = Intent.getIntent("intent://map/direction?destination=" + address + "&mode=driving&src=#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");//
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            startActivity(intent); //启动调用

        } else {//未安装
            //market为路径，id为包名
            //显示手机上所有的market商店
            Toast.makeText(this, "您尚未安装百度地图", Toast.LENGTH_LONG).show();
            Uri uri = Uri.parse("market://details?id=com.baidu.BaiduMap");
            intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    public void setMiniMap(String address) {
        Intent intent1;
        if (isAvilible(this, "com.autonavi.minimap")) {//传入指定应用包名
            try {
                //单独导航
//            cat=android.intent.category.DEFAULTdat=androidamap://navi?sourceApplication=appname&poiname=fangheng&lat=36.547901&lon=104.258354&dev=1&style=2pkg=com.autonavi.minimap
//            Intent intent = new Intent("android.intent.action.VIEW",
//                    android.net.Uri.parse("androidamap://navi?sourceApplication=某某公司&lat=" + loc.getLat() + "&lon=" + loc.getLng() + "&dev=1&style=2"));
//            intent.setPackage("com.autonavi.minimap");


                //反地理编码
//            Intent inten1 = new Intent("android.intent.action.VIEW"
//                    , android.net.Uri.parse("androidamap://viewReGeo?sourceApplication=某某公司&lat=" + loc.getLat() + "&lon=" + loc.getLng() + "&dev=1"));
//            inten1.setPackage("com.autonavi.minimap");// pkg=com.autonavi.minimap
//            inten1.addCategory("android.intent.category.DEFAULT");
//            this.startActivity(inten1);

                intent1 = Intent.getIntent("androidamap://route?sourceApplication=softname" + "&sname=我的位置&dname=" + address + "&dev=0&m=0&t=1");
                //地理编码
//               intent1 = new Intent("android.intent.action.VIEW"
//                        , Uri.parse("androidamap://viewGeo?sourceApplication=appname&addr=" + address+"&dev=0&t=0"));
                intent1.setPackage("com.autonavi.minimap");// pkg=com.autonavi.minimap
                intent1.addCategory("android.intent.category.DEFAULT");
                startActivity(intent1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            //未安装
            //market为路径，id为包名
            //显示手机上所有的market商店
            Toast.makeText(this, "您尚未安装高德地图", Toast.LENGTH_LONG).show();
            Uri uri = Uri.parse("market://details?id=com.autonavi.minimap");
            intent1 = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent1);

        }

    }

    public void setGooGel(String address) {
        Intent intent;
        if (isAvilible(this, "com.google.android.apps.maps")) {
            Uri gmmIntentUri = Uri.parse("google.navigation:q=" + address);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            this.startActivity(mapIntent);
        } else {
            Toast.makeText(this, "您尚未安装谷歌地图", Toast.LENGTH_LONG).show();

            Uri uri = Uri.parse("market://details?id=com.google.android.apps.maps");
            intent = new Intent(Intent.ACTION_VIEW, uri);
            this.startActivity(intent);
        }
    }
}
