package com.macth.match.mine.activity;

import com.macth.match.AppConfig;
import com.macth.match.AppContext;
import com.macth.match.R;
import com.macth.match.common.base.BaseListActivity;
import com.macth.match.common.dto.BaseDTO;
import com.macth.match.common.http.CallBack;
import com.macth.match.common.http.CommonApiClient;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.common.widget.EmptyLayout;
import com.macth.match.mine.adapter.MineProjects2Adapter;
import com.macth.match.mine.entity.MineProjectsEntity;
import com.macth.match.mine.entity.MineProjectsResult;
import com.qluxstory.ptrrecyclerview.BaseRecyclerAdapter;

import java.io.Serializable;
import java.util.List;

public class MyProjects2Activity extends BaseListActivity<MineProjectsEntity> {

    private MineProjects2Adapter mineProjects2Adapter;

    @Override
    public BaseRecyclerAdapter createAdapter() {
        mineProjects2Adapter = new MineProjects2Adapter();
        return mineProjects2Adapter;
    }

    @Override
    protected String getCacheKeyPrefix() {

        return "MyProjects2Activity";
    }


    @Override
    public List readList(Serializable seri) {
        return ((MineProjectsResult) seri).getData().getList();
    }

    @Override
    public void initView() {
        super.initView();
        setTitleText("我的项目");
        sendRequestData();
    }

    @Override
    public void initData() {
        super.initData();
//        sendRequestData();
    }

    public boolean autoRefreshIn() {
        return true;
    }

    @Override
    protected void sendRequestData() {

        BaseDTO dto=new BaseDTO();
        dto.setUserid(AppContext.get("usertoken",""));
        CommonApiClient.mineProjects(MyProjects2Activity.this, dto, new CallBack<MineProjectsResult>() {
            @Override
            public void onSuccess(MineProjectsResult result) {
                if(AppConfig.SUCCESS.equals(result.getCode())){
                    LogUtils.e("推荐项目列表成功");
                    mErrorLayout.setErrorMessage("暂无推荐记录", mErrorLayout.FLAG_NODATA);
                    mErrorLayout.setErrorImag(R.drawable.page_icon_empty, mErrorLayout.FLAG_NODATA);
                    if(null==result.getData()){
                        mErrorLayout.setErrorType(EmptyLayout.NODATA);
                    }else {
                        setDataResult(result.getData().getList());
                    }
                }
            }
        });

    }
}
