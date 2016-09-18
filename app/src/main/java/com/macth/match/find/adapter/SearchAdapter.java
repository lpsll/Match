package com.macth.match.find.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.macth.match.R;
import com.macth.match.common.utils.ImageLoaderUtils;
import com.macth.match.find.entity.FindEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/9/14.
 */
public class SearchAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<FindEntity> list;

    public SearchAdapter(Context context, List<FindEntity> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_find, parent, false);
        return new SearchHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SearchHolder searchHolder = (SearchHolder) holder;
        FindEntity findEntity = list.get(position);
        searchHolder.tv_find_company_name.setText(findEntity.getCompanyname());
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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class SearchHolder extends RecyclerView.ViewHolder {
        TextView tv_find_company_name;
        TextView tv_find_price;
        TextView tv_find_project_termunit;
        TextView tv_find_project_type;
        TextView tv_find_ctime;
        TextView tv_find_project_status;
        ImageView img_find;


        public SearchHolder(View itemView) {
            super(itemView);
            tv_find_company_name = (TextView) itemView.findViewById(R.id.tv_find_company_name);
            tv_find_price = (TextView) itemView.findViewById(R.id.tv_find_price);
            tv_find_project_termunit = (TextView) itemView.findViewById(R.id.tv_find_project_termunit);
            tv_find_project_type = (TextView) itemView.findViewById(R.id.tv_find_project_type);
            tv_find_ctime = (TextView) itemView.findViewById(R.id.tv_find_ctime);
            tv_find_project_status = (TextView) itemView.findViewById(R.id.tv_find_project_status);
            img_find = (ImageView) itemView.findViewById(R.id.img_find);
        }
    }

}
