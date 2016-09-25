package com.macth.match.recommend.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.mapapi.map.MapView;
import com.macth.match.AppConfig;
import com.macth.match.R;
import com.macth.match.common.base.BaseTitleActivity;
import com.macth.match.common.http.CallBack;
import com.macth.match.common.http.CommonApiClient;
import com.macth.match.common.utils.DialogUtils;
import com.macth.match.common.utils.ImageLoaderUtils;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.recommend.dto.FundsDTO;
import com.macth.match.recommend.dto.UploadDTO;
import com.macth.match.recommend.entity.AddUseEvent;
import com.macth.match.recommend.entity.FundsResult;
import com.macth.match.recommend.entity.MilDetailsResult;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * 修改资金用途页
 */
public class ModifyCapitalActivity extends BaseTitleActivity {
    @Bind(R.id.et_01)
    EditText mEt01;
    @Bind(R.id.et_02)
    EditText mEt02;
    @Bind(R.id.bmapView)
    MapView mBmapView;
    @Bind(R.id.tv)
    TextView tv;
    @Bind(R.id.list)
    ListView list;
    private String fundsid;
    private ListAdapter listAdapter;

    @Override
    protected int getContentResId() {
        return R.layout.activity_increasecapital;

    }

    @Override
    public void initView() {
        tv.setVisibility(View.GONE);
        mBmapView.setVisibility(View.GONE);
        setEnsureText("完成");
        setTitleText("修改资金用途");
        fundsid = getIntent().getBundleExtra("bundle").getString("fundsid");
        reqFundsDetails();

    }

    private void reqFundsDetails() {
        FundsDTO dto = new FundsDTO();
        dto.setFundid(fundsid);
        CommonApiClient.funds(this, dto, new CallBack<FundsResult>() {
            @Override
            public void onSuccess(FundsResult result) {
                if (AppConfig.SUCCESS.equals(result.getCode())) {
                    LogUtils.e("获取资金用途详情成功");
                    setResult(result);

                }

            }
        });
    }

    private void setResult(FundsResult result) {
        mEt01.setText(result.getData().getFunds_desc());
        mEt02.setText(result.getData().getFunds_companyaddrress());
        listAdapter = new ListAdapter(result.getData().getImageurl());
        list.setAdapter(listAdapter);
        setListViewHeightBasedOnChildren(list);
    }

    /**
     * 动态设置ListView的高度
     *
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        if (listView == null) return;
        ListAdapter listAdapter = (ListAdapter) listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }


    @Override
    public void initData() {

    }


    @OnClick(R.id.bmapView)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.base_titlebar_ensure:
                if (TextUtils.isEmpty(mEt01.getText().toString())) {
                    DialogUtils.showPrompt(this, "提示", "项目介绍不能为空", "知道了");
                } else if (TextUtils.isEmpty(mEt02.getText().toString())) {
                    DialogUtils.showPrompt(this, "提示", "公司地址不能为空", "知道了");
                } else {
                    reqUpload();//上传资金用途
                }
                break;
            default:
                break;
        }
        super.onClick(v);
    }


    private void reqUpload() {
        UploadDTO dto = new UploadDTO();
        dto.setProjectno(fundsid);
        dto.setDesc(mEt01.getText().toString());
        dto.setAddress(mEt02.getText().toString());
//        dto.setPimg("");
        CommonApiClient.upMdCaoital(this, dto, new CallBack<MilDetailsResult>() {
            @Override
            public void onSuccess(MilDetailsResult result) {
                if (AppConfig.SUCCESS.equals(result.getCode())) {
                    LogUtils.e("修改项目资金用途成功");
                    EventBus.getDefault().post(
                            new AddUseEvent("ok"));
                    finish();

                }

            }
        });
    }


    private class ListAdapter extends BaseAdapter {
        private String[] listUrls;
        private LayoutInflater inflater;

        public ListAdapter(String[] listUrls) {
            this.listUrls = listUrls;

            inflater = LayoutInflater.from(ModifyCapitalActivity.this);
        }

        public int getCount() {
            return listUrls.length;
        }

        @Override
        public String getItem(int position) {
            return listUrls[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.item_increasecapital, parent, false);
                holder.image = (ImageView) convertView.findViewById(R.id.item_in_img);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            final String path = listUrls[position];
            if(position ==0){

            }else {
                LogUtils.e("path----", "" + path);
                if (TextUtils.isEmpty(path)) {
                    holder.image.setVisibility(View.GONE);
                } else {
                    ImageLoaderUtils.displayImage(path, holder.image);
                }
            }

            return convertView;
        }

        class ViewHolder {
            ImageView image;
        }
    }


}
