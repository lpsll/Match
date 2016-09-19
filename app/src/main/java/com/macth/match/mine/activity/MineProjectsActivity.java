package com.macth.match.mine.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.macth.match.AppConfig;
import com.macth.match.AppContext;
import com.macth.match.R;
import com.macth.match.common.base.BaseTitleActivity;
import com.macth.match.common.http.CallBack;
import com.macth.match.common.http.CommonApiClient;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.common.widget.EmptyLayout;
import com.macth.match.mine.adapter.MineProjectsAdapter;
import com.macth.match.mine.dto.MyProjectsDto;
import com.macth.match.mine.entity.MineProjectsEntity;
import com.macth.match.mine.entity.MineProjectsResult;

import java.util.List;

import butterknife.Bind;

public class MineProjectsActivity extends BaseTitleActivity {


    @Bind(R.id.mine_projects_recyclerview)
    RecyclerView mineProjectsRecyclerview;
    @Bind(R.id.error_layout)
    EmptyLayout errorLayout;

    private MineProjectsAdapter mineProjectsAdapter;
    private List<MineProjectsEntity> mineProjectsData;

    @Override
    protected int getContentResId() {
        return R.layout.activity_mine_projects;
    }

    @Override
    public void initView() {
        setTitleText("我的项目");
        showDialogLoading();


    }

    @Override
    public void initData() {

        getData();


    }

    /**
     * 获取我的项目数据  拼接uid默认为2
     */
    private void getData() {
        MyProjectsDto dto = new MyProjectsDto();
        dto.setUserid(AppContext.get("usertoken", ""));
        dto.setPage("1");
        CommonApiClient.mineProjects(MineProjectsActivity.this, dto, new CallBack<MineProjectsResult>() {
            @Override
            public void onSuccess(MineProjectsResult result) {
                if (AppConfig.SUCCESS.equals(result.getCode())) {
                    LogUtils.e("推荐项目列表成功");
                    errorLayout.setErrorMessage("暂无项目记录", errorLayout.FLAG_NODATA);
                    errorLayout.setErrorImag(R.drawable.page_icon_empty, errorLayout.FLAG_NODATA);
                    if (null == result.getData()) {
                        errorLayout.setErrorType(EmptyLayout.NODATA);
                    } else {
                        mineProjectsData = result.getData().getList();
                        LogUtils.d("mineProjectsData===========" + mineProjectsData.get(0).getPid());

                        mineProjectsRecyclerview.setLayoutManager(new LinearLayoutManager(MineProjectsActivity.this));
                        mineProjectsAdapter = new MineProjectsAdapter(MineProjectsActivity.this, mineProjectsData);
                        mineProjectsRecyclerview.setAdapter(mineProjectsAdapter);
                        hideDialogLoading();

                    }
                }
            }
        });


    }
}

