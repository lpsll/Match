package com.macth.match.mine.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.macth.match.R;
import com.macth.match.common.base.BaseTitleActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 个人信息页
 */
public class PersonalInformationActivity extends BaseTitleActivity {
    @Bind(R.id.img_img)
    ImageView imgImg;
    @Bind(R.id.img_rel)
    RelativeLayout imgRel;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.name_rel)
    RelativeLayout nameRel;
    @Bind(R.id.phone_tv)
    TextView phoneTv;
    @Bind(R.id.user_tv)
    TextView userTv;
    @Bind(R.id.nm_tv)
    TextView nmTv;
    @Bind(R.id.post_tv)
    TextView postTv;
    @Bind(R.id.role_tv)
    TextView roleTv;

    @Override
    protected int getContentResId() {
        return R.layout.activity_personal;
    }

    @Override
    public void initView() {
        setTitleText("个人信息");

    }

    @Override
    public void initData() {

    }


    @OnClick({R.id.img_rel, R.id.name_rel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_rel:
                break;
            case R.id.name_rel:
                break;
        }
    }
}
