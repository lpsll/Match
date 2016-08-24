package com.macth.match.notice.fragment;

import com.macth.match.AppConfig;
import com.macth.match.R;
import com.macth.match.common.base.BaseListFragment;
import com.macth.match.common.dto.BaseDTO;
import com.macth.match.common.http.CallBack;
import com.macth.match.common.http.CommonApiClient;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.common.widget.EmptyLayout;
import com.macth.match.notice.adapter.NoticeAdapter;
import com.macth.match.notice.entity.NoticeEntity;
import com.macth.match.notice.entity.NoticeResult;
import com.qluxstory.ptrrecyclerview.BaseRecyclerAdapter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by John_Libo on 2016/8/22.
 * 公告页面
 */
public class NoticeFragment extends BaseListFragment<NoticeEntity> {


//    @Bind(R.id.recyclerview_notice)
//    RecyclerView recyclerviewNotice;
//
//    private NoticeAdapter noticeAdapter;
//
//    @Override
//    protected void retry() {
//
//    }
//
//    @Override
//    public BaseRecyclerAdapter<NoticeEntity> createAdapter() {
//        return null;
//    }
//
//    @Override
//    protected String getCacheKeyPrefix() {
//        return null;
//    }
//
//    @Override
//    public List<NoticeEntity> readList(Serializable seri) {
//        return null;
//    }
//
//    @Override
//    protected void sendRequestData() {
//
//    }
//
//    @Override
//    protected int getLayoutResId() {
//        return R.layout.fragment_notice;
//    }
//
//    @Override
//    public void initView(View view) {
//
//        recyclerviewNotice.setLayoutManager(new LinearLayoutManager(getContext()));
//        //设置adapter
//        noticeAdapter = new NoticeAdapter(getContext());
//        recyclerviewNotice.setAdapter(noticeAdapter);
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

    @Override
    public BaseRecyclerAdapter<NoticeEntity> createAdapter() {
        return new NoticeAdapter();
    }

    @Override
    protected String getCacheKeyPrefix() {
        return "RecommendFragment";
    }

    @Override
    public List<NoticeEntity> readList(Serializable seri) {
        return ((NoticeResult)seri).getData().getList();
    }

    @Override
    protected void sendRequestData() {
        BaseDTO dto=new BaseDTO();
        CommonApiClient.notice(this, dto, new CallBack<NoticeResult>() {
            @Override
            public void onSuccess(NoticeResult result) {
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

    }
    public boolean autoRefreshIn(){
        return true;
    }
}
