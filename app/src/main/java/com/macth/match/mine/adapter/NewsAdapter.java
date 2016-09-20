package com.macth.match.mine.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.macth.match.AppConfig;
import com.macth.match.AppContext;
import com.macth.match.R;
import com.macth.match.common.entity.BaseEntity;
import com.macth.match.common.http.CallBack;
import com.macth.match.common.http.CommonApiClient;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.common.utils.ToastUtils;
import com.macth.match.mine.dto.DeleteNewDTO;
import com.macth.match.mine.entity.NewsEntity;
import com.qluxstory.ptrrecyclerview.BaseRecyclerViewHolder;
import com.qluxstory.ptrrecyclerview.BaseSimpleRecyclerAdapter;

/**
 * Created by Administrator on 2016/9/19.
 */
public class NewsAdapter extends BaseSimpleRecyclerAdapter<NewsEntity> {


    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_mine_news;
    }

    @Override
    public void bindData(final BaseRecyclerViewHolder holder, final NewsEntity newsEntity, int position) {

        holder.setText(R.id.tv_news_title, newsEntity.getNotice_title());
        holder.setText(R.id.tv_news_msg, newsEntity.getNotice_content());
        holder.setText(R.id.tv_news_date, newsEntity.getNotice_ctime());
        holder.setText(R.id.tv_news_hours, "2");

    }


}
