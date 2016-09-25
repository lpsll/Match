package com.macth.match.group.fragment;

import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.macth.match.AppConfig;
import com.macth.match.AppContext;
import com.macth.match.R;
import com.macth.match.common.base.BaseFragment;
import com.macth.match.common.base.BasePullScrollViewFragment;
import com.macth.match.common.base.SimplePage;
import com.macth.match.common.http.CallBack;
import com.macth.match.common.http.CommonApiClient;
import com.macth.match.common.utils.DialogUtils;
import com.macth.match.common.utils.ImageLoaderUtils;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.common.utils.UIHelper;
import com.macth.match.common.widget.EmptyLayout;
import com.macth.match.common.widget.FullyLinearLayoutManager;
import com.macth.match.group.dto.MembersDTO;
import com.macth.match.group.entity.GroupEntity;
import com.macth.match.group.entity.MembersEntity;
import com.macth.match.group.entity.MembersResult;
import com.qluxstory.ptrrecyclerview.BaseRecyclerAdapter;
import com.qluxstory.ptrrecyclerview.BaseRecyclerViewHolder;
import com.qluxstory.ptrrecyclerview.BaseSimpleRecyclerAdapter;

import java.util.List;

import butterknife.Bind;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;

/**
 * Created by John_Libo on 2016/9/26.
 */
public class GroupMembersFragment extends BasePullScrollViewFragment {
    boolean login;
    @Bind(R.id.group_list)
    RecyclerView groupList;
    BaseSimpleRecyclerAdapter mAdapter;

    List<MembersEntity> data;
    MembersEntity entity;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_group;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        login = AppContext.get("IS_LOGIN", false);
        LogUtils.e("rytoken----",""+AppContext.get("rytoken",""));
        groupList.setLayoutManager(new FullyLinearLayoutManager(getActivity()));
        mAdapter=new BaseSimpleRecyclerAdapter<MembersEntity>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.item_group;
            }

            @Override
            public void bindData(BaseRecyclerViewHolder holder, MembersEntity membersEntity, int position) {
                holder.setText(R.id.group_tv, membersEntity.getName());
                ImageView img = holder.getView(R.id.group_img);
                if(TextUtils.isEmpty(membersEntity.getImg())){

                }else {
                    ImageLoaderUtils.displayAvatarImage(membersEntity.getImg(),img);
                }
            }


        };
        groupList.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View itemView, Object itemBean, int position) {
                entity = (MembersEntity) itemBean;
                RongIM.getInstance().startPrivateChat(getActivity(), entity.getId(), "标题");
            }
        });

    }
    @Override
    public void initData() {
        sendRequestData();
    }

    @Override
    protected void sendRequestData() {
        reqMembers();

    }

    @Override
    public boolean pulltoRefresh() {
        return true;
    }

    private void reqMembers() {
        MembersDTO dto = new MembersDTO();
        dto.setName(AppContext.get("groupname", "")+"群");
        CommonApiClient.members(getActivity(), dto, new CallBack<MembersResult>() {
            @Override
            public void onSuccess(MembersResult result) {
                if (AppConfig.SUCCESS.equals(result.getCode())) {
                    LogUtils.e("获取群成员成功");
                    data = result.getData();
                    mErrorLayout.setErrorMessage("暂无群成员记录", mErrorLayout.FLAG_NODATA);
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
}
