package com.macth.match.recommend.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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

import com.baidu.mapapi.map.MapView;
import com.bumptech.glide.Glide;
import com.lidong.photopicker.PhotoPickerActivity;
import com.lidong.photopicker.SelectModel;
import com.lidong.photopicker.intent.PhotoPickerIntent;
import com.macth.match.AppConfig;
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
    @Bind(R.id.mdca_img)
    ImageView mImg;
    @Bind(R.id.tv)
    TextView tv;
    @Bind(R.id.list)
    ListView list;
    private String fundsid;
    private ListAdapter listAdapter;
    PopupWindow popWindow;
    TextView mCamera, mPhoto, mExit;
    /* 请求识别码 */
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int REQUEST_CAMERA_CODE = 10;
    private ArrayList<String> imagePaths = new ArrayList<>();

    @Override
    protected int getContentResId() {
        return R.layout.activity_modifycapital;

    }

    @Override
    public void initView() {
        setEnsureText("完成");
        setTitleText("修改资金用途");
        fundsid = getIntent().getBundleExtra("bundle").getString("fundsid");
        LogUtils.e("fundsid----",fundsid);
        reqFundsDetails();

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
        ImageLoaderUtils.displayAvatarImage(result.getData().getImageurl()[0],mImg);
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
                    reqUpload();//上传资金用途
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

    private File imageFile;
    private void reqUpload() {
        ModifyCapitalDTO dto = new ModifyCapitalDTO();
        dto.setFundsid(fundsid);
        dto.setDesc(mEt01.getText().toString());
        dto.setAddress(mEt02.getText().toString());
        File[] files = (File[])mPic.toArray(new File[mPic.size()]);
        String id = "lbsimg";
        String ids = "pimg";
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
                LogUtils.e("mPic---", "" + mPic);

            }

            holder.image_sc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogUtils.confirm(ModifyCapitalActivity.this, "是否删除图片？", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
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
