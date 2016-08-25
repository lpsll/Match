package com.macth.match.find.fragment;

import android.view.View;

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
        findAdapter = new FindAdapter(getContext());
        return findAdapter;
    }

    @Override
    protected String getCacheKeyPrefix() {
          return "FindFragment";
    }

    @Override
    public List<FindEntity> readList(Serializable seri) {
        return ((FindResult)seri).getData().getList();
    }

    @Override
    protected void sendRequestData() {

        BaseDTO dto=new BaseDTO();
        CommonApiClient.find(this, dto, new CallBack<FindResult>() {
            @Override
            public void onSuccess(FindResult result) {
                if(AppConfig.SUCCESS.equals(result.getCode())){
                    LogUtils.e("推荐项目列表成功");
                    mErrorLayout.setErrorMessage("暂无推荐记录",mErrorLayout.FLAG_NODATA);
                    mErrorLayout.setErrorImag(R.drawable.page_icon_empty,mErrorLayout.FLAG_NODATA);
                    if(null==result.getData()){
                        mErrorLayout.setErrorType(EmptyLayout.NODATA);
                    }else {
//                        requestDataSuccess(result);
                        setDataResult(result.getData().getList());
                    }
                }
            }
        });

    }

    @Override
    public void initData() {
        //显示头部搜索布局
        ll_find_header.setVisibility(View.VISIBLE);

    }

    public boolean autoRefreshIn(){
        return true;
    }


//    @Bind(R.id.recyclerview_find)
//    RecyclerView recyclerviewFind;
//
//    public FindAdapter findAdapter;
//
//    @Override
//    protected void retry() {
//
//    }
//
//    @Override
//    protected int getLayoutResId() {
//        return R.layout.fragment_find;
//    }
//
//    @Override
//    public void initView(View view) {
//
//        recyclerviewFind.setLayoutManager(new LinearLayoutManager(getContext()));
//        //设置adapter
//        findAdapter = new FindAdapter(getContext());
//        recyclerviewFind.setAdapter(findAdapter);
//
//    }
//
//    @Override
//    public void initData() {
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        // TODO: inflate a fragment view
//        View rootView = super.onCreateView(inflater, container, savedInstanceState);
//        ButterKnife.bind(this, rootView);
//        return rootView;
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        ButterKnife.unbind(this);
//    }
}
