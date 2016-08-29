package com.macth.match.find.fragment;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.macth.match.AppConfig;
import com.macth.match.R;
import com.macth.match.common.base.BaseListFragment;
import com.macth.match.common.dto.BaseDTO;
import com.macth.match.common.http.CallBack;
import com.macth.match.common.http.CommonApiClient;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.common.widget.EmptyLayout;
import com.macth.match.find.adapter.FindAdapter;
import com.macth.match.find.entity.FindEntity;
import com.macth.match.find.entity.FindResult;
import com.macth.match.find.dto.SearchDTO;
import com.qluxstory.ptrrecyclerview.BaseRecyclerAdapter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by John_Libo on 2016/8/22.
 */
public class FindFragment extends BaseListFragment<FindEntity> {


    private FindAdapter findAdapter;

    private LinearLayout ll_find_search1;
    private Button btn_find;
    private EditText et_find_search;
    private ImageView img_find_search;


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

        BaseDTO dto = new BaseDTO();
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
    public void initData() {



        ll_find_header.setVisibility(View.VISIBLE);
        ll_find_search1 = (LinearLayout) ll_find_header.findViewById(R.id.ll_find_search1);
        btn_find = (Button) ll_find_header.findViewById(R.id.btn_find);
        et_find_search = (EditText) ll_find_header.findViewById(R.id.et_find_search);
        img_find_search = (ImageView) ll_find_header.findViewById(R.id.img_find_search);

        btn_find.setOnClickListener(this);
        img_find_search.setOnClickListener(this);

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
            case  R.id.btn_find:
                btn_find.setVisibility(View.GONE);
                ll_find_search1.setVisibility(View.VISIBLE);
                //自动获得焦点并弹出软键盘
                et_find_search.setFocusable(true);
                et_find_search.setFocusableInTouchMode(true);
                et_find_search.requestFocus();
//                InputMethodManager imm = (InputMethodManager)et_find_search.getContext().getSystemService(getContext().INPUT_METHOD_SERVICE);
//                imm.toggleSoftInput(0,InputMethodManager.SHOW_FORCED);

                InputMethodManager inputMethodManager=(InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);


                break;

            case R.id.img_find_search:
                String searchKeyWords = et_find_search.getText().toString().trim();
                if(TextUtils.isEmpty(searchKeyWords)) {
                    //获取发现列表的数据
                    sendRequestData();
                    //把搜索框复原
                    btn_find.setVisibility(View.VISIBLE);
                    ll_find_search1.setVisibility(View.GONE);
                    //隐藏键盘
                    et_find_search.clearFocus();
                    InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(et_find_search.getWindowToken(),0);

                }else {
                    //搜索操作
                    getSearchContent(searchKeyWords);
                    et_find_search.clearFocus();
                    InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(et_find_search.getWindowToken(),0);

                }


                break;
        }
    }

    /**
     * 搜索操作
     * @param searchKeyWords
     */
    private void getSearchContent(String searchKeyWords) {
        SearchDTO searchDTO = new SearchDTO();
        searchDTO.setSearch(searchKeyWords);
        //page??????????????????????????????
        searchDTO.setSearch("1");
        CommonApiClient.search(this, searchDTO, new CallBack<FindResult>() {
            @Override
            public void onSuccess(FindResult result) {
                if (AppConfig.SUCCESS.equals(result.getCode())) {
                    LogUtils.e("搜索列表成功");
                    mErrorLayout.setErrorMessage("暂无搜索记录", mErrorLayout.FLAG_NODATA);
                    mErrorLayout.setErrorImag(R.drawable.page_icon_empty, mErrorLayout.FLAG_NODATA);
                    if (null == result.getData()) {
                        mErrorLayout.setErrorType(EmptyLayout.NODATA);
                    } else {
                        //获取数据
                        if (result.getData().getList() != null && result.getData().getList().size() != 0) {
                            setDataResult(result.getData().getList());
                        } else {
                            mErrorLayout.setErrorType(EmptyLayout.NODATA);
                        }
                    }
                }
            }
        });


    }
}
