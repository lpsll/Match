package com.macth.match.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.macth.match.AppConfig;
import com.macth.match.AppContext;
import com.macth.match.R;
import com.macth.match.common.base.BaseTitleActivity;
import com.macth.match.common.dto.BaseDTO;
import com.macth.match.common.eventbus.ErrorEvent;
import com.macth.match.common.http.CallBack;
import com.macth.match.common.http.CommonApiClient;
import com.macth.match.common.utils.ImageLoaderUtils;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.mine.MineUIGoto;
import com.macth.match.mine.entity.InformationEntity;
import com.macth.match.mine.entity.InformationResult;
import com.macth.match.mine.entity.PicEvent;
import com.macth.match.mine.fragment.MineFragment;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

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

    InformationEntity data;

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
        reqInformation();

    }

    private void reqInformation() {
        BaseDTO dto = new BaseDTO();
        dto.setUserid(AppContext.get("usertoken", ""));
        CommonApiClient.information(this, dto, new CallBack<InformationResult>() {
            @Override
            public void onSuccess(InformationResult result) {
                if (AppConfig.SUCCESS.equals(result.getCode())) {
                    LogUtils.e("获取个人信息成功");
                    data = result.getData();
                    bindInformation();

                }

            }
        });
    }

    private void bindInformation() {
        if(!TextUtils.isEmpty(data.getUser_image())){
            ImageLoaderUtils.displayAvatarImage(data.getUser_image(), imgImg);
        }
        nameTv.setText(data.getUser_name());
        phoneTv.setText(data.getUser_mobile());
        userTv.setText(data.getUser_identityname());
        nmTv.setText(data.getUser_company());
        postTv.setText(data.getUser_work());
        roleTv.setText(data.getUser_cooperativename());
    }


    @OnClick({R.id.img_rel, R.id.name_rel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_rel:
                //跳转到修改信息页
                Bundle b = new Bundle();
                b.putSerializable("entity",data);
                MineUIGoto.gotoModifyinformation(this, b);
                break;
            case R.id.name_rel:
                //跳转到修改信息页
                Bundle bundle = new Bundle();
                bundle.putSerializable("entity",data);
                MineUIGoto.gotoModifyinformation(this,bundle);
                break;
            case R.id.base_titlebar_back:
                baseGoBack();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MineUIGoto.MF_REQUEST) {
            if(TextUtils.isEmpty(AppContext.get("userimager",""))){
                ImageLoaderUtils.displayAvatarImage(AppContext.get("userimager",""), imgImg);
            }
            nameTv.setText(AppContext.get("username",""));
            EventBus.getDefault().post(
                    new PicEvent("ok"));
        }
    }
}
