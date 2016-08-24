package com.macth.match.notice.adapter;

import android.content.Context;
import android.view.View;

import com.macth.match.R;
import com.macth.match.notice.entity.NoticeEntity;
import com.qluxstory.ptrrecyclerview.BaseRecyclerViewHolder;
import com.qluxstory.ptrrecyclerview.BaseSimpleRecyclerAdapter;

/**
 * Created by Administrator on 2016/8/22.
 */
public class NoticeAdapter extends BaseSimpleRecyclerAdapter<NoticeEntity> {

    private Context context;

    public NoticeAdapter(Context context) {
        this.context = context;
    }

    //    @Override
//    public int getItemViewLayoutId() {
//        return R.layout.item_recommend;
//    }
//
//    @Override
//    public void bindData(BaseRecyclerViewHolder holder, RecommendEntity recommendEntity, int position) {
//        holder.setText(R.id.rc_tv_company,recommendEntity.getCompanyname());
//        holder.setText(R.id.rc_tv_money,recommendEntity.getPrice());
//        holder.setText(R.id.rc_tv_term,recommendEntity.getProject_termunit()+" "+recommendEntity.getProject_type());
//        holder.setText(R.id.rc_tv_data,recommendEntity.getCtime());
//        ImageView mImg =holder.getView( R.id.rc_img);
//        ImageLoaderUtils.displayImage(recommendEntity.getImage(), mImg);
//    }{
//
//    private Context context;
//
//    public NoticeAdapter(Context context) {
//        this.context = context;
//    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
//        View view = LayoutInflater.from(context).inflate(R.layout.item_notice,parent,false);
//
//        return new NoticeHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return 5;
//    }
//
//
//    @Override
//    public int getItemViewType(int position) {
//
//        return super.getItemViewType(position);
//    }
//
//    class NoticeHolder extends RecyclerView.ViewHolder{
//
//        public NoticeHolder(View itemView) {
//            super(itemView);
//
//        }
//    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_notice;
    }

    @Override
    public void bindData(final BaseRecyclerViewHolder holder, final NoticeEntity noticeEntity, int position) {
        holder.setText(R.id.tv_title, noticeEntity.getMessage_title());
        holder.setText(R.id.tv_notice_date, noticeEntity.getMessage_ctime());
        holder.setText(R.id.tv_notice_msg, noticeEntity.getMessage_content());

        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int layoutPosition = holder.getLayoutPosition();
                    onItemClickListener.onClick(v, layoutPosition, noticeEntity.getMessage_url());
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onClick(View v, int position, String url);
    }

    private OnItemClickListener onItemClickListener;

    public void setRecylerViewInterface(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}