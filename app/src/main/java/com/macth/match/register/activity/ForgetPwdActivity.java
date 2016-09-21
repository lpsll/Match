package com.macth.match.register.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.macth.match.AppConfig;
import com.macth.match.R;
import com.macth.match.common.base.BaseTitleActivity;
import com.macth.match.common.entity.BaseEntity;
import com.macth.match.common.http.CallBack;
import com.macth.match.common.http.CommonApiClient;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.common.utils.PhoneUtils;
import com.macth.match.common.utils.TimeCountUtil;
import com.macth.match.common.utils.ToastUtils;
import com.macth.match.register.dto.ForgetPwdDTO;
import com.macth.match.register.dto.VerifyDTO;

import butterknife.Bind;
import butterknife.OnClick;

public class ForgetPwdActivity extends BaseTitleActivity {


    @Bind(R.id.et_forget_pwd_phone)
    EditText etForgetPwdPhone;
    @Bind(R.id.TimeButton_forget_pwd)
    Button TimeButtonForgetPwd;
    @Bind(R.id.et_forget_pwd_code)
    EditText etForgetPwdCode;
    @Bind(R.id.tv_forget_pwd_next)
    TextView tvForgetPwdNext;
    private TimeCountUtil timeCountUtil;

    @Override
    protected int getContentResId() {
        return R.layout.activity_forget_pwd;
    }

    @Override
    public void initView() {

        setTitleText("忘记密码");



    }

    @Override
    public void initData() {
        timeCountUtil = new TimeCountUtil(this, 60000, 1000, TimeButtonForgetPwd);
    }


    @OnClick({R.id.TimeButton_forget_pwd, R.id.tv_forget_pwd_next})
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.TimeButton_forget_pwd:

                //验证电话号码
                boolean isValid = PhoneUtils.isPhoneNumberValid(etForgetPwdPhone.getText().toString());
                if (!isValid) {
                    new AlertDialog.Builder(this).setTitle("温馨提示").setMessage("请输入正确的电话号码!").setPositiveButton("确定", null).show();
                } else {
                    timeCountUtil.start();
                    //获取验证码
                    getSmsVerifyCode();
                }

                break;
            case R.id.tv_forget_pwd_next:

                //手机号,验证码验证
                phoneAndYZMVerify();

                break;
        }
    }

    /**
     * 手机号,验证码验证
     * 参数：usermobile---用户手机号
             useryzm------短信验证码
     */
    private void phoneAndYZMVerify() {
        ForgetPwdDTO forgetPwdDTO = new ForgetPwdDTO();
        forgetPwdDTO.setUsermobile(etForgetPwdPhone.getText().toString().trim());
        forgetPwdDTO.setUseryzm(etForgetPwdCode.getText().toString().trim());
        CommonApiClient.forgetPwd(this, forgetPwdDTO, new CallBack<BaseEntity>() {
            @Override
            public void onSuccess(BaseEntity result) {
                if (AppConfig.SUCCESS.equals(result.getCode())) {
                    LogUtils.e("手机号，验证码验证成功");
                    ToastUtils.showShort(ForgetPwdActivity.this,"验证通过");

                    //跳转到密码重设页
                    Intent intent = new Intent(ForgetPwdActivity.this, SetNewPwdActivity.class);
                    intent.putExtra("userMobile",etForgetPwdPhone.getText().toString().trim());
                    startActivity(intent);
                    finish();

                }
            }
        });
    }

    /**
     * 获取验证码
     * 参数：user_mobile-----用户手机号
     */
    private void getSmsVerifyCode() {
        VerifyDTO verifyDTO = new VerifyDTO();
        verifyDTO.setUser_mobile(etForgetPwdPhone.getText().toString().trim());
        CommonApiClient.verifyCode(this, verifyDTO, new CallBack<BaseEntity>() {
            @Override
            public void onSuccess(BaseEntity result) {
                if (AppConfig.SUCCESS.equals(result.getCode())) {
                    LogUtils.e("获取验证码成功，");
                    ToastUtils.showShort(ForgetPwdActivity.this,"获取验证码成功");
                }
            }
        });
    }

}
