package com.macth.match.recommend.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.macth.match.AppConfig;
import com.macth.match.AppContext;
import com.macth.match.R;
import com.macth.match.common.base.BasePullScrollViewFragment;
import com.macth.match.common.http.CallBack;
import com.macth.match.common.http.CommonApiClient;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.common.widget.EmptyLayout;
import com.macth.match.common.widget.FullyLinearLayoutManager;
import com.macth.match.recommend.dto.MinestoneDetailsDTO;
import com.macth.match.recommend.entity.AttachmentsDTO;
import com.macth.match.recommend.entity.MilDetailsEntity;
import com.macth.match.recommend.entity.MilDetailsResult;
import com.qluxstory.ptrrecyclerview.BaseRecyclerAdapter;
import com.qluxstory.ptrrecyclerview.BaseRecyclerViewHolder;
import com.qluxstory.ptrrecyclerview.BaseSimpleRecyclerAdapter;

import butterknife.Bind;

/**
 * 查看附件
 */
public class AttachmentsFragment extends BasePullScrollViewFragment {
    @Bind(R.id.attachments_list)
    RecyclerView mAttachmentsList;
    BaseSimpleRecyclerAdapter mAdapter;
    private String mMfileid;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_attachments;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        Bundle bundle = getArguments();
        mMfileid = bundle.getString("mfileid");
        mAttachmentsList.setLayoutManager(new FullyLinearLayoutManager(getActivity()));
        mAdapter=new BaseSimpleRecyclerAdapter<MilDetailsEntity>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.item_attachments;
            }

            @Override
            public void bindData(BaseRecyclerViewHolder holder, MilDetailsEntity milDetailsEntity, int position) {
                holder.setText(R.id.md_tv1_01, milDetailsEntity.getName());
                if(milDetailsEntity.getFinsh().equals("1")){
                    holder.setText(R.id.md_tv1_02, "未完成");
                }else if(milDetailsEntity.getFinsh().equals("2")){
                    holder.setText(R.id.md_tv1_02, "已完成");
                }
            }


        };
        mAttachmentsList.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View itemView, Object itemBean, int position) {

            }
        });

    }

    @Override
    public void initData() {
        reqAttachments();
    }

    private void reqAttachments() {
        AttachmentsDTO dto = new AttachmentsDTO();
        dto.setMfileid(mMfileid);
        CommonApiClient.attachments(getActivity(), dto, new CallBack<MilDetailsResult>() {
            @Override
            public void onSuccess(MilDetailsResult result) {
                if (AppConfig.SUCCESS.equals(result.getCode())) {
                    LogUtils.e("查看附件成功");
                    mErrorLayout.setErrorMessage("暂无附件记录", mErrorLayout.FLAG_NODATA);
                    mErrorLayout.setErrorImag(R.drawable.page_icon_empty, mErrorLayout.FLAG_NODATA);
                    if (null == result.getData()) {
                        mErrorLayout.setErrorType(EmptyLayout.NODATA);
                    } else {
                        mAdapter.removeAll();
                        mAdapter.append(result.getData());
//                        refreshComplete();
                    }
                }

            }
        });
    }
}
