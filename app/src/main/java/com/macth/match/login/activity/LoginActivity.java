package com.macth.match.login.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.macth.match.AppConfig;
import com.macth.match.AppContext;
import com.macth.match.R;
import com.macth.match.common.base.BaseApplication;
import com.macth.match.common.base.BaseTitleActivity;
import com.macth.match.common.dto.BaseDTO;
import com.macth.match.common.http.CallBack;
import com.macth.match.common.http.CommonApiClient;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.common.utils.PhoneUtils;
import com.macth.match.common.utils.ToastUtils;
import com.macth.match.group.entity.GroupEntity;
import com.macth.match.group.entity.GroupResult;
import com.macth.match.login.dto.LoginDTO;
import com.macth.match.login.entity.LoginEntity;
import com.macth.match.register.activity.ForgetPwdActivity;
import com.macth.match.register.activity.RegisterActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.UserInfo;

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
                if (AppConfig.SUCCESS.equals(result.getCode())) {
                    LogUtils.e("登录成功");
                    ToastUtils.showShort(LoginActivity.this,"登录成功");

                    AppContext.set("username",result.getData().getUsername());
                    AppContext.set("usermobile",account);
                    AppContext.set("useridentity",result.getData().getUseridentity());
                    AppContext.set("usercompany",result.getData().getUsercompany());
                    AppContext.set("userwork",result.getData().getUserwork());
                    AppContext.set("usertoken",result.getData().getUsertoken());
                    AppContext.set("cooperativeid",result.getData().getCooperativeid());
                    AppContext.set("rytoken",result.getData().getRytoken());
                    AppContext.set("userimager",result.getData().getUserimage());
                    AppContext.set("IS_LOGIN",true);
                    Initialization();//初始化聊天界面信息
                    setResult(1001);
                    finish();

                }
            }
        });
    }

    private void Initialization() {
        reqGroup();
        //组信息
        RongIM.setGroupInfoProvider(new RongIM.GroupInfoProvider() {
            @Override
            public Group getGroupInfo(String s) {
                List<GroupEntity> groupEntityList = AppContext.getInstance().getGroupEntityList();
                for(GroupEntity entity : groupEntityList){
                    if(entity.getGroupid().equals(s)){
                        return new Group(s,entity.getGroupname(), Uri.parse(entity.getGroupimg()));
                    }
                }
                return null;
            }
        },true);
//        //组成员信息
//
//        RongIM.setGroupUserInfoProvider(new RongIM.GroupUserInfoProvider() {
//            @Override
//            public GroupUserInfo getGroupUserInfo(String s, String s1) {
//                return null;
//            }
//        },true);
//        //个人信息
//        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
//            @Override
//            public UserInfo getUserInfo(String s) {
//                return null;
//            }
//        },true);
        service();
    }

    private void reqGroup() {
        BaseDTO dto = new BaseDTO();
        dto.setUserid(AppContext.get("usertoken", ""));
        CommonApiClient.group(this, dto, new CallBack<GroupResult>() {
            @Override
            public void onSuccess(GroupResult result) {
                if (AppConfig.SUCCESS.equals(result.getCode())) {
                    AppContext.getInstance().setGroupEntityList(result.getData());
                }
            }
        });
    }

    private void service() {
        if (this.getApplicationInfo().packageName
                .equals(BaseApplication.getCurProcessName(this.getApplicationContext()))) {
                        /*IMKit SDK调用第二步, 建立与服务器的连接*/
            LogUtils.e("rytoken",""+AppContext.get("rytoken", ""));
            RongIM.connect(AppContext.get("rytoken",""), new RongIMClient.ConnectCallback() {
                /*  *
                  *
                  Token 错误
                  ，
                  在线上环境下主要是因为 Token
                  已经过期，
                  您需要向 App
                  Server 重新请求一个新的
                  Token*/
                @Override
                public void onTokenIncorrect() {
                    LogUtils.e("", "--onTokenIncorrect");
                }

                /**
                 *连接融云成功
                 *
                 @param
                 userid 当前
                 token*/
                @Override
                public void onSuccess(String userid) {
                    LogUtils.e("--onSuccess", "--onSuccess" + userid);
                    LogUtils.e("--onSuccess", "--onSuccess" + userid);
                    LogUtils.e("AppContext.get(\"username\",\"\")----",""+AppContext.get("username",""));
                    RongIM.getInstance().setCurrentUserInfo(new UserInfo(userid,AppContext.get("username",""), Uri.parse(AppContext.get("userimager",""))));
                    RongIM.getInstance().setMessageAttachedUserInfo(true);
                }

                /*  *
                  *连接融云失败
                  @param
                  errorCode 错误码
                  可到官网 查看错误码对应的注释*/
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    LogUtils.e("--onError", "--onError" + errorCode);
                }
            });
        }


    }
}
