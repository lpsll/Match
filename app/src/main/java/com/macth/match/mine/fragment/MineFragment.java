package com.macth.match.mine.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.macth.match.AppContext;
import com.macth.match.R;
import com.macth.match.common.base.BaseFragment;
import com.macth.match.common.utils.DialogUtils;
import com.macth.match.common.utils.ImageLoaderUtils;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.mine.MineUIGoto;
import com.macth.match.mine.activity.MyProjectsActivity;
import com.macth.match.mine.entity.PicEvent;

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
    @Bind(R.id.lin_user)
    LinearLayout mLinUser;

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
        LogUtils.e("isLogin====","" + AppContext.get("IS_LOGIN", false));
        LogUtils.e("userimager====" ,""+ AppContext.get("userimager", ""));
        if (isLogin) {
            LogUtils.e("if====" ,"if");
            String userName = AppContext.get("username", "");
            tvUsername.setText(userName);
            String mobile = AppContext.get("usermobile", "");
            tvUserphone.setText(mobile);
            LogUtils.e("userimager---", AppContext.get("userimager", ""));
            if (TextUtils.isEmpty(AppContext.get("userimager", ""))) {

                imgLoginTouxiang.setBackgroundResource(R.drawable.morenxdpi_03);
            } else {
                ImageLoaderUtils.displayAvatarImage(AppContext.get("userimager", ""), imgLoginTouxiang);

            }

        } else {
            LogUtils.e("else====" ,"else");
            imgLoginTouxiang.setBackgroundResource(R.drawable.touxiang_zhuce);
            tvUserphone.setText("");
            tvUsername.setText("请登录");
        }

    }

    @Override
    public void initData() {

    }


    @OnClick({R.id.rl_mine_projects, R.id.rl_mine_news, R.id.rl_mine_setting, R.id.rl_mine_exit, R.id.lin_user})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_mine_projects:
                //进入到我的项目页
                if (!isLogin) {
                    //打开登录页面
                    new AlertDialog.Builder(getActivity()).setTitle("温馨提示").setMessage("您还没有登录，是否进行登录？").setNegativeButton("暂不登录", null).setPositiveButton("去登录", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MineUIGoto.gotoLoginForPwd(getActivity());
                        }
                    }).show();
                } else {
                    startActivity(new Intent(getActivity(), MyProjectsActivity.class));
                }

                break;
            case R.id.rl_mine_news:
                if (!isLogin) {
                    //打开登录页面
                    new AlertDialog.Builder(getActivity()).setTitle("温馨提示").setMessage("您还没有登录，是否进行登录？").setNegativeButton("暂不登录", null).setPositiveButton("去登录", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MineUIGoto.gotoLoginForPwd(getActivity());
                        }
                    }).show();

                } else {
                    MineUIGoto.gotoNews(getActivity());
                }
                break;
            case R.id.rl_mine_setting:
                if (!isLogin) {
                    //打开登录页面
                    new AlertDialog.Builder(getActivity()).setTitle("温馨提示").setMessage("您还没有登录，是否进行登录？").setNegativeButton("暂不登录", null).setPositiveButton("去登录", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MineUIGoto.gotoLoginForPwd(getActivity());
                        }
                    }).show();

                } else {
                    //打开设置页面
                    MineUIGoto.gotoSetting(getActivity());
                }
//                startActivity(new Intent(getContext(), AddInfoActivity.class));

                break;
            case R.id.rl_mine_exit:
                if (!isLogin) {
                    //打开登录页面
                    new AlertDialog.Builder(getActivity()).setTitle("温馨提示").setMessage("您还没有登录，是否进行登录？").setNegativeButton("暂不登录", null).setPositiveButton("去登录", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MineUIGoto.gotoLoginForPwd(getActivity());
                        }
                    }).show();

                } else {
                    //退出对话框
                    exitDialog();
                }
                break;

            case R.id.lin_user:
                if (!isLogin) {
                    //打开登录页面
                    MineUIGoto.gotoLoginForPwd(getActivity());
                } else {
                    MineUIGoto.gotoPersonal(getActivity()); //个人信息
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
        AppContext.set("userimager", "");
        AppContext.set("IS_LOGIN", false);

        imgLoginTouxiang.setBackgroundResource(R.drawable.touxiang_zhuce);
        tvUserphone.setText("");
        tvUsername.setText("请登录");
        //刷新MainActivity
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        MineFragment meFragment = (MineFragment) fragmentManager.findFragmentByTag("tag4");
//        meFragment.initView(null);


    }


    public void onEventMainThread(PicEvent event) {
        String msg = event.getMsg();
        LogUtils.e("msg---", "" + msg);
        if (TextUtils.isEmpty(msg)) {

        } else {
            tvUsername.setText(AppContext.get("username", ""));
            LogUtils.e("userimager---", "" + AppContext.get("userimager", ""));
            if (TextUtils.isEmpty(AppContext.get("userimager", ""))) {
                imgLoginTouxiang.setBackgroundResource(R.drawable.morenxdpi_03);
            } else {
                ImageLoaderUtils.displayAvatarImage(AppContext.get("userimager", ""), imgLoginTouxiang);

            }
        }
    }
}
