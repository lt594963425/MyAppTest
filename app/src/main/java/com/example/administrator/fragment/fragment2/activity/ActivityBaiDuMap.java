package com.example.administrator.fragment.fragment2.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.example.administrator.R;
import com.example.administrator.base.BaseActivity;
import com.example.administrator.fragment.fragment4.activity.MyOrientationListener;
import com.example.administrator.utils.NetWorkUtils;
import com.example.administrator.utils.ToastUtils;

import static com.example.administrator.R.id.bmapView;
import static com.example.administrator.R.id.location;

/**
 * 百度地图
 * Created by LiuTao on 2017/7/26 0026.
 */
public class ActivityBaiDuMap extends BaseActivity implements View.OnClickListener, Toolbar.OnMenuItemClickListener {
    private BaiduMap mBaiduMap;
    private Button common,satellite,traffic,hot;
    private StringBuffer sb;
    public String registrationID;
    //定位的相关声明
    private MapView mMapView;
    private LocationClient mlocationClient;
    private MyBDlocationListener mlistener = new MyBDlocationListener();
    private double mLatitude;// 经度
    private double mLongitude;// 纬度
    private float mCurrentX;
    private Button mGetMylocationBN;
    private MyOrientationListener myOrientationListener;
    //定位图层显示方式
    private MyLocationConfiguration.LocationMode locationMode;
    public LocationClient LocationClient = null;
    private Context context;

    boolean isHot = false;
    boolean isTraffic = false; //是否是首次定位
    private Toolbar mToolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_baidu_map);
        this.context=this;
        mMapView = (MapView) findViewById(bmapView);
        common = (Button) findViewById(R.id.common);
        satellite = (Button) findViewById(R.id.satellite);
        traffic = (Button) findViewById(R.id.traffic);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.inflateMenu(R.menu.menu);
        mToolbar.setOnMenuItemClickListener(this);
        hot = (Button) findViewById(R.id.hot);
        common.setOnClickListener(this);
        satellite.setOnClickListener(this);
        traffic.setOnClickListener(this);
        hot.setOnClickListener(this);
        setBarBack();
        init();
        initLocation();
    }
    private void init() {
        mBaiduMap = mMapView.getMap();
        //设定初始化地图时的比例,当前为500米
        MapStatusUpdate msu= MapStatusUpdateFactory.zoomTo(15.0f);
        mBaiduMap.setMapStatus(msu);
        mGetMylocationBN=(Button)findViewById(location);
        mGetMylocationBN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMyLocation();
            }
        });


    }
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_map_model_common:
                //普通模式
                locationMode= MyLocationConfiguration.LocationMode.NORMAL;
                ToastUtils.showToast("普通模式");
                break;
            case R.id.id_map_model_following:
                //跟随模式
                locationMode= MyLocationConfiguration.LocationMode.FOLLOWING;
                ToastUtils.showToast("跟随模式");
                break;
            case R.id.id_map_model_compass:
                //罗盘模式
                locationMode= MyLocationConfiguration.LocationMode.COMPASS;
                ToastUtils.showToast("罗盘模式");
                break;
        }

        return false;
    }

    /***
     * 实现BDLocationListener接口，重写onReceiveLocation处理接收的定位数据
     *
     * @author mishaoshuai
     *
     */
    class MyBDlocationListener implements BDLocationListener {
        //定位请求回调接口
        private boolean isFirstIn=true;
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            //BDLocation 回调的百度坐标类，内部封装了如经纬度、半径等属性信息
            //MyLocationData 定位数据,定位数据建造器
            /*
            * 可以通过BDLocation配置如下参数
            * 1.accuracy 定位精度
            * 2.latitude 百度纬度坐标
            * 3.longitude 百度经度坐标
            * 4.satellitesNum GPS定位时卫星数目 getSatelliteNumber() gps定位结果时，获取gps锁定用的卫星数
            * 5.speed GPS定位时速度 getSpeed()获取速度，仅gps定位结果时有速度信息，单位公里/小时，默认值0.0f
            * 6.direction GPS定位时方向角度
            *
            *
            * */
            mLatitude= bdLocation.getLatitude();
            mLongitude=bdLocation.getLongitude();
            MyLocationData data= new MyLocationData.Builder()
                    .direction(mCurrentX)//设定图标方向
                    .accuracy(bdLocation.getRadius())//getRadius 获取定位精度,默认值0.0f
                    .latitude(mLatitude)//百度纬度坐标
                    .longitude(mLongitude)//百度经度坐标
                    .build();
            //设置定位数据, 只有先允许定位图层后设置数据才会生效，参见 setMyLocationEnabled(boolean)
            mBaiduMap.setMyLocationData(data);
            //配置定位图层显示方式,三个参数的构造器
            /*
            * 1.定位图层显示模式
            * 2.是否允许显示方向信息
            * 3.用户自定义定位图标
            *
            * */
            MyLocationConfiguration configuration
                    =new MyLocationConfiguration(locationMode,true,null);
            //设置定位图层配置信息，只有先允许定位图层后设置定位图层
            // m配置信息才会生效，参见 setMyLocationEnabled(boolean)
            mBaiduMap.setMyLocationConfiguration(configuration);
            //判断是否为第一次定位,是的话需要定位到用户当前位置
            if(isFirstIn)
            {
                //地理坐标基本数据结构
                LatLng latLng=new LatLng(bdLocation.getLatitude(),bdLocation.getLongitude());
                //描述地图状态将要发生的变化,通过当前经纬度来使地图显示到该位置
                MapStatusUpdate msu= MapStatusUpdateFactory.newLatLng(latLng);
                //改变地图状态
                mBaiduMap.setMapStatus(msu);
                isFirstIn=false;
                Toast.makeText(context, bdLocation.getAddrStr(), Toast.LENGTH_SHORT).show();
            }


//            // // 当不需要定位图层时关闭定位图层
//            // mBaiduMap.setMyLocationEnabled(false);
//            // Receive Location
//            sb = new StringBuffer(256);
//            if (bdLocation.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
//                sb.append("\ndescribe : ");
//                sb.append("gps定位成功");
//            } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
//                sb.append("\ndescribe : ");
//                sb.append("网络定位成功");
//            } else if (bdLocation.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
//                sb.append("\ndescribe : ");
//                sb.append("离线定位成功，离线定位结果也是有效的");
//            } else if (bdLocation.getLocType() == BDLocation.TypeServerError) {
//                sb.append("\ndescribe : ");
//                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
//            } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkException) {
//                sb.append("\ndescribe : ");
//                sb.append("网络不同导致定位失败，请检查网络是否通畅");
//            } else if (bdLocation.getLocType() == BDLocation.TypeCriteriaException) {
//                sb.append("\ndescribe : ");
//                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
//            }
//            sb.append("\nlocationdescribe : ");
//            sb.append(bdLocation.getLocationDescribe());// 位置语义化信息
//            List<Poi> list = bdLocation.getPoiList();// POI数据
//            if (list != null) {
//                sb.append("\npoilist size = : ");
//                sb.append(list.size());
//                for (Poi p : list) {
//                    sb.append("\npoi= : ");
//                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
//                }
//            }
//            sb.append("\nregistrationID:");
//            sb.append(registrationID);

        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }


    public void getMyLocation()
    {
        LatLng latLng=new LatLng(mLatitude,mLongitude);
        MapStatusUpdate msu= MapStatusUpdateFactory.newLatLng(latLng);
        mBaiduMap.setMapStatus(msu);
    }

    /**
     * 定位参数
     */
    private void initLocation() {
        locationMode= MyLocationConfiguration.LocationMode.NORMAL;

        //定位服务的客户端。宿主程序在客户端声明此类，并调用，目前只支持在主线程中启动
        mlocationClient=new LocationClient(this);
        mlistener=new MyBDlocationListener();

        //注册监听器
        mlocationClient.registerLocationListener(mlistener);
        //配置定位SDK各配置参数，比如定位模式、定位时间间隔、坐标系类型等
        LocationClientOption mOption=new LocationClientOption();
        //设置坐标类型
        mOption.setCoorType("bd09ll");
        //设置是否需要地址信息，默认为无地址
        mOption.setIsNeedAddress(true);
        //设置是否打开gps进行定位
        mOption.setOpenGps(true);
        //设置扫描间隔，单位是毫秒 当<1000(1s)时，定时定位无效
        int span=1000;
        mOption.setScanSpan(span);
        //设置 LocationClientOption
        mlocationClient.setLocOption(mOption);

//        //初始化图标,BitmapDescriptorFactory是bitmap 描述信息工厂类，在使用该类方法之前请确保已经调用了 SDKInitializer.initialize(Context) 函数以提供全局 Context 信息。
//        mIconLocation= BitmapDescriptorFactory
//                .fromResource(R.drawable.location_marker);

        myOrientationListener=new MyOrientationListener(context);

        myOrientationListener.setOnOrientationListener(new MyOrientationListener.OnOrientationListener() {
            @Override
            public void onOrientationChanged(float x) {
                mCurrentX=x;
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.common:
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                break;
            case  R.id.satellite:
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                break;
            case  R.id.traffic:
                if (isTraffic){
                    mBaiduMap.setTrafficEnabled(false);

                    isTraffic =false;

                }
                else {
                    mBaiduMap.setTrafficEnabled(true);
                    isTraffic =true;
                }

                break;
            case  R.id.hot:
                if (isHot){
                    mBaiduMap.setBaiduHeatMapEnabled(false);
                    isHot =false;
                    ToastUtils.showToast("关闭热力");
                }
                else {
                    mBaiduMap.setBaiduHeatMapEnabled(true);

                    isHot =true;
                    ToastUtils.showToast("开启热力");
                }
                break;
        }
    }
    @Override
    public void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }
    @Override
    protected void onStart() {
        //开启定位
        mBaiduMap.setMyLocationEnabled(true);
        super.onStart();
        if (NetWorkUtils.isNetworkConnected(this)&&!mlocationClient.isStarted()) {
            mlocationClient.start();
            //开启方向传感器

        }else {
            Toast.makeText(this, "无网络", Toast.LENGTH_SHORT).show();
        }
        myOrientationListener.start();

    }
    @Override
    protected void onStop() {
        super.onStop();
        //关闭定位
        mBaiduMap.setMyLocationEnabled(false);
        mlocationClient.stop();
        //停止方向传感器
        myOrientationListener.stop();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}
