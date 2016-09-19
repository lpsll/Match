package com.macth.match.find;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.macth.match.AppConfig;
import com.macth.match.R;
import com.macth.match.common.base.BaseListActivity;
import com.macth.match.common.http.CallBack;
import com.macth.match.common.http.CommonApiClient;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.common.utils.ToastUtils;
import com.macth.match.common.widget.EmptyLayout;
import com.macth.match.find.adapter.Search2Adapter;
import com.macth.match.find.dto.SearchDTO;
import com.macth.match.find.entity.FindEntity;
import com.macth.match.find.entity.FindResult;
import com.qluxstory.ptrrecyclerview.BaseRecyclerAdapter;

import java.io.Serializable;
import java.util.List;

public class Search2Activity extends BaseListActivity<FindEntity> {

    private Search2Adapter search2Adapter;

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
                ToastUtils.showShort(Search2Activity.this, "搜索开始");
                sendRequestData();
            }
        });

    }


    @Override
    public void initData() {
        super.initData();
    }


    @Override
    public BaseRecyclerAdapter<FindEntity> createAdapter() {
        search2Adapter = new Search2Adapter();
        return search2Adapter;
    }

    @Override
    protected String getCacheKeyPrefix() {
        return "Search2Activity";
    }

    @Override
    public List readList(Serializable seri) {
        return ((FindResult) seri).getData().getList();
    }

//    public boolean autoRefreshIn() {
//        return true;
//    }

    @Override
    protected void sendRequestData() {
        String searchKeyWords = et_find_search.getText().toString().trim();
        SearchDTO searchDTO = new SearchDTO();

        searchDTO.setSearch(searchKeyWords);
        searchDTO.setSearch("1");  //page

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
                    if (null == result.getData()) {
                        mErrorLayout.setErrorType(EmptyLayout.NODATA);
                    } else {
                        //获取数据
                        setDataResult(result.getData().getList());
                    }
                }
            }
        });
    }
}
