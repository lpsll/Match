package com.macth.match.find.adapter;

import android.widget.ImageView;

import com.macth.match.R;
import com.macth.match.common.utils.ImageLoaderUtils;
import com.macth.match.find.entity.FindEntity;
import com.qluxstory.ptrrecyclerview.BaseRecyclerViewHolder;
import com.qluxstory.ptrrecyclerview.BaseSimpleRecyclerAdapter;

/**
 * Created by Administrator on 2016/9/18.
 */
public class Search2Adapter extends BaseSimpleRecyclerAdapter<FindEntity> {


    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_find;
    }



    @Override
    public void bindData(BaseRecyclerViewHolder holder, FindEntity findEntity, int position) {
        /**
         *  searchHolder.tv_find_company_name.setText(findEntity.getCompanyname());
         searchHolder.tv_find_price.setText(findEntity.getPrice());
         searchHolder.tv_find_project_termunit.setText(findEntity.getProject_termunit());
         searchHolder.tv_find_ctime.setText(findEntity.getCtime());
         int project_status = findEntity.getProject_status();

         if (project_status == 1) {
         searchHolder.tv_find_project_status.setText( "进行中");
         } else {
         searchHolder.tv_find_project_status.setText( "关闭");
         }

         ImageLoaderUtils.displayImage(findEntity.getImage(), searchHolder.img_find);
         */

        holder.setText(R.id.tv_find_company_name,findEntity.getCompanyname());
        holder.setText(R.id.tv_find_price,findEntity.getPrice());
        holder.setText(R.id.tv_find_project_termunit,findEntity.getProject_termunit());
        holder.setText(R.id.tv_find_ctime,findEntity.getCtime());

        int project_status = findEntity.getProject_status();
        if (project_status == 1) {
            holder.setText(R.id.tv_find_project_status,"进行中");
        } else {
            holder.setText(R.id.tv_find_project_status,"关闭");
        }

        ImageView mImg = holder.getView(R.id.img_find);
        ImageLoaderUtils.displayImage(findEntity.getImage(), mImg);
    }


}
