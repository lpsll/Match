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

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
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
import com.macth.match.common.widget.EmptyLayout;
import com.macth.match.group.dto.MembersDTO;
import com.macth.match.group.entity.GroupEntity;
import com.macth.match.group.entity.GroupNewsEvent;
import com.macth.match.group.entity.GroupResult;
import com.macth.match.group.entity.MembersEntity;
import com.macth.match.group.entity.MembersResult;
import com.macth.match.login.dto.LoginDTO;
import com.macth.match.login.entity.LoginEntity;
import com.macth.match.register.activity.ForgetPwdActivity;
import com.macth.match.register.activity.RegisterActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import io.rong.imkit.RongIM;
import io.rong.imkit.model.GroupUserInfo;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
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
    public LocationClient mLocationClient = null;

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

                    AppContext.set("RI","1");
                    Initialization();//初始化聊天界面信息
                    initNotice();//是否有未读消息通知
//                    initLocation();//初始化定位
                    setResult(1001);
                    finish();

                }
            }
        });
    }

    private void initNotice() {
        if (RongIM.getInstance() != null) {
            /**
             * 接收未读消息的监听器。
             *
             * @param listener          接收所有未读消息消息的监听器。
             */
            RongIM.getInstance().setOnReceiveUnreadCountChangedListener(new MyGroupReceiveUnreadCountChangedListener(), Conversation.ConversationType.GROUP);
            RongIM.getInstance().setOnReceiveUnreadCountChangedListener(new MyPrivateReceiveUnreadCountChangedListener(), Conversation.ConversationType.PRIVATE);
        }
    }


    /**
     * 接收未读消息的监听器。(单聊消息)
     */
    int groupCount;
    public class MyPrivateReceiveUnreadCountChangedListener implements RongIM.OnReceiveUnreadCountChangedListener {

        /**
         * @param count           未读消息数。
         */
        @Override
        public void onMessageIncreased(int count) {
            groupCount = count;
            LogUtils.e("count---2",""+count);
            LogUtils.e("prCount---2",""+prCount);
            if(prCount==0){
                initCount(count);
            }else {
                initCount(prCount);
            }




        }
    }

    private void initCount(int count) {
        String string =AppContext.get("RI","");
        if(string.equals("1")){
            if(count>0){
                EventBus.getDefault().post(
                        new GroupNewsEvent("1"));
            }else {
                EventBus.getDefault().post(
                        new GroupNewsEvent("0"));
            }
        }


    }

    /**
     * 接收未读消息的监听器。(群消息)
     */
    int prCount;
    public class MyGroupReceiveUnreadCountChangedListener implements RongIM.OnReceiveUnreadCountChangedListener {

        /**
         * @param count           未读消息数。
         */
        @Override
        public void onMessageIncreased(int count) {
            prCount = count;
            LogUtils.e("count---1",""+count);
            LogUtils.e("groupCount---1",""+groupCount);
            if(groupCount==0){
                initCount(count);
            }else {
                initCount(groupCount);
            }



        }
    }

    private void initLocation() {
        requestLocationInfo();//发请定位
    }
    /**
     * 发起定位
     */
    public void requestLocationInfo(){
        setLocationOption();
        if (mLocationClient != null && !mLocationClient.isStarted()){
            mLocationClient.start();
        }
        if (mLocationClient != null && mLocationClient.isStarted()){
            mLocationClient.requestLocation();
        }
    }

    /**
     * 设置相关参数
     */
    private void setLocationOption(){
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener( new MyLocationListener());    //注册监听函数
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);        //是否打开GPS
        option.setCoorType("bd09ll");       //设置返回值的坐标类型。
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setProdName("LocationDemo"); //设置产品线名称。强烈建议您使用自定义的产品线名称，方便我们以后为您提供更高效准确的定位服务。
        mLocationClient.setLocOption(option);
    }

    class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null) {
                LogUtils.e("location_failed----","location_failed");
                return;
            } else {
                int locType = location.getLocType();
                LogUtils.i("locType:",""+locType);
                LogUtils.i("getLatitude:","" + location.getLatitude());
                LogUtils.i("getLongitude:" ,""+ location.getLongitude());
                if (TextUtils.isEmpty(String.valueOf(location.getLatitude()))) {
                    LogUtils.e("locType----","定位失败");
                    mLocationClient.start();
                } else {

                    AppContext.set("latitude",String.valueOf(location.getLatitude()));
                    AppContext.set("longitude",String.valueOf(location.getLongitude()));
                    mLocationClient.stop();
                }
            }

        }
    }

    private void Initialization() {
        reqGroup();
        reqMembers();
        //组信息
        RongIM.setGroupInfoProvider(new RongIM.GroupInfoProvider() {
            @Override
            public Group getGroupInfo(String s) {
                List<GroupEntity> groupEntityList = AppContext.getInstance().getGroupEntityList();
                for(GroupEntity entity : groupEntityList){
                    if(entity.getGroupid().equals(s)){
                        LogUtils.e("Uri-----",""+Uri.parse(entity.getGroupimg()));
                        return new Group(s,entity.getGroupname(), Uri.parse(entity.getGroupimg()));
                    }
                }
                return null;
            }
        },true);

        //组成员信息

//        RongIM.setGroupUserInfoProvider(new RongIM.GroupUserInfoProvider() {
//            @Override
//            public GroupUserInfo getGroupUserInfo(String s, String s1) {
//                List<MembersEntity> membersEntity = AppContext.getInstance().getMembersEntity();
////                for(MembersEntity entity : membersEntity){
////                    if(entity.getId().equals(s)){
////                        return new GroupUserInfo(s,entity.getName(), Uri.parse(entity.getImg()));
////                    }
////                }
//                return null;
//            }
//        },true);
        //个人信息
//        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
//            @Override
//            public UserInfo getUserInfo(String s) {
//                List<MembersEntity> membersEntity = AppContext.getInstance().getMembersEntity();
//                for(MembersEntity entity : membersEntity){
//                    if(entity.getId().equals(s)){
//                        return new UserInfo(s,entity.getName(), Uri.parse(entity.getImg()));
//                    }
//                }
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
                    LogUtils.e("获取群列表成功");
                    AppContext.getInstance().setGroupEntityList(result.getData());
                }
            }
        });
    }

    private void reqMembers() {
        MembersDTO dto = new MembersDTO();
        dto.setName(AppContext.get("groupname", "")+"群");
        CommonApiClient.members(this, dto, new CallBack<MembersResult>() {
            @Override
            public void onSuccess(MembersResult result) {
                if (AppConfig.SUCCESS.equals(result.getCode())) {
                    LogUtils.e("获取群成员成功");
                    AppContext.getInstance().setMembersEntity(result.getData());
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
                    LogUtils.e("AppContext.get(\"userimager\",\"\")----",""+AppContext.get("userimager",""));
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
