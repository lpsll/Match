package com.macth.match.mine.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
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
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lidong.photopicker.PhotoPickerActivity;
import com.lidong.photopicker.SelectModel;
import com.lidong.photopicker.intent.PhotoPickerIntent;
import com.macth.match.AppConfig;
import com.macth.match.AppContext;
import com.macth.match.R;
import com.macth.match.common.base.BaseTitleActivity;
import com.macth.match.common.dto.BaseDTO;
import com.macth.match.common.entity.BaseEntity;
import com.macth.match.common.http.CallBack;
import com.macth.match.common.http.CommonApiClient;
import com.macth.match.common.http.UploadFileTask;
import com.macth.match.common.utils.BitmapToRound_Util;
import com.macth.match.common.utils.DialogUtils;
import com.macth.match.common.utils.ImageLoaderUtils;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.common.utils.PhoneUtils;
import com.macth.match.common.utils.PhotoSystemUtils;
import com.macth.match.mine.dto.AddInfoDTO;
import com.macth.match.mine.entity.InformationEntity;
import com.macth.match.mine.entity.MdInformationResult;
import com.macth.match.register.entity.Data;
import com.macth.match.register.entity.ShenFenEntity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 修改用户信息
 */
public class ModifyInformationActivity extends BaseTitleActivity {
    @Bind(R.id.cb_add_info_username)
    CheckBox cbAddInfoUsername;
    @Bind(R.id.et_add_info_username)
    EditText etAddInfoUsername;
    @Bind(R.id.ll_add_info_username)
    LinearLayout llAddInfoUsername;
    @Bind(R.id.cb_add_info_phone)
    CheckBox cbAddInfoPhone;
    @Bind(R.id.et_add_info_phone)
    TextView etAddInfoPhone;
    @Bind(R.id.ll_add_info_phone)
    LinearLayout llAddInfoPhone;
    @Bind(R.id.cb_add_info_shenfen)
    CheckBox cbAddInfoShenfen;
    @Bind(R.id.et_add_info_shenfen)
    TextView etAddInfoShenfen;
    @Bind(R.id.ll_add_info_shenfen)
    LinearLayout llAddInfoShenfen;
    @Bind(R.id.cb_add_info_company)
    CheckBox cbAddInfoCompany;
    @Bind(R.id.et_add_info_company)
    TextView etAddInfoCompany;
    @Bind(R.id.ll_add_info_company)
    LinearLayout llAddInfoCompany;
    @Bind(R.id.cb_add_info_work)
    CheckBox cbAddInfoWork;
    @Bind(R.id.et_add_info_work)
    TextView etAddInfoWork;
    @Bind(R.id.ll_add_info_work)
    LinearLayout llAddInfoWork;
    @Bind(R.id.cb_add_info_role)
    CheckBox cbAddInfoRole;
    @Bind(R.id.et_add_info_role)
    TextView etAddInfoRole;
    @Bind(R.id.ll_add_info_role)
    LinearLayout llAddInfoRole;
    @Bind(R.id.tv_add_info_ok)
    TextView tvAddInfoOk;
    @Bind(R.id.img_user_card)
    ImageView imgUserCard;


    PopupWindow popWindow;
    TextView mCamera,mPhoto,mExit;
    private Bitmap photo;
    /* 请求识别码 */
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int REQUEST_CAMERA_CODE = 10;

    private File mUrl;
    InformationEntity data;
    private ArrayList<String> imagePaths = new ArrayList<>();

    @Override
    protected int getContentResId() {
        return R.layout.activity_modifyinformation;
    }

    @Override
    public void initView() {
        setTitleText("修改用户信息");
        if (Build.VERSION.SDK_INT >= 23) {
            int readSDPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
            if (readSDPermission != PackageManager.PERMISSION_GRANTED) {
                LogUtils.e("readSDPermission",""+readSDPermission);
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        123);
            }
        }
        //设置框背景色
        setEdittextBackground();
        data = (InformationEntity) getIntent().getBundleExtra("bundle").getSerializable("entity");
        if(TextUtils.isEmpty(AppContext.get("imager", ""))){
            imgUserCard.setBackgroundResource(R.drawable.morenxdpi_03);
        }else {
            ImageLoaderUtils.displayAvatarImage(AppContext.get("imager", ""),imgUserCard);

        }
        etAddInfoUsername.setText(data.getUser_name());
        etAddInfoPhone.setText(data.getUser_mobile());
        etAddInfoShenfen.setText(data.getUser_identityname());
        etAddInfoCompany.setText(data.getUser_company());
        etAddInfoWork.setText(data.getUser_work());
        etAddInfoRole.setText(data.getUser_cooperativename());
    }



    /**
     * 输入框在输入字符以后背景变亮
     */
    private void setEdittextBackground() {
        cbAddInfoUsername.setClickable(false);
        etAddInfoUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    cbAddInfoUsername.setChecked(true);
                    llAddInfoUsername.setBackgroundResource(R.drawable.login_border_light);

                } else {
                    if (!TextUtils.isEmpty(etAddInfoUsername.getText().toString())) {
                        cbAddInfoUsername.setChecked(true);
                        llAddInfoUsername.setBackgroundResource(R.drawable.login_border_light);
                    } else {
                        cbAddInfoUsername.setChecked(false);
                        llAddInfoUsername.setBackgroundResource(R.drawable.login_border);
                    }

                }
            }
        });

    }

    @Override
    public void initData() {

    }


    @OnClick({R.id.tv_add_info_ok,R.id.img_user_card})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_add_info_ok:
                phoneVerify();
                break;
            case R.id.img_user_card:
                showPicPop();
                break;

            case R.id.btn_alter_pic_camera://拍照
                Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intentFromCapture, CODE_CAMERA_REQUEST);
                break;

            case R.id.btn_alter_pic_photo://选择照片
                PhotoPickerIntent intent = new PhotoPickerIntent(ModifyInformationActivity.this);
                intent.setSelectModel(SelectModel.MULTI);
                intent.setShowCarema(true); // 是否显示拍照
                intent.setMaxTotal(1); // 最多选择照片数量
                intent.setSelectedPaths(imagePaths); // 已选中的照片地址， 用于回显选中状态
                LogUtils.e("imagePaths---", "" + imagePaths);
                startActivityForResult(intent, REQUEST_CAMERA_CODE);
                break;

            case R.id.btn_alter_exit:  //取消
                backgroundAlpha(1f);
                popWindow.dismiss();
                break;
        }

    }


    private void showPicPop() {
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.from(this).inflate(R.layout.popup_pic, null);
        popWindow = new PopupWindow(view, WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT,true);

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
        LogUtils.e("parent---",""+parent);
        popWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        //添加pop窗口关闭事件
        popWindow.setOnDismissListener(new poponDismissListener());
    }
    public class poponDismissListener implements PopupWindow.OnDismissListener{

        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
            popWindow.dismiss();
        }

    }
    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }


    private void phoneVerify() {
        if (TextUtils.isEmpty(etAddInfoUsername.getText().toString())) {
            DialogUtils.showPrompt(this, "提示","用户名不能为空", "知道了");
        }else {
            addInfo();
        }



    }




    private void addInfo() {
        AddInfoDTO addInfoDto = new AddInfoDTO();
        addInfoDto.setUsermobile(data.getUser_mobile());
        addInfoDto.setUsername(etAddInfoUsername.getText().toString());
        addInfoDto.setIdentity(data.getUser_identity());
        addInfoDto.setCompany(data.getUser_company());
        addInfoDto.setWork(data.getUser_work());
        addInfoDto.setCooperative(data.getUser_cooperative());
        if(null==mUrl) {
        }else {
            addInfoDto.setUserimg(mUrl);
        }
        CommonApiClient.mdInfo(this, addInfoDto, new CallBack<MdInformationResult>() {
            @Override
            public void onSuccess(MdInformationResult result) {
                if (AppConfig.SUCCESS.equals(result.getCode())) {
                    LogUtils.e("修改用户信息成功");
                    AppContext.set("username",etAddInfoUsername.getText().toString());
                    AppContext.set("userimager",result.getData().getUserimg());
                    setResult(000001);
                    finish();
                }
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            //拍照
            case CODE_CAMERA_REQUEST:
                LogUtils.e("CODE_CAMERA_REQUEST----", "CODE_CAMERA_REQUEST");
                popWindow.dismiss();
                backgroundAlpha(1f);
                if (data == null||"".equals(data)) {
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

                    File file = new File("/sdcard/myImage/");
                    file.mkdirs();// 创建文件夹

                    String fileName = "/sdcard/myImage/" + name;
                    LogUtils.e("fileName", "" + fileName);
                    FileOutputStream b = null;
                    try {
                        b = new FileOutputStream(fileName);
                        bm.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    finally {
                        try {
                            b.flush();
                            b.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    Bitmap roundedCornerBitmap = ImageLoaderUtils.createCircleImage(bm, 200);
                    imgUserCard.setImageBitmap(roundedCornerBitmap);

                    mUrl = file;
                    LogUtils.e("mUrl---1--",""+mUrl);
                }
                break;

            // 选择照片
            case REQUEST_CAMERA_CODE:
                backgroundAlpha(1f);
                popWindow.dismiss();
                if(data == null){
                    return;
                }else{
                    ArrayList<String> list = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);
                    if(null==list){
                        return;
                    }else {
                        LogUtils.e("list.get(0)---", "" + list.get(0));
                        Bitmap bit = ImageLoaderUtils.readBitmap(list.get(0));
                        LogUtils.e("bit---", "" + bit);
                        Bitmap roundedCornerBitmap = ImageLoaderUtils.createCircleImage(bit, 200);
                        imgUserCard.setImageBitmap(roundedCornerBitmap);
                        File file = new File(list.get(0));
                        mUrl = file;
                        LogUtils.e("mUrl---2--",""+mUrl);

                    }
                }

                break;

        }
    }

}
