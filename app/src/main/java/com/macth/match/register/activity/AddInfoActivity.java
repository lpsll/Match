package com.macth.match.register.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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

import com.lidong.photopicker.PhotoPickerActivity;
import com.lidong.photopicker.SelectModel;
import com.lidong.photopicker.intent.PhotoPickerIntent;
import com.macth.match.AppConfig;
import com.macth.match.R;
import com.macth.match.common.base.BaseTitleActivity;
import com.macth.match.common.dto.BaseDTO;
import com.macth.match.common.http.CallBack;
import com.macth.match.common.http.CommonApiClient;
import com.macth.match.common.utils.DialogUtils;
import com.macth.match.common.utils.ImageLoaderUtils;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.common.utils.PhoneUtils;
import com.macth.match.common.utils.PhotoSystemUtils;
import com.macth.match.mine.dto.AddInfoDTO;
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
 * 完善个人信息页
 */
public class AddInfoActivity extends BaseTitleActivity {
    @Bind(R.id.cb_add_info_username)
    CheckBox cbAddInfoUsername;
    @Bind(R.id.et_add_info_username)
    EditText etAddInfoUsername;
    @Bind(R.id.ll_add_info_username)
    LinearLayout llAddInfoUsername;
    @Bind(R.id.cb_add_info_phone)
    CheckBox cbAddInfoPhone;
    @Bind(R.id.et_add_info_phone)
    EditText etAddInfoPhone;
    @Bind(R.id.ll_add_info_phone)
    LinearLayout llAddInfoPhone;
    @Bind(R.id.cb_add_info_shenfen)
    CheckBox cbAddInfoShenfen;
    @Bind(R.id.et_add_info_shenfen)
    EditText etAddInfoShenfen;
    @Bind(R.id.ll_add_info_shenfen)
    LinearLayout llAddInfoShenfen;
    @Bind(R.id.cb_add_info_company)
    CheckBox cbAddInfoCompany;
    @Bind(R.id.et_add_info_company)
    EditText etAddInfoCompany;
    @Bind(R.id.ll_add_info_company)
    LinearLayout llAddInfoCompany;
    @Bind(R.id.cb_add_info_work)
    CheckBox cbAddInfoWork;
    @Bind(R.id.et_add_info_work)
    EditText etAddInfoWork;
    @Bind(R.id.ll_add_info_work)
    LinearLayout llAddInfoWork;
    @Bind(R.id.cb_add_info_role)
    CheckBox cbAddInfoRole;
    @Bind(R.id.et_add_info_role)
    EditText etAddInfoRole;
    @Bind(R.id.ll_add_info_role)
    LinearLayout llAddInfoRole;
    @Bind(R.id.tv_add_info_ok)
    TextView tvAddInfoOk;
    @Bind(R.id.img_user_card)
    ImageView imgUserCard;

    private List<Data> shenFenData; //身份数据集合
    private String[] shenFenStringArray; //用户身份数据数组
    private int[] shenFenIntArray;  //用户身份id的集合
    private String shenFen;  //用户选择的身份、
    private int shenFenId;  //用户选择的身份、

    private List<Data> roleData; //协同角色数据集合
    private String[] roleStringArray; //协同角色数据数组
    private int[] roleIntArray;  //用户协同角色id的集合
    private String role;  //协同角色
    private int roleId;  //协同角色

    PopupWindow popWindow;
    TextView mCamera,mPhoto,mExit;
    private File mPhotoFile;
    /* 请求识别码 */
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int REQUEST_CAMERA_CODE = 10;

    private String saveDir = Environment.getExternalStorageDirectory()
            .getPath() + "/temp_image";

    private String mImg; //用户上传的头像
    private TextView mBaseBack;

    private ArrayList<String> imagePaths = new ArrayList<>();
    private File mUrl;
    @Override
    protected int getContentResId() {
        return R.layout.activity_add_info;
    }

    @Override
    public void initView() {

        if (Build.VERSION.SDK_INT >= 23) {
            int readSDPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
            if (readSDPermission != PackageManager.PERMISSION_GRANTED) {
                LogUtils.e("readSDPermission",""+readSDPermission);
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        123);
            }
        }

        setTitleText("完善个人信息");
        mBaseBack = (TextView) findViewById(R.id.base_titlebar_back);
        mBaseBack.setVisibility(View.GONE);
        //设置框背景色
        setEdittextBackground();

        getShenFenData();

        getRoleData();

        etAddInfoShenfen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openShenFenSelectDialog();
            }
        });

        etAddInfoRole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRoleSelectDialog();
            }
        });
    }

    /**
     * 打开角色选择对话框
     */
    private void openRoleSelectDialog() {
        if (roleStringArray != null && roleStringArray.length != 0) {
            new AlertDialog.Builder(this)
                    .setTitle("请选择您的协同角色")
                    .setSingleChoiceItems(roleStringArray, -1,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    role = roleStringArray[which];
                                    roleId = roleIntArray[which];
                                }
                            }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    LogUtils.d("which====" + which);
                    etAddInfoRole.setText(role);
                    dialog.dismiss();
                }
            }).setNegativeButton("取消", null).show();
        }


    }

    /**
     * 获取协同角色数据
     */
    private void getRoleData() {
        CommonApiClient.getRole(this, new BaseDTO(), new CallBack<ShenFenEntity>() {
            @Override
            public void onSuccess(ShenFenEntity result) {
                if (result != null) {
                    if (AppConfig.SUCCESS.equals(result.getCode())) {
                        LogUtils.e("获取协同角色成功====" + result.getData().size());
                        roleData = result.getData();
                        //把角色信息转换为数组
                        changeRoleDataToStringArray();
                    }
                }
            }
        });
    }

    /**
     * 获取服务端数据
     */
    private void getShenFenData() {
        CommonApiClient.getShenFen(this, new BaseDTO(), new CallBack<ShenFenEntity>() {
            @Override
            public void onSuccess(ShenFenEntity result) {
                if (result != null) {
                    if (AppConfig.SUCCESS.equals(result.getCode())) {
                        LogUtils.e("获取身份信息成功");
                        shenFenData = result.getData();
                        //把ShenFenData转换为数组
                        changeShenFenDataToStringArray();
                    }
                }
            }
        });
    }


    /**
     * 把角色信息转换为数组
     */
    private void changeRoleDataToStringArray() {
        if (roleData != null && roleData.size() != 0) {
            roleStringArray = new String[roleData.size()];
            roleIntArray = new int[roleData.size()];
            for (int i = 0; i < roleData.size(); i++) {
                roleStringArray[i] = roleData.get(i).getName();
                roleIntArray[i] = roleData.get(i).getId();
                LogUtils.d("角色信息=====" + roleStringArray[i]);
                LogUtils.d("角色信息=====" + roleIntArray[i]);
            }
        }

    }

    /**
     * 把ShenFenData转换为数组
     */
    private void changeShenFenDataToStringArray() {
        if (shenFenData != null && shenFenData.size() != 0) {
            shenFenStringArray = new String[shenFenData.size()];
            shenFenIntArray = new int[shenFenData.size()];

            for (int i = 0; i < shenFenData.size(); i++) {
                shenFenStringArray[i] = shenFenData.get(i).getName();
                shenFenIntArray[i] = shenFenData.get(i).getId();
                LogUtils.d("身份信息=====" + shenFenStringArray[i]);
                LogUtils.d("身份信息=====" + shenFenIntArray[i]);
            }
        }
    }

    /**
     * 打开选择身份dialog
     */
    private void openShenFenSelectDialog() {
        if (shenFenStringArray != null && shenFenStringArray.length != 0) {
            new AlertDialog.Builder(this)
                    .setTitle("请选择您的身份")
                    .setSingleChoiceItems(shenFenStringArray, -1,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    shenFen = shenFenStringArray[which];
                                    shenFenId = shenFenIntArray[which];
                                }
                            }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    LogUtils.d("which====" + which);
                    etAddInfoShenfen.setText(shenFen);
                    dialog.dismiss();
                }
            }).setNegativeButton("取消", null).show();
        }
    }

    /**
     * 输入框在输入字符以后背景变亮
     */
    private void setEdittextBackground() {
        cbAddInfoCompany.setClickable(false);
        cbAddInfoPhone.setClickable(false);
        cbAddInfoRole.setClickable(false);
        cbAddInfoShenfen.setClickable(false);
        cbAddInfoUsername.setClickable(false);
        cbAddInfoWork.setClickable(false);

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
        etAddInfoPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    cbAddInfoPhone.setChecked(true);
                    llAddInfoPhone.setBackgroundResource(R.drawable.login_border_light);

                } else {
                    if (!TextUtils.isEmpty(etAddInfoPhone.getText().toString())) {
                        cbAddInfoPhone.setChecked(true);
                        llAddInfoPhone.setBackgroundResource(R.drawable.login_border_light);
                    } else {
                        cbAddInfoPhone.setChecked(false);
                        llAddInfoPhone.setBackgroundResource(R.drawable.login_border);
                    }

                }
            }
        });
        etAddInfoCompany.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    cbAddInfoCompany.setChecked(true);
                    llAddInfoCompany.setBackgroundResource(R.drawable.login_border_light);

                } else {
                    if (!TextUtils.isEmpty(etAddInfoCompany.getText().toString())) {
                        cbAddInfoCompany.setChecked(true);
                        llAddInfoCompany.setBackgroundResource(R.drawable.login_border_light);
                    } else {
                        cbAddInfoCompany.setChecked(false);
                        llAddInfoCompany.setBackgroundResource(R.drawable.login_border);
                    }

                }
            }
        });
        etAddInfoShenfen.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    openShenFenSelectDialog();
                    cbAddInfoShenfen.setChecked(true);
                    llAddInfoShenfen.setBackgroundResource(R.drawable.login_border_light);

                } else {
                    if (!TextUtils.isEmpty(etAddInfoShenfen.getText().toString())) {
                        cbAddInfoShenfen.setChecked(true);
                        llAddInfoShenfen.setBackgroundResource(R.drawable.login_border_light);
                    } else {
                        cbAddInfoShenfen.setChecked(false);
                        llAddInfoShenfen.setBackgroundResource(R.drawable.login_border);
                    }

                }
            }
        });
        etAddInfoRole.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    cbAddInfoRole.setChecked(true);
                    llAddInfoRole.setBackgroundResource(R.drawable.login_border_light);
                    openRoleSelectDialog();

                } else {
                    if (!TextUtils.isEmpty(etAddInfoRole.getText().toString())) {
                        cbAddInfoRole.setChecked(true);
                        llAddInfoRole.setBackgroundResource(R.drawable.login_border_light);
                    } else {
                        cbAddInfoRole.setChecked(false);
                        llAddInfoRole.setBackgroundResource(R.drawable.login_border);
                    }

                }
            }
        });
        etAddInfoWork.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    cbAddInfoWork.setChecked(true);
                    llAddInfoWork.setBackgroundResource(R.drawable.login_border_light);

                } else {
                    if (!TextUtils.isEmpty(etAddInfoWork.getText().toString())) {
                        cbAddInfoWork.setChecked(true);
                        llAddInfoWork.setBackgroundResource(R.drawable.login_border_light);
                    } else {
                        cbAddInfoWork.setChecked(false);
                        llAddInfoWork.setBackgroundResource(R.drawable.login_border);
                    }

                }
            }
        });
    }

    @Override
    public void initData() {
        File savePath = new File(saveDir);
        if (!savePath.exists()) {
            savePath.mkdirs();
        }

    }


    @OnClick({R.id.tv_add_info_ok,R.id.img_user_card})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_add_info_ok:
                phoneVerify();

                break;
            case R.id.img_user_card:
                //用户选择名片
                showPicPop();
                break;

            case R.id.btn_alter_pic_camera://拍照
                Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intentFromCapture, CODE_CAMERA_REQUEST);

                break;

            case R.id.btn_alter_pic_photo://选择照片
                PhotoPickerIntent intent = new PhotoPickerIntent(AddInfoActivity.this);
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

    /**
     * 用户手机号码验证
     * 手机号用户名不能为空
     */
    private void phoneVerify() {
        String phone = etAddInfoPhone.getText().toString().trim();
        String username = etAddInfoUsername.getText().toString().trim();
        //手机号非空验证
        if (TextUtils.isEmpty(phone)) {
            new AlertDialog.Builder(this).setTitle("温馨提示").setMessage("手机号不能为空!").setPositiveButton("确定", null).show();
            return;
        }

        //手机号码格式验证
        boolean valid = PhoneUtils.isPhoneNumberValid(phone);
        if (!valid) {
            new AlertDialog.Builder(this).setTitle("温馨提示").setMessage("请输入正确的电话号码!").setPositiveButton("确定", null).show();
            return;
        }

        //用户名非空验证
        if (TextUtils.isEmpty(username)) {
            new AlertDialog.Builder(this).setTitle("温馨提示").setMessage("用户名不能为空!").setPositiveButton("确定", null).show();
            return;
        }

        if (TextUtils.isEmpty(shenFen)) {
            new AlertDialog.Builder(this).setTitle("温馨提示").setMessage("请选择身份！").setPositiveButton("确定", null).show();
            return;
        }

        if (TextUtils.isEmpty(etAddInfoCompany.getText().toString())) {
            new AlertDialog.Builder(this).setTitle("温馨提示").setMessage("请输入公司名称！").setPositiveButton("确定", null).show();
            return;
        }

        if (TextUtils.isEmpty(etAddInfoWork.getText().toString())) {
            new AlertDialog.Builder(this).setTitle("温馨提示").setMessage("请输入公司职务！").setPositiveButton("确定", null).show();
            return;
        }

        if (TextUtils.isEmpty(role)) {
            new AlertDialog.Builder(this).setTitle("温馨提示").setMessage("请选择协同角色！").setPositiveButton("确定", null).show();
            return;
        }

        addInfo();

    }

    /**
     * 完善用户信息
     * 参数：usermobile-----用户手机号----必填
     * username-------用户名称------必填
     * identity-------用户身份
     * company--------公司名称
     * work-----------职务
     * cooperative----协同角色
     * userimg--------用户名片
     */
    private void addInfo() {

        String usermobile = etAddInfoPhone.getText().toString().trim();
        String username = etAddInfoUsername.getText().toString().trim();
        String company = etAddInfoCompany.getText().toString().trim();
        String work = etAddInfoWork.getText().toString().trim();

        AddInfoDTO addInfoDto = new AddInfoDTO();
        addInfoDto.setUsermobile(usermobile);
        addInfoDto.setUsername(username);
        addInfoDto.setIdentity(shenFenId + "");
        addInfoDto.setCompany(company);
        addInfoDto.setWork(work);
        addInfoDto.setCooperative(roleId + "");
        if(mUrl == null) {

        }else {
            addInfoDto.setUserimg(mUrl);
        }
        CommonApiClient.addInfo(this, addInfoDto, new CallBack<MdInformationResult>() {
            @Override
            public void onSuccess(MdInformationResult result) {
                if (AppConfig.SUCCESS.equals(result.getCode())) {
                    LogUtils.e("完善用户信息成功");
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

//                    mUrl = ImageLoaderUtils.getBitmapByte(fileName,null);
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
                    LogUtils.e("list: ", "list = " + list + "--size = " + list.size());
                    if(null==list){
                        return;
                    }else {
                        LogUtils.e("list.get(0)---", "" + list.get(0));
                        Bitmap bit = ImageLoaderUtils.readBitmap(list.get(0));
                        Bitmap roundedCornerBitmap = ImageLoaderUtils.createCircleImage(bit, 200);
                        imgUserCard.setImageBitmap(roundedCornerBitmap);

//                        mUrl = ImageLoaderUtils.getBitmapByte(list.get(0),null);
                    }
                }

                break;

        }
    }


    @Override
    public void onBackPressed() {
        DialogUtils.showPrompt(this, "提示","请完善个人信息！", "知道了");
    }
}
