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
import com.macth.match.AppContext;
import com.macth.match.R;
import com.macth.match.common.base.BaseTitleActivity;
import com.macth.match.common.base.SimplePage;
import com.macth.match.common.http.CallBack;
import com.macth.match.common.http.CommonApiClient;
import com.macth.match.common.utils.DialogUtils;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.common.utils.UIHelper;
import com.macth.match.recommend.RecommendUiGoto;
import com.macth.match.recommend.dto.AddItemListDTO;
import com.macth.match.recommend.dto.SubmitDTO;
import com.macth.match.recommend.entity.AddItemListEntity;
import com.macth.match.recommend.entity.AddItemListResult;
import com.macth.match.recommend.entity.SubmitResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 新增项目页(暂时不用，用h5)
 */
public class AddItemActivity extends BaseTitleActivity {
    @Bind(R.id.et01)
    EditText et01;
    @Bind(R.id.et02)
    TextView et02;
    @Bind(R.id.et03)
    TextView et03;
    @Bind(R.id.et04)
    TextView et04;
    @Bind(R.id.et05)
    TextView et05;
    @Bind(R.id.et06)
    EditText et06;
    @Bind(R.id.et07)
    TextView et07;
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
    int type,mTimeType;
    List<AddItemListEntity> mListEntity;
    private String mType;

    @Override
    protected int getContentResId() {
        return R.layout.activity_additem;
    }

    @Override
    public void initView() {
        setTitleText("新增项目");
        if(null ==AppContext.get("useridentity","")){
            et18.setVisibility(View.GONE);
        }else {
            et18.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void initData() {

    }




    @OnClick({R.id.et02, R.id.et03, R.id.et04, R.id.et05,R.id.et07, R.id.et08, R.id.et10, R.id.et11,R.id.et17, R.id.tv_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et02:
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
            case R.id.et07:
                mTimeType =1;
                showTimePop();
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
            case R.id.base_titlebar_back:
                baseGoBack();
                break;
            case R.id.tv_btn:
                if(TextUtils.isEmpty(et01.getText().toString())){
                    DialogUtils.showPrompt(this, "提示","公司名称不能为空", "知道了");
                }
                else if(et02.getText().toString().equals("请选择您所在的省份")){
                    DialogUtils.showPrompt(this, "提示","省份不能为空", "知道了");
                }
                else if(et03.getText().toString().equals("请选择您所在的地市")){
                    DialogUtils.showPrompt(this, "提示","地市不能为空", "知道了");
                }
                else if(et04.getText().toString().equals("请选择项目类型")){
                    DialogUtils.showPrompt(this, "提示","项目类型不能为空", "知道了");
                }
                else if(et05.getText().toString().equals("请选择企业性质")){
                    DialogUtils.showPrompt(this, "提示","企业性质不能为空", "知道了");
                }
                else if(TextUtils.isEmpty(et06.getText().toString())){
                    DialogUtils.showPrompt(this, "提示","项目金额不能为空", "知道了");
                }
                else if(et07.getText().toString().equals("请选择期限")){
                    DialogUtils.showPrompt(this, "提示","期限不能为空", "知道了");
                }
                else if(et08.getText().toString().equals("请选择增信措施")){
                    DialogUtils.showPrompt(this, "提示","增信措施不能为空", "知道了");
                }
                else if(et08.getText().toString().equals("担保")){
                    mType = "1";
                    if(TextUtils.isEmpty(et09.getText().toString())){
                        DialogUtils.showPrompt(this, "提示","担保公司不能为空", "知道了");
                    }
                    else if(et10.getText().toString().equals("请输入项目评级")){
                        DialogUtils.showPrompt(this, "提示","项目评级不能为空", "知道了");
                    }
                    else if(et11.getText().toString().equals("请输入主题评级")){
                        DialogUtils.showPrompt(this, "提示","主题评级不能为空", "知道了");
                    }
                    else if(et17.getText().toString().equals("请选择债券评级")){
                    DialogUtils.showPrompt(this, "提示","债券评级不能为空", "知道了");
                    }
                    else if(et18.getVisibility()== View.VISIBLE &&TextUtils.isEmpty(et18.getText().toString())){
                        DialogUtils.showPrompt(this, "提示","综合成本不能为空", "知道了");
                    }
                    else {
                        LogUtils.e("tj---","tj");
                        reqSubmit();//提交
                    }

                }
                else if(et08.getText().toString().equals("抵押")){
                    mType = "2";
                    LogUtils.e("et12----",""+et12.getText().toString());
                    if(TextUtils.isEmpty(et12.getText().toString())){
                        DialogUtils.showPrompt(this, "提示","评估价值不能为空", "知道了");
                    }
                    else if(TextUtils.isEmpty(et13.getText().toString())){
                        DialogUtils.showPrompt(this, "提示","介绍不能为空", "知道了");
                    }
                    else if(et17.getText().toString().equals("请选择债券评级")){
                    DialogUtils.showPrompt(this, "提示","债券评级不能为空", "知道了");
                    }
                    else if(et18.getVisibility()== View.VISIBLE &&TextUtils.isEmpty(et18.getText().toString())){
                        DialogUtils.showPrompt(this, "提示","综合成本不能为空", "知道了");
                    }
                    else {
                        LogUtils.e("tj---","tj");
                        reqSubmit();//提交
                    }

                }
                else if(et08.getText().toString().equals("人大决议")){
                    mType = "3";
                    if(TextUtils.isEmpty(et14.getText().toString())){
                        DialogUtils.showPrompt(this, "提示","是/否不能为空", "知道了");
                    }
                    else if(TextUtils.isEmpty(et15.getText().toString())){
                        DialogUtils.showPrompt(this, "提示","哪一级政府不能为空", "知道了");
                    }
                    else if(et17.getText().toString().equals("请选择债券评级")){
                    DialogUtils.showPrompt(this, "提示","债券评级不能为空", "知道了");
                    }
                    else if(et18.getVisibility()== View.VISIBLE &&TextUtils.isEmpty(et18.getText().toString())){
                        DialogUtils.showPrompt(this, "提示","综合成本不能为空", "知道了");
                    }
                    else {
                        LogUtils.e("tj---","tj");
                        reqSubmit();//提交
                    }

                }
                else if(et08.getText().toString().equals("财政承诺")){
                    mType = "4";
                    if(TextUtils.isEmpty(et14.getText().toString())){
                        DialogUtils.showPrompt(this, "提示","是/否不能为空", "知道了");
                    }
                    else if(TextUtils.isEmpty(et15.getText().toString())){
                        DialogUtils.showPrompt(this, "提示","哪一级政府不能为空", "知道了");
                    }
                    else if(et17.getText().toString().equals("请选择债券评级")){
                    DialogUtils.showPrompt(this, "提示","债券评级不能为空", "知道了");
                    }
                    else if(et18.getVisibility()== View.VISIBLE &&TextUtils.isEmpty(et18.getText().toString())){
                        DialogUtils.showPrompt(this, "提示","综合成本不能为空", "知道了");
                    }
                    else {
                        LogUtils.e("tj---","tj");
                        reqSubmit();//提交
                    }

                }

                else if(et08.getText().toString().equals("其它")){
                    mType = "5";
                    if(TextUtils.isEmpty(et16.getText().toString())){
                        DialogUtils.showPrompt(this, "提示","其他说明不能为空", "知道了");
                    }
                    else if(et17.getText().toString().equals("请选择债券评级")){
                    DialogUtils.showPrompt(this, "提示","债券评级不能为空", "知道了");
                    }
                    else if(et18.getVisibility()== View.VISIBLE &&TextUtils.isEmpty(et18.getText().toString())){
                        DialogUtils.showPrompt(this, "提示","综合成本不能为空", "知道了");
                    }
                    else {
                        LogUtils.e("tj---","tj");
                        reqSubmit();//提交
                    }

                }



                break;
        }
    }

    private void reqSubmit() {
        SubmitDTO dto=new SubmitDTO();
        dto.setUserid(AppContext.get("usertoken",""));
        dto.setCompanyname(et01.getText().toString());
        dto.setS_province(et02.getText().toString());
        dto.setS_city(et03.getText().toString());
        dto.setProjecttype(et04.getText().toString());
        dto.setNature(et05.getText().toString());
        dto.setPrice(et06.getText().toString());
        dto.setTermunit(mTerm);
        dto.setTermvalue(time);
        dto.setSincerity(et08.getText().toString());
        dto.setBond(et17.getText().toString());
        dto.setCost(et18.getText().toString());
        dto.setPromote("2");
        if(mType.equals("1")){
            dto.setSincerity("1");
            dto.setDbcompanyname(et09.getText().toString());
            dto.setDbitemrate(et10.getText().toString());
            dto.setDbassure_ztrate(et11.getText().toString());
        }
        else if(mType.equals("2")){
            dto.setSincerity("2");
            dto.setDyratevalue(et12.getText().toString());
            dto.setDydesc(et13.getText().toString());
        }
        else if(mType.equals("3")){
            dto.setSincerity("3");
            if(et15.getText().toString().equals("是")){
                dto.setRest("2");
            }else if(et15.getText().toString().equals("否")){
                dto.setRest("1");
            }
            dto.setGovernment(et15.getText().toString());
        }
        else if(mType.equals("4")){
            dto.setSincerity("4");
            if(et15.getText().toString().equals("是")){
                dto.setRest("2");
            }else if(et15.getText().toString().equals("否")){
                dto.setRest("1");
            }

            dto.setGovernment(et15.getText().toString());
        }
        else if(mType.equals("5")){
            dto.setSincerity("5");

            dto.setQtdesc(et16.getText().toString());
        }

        CommonApiClient.submit(this, dto, new CallBack<SubmitResult>() {
            @Override
            public void onSuccess(SubmitResult result) {
                if(AppConfig.SUCCESS.equals(result.getCode())){
                    LogUtils.e("提交项目成功");
                    Bundle b = new Bundle();
                    b.putString("pid", result.getData().getProjectno());
                    b.putString("flag","1");
                    UIHelper.showBundleFragment(AddItemActivity.this, SimplePage.ADD_USE,b);//增加资金用途
                }
            }
        });
    }

    private void reqList() {
        AddItemListDTO dto=new AddItemListDTO();
        if(type==1){
            dto.setConstantType("2");
        }
        if(type==2){
            dto.setConstantType("3");
        }
        if(type==3){
            dto.setConstantType("7");
        }
        if(type==4){
            dto.setConstantType("6");
        }
        if(type==5){
            dto.setConstantType("1");
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
    ArrayList<String>  mTimeList ;
    String[] data;
    private String time,mTerm;
    private void showTimePop() {
        mTimeList = new ArrayList<>();
        if(mTimeType==1){
             data = getResources().getStringArray(R.array.term);
        }
        else if(mTimeType==2){
            data = getResources().getStringArray(R.array.year);
        }
        else if(mTimeType==3){
            data = getResources().getStringArray(R.array.month);
        }

        for(int i = 0;i<data.length;i++){
            mTimeList.add(data[i]);
        }
        OptionsPopupWindow tipPopup = new OptionsPopupWindow(this);
        tipPopup.setPicker(mTimeList);//设置里面list
        tipPopup.setOnoptionsSelectListener(new OptionsPopupWindow.OnOptionsSelectListener() {//确定的点击监听
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                if(mTimeType==1){
                    if(mTimeList.get(options1).equals("年")){
                        mTimeType =2;
                    }else if(mTimeList.get(options1).equals("月")){
                        mTimeType =3;
                    }
                    showTimePop();
                }
                else {
                    et07.setText(mTimeList.get(options1));
                    if(mTimeList.get(options1).contains("年")){
                        mTerm="1";
                        time =mTimeList.get(options1).replace("年","");
                        LogUtils.e("time---",""+time);
                    }
                    else if(mTimeList.get(options1).contains("月")){
                        mTerm="2";
                        time =mTimeList.get(options1).replace("个月","");
                        LogUtils.e("time---",""+time);
                    }

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
