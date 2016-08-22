package com.macth.match.common.base;

import android.support.v4.widget.NestedScrollView;
import android.view.View;


import com.macth.match.R;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 下拉刷新的Fragment的基类（嵌入了ScrollView）,继承这个类的initview,需要在第一行手动调用super.initview（）
 */
public abstract class BasePullScrollViewFragment extends BaseFragment {
    protected int PAGE_INDEX = 1;
    protected final static int PAGE_SIZE = 20;

    private  PtrClassicFrameLayout ptf;
    NestedScrollView mScrollView;
    /**
     * 是否打开下拉刷新。默认关闭
     *
     * @return
     */
    public boolean pulltoRefresh() {
        return false;
    }

    public void refreshComplete() {
        ptf.refreshComplete();
    }

    public int getRootLayoutId(){
        return R.layout.base_pull_scrollview_fragment;
    }

    @Override
    public void initView(View view) {
        ptf = (PtrClassicFrameLayout) view.findViewById(R.id.base_ptr_frame);
        mScrollView= (NestedScrollView) view.findViewById(R.id.nsv);
        ptf.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                sendRequestData();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                if (pulltoRefresh()) {
                    return PtrDefaultHandler.checkContentCanBePulledDown(frame, mScrollView, header);
                }
                return pulltoRefresh();
            }
        });
    }

    //请求数据方法
    protected void sendRequestData() {

    }

    @Override
    public void retryBefore() {
        ptf.refreshComplete();
    }

    @Override
    public void retry() {
        sendRequestData();
    }
}
