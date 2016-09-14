package com.qluxstory.ptrrecyclerview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.news.ptrrecyclerview.R;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.PtrUIHandler;

/**
 * 用PtrFrameLayout包裹一层RecyclerView
 */
public class PtrRecyclerView extends PtrFrameLayout {

    public enum RecyclerMode {
        NONE, BOTH, TOP, Gravity, BOTTOM
    }

    private LayoutInflater inflater;

    public RecyclerView getRealRecyclerView() {
        return mRecyclerView;
    }

    private RecyclerView mRecyclerView;

    private View loadMoreFooterView,loadCompleteFooterView;

    private BaseRecyclerAdapter mAdapter;

    private Context mContext;

    private OnLoadMoreListener onLoadMoreListener;
    private OnPullRefreshListener onPullRefreshListener;

    private RecyclerMode mode;

    //是否正在加载数据
    boolean isloading = false;
    //是否正在下拉刷新
    boolean pullLoading = false;
    //是否正在加载更多
    boolean loadingMore = false;
    boolean noMoreData = false;

    public PtrRecyclerView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public PtrRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public PtrRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init();
    }

    private void init() {
        inflater = LayoutInflater.from(mContext);
        mRecyclerView = new RecyclerView(mContext);
        PtrFrameLayout.LayoutParams params = new PtrFrameLayout.LayoutParams(
                PtrFrameLayout.LayoutParams.MATCH_PARENT, PtrFrameLayout.LayoutParams.MATCH_PARENT);
        mRecyclerView.setLayoutParams(params);
        addView(mRecyclerView);
        setResistance(2.4f);//阻尼系数，默认: 1.7f ，越大，感觉下拉时越吃力。
        setRatioOfHeaderHeightToRefresh(1.2f);//触发刷新时移动的位置比例，默认， 1.2f ，移动达到头部高度 1.2 倍时可触发刷新操作。
        setDurationToClose(200);//回弹延时，默认 200ms ，回弹到刷新高度所用时间。
        setDurationToCloseHeader(1000);//头部回弹时间，默认 1000ms 。
        setKeepHeaderWhenRefresh(true);//下拉刷新/释放刷新，默认为true释放刷新。
        setPullToRefresh(false);//刷新是否保持头部，默认值 true 。

    }

    public RecyclerMode getMode() {
        return mode;
    }

    public void setMode(final RecyclerMode mode) {
        this.mode=mode;
        if ((mode == RecyclerMode.BOTH || mode == RecyclerMode.TOP)&&(getPullRefreshHeaderView()==null)){
            throw new NullPointerException("PullRefreshHeaderView is null,but you mode is apply");
        }
        setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                if (mode == RecyclerMode.BOTH || mode == RecyclerMode.TOP) {
                    if(loadingMore){
                        return false;
                    }
                    return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
                } else {
                    return false;
                }

            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                if (onPullRefreshListener != null && !isloading && !loadingMore && !pullLoading) {
                    isloading = true;
                    pullLoading = true;
                    onPullRefreshListener.onPullRefresh();
                }
            }
        });

        if (mode == RecyclerMode.BOTH || mode == RecyclerMode.BOTTOM) {
            mRecyclerView.addOnScrollListener(onScrollListener);
        }
    }


    RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {

        private int lastVisibleItemPosition;
        /**
         * 是否发生了滚动
         */
        private boolean isScrolled = false;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            isScrolled = (dy > 0);
            RecyclerView.LayoutManager mLayoutManager = recyclerView.getLayoutManager();
            if (null != mLayoutManager) {
                if (mLayoutManager instanceof LinearLayoutManager) {
                    lastVisibleItemPosition = ((LinearLayoutManager) mLayoutManager)
                            .findLastVisibleItemPosition();
                } else if (mLayoutManager instanceof GridLayoutManager) {
                    lastVisibleItemPosition = ((GridLayoutManager) mLayoutManager)
                            .findLastVisibleItemPosition();
                }
            }
            if (isScrolled && loadMoreFooterView == null) {
                Log.e("onScrolled----1---","loadMoreFooterView");
                addLoadMoreFooterView();
            }
            if (isScrolled && !noMoreData) {
                Log.e("onScrolled----2----","noMoreData");
                mAdapter.showloadMoreFooterView(true);
            }
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            RecyclerView.LayoutManager mLayoutManager = recyclerView.getLayoutManager();
            int visibleItemCount = mLayoutManager.getChildCount();
            int totalItemCount = mLayoutManager.getItemCount();
            if (!isloading && isScrolled && (visibleItemCount > 0
                    && newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItemPosition >= totalItemCount - 1 && !noMoreData && !pullLoading)
                    &&(onLoadMoreListener != null)) {
                    isloading = true;
                    loadingMore = true;
                    onLoadMoreListener.onloadMore();
            }
        }
    };


    public void addLoadMoreFooterView() {
        loadMoreFooterView = inflater.inflate(R.layout.view_push_load_more_footer, mRecyclerView, false);
        loadMoreFooterView.setTag("loadMoreFooterView");
        addFooterView(loadMoreFooterView);
        mAdapter.showloadMoreFooterView(false);
        mAdapter.notifyItemChanged(mAdapter.getItemCount() - 1);
    }


    public void pullRefreshComplete() {
        refreshComplete();
        reset();
    }


    public void loadMoreComplete() {
        loadingMore = false;
        isloading = false;
        mAdapter.showloadMoreFooterView(false);
    }


    public void noMoreData() {
        Toast.makeText(mContext, "没有更多数据了", Toast.LENGTH_SHORT).show();
        noMoreData = true;
        addLoadCompleteFooterView();

    }

    public void addLoadCompleteFooterView() {
        loadCompleteFooterView = inflater.inflate(R.layout.view_push_load_complete_footer, mRecyclerView, false);
        loadCompleteFooterView.setTag("loadCompleteFooterView");
        addFooterView(loadCompleteFooterView);
//        mAdapter.showloadMoreFooterView(false);
        mAdapter.notifyItemChanged(mAdapter.getItemCount() - 1);
    }

    public void reset() {
        isloading = false;
        pullLoading = false;
        loadingMore = false;
        noMoreData = false;
    }


    /**
     * 设置头部刷新视图
     *
     * @param PullRefreshHeaderView 头部刷新视图
     */
    public void setPullRefreshHeaderView(PtrUIHandler PullRefreshHeaderView) {
        setHeaderView((View) PullRefreshHeaderView);
        try {
            addPtrUIHandler(PullRefreshHeaderView);
        } catch (Exception e) {
            throw new ClassCastException("PullRefreshHeaderView is not implement PtrUIHandler  ");
        }
    }

    public View getPullRefreshHeaderView() {
        return getHeaderView();
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        mRecyclerView.setLayoutManager(layoutManager);
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return mRecyclerView.getLayoutManager();
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        if (adapter instanceof BaseRecyclerAdapter) {
            mAdapter = (BaseRecyclerAdapter) adapter;
        } else {
            throw new ClassCastException("adapter is not BaseRecyclerAdapter");
        }
        mRecyclerView.setAdapter(adapter);
    }

    public RecyclerView.Adapter getAdapter() {
        return mRecyclerView.getAdapter();
    }


    /**
     * this method must be called after setAdapter
     *
     * @param view headView
     */
    public void addHeaderView(View view) {
        if (mAdapter == null) {
            throw new NullPointerException("setAdapter() it wasn't already called before");
        }
        mAdapter.addHeaderView(view);
    }

    /**
     * this method must be called after setAdapter
     *
     * @param view footView
     */
    public void addFooterView(View view) {
        if (mAdapter == null) {
            throw new NullPointerException("setAdapter() it wasn't already called before");
        }
        mAdapter.addFooterView(view);
    }


    public void removeFooterView(View view) {
        if (mAdapter == null) {
            throw new NullPointerException("setAdapter() it wasn't already called before");
        }
        mAdapter.removeFooterView(view);
    }

    public interface OnPullRefreshListener {
        void onPullRefresh();
    }


    public interface OnLoadMoreListener {
        void onloadMore();
    }


    public void setOnPullRefreshListener(OnPullRefreshListener onPullRefreshListener) {
        this.onPullRefreshListener = onPullRefreshListener;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }
}
