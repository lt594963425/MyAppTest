package com.example.administrator.fragmenttabhost;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.ui.activity.MainActivity;
import com.example.administrator.utils.Md5Utils;
import com.example.administrator.utils.ToastUtils;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by Carson_Ho on 16/5/23.
 */
public class Fragment1 extends Fragment implements View.OnClickListener {
    String[] imagess = new String[]{"http://pic29.photophoto.cn/20131125/0022005500418920_b.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498558030884&di=b10f693abcebd09dfb309d89702672e5&imgtype=0&src=http%3A%2F%2Fpic29.nipic.com%2F20130511%2F12011435_141504339147_2.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498558204252&di=8a6ce8463360d42b7518665a469391fc&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F11%2F04%2F37%2F04658PICQHc.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498558224830&di=b546d2811f9fa910decc55b981f8df8c&imgtype=0&src=http%3A%2F%2Fpic2.ooopic.com%2F11%2F77%2F47%2F63bOOOPIC74_1024.jpg"};
    private static SimpleDateFormat sdf;
    private final OkHttpClient client = new OkHttpClient();
    private EditText urlEt;
    private TextView urlLog, time;
    private Button urlBtn, RxBtn, md5Btn;
    private ImageView ivDelete;
    protected static final int SUCCESS = 1;
    protected static final int ERROR_CITY = 2;
    protected static final int ERROR = 3;//
    protected static final int TIMEOK = 4;
    protected static final int MD5 = 5;


    private  Handler handler;

    String url = "http://i.qq.com/?s_url=http%3A%2F%2Fuser.qzone.qq.com%2F594963425";
    private ImageView iVShow;
    private Banner banner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item1, null);
        urlEt = (EditText) view.findViewById(R.id.url_et);
        urlLog = (TextView) view.findViewById(R.id.log_tv);
        urlBtn = (Button) view.findViewById(R.id.url_btn);
        md5Btn = (Button) view.findViewById(R.id.md5_btn);
        ivDelete = (ImageView) view.findViewById(R.id.iv_delete);
        time = (TextView) view.findViewById(R.id.time);

        iVShow = (ImageView) view.findViewById(R.id.iv_show);
        VectorDrawableCompat drawable = VectorDrawableCompat.create(getResources(), R.drawable.ic_delete, null);
        ivDelete.setImageDrawable(drawable);
        RxBtn = (Button) view.findViewById(R.id.btn_rx);
        RxBtn.setOnClickListener(this);
        urlBtn.setOnClickListener(this);
        ivDelete.setOnClickListener(this);
        md5Btn.setOnClickListener(this);
        MyTextWatcher.setPhoneEdit(getActivity(),urlEt);
        MyTextWatchers myTextWatcher = new MyTextWatchers();
        urlEt.addTextChangedListener(myTextWatcher);
        iVShow.setOnClickListener(new View.OnClickListener() {//拍照
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intent1, Activity.DEFAULT_KEYS_DIALER);//intent,Activity.DEFAULT_KEYS_DIALER

            }
        });

        Integer [] imageResIDs = {R.mipmap.advinfo,
                R.mipmap.advinfo_one,
                R.mipmap.advinfo_two,
                R.mipmap.advinfo4};
        //  banner.setImages(new ArrayList<>(Arrays.asList(imagess))).setImageLoader(new GlideImageLoader()).start();
        //轮播图
        banner = (Banner) view.findViewById(R.id.banner);
        //设置图片集合
        banner.setImages(new ArrayList<>(Arrays.asList(imagess))).setImageLoader(new GlideImageLoader()).start();
        handler =new MyHandler((MainActivity)getActivity());
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        banner.isAutoPlay(true);
    }

    /**
     * 回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Activity.RESULT_OK) {
            String statue = Environment.getExternalStorageState();
            //检查sd卡是否可用
            if (!statue.equals(Environment.MEDIA_MOUNTED)) {
                return;
            }
            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
            FileOutputStream b = null;
            File file = new File("/sdcard/myImage/");
            file.mkdirs();// 创建文件夹，名称为myimage
            //照片的命名，目标文件夹下，以当前时间数字串为名称，即可确保每张照片名称不相同。
            String str = null;
            Date date = null;
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");//获取当前时间，进一步转化为字符串
            date = new Date();
            str = format.format(date);
            String fileName = "/sdcard/myImage/" + str + ".jpg";
            try {
                b = new FileOutputStream(fileName);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    b.flush();
                    b.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (data != null) {
                    Bitmap cameraBitmap = (Bitmap) data.getExtras().get("data");
                    System.out.println("fdf=================" + data.getDataString());
                    iVShow.setImageBitmap(cameraBitmap);
                    System.out.println("成功======" + cameraBitmap.getWidth() + cameraBitmap.getHeight());
                }
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.url_btn:
                long time1 = System.currentTimeMillis();
                String s = String.valueOf(time1);
                Message msg = Message.obtain();
                msg.obj = s;
                msg.what = TIMEOK;
                handler.sendMessage(msg);
                String newurl = urlEt.getText().toString().trim();
                loginUUrl(newurl);
                break;
            case R.id.iv_delete:
                urlEt.setText("");
                break;
            case R.id.btn_rx:
                RXjavaTest();
                break;
            case R.id.md5_btn:
                String s1 = urlEt.getText().toString().trim();
                String md5pwd = Md5Utils.encryptpwd(s1);
                Message msg1 = Message.obtain();
                msg1.obj = md5pwd;
                msg1.what = MD5;
                handler.sendMessage(msg1);
                break;
        }
    }

    /*将字符串转为时间戳*/
    public static long getStringToDate(String time) {
        sdf = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date();
        try {
            date = sdf.parse(String.valueOf(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    public void loginUUrl(String url) {
        try {
            Request requst = new Request.Builder()
                    .url(url)
                    .get()//http://192.169.6.119/login/login/login/tel/15974255013/pwd/123456/code/wrty
                    .build();
            client.newCall(requst).enqueue(new Callback() {
                @Override
                public void onFailure(okhttp3.Call call, IOException e) {
                    ToastUtils.showToast("网络不佳，登录失败");
                    Message msg = Message.obtain();
                    msg.what = ERROR;
                    handler.sendMessage(msg);
                }

                @Override
                public void onResponse(okhttp3.Call call, Response response) throws IOException {
                    String s = response.body().string().trim();
                    try {
                        JSONObject object = new JSONObject(s);
                        InputStream is = response.body().byteStream();//字节流
                        Message msg = Message.obtain();
                        msg.obj = s;
                        msg.what = SUCCESS;
                        handler.sendMessage(msg);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Message msg = Message.obtain();
                        msg.what = ERROR_CITY;
                        msg.obj = s;
                        handler.sendMessage(msg);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.showToast("URL错误");
                }
            });
        }
    }

    public void RXjavaTest() {
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                //执行一些其他操作

                e.onNext("把操作结果发送出去");
            }
        });
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {//解除订阅

            }

            @Override
            public void onNext(String s) {  //正常接收到的数据

                ToastUtils.showToast("收到消息：" + s);
            }

            @Override
            public void onError(Throwable e) {//错误

            }

            @Override
            public void onComplete() {//事件完成之后

            }
        };
        observable.subscribe(observer);//被观察者和观察者关联
    }

    /**
     * 监听手机号码的长度
     */
    CharSequence temp;

    public class MyTextWatchers implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            temp = s;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (temp.length() > 0 && !urlEt.getText().toString().isEmpty()) {
                ivDelete.setVisibility(View.VISIBLE);
                temp = "";
            } else {
                ivDelete.setVisibility(View.INVISIBLE);

            }
        }
    }


    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            /**
             注意：
             1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
             2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
             传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
             切记不要胡乱强转！
             */

            //Glide 加载图片简单用法
            Glide.with(context).load(path).into(imageView);
//
//            //Picasso 加载图片简单用法
//            Picasso.with(context).load("").into(imageView);
//
//            //用fresco加载图片简单用法，记得要写下面的createImageView方法
//            Uri uri = Uri.parse((String) path);
//            imageView.setImageURI(uri);
        }

   /*     //提供createImageView 方法，如果不用可以不重写这个方法，主要是方便自定义ImageView的创建
        @Override
        public ImageView createImageView(Context context) {
            //使用fresco，需要创建它提供的ImageView，当然你也可以用自己自定义的具有图片加载功能的ImageView
            SimpleDraweeView simpleDraweeView = new SimpleDraweeView(context);
            return simpleDraweeView;
        }*/
    }

    private class MyHandler extends Handler {
        //弱引用防止内存泄漏
        WeakReference<MainActivity > mActivity;
        public MyHandler (MainActivity activity){
            mActivity =new WeakReference<MainActivity>(activity);
        }
        public void handleMessage(android.os.Message msg) {
            MainActivity activity =mActivity.get();
            switch (msg.what) {
                case ERROR:
                    Toast.makeText(activity, "网络异常", Toast.LENGTH_SHORT).show();
                    break;

                case ERROR_CITY:
                    String str = (String) msg.obj;
                    urlLog.setText(str);
                    Toast.makeText(activity, "检查url是否正确", Toast.LENGTH_SHORT).show();
                    break;
                case SUCCESS:
                    String strw = (String) msg.obj;
                    urlLog.setText(strw);
                    break;
                case TIMEOK:
                    String strTime = (String) msg.obj;
                    time.setText(strTime);
                    break;
                case MD5:
                    String md = (String) msg.obj;
                    urlLog.setText(md);
                    break;

            }
        }
    }
}

