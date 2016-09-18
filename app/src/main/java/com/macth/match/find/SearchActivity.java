package com.macth.match.find;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.macth.match.AppConfig;
import com.macth.match.R;
import com.macth.match.common.base.BaseTitleActivity;
import com.macth.match.common.http.CallBack;
import com.macth.match.common.http.CommonApiClient;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.common.widget.EmptyLayout;
import com.macth.match.find.adapter.SearchAdapter;
import com.macth.match.find.dto.SearchDTO;
import com.macth.match.find.entity.FindEntity;
import com.macth.match.find.entity.FindResult;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class SearchActivity extends BaseTitleActivity {


    @Bind(R.id.et_find_search)
    EditText etFindSearch;
    @Bind(R.id.tv_search)
    TextView tvSearch;
    @Bind(R.id.base_recyclerview)
    RecyclerView baseRecyclerview;
    @Bind(R.id.error_layout)
    EmptyLayout mErrorLayout;

    private String keyWords;

    //搜索获取的数据
    private List<FindEntity> searchDatas;
    private SearchAdapter searchAdatper;


    @Override
    protected int getContentResId() {
        return R.layout.activity_search;
    }

    @Override
    public void initView() {
        setTitle("搜索");

        //自动获得焦点并弹出软键盘
        etFindSearch.setFocusable(true);
        etFindSearch.setFocusableInTouchMode(true);
        etFindSearch.requestFocus();
//                InputMethodManager imm = (InputMethodManager)et_find_search.getContext().getSystemService(getContext().INPUT_METHOD_SERVICE);
//                imm.toggleSoftInput(0,InputMethodManager.SHOW_FORCED);

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);


    }

    @Override
    public void initData() {

        baseRecyclerview.setLayoutManager(new LinearLayoutManager(this));

    }


    @OnClick(R.id.tv_search)
    public void onClick() {
        keyWords = etFindSearch.getText().toString().trim();
        getSearchContent(keyWords);

    }

    /**
     * 搜索操作
     *
     * @param searchKeyWords
     */
    private void getSearchContent(String searchKeyWords) {
        SearchDTO searchDTO = new SearchDTO();
        searchDTO.setSearch(searchKeyWords);
        searchDTO.setSearch("1");
        CommonApiClient.search(this, searchDTO, new CallBack<FindResult>() {
            @Override
            public void onSuccess(FindResult result) {
                if (AppConfig.SUCCESS.equals(result.getCode())) {
                    LogUtils.e("搜索列表成功");

                    //隐藏键盘
                    etFindSearch.clearFocus();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(etFindSearch.getWindowToken(),0);

                    mErrorLayout.setErrorMessage("暂无搜索记录", mErrorLayout.FLAG_NODATA);
                    mErrorLayout.setErrorImag(R.drawable.page_icon_empty, mErrorLayout.FLAG_NODATA);
                    if (null == result.getData()) {
                        mErrorLayout.setErrorType(EmptyLayout.NODATA);
                    } else {
                        //获取数据
                        if (result.getData().getList() != null && result.getData().getList().size() != 0) {
                            searchDatas = result.getData().getList();

                            searchAdatper = new SearchAdapter(SearchActivity.this, searchDatas);
                            baseRecyclerview.setAdapter(searchAdatper);


                        } else {
                            mErrorLayout.setErrorType(EmptyLayout.NODATA);
                        }
                    }
                }
            }
        });
    }

}
