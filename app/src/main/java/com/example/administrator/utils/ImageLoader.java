package com.example.administrator.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.example.administrator.OkHttp.OkHttpUtils;
import com.example.administrator.OkHttp.callback.BitmapCallback;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Call;

import static com.example.administrator.utils.ShowUtils.e;
/**
 * Created by LiuTao on 2017/7/21 0021.
 */

public class ImageLoader {
    private static final String TAG = "ImageLoader";
    //下载使用的线程池对象
    ExecutorService threadLooper;
    //缓存类，能过获取和写入数据到缓存中，短时间的存储！！
    private static LruCache<String, Bitmap> cache;
    //文件操作类对象
    private FileUtils fileUtils;

    /**
     * 构造方法，需要传入一个保存文件的名字
     * 实例化：线程池对象，缓存类，文件操作类对象
     */
    public ImageLoader(Context context, String dirName) {
        //获取系统分配的最大内存
        int maxSize = (int) (Runtime.getRuntime().maxMemory() / 8);
        //实例化缓存类的对象
        cache = new LruCache<String, Bitmap>(maxSize) {
            //每一个键所对应的值的大小
            //自动释放低频率的文件
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
        fileUtils = new FileUtils(context, dirName);//实例化文件操作类的对象
        threadLooper = Executors.newFixedThreadPool(5);//实例化线程池，并设置运行的最大线程数
    }


    /**
     * 下载图片的方法，这也是提供给我们调用的方法
     * 需要传入一个URL地址，和一个图片下载成功后的回调方法
     */
    public void loadImage(final String url, @NonNull final ImageLoadListener listener) {
        //去掉所有需要转义的斜杠，把获得的字符串作为Bitmap对象的一个标示符，通过它和它的Bitmap对象一一对应
        final String key = url.replaceAll("[\\W]", "")+".jpg";//本地存储命名和格式
        Log.e(TAG,"key:"+key);
        //第一级，先判断断缓存类中是否有数据
        if (readFromCache(key) != null) {
            Log.e(TAG,"缓存类中有数据");
            //直接拿出来
            e("从缓存中加载");
            listener.loadImage(readFromCache(key));
        } else {
            //第二级，再判断本地中是否存在数据
            final Bitmap bitmap = fileUtils.readFromSDCard(key);//查看是否存在数据
            Log.e(TAG,"本地中存在数据");
            if (bitmap != null) {//本地中存在数据
                //存储到缓存
                e("从SDCard中加载");
                saveToCache(key, bitmap);//把从SD卡中读取到的数据保存到缓存中，
                //返回
                listener.loadImage(fileUtils.readFromSDCard(key));//返回一个数据给调用者
            } else {
                Log.e(TAG,"从网络中下载数据");
                OkHttpUtils.get()
                        .url(url)
                        .build()
                        .execute(new BitmapCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(Bitmap response, int id) {
                                listener.loadImage(response);
                                fileUtils.saveToSDCard(key, response);//保存文件到SD卡
                                saveToCache(key, response);//保存文件到内存中
                            }
                        });
            }
        }

    }

    /**
     * 取消子线程的任务
     */
    public void cancelDownLoad() {
        threadLooper.shutdown();
    }


    /**
     * 定义一个接口，里面有一个方法，
     * 这里有一个Bitmap对象参数，作用是让调用这接收这个Bitmap对象，实际这bitmap对象就是缓存中的对象
     */
    public interface ImageLoadListener {
        public void loadImage(Bitmap bmp);
    }

    /**
     * 使用缓存类存储Bitmap对象
     */
    private void saveToCache(String key, Bitmap bmp) {
        cache.put(key, bmp);
    }

    /**
     * 使用缓存类获取Bitmap对象
     */
    private Bitmap readFromCache(String key) {
        return cache.get(key);
    }
}
