package com.macth.match.notice.adapter;

import com.macth.match.R;
import com.macth.match.notice.entity.NoticeEntity;
import com.qluxstory.ptrrecyclerview.BaseRecyclerViewHolder;
import com.qluxstory.ptrrecyclerview.BaseSimpleRecyclerAdapter;

/**
 * Created by Administrator on 2016/8/22.
 */
public class NoticeAdapter extends BaseSimpleRecyclerAdapter<NoticeEntity> {
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
    public void bindData(BaseRecyclerViewHolder holder, NoticeEntity noticeEntity, int position) {
        holder.setText(R.id.tv_title,noticeEntity.getMessage_title());
        holder.setText(R.id.tv_notice_date,noticeEntity.getMessage_ctime());
        holder.setText(R.id.tv_notice_msg,noticeEntity.getMessage_content());
    }

}
