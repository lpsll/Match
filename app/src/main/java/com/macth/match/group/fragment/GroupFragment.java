package com.macth.match.group.fragment;

import android.view.View;

import com.macth.match.AppConfig;
import com.macth.match.AppContext;
import com.macth.match.R;
import com.macth.match.common.base.BaseFragment;
import com.macth.match.common.dto.BaseDTO;
import com.macth.match.common.http.CallBack;
import com.macth.match.common.http.CommonApiClient;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.recommend.entity.AddItemListResult;

/**
 * 群组
 */
public class GroupFragment extends BaseFragment {
    @Override
    protected void retry() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.base_fragment;
    }

    @Override
    public void initView(View view) {


    }

    private void reqGroup() {
        BaseDTO dto=new BaseDTO();
        dto.setUserid(AppContext.get("usertoken",""));
        CommonApiClient.group(getActivity(), dto, new CallBack<AddItemListResult>() {
            @Override
            public void onSuccess(AddItemListResult result) {
                if(AppConfig.SUCCESS.equals(result.getCode())){
                    LogUtils.e("获取用户群成功");
                }
            }
        });
    }

    @Override
    public void initData() {
        reqGroup();
    }
}
