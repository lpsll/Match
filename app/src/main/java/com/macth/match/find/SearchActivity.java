package com.macth.match.find;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.macth.match.AppConfig;
import com.macth.match.AppContext;
import com.macth.match.R;
import com.macth.match.common.base.BaseListActivity;
import com.macth.match.common.http.CallBack;
import com.macth.match.common.http.CommonApiClient;
import com.macth.match.common.utils.DialogUtils;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.common.widget.EmptyLayout;
import com.macth.match.find.adapter.SearchAdapter;
import com.macth.match.find.dto.SearchDTO;
import com.macth.match.find.entity.FindEntity;
import com.macth.match.find.entity.FindResult;
import com.macth.match.recommend.RecommendUiGoto;
import com.qluxstory.ptrrecyclerview.BaseRecyclerAdapter;

import java.io.Serializable;
import java.util.List;

public class SearchActivity extends BaseListActivity<FindEntity> {

    private SearchAdapter searchAdapter;

    @Override
    public void initView() {
        super.initView();
        rl_search.setVisibility(View.VISIBLE);
        setTitleText("搜索");
        //自动获得焦点并弹出软键盘
        et_find_search.setFocusable(true);
        et_find_search.setFocusableInTouchMode(true);
        et_find_search.requestFocus();
//                InputMethodManager imm = (InputMethodManager)et_find_search.getContext().getSystemService(getContext().INPUT_METHOD_SERVICE);
//                imm.toggleSoftInput(0,InputMethodManager.SHOW_FORCED);

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mErrorLayout.setVisibility(View.VISIBLE);
                String searchKeyWords = et_find_search.getText().toString().trim();
                if (TextUtils.isEmpty(searchKeyWords)) {
                    DialogUtils.showPrompt(SearchActivity.this, "提示", "请输入搜索内容", "好的");
                    return;
                }
                showDialogLoading();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mCurrentPage = 1;
                        sendRequestData();
                    }
                },1000);
            }
        });

    }

    public boolean autoRefreshIn() {
        return true;
    }

    @Override
    public void initData() {

    }


    @Override
    public BaseRecyclerAdapter<FindEntity> createAdapter() {
        searchAdapter = new SearchAdapter();
        return searchAdapter;
    }

    @Override
    protected String getCacheKeyPrefix() {
        return "SearchActivity";
    }

    @Override
    public List readList(Serializable seri) {
        return ((FindResult) seri).getData().getList();
    }


    @Override
    protected void sendRequestData() {
        String searchKeyWords = et_find_search.getText().toString().trim();
        SearchDTO searchDTO = new SearchDTO();
        //防止一开始就报数据解析错误
        if(TextUtils.isEmpty(searchKeyWords)) {
            searchDTO.setSearch("#%^&*");
        }else {
            searchDTO.setSearch(searchKeyWords);
        }
//        searchDTO.setSearch(searchKeyWords);
        searchDTO.setPage(String.valueOf(mCurrentPage));
        CommonApiClient.search(this, searchDTO, new CallBack<FindResult>() {
            @Override
            public void onSuccess(FindResult result) {
                if (AppConfig.SUCCESS.equals(result.getCode())) {
                    LogUtils.e("搜索列表成功");

                    //隐藏键盘
                    et_find_search.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(et_find_search.getWindowToken(), 0);

                    mErrorLayout.setErrorMessage("暂无搜索记录", mErrorLayout.FLAG_NODATA);
                    mErrorLayout.setErrorImag(R.drawable.page_icon_empty, mErrorLayout.FLAG_NODATA);
                    if (null == result.getData() || "0".equals(result.getData().getCount())) {
                        mErrorLayout.setErrorType(EmptyLayout.NODATA);
                        searchAdapter.removeAll();
                    } else {
                        //获取数据
                        requestDataSuccess(result);
                        setDataResult(result.getData().getList());
                        //刷新数据
                        searchAdapter.notifyDataSetChanged();
                    }
                    hideDialogLoading();
                }
            }
        });
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
        b.putString("title","项目详情");
        b.putString("url", AppConfig.DETAILS_H5_URL+ AppContext.get("usertoken","")+"&flag="+flag+"&pid="+entity.getPid());
        LogUtils.e("url---",""+AppConfig.DETAILS_H5_URL+ AppContext.get("usertoken","")+"&flag="+flag+"&pid="+entity.getPid());
        RecommendUiGoto.gotoPdb(this, b);
    }
}
