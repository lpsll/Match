package com.qluxstory.ptrrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 简单的adapter处理单一布局
 * Created by bixinwei on 16/3/9.
 */
public abstract class BaseSimpleRecyclerAdapter<T> extends BaseRecyclerAdapter<T> {

    private final static int VIEW_TYPE_NORMAL = Integer.MAX_VALUE-Integer.MIN_VALUE;

    public abstract int getItemViewLayoutId();

    public abstract void bindData(BaseRecyclerViewHolder holder, T t, int position);

    public void bindData(BaseRecyclerViewHolder holder, T itemBean, int position, int viewType){
        bindData(holder, itemBean, position);
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (!setLayoutManager && parent instanceof RecyclerView) {
            setLayoutManager((RecyclerView) parent);
            setLayoutManager = true;
        }

        if (mHeaderViewTypes.contains(viewType)) {
            return new BaseRecyclerViewHolder(mHeaderViews.get(viewType - VIEW_TYPE_HEADER));
        } else if (mFooterViewTypes.contains(viewType)) {
            return new BaseRecyclerViewHolder(mFootViews.get(VIEW_TYPE_FOOTER - viewType));
        } else {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(getItemViewLayoutId(), parent,
                    false);
            return new BaseRecyclerViewHolder(itemView);
        }

    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemViewType(int position) {
        int numHeaders = getHeadersCount();
        int realPosition = position - numHeaders;
        if (numHeaders > 0 && position < numHeaders) {
            int mHeaderViewType = VIEW_TYPE_HEADER + position;
            mHeaderViewTypes.add(mHeaderViewType);
            return mHeaderViewType;
        } else if (getFootersCount() > 0 && realPosition >= mDatas.size()) {
            int mFooterViewType = VIEW_TYPE_FOOTER - (position - numHeaders - mDatas.size());
            mFooterViewTypes.add(mFooterViewType);
            return mFooterViewType;
        } else {
            return VIEW_TYPE_NORMAL;
        }
    }

}
