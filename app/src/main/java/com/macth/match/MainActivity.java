package com.macth.match;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.macth.match.common.base.BaseFragment;
import com.macth.match.common.base.BaseTitleActivity;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.common.utils.TextViewUtils;
import com.macth.match.find.fragment.FindFragment;
import com.macth.match.mine.MineUIGoto;
import com.macth.match.mine.fragment.MineFragment;
import com.macth.match.notice.fragment.NoticeFragment;
import com.macth.match.recommend.RecommendUiGoto;
import com.macth.match.recommend.fragment.RecommendFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

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
    private List<BaseFragment> fragmentList=new ArrayList<>();
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

    private int currentTab=-1; // 当前Tab页面索引
    private TextView mBaseEnsure,mBaseBack;

    @Override
    protected int getContentResId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
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
                    LogUtils.e("j----",""+j);
                    showTab(j);
                }
            });
        }
        showTab(0); // 显示目标tab
    }

    /**
     *
     * @param fragment 除了fragment，其他的都hide
     */
    private void hideAllFragments(BaseFragment fragment) {
        for (int i = 0; i < TAB_NUM; i++) {
            Fragment f = fragmentManager.findFragmentByTag("tag" + i);
            LogUtils.e("f----","i--"+i+"---"+f);
            if (f != null&&f.isAdded()&&f!=fragment) {
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
                fragment = new RecommendFragment();
                break;
            case 4:
                fragment = new MineFragment();
                break;

        }
        LogUtils.e("index----", "" + index);
        LogUtils.e("fragment----", "" + fragment);
        fragmentList.add(index,fragment);
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
        LogUtils.e("intent-----",intent+"");
        if(intent != null) {
            if(intent.getExtras()!=null){
                int tag = intent.getExtras().getInt("tag");
                LogUtils.e("tag-----",tag+"");
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
        if(currentTab==idx){return;}
        BaseFragment targetFragment = (BaseFragment) fragmentManager
                .findFragmentByTag("tag" + idx);
        LogUtils.e("showTab---idx----",""+idx);
        LogUtils.e("targetFragment----", "" + targetFragment);
//        targetFragment = fragmentList.get(idx);

        if (targetFragment == null || !targetFragment.isAdded()) {
            LogUtils.e("size----", "" + fragmentList.size());
            if(idx<fragmentList.size()&&fragmentList.get(idx)!=null) {
                targetFragment = fragmentList.get(idx);
                LogUtils.e("targetFragment----idx---if", "" + idx+"---"+targetFragment);
            }else{
                targetFragment=addFragment(idx);
                LogUtils.e("targetFragment----idx---else", "" +idx+"---"+ targetFragment);
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
        switch (currentTab){
            case 0:
                setTitleText("推荐");
                mBaseEnsure.setVisibility(View.VISIBLE);
                mBaseEnsure.setText("新增项目");
                break;
            case 1:
                setTitleText("发现");
                mBaseEnsure.setText("筛选");
                break;
            case 2:
                setTitleText("公告");
                mBaseEnsure.setVisibility(View.GONE);
                break;
            case 3:
                setTitleText("群组");
                mBaseEnsure.setVisibility(View.GONE);
                break;
            case 4:
                setTitleText("我的");
                mBaseEnsure.setVisibility(View.GONE);
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
                RecommendUiGoto.gotoAddItem(this);
            default:
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == MineUIGoto.LOFIN_REQUEST)
        {
            FragmentManager fragmentManager = getSupportFragmentManager();
            MineFragment meFragment = (MineFragment)fragmentManager.findFragmentByTag("tag4");
            meFragment.initView(null);
        }
    }

}
