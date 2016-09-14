package com.macth.match.login.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.macth.match.AppConfig;
import com.macth.match.AppContext;
import com.macth.match.R;
import com.macth.match.common.base.BaseTitleActivity;
import com.macth.match.common.http.CallBack;
import com.macth.match.common.http.CommonApiClient;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.common.utils.PhoneUtils;
import com.macth.match.common.utils.ToastUtils;
import com.macth.match.login.dto.LoginDTO;
import com.macth.match.login.entity.LoginEntity;
import com.macth.match.register.activity.AddInfoActivity;
import com.macth.match.register.activity.ForgetPwdActivity;
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
    @Bind(R.id.ll_login_username)
    LinearLayout llLoginUsername;
    @Bind(R.id.ll_login_pwd)
    LinearLayout llLoginPwd;

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
        cbLoginUsernameImg.setClickable(false);
        cbLoginPwdImg.setClickable(false);
        etLoginUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    cbLoginUsernameImg.setChecked(true);
                    llLoginUsername.setBackgroundResource(R.drawable.login_border_light);

                } else {
                    if (!TextUtils.isEmpty(etLoginUsername.getText().toString())) {
                        cbLoginUsernameImg.setChecked(true);
                        llLoginUsername.setBackgroundResource(R.drawable.login_border_light);
                    } else {
                        cbLoginUsernameImg.setChecked(false);
                        llLoginUsername.setBackgroundResource(R.drawable.login_border);
                    }

                }
            }
        });
        etLoginPwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    cbLoginPwdImg.setChecked(true);
                    llLoginPwd.setBackgroundResource(R.drawable.login_border_light);

                } else {
                    if (!TextUtils.isEmpty(etLoginPwd.getText().toString())) {
                        cbLoginPwdImg.setChecked(true);
                        llLoginPwd.setBackgroundResource(R.drawable.login_border_light);
                    } else {
                        cbLoginPwdImg.setChecked(false);
                        llLoginPwd.setBackgroundResource(R.drawable.login_border);
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
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_login_forgetpwd:
                startActivity(new Intent(LoginActivity.this, ForgetPwdActivity.class));
                finish();
                break;
            case R.id.tv_login_register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.tv_login_ok:
                //登录
                //判断电话，密码格式和是否为空
                if (TextUtils.isEmpty(etLoginUsername.getText().toString().trim())) {
                    new AlertDialog.Builder(LoginActivity.this).setTitle("温馨提示").setMessage("请输入用户名").setPositiveButton("确定", null).show();
                    break;
                }
                boolean isValid = PhoneUtils.isPhoneNumberValid(etLoginUsername.getText().toString());
                if (!isValid) {
                    new AlertDialog.Builder(this).setTitle("温馨提示").setMessage("请输入正确的电话号码!").setPositiveButton("确定", null).show();
                    break;
                }
                if (TextUtils.isEmpty(etLoginPwd.getText().toString().trim())) {
                    new AlertDialog.Builder(this).setTitle("温馨提示").setMessage("请输入密码").setPositiveButton("确定", null).show();
                    break;
                }
                login();
                break;
        }
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

                    AppContext.set("username",result.getData().getUsername());
                    AppContext.set("usermobile",account);
                    AppContext.set("useridentity",result.getData().getUseridentity());
                    AppContext.set("usercompany",result.getData().getUsercompany());
                    AppContext.set("userwork",result.getData().getUserwork());
                    AppContext.set("usertoken",result.getData().getUsertoken());
                    AppContext.set("cooperativeid",result.getData().getCooperativeid());
                    AppContext.set("IS_LOGIN",true);
                    LogUtils.d("用户信息==="+result.getData().toString());
                    finish();


//                    setResult(1001);
//                    //页面跳转
//                    startActivity(new Intent(LoginActivity.this, AddInfoActivity.class));


                }
            }
        }, account, pwd);
    }
}
