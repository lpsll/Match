package com.macth.match.find.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.macth.match.AppConfig;
import com.macth.match.AppContext;
import com.macth.match.R;
import com.macth.match.common.base.BaseListFragment;
import com.macth.match.common.dto.BaseDTO;
import com.macth.match.common.http.CallBack;
import com.macth.match.common.http.CommonApiClient;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.common.widget.EmptyLayout;
import com.macth.match.find.SearchActivity;
import com.macth.match.find.adapter.FindAdapter;
import com.macth.match.find.entity.FindEntity;
import com.macth.match.find.entity.FindResult;
import com.macth.match.recommend.RecommendUiGoto;
import com.qluxstory.ptrrecyclerview.BaseRecyclerAdapter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by John_Libo on 2016/8/22.
 */
public class FindFragment extends BaseListFragment<FindEntity> {


    private FindAdapter findAdapter;



    @Override
    public BaseRecyclerAdapter<FindEntity> createAdapter() {
        findAdapter = new FindAdapter();
        return findAdapter;
    }

    @Override
    protected String getCacheKeyPrefix() {
        return "FindFragment";
    }

    @Override
    public List<FindEntity> readList(Serializable seri) {
        return ((FindResult) seri).getData().getList();
    }


    protected void sendRequestData() {
        LogUtils.e("sendRequestData---","sendRequestData");
        BaseDTO dto = new BaseDTO();
        dto.setPage(String.valueOf(mCurrentPage));
        CommonApiClient.find(this, dto, new CallBack<FindResult>() {
            @Override
            public void onSuccess(FindResult result) {
                if (AppConfig.SUCCESS.equals(result.getCode())) {
                    LogUtils.e("发现列表成功");
                    mErrorLayout.setErrorMessage("暂无发现记录", mErrorLayout.FLAG_NODATA);
                    mErrorLayout.setErrorImag(R.drawable.page_icon_empty, mErrorLayout.FLAG_NODATA);
                    if (null == result.getData()) {
                        mErrorLayout.setErrorType(EmptyLayout.NODATA);
                    } else {
                        //获取数据
                        if (result.getData().getList() != null && result.getData().getList().size() != 0) {
                            requestDataSuccess(result);
                            setDataResult(result.getData().getList());
                        } else {
                            mErrorLayout.setErrorType(EmptyLayout.NODATA);
                        }
                    }
                }
            }
        });
    }


    @Override
    public void initView(View view) {
        super.initView(view);
        ll_find_header.setVisibility(View.VISIBLE);
        ll_find_header.setOnClickListener(this);
    }

    @Override
    public void initData() {
        LogUtils.e("initData---","initData");
        mCurrentPage = 1;
        sendRequestData();
    }

    public boolean autoRefreshIn() {
        return true;
    }


    @Override
    protected void retry() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case  R.id.ll_find_header:
//                btn_find.setVisibility(View.GONE);
//                ll_find_search1.setVisibility(View.VISIBLE);
//                //自动获得焦点并弹出软键盘
//                et_find_search.setFocusable(true);
//                et_find_search.setFocusableInTouchMode(true);
//                et_find_search.requestFocus();
////                InputMethodManager imm = (InputMethodManager)et_find_search.getContext().getSystemService(getContext().INPUT_METHOD_SERVICE);
////                imm.toggleSoftInput(0,InputMethodManager.SHOW_FORCED);
//
//                InputMethodManager inputMethodManager=(InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

                    //跳转到搜索页面
//                startActivity(new Intent(getContext(), SearchActivity.class));
                startActivity(new Intent(getContext(), SearchActivity.class));

                break;

//            case R.id.img_find_search:
//                String searchKeyWords = et_find_search.getText().toString().trim();
//                if(TextUtils.isEmpty(searchKeyWords)) {
//                    //获取发现列表的数据
//                    sendRequestData();
//                    //把搜索框复原
//                    btn_find.setVisibility(View.VISIBLE);
//                    ll_find_search1.setVisibility(View.GONE);
//                    //隐藏键盘
//                    et_find_search.clearFocus();
//                    InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(et_find_search.getWindowToken(),0);
//
//                }else {
//                    //搜索操作
//                    getSearchContent(searchKeyWords);
//                    et_find_search.clearFocus();
//                    InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(et_find_search.getWindowToken(),0);
//
//                }
//
//
//                break;
        }
    }

    private String flag;
    @Override
    public void onItemClick(View itemView, Object itemBean, int position) {
        super.onItemClick(itemView, itemBean, position);
//        FindEntity entity = (FindEntity) itemBean;
//        Bundle b = new Bundle();
//        b.putString("pid", entity.getPid());
//        RecommendUiGoto.gotoProject(getActivity(), b);

        FindEntity entity = (FindEntity) itemBean;
        Bundle b = new Bundle();
        if(AppContext.get("useridentity","").equals("内部用户")){
            flag ="1";
        }else {
            flag ="2";
        }
        b.putString("isflag","0");
        b.putString("title","项目详情");
        b.putString("url", AppConfig.DETAILS_H5_URL+ "userid="+ AppContext.get("usertoken","")+"&flag="+flag+"&pid="+entity.getPid());
        b.putString("pid", entity.getPid());
        LogUtils.e("url---",""+AppConfig.DETAILS_H5_URL+ AppContext.get("usertoken","")+"&flag="+flag+"&pid="+entity.getPid());
        RecommendUiGoto.gotoPdb(getActivity(), b);
    }
}
