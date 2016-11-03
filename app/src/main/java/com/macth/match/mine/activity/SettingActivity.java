package com.macth.match.mine.activity;

import android.content.DialogInterface;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.macth.match.AppContext;
import com.macth.match.R;
import com.macth.match.common.base.BaseTitleActivity;
import com.macth.match.common.utils.DialogUtils;
import com.macth.match.common.utils.GetFileSizeUtil;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.common.utils.ToastUtils;
import com.macth.match.mine.MineUIGoto;
import com.nostra13.universalimageloader.cache.disc.DiskCache;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;

import butterknife.Bind;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.push.RongPushClient;
import io.rong.push.common.RongException;

public class SettingActivity extends BaseTitleActivity {


    @Bind(R.id.rl_setting_change_pwd)
    RelativeLayout rlSettingChangePwd;
    @Bind(R.id.tv_moresetting_cache)
    TextView tvMoresettingCache;
    @Bind(R.id.rl_setting_clear_cache)
    RelativeLayout rlSettingClearCache;
//    @Bind(R.id.togglebutton)
//    ToggleButton mToggle;

    @Override
    protected int getContentResId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initView() {
        setTitleText("设置");
        try {
            RongPushClient.checkManifest(this);
        } catch (RongException e) {
            e.printStackTrace();
        }
//        mToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView,
//                                         boolean isChecked) {
//                if(isChecked){
//                    AppContext.set("receiver","0");
//                }
//                else{
//                    AppContext.set("receiver","1");
//                }
//            }
//        });

    }

    /**
     * 接收未读消息的监听器。
     */
    private class MyReceiveUnreadCountChangedListener implements RongIM.OnReceiveUnreadCountChangedListener {

        /**
         * @param count           未读消息数。
         */
        @Override
        public void onMessageIncreased(int count) {

        }
    }

    @Override
    public void initData() {
        //获取缓存数据
        try {
            getCache();
        } catch (Exception e) {
            LogUtils.d("获取缓存异常=========="+e.getMessage());
            e.printStackTrace();
        }

    }



    @OnClick({R.id.rl_setting_change_pwd, R.id.rl_setting_clear_cache})
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.rl_setting_change_pwd:
                //跳转到修改密码页
                MineUIGoto.gotoChangePwd(SettingActivity.this);

                break;
//            case R.id.rlsetting_add_info:
//                //跳转到完善信息页
//                MineUIGoto.gotoAddInfo(SettingActivity.this);
//
//                break;
            case R.id.rl_setting_clear_cache:

                //清除缓存
                DialogUtils.confirm(SettingActivity.this, "确定清除缓存吗？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        clearCache();
                        //改变ui
                        tvMoresettingCache.setText("0KB");
                        ToastUtils.showShort(SettingActivity.this, "已清除数据");
                    }
                });

                break;
        }
    }

    /**
     *获取缓存大小
     */
    private void getCache() throws Exception {
        DiskCache diskCache = ImageLoader.getInstance().getDiskCache();
        File directory = diskCache.getDirectory();
        long fileSizes = GetFileSizeUtil.getInstance().getFileSize(directory);
        String size = GetFileSizeUtil.getInstance().FormetFileSize(fileSizes);
        LogUtils.d("缓存大小==========" + size);
        tvMoresettingCache.setText(size);
    }

    /**
     * 清除缓存的操作
     */
    private void clearCache() {
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.clearMemoryCache();
        imageLoader.clearDiskCache();
    }
}
