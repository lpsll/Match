package com.macth.match.register.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.macth.match.AppConfig;
import com.macth.match.R;
import com.macth.match.common.base.BaseTitleActivity;
import com.macth.match.common.dto.BaseDTO;
import com.macth.match.common.entity.BaseEntity;
import com.macth.match.common.http.CallBack;
import com.macth.match.common.http.CommonApiClient;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.common.utils.PhoneUtils;
import com.macth.match.common.utils.ToastUtils;
import com.macth.match.mine.dto.AddInfoDTO;
import com.macth.match.register.entity.Data;
import com.macth.match.register.entity.ShenFenEntity;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 完善信息页
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

    private List<Data> shenFenData; //身份数据集合
    private String[] shenFenStringArray; //用户身份数据数组
    private String shenFen;  //用户选择的身份、

    private List<Data> roleData; //协同角色数据集合
    private String[] roleStringArray; //协同角色数据数组
    private String role;  //协同角色

    @Override
    protected int getContentResId() {
        return R.layout.activity_add_info;
    }

    @Override
    public void initView() {

        setTitleText("注册");
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
            for (int i = 0; i < roleData.size(); i++) {
                roleStringArray[i] = roleData.get(i).getName();
            }
        }

    }

    /**
     * 把ShenFenData转换为数组
     */
    private void changeShenFenDataToStringArray() {
        if (shenFenData != null && shenFenData.size() != 0) {
            shenFenStringArray = new String[shenFenData.size()];
            for (int i = 0; i < shenFenData.size(); i++) {
                shenFenStringArray[i] = shenFenData.get(i).getName();
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


    }


    @OnClick(R.id.tv_add_info_ok)
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_add_info_ok:
                phoneVerify();

                break;
        }

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

        String usermobile = etAddInfoUsername.getText().toString().trim();
        String username = etAddInfoUsername.getText().toString().trim();
        String identity = etAddInfoShenfen.getText().toString().trim();
        String company = etAddInfoCompany.getText().toString().trim();
        String work = etAddInfoWork.getText().toString().trim();
        String cooperative = etAddInfoRole.getText().toString().trim();

        AddInfoDTO addInfoDto = new AddInfoDTO();
        addInfoDto.setUsermobile(usermobile);
        addInfoDto.setUsername(username);
        addInfoDto.setIdentity(identity);
        addInfoDto.setCompany(company);
        addInfoDto.setWork(work);
        addInfoDto.setCooperative(cooperative);
        addInfoDto.setUserimg("");

        CommonApiClient.addInfo(this, addInfoDto, new CallBack<BaseEntity>() {
            @Override
            public void onSuccess(BaseEntity result) {
                LogUtils.e("result========" + result.getMsg());
                if (AppConfig.SUCCESS.equals(result.getCode())) {
                    LogUtils.e("完善用户信息成功");
                    ToastUtils.showShort(AddInfoActivity.this, "信息补充成功");
                }
            }
        });
    }
}
