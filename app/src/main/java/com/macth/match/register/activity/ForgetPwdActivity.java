package com.macth.match.register.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.macth.match.R;
import com.macth.match.common.base.BaseTitleActivity;
import com.macth.match.register.view.TimeButton;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgetPwdActivity extends BaseTitleActivity {


    @Bind(R.id.et_forget_pwd_phone)
    EditText etForgetPwdPhone;
    @Bind(R.id.TimeButton_forget_pwd)
    TimeButton TimeButtonForgetPwd;
    @Bind(R.id.et_forget_pwd_code)
    EditText etForgetPwdCode;
    @Bind(R.id.tv_forget_pwd_next)
    TextView tvForgetPwdNext;

    @Override
    protected int getContentResId() {
        return R.layout.activity_forget_pwd;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.TimeButton_forget_pwd, R.id.tv_forget_pwd_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.TimeButton_forget_pwd:
                break;
            case R.id.tv_forget_pwd_next:
                break;
        }
    }
}
