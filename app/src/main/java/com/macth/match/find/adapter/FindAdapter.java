package com.macth.match.find.adapter;

import android.content.Context;
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

    private Context context;

    public FindAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_find;
    }



    @Override
    public void bindData(BaseRecyclerViewHolder holder, FindEntity findEntity, int position) {
        int project_status = findEntity.getProject_status();
        holder.setText(R.id.tv_find_company_name,findEntity.getCompanyname());
        if(project_status==1) {
            holder.setText(R.id.tv_find_project_status,"进行中");
        }else {
            holder.setText(R.id.tv_find_project_status,"关闭");
        }
        holder.setText(R.id.tv_find_price,findEntity.getPrice()+"万元");

        holder.setText(R.id.tv_find_project_termunit,findEntity.getProject_termunit()+"年");
        holder.setText(R.id.tv_find_project_type,findEntity.getProject_type());
        holder.setText(R.id.tv_find_ctime,findEntity.getCtime());

        ImageView mImg =holder.getView( R.id.img_find);
        ImageLoaderUtils.displayImage(findEntity.getImage(), mImg);
    }

//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
//        View view = LayoutInflater.from(context).inflate(R.layout.item_find,parent,false);
//
//        return new FindHolder(view);
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
//    class FindHolder extends RecyclerView.ViewHolder{
//
//        public FindHolder(View itemView) {
//            super(itemView);
//
//        }
//    }
}
