package com.macth.match.mine.activity;

import com.macth.match.AppConfig;
import com.macth.match.AppContext;
import com.macth.match.R;
import com.macth.match.common.base.BaseListActivity;
import com.macth.match.common.http.CallBack;
import com.macth.match.common.http.CommonApiClient;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.common.widget.EmptyLayout;
import com.macth.match.mine.adapter.News2Adapter;
import com.macth.match.mine.dto.NewsDto;
import com.macth.match.mine.entity.NewsEntity;
import com.macth.match.mine.entity.NewsResult;
import com.qluxstory.ptrrecyclerview.BaseRecyclerAdapter;

import java.io.Serializable;
import java.util.List;

public class News2Activity extends BaseListActivity<NewsEntity> {

    private News2Adapter news2Adapter;

    @Override
    public BaseRecyclerAdapter<NewsEntity> createAdapter() {
        news2Adapter = new News2Adapter(this);
        return news2Adapter;
    }

    @Override
    protected String getCacheKeyPrefix() {
        return "News2Activity";
    }

    @Override
    public List<NewsEntity> readList(Serializable seri) {
        return ((NewsResult) seri).getData().getList();
    }

    @Override
    public void initView() {
        super.initView();
        setTitleText("消息");
        sendRequestData();
    }

    public boolean autoRefreshIn() {
        return true;
    }

    @Override
    public void initData() {
        super.initData();
//        sendRequestData();
    }

    @Override
    protected void sendRequestData() {

        NewsDto dto = new NewsDto();
        //此处需要替换用户id
        dto.setUserid(AppContext.get("usertoken", ""));
        dto.setPage("1");
        CommonApiClient.newsList(News2Activity.this, dto, new CallBack<NewsResult>() {
            @Override
            public void onSuccess(NewsResult result) {
                if (AppConfig.SUCCESS.equals(result.getCode())) {
                    LogUtils.e("消息列表获取成功");
                    mErrorLayout.setErrorMessage("暂无消息记录", mErrorLayout.FLAG_NODATA);
                    mErrorLayout.setErrorImag(R.drawable.page_icon_empty, mErrorLayout.FLAG_NODATA);
                    if (null == result.getData()) {
                        mErrorLayout.setErrorType(EmptyLayout.NODATA);
                    } else {
                        if (result.getData().getList() == null) {
                            mErrorLayout.setErrorType(EmptyLayout.NODATA);
                        } else {


//                            news2Adapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
//                                @Override
//                                public void onClick(View v, int position, String url) {
//                                    //跳转到消息详情页面
//                                    MineUIGoto.gotoNewsDetails(News2Activity.this,url);
//                                }
//                            });
//
                        }
                        hideDialogLoading();
                    }
                }
            }
        });
    }
}
