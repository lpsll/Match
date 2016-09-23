package com.macth.match.mine.activity;

import android.os.Bundle;
import android.view.View;

import com.macth.match.AppConfig;
import com.macth.match.AppContext;
import com.macth.match.R;
import com.macth.match.common.base.BaseListActivity;
import com.macth.match.common.dto.BaseDTO;
import com.macth.match.common.http.CallBack;
import com.macth.match.common.http.CommonApiClient;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.common.widget.EmptyLayout;
import com.macth.match.mine.adapter.MineProjectsAdapter;
import com.macth.match.mine.entity.MineProjectsEntity;
import com.macth.match.mine.entity.MineProjectsResult;
import com.macth.match.recommend.RecommendUiGoto;
import com.qluxstory.ptrrecyclerview.BaseRecyclerAdapter;

import java.io.Serializable;
import java.util.List;
/**
 * 我的项目
 */
public class MyProjectsActivity extends BaseListActivity<MineProjectsEntity> {

    private MineProjectsAdapter mineProjects2Adapter;

    @Override
    public BaseRecyclerAdapter createAdapter() {
        mineProjects2Adapter = new MineProjectsAdapter(this);
        return mineProjects2Adapter;
    }

    @Override
    protected String getCacheKeyPrefix() {
        return "MyProjectsActivity";
    }


    @Override
    public List readList(Serializable seri) {
        return ((MineProjectsResult) seri).getData().getList();
    }

    @Override
    public void initView() {
        super.initView();
        setTitleText("我的项目");
    }

    @Override
    public void initData() {
//        sendRequestData();
    }

    public boolean autoRefreshIn() {
        return true;
    }

    @Override
    protected void sendRequestData() {
        BaseDTO dto = new BaseDTO();
        dto.setUserid(AppContext.get("usertoken", ""));
        dto.setPage(String.valueOf(mCurrentPage));
        CommonApiClient.mineProjects(MyProjectsActivity.this, dto, new CallBack<MineProjectsResult>() {
            @Override
            public void onSuccess(MineProjectsResult result) {
                if(AppConfig.SUCCESS.equals(result.getCode())){
                    LogUtils.e("我的项目成功");
                    mErrorLayout.setErrorMessage("暂无我的项目记录", mErrorLayout.FLAG_NODATA);
                    mErrorLayout.setErrorImag(R.drawable.page_icon_empty, mErrorLayout.FLAG_NODATA);
                    if(null==result.getData()){
                        mErrorLayout.setErrorType(EmptyLayout.NODATA);
                    }else {
                        requestDataSuccess(result);
                        setDataResult(result.getData().getList());
                    }
                }
            }
        });

    }
    private String flag;
    @Override
    public void onItemClick(View itemView, Object itemBean, int position) {
        super.onItemClick(itemView, itemBean, position);
//        MineProjectsEntity entity = (MineProjectsEntity) itemBean;
//        Bundle b = new Bundle();
//        b.putString("pid", entity.getPid());
//        RecommendUiGoto.gotoProject(this, b);

        MineProjectsEntity entity = (MineProjectsEntity) itemBean;
        Bundle b = new Bundle();
        if(AppContext.get("useridentity","").equals("内部用户")){
            flag ="1";
        }else {
            flag ="2";
        }
        b.putString("title","项目详情");
        b.putString("url", AppConfig.DETAILS_H5_URL+ AppContext.get("usertoken","")+"&flag="+flag+"&pid="+entity.getPid());
        LogUtils.e("url---",""+AppConfig.DETAILS_H5_URL+ AppContext.get("usertoken","")+"&flag="+flag+"&pid="+entity.getPid());
        RecommendUiGoto.gotoPdb(this, b);
    }
}
