package com.macth.match.mine.activity;

import android.app.AlertDialog;
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
import com.macth.match.mine.MineUIGoto;
import com.macth.match.mine.entity.ChangePwdDTO;

import butterknife.Bind;
import butterknife.OnClick;

public class ChangePwdActivity extends BaseTitleActivity {


    @Bind(R.id.et_changepwd_rawpwd)
    EditText etChangepwdRawpwd;
    @Bind(R.id.et_changepwd_newpwd)
    EditText etChangepwdNewpwd;
    @Bind(R.id.et_changepwd_newpwd_again)
    EditText etChangepwdNewpwdAgain;
    @Bind(R.id.tv_set_new_pwd_ok)
    TextView tvSetNewPwdOk;

    @Override
    protected int getContentResId() {
        return R.layout.activity_change_pwd;
    }

    @Override
    public void initView() {
        setTitleText("修改密码");

    }

    @Override
    public void initData() {

    }


    @OnClick(R.id.tv_set_new_pwd_ok)
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case  R.id.tv_set_new_pwd_ok:
                //验证空，一致
                verify();


                break;
        }
    }

    /**
     * 验证输入是否为空，两次密码是否一致
     */
    private void verify() {
        String rawPwd = etChangepwdRawpwd.getText().toString().trim();
        String newPwd = etChangepwdNewpwd.getText().toString().trim();
        String newPwdAgain = etChangepwdNewpwdAgain.getText().toString().trim();

        //密码非空验证
        if (TextUtils.isEmpty(rawPwd)) {
            new AlertDialog.Builder(this).setTitle("温馨提示").setMessage("原密码不能为空!").setPositiveButton("确定", null).show();
            return;
        }
        if (TextUtils.isEmpty(newPwd)) {
            new AlertDialog.Builder(this).setTitle("温馨提示").setMessage("新密码不能为空!").setPositiveButton("确定", null).show();
            return;
        }

        //和原密码一致验证
        if (rawPwd.equals(newPwd)) {
            //进行注册操作
            new AlertDialog.Builder(this).setTitle("温馨提示").setMessage("新密码不能和原密码相同!").setPositiveButton("确定", null).show();
            return;
        }

        //两次密码一致验证
        if (!newPwd.equals(newPwdAgain)) {
            //进行注册操作
            new AlertDialog.Builder(this).setTitle("温馨提示").setMessage("两次密码不一致!").setPositiveButton("确定", null).show();
            return;
        }

        //设置新密码操作
        changePwd();

    }

    /**
     * 设置新密码
     */
    private void changePwd() {
        String oldPwd = etChangepwdRawpwd.getText().toString().trim();
        String newPwd = etChangepwdNewpwd.getText().toString().trim();

        ChangePwdDTO changePwdDTO = new ChangePwdDTO();

        //uid?????????????????????????????????
        changePwdDTO.setUserid("2");
        changePwdDTO.setUseroldpwd(oldPwd);
        changePwdDTO.setUsernewpwd(newPwd);

        CommonApiClient.ChangePwd(this, changePwdDTO, new CallBack<BaseEntity>() {
            @Override
            public void onSuccess(BaseEntity result) {
                if (AppConfig.SUCCESS.equals(result.getCode())) {
                    LogUtils.e("设置新密码成功");
                    ToastUtils.showShort(ChangePwdActivity.this, "设置新密码成功");

                    MineUIGoto.gotoLoginForPwd(ChangePwdActivity.this);
                    finish();
                }
            }
        });
    }
}
