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
 * 群组
 */
public class GroupFragment extends BasePullScrollViewFragment {
    boolean login;
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
        login = AppContext.get("IS_LOGIN", false);
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
                LogUtils.e("onItemClick----","onItemClick");
                GroupEntity entity = (GroupEntity) itemBean;
                AppContext.set("groupname",entity.getGroupname());
                connect(entity.getGroupid());
            }
        });

    }

    @Override
    protected void sendRequestData() {
        if(login){
            reqGroup();
        }
        else {
            DialogUtils.confirm(getActivity(), "您尚未登录，是否去登录？", listener);
        }

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


    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            GroupUiGoto.gotoLogin(getActivity());
        }
    };

    @Override
    public boolean pulltoRefresh() {
        return true;
    }



    /**
     * 建立与融云服务器的连接
     *
     * @param id
     */
    private void connect(final String id) {


        if (getActivity().getApplicationInfo().packageName.equals(BaseApplication.getCurProcessName(getActivity().getApplicationContext()))) {

            /**
             * IMKit SDK调用第二步,建立与服务器的连接
             */
            RongIM.connect(AppContext.get("rytoken",""), new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
                 */
                @Override
                public void onTokenIncorrect() {
                    LogUtils.e("onTokenIncorrect", "------onTokenIncorrect");
                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token
                 */
                @Override
                public void onSuccess(String userid) {
                    LogUtils.e("--onSuccess\"", "" + userid);
                    /**
                     *启动群组聊天界面。
                     *@param context 应用上下文。
                     *@param targetId Id。
                     *@param title 标题。
                     * */

                    RongIM.getInstance().startGroupChat(getActivity(),
                            id, "群组聊天");//生产环境


                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    LogUtils.e("--onError", "" + errorCode);
                }
            });
        }
    }

    private void service() {
        if (getActivity().getApplicationInfo().packageName
                .equals(BaseApplication.getCurProcessName(getActivity().getApplicationContext()))) {
                        /*IMKit SDK调用第二步, 建立与服务器的连接*/
            LogUtils.e("mRongyunToken",""+AppContext.get("mRongyunToken", ""));
            RongIM.connect(AppContext.get("mRongyunToken", ""), new RongIMClient.ConnectCallback() {
                /*  *
                  *
                  Token 错误
                  ，
                  在线上环境下主要是因为 Token
                  已经过期，
                  您需要向 App
                  Server 重新请求一个新的
                  Token*/
                @Override
                public void onTokenIncorrect() {
                    LogUtils.e("", "--onTokenIncorrect");
                }

                /**
                 *连接融云成功
                 *
                 @param
                 userid 当前
                 token*/
                @Override
                public void onSuccess(String userid) {
                    LogUtils.e("--onSuccess", "--onSuccess" + userid);
                    /**
                     *启动聊天界面。
                     *
                     *@param context 应用上下文。
                     *@param conversationType 开启会话类型。
                     *@param targetId  Id。
                     *@param title 标题。*/
                    //                    RongIM.getInstance().startConversation(ProductBrowserActivity.this,
//                            io.rong.imlib.model.Conversation.ConversationType.APP_PUBLIC_SERVICE,
//                            "KEFU146286268172386", "客服");//开发环境

                    RongIM.getInstance().startConversation(getActivity(),
                            io.rong.imlib.model.Conversation.ConversationType.APP_PUBLIC_SERVICE,
                            "m7ua80gbuukrm", "会话");//生产环境
                }

                /*  *
                  *连接融云失败
                  @param
                  errorCode 错误码
                  可到官网 查看错误码对应的注释*/
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    LogUtils.e("--onError", "--onError" + errorCode);
                }
            });
        }


    }



}
