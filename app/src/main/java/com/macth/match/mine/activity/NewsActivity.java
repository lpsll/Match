package com.macth.match.mine.activity;

import android.view.View;
import android.widget.ImageView;

import com.macth.match.AppConfig;
import com.macth.match.AppContext;
import com.macth.match.R;
import com.macth.match.common.base.BaseListActivity;
import com.macth.match.common.dto.BaseDTO;
import com.macth.match.common.entity.BaseEntity;
import com.macth.match.common.http.CallBack;
import com.macth.match.common.http.CommonApiClient;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.common.utils.ToastUtils;
import com.macth.match.common.widget.EmptyLayout;
import com.macth.match.mine.MineUIGoto;
import com.macth.match.mine.adapter.NewsAdapter;
import com.macth.match.mine.dto.DeleteNewDTO;
import com.macth.match.mine.entity.NewsEntity;
import com.macth.match.mine.entity.NewsResult;
import com.qluxstory.ptrrecyclerview.BaseRecyclerAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends BaseListActivity<NewsEntity> {

    private NewsAdapter newsAdapter;

    @Override
    public BaseRecyclerAdapter<NewsEntity> createAdapter() {
        newsAdapter = new NewsAdapter(this);
        return newsAdapter;
    }

    @Override
    protected String getCacheKeyPrefix() {
        return "NewsActivity";
    }

    @Override
    public List<NewsEntity> readList(Serializable seri) {
        return ((NewsResult) seri).getData().getList();
    }

    @Override
    public void initView() {
        super.initView();
        setTitleText("消息");
    }

    public boolean autoRefreshIn() {
        return true;
    }

    @Override
    public void initData() {
        mCurrentPage = 1;
        sendRequestData();
    }

    @Override
    protected void sendRequestData() {

        BaseDTO dto = new BaseDTO();
        dto.setUserid(AppContext.get("usertoken", ""));
        dto.setPage(String.valueOf(mCurrentPage));

        CommonApiClient.newsList(NewsActivity.this, dto, new CallBack<NewsResult>() {
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

                            requestDataSuccess(result);
                            setDataResult(result.getData().getList());

                        }
                        hideDialogLoading();
                    }
                }
            }
        });
    }

    List<NewsEntity> list = new ArrayList<>();
    @Override
    public void onItemClick(View itemView, Object itemBean, final int position) {
        super.onItemClick(itemView, itemBean, position);
        final NewsEntity entity = (NewsEntity) itemBean;
        String notice_url = entity.getNotice_url();
        list.add(position,entity);
        //跳转到消息详情页面
        MineUIGoto.gotoNewsDetails(NewsActivity.this, notice_url);

        ImageView delete = (ImageView) itemView.findViewById(R.id.img_news_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNew(list.get(position).getId());
                ToastUtils.showShort(NewsActivity.this,"shangchuu");
            }
        });
    }

    /**
     * 调接口删除数据
     */
    private void deleteNew(final String id) {

        DeleteNewDTO deleteNewDTO = new DeleteNewDTO();

        //修改userid
        deleteNewDTO.setUserid(AppContext.get("usertoken", ""));
        deleteNewDTO.setNoticeid(id);

        CommonApiClient.deleteNew(this, deleteNewDTO, new CallBack<BaseEntity>() {
            @Override
            public void onSuccess(BaseEntity result) {
                LogUtils.e("result========" + result.getMsg());
                if (AppConfig.SUCCESS.equals(result.getCode())) {
                    LogUtils.e("删除成功");
                    ToastUtils.showShort(NewsActivity.this, "删除成功");

                    mCurrentPage = 1;
                    sendRequestData();
                    newsAdapter.notifyItemRemoved(Integer.parseInt(id));

                }
            }
        });
    }
}
