package com.macth.match.common.base;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.macth.match.AppContext;
import com.macth.match.R;
import com.macth.match.common.cache.CacheManager;
import com.macth.match.common.entity.BaseEntity;
import com.macth.match.common.utils.StringUtils;
import com.macth.match.common.utils.TDevice;
import com.macth.match.common.widget.EmptyLayout;
import com.qluxstory.ptrrecyclerview.BaseRecyclerAdapter;
import com.qluxstory.ptrrecyclerview.PtrRecyclerView;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrUIHandler;

/**
 * Created by Administrator on 2016/9/18.
 */
public abstract class BaseListActivity<T> extends BaseTitleActivity implements BaseRecyclerAdapter.OnRecyclerViewItemClickListener{

    protected PtrRecyclerView mPtrRecyclerView;
    protected BaseRecyclerAdapter<T> mAdapter;
    protected int mCurrentPage = 1;
    protected final static int PAGE_SIZE = 20;
    private final static int ACTION_PULL_REFRESH = 1;
    private final static int ACTION_LOAD_MORE = 2;
    private AsyncTask<String, Void, List<T>> mCacheTask;
    // return CACHE_KEY_PREFIX + mCatalog;
    protected int mCatalog = 1;
    public static final String BUNDLE_KEY_CATALOG = "BUNDLE_KEY_CATALOG";
    private int action;

    //头部搜索布局
    public FrameLayout ll_find_header;
    public EmptyLayout mErrorLayout;
    public RelativeLayout rl_search;
    public EditText et_find_search; //搜索关键字
    public TextView tv_search;  //搜索按钮


    public RecyclerView.LayoutManager setupLayoutManager() {
        return new LinearLayoutManager(this);
    }

    /**
     * 是否需要开启缓存,默认开启
     * @return
     */
    public boolean cache() {
        return true;
    }

    public PtrRecyclerView.RecyclerMode setupMode() {
        return PtrRecyclerView.RecyclerMode.BOTH;
    }

    public PtrUIHandler setupPullRefreshHeaderView() {
        return new PtrClassicDefaultHeader(this); //经典下拉刷新头部
        // return new YncPullRefreshHeader(this);
    }


    @Override
    protected int getContentResId() {
        return R.layout.activity_base_list;
    }

    @Override
    public void initView() {


        ll_find_header = (FrameLayout) findViewById(R.id.ll_find_header);
        mPtrRecyclerView = (PtrRecyclerView) findViewById(R.id.base_recyclerview);
        mErrorLayout = (EmptyLayout) findViewById(R.id.error_layout);
        rl_search = (RelativeLayout) findViewById(R.id.rl_search);
        et_find_search = (EditText) rl_search.findViewById(R.id.et_find_search);
        tv_search = (TextView) rl_search.findViewById(R.id.tv_search);

        mPtrRecyclerView.setLayoutManager(setupLayoutManager());
        mPtrRecyclerView.setPullRefreshHeaderView(setupPullRefreshHeaderView());
        mPtrRecyclerView.setMode(setupMode());
        mAdapter = createAdapter();
        mAdapter.setOnItemClickListener(this);
        mPtrRecyclerView.setAdapter(mAdapter);

        if (mPtrRecyclerView.getMode() == PtrRecyclerView.RecyclerMode.TOP || mPtrRecyclerView.getMode() == PtrRecyclerView.RecyclerMode.BOTH) {
            mPtrRecyclerView.setOnPullRefreshListener(new PtrRecyclerView.OnPullRefreshListener() {
                @Override
                public void onPullRefresh() {
                    action = ACTION_PULL_REFRESH;
                    mCurrentPage = 1;
                    requestData();
                }
            });
        }
        if (mPtrRecyclerView.getMode() == PtrRecyclerView.RecyclerMode.BOTTOM || mPtrRecyclerView.getMode() == PtrRecyclerView.RecyclerMode.BOTH) {
            mPtrRecyclerView.setOnLoadMoreListener(new PtrRecyclerView.OnLoadMoreListener() {
                @Override
                public void onloadMore() {
                    action = ACTION_LOAD_MORE;
                    requestData();
                }

            });
        }
        if(autoRefreshIn()) {
            if (!TDevice.hasInternet(this)) {
                mErrorLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
            } else {
                mPtrRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showDialogLoading();
                        action = ACTION_PULL_REFRESH;
                        mCurrentPage = 1;
                        requestData();
                    }
                }, 100);
            }

        }

    }


    public void reset(){
        if (action == ACTION_PULL_REFRESH) {
            mPtrRecyclerView.pullRefreshComplete();
        } else if (action == ACTION_LOAD_MORE) {
            mPtrRecyclerView.loadMoreComplete();
        }
    }

    /*
        * 一进入页面就自动刷新,默认不刷新
        * */
    public boolean autoRefreshIn(){
        return false;
    }

    public abstract BaseRecyclerAdapter<T> createAdapter();

    public void setDataResult(List<T> list) {
        hideDialogLoading();
        reset();
        mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
        if (list == null || list.size() == 0) {
            if (mCurrentPage == 1) {
                mErrorLayout.setErrorType(EmptyLayout.NODATA);
            } else {
                mPtrRecyclerView.noMoreData();
            }
        } else if (list != null && list.size() > 0) {
            mCurrentPage += 1;
            if (action == ACTION_PULL_REFRESH) {
                mAdapter.removeAll();
                mAdapter.append(list);
                mPtrRecyclerView.pullRefreshComplete();
            } else if (action == ACTION_LOAD_MORE) {
                mAdapter.append(list);
                mPtrRecyclerView.loadMoreComplete();
                if (list.size() < PAGE_SIZE) {
                    mPtrRecyclerView.noMoreData();
                }
            }

        }
    }

    public RecyclerView getRecyclerView() {
        return mPtrRecyclerView.getRealRecyclerView();
    }

    @Override
    public void onItemClick(View itemView, Object itemBean, int position) {
    }

    //缓存key的prefix
    protected abstract String getCacheKeyPrefix();

    private String getCacheKey() {
        return new StringBuilder(getCacheKeyPrefix()).append("_")
                .append(mCurrentPage).toString();
    }

    //没网且有缓存且缓存未失效时，使用缓存数据。有网一律重新获取数据
    protected void requestData() {
        if (!cache()) {
            if (!TDevice.hasInternet(this)) {
                hideDialogLoading();
                mErrorLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
            } else {
                sendRequestData();
            }
            return;
        }

        String key = getCacheKey();
        if (!TDevice.hasInternet(this)) {
            if (CacheManager.isExistDataCache(key)
                    && !CacheManager.isCacheDataFailure(this, key)
                    ) {
                readCacheData(key);
            } else {
                hideDialogLoading();
                mErrorLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
            }
        } else {
            // 取新的数据
            sendRequestData();
        }

    }

    private void cancelReadCacheTask() {
        if (mCacheTask != null) {
            mCacheTask.cancel(true);
            mCacheTask = null;
        }
    }

    private void readCacheData(String cacheKey) {
        cancelReadCacheTask();
        mCacheTask = new CacheTask(this).execute(cacheKey);
    }

    private class CacheTask extends AsyncTask<String, Void, List<T>> {
        private final WeakReference<Context> mContext;

        private CacheTask(Context context) {
            mContext = new WeakReference<>(context);
        }

        @Override
        protected List<T> doInBackground(String... params) {
            if (mContext == null) return null;
            Serializable seri = CacheManager.readObject(params[0]);
            if (seri == null) {
                return null;
            } else {
                return readList(seri);
            }
        }

        @Override
        protected void onPostExecute(List<T> list) {
            super.onPostExecute(list);
            setDataResult(list);
        }
    }

    public abstract List<T> readList(Serializable seri);

    private class SaveCacheTask extends AsyncTask<Void, Void, Void> {
        private final WeakReference<Context> mContext;
        private final Serializable seri;
        private final String key;

        private SaveCacheTask(Context context, Serializable seri, String key) {
            mContext = new WeakReference<>(context);
            this.seri = seri;
            this.key = key;
        }

        @Override
        protected Void doInBackground(Void... params) {
            if (mContext == null) return null;
            CacheManager.saveObject(seri, key);
            return null;
        }
    }


    //请求数据方法
    protected abstract void sendRequestData();


    public void requestDataSuccess(BaseEntity res) {
        try {
            if (cache()) {
                new SaveCacheTask(this, res, getCacheKey()).execute();
            }
            AppContext.putToLastRefreshTime(getCacheKey(),
                    StringUtils.getCurTimeStr());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("tag", "execute SaveCacheTask failure!");
        }
    }

    // 是否到时间去刷新数据了
    private boolean onTimeRefresh() {
        String lastRefreshTime = AppContext.getLastRefreshTime(getCacheKey());
        String currTime = StringUtils.getCurTimeStr();
        long diff = StringUtils.calDateDifferent(lastRefreshTime, currTime);
        return needAutoRefresh() && diff > getAutoRefreshTime();
    }


    /***
     * 自动刷新的时间
     * 默认：自动刷新的时间为半天时间
     */
    protected long getAutoRefreshTime() {
        return 12 * 60 * 60;
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (onTimeRefresh()) {
//            sendRequestData();
//        }
    }

    // 是否需要自动刷新
    protected boolean needAutoRefresh() {
        return true;
    }

}
