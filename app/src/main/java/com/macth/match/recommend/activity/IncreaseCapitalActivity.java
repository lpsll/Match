package com.macth.match.recommend.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.macth.match.AppConfig;
import com.macth.match.R;
import com.macth.match.common.base.BaseTitleActivity;
import com.macth.match.common.http.CallBack;
import com.macth.match.common.http.CommonApiClient;
import com.macth.match.common.utils.DialogUtils;
import com.macth.match.common.utils.ImageLoaderUtils;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.recommend.dto.AddUseDTO;
import com.macth.match.recommend.dto.FundsDTO;
import com.macth.match.recommend.dto.UploadDTO;
import com.macth.match.recommend.entity.AddUseEvent;
import com.macth.match.recommend.entity.FundsResult;
import com.macth.match.recommend.entity.RecommendResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * 添加资金用途页
 */
public class IncreaseCapitalActivity extends BaseTitleActivity {
    @Bind(R.id.et_01)
    EditText mEt01;
    @Bind(R.id.et_02)
    EditText mEt02;
    @Bind(R.id.bmapView)
    MapView mBmapView;
    //定位都要通过LocationManager这个类实现
    private LocationManager locationManager;
    private String provider;
    boolean ifFrist = true;
    BaiduMap mBaiduMap;
    private String mLongitude,mLatitude;
    private String mId,flag,fundsid;

    @Override
    protected int getContentResId() {
        return R.layout.activity_increasecapital;

    }

    @Override
    public void initView() {

        setEnsureText("完成");
        mId = getIntent().getBundleExtra("bundle").getString("pid");
        if(null==getIntent().getBundleExtra("bundle").getString("flag")){
            setTitleText("增加资金用途");
        }else {
            setTitleText("修改资金用途");
            flag =getIntent().getBundleExtra("bundle").getString("flag");
            fundsid =getIntent().getBundleExtra("bundle").getString("fundsid");
            reqFundsDetails();
        }

    }

    private void reqFundsDetails() {
        FundsDTO dto = new FundsDTO();
        dto.setFundid(fundsid);
        CommonApiClient.funds(this, dto, new CallBack<FundsResult>() {
            @Override
            public void onSuccess(FundsResult result) {
                if (AppConfig.SUCCESS.equals(result.getCode())) {
                    LogUtils.e("获取资金用途详情成功");
                    setResult(result);

                }

            }
        });
    }

    private void setResult(FundsResult result) {
        mEt01.setText(result.getData().getFunds_desc());
        mEt02.setText(result.getData().getFunds_companyaddrress());
//        ImageLoaderUtils.displayImage(AppConfig.BASE_URL+result.getData().getFunds_images(), img01);
//        ImageLoaderUtils.displayImage(result.getData().getImageurl()[0], img02);
//        ImageLoaderUtils.displayImage(result.getData().getImageurl()[0], img03);
    }


    @Override
    public void initData() {
        mBaiduMap = mBmapView.getMap();

        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        //卫星地图
//        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);

        //空白地图, 基础地图瓦片将不会被渲染。在地图类型中设置为NONE，将不会使用流量下载基础地图瓦片图层。
        // 使用场景：与瓦片图层一起使用，节省流量，提升自定义瓦片图下载速度。
//        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NONE);

        // 设置可改变地图位置
        mBaiduMap.setMyLocationEnabled(true);
        //获取定位服务
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //判断GPS是否正常启动
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            Toast.makeText(this, "请开启GPS导航...", Toast.LENGTH_SHORT).show();
            //返回开启GPS导航设置界面
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent,0);
            return;
        }
        //获取当前可用的位置控制器
        List<String> list = locationManager.getProviders(true);
//        String str = locationManager.getBestProvider(getCriteria(),true);
        LogUtils.e("list---",""+list);
        if (list.contains(LocationManager.GPS_PROVIDER)) {
            //是否为GPS位置控制器
            provider = LocationManager.GPS_PROVIDER;
        } else if (list.contains(LocationManager.NETWORK_PROVIDER)) {
            //是否为网络位置控制器
            provider = LocationManager.NETWORK_PROVIDER;

        } else {
            Toast.makeText(this, "请检查网络或GPS是否打开",
                    Toast.LENGTH_LONG).show();
            return;
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LogUtils.e("provider---",""+provider);
        Location location = locationManager.getLastKnownLocation(provider);
        LogUtils.e("location---",""+location);
        if (location != null) {
            navigateTo(location);
        }
        //绑定定位事件，监听位置是否改变
        //第一个参数为控制器类型
        //第二个参数为监听位置变化的时间间隔（单位：毫秒）
        //第三个参数为位置变化的间隔（单位：米）
        // 第四个参数为位置监听器
        locationManager.requestLocationUpdates(provider, 5000, 1,
                locationListener);


    }


    private void navigateTo(Location location) {
        // 按照经纬度确定地图位置
        if (ifFrist) {
            LogUtils.e("getLatitude---",""+location.getLatitude());
            LogUtils.e("getLongitude---",""+location.getLongitude());
            LatLng ll = new LatLng(location.getLatitude(),
                    location.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
            // 移动到某经纬度
            mBaiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomBy(5f);
            // 放大
            mBaiduMap.animateMapStatus(update);

            ifFrist = false;
        }
        // 显示个人位置图标
        MyLocationData.Builder builder = new MyLocationData.Builder();
        builder.latitude(location.getLatitude());
        builder.longitude(location.getLongitude());
        MyLocationData data = builder.build();
        mBaiduMap.setMyLocationData(data);
    }

    LocationListener locationListener = new LocationListener() {

        @Override
        public void onStatusChanged(String arg0, int status, Bundle arg2) {
            switch (status) {
                //GPS状态为可见时
                case LocationProvider.AVAILABLE:
                    LogUtils.e("AVAILABLE----", "当前GPS状态为可见状态");
                    break;
                //GPS状态为服务区外时
                case LocationProvider.OUT_OF_SERVICE:
                    LogUtils.e("OUT_OF_SERVICE----", "当前GPS状态为服务区外状态");
                    break;
                //GPS状态为暂停服务时
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    LogUtils.e("TEMPORARILY_UNAVAILABLE----", "当前GPS状态为暂停服务状态");
                    break;
            }
        }

        @Override
        public void onProviderEnabled(String arg0) {

        }

        @Override
        public void onProviderDisabled(String arg0) {

        }

        @Override
        public void onLocationChanged(Location location) {
            LogUtils.e("时间：",""+location.getTime());
            LogUtils.e("经度：",""+location.getLongitude());
            LogUtils.e("纬度：",""+location.getLatitude());
            LogUtils.e("海拔：",""+location.getAltitude());
            mLongitude = String.valueOf(location.getLongitude());
            mLatitude= String.valueOf(location.getLatitude());
            // 位置改变则重新定位并显示地图
            navigateTo(location);
        }
    };



    @OnClick(R.id.bmapView)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.base_titlebar_ensure:
                if(TextUtils.isEmpty(mEt01.getText().toString())){
                    DialogUtils.showPrompt(this, "提示","项目介绍不能为空", "知道了");
                }
                else if(TextUtils.isEmpty(mEt02.getText().toString())){
                    DialogUtils.showPrompt(this, "提示","公司地址不能为空", "知道了");
                }
                else {
                    reqFunds();//地图截图
                }


                break;
            default:
                break;
        }
        super.onClick(v);
    }

    private void reqFunds() {
        mBaiduMap.snapshot(new BaiduMap.SnapshotReadyCallback() {
            @Override
            public void onSnapshotReady(Bitmap snapshot) {
                File file = new File("/mnt/sdcard/test.png");
                FileOutputStream out;
                try {
                    out = new FileOutputStream(file);
                    if (snapshot.compress(Bitmap.CompressFormat.PNG, 100,
                            out)) {
                        out.flush();
                        out.close();
                    }
                    LogUtils.e("file---",""+file);
                   reqUpload();//上传资金用途
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }



            }

        });


    }

    private void reqUpload() {
        UploadDTO dto = new UploadDTO();
        dto.setProjectno(mId);
        dto.setDesc(mEt01.getText().toString());
        dto.setAddress(mEt02.getText().toString());
        if(!TextUtils.isEmpty(mLongitude)){
            dto.setLbs(mLongitude+","+mLatitude);
            dto.setLbsimg("");
            dto.setPimg("");
        }
        CommonApiClient.upload(this, dto, new CallBack<RecommendResult>() {
            @Override
            public void onSuccess(RecommendResult result) {
                if (AppConfig.SUCCESS.equals(result.getCode())) {
                    LogUtils.e("上传项目资金用途成功");
                    EventBus.getDefault().post(
                            new AddUseEvent("ok"));
                    finish();

                }

            }
        });
    }


}
