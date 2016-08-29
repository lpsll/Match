package com.macth.match.mine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.macth.match.AppConfig;
import com.macth.match.R;
import com.macth.match.common.entity.BaseEntity;
import com.macth.match.common.http.CallBack;
import com.macth.match.common.http.CommonApiClient;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.common.utils.ToastUtils;
import com.macth.match.mine.activity.NewsActivity;
import com.macth.match.mine.dto.DeleteNewDTO;
import com.macth.match.mine.entity.NewsEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/8/28.
 */
public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private List<NewsEntity> list;


    public NewsAdapter(Context context, List<NewsEntity> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(context).inflate(R.layout.item_mine_news, parent, false);

        return new MineNewsHolder(view);

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof MineNewsHolder) {

            final MineNewsHolder mineNewsHolder = (MineNewsHolder) holder;

            final NewsEntity newsEntity = list.get(position);
            mineNewsHolder.tv_news_title.setText(newsEntity.getNotice_title());
            mineNewsHolder.tv_news_msg.setText(newsEntity.getNotice_content());
            mineNewsHolder.tv_news_date.setText(newsEntity.getNotice_ctime());
            mineNewsHolder.tv_news_hours.setText("2");



            //点击垃圾桶调删除消息接口，并刷新adapter
            mineNewsHolder.img_news_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    deleteNew(position);

                }
            });

            if (onItemClickListener != null) {
                mineNewsHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int layoutPosition = mineNewsHolder.getLayoutPosition();
                        onItemClickListener.onClick(v, layoutPosition, newsEntity.getNotice_url());
                    }
                });
            }
        }
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    class MineNewsHolder extends RecyclerView.ViewHolder {

        private TextView tv_news_title;
        private TextView tv_news_date;
        private TextView tv_news_msg;
        private TextView tv_news_hours;
        private ImageView img_news_delete;

        public MineNewsHolder(View itemView) {
            super(itemView);

            tv_news_title = (TextView) itemView.findViewById(R.id.tv_news_title);
            tv_news_date = (TextView) itemView.findViewById(R.id.tv_news_date);
            tv_news_msg = (TextView) itemView.findViewById(R.id.tv_news_msg);
            tv_news_hours = (TextView) itemView.findViewById(R.id.tv_news_hours);
            img_news_delete = (ImageView) itemView.findViewById(R.id.img_news_delete);


        }
    }

    /**
     *调接口删除数据
     */
    private void deleteNew(final int position) {

        DeleteNewDTO deleteNewDTO = new DeleteNewDTO();

        //修改userid
        deleteNewDTO.setUserid("2");
        deleteNewDTO.setNoticeid(list.get(position).getId());

        CommonApiClient.deleteNew((NewsActivity)context, deleteNewDTO, new CallBack<BaseEntity>() {
            @Override
            public void onSuccess(BaseEntity result) {
                LogUtils.e("result========" + result.getMsg());
                if (AppConfig.SUCCESS.equals(result.getCode())) {
                    LogUtils.e("删除成功");
                    ToastUtils.showShort(context, "删除成功");

                    list.remove(position);
                    notifyItemRemoved(position);
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