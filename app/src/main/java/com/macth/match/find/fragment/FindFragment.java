package com.macth.match.find.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.macth.match.R;
import com.macth.match.common.base.BaseFragment;
import com.macth.match.find.fragment.adapter.FindAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by John_Libo on 2016/8/22.
 */
public class FindFragment extends BaseFragment {

    @Bind(R.id.recyclerview_find)
    RecyclerView recyclerviewFind;

    private FindAdapter findAdapter;

    @Override
    protected void retry() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_find;
    }

    @Override
    public void initView(View view) {

        recyclerviewFind.setLayoutManager(new LinearLayoutManager(getContext()));
        //设置adapter
        findAdapter = new FindAdapter(getContext());
        recyclerviewFind.setAdapter(findAdapter);

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
