package com.macth.match.find.adapter;

import android.widget.ImageView;

import com.macth.match.R;
import com.macth.match.common.utils.ImageLoaderUtils;
import com.macth.match.find.entity.FindEntity;
import com.qluxstory.ptrrecyclerview.BaseRecyclerViewHolder;
import com.qluxstory.ptrrecyclerview.BaseSimpleRecyclerAdapter;

/**
 * Created by Administrator on 2016/8/23.
 */
public class FindAdapter extends BaseSimpleRecyclerAdapter<FindEntity> {

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_find;
    }

    @Override
    public void bindData(BaseRecyclerViewHolder holder, FindEntity findEntity, int position) {
        int project_status = findEntity.getProject_status();
        holder.setText(R.id.tv_find_company_name, findEntity.getCompanyname());
        if (project_status == 1) {
            holder.setText(R.id.tv_find_project_status, "进行中");
        } else {
            holder.setText(R.id.tv_find_project_status, "关闭");
        }
        holder.setText(R.id.tv_find_price, findEntity.getPrice() + "万元");

        holder.setText(R.id.tv_find_project_termunit, findEntity.getProject_termunit() + "年");
        holder.setText(R.id.tv_find_project_type, findEntity.getProject_type());
        holder.setText(R.id.tv_find_ctime, findEntity.getCtime());

        ImageView mImg = holder.getView(R.id.img_find);
        ImageLoaderUtils.displayImage(findEntity.getImage(), mImg);
    }





//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if (viewType == 0) {
//            View view = LayoutInflater.from(context).inflate(R.layout.find_recyclerview_header, parent, false);
//            return new HeaderHolder(view);
//        }
//
//        View view = LayoutInflater.from(context).inflate(R.layout.item_find, parent, false);
//        return new FindHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
//        if (holder instanceof FindHolder) {
//            FindHolder findHolder = (FindHolder) holder;
//            FindEntity findEntity = list.get(position);
//            int project_status = findEntity.getProject_status();
//            findHolder.tv_find_company_name.setText(findEntity.getCompanyname());
//            if (project_status == 1) {
//                findHolder.tv_find_project_status.setText("进行中");
//            } else {
//                findHolder.tv_find_project_status.setText("关闭");
//            }
//            findHolder.tv_find_price.setText(findEntity.getPrice() + "万元");
//            findHolder.tv_find_project_termunit.setText(findEntity.getProject_termunit() + "年");
//            findHolder.tv_find_project_type.setText(findEntity.getProject_type());
//            findHolder.tv_find_ctime.setText(findEntity.getCtime());
//
//            ImageLoaderUtils.displayImage(findEntity.getImage(), findHolder.img_find);
//
//        }
//
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//
//    @Override
//    public int getItemViewType(int position) {
//
//        if (position == 0) {
//            return 0;
//
//        } else {
//            return 1;
//        }
//    }
//
//    class FindHolder extends RecyclerView.ViewHolder {
//        private TextView tv_find_company_name;
//        private TextView tv_find_price;
//        private TextView tv_find_project_termunit;
//        private TextView tv_find_project_type;
//        private TextView tv_find_ctime;
//        private TextView tv_find_project_status;
//        private ImageView img_find;
//
//        public FindHolder(View itemView) {
//            super(itemView);
//
//            tv_find_company_name = (TextView) itemView.findViewById(R.id.tv_find_company_name);
//            tv_find_price = (TextView) itemView.findViewById(R.id.tv_find_price);
//            tv_find_project_termunit = (TextView) itemView.findViewById(R.id.tv_find_project_termunit);
//            tv_find_project_type = (TextView) itemView.findViewById(R.id.tv_find_project_type);
//            tv_find_ctime = (TextView) itemView.findViewById(R.id.tv_find_ctime);
//            tv_find_project_status = (TextView) itemView.findViewById(R.id.tv_find_project_status);
//            img_find = (ImageView) itemView.findViewById(R.id.img_find);
//
//        }
//    }
//
//    class HeaderHolder extends RecyclerView.ViewHolder {
//
//
//        public HeaderHolder(View itemView) {
//            super(itemView);
//
//        }
//    }
}
