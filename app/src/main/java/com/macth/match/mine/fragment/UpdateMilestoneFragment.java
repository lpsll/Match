package com.macth.match.mine.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.macth.match.AppConfig;
import com.macth.match.AppContext;
import com.macth.match.R;
import com.macth.match.common.base.BasePullScrollViewFragment;
import com.macth.match.common.http.CallBack;
import com.macth.match.common.http.CommonApiClient;
import com.macth.match.common.utils.DialogUtils;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.common.utils.ToastUtils;
import com.macth.match.common.widget.EmptyLayout;
import com.macth.match.common.widget.FullyLinearLayoutManager;
import com.macth.match.mine.dto.UpdataDTO;
import com.macth.match.mine.entity.UpdateResult;
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
 * 更新里程碑
 */
public class UpdateMilestoneFragment extends BasePullScrollViewFragment {
    @Bind(R.id.updata_list)
    RecyclerView mUpdataList;
    BaseSimpleRecyclerAdapter mAdapter;
    private String mProjectID, mDID;
    List<Boolean> mList = new ArrayList<>();
    boolean isFrist;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_updata;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        isFrist = true;
        Bundle bundle = getArguments();
        mProjectID = bundle.getString("pid");
        mDID = bundle.getString("DId");
        mUpdataList.setLayoutManager(new FullyLinearLayoutManager(getActivity()));
        mAdapter = new BaseSimpleRecyclerAdapter<MilDetailsEntity>() {
            TextView tv02;
            List<String> list = new ArrayList<>();

            @Override
            public int getItemViewLayoutId() {
                return R.layout.item_updata;
            }

            @Override
            public void bindData(BaseRecyclerViewHolder holder, MilDetailsEntity milDetailsEntity, final int position) {
                tv02 = holder.getView(R.id.md_tv_02);
                list.add(position, milDetailsEntity.getMid());
                if (isFrist) {
                    if (position == 0) {
                        mList.add(position, true);
                    } else {
                        mList.add(position, false);
                    }

                }

                if (mList.get(position)) {
                    tv02.setEnabled(true);
                } else {
                    tv02.setEnabled(false);
                }
                LogUtils.e("mList----", "" + mList);

                holder.setText(R.id.md_tv_01, milDetailsEntity.getName());
                LogUtils.e("milDetailsEntity.getFinsh()----", "" + milDetailsEntity.getFinsh());
                if (milDetailsEntity.getFinsh().equals("1")) {
                    tv02.setText("未完成");
                } else if (milDetailsEntity.getFinsh().equals("2")) {
                    tv02.setText("已完成");
                }
                tv02.setOnClickListener(new View.OnClickListener() {
                    TextView tv = tv02;
                    @Override
                    public void onClick(View v) {
                        tv02 = (TextView) v;
                        isFrist = false;
                        if (position + 1 < mList.size()) {
                            mList.set(position + 1, true);
                        }

                        if (tv02.getText().toString().equals("已完成")) {
                            DialogUtils.showPrompt(getActivity(), "提示", "里程碑更新已完成！", "知道了");
                        } else {
                            new AlertDialog.Builder(getActivity()).setTitle("温馨提示").setMessage("确定要更新里程碑吗?").setNegativeButton("取消", null).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    UpdataDTO dto = new UpdataDTO();
                                    dto.setCooperativeID(AppContext.get("cooperativeid", ""));
                                    dto.setProjectID(mProjectID);
                                    dto.setMilepostID(list.get(position));
                                    dto.setUserID(AppContext.get("usertoken", ""));
                                    CommonApiClient.milestoneStatus(getActivity(), dto, new CallBack<MilDetailsResult>() {
                                        @Override
                                        public void onSuccess(MilDetailsResult result) {
                                            if (AppConfig.SUCCESS.equals(result.getCode())) {
                                                LogUtils.e("更新里程碑状态成功");
                                                ToastUtils.showShort(getActivity(),"更新里程碑状态成功laaaaaaaa");
                                                tv.setText("已完成");
                                            }
                                        }
                                    });
                                }
                            }).show();
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                });

//                tv03.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                    }
//                });

//                mTvUp.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        UpdataDTO dto = new UpdataDTO();
//                        dto.setCooperativeID(AppContext.get("cooperativeid", ""));
//                        dto.setProjectID(mProjectID);
//                        dto.setMilepostID(list.get(position));
//                        dto.setUserID(AppContext.get("usertoken",""));
//                        CommonApiClient.download(getActivity(), dto, new CallBack<MilDetailsResult>() {
//                            @Override
//                            public void onSuccess(MilDetailsResult result) {
//                                if (AppConfig.SUCCESS.equals(result.getCode())) {
//                                    LogUtils.e("里程碑上传文件成功");
//
//                                }
//
//                            }
//                        });
//
//
//
//                    }
//                });
            }


        };
        mUpdataList.setAdapter(mAdapter);
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
        dto.setCooperativeID(mDID);
        dto.setProjectID(mProjectID);
        CommonApiClient.milestoneDetails(getActivity(), dto, new CallBack<MilDetailsResult>() {
            @Override
            public void onSuccess(MilDetailsResult result) {
                if (AppConfig.SUCCESS.equals(result.getCode())) {
                    LogUtils.e("里程碑详情成功");
                    mErrorLayout.setErrorMessage("暂无里程碑记录", mErrorLayout.FLAG_NODATA);
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

    private void reqUpdata() {
        UpdataDTO dto = new UpdataDTO();
        dto.setProjectID(mProjectID);
        dto.setUserID(AppContext.get("usertoken", ""));
        CommonApiClient.see(getActivity(), dto, new CallBack<UpdateResult>() {
            @Override
            public void onSuccess(UpdateResult result) {
                if (AppConfig.SUCCESS.equals(result.getCode())) {
                    LogUtils.e("查看里程碑成功");
                    mErrorLayout.setErrorMessage("暂无里程碑记录", mErrorLayout.FLAG_NODATA);
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
