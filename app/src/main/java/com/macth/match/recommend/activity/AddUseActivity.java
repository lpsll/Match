package com.macth.match.recommend.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.macth.match.R;
import com.macth.match.common.base.BaseTitleActivity;
import com.macth.match.common.widget.FullyLinearLayoutManager;
import com.macth.match.recommend.RecommendUiGoto;
import com.macth.match.recommend.entity.MilDetailsEntity;
import com.qluxstory.ptrrecyclerview.BaseRecyclerAdapter;
import com.qluxstory.ptrrecyclerview.BaseRecyclerViewHolder;
import com.qluxstory.ptrrecyclerview.BaseSimpleRecyclerAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 增加资金用途页
 */
public class AddUseActivity extends BaseTitleActivity {
    @Bind(R.id.tv_btn)
    TextView tvBtn;
    @Bind(R.id.adduse_list)
    RecyclerView adduseList;
    BaseSimpleRecyclerAdapter mAdapter;

    @Override
    protected int getContentResId() {
        return R.layout.activity_adduse;
    }

    @Override
    public void initView() {
        adduseList.setLayoutManager(new FullyLinearLayoutManager(this));
        mAdapter=new BaseSimpleRecyclerAdapter<MilDetailsEntity>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.item_attachments;
            }

            @Override
            public void bindData(BaseRecyclerViewHolder holder, MilDetailsEntity milDetailsEntity, int position) {
                holder.setText(R.id.md_tv1_01, milDetailsEntity.getName());
                if(milDetailsEntity.getFinsh().equals("1")){
                    holder.setText(R.id.md_tv1_02, "未完成");
                }else if(milDetailsEntity.getFinsh().equals("2")){
                    holder.setText(R.id.md_tv1_02, "已完成");
                }
            }


        };
        adduseList.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View itemView, Object itemBean, int position) {

            }
        });

    }

    @Override
    public void initData() {

    }



    @OnClick(R.id.tv_btn)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_btn:
                RecommendUiGoto.increase(this);//添加资金用途
                break;


        }
    }
}
