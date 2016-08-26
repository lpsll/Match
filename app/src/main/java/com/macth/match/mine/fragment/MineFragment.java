package com.macth.match.mine.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.macth.match.AppConfig;
import com.macth.match.AppContext;
import com.macth.match.R;
import com.macth.match.common.base.BaseFragment;
import com.macth.match.common.utils.DialogUtils;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.login.activity.LoginActivity;
import com.macth.match.mine.activity.MineProjectsActivity;
import com.macth.match.register.activity.AddInfoActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by John_Libo on 2016/8/22.
 */
public class MineFragment extends BaseFragment {
    @Bind(R.id.tv_username)
    TextView tvUsername;
    @Bind(R.id.tv_userphone)
    TextView tvUserphone;
    @Bind(R.id.rl_mine_projects)
    RelativeLayout rlMineProjects;
    @Bind(R.id.rl_mine_news)
    RelativeLayout rlMineNews;
    @Bind(R.id.rl_mine_setting)
    RelativeLayout rlMineSetting;
    @Bind(R.id.rl_mine_exit)
    RelativeLayout rlMineExit;
    @Bind(R.id.img_login_touxiang)
    ImageView imgLoginTouxiang;

    private boolean isLogin;

    @Override
    protected void retry() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void initData() {
        //获取用户登录状态
        isLogin = AppContext.get(AppConfig.IS_LOGIN, false);
        LogUtils.i("登录状态====" + AppContext.get(AppConfig.IS_LOGIN, false));

        addUsernameAndPhone();
    }

    /**
     * 显示登录用户的用户名和手机号
     */
    private void addUsernameAndPhone() {
        if (isLogin) {
            imgLoginTouxiang.setBackgroundResource(R.drawable.touxiangxdpi_03);
            String userName = AppContext.get(AppConfig.USERNAME, "");
            tvUsername.setText(userName);
            String mobile = AppContext.get(AppConfig.USERMOBILE, "");
            tvUserphone.setText(mobile);
        } else {
            imgLoginTouxiang.setBackgroundResource(R.drawable.touxiang_zhuce);
        }
    }


    @OnClick({R.id.rl_mine_projects, R.id.rl_mine_news, R.id.rl_mine_setting, R.id.rl_mine_exit, R.id.img_login_touxiang})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_mine_projects:
                //进入到我的项目页
                if (!isLogin) {
                    //打开登录页面
                    startActivity(new Intent(getContext(), LoginActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), MineProjectsActivity.class));

                }
                break;
            case R.id.rl_mine_news:
                if (!isLogin) {
                    //打开登录页面
                    startActivity(new Intent(getContext(), LoginActivity.class));
                } else {

                }
                break;
            case R.id.rl_mine_setting:
//                if (!isLogin) {
//                    //打开登录页面
//                    startActivity(new Intent(getContext(), LoginActivity.class));
//                } else {
////                    startActivity(new Intent(getActivity(), LoginActivity.class));
//                }
                startActivity(new Intent(getContext(), AddInfoActivity.class));

                break;
            case R.id.rl_mine_exit:
                if (!isLogin) {
                    //打开登录页面
                    startActivity(new Intent(getContext(), LoginActivity.class));

                }else {
                    //退出对话框
                    exitDialog();

                }

                break;

            case R.id.img_login_touxiang:
                if (!isLogin) {
                    //打开登录页面
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
                break;
        }
    }

    /**
     * 退出对话框
     */
    private void exitDialog() {
        DialogUtils.confirm(getContext(), "确定要退出吗？", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                exit();
            }
        });

    }

    /**
     * 退出操作
     */
    private void exit() {

        AppContext.set("username", null);
        AppContext.set("usermobile", null);
        AppContext.set("useridentity", null);
        AppContext.set("usercompany", null);
        AppContext.set("userwork", null);
        AppContext.set("usertoken", null);
        AppContext.set("cooperativeid", null);
        AppContext.set("isLogin", false);


    }
}
