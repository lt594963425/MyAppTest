package com.example.administrator.fragmenttabhost;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.GreenDao.ActivityGreenDao;
import com.example.administrator.activity.SplashActivity;
import com.example.administrator.retrofit.ActivityRetrofit;
import com.example.administrator.ui.activity.ActivityListPup;
import com.example.administrator.ui.activity.ActivityListViewRefresh;
import com.example.administrator.ui.activity.ActivityOne;
import com.example.administrator.ui.activity.ActivityTwo;
import com.example.administrator.ui.activity.OKHttpActivity;

import cn.sharesdk.onekeyshare.OnekeyShare;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by Carson_Ho on 16/5/23.
 */
public class Fragment4 extends Fragment implements View.OnClickListener {
    String url = "http://www.baidu.com/";

    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    String[] arr = {"asdasd", "asfsfsd", "rfefe",};
    private String TAG = "MainActivity";
    private CircleImageView proFileImage;
    private ImageView circleimage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_item4, null);
        proFileImage = (CircleImageView) view.findViewById(R.id.profile_image);
        circleimage = (ImageView) view.findViewById(R.id.circle_image);
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
        initView();
        return view;
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
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn1:
                intent = new Intent(getActivity(), ActivityOne.class);
                startActivity(intent);
                break;
            case R.id.btn2:
                intent = new Intent(getActivity(), ActivityTwo.class);
                startActivity(intent);
                break;
            case R.id.btn3:
                intent = new Intent(getActivity(), ActivityListPup.class);
                startActivity(intent);
                break;
            case R.id.btn4:
                intent = new Intent(getActivity(), ActivityListViewRefresh.class);
                startActivity(intent);
                break;
            case R.id.btn5:
                intent = new Intent(getActivity(), SplashActivity.class);
                startActivity(intent);
                break;
            case R.id.btn6:
                showShare();
                break;
            case R.id.btn7:
                intent = new Intent(getActivity(), OKHttpActivity.class);
                startActivity(intent);
                break;
            case R.id.btn8:
                intent = new Intent(getActivity(), ActivityGreenDao.class);
                startActivity(intent);
                break;
            case R.id.btn9:
                intent = new Intent(getActivity(), ActivityRetrofit.class);
                startActivity(intent);
                break;


        }
    }

    private void showShare() {

        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("测试测试测试----标题");
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("测试测试测试-----我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");
        // 启动分享GUI
        oks.show(getActivity());
    }
    /*          //创建观察者observable
                Observable.from(arr)
                        .filter(new Func1<String, Boolean>() {
                            @Override
                            public Boolean call(String mS) {
                                return mS.startsWith("a");
                            }
                        })
                        .map(new Func1<String, String>() {
                            @Override
                            public String call(String mS) {
                                return mS+"我是大神";
                            }
                        })
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String mO) {
                                ToastUtils.showToast("Rxrxrxrxrxrxrx:"+mO.length()+mO);
                                Log.i(TAG,"Rxrxrxrxrxrxrx:"+mO.length()+mO);
                            }
                        });*/
}
