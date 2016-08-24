package com.macth.match.notice.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.macth.match.R;
import com.macth.match.common.base.BaseFragment;
import com.macth.match.notice.adapter.NoticeAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by John_Libo on 2016/8/22.
 * 公告页面
 */
public class NoticeFragment extends BaseFragment {


    @Bind(R.id.recyclerview_notice)
    RecyclerView recyclerviewNotice;

    private NoticeAdapter noticeAdapter;

    @Override
    protected void retry() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_notice;
    }

    @Override
    public void initView(View view) {

        recyclerviewNotice.setLayoutManager(new LinearLayoutManager(getContext()));
        //设置adapter
        noticeAdapter = new NoticeAdapter(getContext());
        recyclerviewNotice.setAdapter(noticeAdapter);

    }

    @Override
    public void initData() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
