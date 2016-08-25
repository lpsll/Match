package com.macth.match.mine.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.macth.match.R;
import com.macth.match.common.base.BaseFragment;

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
    ImageView rlMineExit;

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

    }


    @OnClick({R.id.rl_mine_projects, R.id.rl_mine_news, R.id.rl_mine_setting, R.id.rl_mine_exit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_mine_projects:
                //进入到我的项目页
                startActivity(new Intent(getActivity(),MineProjectsActivity.class));
                break;
            case R.id.rl_mine_news:
                break;
            case R.id.rl_mine_setting:
                break;
            case R.id.rl_mine_exit:
                break;
        }
    }
}
