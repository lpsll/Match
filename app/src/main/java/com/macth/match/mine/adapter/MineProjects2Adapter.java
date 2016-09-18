package com.macth.match.mine.adapter;

import android.widget.ImageView;

import com.macth.match.R;
import com.macth.match.common.utils.ImageLoaderUtils;
import com.macth.match.mine.entity.MineProjectsEntity;
import com.qluxstory.ptrrecyclerview.BaseRecyclerViewHolder;
import com.qluxstory.ptrrecyclerview.BaseSimpleRecyclerAdapter;

/**
 * Created by Administrator on 2016/9/18.
 */
public class MineProjects2Adapter extends BaseSimpleRecyclerAdapter<MineProjectsEntity> {

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_mine_projects;
    }

    @Override
    public void bindData(BaseRecyclerViewHolder holder, MineProjectsEntity mineProjectsEntity, int position) {

        /**
         *  min.tv_mine_project_company_name.setText(mineProjectsEntity.getCompanyname());
         min.tv_mine_project_price.setText(mineProjectsEntity.getPrice() + "万元");
         min.tv_mine_project_project_termunit.setText(mineProjectsEntity.getProject_termunit()+"年");
         min.tv_mine_project_project_type.setText(mineProjectsEntity.getProject_type());
         min.tv_mine_project_ctime.setText(mineProjectsEntity.getCtime());
         int project_status = mineProjectsEntity.getProject_status();
         if (project_status == 1) {
         min.tv_mine_project_project_status.setText("进行中");
         } else {
         min.tv_mine_project_project_status.setText("关闭");
         }



         ImageLoaderUtils.displayImage(mineProjectsEntity.getImage(), min.img_mine_project);
         */

        holder.setText(R.id.tv_mine_project_company_name, mineProjectsEntity.getCompanyname());
        holder.setText(R.id.tv_mine_project_price, mineProjectsEntity.getPrice());
        holder.setText(R.id.tv_mine_project_project_termunit, mineProjectsEntity.getProject_termunit());
        holder.setText(R.id.tv_mine_project_project_type, mineProjectsEntity.getProject_type());
        holder.setText(R.id.tv_mine_project_ctime, mineProjectsEntity.getCtime());

        int project_status = mineProjectsEntity.getProject_status();
        if (project_status == 1) {
            holder.setText(R.id.tv_mine_project_project_status, "进行中");
        } else {
            holder.setText(R.id.tv_mine_project_project_status, "关闭");
        }

        ImageView mImg = holder.getView(R.id.img_mine_project);
        ImageLoaderUtils.displayImage(mineProjectsEntity.getImage(), mImg);

    }
}
