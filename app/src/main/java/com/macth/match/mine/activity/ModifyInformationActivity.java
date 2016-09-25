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
import android.util.Log;
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
import com.macth.match.common.utils.CircleTransform;
import com.macth.match.common.utils.DialogUtils;
import com.macth.match.common.utils.ImageLoaderUtils;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.common.utils.PhoneUtils;
import com.macth.match.common.utils.PhotoSystemUtils;
import com.macth.match.common.utils.SelectPhotoDialogHelper;
import com.macth.match.mine.dto.AddInfoDTO;
import com.macth.match.mine.entity.InformationEntity;
import com.macth.match.mine.entity.MdInformationResult;
import com.macth.match.register.entity.Data;
import com.macth.match.register.entity.ShenFenEntity;
import com.squareup.picasso.Picasso;

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
    private SelectPhotoDialogHelper selectPhotoDialogHelper;//选择图片工具类
    private File imageFile;//选择的图像文件

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
        Picasso.with(this).load(Uri.parse(AppContext.get("userimager", ""))).transform(new CircleTransform()).error(R.drawable.morenxdpi_03).into(imgUserCard);
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
                selectPhotoDialogHelper = new SelectPhotoDialogHelper(this, new OnPickPhotoFinishListener(),300,1,1);
                selectPhotoDialogHelper.showPickPhotoDialog();
                break;


        }

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
        CommonApiClient.mdInfo(this, addInfoDto,imageFile, new CallBack<MdInformationResult>() {
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

    /*选择照片结束的回调*/
    private class OnPickPhotoFinishListener implements SelectPhotoDialogHelper.OnPickPhotoFinishListener {
        @Override
        public void onPickPhotoFinishListener(File imageFile) {
            if(imageFile!=null){
                ModifyInformationActivity.this.imageFile = imageFile;
                Log.e("imageFile---",""+imageFile);
                Picasso.with(ModifyInformationActivity.this).load(Uri.fromFile(imageFile)).transform(new CircleTransform()).into(imgUserCard);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(selectPhotoDialogHelper!=null){//选择图片
            selectPhotoDialogHelper.onActivityResult(requestCode, resultCode, data);
        }
    }

}
