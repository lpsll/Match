package com.macth.match.recommend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.macth.match.AppConfig;
import com.macth.match.R;
import com.macth.match.common.base.BaseTitleActivity;
import com.macth.match.common.http.CallBack;
import com.macth.match.common.http.CommonApiClient;
import com.macth.match.common.utils.ImageLoaderUtils;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.recommend.dto.FundsDTO;
import com.macth.match.recommend.entity.FundsResult;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 资金用途详情页
 */
public class DetailsFundsActivity extends BaseTitleActivity {
    @Bind(R.id.et_01)
    TextView et01;
    @Bind(R.id.et_02)
    TextView et02;
    @Bind(R.id.img_01)
    ImageView img01;
    @Bind(R.id.img_02)
    ImageView img02;
    @Bind(R.id.img_03)
    ImageView img03;
    private String mFundid;

    @Override
    protected int getContentResId() {
        return R.layout.activity_detailsfunds;
    }

    @Override
    public void initView() {
        setTitleText("资金用途详情");
        Intent intent = getIntent();
        mFundid = intent.getBundleExtra("bundle").getString("fundid");

    }

    @Override
    public void initData() {
        reqFunds();

    }

    private void reqFunds() {
        FundsDTO dto = new FundsDTO();
        dto.setFundid(mFundid);
        CommonApiClient.funds(this, dto, new CallBack<FundsResult>() {
            @Override
            public void onSuccess(FundsResult result) {
                if (AppConfig.SUCCESS.equals(result.getCode())) {
                    LogUtils.e("获取项目资金用途详情");
                    setResult(result);

                }

            }
        });
    }

    private void setResult(FundsResult result) {
        et01.setText(result.getData().getFunds_desc());
        et02.setText(result.getData().getFunds_companyaddrress());
        ImageLoaderUtils.displayImage(AppConfig.BASE_URL+result.getData().getFunds_images(), img01);
        ImageLoaderUtils.displayImage(result.getData().getImageurl()[0], img02);
    }


}
