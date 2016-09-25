package com.macth.match.recommend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.macth.match.AppConfig;
import com.macth.match.R;
import com.macth.match.common.base.BaseTitleActivity;
import com.macth.match.common.http.CallBack;
import com.macth.match.common.http.CommonApiClient;
import com.macth.match.common.utils.ImageLoaderUtils;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.recommend.dto.FundsDTO;
import com.macth.match.recommend.entity.FundsResult;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
    @Bind(R.id.list)
    ListView list;
    private String mFundid;
    private ListAdapter listAdapter;

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
                    LogUtils.e("获取资金用途详情成功");
                    setResult(result);

                }

            }
        });
    }

    private void setResult(FundsResult result) {
        et01.setText(result.getData().getFunds_desc());
        et02.setText(result.getData().getFunds_companyaddrress());
        if(!TextUtils.isEmpty(result.getData().getFunds_images())){
            ImageLoaderUtils.displayImage(AppConfig.BASE_URL+result.getData().getFunds_images(), img01);
        }
        listAdapter = new ListAdapter(result.getData().getImageurl());
        list.setAdapter(listAdapter);
        setListViewHeightBasedOnChildren(list);

    }

    /**
     * 动态设置ListView的高度
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        if(listView == null) return;
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



    private class ListAdapter extends BaseAdapter {
        private String[] listUrls;
        private LayoutInflater inflater;

        public ListAdapter(String[] listUrls) {
            this.listUrls = listUrls;

            inflater = LayoutInflater.from(DetailsFundsActivity.this);
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
            LogUtils.e("path----",""+path);
            if (TextUtils.isEmpty(path)) {
                holder.image.setVisibility(View.GONE);
            } else {
                ImageLoaderUtils.displayImage(path, holder.image);
            }
            return convertView;
        }

        class ViewHolder {
            ImageView image;
        }
    }


}
