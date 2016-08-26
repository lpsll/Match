package com.macth.match.recommend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.macth.match.AppConfig;
import com.macth.match.AppContext;
import com.macth.match.R;
import com.macth.match.common.base.BaseTitleActivity;
import com.macth.match.common.http.CallBack;
import com.macth.match.common.http.CommonApiClient;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.recommend.dto.FundsDTO;
import com.macth.match.recommend.dto.MinestoneDetailsDTO;
import com.macth.match.recommend.entity.FundsResult;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 里程碑详情页
 */
public class MilestoneDetailsActivity extends BaseTitleActivity {
    @Bind(R.id.md_tv1_01)
    TextView mMdTv101;
    @Bind(R.id.md_tv1_02)
    TextView mMdTv102;
    @Bind(R.id.md_tv1_03)
    TextView mMdTv103;
    @Bind(R.id.md_tv1_04)
    TextView mMdTv104;
    @Bind(R.id.md_tv2_01)
    TextView mMdTv201;
    @Bind(R.id.md_tv2_02)
    TextView mMdTv202;
    @Bind(R.id.md_tv2_03)
    TextView mMdTv203;
    @Bind(R.id.md_tv2_04)
    TextView mMdTv204;
    @Bind(R.id.md_tv3_01)
    TextView mMdTv301;
    @Bind(R.id.md_tv3_02)
    TextView mMdTv302;
    @Bind(R.id.md_tv3_03)
    TextView mMdTv303;
    @Bind(R.id.md_tv3_04)
    TextView mMdTv304;
    @Bind(R.id.md_tv4_01)
    TextView mMdTv401;
    @Bind(R.id.md_tv4_02)
    TextView mMdTv402;
    @Bind(R.id.md_tv4_03)
    TextView mMdTv403;
    @Bind(R.id.md_tv4_04)
    TextView mMdTv404;
    @Bind(R.id.md_tv5_01)
    TextView mMdTv501;
    @Bind(R.id.md_tv5_02)
    TextView mMdTv502;
    @Bind(R.id.md_tv5_03)
    TextView mMdTv503;
    @Bind(R.id.md_tv5_04)
    TextView mMdTv504;
    @Bind(R.id.md_tv6_01)
    TextView mMdTv601;
    @Bind(R.id.md_tv6_02)
    TextView mMdTv602;
    @Bind(R.id.md_tv6_03)
    TextView mMdTv603;
    @Bind(R.id.md_tv6_04)
    TextView mMdTv604;
    @Bind(R.id.md_tv7_01)
    TextView mMdTv701;
    @Bind(R.id.md_tv7_02)
    TextView mMdTv702;
    @Bind(R.id.md_tv7_03)
    TextView mMdTv703;
    @Bind(R.id.md_tv7_04)
    TextView mMdTv704;
    private String mProjectID;

    @Override
    protected int getContentResId() {
        return R.layout.activity_milestonedetails;
    }

    @Override
    public void initView() {
        setTitleText("里程碑详情");
        Intent intent = getIntent();
        mProjectID = intent.getBundleExtra("bundle").getString("projectID");
        LogUtils.e("mProjectID---",""+mProjectID);

    }

    @Override
    public void initData() {

        reqMilDetails();
    }

    private void reqMilDetails() {
        MinestoneDetailsDTO dto = new MinestoneDetailsDTO();
        dto.setCooperativeID(AppContext.get("cooperativeid",""));
        dto.setProjectID(mProjectID);
        CommonApiClient.milestoneDetails(this, dto, new CallBack<FundsResult>() {
            @Override
            public void onSuccess(FundsResult result) {
                if (AppConfig.SUCCESS.equals(result.getCode())) {
                    LogUtils.e("获取项目资金用途详情成功");

                }

            }
        });
    }


    @OnClick({R.id.md_tv1_03, R.id.md_tv1_04, R.id.md_tv2_03, R.id.md_tv2_04, R.id.md_tv3_03, R.id.md_tv3_04, R.id.md_tv4_03, R.id.md_tv4_04, R.id.md_tv5_03, R.id.md_tv5_04, R.id.md_tv6_03, R.id.md_tv6_04, R.id.md_tv7_03, R.id.md_tv7_04})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.md_tv1_03:
                break;
            case R.id.md_tv1_04:
                break;
            case R.id.md_tv2_03:
                break;
            case R.id.md_tv2_04:
                break;
            case R.id.md_tv3_03:
                break;
            case R.id.md_tv3_04:
                break;
            case R.id.md_tv4_03:
                break;
            case R.id.md_tv4_04:
                break;
            case R.id.md_tv5_03:
                break;
            case R.id.md_tv5_04:
                break;
            case R.id.md_tv6_03:
                break;
            case R.id.md_tv6_04:
                break;
            case R.id.md_tv7_03:
                break;
            case R.id.md_tv7_04:
                break;
            case R.id.base_titlebar_back:
                baseGoBack();
                break;
        }
    }
}
