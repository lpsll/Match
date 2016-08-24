package com.macth.match.notice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.macth.match.R;

/**
 * Created by Administrator on 2016/8/22.
 */
public class NoticeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;

    public NoticeAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_notice,parent,false);

        return new NoticeHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }


    @Override
    public int getItemViewType(int position) {

        return super.getItemViewType(position);
    }

    class NoticeHolder extends RecyclerView.ViewHolder{

        public NoticeHolder(View itemView) {
            super(itemView);

        }
    }
}
