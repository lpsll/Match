package com.macth.match.recommend.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.macth.match.R;
import com.macth.match.common.utils.ImageLoaderUtils;
import com.macth.match.recommend.entity.RecommendEntity;
import com.qluxstory.ptrrecyclerview.BaseRecyclerViewHolder;
import com.qluxstory.ptrrecyclerview.BaseSimpleRecyclerAdapter;

/**
 * Created by John_Libo on 2016/8/23.
 */
public class RecommendAdapter extends BaseSimpleRecyclerAdapter<RecommendEntity> {
    TextView mTv,mTvCompany;
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_recommend;
    }

    @Override
    public void bindData(BaseRecyclerViewHolder holder, RecommendEntity recommendEntity, int position) {
        mTv = holder.getView(R.id.tv);
        mTvCompany = holder.getView(R.id.rc_tv_company);
        mTv.getPaint().setFakeBoldText(true);//加粗
        mTvCompany.getPaint().setFakeBoldText(true);//加粗
        mTvCompany.setText(recommendEntity.getCompanyname());
        holder.setText(R.id.rc_tv_money,recommendEntity.getPrice());
        holder.setText(R.id.rc_tv_term,recommendEntity.getProject_termunit()+"年 "+recommendEntity.getProject_type());
        holder.setText(R.id.rc_tv_data,recommendEntity.getCtime());
        ImageView mImg =holder.getView( R.id.rc_img);
        ImageLoaderUtils.displayImage(recommendEntity.getImage(), mImg);
    }
}
