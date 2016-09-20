package com.macth.match.notice.fragment;

import android.content.Intent;
import android.view.View;

import com.macth.match.AppConfig;
import com.macth.match.R;
import com.macth.match.common.base.BaseListFragment;
import com.macth.match.common.dto.BaseDTO;
import com.macth.match.common.http.CallBack;
import com.macth.match.common.http.CommonApiClient;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.common.widget.EmptyLayout;
import com.macth.match.notice.activity.NoticeDetailActivity;
import com.macth.match.notice.adapter.NoticeAdapter;
import com.macth.match.notice.entity.NoticeEntity;
import com.macth.match.notice.entity.NoticeResult;
import com.qluxstory.ptrrecyclerview.BaseRecyclerAdapter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by John_Libo on 2016/8/22.
 * 公告页面
 */
public class NoticeFragment extends BaseListFragment<NoticeEntity> {

    private NoticeAdapter noticeAdapter;


    @Override
    public BaseRecyclerAdapter<NoticeEntity> createAdapter() {
        noticeAdapter = new NoticeAdapter(getContext());
        return noticeAdapter;
    }

    @Override
    protected String getCacheKeyPrefix() {
        return "NoticesFragment";
    }

    @Override
    public List<NoticeEntity> readList(Serializable seri) {
        return ((NoticeResult) seri).getData().getList();
    }

    @Override
    protected void sendRequestData() {
        BaseDTO dto = new BaseDTO();
        CommonApiClient.notice(this, dto, new CallBack<NoticeResult>() {
            @Override
            public void onSuccess(NoticeResult result) {
                if (AppConfig.SUCCESS.equals(result.getCode())) {
                    LogUtils.e("推荐项目列表成功");
                    mErrorLayout.setErrorMessage("暂无推荐记录", mErrorLayout.FLAG_NODATA);
                    mErrorLayout.setErrorImag(R.drawable.page_icon_empty, mErrorLayout.FLAG_NODATA);
                    if (null == result.getData()) {
                        mErrorLayout.setErrorType(EmptyLayout.NODATA);
                    } else {
                        requestDataSuccess(result);
                        setDataResult(result.getData().getList());

                        //点击每一项的监听
                        noticeAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnRecyclerViewItemClickListener() {
                            @Override
                            public void onItemClick(View itemView, Object itemBean, int position) {
                                LogUtils.d("position====" + position);
                                NoticeEntity noticeBean = (NoticeEntity) itemBean;
                                String url = noticeBean.getMessage_url();
                                LogUtils.d("url====" + url);
                                //跳转到对应的h5页面
                                openH5( url);

                            }
                        });
                    }
                }

            }
        });

    }

    /**
     * 跳转到对应的h5页面
     */
    private void openH5(String url) {

        Intent intent = new Intent(getContext(), NoticeDetailActivity.class);
        intent.putExtra("noticeUrl",url);
        startActivity(intent);


    }

    @Override
    public void initData() {
        mCurrentPage = 1;
        sendRequestData();
    }

    public boolean autoRefreshIn() {
        return true;
    }
}
