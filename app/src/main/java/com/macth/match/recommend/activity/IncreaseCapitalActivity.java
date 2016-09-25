package com.macth.match.recommend.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.bumptech.glide.Glide;
import com.lidong.photopicker.PhotoPickerActivity;
import com.lidong.photopicker.SelectModel;
import com.lidong.photopicker.intent.PhotoPickerIntent;
import com.macth.match.AppConfig;
import com.macth.match.AppContext;
import com.macth.match.R;
import com.macth.match.common.base.BaseTitleActivity;
import com.macth.match.common.http.CallBack;
import com.macth.match.common.http.CommonApiClient;
import com.macth.match.common.utils.DialogUtils;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.common.utils.PhotoSystemUtils;
import com.macth.match.common.utils.SelectPhotoDialogHelper;
import com.macth.match.recommend.dto.UploadDTO;
import com.macth.match.recommend.entity.AddUseEvent;
import com.macth.match.recommend.entity.ImgEntity;
import com.macth.match.recommend.entity.RecommendResult;
import com.qluxstory.ptrrecyclerview.BaseSimpleRecyclerAdapter;

import org.json.JSONArray;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import de.greenrobot.event.EventBus;

/**
 * 添加资金用途页
 */
public class IncreaseCapitalActivity extends BaseTitleActivity {

    @Bind(R.id.et_01)
    EditText et01;
    @Bind(R.id.et_02)
    EditText et02;
    @Bind(R.id.tv)
    TextView tv;
    @Bind(R.id.bmapView)
    MapView mBmapView;
    @Bind(R.id.list)
    ListView list;
    //定位都要通过LocationManager这个类实现
    private LocationManager locationManager;
    private String provider;
    boolean ifFrist = true;
    BaiduMap mBaiduMap;
    private String mLongitude, mLatitude;
    private String mId;
    BaseSimpleRecyclerAdapter mListAdapter;
    private ArrayList<String> imagePaths = new ArrayList<>();
    private SelectPhotoDialogHelper selectPhotoDialogHelper;//选择图片工具类
    PopupWindow popWindow;
    TextView mCamera, mPhoto, mExit;
    /* 请求识别码 */
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int REQUEST_CAMERA_CODE = 10;
    ImgEntity entity;
    private ListAdapter listAdapter;
    private ArrayList<File> mPic= new ArrayList<>();
    private File imageFile;//地图文件
    private List<File> imgListFile;
    public LocationClient mLocationClient = null;

    @Override
    protected int getContentResId() {
        return R.layout.activity_increasecapital;

    }

    @Override
    public void initView() {

        LogUtils.e("initView---", "initView");
        if (Build.VERSION.SDK_INT >= 23) {
            int readSDPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
            if (readSDPermission != PackageManager.PERMISSION_GRANTED) {
                LogUtils.e("readSDPermission", "" + readSDPermission);
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        123);
            }
        }
        setEnsureText("完成");
        mId = getIntent().getBundleExtra("bundle").getString("pid");
        setTitleText("添加资金用途");
//        increasercapitalList.setLayoutManager(new FullyLinearLayoutManager(this));
//        mListAdapter = new BaseSimpleRecyclerAdapter<ImgEntity>() {
//            @Override
//            public int getItemViewLayoutId() {
//                return R.layout.item_increasecapital;
//            }
//
//            @Override
//            public void bindData(BaseRecyclerViewHolder holder, ImgEntity imaEntity, final int position) {
//                ImageView img = holder.getView(R.id.item_in_img);
//                if (imaEntity.getImg().get(position).equals("000000")) {
//                    img.setImageResource(R.drawable.paizhaoxdpi_03);
//                } else {
//                    Glide.with(IncreaseCapitalActivity.this)
//                            .load(imaEntity.getImg().get(position))
//                            .placeholder(R.mipmap.default_error)
//                            .error(R.mipmap.default_error)
//                            .centerCrop()
//                            .crossFade()
//                            .into(img);
//                }
//            }
//
//
//        };
//
//        mListAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnRecyclerViewItemClickListener() {
//            @Override
//            public void onItemClick(View itemView, Object itemBean, int position) {
////                selectPhotoDialogHelper = new SelectPhotoDialogHelper(this, new OnPickPhotoFinishListener(),300,1,1);
////                selectPhotoDialogHelper.showPickPhotoDialog();
//                entity = (ImgEntity) itemBean;
//                String imgs = entity.getImg().get(position);
//                LogUtils.e("imgs----", "" + imgs);
//                if ("000000".equals(imgs)) {
//                    showPicPop();
//                } else {
//                    return;
//                }
//
//            }
//        });


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String imgs = (String) parent.getItemAtPosition(position);
                LogUtils.e("imgs----", "" + imgs);
                if ("000000".equals(imgs)) {
                    showPicPop();
                } else {
                    return;
                }
            }
        });
    }


    /**
     * -------------------------------
     */

    private static final int UPDATE_TIME = 5000;
    private static int LOCATION_COUTNS = 0;
    public LocationClient locationClient = null;
    private double Latitude;
    private double Longitude;




    /**
     * -------------------------------
     */




    @Override
    public void initData() {
        LogUtils.e("initData---", "initData");
        imagePaths.add("000000");
        listAdapter = new ListAdapter(imagePaths);
        list.setAdapter(listAdapter);
        setListViewHeightBasedOnChildren(list);
//        entity = new ImgEntity();
//        entity.setImg(imagePaths);
//        RecyclerViewHeader header = (RecyclerViewHeader) findViewById(R.id.header);
//        header.attachTo(increasercapitalList, true);
//        increasercapitalList.setAdapter(mListAdapter);
//        mListAdapter.removeAll();
//        mListAdapter.append(entity);
//        mEt01 = (EditText) header.findViewById(R.id.et_01);
//        mEt02 = (EditText) header.findViewById(R.id.et_02);
//        mBmapView = (MapView) header.findViewById(R.id.bmapView);


         /**
         * ----------------------------------------------
        */


        locationClient = new LocationClient(this);
        // 设置定位条件
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 是否打开GPS
        option.setCoorType("bd09ll"); // 设置返回值的坐标类型。
        option.setPriority(LocationClientOption.NetWorkFirst); // 设置定位优先级
        option.setProdName("LocationDemo"); // 设置产品线名称。强烈建议您使用自定义的产品线名称，方便我们以后为您提供更高效准确的定位服务。
        option.setScanSpan(UPDATE_TIME);// 设置定时定位的时间间隔。单位毫秒
        locationClient.setLocOption(option);

        locationClient.start();
			/*
			 * 当所设的整数值大于等于1000（ms）时，定位SDK内部使用定时定位模式。调用requestLocation(
			 * )后，每隔设定的时间，定位SDK就会进行一次定位。如果定位SDK根据定位依据发现位置没有发生变化，就不会发起网络请求，
			 * 返回上一次定位的结果；如果发现位置改变，就进行网络请求进行定位，得到新的定位结果。
			 * 定时定位时，调用一次requestLocation，会定时监听到定位结果。
			 */
        locationClient.requestLocation();

        // 注册位置监听器
        locationClient.registerLocationListener(new BDLocationListener() {

            @Override
            public void onReceiveLocation(BDLocation location) {
                // TODO Auto-generated method stub
                if (location == null) {
                    return;
                }
                Latitude = location.getLatitude();
                Longitude = location.getLongitude();

                LogUtils.e("Latitude========="+Latitude);
                LogUtils.e("Longitude========="+Longitude);

                StringBuffer sb = new StringBuffer(256);
                sb.append("Time : ");
                sb.append(location.getTime());
                sb.append("\nError code : ");
                sb.append(location.getLocType());
                sb.append("\nLatitude : ");
                sb.append(location.getLatitude());
                sb.append("\nLontitude : ");
                sb.append(location.getLongitude());
                sb.append("\nRadius : ");
                sb.append(location.getRadius());
                if (location.getLocType() == BDLocation.TypeGpsLocation) {
                    sb.append("\nSpeed : ");
                    sb.append(location.getSpeed());
                    sb.append("\nSatellite : ");
                    sb.append(location.getSatelliteNumber());
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                    sb.append("\nAddress : ");
                    sb.append(location.getAddrStr());
                }
                LOCATION_COUTNS++;
                sb.append("\n检查位置更新次数：");
                sb.append(String.valueOf(LOCATION_COUTNS));


                navigateTo(Latitude,Longitude);

            }

        });



        /**
         * -------------------------------------------------------------
         */






//
        mBaiduMap = mBmapView.getMap();
//        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
//        //卫星地图
//        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
//
//        //空白地图, 基础地图瓦片将不会被渲染。在地图类型中设置为NONE，将不会使用流量下载基础地图瓦片图层。
//        // 使用场景：与瓦片图层一起使用，节省流量，提升自定义瓦片图下载速度。
////        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NONE);
//
//        // 设置可改变地图位置
        mBaiduMap.setMyLocationEnabled(true);
//        //获取定位服务
//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        //判断GPS是否正常启动
//        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//            Toast.makeText(this, "请开启GPS导航...", Toast.LENGTH_SHORT).show();
//            //返回开启GPS导航设置界面
//            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//            startActivityForResult(intent, 0);
//            return;
//        }
//        //获取当前可用的位置控制器
//        List<String> list = locationManager.getProviders(true);
////        String str = locationManager.getBestProvider(getCriteria(),true);
//        LogUtils.e("list---", "" + list);
//        if (list.contains(LocationManager.GPS_PROVIDER)) {
//            //是否为GPS位置控制器
//            provider = LocationManager.GPS_PROVIDER;
//        } else if (list.contains(LocationManager.NETWORK_PROVIDER)) {
//            //是否为网络位置控制器
//            provider = LocationManager.NETWORK_PROVIDER;
//
//        } else {
//            Toast.makeText(this, "请检查网络或GPS是否打开",
//                    Toast.LENGTH_LONG).show();
//            return;
//        }
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        LogUtils.e("provider---", "" + provider);
//        Location location = locationManager.getLastKnownLocation(provider);
//        LogUtils.e("location---", "" + location);
//        if (location != null) {
//            navigateTo(location);
//        }
        //绑定定位事件，监听位置是否改变
        //第一个参数为控制器类型
        //第二个参数为监听位置变化的时间间隔（单位：毫秒）
        //第三个参数为位置变化的间隔（单位：米）
        // 第四个参数为位置监听器
//        locationManager.requestLocationUpdates(provider, 5000, 1,
//                locationListener);
//        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
//        LogUtils.e("mLocationClient----",""+mLocationClient);
//        requestLocationInfo();//发请定位
    }

    /**
     * 动态设置ListView的高度
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        if(listView == null) return;
        ListAdapter listAdapter = (ListAdapter) listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    /**
     * 发起定位
     */
    public void requestLocationInfo(){
        LogUtils.e("requestLocationInfo----","requestLocationInfo");

        setLocationOption();

        if (mLocationClient != null && !mLocationClient.isStarted()){
            mLocationClient.start();
        }
        if (mLocationClient != null && mLocationClient.isStarted()){
            mLocationClient.requestLocation();
        }
    }

    /**
     * 设置相关参数
     */
    private void setLocationOption(){
        LogUtils.e("setLocationOption----","setLocationOption");

        mLocationClient.registerLocationListener( new MyLocationListener());    //注册监听函数
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);        //是否打开GPS
        option.setCoorType("bd09ll");       //设置返回值的坐标类型。
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setProdName("LocationDemo"); //设置产品线名称。强烈建议您使用自定义的产品线名称，方便我们以后为您提供更高效准确的定位服务。

        mLocationClient.setLocOption(option);
    }

    class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null) {
                LogUtils.e("location_failed----","location_failed");
                return;
            } else {
                int locType = location.getLocType();
                LogUtils.e("locType:",""+locType);
                LogUtils.e("province:","" + location.getProvince());
                LogUtils.e("city:" ,""+ location.getCity());
                LogUtils.e("district:" ,""+ location.getDistrict());
                LogUtils.e("AddrStr:" ,""+ location.getAddrStr());
                String city = location.getCity();
                String province = location.getProvince();
                String district = location.getDistrict();
                if (TextUtils.isEmpty(city)) {
                    LogUtils.e("locType----","定位失败");
                    mLocationClient.start();
                } else {
                    Latitude = location.getLatitude();
                    Longitude = location.getLongitude();
                    LogUtils.e("Latitude----",""+Latitude);
                    LogUtils.e("Longitude----",""+Longitude);
                    navigateTo(Latitude,Longitude);
                    mLocationClient.stop();
                }
            }

        }
    }


//    private void navigateTo(Location location) {
    private void navigateTo(double Latitude,double Longitude) {
        // 按照经纬度确定地图位置
        if (ifFrist) {
//            LogUtils.e("getLatitude---", "" + location.getLatitude());
//            LogUtils.e("getLongitude---", "" + location.getLongitude());
            LatLng ll = new LatLng(Latitude,
                    Longitude);
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
//        builder.latitude(location.getLatitude());
//        builder.longitude(location.getLongitude());
        builder.latitude(Latitude);
        builder.longitude(Longitude);

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
            LogUtils.e("时间：", "" + location.getTime());
            LogUtils.e("经度：", "" + location.getLongitude());
            LogUtils.e("纬度：", "" + location.getLatitude());
            LogUtils.e("海拔：", "" + location.getAltitude());
            mLongitude = String.valueOf(location.getLongitude());
            mLatitude = String.valueOf(location.getLatitude());
            // 位置改变则重新定位并显示地图
            navigateTo(location.getLatitude(),location.getLongitude());
        }
    };


    private void showPicPop() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.from(this).inflate(R.layout.popup_pic, null);
        popWindow = new PopupWindow(view, WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true);

        // 需要设置一下此参数，点击外边可消失
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置点击窗口外边窗口消失
        popWindow.setOutsideTouchable(true);
        // 设置此参数获得焦点，否则无法点击
        popWindow.setFocusable(true);
        //防止虚拟软键盘被弹出菜单遮住
        popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        backgroundAlpha(0.7f);

        mCamera = (TextView) view.findViewById(R.id.btn_alter_pic_camera);
        mPhoto = (TextView) view.findViewById(R.id.btn_alter_pic_photo);
        mExit = (TextView) view.findViewById(R.id.btn_alter_exit);
        mCamera.setOnClickListener(this);
        mPhoto.setOnClickListener(this);
        mExit.setOnClickListener(this);

        View parent = getWindow().getDecorView();//高度为手机实际的像素高度
        LogUtils.e("parent---", "" + parent);
        popWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        //添加pop窗口关闭事件
        popWindow.setOnDismissListener(new poponDismissListener());
    }


    public class poponDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
            popWindow.dismiss();
        }

    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.base_titlebar_ensure:
                if (TextUtils.isEmpty(et01.getText().toString())) {
                    DialogUtils.showPrompt(this, "提示", "项目介绍不能为空", "知道了");
                } else if (TextUtils.isEmpty(et02.getText().toString())) {
                    DialogUtils.showPrompt(this, "提示", "公司地址不能为空", "知道了");
                } else {
                    reqFunds();//地图截图
                }

                break;
            case R.id.btn_alter_pic_camera://拍照
                Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intentFromCapture, CODE_CAMERA_REQUEST);

                break;

            case R.id.btn_alter_pic_photo://选择照片

                PhotoPickerIntent intent = new PhotoPickerIntent(IncreaseCapitalActivity.this);
                intent.setSelectModel(SelectModel.MULTI);
                intent.setShowCarema(true); // 是否显示拍照
                intent.setMaxTotal(8); // 最多选择照片数量，默认为6
                intent.setSelectedPaths(imagePaths); // 已选中的照片地址， 用于回显选中状态
                LogUtils.e("imagePaths---", "" + imagePaths);
                startActivityForResult(intent, REQUEST_CAMERA_CODE);

                break;
            case R.id.btn_alter_exit:
                backgroundAlpha(1f);
                popWindow.dismiss();
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
                    LogUtils.e("file---", "" + file);
                    imageFile = file;
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
        dto.setDesc(et01.getText().toString());
        dto.setAddress(et02.getText().toString());
        if (!TextUtils.isEmpty(mLongitude)) {
            dto.setLbs(mLongitude + "," + mLatitude);

        }
        String id ="pimg";
        CommonApiClient.upload(this, dto, imageFile,mPic,id,new CallBack<RecommendResult>() {
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


    //    /*选择照片结束的回调*/
//    private class OnPickPhotoFinishListener implements SelectPhotoDialogHelper.OnPickPhotoFinishListener {
//        @Override
//        public void onPickPhotoFinishListener(File imageFile) {
//            if(imageFile!=null){
//                IncreaseCapitalActivity.this.imageFile = imageFile;
//                Log.e("imageFile---",""+imageFile);
//                Picasso.with(IncreaseCapitalActivity.this).load(Uri.fromFile(imageFile)).transform(new CircleTransform()).into(imgUserCard);
//            }
//        }
//    }
//
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if(selectPhotoDialogHelper!=null){//选择图片
//            selectPhotoDialogHelper.onActivityResult(requestCode, resultCode, data);
//        }
        switch (requestCode) {

            //拍照
            case CODE_CAMERA_REQUEST:
                LogUtils.e("CODE_CAMERA_REQUEST----", "CODE_CAMERA_REQUEST");
                popWindow.dismiss();
                backgroundAlpha(1f);
                if (data == null || "".equals(data)) {
                    LogUtils.e("data----CODE_CAMERA_REQUEST", "" + data);
                    return;
                } else {
                    LogUtils.e("data----else", "else");
                    String sdStatus = Environment.getExternalStorageState();
                    if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
                        LogUtils.e("TestFile", "SD card is not avaiable/writeable right now.");
                        return;
                    }
                    String name = new DateFormat().format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
                    LogUtils.e("name", "" + name);
                    Bundle bundle = data.getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
                    LogUtils.e("bitmap---", "" + bitmap);
                    Bitmap bm = PhotoSystemUtils.comp(bitmap);
                    LogUtils.e("bm---", "" + bm);

                    File file = new File("/sdcard/myImage/");
                    file.mkdirs();// 创建文件夹
                    String fileName = "/sdcard/myImage/" + name;
                    LogUtils.e("fileName", "" + fileName);

                    FileOutputStream b = null;
                    try {
                        b = new FileOutputStream(fileName);
                        LogUtils.e("b---", "" + b);
                        bm.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
                        LogUtils.e("bm.compress---", "" + bm);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            b.flush();
                            b.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    LogUtils.e("imagePaths----if---", "" + imagePaths);


                    if (imagePaths.size() == 1) {
                        imagePaths.clear();
                        imagePaths.add(fileName);
                        imagePaths.add("000000");
                        LogUtils.e("imagePaths----1", "" + imagePaths);
                    } else if (imagePaths.size() > 1) {
                        imagePaths.set(imagePaths.size() - 1, fileName);
                        imagePaths.add("000000");
                        LogUtils.e("imagePaths----set----", "   " + (imagePaths.size() - 1) + "---" + imagePaths);
                    }
                    listAdapter = new ListAdapter(imagePaths);
                    list.setAdapter(listAdapter);
                    setListViewHeightBasedOnChildren(list);

                }
                break;

            // 选择照片
            case REQUEST_CAMERA_CODE:
                backgroundAlpha(1f);
                popWindow.dismiss();
                if (data == null) {
                    return;
                } else {
                    ArrayList<String> list = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);
                    LogUtils.e("list: ", "list = " + list + "--size = " + list.size());
                    if (null == list) {
                        return;
                    } else {
                        loadAdpater(list);
                    }
                }

                break;

            default:
                break;

        }

    }

    private void loadAdpater(ArrayList<String> paths) {
        if (imagePaths != null && imagePaths.size() > 0) {
            imagePaths.clear();
        }
        if (paths.contains("000000")) {
            paths.remove("000000");
        }
        paths.add("000000");
        LogUtils.e("paths----", "" + paths);
        imagePaths.addAll(paths);
//        entity.setImg(imagePaths);
//        mListAdapter.removeAll();
//        mListAdapter.append(entity);
        listAdapter = new ListAdapter(imagePaths);
        list.setAdapter(listAdapter);
        setListViewHeightBasedOnChildren(list);

        try {
            JSONArray obj = new JSONArray(imagePaths);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    boolean flag = false;

    private class ListAdapter extends BaseAdapter {
        private ArrayList<String> listUrls;
        private LayoutInflater inflater;

        public ListAdapter(ArrayList<String> listUrls) {
            this.listUrls = listUrls;
            if (listUrls.size() == 9) {
                LogUtils.e("listUrls---remove----1---", "" + listUrls);
                listUrls.remove(listUrls.size() - 1);
                flag = true;
                LogUtils.e("listUrls---remove----2---", "" + listUrls);
            }
            inflater = LayoutInflater.from(IncreaseCapitalActivity.this);
        }

        public int getCount() {
            return listUrls.size();
        }

        @Override
        public String getItem(int position) {
            return listUrls.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.item_increasecapital, parent, false);
                holder.image = (ImageView) convertView.findViewById(R.id.item_in_img);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final String path = listUrls.get(position);
            LogUtils.e("listUrls---", "" + listUrls);
            LogUtils.e("listUrls.size()---", "" + listUrls.size());

            if (listUrls.size() > 1) {
                mPic.clear();
                for (int i = 0; i < listUrls.size() - 1; i++) {
                    mPic.add(new File(listUrls.get(i)));
                }
                if (listUrls.size() == 8 && flag == true) {
                    mPic.add(new File(listUrls.get(listUrls.size() - 1)));
                }
                LogUtils.e("mPic---", "" + mPic);

            }

            if (path.equals("000000")) {
                holder.image.setImageResource(R.drawable.paizhaoxdpi_03);
            } else {
                Glide.with(IncreaseCapitalActivity.this)
                        .load(path)
                        .placeholder(R.mipmap.default_error)
                        .error(R.mipmap.default_error)
                        .centerCrop()
                        .crossFade()
                        .into(holder.image);
            }
            return convertView;
        }

        class ViewHolder {
            ImageView image;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLocationClient != null && mLocationClient.isStarted()) {
            mLocationClient.stop();
            mLocationClient = null;
        }
    }


}
