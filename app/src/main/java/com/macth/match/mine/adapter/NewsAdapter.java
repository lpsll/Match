package com.macth.match.mine.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ImageView;

import com.macth.match.AppConfig;
import com.macth.match.AppContext;
import com.macth.match.R;
import com.macth.match.common.entity.BaseEntity;
import com.macth.match.common.http.CallBack;
import com.macth.match.common.http.CommonApiClient;
import com.macth.match.common.utils.DialogUtils;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.mine.dto.DeleteNewDTO;
import com.macth.match.mine.entity.NewsEntity;
import com.macth.match.mine.entity.NewsEvent;
import com.qluxstory.ptrrecyclerview.BaseRecyclerViewHolder;
import com.qluxstory.ptrrecyclerview.BaseSimpleRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/9/19.
 */
public class NewsAdapter extends BaseSimpleRecyclerAdapter<NewsEntity> {

    List<String> list = new ArrayList<>();
    private final Context context;

    public NewsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_mine_news;
    }

    @Override
    public void bindData(final BaseRecyclerViewHolder holder, final NewsEntity newsEntity, final int position) {
        list.add(position,newsEntity.getId());
        holder.setText(R.id.tv_news_title, newsEntity.getNotice_title());
        holder.setText(R.id.tv_news_msg, newsEntity.getNotice_content());
        holder.setText(R.id.tv_news_date, newsEntity.getNotice_ctime());
        holder.setText(R.id.tv_news_hours, "2");
        ImageView img = holder.getView(R.id.img_news_delete);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.confirm(context, "是否删除此条消息", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DeleteNewDTO deleteNewDTO = new DeleteNewDTO();
                        //修改userid
                        deleteNewDTO.setUserid(AppContext.get("usertoken", ""));
                        deleteNewDTO.setNoticeid(list.get(position));

                        CommonApiClient.deleteNew((Activity) context, deleteNewDTO, new CallBack<BaseEntity>() {
                            @Override
                            public void onSuccess(BaseEntity result) {
                                if (AppConfig.SUCCESS.equals(result.getCode())) {
                                    LogUtils.e("删除成功");
                                    EventBus.getDefault().post(
                                            new NewsEvent("ok"));

                                }
                            }
                        });
                    }
                });
            }
        });

    }



}
