package com.macth.match.recommend.fragment;

import android.os.Bundle;
import android.view.View;

import com.macth.match.AppConfig;
import com.macth.match.AppContext;
import com.macth.match.R;
import com.macth.match.common.base.BaseListFragment;
import com.macth.match.common.dto.BaseDTO;
import com.macth.match.common.http.CallBack;
import com.macth.match.common.http.CommonApiClient;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.common.widget.EmptyLayout;
import com.macth.match.recommend.RecommendUiGoto;
import com.macth.match.recommend.adapter.RecommendAdapter;
import com.macth.match.recommend.entity.RecommendEntity;
import com.macth.match.recommend.entity.RecommendResult;
import com.qluxstory.ptrrecyclerview.BaseRecyclerAdapter;

import java.io.Serializable;
import java.util.List;

/**
 * 推荐页
 */
public class RecommendFragment extends BaseListFragment<RecommendEntity> {
    private String flag;
    @Override
    public BaseRecyclerAdapter<RecommendEntity> createAdapter() {
        return new RecommendAdapter(getActivity());
    }

    @Override
    protected String getCacheKeyPrefix() {
        return "RecommendFragment";
    }

    @Override
    public List<RecommendEntity> readList(Serializable seri) {
        return ((RecommendResult)seri).getData().getList();
    }

    private void reqRecommend() {
        BaseDTO dto = new BaseDTO();
        dto.setPage(String.valueOf(mCurrentPage));
        CommonApiClient.recommend(this, dto, new CallBack<RecommendResult>() {
            @Override
            public void onSuccess(RecommendResult result) {
                if (AppConfig.SUCCESS.equals(result.getCode())) {
                    LogUtils.e("推荐项目列表成功");
                    mErrorLayout.setErrorMessage("暂无推荐记录", mErrorLayout.FLAG_NODATA);
                    mErrorLayout.setErrorImag(R.drawable.page_icon_empty, mErrorLayout.FLAG_NODATA);
                    if (null == result.getData()) {
                        mErrorLayout.setErrorType(EmptyLayout.NODATA);
                    } else {
                        requestDataSuccess(result);
                        setDataResult(result.getData().getList());
                    }
                }

            }
        });

    }

    @Override
    public void initData() {
        mCurrentPage =1;
        sendRequestData();

    }
    @Override
    protected void sendRequestData() {
        reqRecommend();
    }

    public boolean autoRefreshIn(){
        return true;
    }


    @Override
    public void onItemClick(View itemView, Object itemBean, int position) {
        super.onItemClick(itemView, itemBean, position);
        RecommendEntity entity = (RecommendEntity) itemBean;
        Bundle b = new Bundle();
        if(AppContext.get("useridentity","").equals("内部用户")){
            flag ="1";
        }else {
            flag ="2";
        }
        b.putString("title","项目详情");
        b.putString("url", AppConfig.DETAILS_H5_URL+ AppContext.get("usertoken","")+"&flag="+flag+"&pid="+entity.getPid());
        LogUtils.e("url---",""+AppConfig.DETAILS_H5_URL+ AppContext.get("usertoken","")+"&flag="+flag+"&pid="+entity.getPid());
        RecommendUiGoto.gotoPdb(getActivity(), b);

    }
}
