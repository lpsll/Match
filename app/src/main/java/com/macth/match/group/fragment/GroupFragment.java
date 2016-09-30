package com.macth.match.group.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.macth.match.AppConfig;
import com.macth.match.AppContext;
import com.macth.match.R;
import com.macth.match.common.base.BaseApplication;
import com.macth.match.common.base.BaseFragment;
import com.macth.match.common.base.BasePullScrollViewFragment;
import com.macth.match.common.base.BasePullScrollViewSearchViewFragment;
import com.macth.match.common.dto.BaseDTO;
import com.macth.match.common.http.CallBack;
import com.macth.match.common.http.CommonApiClient;
import com.macth.match.common.utils.DialogUtils;
import com.macth.match.common.utils.ImageLoaderUtils;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.common.widget.ClearEditText;
import com.macth.match.common.widget.EmptyLayout;
import com.macth.match.common.widget.FullyLinearLayoutManager;
import com.macth.match.common.widget.SearchView;
import com.macth.match.group.GroupUiGoto;
import com.macth.match.group.adapter.SortAdapter;
import com.macth.match.group.entity.GroupEntity;
import com.macth.match.group.entity.GroupResult;
import com.macth.match.recommend.RecommendUiGoto;
import com.macth.match.recommend.entity.RecommendEntity;
import com.qluxstory.ptrrecyclerview.BaseRecyclerAdapter;
import com.qluxstory.ptrrecyclerview.BaseRecyclerViewHolder;
import com.qluxstory.ptrrecyclerview.BaseSimpleRecyclerAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

/**
 * 群组列表
 */
public class GroupFragment extends BaseFragment {
    @Bind(R.id.group_et)
    EditText mEroupEt;
    @Bind(R.id.group_list)
    ListView groupList;
    List<GroupEntity> entity;
    private SortAdapter sortadapter;
    int flag;


    @Override
    protected void retry() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_group;
    }


    private void reqGroup() {
        BaseDTO dto = new BaseDTO();
        dto.setUserid(AppContext.get("usertoken", ""));
        CommonApiClient.group(getActivity(), dto, new CallBack<GroupResult>() {
            @Override
            public void onSuccess(GroupResult result) {
                if (AppConfig.SUCCESS.equals(result.getCode())) {
                    AppContext.getInstance().setGroupEntityList(result.getData());
                    LogUtils.e("获取用户群成功");
                    entity = result.getData();
                    sortadapter = new SortAdapter(getActivity(), result.getData());
                    groupList.setAdapter(sortadapter);
                }
            }
        });
    }


    @Override
    public void initView(View view) {
        groupList.setSelected(true);
        mEroupEt.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                LogUtils.e("s----",""+s.toString());
                filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        groupList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AppContext.set("groupname",entity.get(position).getGroupname());
                RongIM.getInstance().startGroupChat(getActivity(),
                        entity.get(position).getGroupid(), entity.get(position).getGroupname());
            }
        });
    }

    @Override
    public void initData() {
        reqGroup();
    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     * @param filterStr
     */
    private void filterData(String filterStr) {
        LogUtils.e("filterStr----",""+filterStr);
        if (TextUtils.isEmpty(filterStr)) {
            return;

        }else {
            for(int i =0;i<entity.size();i++){
                if(entity.get(i).getGroupname().contains(filterStr)){
                    flag = i;
                    LogUtils.e("flag----",""+flag);
                    groupList.setSelection(flag);
                    sortadapter.notifyDataSetInvalidated();
                    return;
                }
            }
        }

    }
}
