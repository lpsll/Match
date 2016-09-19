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
public class News2Adapter extends BaseSimpleRecyclerAdapter<NewsEntity> {

    private Activity act;

    public News2Adapter(Activity act) {
        this.act = act;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_mine_news;
    }

    @Override
    public void bindData(final BaseRecyclerViewHolder holder, final NewsEntity newsEntity, int position) {

        /**
         *   mineNewsHolder.tv_news_title.setText(newsEntity.getNotice_title());
         mineNewsHolder.tv_news_msg.setText(newsEntity.getNotice_content());
         mineNewsHolder.tv_news_date.setText(newsEntity.getNotice_ctime());
         mineNewsHolder.tv_news_hours.setText("2");
         */
        holder.setText(R.id.tv_news_title, newsEntity.getNotice_title());
        holder.setText(R.id.tv_news_msg, newsEntity.getNotice_content());
        holder.setText(R.id.tv_news_date, newsEntity.getNotice_ctime());
        holder.setText(R.id.tv_news_hours, "2");

        String notice_url = newsEntity.getNotice_url();

        //点击垃圾桶调删除消息接口，并刷新adapter
        ImageView delete = (ImageView) holder.itemView.findViewById(R.id.img_news_delete);

        final String id = newsEntity.getId();

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteNew(id);

            }
        });


        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int layoutPosition = holder.getLayoutPosition();
                    onItemClickListener.onClick(v, layoutPosition, newsEntity.getNotice_url());
                }
            });
        }
    }

    /**
     * 调接口删除数据
     */
    private void deleteNew(final String id) {

        DeleteNewDTO deleteNewDTO = new DeleteNewDTO();

        //修改userid
        deleteNewDTO.setUserid(AppContext.get("usertoken", ""));
        deleteNewDTO.setNoticeid(id);

        CommonApiClient.deleteNew(act, deleteNewDTO, new CallBack<BaseEntity>() {
            @Override
            public void onSuccess(BaseEntity result) {
                LogUtils.e("result========" + result.getMsg());
                if (AppConfig.SUCCESS.equals(result.getCode())) {
                    LogUtils.e("删除成功");
                    ToastUtils.showShort(act, "删除成功");

                    notifyItemRemoved(Integer.parseInt(id));

                }
            }
        });
    }

    public interface OnItemClickListener {
        void onClick(View v, int position, String url);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
