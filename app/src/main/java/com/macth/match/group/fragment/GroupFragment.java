package com.macth.match.group.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.macth.match.AppConfig;
import com.macth.match.AppContext;
import com.macth.match.R;
import com.macth.match.common.base.BaseApplication;
import com.macth.match.common.base.BasePullScrollViewFragment;
import com.macth.match.common.dto.BaseDTO;
import com.macth.match.common.http.CallBack;
import com.macth.match.common.http.CommonApiClient;
import com.macth.match.common.utils.DialogUtils;
import com.macth.match.common.utils.ImageLoaderUtils;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.common.widget.EmptyLayout;
import com.macth.match.common.widget.FullyLinearLayoutManager;
import com.macth.match.group.GroupUiGoto;
import com.macth.match.group.entity.GroupEntity;
import com.macth.match.group.entity.GroupResult;
import com.macth.match.recommend.RecommendUiGoto;
import com.macth.match.recommend.entity.RecommendEntity;
import com.qluxstory.ptrrecyclerview.BaseRecyclerAdapter;
import com.qluxstory.ptrrecyclerview.BaseRecyclerViewHolder;
import com.qluxstory.ptrrecyclerview.BaseSimpleRecyclerAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

/**
 * 群组列表
 */
public class GroupFragment extends BasePullScrollViewFragment {
    @Bind(R.id.group_list)
    RecyclerView groupList;
    BaseSimpleRecyclerAdapter mAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_group;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        LogUtils.e("rytoken----",""+AppContext.get("rytoken",""));
        groupList.setLayoutManager(new FullyLinearLayoutManager(getActivity()));
        mAdapter=new BaseSimpleRecyclerAdapter<GroupEntity>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.item_group;
            }

            @Override
            public void bindData(BaseRecyclerViewHolder holder, GroupEntity groupEntity, int position) {
                holder.setText(R.id.group_tv, groupEntity.getGroupname());
                ImageView img = holder.getView(R.id.group_img);
                if(TextUtils.isEmpty(groupEntity.getGroupimg())){

                }else {
                    ImageLoaderUtils.displayAvatarImage(groupEntity.getGroupimg(),img);
                }
            }

        };
        groupList.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View itemView, Object itemBean, int position) {
                LogUtils.e("rytoken----",""+AppContext.get("rytoken",""));
                GroupEntity entity = (GroupEntity) itemBean;
                AppContext.set("groupname",entity.getGroupname());
                RongIM.getInstance().startGroupChat(getActivity(),
                        entity.getGroupid(), "群组聊天");
            }
        });

    }

    @Override
    protected void sendRequestData() {
            reqGroup();
    }

    private void reqGroup() {
        BaseDTO dto = new BaseDTO();
        dto.setUserid(AppContext.get("usertoken", ""));
        CommonApiClient.group(getActivity(), dto, new CallBack<GroupResult>() {
            @Override
            public void onSuccess(GroupResult result) {
                if (AppConfig.SUCCESS.equals(result.getCode())) {
                    AppContext.getInstance().setGroupEntityList(result.getData());
                    LogUtils.e("获取用户群成功");
                    mErrorLayout.setErrorMessage("暂无用户群记录", mErrorLayout.FLAG_NODATA);
                    mErrorLayout.setErrorImag(R.drawable.page_icon_empty, mErrorLayout.FLAG_NODATA);
                    if (null == result.getData()) {
                        mErrorLayout.setErrorType(EmptyLayout.NODATA);
                    } else {
                        mAdapter.removeAll();
                        mAdapter.append(result.getData());
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
    public boolean pulltoRefresh() {
        return true;
    }


}
