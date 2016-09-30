package com.macth.match.notice.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.macth.match.AppConfig;
import com.macth.match.R;
import com.macth.match.common.base.BasePullScrollViewFragment;
import com.macth.match.common.dto.BaseDTO;
import com.macth.match.common.http.CallBack;
import com.macth.match.common.http.CommonApiClient;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.common.widget.EmptyLayout;
import com.macth.match.notice.entity.NoticeEntity;
import com.macth.match.notice.entity.NoticeResult;
import com.macth.match.recommend.RecommendUiGoto;
import com.qluxstory.ptrrecyclerview.BaseRecyclerAdapter;
import com.qluxstory.ptrrecyclerview.BaseRecyclerViewHolder;
import com.qluxstory.ptrrecyclerview.BaseSimpleRecyclerAdapter;

import butterknife.Bind;

/**
 * 公告页面
 */
public class NoticeFragment extends BasePullScrollViewFragment {
    @Bind(R.id.recyclerview_notice)
    RecyclerView mRecyclerview;
    BaseSimpleRecyclerAdapter mAdapter;


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_notice;
    }
    @Override
    public void initView(View view) {
        super.initView(view);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter=new BaseSimpleRecyclerAdapter<NoticeEntity>() {

            @Override
            public int getItemViewLayoutId() {
                return R.layout.item_notice;
            }

            @Override
            public void bindData(BaseRecyclerViewHolder holder, NoticeEntity noticeEntity, int position) {
                holder.setText(R.id.tv_title, noticeEntity.getMessage_title());
                holder.setText(R.id.tv_notice_date, noticeEntity.getMessage_ctime());
                holder.setText(R.id.tv_notice_msg, noticeEntity.getMessage_content());

            }



        };
        mRecyclerview.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View itemView, Object itemBean, int position) {
                NoticeEntity noticeBean = (NoticeEntity) itemBean;
                Bundle b = new Bundle();
                b.putString("url",noticeBean.getMessage_url());
                b.putString("title","公告详情");
                RecommendUiGoto.gotoBrowser(getActivity(),b);
            }
        });


    }

    @Override
    public void initData() {
        sendRequestData();
    }
    @Override
    protected void sendRequestData() {
        reqNotice();
    }

    private void reqNotice() {
        BaseDTO dto = new BaseDTO();
        CommonApiClient.notice(this, dto, new CallBack<NoticeResult>() {
            @Override
            public void onSuccess(NoticeResult result) {
                if (AppConfig.SUCCESS.equals(result.getCode())) {
                    LogUtils.e("公告成功");
                    mErrorLayout.setErrorMessage("暂无公告记录", mErrorLayout.FLAG_NODATA);
                    mErrorLayout.setErrorImag(R.drawable.page_icon_empty, mErrorLayout.FLAG_NODATA);
                    if (null == result.getData().getList()) {
                        mErrorLayout.setErrorType(EmptyLayout.NODATA);
                    } else {
                        mAdapter.removeAll();
                        mAdapter.append(result.getData().getList());
                        refreshComplete();

                    }
                }

            }
        });

    }

    @Override
    public boolean pulltoRefresh() {
        return true;
    }


}
