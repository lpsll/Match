package com.macth.match.recommend.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.macth.match.AppConfig;
import com.macth.match.AppContext;
import com.macth.match.R;
import com.macth.match.common.base.BasePullScrollViewFragment;
import com.macth.match.common.base.SimplePage;
import com.macth.match.common.http.CallBack;
import com.macth.match.common.http.CommonApiClient;
import com.macth.match.common.utils.DialogUtils;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.common.utils.UIHelper;
import com.macth.match.common.widget.EmptyLayout;
import com.macth.match.common.widget.FullyLinearLayoutManager;
import com.macth.match.group.entity.GroupEntity;
import com.macth.match.recommend.RecommendUiGoto;
import com.macth.match.recommend.dto.MinestoneDetailsDTO;
import com.macth.match.recommend.entity.MilDetailsEntity;
import com.macth.match.recommend.entity.MilDetailsResult;
import com.qluxstory.ptrrecyclerview.BaseRecyclerAdapter;
import com.qluxstory.ptrrecyclerview.BaseRecyclerViewHolder;
import com.qluxstory.ptrrecyclerview.BaseSimpleRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 里程碑详情
 */
public class MilestoneDetailsFragment extends BasePullScrollViewFragment {
    @Bind(R.id.miles_list)
    RecyclerView mMilesList;
    BaseSimpleRecyclerAdapter mAdapter;
    private String mProjectID,MDid;
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_milestonedetails;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        Bundle bundle = getArguments();
        mProjectID = bundle.getString("projectID");
        MDid = bundle.getString("DId");
        mMilesList.setLayoutManager(new FullyLinearLayoutManager(getActivity()));
        mAdapter=new BaseSimpleRecyclerAdapter<MilDetailsEntity>() {
            TextView tv;
            List<String> list =  new ArrayList<>();
            @Override
            public int getItemViewLayoutId() {
                return R.layout.item_milestonedetails;
            }

            @Override
            public void bindData(BaseRecyclerViewHolder holder, MilDetailsEntity milDetailsEntity, final int position) {
                tv = holder.getView(R.id.md_tv1_03);
                list.add(position,milDetailsEntity.getMfileid());
                holder.setText(R.id.md_tv1_01, milDetailsEntity.getName());
                if(milDetailsEntity.getFinsh().equals("1")){
                    holder.setText(R.id.md_tv1_02, "未完成");
                }else if(milDetailsEntity.getFinsh().equals("2")){
                    holder.setText(R.id.md_tv1_02, "已完成");
                }
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(list.get(position).equals("0")){
                            DialogUtils.showPrompt(getActivity(), "提示","暂无附件", "知道了");
                        }else {
                            Bundle bundle = new Bundle();
                            bundle.putString("mfileid",list.get(position));
                            LogUtils.e("mfileid---",""+list.get(position));
                            UIHelper.showBundleFragment(getActivity(), SimplePage.ATTACHMENTS,bundle);//查看附件
                        }


                    }
                });
            }


        };
        mMilesList.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View itemView, Object itemBean, int position) {

            }
        });

    }

    @Override
    public void initData() {
        reqMilDetails();
    }

    private void reqMilDetails() {
        MinestoneDetailsDTO dto = new MinestoneDetailsDTO();
        dto.setCooperativeID(MDid);
        dto.setProjectID(mProjectID);
        CommonApiClient.milestoneDetails(getActivity(), dto, new CallBack<MilDetailsResult>() {
            @Override
            public void onSuccess(MilDetailsResult result) {
                if (AppConfig.SUCCESS.equals(result.getCode())) {
                    LogUtils.e("里程碑详情成功");
                    mErrorLayout.setErrorMessage("暂无里程碑详情记录", mErrorLayout.FLAG_NODATA);
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
