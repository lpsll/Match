package com.macth.match.register.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.macth.match.AppConfig;
import com.macth.match.R;
import com.macth.match.common.base.BaseTitleActivity;
import com.macth.match.common.entity.BaseEntity;
import com.macth.match.common.http.CallBack;
import com.macth.match.common.http.CommonApiClient;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.common.utils.ToastUtils;
import com.macth.match.login.activity.LoginActivity;
import com.macth.match.register.dto.SetNewPwdDTO;

import butterknife.Bind;
import butterknife.OnClick;

public class SetNewPwdActivity extends BaseTitleActivity {

    @Bind(R.id.et_set_new_pwd)
    EditText etSetNewPwd;
    @Bind(R.id.et_set_new_pwd_again)
    EditText etSetNewPwdAgain;
    @Bind(R.id.tv_set_new_pwd_ok)
    TextView tvSetNewPwdOk;
    private String userMobile;


    @Override
    protected int getContentResId() {
        return R.layout.activity_set_new_pwd;
    }

    @Override
    public void initView() {
        setTitleText("设置新密码");

    }

    @Override
    public void initData() {

        Intent intent = getIntent();
        userMobile = intent.getStringExtra("userMobile");

    }


    @OnClick(R.id.tv_set_new_pwd_ok)
    public void onClick(View v) {
        super.onClick(v);
        //先判断两次密码是否一致，再重设密码
        //密码非空验证
        String pwd = etSetNewPwd.getText().toString().trim();
        String pwdAgain = etSetNewPwdAgain.getText().toString().trim();
        if (TextUtils.isEmpty(etSetNewPwd.getText().toString().trim())) {
            new AlertDialog.Builder(this).setTitle("温馨提示").setMessage("密码不能为空!").setPositiveButton("确定", null).show();
            return;
        }
        //两次密码一致验证
        if (!pwd.equals(pwdAgain)) {
            //进行注册操作
            new AlertDialog.Builder(this).setTitle("温馨提示").setMessage("两次密码不一致!").setPositiveButton("确定", null).show();
            return;
        }
        // 重设密码操作
        setNewPwd();
    }

    /**
     * 重设密码，忘记密码第二步
     * 参数：usermobile---用户账号
     * userpwd---用户密码【md5加密后字符串】
     */
    private void setNewPwd() {
        SetNewPwdDTO setNewPwdDTO = new SetNewPwdDTO();
        setNewPwdDTO.setUsermobile(userMobile);
        setNewPwdDTO.setUserpwd(etSetNewPwd.getText().toString().trim());
        CommonApiClient.setNewPwd(this, setNewPwdDTO, new CallBack<BaseEntity>() {
            @Override
            public void onSuccess(BaseEntity result) {
                if (AppConfig.SUCCESS.equals(result.getCode())) {
                    LogUtils.e("设置新密码成功");
                    ToastUtils.showShort(SetNewPwdActivity.this, "设置新密码成功");

                    //跳转到登录页面
                    startActivity(new Intent(SetNewPwdActivity.this, LoginActivity.class));
                    finish();
                }
            }
        }, userMobile, etSetNewPwd.getText().toString().trim());
    }
}
