package com.macth.match.find.fragment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.macth.match.R;

/**
 * Created by Administrator on 2016/8/23.
 */
public class FindAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;

    public FindAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_find,parent,false);

        return new FindHolder(view);
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

    class FindHolder extends RecyclerView.ViewHolder{

        public FindHolder(View itemView) {
            super(itemView);

        }
    }
}
