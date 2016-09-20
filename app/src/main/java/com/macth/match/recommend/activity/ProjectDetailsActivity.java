package com.macth.match.recommend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.macth.match.AppConfig;
import com.macth.match.AppContext;
import com.macth.match.R;
import com.macth.match.common.base.BaseTitleActivity;
import com.macth.match.common.base.SimplePage;
import com.macth.match.common.dto.BaseDTO;
import com.macth.match.common.http.CallBack;
import com.macth.match.common.http.CommonApiClient;
import com.macth.match.common.utils.DialogUtils;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.common.utils.TextViewUtils;
import com.macth.match.common.utils.UIHelper;
import com.macth.match.common.widget.EmptyLayout;
import com.macth.match.recommend.RecommendUiGoto;
import com.macth.match.recommend.dto.CooperativeDTO;
import com.macth.match.recommend.dto.ProjectDetailsDTO;
import com.macth.match.recommend.entity.AddItemListResult;
import com.macth.match.recommend.entity.ProjectDetailsData;
import com.macth.match.recommend.entity.ProjectDetailsEntity;
import com.macth.match.recommend.entity.ProjectDetailsListEntity;
import com.macth.match.recommend.entity.ProjectDetailsResult;
import com.macth.match.recommend.entity.RecommendResult;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 项目详情页
 */
public class ProjectDetailsActivity extends BaseTitleActivity {
    @Bind(R.id.pd_tv01)
    TextView mPdTv01;
    @Bind(R.id.pd_tv02)
    TextView mPdTv02;
    @Bind(R.id.et01)
    TextView mEt01;
    @Bind(R.id.et02)
    TextView mEt02;
    @Bind(R.id.et03)
    TextView mEt03;
    @Bind(R.id.et04)
    TextView mEt04;
    @Bind(R.id.et05)
    TextView mEt05;
    @Bind(R.id.et06)
    TextView mEt06;
    @Bind(R.id.et07)
    TextView mEt07;
    @Bind(R.id.et08)
    TextView mEt08;
    @Bind(R.id.et09)
    TextView mEt09;
    @Bind(R.id.et10)
    TextView mEt10;
    @Bind(R.id.tv09)
    TextView mTv09;
    @Bind(R.id.tv10)
    TextView mTv10;
    @Bind(R.id.lin09)
    LinearLayout lin09;
    @Bind(R.id.lin10)
    LinearLayout lin10;
    @Bind(R.id.tv_btn)
    TextView mTvBtn;
    @Bind(R.id.lin11)
    LinearLayout lin11;
    @Bind(R.id.tv11)
    TextView mTv11;
    @Bind(R.id.et11)
    TextView mEt11;
    private TextView mBaseEnsure;
    private String mPid;
    ProjectDetailsData data;
    List<ProjectDetailsListEntity> mEntity;
    boolean bool;

    @Override
    protected int getContentResId() {
        return R.layout.activity_projectdetails;
    }

    @Override
    public void initView() {
        setTitleText("项目详情");
        mBaseEnsure = (TextView) findViewById(R.id.base_titlebar_ensure);
        // 初始化返回按钮图片大小
        TextViewUtils.setTextViewIcon(this, mBaseEnsure, R.drawable.fenxiangxdpi_03,
                R.dimen.common_titlebar_right_width,
                R.dimen.common_titlebar_icon_height, TextViewUtils.DRAWABLE_LEFT);

        Intent intent = getIntent();
        mPid = intent.getBundleExtra("bundle").getString("pid");
        bool = AppContext.get("IS_LOGIN",false);
    }

    @Override
    public void initData() {
        reqProjectDetails();//获取项目详情

    }

    private void reqProjectDetails() {
        ProjectDetailsDTO dto=new ProjectDetailsDTO();
        dto.setPid(mPid);
        CommonApiClient.projectDetails(this, dto, new CallBack<ProjectDetailsResult>() {
            @Override
            public void onSuccess(ProjectDetailsResult result) {
                if(AppConfig.SUCCESS.equals(result.getCode())){
                    LogUtils.e("获取项目详情成功");
                    if(null!=result){
                        setData(result);
                    }


                }

            }
        });

    }

    private void setData(ProjectDetailsResult result) {
        data = result.getData();
        ProjectDetailsEntity entity = data.getProject_signceritynode();
        mEntity =data.getProject_fundslist();
        mEt01.setText(data.getProject_companyname());
        mEt02.setText(data.getProject_province());
        mEt03.setText(data.getProject_city());
        mEt04.setText(data.getProject_projecttype());
        mEt05.setText(data.getProject_nature());
        mEt06.setText(data.getProject_price());
        mEt07.setText(data.getProject_termunit());
        if(data.getProject_sincerity().equals("1")){
            mEt08.setText("担保");
            lin09.setVisibility(View.VISIBLE);
            lin10.setVisibility(View.VISIBLE);
            lin11.setVisibility(View.VISIBLE);
            mTv09.setText("担保公司");
            mTv10.setText("项目评级");
            mTv11.setText("主题评价");
            mEt09.setText(entity.getAssure_companyname());
            mEt10.setText(entity.getAssure_itemrate());
            mEt11.setText(entity.getAssure_ztrate());
        }
        if(data.getProject_sincerity().equals("2")){
            mEt08.setText("抵押");
            lin09.setVisibility(View.VISIBLE);
            lin10.setVisibility(View.VISIBLE);
            lin11.setVisibility(View.GONE);
            mTv09.setText("评估价格");
            mTv10.setText("介绍");
            mEt09.setText(entity.getMortgage_ratevalue());
            mEt10.setText(entity.getMortgage_desc());
        }
        if(data.getProject_sincerity().equals("3")){
            mEt08.setText("人大决议");
            lin09.setVisibility(View.VISIBLE);
            lin10.setVisibility(View.VISIBLE);
            lin11.setVisibility(View.GONE);
            mTv09.setText("是/否");
            mTv10.setText("哪一级政府");
            mEt09.setText(entity.getResolution_true());
            mEt10.setText(entity.getResolution_government());
        }
        if(data.getProject_sincerity().equals("4")){
            mEt08.setText("财承诺政");
            lin09.setVisibility(View.VISIBLE);
            lin10.setVisibility(View.VISIBLE);
            lin11.setVisibility(View.GONE);
            mTv09.setText("是/否");
            mTv10.setText("哪一级政府");
            mEt09.setText(entity.getResolution_true());
            mEt10.setText(entity.getResolution_government());
        }
        if(data.getProject_sincerity().equals("5")){
            mEt08.setText("其他");
            lin09.setVisibility(View.VISIBLE);
            lin10.setVisibility(View.GONE);
            lin11.setVisibility(View.GONE);
            mTv09.setText("其他说明");
            mEt09.setText(entity.getOther_desc());
        }

        if(null==mEntity){
            mTvBtn.setVisibility(View.GONE);
        }else {
            mTvBtn.setVisibility(View.VISIBLE);
        }


    }


    @OnClick({R.id.pd_tv01, R.id.pd_tv02, R.id.tv_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pd_tv01:
                if(bool){
                    reqCooperative();//申请协同作业
                }else {
                    RecommendUiGoto.gotoLogin(this);//登录
                }

                break;
            case R.id.pd_tv02:
                if(bool){
                    Bundle bundle = new Bundle();
                    bundle.putString("projectID",mPid);
                    UIHelper.showBundleFragment(this, SimplePage.MILESTONE_DETAILS,bundle);//里程碑详情
                }else {
                    RecommendUiGoto.gotoLogin(this);//登录
                }

                break;
            case R.id.tv_btn:
                Bundle b = new Bundle();
                b.putString("fundid",mEntity.get(0).getFundid());
                RecommendUiGoto.gotoDetailsFunds(this,b);//资金用途详情
                break;
            case R.id.base_titlebar_ensure:
                break;
            case R.id.base_titlebar_back:
                baseGoBack();
                break;
        }
    }

    private void reqCooperative() {
        CooperativeDTO dto=new CooperativeDTO();
        dto.setProjectID(mPid);
        dto.setUserID(AppContext.get("usertoken",""));
        CommonApiClient.cooperative(this, dto, new CallBack<AddItemListResult>() {
            @Override
            public void onSuccess(AddItemListResult result) {
                if(AppConfig.SUCCESS.equals(result.getCode())){
                    LogUtils.e("申请协同作业成功");
                    mPdTv01.setEnabled(false);
                    DialogUtils.showPrompt(ProjectDetailsActivity.this, "提示","申请成功！", "知道了");

                }

            }
        });
    }
}
