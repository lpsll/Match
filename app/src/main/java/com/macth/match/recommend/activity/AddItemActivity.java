package com.macth.match.recommend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPopupWindow;
import com.macth.match.AppConfig;
import com.macth.match.R;
import com.macth.match.common.base.BaseTitleActivity;
import com.macth.match.common.http.CallBack;
import com.macth.match.common.http.CommonApiClient;
import com.macth.match.common.utils.DialogUtils;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.common.utils.TextViewUtils;
import com.macth.match.recommend.RecommendUiGoto;
import com.macth.match.recommend.dto.AddItemListDTO;
import com.macth.match.recommend.dto.ProjectDetailsDTO;
import com.macth.match.recommend.dto.SubmitDTO;
import com.macth.match.recommend.entity.AddItemListEntity;
import com.macth.match.recommend.entity.AddItemListResult;
import com.macth.match.recommend.entity.ProjectDetailsResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 新增项目页
 */
public class AddItemActivity extends BaseTitleActivity {
    @Bind(R.id.et01)
    EditText et01;
    @Bind(R.id.et02)
    EditText et02;
    @Bind(R.id.et03)
    TextView et03;
    @Bind(R.id.et04)
    TextView et04;
    @Bind(R.id.et05)
    TextView et05;
    @Bind(R.id.et06)
    EditText et06;
    @Bind(R.id.et07)
    EditText et07;
    @Bind(R.id.et08)
    TextView et08;
    @Bind(R.id.et09)
    EditText et09;
    @Bind(R.id.lin09)
    LinearLayout lin09;
    @Bind(R.id.et10)
    TextView et10;
    @Bind(R.id.lin10)
    LinearLayout lin10;
    @Bind(R.id.et11)
    TextView et11;
    @Bind(R.id.lin11)
    LinearLayout lin11;
    @Bind(R.id.et12)
    EditText et12;
    @Bind(R.id.lin12)
    LinearLayout lin12;
    @Bind(R.id.et13)
    EditText et13;
    @Bind(R.id.lin13)
    LinearLayout lin13;
    @Bind(R.id.et14)
    EditText et14;
    @Bind(R.id.lin14)
    LinearLayout lin14;
    @Bind(R.id.et15)
    EditText et15;
    @Bind(R.id.lin15)
    LinearLayout lin15;
    @Bind(R.id.et16)
    EditText et16;
    @Bind(R.id.lin16)
    LinearLayout lin16;
    @Bind(R.id.et17)
    TextView et17;
    @Bind(R.id.lin17)
    LinearLayout lin17;
    @Bind(R.id.tv_btn)
    TextView tvBtn;
    @Bind(R.id.et18)
    EditText et18;
    @Bind(R.id.lin18)
    LinearLayout lin18;
    private String mProvince,mCity,mArea;
    int type;
    List<AddItemListEntity> mListEntity;

    @Override
    protected int getContentResId() {
        return R.layout.activity_additem;
    }

    @Override
    public void initView() {
        setTitleText("新增项目");
        setEnsureText("提交");

    }

    @Override
    public void initData() {

    }




    @OnClick({R.id.et02, R.id.et03, R.id.et04, R.id.et05, R.id.et08, R.id.et10, R.id.et11,R.id.et17, R.id.tv_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et02:
                et02.setInputType(InputType.TYPE_DATETIME_VARIATION_NORMAL);
                RecommendUiGoto.city(this);
                break;
            case R.id.et03:
                RecommendUiGoto.city(this);
                break;
            case R.id.et04:
                type =1;
                reqList();//获取下拉框列表
                break;
            case R.id.et05:
                type =2;
                reqList();
                break;
            case R.id.et08:
                showTrustPop();
                break;
            case R.id.et10:
                type =3;
                reqList();
                break;
            case R.id.et11:
                type =4;
                reqList();
                break;
            case R.id.et17:
                type =5;
                reqList();
                break;
            case R.id.tv_btn:
                RecommendUiGoto.increase(this);
                break;
            case R.id.base_titlebar_back:
                baseGoBack();
                break;
            case R.id.base_titlebar_ensure:
                if(TextUtils.isEmpty(et01.getText().toString())){
                    DialogUtils.showPrompt(this, "提示","公司名称不能为空", "知道了");
                }
                else if(TextUtils.isEmpty(et02.getText().toString())){
                    DialogUtils.showPrompt(this, "提示","省份不能为空", "知道了");
                }
                else if(TextUtils.isEmpty(et03.getText().toString())){
                    DialogUtils.showPrompt(this, "提示","地市不能为空", "知道了");
                }
                else if(TextUtils.isEmpty(et04.getText().toString())){
                    DialogUtils.showPrompt(this, "提示","项目类型不能为空", "知道了");
                }
                else if(TextUtils.isEmpty(et05.getText().toString())){
                    DialogUtils.showPrompt(this, "提示","企业性质不能为空", "知道了");
                }
                else if(TextUtils.isEmpty(et06.getText().toString())){
                    DialogUtils.showPrompt(this, "提示","项目金额不能为空", "知道了");
                }
                else if(TextUtils.isEmpty(et07.getText().toString())){
                    DialogUtils.showPrompt(this, "提示","期限不能为空", "知道了");
                }
                else if(TextUtils.isEmpty(et08.getText().toString())){
                    DialogUtils.showPrompt(this, "提示","增信措施不能为空", "知道了");
                }
                else if(TextUtils.isEmpty(et09.getText().toString())){
                    DialogUtils.showPrompt(this, "提示","债券评级不能为空", "知道了");
                }
                else if(TextUtils.isEmpty(et10.getText().toString())){
                    DialogUtils.showPrompt(this, "提示","综合成本不能为空", "知道了");
                }
                else {
                    reqSubmit();//提交
                }

                break;
        }
    }

    private void reqSubmit() {
        SubmitDTO dto=new SubmitDTO();
        dto.setCompanyname(et01.getText().toString());
        CommonApiClient.submit(this, dto, new CallBack<AddItemListResult>() {
            @Override
            public void onSuccess(AddItemListResult result) {
                if(AppConfig.SUCCESS.equals(result.getCode())){
                    LogUtils.e("提交项目成功");
                    finish();

                }

            }
        });
    }

    private void reqList() {
        AddItemListDTO dto=new AddItemListDTO();
        if(type==1){
            dto.setConstantType("项目类型");
        }
        if(type==2){
            dto.setConstantType("企业性质");
        }
        if(type==3){
            dto.setConstantType("项目评级");
        }
        if(type==4){
            dto.setConstantType("主体评级");
        }
        if(type==5){
            dto.setConstantType("债券评级");
        }

        CommonApiClient.addList(this, dto, new CallBack<AddItemListResult>() {
            @Override
            public void onSuccess(AddItemListResult result) {
                if(AppConfig.SUCCESS.equals(result.getCode())){
                    LogUtils.e("获取项目下拉框列表成功");
                    mListEntity = result.getData();
                    showPop();
                }

            }
        });
    }

    String[] str = new String[]{"担保","抵押","人大决议","财政承诺","其它"};
    ArrayList<String>  tList ;

    private void showTrustPop() {
        tList = new ArrayList<>();
        for(int i = 0;i<str.length;i++){
            tList.add(str[i]);
        }
        OptionsPopupWindow tipPopup = new OptionsPopupWindow(this);
        tipPopup.setPicker(tList);//设置里面list
        tipPopup.setOnoptionsSelectListener(new OptionsPopupWindow.OnOptionsSelectListener() {//确定的点击监听
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                String str = tList.get(options1);
                if(str.equals("担保")){
                    et08.setText(tList.get(options1));
                    lin09.setVisibility(View.VISIBLE);
                    lin10.setVisibility(View.VISIBLE);
                    lin11.setVisibility(View.VISIBLE);
                    lin12.setVisibility(View.GONE);
                    lin13.setVisibility(View.GONE);
                    lin14.setVisibility(View.GONE);
                    lin15.setVisibility(View.GONE);
                    lin16.setVisibility(View.GONE);
                }
                if(str.equals("抵押")){
                    et08.setText(tList.get(options1));
                    lin09.setVisibility(View.GONE);
                    lin10.setVisibility(View.GONE);
                    lin11.setVisibility(View.GONE);
                    lin12.setVisibility(View.VISIBLE);
                    lin13.setVisibility(View.VISIBLE);
                    lin14.setVisibility(View.GONE);
                    lin15.setVisibility(View.GONE);
                    lin16.setVisibility(View.GONE);
                }
                if(str.equals("人大决议")){
                    et08.setText(tList.get(options1));
                    lin09.setVisibility(View.GONE);
                    lin10.setVisibility(View.GONE);
                    lin11.setVisibility(View.GONE);
                    lin12.setVisibility(View.GONE);
                    lin13.setVisibility(View.GONE);
                    lin14.setVisibility(View.VISIBLE);
                    lin15.setVisibility(View.VISIBLE);
                    lin16.setVisibility(View.GONE);
                }
                if(str.equals("财政承诺")){
                    et08.setText(tList.get(options1));
                    lin09.setVisibility(View.GONE);
                    lin10.setVisibility(View.GONE);
                    lin11.setVisibility(View.GONE);
                    lin12.setVisibility(View.GONE);
                    lin13.setVisibility(View.GONE);
                    lin14.setVisibility(View.VISIBLE);
                    lin15.setVisibility(View.VISIBLE);
                    lin16.setVisibility(View.GONE);
                }
                if(str.equals("其它")){
                    et08.setText(tList.get(options1));
                    lin09.setVisibility(View.GONE);
                    lin10.setVisibility(View.GONE);
                    lin11.setVisibility(View.GONE);
                    lin12.setVisibility(View.GONE);
                    lin13.setVisibility(View.GONE);
                    lin14.setVisibility(View.GONE);
                    lin15.setVisibility(View.GONE);
                    lin16.setVisibility(View.VISIBLE);
                }

            }
        });
        tipPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {//设置窗体消失后，屏幕恢复亮度
            @Override
            public void onDismiss() {
                closePopupWindow();
            }
        });
        tipPopup.showAtLocation(et07, Gravity.BOTTOM, 0, 0);//显示的位置
        //弹窗后背景变暗
        openPopupWindow();

    }

    ArrayList<String>  mList ;

    private void showPop() {
        mList = new ArrayList<>();
        for(int i = 0;i<mListEntity.size();i++){
            mList.add(mListEntity.get(i).getConstant_name());
        }
        OptionsPopupWindow tipPopup = new OptionsPopupWindow(this);
        tipPopup.setPicker(mList);//设置里面list
        tipPopup.setOnoptionsSelectListener(new OptionsPopupWindow.OnOptionsSelectListener() {//确定的点击监听
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                if(type==1){
                    et04.setText(mList.get(options1));
                }
                if(type==2){
                    et05.setText(mList.get(options1));
                }
                if(type==3){
                    et10.setText(mList.get(options1));
                }
                if(type==4){
                    et11.setText(mList.get(options1));
                }
                if(type==5){
                    et17.setText(mList.get(options1));
                }
            }
        });
        tipPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {//设置窗体消失后，屏幕恢复亮度
            @Override
            public void onDismiss() {
                closePopupWindow();
            }
        });
        tipPopup.showAtLocation(et07, Gravity.BOTTOM, 0, 0);//显示的位置
        //弹窗后背景变暗
        openPopupWindow();

    }

    /**
     *  打开窗口 
     */
    private void openPopupWindow() {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.7f;
        getWindow().setAttributes(params);
    }

    /**
     *  关闭窗口 
     */
    private void closePopupWindow() {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 1f;
        getWindow().setAttributes(params);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RecommendUiGoto.CITY_REQUEST:
                if(data ==null){
                    return;
                }else {
                    mProvince = data.getStringExtra("mCurrentProviceName");
                    mCity = data.getStringExtra("mCurrentCityName");
                    mArea = data.getStringExtra("mCurrentDistrictName");
                    et02.setText(mProvince);
                    et03.setText(mCity);
                }

                break;
            default:
                break;

        }
    }



}
