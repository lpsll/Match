package com.macth.match.login.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.macth.match.AppConfig;
import com.macth.match.R;
import com.macth.match.common.base.BaseTitleActivity;
import com.macth.match.common.http.CallBack;
import com.macth.match.common.http.CommonApiClient;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.common.utils.PhoneUtils;
import com.macth.match.common.utils.ToastUtils;
import com.macth.match.login.entity.LoginDTO;
import com.macth.match.login.entity.LoginEntity;
import com.macth.match.register.activity.RegisterActivity;

import butterknife.Bind;
import butterknife.OnClick;

public class LoginActivity extends BaseTitleActivity {


    @Bind(R.id.cb_login_username_img)
    CheckBox cbLoginUsernameImg;
    @Bind(R.id.et_login_username)
    EditText etLoginUsername;
    @Bind(R.id.cb_login_pwd_img)
    CheckBox cbLoginPwdImg;
    @Bind(R.id.et_login_pwd)
    EditText etLoginPwd;
    @Bind(R.id.tv_login_forgetpwd)
    TextView tvLoginForgetpwd;
    @Bind(R.id.tv_login_register)
    TextView tvLoginRegister;
    @Bind(R.id.tv_login_ok)
    TextView tvLoginOk;

    @Override
    protected int getContentResId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        setTitleText("登录");

        //设置框背景色
        setEdittextBackground();


    }

    /**
     * 输入框在输入字符以后背景变亮
     */
    private void setEdittextBackground() {
        etLoginUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
//                    etLoginUsername.setBackgroundColor(Color.parseColor("#ffffff"));
                    cbLoginUsernameImg.setChecked(true);
                    
                }else {
                    if(!TextUtils.isEmpty(etLoginUsername.getText().toString())) {
                        cbLoginUsernameImg.setChecked(true);
                    }else {
                        cbLoginUsernameImg.setChecked(false);
                    }
                    
                }
            }
        });
        etLoginPwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
//                    etLoginUsername.setBackgroundColor(Color.parseColor("#ffffff"));
                    cbLoginPwdImg.setChecked(true);

                }else {
                    if(!TextUtils.isEmpty(etLoginUsername.getText().toString())) {
                        cbLoginPwdImg.setChecked(true);
                    }else {
                        cbLoginPwdImg.setChecked(false);
                    }

                }
            }
        });
    }

    @Override
    public void initData() {

    }


    @OnClick({R.id.cb_login_username_img, R.id.cb_login_pwd_img, R.id.tv_login_forgetpwd, R.id.tv_login_register, R.id.tv_login_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cb_login_username_img:
                if(cbLoginUsernameImg.isChecked()) {
                    etLoginUsername.hasFocus();
                }
                break;
            case R.id.cb_login_pwd_img:
//                pwdChangeLight();
                break;
            case R.id.tv_login_forgetpwd:
                break;
            case R.id.tv_login_register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.tv_login_ok:

                //登录
                //判断电话，密码格式和是否为空
                if(TextUtils.isEmpty(etLoginUsername.getText().toString().trim())) {
                    new AlertDialog.Builder(LoginActivity.this).setMessage("请输入用户名").setPositiveButton("确定",null).show();
                    break;
                }
                boolean isValid = PhoneUtils.isPhoneNumberValid(etLoginUsername.getText().toString());
                if (!isValid) {
                    new AlertDialog.Builder(this).setTitle("请输入正确的电话号码!").setPositiveButton("确定", null).show();
                    break;
                }
                if(TextUtils.isEmpty(etLoginPwd.getText().toString().trim())) {
                    new AlertDialog.Builder(this).setMessage("请输入密码").setPositiveButton("确定",null).show();
                    break;
                }


                login();

                break;
        }
    }



    private void pwdChangeLight() {
        cbLoginUsernameImg.setChecked(true);
        etLoginUsername.hasFocus();

    }

    /**
     * 登录操作
     */
    private void login() {
        /**
         *参数：account---用户账号
         userpwd---用户密码【md5加密后字符串】
         *
         *
         */
        final String account = etLoginUsername.getText().toString().trim();
        String pwd = etLoginPwd.getText().toString().trim();

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setAccount(account);
        loginDTO.setUserpwd(pwd);
        CommonApiClient.login(this, loginDTO, new CallBack<LoginEntity>() {
            @Override
            public void onSuccess(LoginEntity result) {
                LogUtils.e("result========" + result.getMsg());
                if (AppConfig.SUCCESS.equals(result.getCode())) {
                    LogUtils.e("登录成功");
                    ToastUtils.showShort(LoginActivity.this, "登录成功");

                    //保存用户信息
                    AppConfig.account = account;
                    AppConfig.usertoken = result.getData().getUsertoken();
                    AppConfig.isLogin = true;

                    LogUtils.d( result.getData().toString());

                    //页面跳转

                }
            }
        },account,pwd);
    }

}
