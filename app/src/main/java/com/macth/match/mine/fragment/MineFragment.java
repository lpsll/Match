package com.macth.match.mine.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.macth.match.AppContext;
import com.macth.match.R;
import com.macth.match.common.base.BaseFragment;
import com.macth.match.common.utils.DialogUtils;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.mine.MineUIGoto;
import com.macth.match.mine.activity.MyProjectsActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 我的
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
        //获取用户登录状态
        isLogin = AppContext.get("IS_LOGIN", false);
        LogUtils.i("登录状态====" + AppContext.get("IS_LOGIN", false));

        addUsernameAndPhone();

    }

    @Override
    public void initData() {

    }

    /**
     * 显示登录用户的用户名和手机号
     */
    private void addUsernameAndPhone() {
        if (isLogin) {
            imgLoginTouxiang.setBackgroundResource(R.drawable.touxiangxdpi_03);
            String userName = AppContext.get("username", "");
            tvUsername.setText(userName);
            String mobile = AppContext.get("usermobile", "");
            tvUserphone.setText(mobile);
        } else {
            imgLoginTouxiang.setBackgroundResource(R.drawable.touxiang_zhuce);
            tvUserphone.setText("");
            tvUsername.setText("请登录");
        }
    }


    @OnClick({R.id.rl_mine_projects, R.id.rl_mine_news, R.id.rl_mine_setting, R.id.rl_mine_exit, R.id.img_login_touxiang})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_mine_projects:
                //进入到我的项目页
                if (!isLogin) {
                    //打开登录页面

                    MineUIGoto.gotoLoginForPwd(getActivity());
                } else {
//                    startActivity(new Intent(getActivity(), MineProjectsActivity.class));
                    startActivity(new Intent(getActivity(), MyProjectsActivity.class));

                }
                break;
            case R.id.rl_mine_news:
                if (!isLogin) {
                    //打开登录页面
                    MineUIGoto.gotoLoginForPwd(getActivity());
                } else {
                    MineUIGoto.gotoNews(getActivity());
                }
                break;
            case R.id.rl_mine_setting:
                if (!isLogin) {
                    //打开登录页面
                    MineUIGoto.gotoLoginForPwd(getActivity());
                } else {
                    //打开设置页面
                    MineUIGoto.gotoSetting(getActivity());
                }
//                startActivity(new Intent(getContext(), AddInfoActivity.class));

                break;
            case R.id.rl_mine_exit:
                if (!isLogin) {
                    //打开登录页面
                    MineUIGoto.gotoLoginForPwd(getActivity());

                } else {
                    //退出对话框
                    exitDialog();

                }

                break;

            case R.id.img_login_touxiang:
                if (!isLogin) {
                    //打开登录页面
                    MineUIGoto.gotoLoginForPwd(getActivity());
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

        AppContext.set("username", "");
        AppContext.set("usermobile", "");
        AppContext.set("useridentity", "");
        AppContext.set("usercompany", "");
        AppContext.set("userwork", "");
        AppContext.set("usertoken", "");
        AppContext.set("cooperativeid", "");
        AppContext.set("IS_LOGIN", false);


        //刷新MainActivity
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        MineFragment meFragment = (MineFragment) fragmentManager.findFragmentByTag("tag4");
        meFragment.initView(null);



    }
}
