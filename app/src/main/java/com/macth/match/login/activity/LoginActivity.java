package com.macth.match.login.activity;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.macth.match.R;
import com.macth.match.common.base.BaseTitleActivity;

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
//        setEdittextBackground();


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
                }else {
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
                pwdChangeLight();
                break;
            case R.id.tv_login_forgetpwd:
                break;
            case R.id.tv_login_register:
                break;
            case R.id.tv_login_ok:
                break;
        }
    }



    private void pwdChangeLight() {
        cbLoginUsernameImg.setChecked(true);
        etLoginUsername.hasFocus();

    }

}
