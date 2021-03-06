package com.macth.match.recommend.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
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
import com.macth.match.MainActivity;
import com.macth.match.R;
import com.macth.match.common.base.BaseTitleActivity;
import com.macth.match.common.http.CallBack;
import com.macth.match.common.http.CommonApiClient;
import com.macth.match.common.utils.DialogUtils;
import com.macth.match.common.utils.ImageLoaderUtils;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.common.utils.PhotoSystemUtils;
import com.macth.match.recommend.dto.FundsDTO;
import com.macth.match.recommend.dto.ModifyCapitalDTO;
import com.macth.match.recommend.dto.UploadDTO;
import com.macth.match.recommend.entity.AddUseEvent;
import com.macth.match.recommend.entity.FundsResult;
import com.macth.match.recommend.entity.MilDetailsResult;

import org.json.JSONArray;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * 修改资金用途页
 */
public class ModifyCapitalActivity extends BaseTitleActivity {
    @Bind(R.id.et_01)
    EditText mEt01;
    @Bind(R.id.et_02)
    EditText mEt02;
    @Bind(R.id.tv)
    TextView tv;
    @Bind(R.id.list)
    ListView list;
    @Bind(R.id.bmapView)
    MapView mBmapView;
    private String fundsid;
    private ListAdapter listAdapter;
    PopupWindow popWindow;
    TextView mCamera, mPhoto, mExit;
    /* 请求识别码 */
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int REQUEST_CAMERA_CODE = 10;
    private ArrayList<String> imagePaths = new ArrayList<>();
    boolean isFrist;
    BaiduMap mBaiduMap;
    public LocationClient mLocationClient = null;
    boolean ifFrist = true;


    @Override
    protected int getContentResId() {
        return R.layout.activity_modifycapital;

    }

    @Override
    public void initView() {
        if (Build.VERSION.SDK_INT >= 23) {
            int readSDPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
            if (readSDPermission != PackageManager.PERMISSION_GRANTED) {
                LogUtils.e("readSDPermission", "" + readSDPermission);
                ActivityCompat.requestPermissions(this, new String[]{
                                Manifest.permission.CAMERA,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.READ_PHONE_STATE},

                        123);
            }
        }
        isFrist = true;
        setEnsureText("完成");
        setTitleText("修改资金用途");
        fundsid = getIntent().getBundleExtra("bundle").getString("fundsid");
        LogUtils.e("fundsid----",fundsid);
        initBaiduMap();
        reqFundsDetails();

    }

    private void initBaiduMap() {
        mBaiduMap = mBmapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setMyLocationEnabled(true);

        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        initLocation();//初始化定位
    }

    private void initLocation() {
        LogUtils.e("initLocation", "initLocation");
        requestLocationInfo();//发请定位
    }

    private void requestLocationInfo() {
        LogUtils.e("requestLocationInfo----", "requestLocationInfo");
        mLocationClient.registerLocationListener(new MyLocationListener());    //注册监听函数
        setLocationOption();
        if (mLocationClient != null && !mLocationClient.isStarted()) {

            mLocationClient.start();
        }
        if (mLocationClient != null && mLocationClient.isStarted()) {
            mLocationClient.requestLocation();
        }
    }

    private void setLocationOption() {
        LogUtils.e("setLocationOption----", "setLocationOption");

        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);        //是否打开GPS
        option.setCoorType("bd09ll");       //设置返回值的坐标类型。
        option.setAddrType("all");//返回的定位结果包含地址信息
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setProdName("Cuohe"); //设置产品线名称。强烈建议您使用自定义的产品线名称，方便我们以后为您提供更高效准确的定位服务。
        option.setScanSpan(5000);//设置发起定位请求的间隔时间为5000ms
        option.disableCache(true);//禁止启用缓存定位

        mLocationClient.setLocOption(option);
    }

    class MyLocationListener implements BDLocationListener {
        StringBuffer sb = new StringBuffer(256);

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null) {
                mLocationClient.start();
                LogUtils.e("location_failed----", "location_failed");
                return;
            }
            else {
                LogUtils.e("getLocType----", ""+BDLocation.TypeGpsLocation);
                if (location.getLocType() == BDLocation.TypeGpsLocation) {
                    LogUtils.e("TypeGpsLocation----", "gps定位成功");
                    int locType = location.getLocType();
                    LogUtils.e("locType:", "" + locType);
                    LogUtils.e("getLatitude:", "" + location.getLatitude());
                    LogUtils.e("getLongitude:", "" + location.getLongitude());
                    if (TextUtils.isEmpty(String.valueOf(location.getLatitude()))) {

                        mLocationClient.start();
                    } else {
                        initNavigato(location);

                    }


                }
                else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                    sb.append("\naddr : ");
                    sb.append(location.getAddrStr());
                    //运营商信息
                    sb.append("\noperationers : ");
                    sb.append(location.getOperators());
                    sb.append("\ndescribe : ");
                    sb.append("网络定位成功");
                    initNavigato(location);
                }
                else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                    sb.append("\ndescribe : ");
                    sb.append("离线定位成功，离线定位结果也是有效的");
                    initNavigato(location);
                }
                else if (location.getLocType() == BDLocation.TypeServerError) {
                    sb.append("\ndescribe : ");
                    sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
                }
                else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    sb.append("\ndescribe : ");
                    sb.append("网络不同导致定位失败，请检查网络是否通畅");
                }
                else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    sb.append("\ndescribe : ");
                    sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                }
                LogUtils.e("sb----------:", "" + sb.toString());

            }

        }

    }

    private void initNavigato(BDLocation location) {
        AppContext.set("radius", String.valueOf(location.getRadius()));
        AppContext.set("latitude", String.valueOf(location.getLatitude()));
        AppContext.set("longitude", String.valueOf(location.getLongitude()));
        String latitude = AppContext.get("latitude", "");
        String longitude = AppContext.get("longitude", "");
        String radius = AppContext.get("radius", "");
        navigateTo(location, Double.parseDouble(latitude.toString()), Double.parseDouble(longitude), Float.parseFloat(radius));
        mLocationClient.stop();
    }

    private void navigateTo(BDLocation location, double Latitude, double Longitude, float radius) {
        LogUtils.e("navigateTo---radius", "" + radius);
        LogUtils.e("navigateTo---Latitude", "" + Latitude);
        LogUtils.e("navigateTo---Longitude", "" + Longitude);
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 构造定位数据,显示个人位置图标
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(radius)
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(100).latitude(Latitude)
                .longitude(Longitude).build();
        // 设置定位数据
        mBaiduMap.setMyLocationData(locData);

//        // 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
//        mCurrentMarker = BitmapDescriptorFactory
//                .fromResource(R.drawable.icon_geo);
//        MyLocationConfiguration config = new MyLocationConfiguration(mCurrentMode, true, mCurrentMarker);

        // 按照经纬度确定地图位置
        if (ifFrist) {
            ifFrist = false;
            LatLng ll = new LatLng(Latitude,
                    Longitude);
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLngZoom(ll,16);
            // 移动到某经纬度,放大
            mBaiduMap.animateMapStatus(update);
            showPopup(location);

        }

    }

    private View popWon;
    private void showPopup(BDLocation location) {
        popWon = LayoutInflater.from(this).inflate(R.layout.pop_baidu_item, null);
        TextView popText = ((TextView)popWon.findViewById(R.id.location_tips));
        LogUtils.e("location.getAddrStr()----",""+location.getAddrStr());
        popText.setText("[我的位置]\n" + location.getAddrStr());
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

        for(int i=1;i<result.getData().getImageurl().length;i++){
            imagePaths.add(result.getData().getImageurl()[i]);
        }
        imagePaths.add("000000");
        LogUtils.e("setResult--imagePaths----", "" + imagePaths);
        listAdapter = new ListAdapter(imagePaths);
        list.setAdapter(listAdapter);
        setListViewHeightBasedOnChildren(list);
    }

    /**
     * 动态设置ListView的高度
     *
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        if (listView == null) return;
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



    @Override
    public void initData() {

    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.base_titlebar_ensure:
                if (TextUtils.isEmpty(mEt01.getText().toString())) {
                    DialogUtils.showPrompt(this, "提示", "项目介绍不能为空", "知道了");
                } else if (TextUtils.isEmpty(mEt02.getText().toString())) {
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

                PhotoPickerIntent intent = new PhotoPickerIntent(ModifyCapitalActivity.this);
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
                    isFrist = true;
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
                    isFrist = true;
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

    Runnable networkTask = new Runnable() {

        @Override
        public void run() {
            // 在这里进行 http request.网络请求相关操作
            Bitmap bmp = null;
            LogUtils.e("imagePaths--1",""+imagePaths);
            for(int i = 0;i<imagePaths.size()-1;i++){
                String str1 = imagePaths.get(i);
                LogUtils.e("str1--",""+str1);
                String str2 = ""+i;
                try {
                    bmp = ImageLoaderUtils.returnBitmap(str1);
                    LogUtils.e("bmp---", "0--"+bmp);
                    String string = ImageLoaderUtils.saveJPGFile(bmp, str2);
                    if(!TextUtils.isEmpty(string)){
                        mPic.set(i,new File(string));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            LogUtils.e("mPic--2",""+mPic);
            Message msg = new Message();
            msg.what = 1;
            handler.sendMessage(msg);

        }
    };

    /**
     * 网络操作相关的子线程
     */


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    isFrist = false;
                    break;
            }
        }
    };

    private File imageFile;
    private void reqUpload() {
        ModifyCapitalDTO dto = new ModifyCapitalDTO();
        dto.setFundsid(fundsid);
        dto.setDesc(mEt01.getText().toString());
        dto.setAddress(mEt02.getText().toString());
        LogUtils.e("mPic---",""+mPic);
        File[] files = (File[])mPic.toArray(new File[mPic.size()]);
        String id = "lbsimg";
        String ids = "pimg";
        LogUtils.e("imageFile---",""+imageFile);
        LogUtils.e("files---",""+files);
        CommonApiClient.upMdCaoital(this, dto,imageFile,id,files, ids,  new CallBack<MilDetailsResult>() {
            @Override
            public void onSuccess(MilDetailsResult result) {
                if (AppConfig.SUCCESS.equals(result.getCode())) {
                    LogUtils.e("修改项目资金用途成功");
                    EventBus.getDefault().post(
                            new AddUseEvent("ok"));
                    finish();

                }

            }
        });
    }




    boolean flag = false;
    private ArrayList<File> mPic = new ArrayList<>();
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
            inflater = LayoutInflater.from(ModifyCapitalActivity.this);
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.item_increasecapital, parent, false);
                holder.image = (ImageView) convertView.findViewById(R.id.item_in_img);
                holder.image_sc = (ImageView) convertView.findViewById(R.id.item_img_sc);
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
                new Thread(networkTask).start();
                LogUtils.e("mPic---", "" + mPic);

            }
            if(isFrist){
                LogUtils.e("Thread---", "Thread");
                new Thread(networkTask).start();
                isFrist = false;
            }

            holder.image_sc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogUtils.confirm(ModifyCapitalActivity.this, "是否删除图片？", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            isFrist = true;
                            listUrls.remove(position);
                            notifyDataSetChanged();
                        }
                    });
                }
            });
            if (path.equals("000000")) {
                holder.image.setImageResource(R.drawable.paizhaoxdpi_03);
                holder.image_sc.setVisibility(View.GONE);
            } else {
                holder.image_sc.setVisibility(View.VISIBLE);
                Glide.with(ModifyCapitalActivity.this)
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
            ImageView image_sc;
        }
    }


}
