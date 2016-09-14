package com.macth.match.recommend.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.macth.match.AppConfig;
import com.macth.match.R;
import com.macth.match.common.base.BasePullScrollViewFragment;
import com.macth.match.common.dto.BaseDTO;
import com.macth.match.common.http.CallBack;
import com.macth.match.common.http.CommonApiClient;
import com.macth.match.common.utils.ImageLoaderUtils;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.common.widget.EmptyLayout;
import com.macth.match.common.widget.FullyLinearLayoutManager;
import com.macth.match.recommend.RecommendUiGoto;
import com.macth.match.recommend.entity.RecommendEntity;
import com.macth.match.recommend.entity.RecommendResult;
import com.qluxstory.ptrrecyclerview.BaseRecyclerAdapter;
import com.qluxstory.ptrrecyclerview.BaseRecyclerViewHolder;
import com.qluxstory.ptrrecyclerview.BaseSimpleRecyclerAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 推荐页
 */
public class RecommendFragment extends BasePullScrollViewFragment {
    @Bind(R.id.recommend_list)
    RecyclerView mRecommendList;
    BaseSimpleRecyclerAdapter mAdapter;
    TextView mTv,mTvCompany;

    @Override
    public void initView(View view) {
        super.initView(view);

        mRecommendList.setLayoutManager(new FullyLinearLayoutManager(getActivity()));
        mAdapter=new BaseSimpleRecyclerAdapter<RecommendEntity>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.item_recommend;
            }

            @Override
            public void bindData(BaseRecyclerViewHolder holder, RecommendEntity recommendEntity, int position) {
                mTv = holder.getView(R.id.tv);
                mTvCompany = holder.getView(R.id.rc_tv_company);
                mTv.getPaint().setFakeBoldText(true);//加粗
                mTvCompany.getPaint().setFakeBoldText(true);//加粗
                mTvCompany.setText(recommendEntity.getCompanyname());
                holder.setText(R.id.rc_tv_money,recommendEntity.getPrice());
                holder.setText(R.id.rc_tv_term,recommendEntity.getProject_termunit()+"年 "+recommendEntity.getProject_type());
                holder.setText(R.id.rc_tv_data,recommendEntity.getCtime());
                ImageView mImg =holder.getView( R.id.rc_img);
                ImageLoaderUtils.displayImage(recommendEntity.getImage(), mImg);
            }


        };
        mRecommendList.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View itemView, Object itemBean, int position) {
                RecommendEntity entity = (RecommendEntity) itemBean;
                Bundle b = new Bundle();
                b.putString("pid", entity.getPid());
                RecommendUiGoto.gotoProject(getActivity(), b);
            }
        });

    }

    private void reqRecommend() {
        BaseDTO dto = new BaseDTO();
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
                        mAdapter.removeAll();
                        mAdapter.append(result.getData().getList());
                        refreshComplete();
                    }
                }

            }
        });

    }

    @Override
    public void initData() {
        sendRequestData();

    }
    @Override
    protected void sendRequestData() {
        reqRecommend();
    }

    @Override
    public boolean pulltoRefresh() {
        return true;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_recommend;
    }


}
