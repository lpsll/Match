package com.macth.match;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.macth.match.common.base.BaseApplication;
import com.macth.match.common.base.BaseFragment;
import com.macth.match.common.base.BaseTitleActivity;
import com.macth.match.common.base.SimplePage;
import com.macth.match.common.dto.BaseDTO;
import com.macth.match.common.http.CallBack;
import com.macth.match.common.http.CommonApiClient;
import com.macth.match.common.utils.DialogUtils;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.common.utils.TextViewUtils;
import com.macth.match.common.utils.UIHelper;
import com.macth.match.common.widget.EmptyLayout;
import com.macth.match.find.fragment.FindFragment;
import com.macth.match.group.GroupUiGoto;
import com.macth.match.group.entity.GroupEntity;
import com.macth.match.group.entity.GroupResult;
import com.macth.match.group.fragment.ConversationFragment;
import com.macth.match.group.fragment.GroupFragment;
import com.macth.match.mine.MineUIGoto;
import com.macth.match.mine.fragment.MineFragment;
import com.macth.match.notice.fragment.NoticeFragment;
import com.macth.match.recommend.RecommendUiGoto;
import com.macth.match.recommend.fragment.RecommendFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.model.GroupUserInfo;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.UserInfo;

public class MainActivity extends BaseTitleActivity {
    @Bind(R.id.tv_tab_recommend)
    TextView mTvTabRecommend;
    @Bind(R.id.tv_tab_find)
    TextView mTvTabFind;
    @Bind(R.id.tv_tab_notice)
    TextView mTvTabNotice;
    @Bind(R.id.tv_tab_group)
    TextView mTvTabGroup;
    @Bind(R.id.tv_tab_mine)
    TextView mTvTabMine;

    public static final int TAB_NUM = 5;
    private TextView[] mTabViews = new TextView[TAB_NUM];
    private FragmentManager fragmentManager;
    private List<BaseFragment> fragmentList = new ArrayList<>();
    /**
     * Tab图片没有选中的状态资源ID
     */
    private int[] mTabIconNors = {
            R.drawable.tuijianxdpi_03,
            R.drawable.faxianxdpi_03,
            R.drawable.gonggaoxdpi_03,
            R.drawable.qunzuxdpi_03,
            R.drawable.wodexdpi_03};
    /**
     * Tab图片选中的状态资源ID
     */
    private int[] mTabIconSels = {
            R.drawable.tuijianhxdpi_03,
            R.drawable.faxianhxdpi_03,
            R.drawable.gonggaohxdpi_03,
            R.drawable.qunzuhxdpi_03,
            R.drawable.wodehxdpi_03};

    private int currentTab = -1; // 当前Tab页面索引
    private TextView mBaseEnsure, mBaseBack;
    int fg1,fg2,fg3,fg4,fg5;
    private String flag;
    boolean login;

    @Override
    protected int getContentResId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        login = AppContext.get("IS_LOGIN",false);
        if(login){
            Initialization();//初始化聊天界面信息
        }

        fg1 = 0;
        fg2 = 0;
        fg3 = 0;
        fg4 = 0;
        fg5 = 0;

        mBaseBack = (TextView) findViewById(R.id.base_titlebar_back);
        mBaseBack.setVisibility(View.GONE);
        mBaseEnsure = (TextView) findViewById(R.id.base_titlebar_ensure);
        mBaseEnsure.setOnClickListener(this);

        fragmentManager = getSupportFragmentManager();
        mTabViews[0] = mTvTabRecommend;
        mTabViews[1] = mTvTabFind;
        mTabViews[2] = mTvTabNotice;
        mTabViews[3] = mTvTabGroup;
        mTabViews[4] = mTvTabMine;

        for (int i = 0; i < mTabViews.length; i++) {
            fragmentList.add(null);
//            addFragment(i);
            final int j = i;
            mTabViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LogUtils.e("j----", "" + j);
                    showTab(j);
                }
            });
        }
        showTab(0); // 显示目标tab
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
                        return new Group(s,entity.getGroupname(),Uri.parse(entity.getGroupimg()));
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


    /**
     * @param fragment 除了fragment，其他的都hide
     */
    private void hideAllFragments(BaseFragment fragment) {
        for (int i = 0; i < TAB_NUM; i++) {
            Fragment f = fragmentManager.findFragmentByTag("tag" + i);
            LogUtils.e("f----", "i--" + i + "---" + f);
            if (f != null && f.isAdded() && f != fragment) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.hide(f);
                transaction.commitAllowingStateLoss();
                f.setUserVisibleHint(false);
            }
        }
    }

    private BaseFragment addFragment(int index) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        BaseFragment fragment = null;
        switch (index) {
            case 0:
                fragment = new RecommendFragment();
                break;
            case 1:
                fragment = new FindFragment();
                break;
            case 2:
                fragment = new NoticeFragment();
                break;
            case 3:
                fragment = new ConversationFragment();
                break;
            case 4:
                fragment = new MineFragment();
                break;

        }
        LogUtils.e("index----", "" + index);
        LogUtils.e("fragment----", "" + fragment);
        fragmentList.add(index, fragment);
        transaction.add(R.id.realtabcontent, fragment, "tag" + index);
        transaction.commitAllowingStateLoss();
        // fragmentManager.executePendingTransactions();
        return fragment;
    }

    private void showFragment(BaseFragment fragment) {
        LogUtils.e("fragment----showFragment", "" + fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.show(fragment);
        transaction.commitAllowingStateLoss();
        fragment.setUserVisibleHint(true);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        LogUtils.e("intent-----", intent + "");
        if (intent != null) {
            if (intent.getExtras() != null) {
                int tag = intent.getExtras().getInt("tag");
                LogUtils.e("tag-----", tag + "");
                showTab(tag);
            }


        }
    }

    /**
     * 切换tab
     *
     * @param idx
     */
    private void showTab(int idx) {
        if (currentTab == idx) {
            return;
        }
        BaseFragment targetFragment = (BaseFragment) fragmentManager
                .findFragmentByTag("tag" + idx);
        LogUtils.e("showTab---idx----", "" + idx);
        LogUtils.e("targetFragment----", "" + targetFragment);
//        targetFragment = fragmentList.get(idx);

        if (targetFragment == null || !targetFragment.isAdded()) {
            LogUtils.e("size----", "" + fragmentList.size());
            if (idx < fragmentList.size() && fragmentList.get(idx) != null) {
                targetFragment = fragmentList.get(idx);
                LogUtils.e("targetFragment----idx---if", "" + idx + "---" + targetFragment);
            } else {
                targetFragment = addFragment(idx);
                LogUtils.e("targetFragment----idx---else", "" + idx + "---" + targetFragment);
            }
        }


        hideAllFragments(targetFragment);
        showFragment(targetFragment);
        for (int i = 0; i < TAB_NUM; i++) {
            if (idx == i) {
                mTabViews[i].setTextColor(ContextCompat.getColor(this, R.color.navi));
                TextViewUtils.setTextViewIcon(this, mTabViews[i],
                        mTabIconSels[i], R.dimen.bottom_tab_icon_width,
                        R.dimen.bottom_tab_icon_height, TextViewUtils.DRAWABLE_TOP);
            } else {
                mTabViews[i].setTextColor(ContextCompat.getColor(this, R.color.btn_gray));
                TextViewUtils.setTextViewIcon(this, mTabViews[i],
                        mTabIconNors[i], R.dimen.bottom_tab_icon_width,
                        R.dimen.bottom_tab_icon_height, TextViewUtils.DRAWABLE_TOP);
            }
        }
        currentTab = idx; // 更新目标tab为当前tab
        LogUtils.e("currentTab----", "" + currentTab);
        getTitleLayout().setVisibility(View.VISIBLE);
        switch (currentTab) {
            case 0:
                setTitleText("推荐");
                mBaseEnsure.setVisibility(View.VISIBLE);
                mBaseEnsure.setText("新增项目");
                if(fg1==0){
                    fg1=1;
                }else {
                    targetFragment.initData();
                }
                break;
            case 1:
                setTitleText("发现");
                mBaseEnsure.setVisibility(View.GONE);
                if(fg1==0){
                    fg1=1;
                }else {
                    targetFragment.initData();
                }
                break;
            case 2:
                setTitleText("公告");
                mBaseEnsure.setVisibility(View.GONE);
                if(fg2==0){
                    fg2=1;
                }else {
                    targetFragment.initData();
                }
                break;
            case 3:
                setTitleText("会话");
                mBaseEnsure.setVisibility(View.VISIBLE);
                mBaseEnsure.setText("群组");
                if(fg3==0){
                    fg3=1;
                }else {
                    targetFragment.initView(null);
                }
                break;
            case 4:
                setTitleText("我的");
                mBaseEnsure.setVisibility(View.GONE);
                if(fg4==0){
                    fg4=1;
                }else {
                    targetFragment.initView(null);
                }
                break;

        }
    }

    @Override
    public void initData() {
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.base_titlebar_ensure:
                if(mBaseEnsure.getText().toString().equals("新增项目")){
                    if(AppContext.get("useridentity","").equals("内部用户")){
                        flag ="1";
                    }else {
                        flag ="2";
                    }
                    Bundle b = new Bundle();
                    b.putString("url", AppConfig.ADD_H5_URL+ AppContext.get("usertoken","")+"&flag="+flag);
                    LogUtils.e("url---",""+AppConfig.ADD_H5_URL+ AppContext.get("usertoken","")+"&flag="+flag);
                    RecommendUiGoto.gotoAdded(this, b);

                }
                else if(mBaseEnsure.getText().toString().equals("群组")){
                    LogUtils.e("login---",""+login);
                    login =AppContext.get("IS_LOGIN",false);
                    if(login) {
                        UIHelper.showFragment(MainActivity.this, SimplePage.GROUP);//群组
                    }else {
                        DialogUtils.confirm(this, "您尚未登录，是否去登录？", listener);
                    }
                }

            default:
                break;
        }
    }

    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            GroupUiGoto.gotoLogin(MainActivity.this);
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MineUIGoto.LOFIN_REQUEST) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            MineFragment meFragment = (MineFragment) fragmentManager.findFragmentByTag("tag4");
            if (meFragment != null) {
                meFragment.initView(null);
            }
        }

        if (requestCode == GroupUiGoto.LG_REQUEST) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            ConversationFragment conversationFragment = (ConversationFragment) fragmentManager.findFragmentByTag("tag3");
            if (conversationFragment != null) {
                conversationFragment.initView(null);
            }
        }
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
        }}

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
}
