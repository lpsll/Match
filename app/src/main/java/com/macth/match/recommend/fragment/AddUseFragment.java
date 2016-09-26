package com.macth.match.recommend.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.macth.match.AppConfig;
import com.macth.match.R;
import com.macth.match.common.base.BasePullScrollViewFragment;
import com.macth.match.common.http.CallBack;
import com.macth.match.common.http.CommonApiClient;
import com.macth.match.common.utils.DialogUtils;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.common.widget.FullyLinearLayoutManager;
import com.macth.match.recommend.RecommendUiGoto;
import com.macth.match.recommend.dto.AddUseDTO;
import com.macth.match.recommend.dto.DeleteListDTO;
import com.macth.match.recommend.entity.AddUseEntity;
import com.macth.match.recommend.entity.AddUseEvent;
import com.macth.match.recommend.entity.AddUseResult;
import com.qluxstory.ptrrecyclerview.BaseRecyclerViewHolder;
import com.qluxstory.ptrrecyclerview.BaseSimpleRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 增加资金用途
 */
public class AddUseFragment extends BasePullScrollViewFragment {
    @Bind(R.id.adduse_list)
    RecyclerView mAdduseList;
    @Bind(R.id.tv_btn)
    TextView mBtn;
    BaseSimpleRecyclerAdapter mAdapter;
    private String mId,flag;
    List<String> list = new ArrayList<>();

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_adduse;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        Bundle bundle = getArguments();
        mId = bundle.getString("projectNo");
        flag = bundle.getString("flag");
        mAdduseList.setLayoutManager(new FullyLinearLayoutManager(getActivity()));
        mAdapter=new BaseSimpleRecyclerAdapter<AddUseEntity>() {
            TextView tv1;
            @Override
            public int getItemViewLayoutId() {
                return R.layout.item_adduse;
            }

            @Override
            public void bindData(BaseRecyclerViewHolder holder, AddUseEntity adddUseEntity, final int position) {
                list.add(position,adddUseEntity.getFundsid());
                holder.setText(R.id.item_tv,"添加资金用途 "+(position+1));

                TextView tv  = holder.getView(R.id.tv_xc);
                tv1  = holder.getView(R.id.tv_xg);
                if(flag.equals("1")){
                    tv1.setVisibility(View.GONE);
                }else {
                    tv1.setVisibility(View.VISIBLE);
                }
                tv1.setOnClickListener(new View.OnClickListener() {
                    TextView tv = tv1;
                    @Override
                    public void onClick(View v) {
                        Bundle b = new Bundle();
                        b.putString("fundsid", list.get(position));
                        RecommendUiGoto.mdCapital(getActivity(),b);//修改资金用途
                    }
                });

                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogUtils.confirm(getActivity(), "是否删除资金用途？", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DeleteListDTO dto = new DeleteListDTO();
                                dto.setFundsid(list.get(position));
                                CommonApiClient.deleteList(getActivity(), dto, new CallBack<AddUseResult>() {
                                    @Override
                                    public void onSuccess(AddUseResult result) {
                                        if (AppConfig.SUCCESS.equals(result.getCode())) {
                                            LogUtils.e("删除资金用途成功");
                                            initData();

                                        }

                                    }
                                });
                            }
                        });

                    }
                });

            }


        };
        mAdduseList.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseSimpleRecyclerAdapter.OnRecyclerViewItemClickListener(){

            @Override
            public void onItemClick(View itemView, Object itemBean, int position) {
                AddUseEntity entity = (AddUseEntity) itemBean;
                Bundle b = new Bundle();
                b.putString("fundid",entity.getFundsid());
                RecommendUiGoto.gotoDetailsFunds(getActivity(),b);//资金用途详情

            }
        });

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("pid", mId);
                RecommendUiGoto.increase(getActivity(),b);//添加资金用途
            }
        });




    }

    @Override
    public void initData() {
        AddUseDTO dto = new AddUseDTO();
        dto.setProjectno(mId);
        CommonApiClient.useList(getActivity(), dto, new CallBack<AddUseResult>() {
            @Override
            public void onSuccess(AddUseResult result) {
                if (AppConfig.SUCCESS.equals(result.getCode())) {
                    LogUtils.e("获取项目资金用途列表成功");
                    if (null == result.getData()) {
                        return;
                    } else {
                        mAdapter.removeAll();
                        mAdapter.append(result.getData());
//                        refreshComplete();
                    }

                }

            }
        });
    }

    public void onEventMainThread(AddUseEvent event) {
        String msg = event.getMsg();
        LogUtils.e("msg---", "" + msg);
        if (TextUtils.isEmpty(msg)) {

        } else {
            initData();
        }
    }
}
