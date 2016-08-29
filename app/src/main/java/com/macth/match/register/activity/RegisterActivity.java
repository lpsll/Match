package com.macth.match.register.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.macth.match.AppConfig;
import com.macth.match.R;
import com.macth.match.common.base.BaseTitleActivity;
import com.macth.match.common.entity.BaseEntity;
import com.macth.match.common.http.CallBack;
import com.macth.match.common.http.CommonApiClient;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.common.utils.PhoneUtils;
import com.macth.match.common.utils.ToastUtils;
import com.macth.match.login.activity.LoginActivity;
import com.macth.match.register.dto.RegisterDTO;
import com.macth.match.register.dto.VerifyDTO;
import com.macth.match.register.view.TimeButton;

import butterknife.Bind;
import butterknife.OnClick;

public class RegisterActivity extends BaseTitleActivity {


    @Bind(R.id.cb_register_username_img)
    CheckBox cbRegisterUsernameImg;
    @Bind(R.id.et_register_username)
    EditText etRegisterUsername;
    @Bind(R.id.cb_register_code_img)
    CheckBox cbRegisterCodeImg;
    @Bind(R.id.et_register_code)
    EditText etRegisterCode;
    @Bind(R.id.cb_register_pwd_img)
    CheckBox cbRegisterPwdImg;
    @Bind(R.id.et_register_pwd)
    EditText etRegisterPwd;
    @Bind(R.id.cb_register_pwd_again_img)
    CheckBox cbRegisterPwdAgainImg;
    @Bind(R.id.et_register_pwd_again)
    EditText etRegisterPwdAgain;
    @Bind(R.id.tv_register_login)
    TextView tvRegisterLogin;
    @Bind(R.id.tv_register_ok)
    TextView tvRegisterOk;
    @Bind(R.id.TimeButton_register)
    TimeButton TimeButtonRegister;
    @Bind(R.id.rl_register_username)
    RelativeLayout rlRegisterUsername;
    @Bind(R.id.ll_register_code)
    LinearLayout llRegisterCode;
    @Bind(R.id.ll_register_pwd)
    LinearLayout llRegisterPwd;
    @Bind(R.id.ll_register_pwd_again)
    LinearLayout llRegisterPwdAgain;

    @Override
    protected int getContentResId() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {

        setTitleText("注册");

        //设置框背景色
        setEdittextBackground();

    }

    /**
     * 输入框在输入字符以后背景变亮
     */
    private void setEdittextBackground() {
        cbRegisterUsernameImg.setClickable(false);
        cbRegisterCodeImg.setClickable(false);
        cbRegisterPwdImg.setClickable(false);
        cbRegisterPwdAgainImg.setClickable(false);

        etRegisterUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    cbRegisterUsernameImg.setChecked(true);
                    rlRegisterUsername.setBackgroundResource(R.drawable.login_border_light);

                } else {
                    if (!TextUtils.isEmpty(etRegisterUsername.getText().toString())) {
                        cbRegisterUsernameImg.setChecked(true);
                        rlRegisterUsername.setBackgroundResource(R.drawable.login_border_light);
                    } else {
                        cbRegisterUsernameImg.setChecked(false);
                        rlRegisterUsername.setBackgroundResource(R.drawable.login_border);
                    }

                }
            }
        });
        etRegisterCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    cbRegisterCodeImg.setChecked(true);
                    llRegisterCode.setBackgroundResource(R.drawable.login_border_light);

                } else {
                    if (!TextUtils.isEmpty(etRegisterCode.getText().toString())) {
                        cbRegisterCodeImg.setChecked(true);
                        llRegisterCode.setBackgroundResource(R.drawable.login_border_light);
                    } else {
                        cbRegisterCodeImg.setChecked(false);
                        llRegisterCode.setBackgroundResource(R.drawable.login_border);
                    }

                }
            }
        });
        etRegisterPwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    cbRegisterPwdImg.setChecked(true);
                    llRegisterPwd.setBackgroundResource(R.drawable.login_border_light);

                } else {
                    if (!TextUtils.isEmpty(etRegisterPwd.getText().toString())) {
                        cbRegisterPwdImg.setChecked(true);
                        rlRegisterUsername.setBackgroundResource(R.drawable.login_border_light);
                    } else {
                        cbRegisterPwdImg.setChecked(false);
                        llRegisterPwd.setBackgroundResource(R.drawable.login_border);
                    }

                }
            }
        });
        etRegisterPwdAgain.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    cbRegisterPwdAgainImg.setChecked(true);
                    llRegisterPwdAgain.setBackgroundResource(R.drawable.login_border_light);

                } else {
                    if (!TextUtils.isEmpty(etRegisterPwdAgain.getText().toString())) {
                        cbRegisterPwdAgainImg.setChecked(true);
                        llRegisterPwdAgain.setBackgroundResource(R.drawable.login_border_light);
                    } else {
                        cbRegisterPwdAgainImg.setChecked(false);
                        llRegisterPwdAgain.setBackgroundResource(R.drawable.login_border);
                    }

                }
            }
        });
    }

    @Override
    public void initData() {

    }


    @OnClick({R.id.cb_register_username_img, R.id.TimeButton_register, R.id.cb_register_code_img, R.id.cb_register_pwd_img, R.id.cb_register_pwd_again_img, R.id.tv_register_login, R.id.tv_register_ok})
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_register_login:
                //跳转登录页
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                break;
            case R.id.tv_register_ok:
                dataVerify();
                break;
            case R.id.TimeButton_register:
                //验证电话号码
                boolean isValid = PhoneUtils.isPhoneNumberValid(etRegisterUsername.getText().toString());
                if (!isValid) {
                    TimeButtonRegister.setLenght(0);
                    new AlertDialog.Builder(this).setTitle("温馨提示").setMessage("请输入正确的电话号码!").setPositiveButton("确定", null).show();
                } else {
                    TimeButtonRegister.setLenght(60 * 1000);
                    //获取验证码
                    getSmsVerifyCode();
                }
                break;
        }
    }

    /**
     * 验证两次密码是否一致
     */
    private void dataVerify() {
        String phone = etRegisterUsername.getText().toString().trim();
        String pwd = etRegisterPwd.getText().toString().trim();
        String pwdAgain = etRegisterPwdAgain.getText().toString().trim();
        //手机号码格式验证
        boolean valid = PhoneUtils.isPhoneNumberValid(phone);
        if (!valid) {
            new AlertDialog.Builder(this).setTitle("温馨提示").setMessage("请输入正确的电话号码!").setPositiveButton("确定", null).show();
            return;
        }

        //密码非空验证
        if (TextUtils.isEmpty(pwd)) {
            new AlertDialog.Builder(this).setTitle("温馨提示").setMessage("密码不能为空!").setPositiveButton("确定", null).show();
            return;
        }
        //两次密码一致验证
        if (!pwd.equals(pwdAgain)) {
            //进行注册操作
            new AlertDialog.Builder(this).setTitle("温馨提示").setMessage("两次密码不一致!").setPositiveButton("确定", null).show();
            return;
        }
        //注册操作
        register();
    }

    /**
     * 注册
     * 参数：account---用户账号
     * userpwd---用户密码【md5加密后字符串
     * yzm-------短信验证码
     */
    private void register() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setAccount(etRegisterUsername.getText().toString().trim());
        registerDTO.setUserpwd(etRegisterPwd.getText().toString().trim());
        registerDTO.setYzm(etRegisterCode.getText().toString().trim());

        CommonApiClient.register(this, registerDTO, new CallBack<BaseEntity>() {
            @Override
            public void onSuccess(BaseEntity result) {
                if (AppConfig.SUCCESS.equals(result.getCode())) {
                    LogUtils.e("注册成功");
                    ToastUtils.showShort(RegisterActivity.this, "注册成功");
                    LogUtils.e("result---", "" + result);
                    //跳转到完善信息页面
                    startActivity(new Intent(RegisterActivity.this, AddInfoActivity.class));
                    finish();
                }
            }
        }, etRegisterUsername.getText().toString().trim(), etRegisterPwd.getText().toString().trim(), etRegisterCode.getText().toString().trim());

    }

    /**
     * 获取验证码
     * 参数：user_mobile-----用户手机号
     */
    private void getSmsVerifyCode() {
        VerifyDTO verifyDTO = new VerifyDTO();
        verifyDTO.setUser_mobile(etRegisterUsername.getText().toString().trim());
        CommonApiClient.verifyCode(this, verifyDTO, new CallBack<BaseEntity>() {
            @Override
            public void onSuccess(BaseEntity result) {
                if (AppConfig.SUCCESS.equals(result.getCode())) {
                    LogUtils.e("获取验证码成功");
                    LogUtils.e("result---------", "");
                }
            }
        }, etRegisterUsername.getText().toString().trim());
    }


}
