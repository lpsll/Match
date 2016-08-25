package com.macth.match.mine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.macth.match.R;
import com.macth.match.common.utils.ImageLoaderUtils;
import com.macth.match.mine.entity.MineProjectsEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/8/25.
 */
public class MineProjectsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<MineProjectsEntity> list;


    public MineProjectsAdapter(Context context, List<MineProjectsEntity> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_mine_projects, parent, false);

        return new MineProjectsHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MineProjectsHolder) {
            MineProjectsHolder min = (MineProjectsHolder) holder;
            MineProjectsEntity mineProjectsEntity = list.get(position);

            min.tv_mine_project_company_name.setText(mineProjectsEntity.getCompanyname());
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

        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MineProjectsHolder extends RecyclerView.ViewHolder {

        private ImageView img_mine_project;
        private TextView tv_mine_project_company_name;
        private TextView tv_mine_project_price;
        private TextView tv_mine_project_project_termunit;
        private TextView tv_mine_project_project_type;
        private TextView tv_mine_project_ctime;
        private TextView tv_mine_project_project_status;
        private TextView tv_mine_projects_update_milestone;

        public MineProjectsHolder(View itemView) {
            super(itemView);
            img_mine_project = (ImageView) itemView.findViewById(R.id.img_mine_project);
            tv_mine_project_company_name = (TextView) itemView.findViewById(R.id.tv_mine_project_company_name);
            tv_mine_project_price = (TextView) itemView.findViewById(R.id.tv_mine_project_price);
            tv_mine_project_project_termunit = (TextView) itemView.findViewById(R.id.tv_mine_project_project_termunit);
            tv_mine_project_project_type = (TextView) itemView.findViewById(R.id.tv_mine_project_project_type);
            tv_mine_project_ctime = (TextView) itemView.findViewById(R.id.tv_mine_project_ctime);
            tv_mine_project_project_status = (TextView) itemView.findViewById(R.id.tv_mine_project_project_status);
            tv_mine_projects_update_milestone = (TextView) itemView.findViewById(R.id.tv_mine_projects_update_milestone);


        }
    }

}
