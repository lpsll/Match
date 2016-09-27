package com.macth.match.recommend.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;
import android.view.WindowManager;
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
    TextView mTv, mTvCompany;
    private Activity act;

    public RecommendAdapter(Activity act) {
        this.act = act;
    }

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
        holder.setText(R.id.rc_tv_money, recommendEntity.getPrice()+"万");
        
        if("1".equals(recommendEntity.getProject_termunit())) {
            holder.setText(R.id.rc_tv_term, recommendEntity.getProject_termvalue() + "年 " + recommendEntity.getProject_type());
        }else if("2".equals(recommendEntity.getProject_termunit())) {
            holder.setText(R.id.rc_tv_term, recommendEntity.getProject_termvalue() + "个月 " + recommendEntity.getProject_type());
        }
        
        holder.setText(R.id.rc_tv_data, recommendEntity.getCtime());

        ImageView mImg = holder.getView(R.id.rc_img);

        //动态设置图片的宽/高为15:8
        ViewGroup.LayoutParams params = mImg.getLayoutParams();
        WindowManager wm = (WindowManager) act.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        params.height = width / 15 * 8;
//        params.width =100;
        mImg.setLayoutParams(params);

        ImageLoaderUtils.displayImage(recommendEntity.getImage(), mImg);
    }
}
