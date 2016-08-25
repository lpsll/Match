package com.macth.match.recommend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.macth.match.R;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.wheel.widget.OnWheelChangedListener;
import com.macth.match.wheel.widget.WheelView;
import com.macth.match.wheel.widget.adapters.ArrayWheelAdapter;


/**
 * 选择城市列表页
 */
public class ChoiceCityActivity extends WheelAcitivity implements View.OnClickListener, OnWheelChangedListener {
    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;
    private TextView mBtnConfirm;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choicecity);
        setUpViews();
        setUpListener();
        setUpData();
    }

    private void setUpViews() {

        mViewProvince = (WheelView) findViewById(R.id.id_province);
        mViewCity = (WheelView) findViewById(R.id.id_city);
        mViewDistrict = (WheelView) findViewById(R.id.id_district);
        mBtnConfirm = (TextView) findViewById(R.id.btn_confirm);
    }
    private void setUpListener() {
        // 添加change事件
        mViewProvince.addChangingListener(this);
        // 添加change事件
        mViewCity.addChangingListener(this);
        // 添加change事件
        mViewDistrict.addChangingListener(this);
        // 添加onclick事件
        mBtnConfirm.setOnClickListener(this);
    }

    private void setUpData() {
        initProvinceDatas();
        LogUtils.e("mProvinceDatas",""+mProvinceDatas);
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(ChoiceCityActivity.this, mProvinceDatas));
        // 设置可见条目数量
        mViewProvince.setVisibleItems(7);
        mViewCity.setVisibleItems(7);
        mViewDistrict.setVisibleItems(7);
        updateCities();
        updateAreas();
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == mViewProvince) {
            updateCities();
        } else if (wheel == mViewCity) {
            updateAreas();
        } else if (wheel == mViewDistrict) {
            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
            mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
        }
    }

    /**
     * 根据当前的市，更新区WheelView的信息
     */
    private void updateAreas() {
        int pCurrent = mViewCity.getCurrentItem();
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
        String[] areas = mDistrictDatasMap.get(mCurrentCityName);

        if (areas == null) {
            areas = new String[] { "" };
        }
        mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
        mViewDistrict.setCurrentItem(0);
    }

    /**
     * 根据当前的省，更新市WheelView的信息
     */
    private void updateCities() {
        int pCurrent = mViewProvince.getCurrentItem();
        LogUtils.e("pCurrent----",""+pCurrent);
        LogUtils.e("mProvinceDatas",""+mProvinceDatas);
        mCurrentProviceName = mProvinceDatas[pCurrent];
        LogUtils.e("mCurrentProviceName----",""+mCurrentProviceName);
        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
        if (cities == null) {
            cities = new String[] { "" };
        }
        mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(this, cities));
        mViewCity.setCurrentItem(0);
        updateAreas();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm:
                Intent intent = new Intent();
                intent.putExtra("mCurrentProviceName",mCurrentProviceName);
                intent.putExtra("mCurrentCityName",mCurrentCityName);
                intent.putExtra("mCurrentDistrictName",mCurrentDistrictName);
                setResult(10,intent);
                finish();
                break;
            default:
                break;
        }
    }


}
