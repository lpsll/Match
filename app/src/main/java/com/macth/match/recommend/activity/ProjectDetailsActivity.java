package com.macth.match.recommend.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.macth.match.AppConfig;
import com.macth.match.AppContext;
import com.macth.match.R;
import com.macth.match.common.base.BaseTitleActivity;
import com.macth.match.common.base.SimplePage;
import com.macth.match.common.http.CallBack;
import com.macth.match.common.http.CommonApiClient;
import com.macth.match.common.utils.DialogUtils;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.common.utils.TextViewUtils;
import com.macth.match.common.utils.UIHelper;
import com.macth.match.recommend.RecommendUiGoto;
import com.macth.match.recommend.dto.CooperativeDTO;
import com.macth.match.recommend.dto.ProjectDetailsDTO;
import com.macth.match.recommend.entity.AddItemListResult;
import com.macth.match.recommend.entity.ProjectDetailsData;
import com.macth.match.recommend.entity.ProjectDetailsEntity;
import com.macth.match.recommend.entity.ProjectDetailsListEntity;
import com.macth.match.recommend.entity.ProjectDetailsResult;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

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


    @OnClick({R.id.pd_tv01, R.id.pd_tv02, R.id.tv_btn,R.id.base_titlebar_ensure})
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
                // 分享操作
                showPopShare();

                break;
            case R.id.base_titlebar_back:
                baseGoBack();
                break;
            case R.id.share_weixin:
                type = "1";
                showShare();
                break;
            case R.id.share_friend:
                type = "2";
                showShare();
                break;
            case R.id.share_weibo:
                type = "3";
                showShare();
                break;
            case R.id.share_qq:
                type = "4";
                showShare();
                break;
            case R.id.share_qzone:
                type = "5";
                showShare();
                break;
            case R.id.pop_share_text:
                backgroundAlpha(1f);
                popWindow.dismiss();
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

    PopupWindow popWindow;
    private LinearLayout weixin, friend, weibo, qq, qqZon;
    private TextView text;
    private String type;
    private void showPopShare() {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.from(this).inflate(R.layout.pop_share, null);
        popWindow = new PopupWindow(view, WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true);

        // 需要设置一下此参数，点击外边可消失
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置点击窗口外边窗口消失
        popWindow.setOutsideTouchable(true);
        // 设置此参数获得焦点，否则无法点击
        popWindow.setFocusable(true);
        //防止虚拟软键盘被弹出菜单遮住
        popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        backgroundAlpha(0.7f);

        weixin = (LinearLayout) view
                .findViewById(R.id.share_weixin);
        friend = (LinearLayout) view
                .findViewById(R.id.share_friend);
        weibo = (LinearLayout) view
                .findViewById(R.id.share_weibo);
        qq = (LinearLayout) view
                .findViewById(R.id.share_qq);
        qqZon = (LinearLayout) view
                .findViewById(R.id.share_qzone);

        text = (TextView) view
                .findViewById(R.id.pop_share_text);
        weixin.setOnClickListener(this);
        friend.setOnClickListener(this);
        weibo.setOnClickListener(this);
        qq.setOnClickListener(this);
        qqZon.setOnClickListener(this);
        text.setOnClickListener(this);

        View parent = this.getWindow().getDecorView();//高度为手机实际的像素高度
        popWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        //添加pop窗口关闭事件
        popWindow.setOnDismissListener(new poponDismissListener());

    }

    public class poponDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            LogUtils.e("List_noteTypeActivity:", "我是关闭事件");
            backgroundAlpha(1f);
            popWindow.dismiss();
            LogUtils.e("List_noteTypeActivity:", "dismiss");
        }

    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        this.getWindow().setAttributes(lp);
    }


    private void showShare() {

        if(type.equals("1")){
            LogUtils.e("type---",""+type);
            WechatMoments.ShareParams sp = new WechatMoments.ShareParams();
            sp.setShareType(Platform.SHARE_WEBPAGE);
            sp.setTitle("项目详情");
            // text是分享文本，所有平台都需要这个字段
            sp.setText("项目详情");
            // url仅在微信（包括好友和朋友圈）中使用11111
            sp.setUrl("");

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.juesehxdpi_03);
            sp.setImageData(bitmap);

            LogUtils.e("sp---",""+sp);
            Platform wm = ShareSDK.getPlatform(WechatMoments.NAME);
            wm.setPlatformActionListener(paListener);
            LogUtils.e("wm---",""+wm);

            wm.share(sp);
        }
        else if(type.equals("2")){
            LogUtils.e("type---",""+type);
            Wechat.ShareParams sp = new Wechat.ShareParams();
            sp.setShareType(Platform.SHARE_WEBPAGE);
            sp.setTitle("项目详情");
            // text是分享文本，所有平台都需要这个字段
            sp.setText("项目详情");
            // url仅在微信（包括好友和朋友圈）中使用
            sp.setUrl("");

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.juesehxdpi_03);
            sp.setImageData(bitmap);

//            sp.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jp");

            Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
            wechat.setPlatformActionListener(paListener);
            wechat.share(sp);
        }
        else if(type.equals("3")){
            LogUtils.e("type---",""+type);
            SinaWeibo.ShareParams sp = new SinaWeibo.ShareParams();
            sp.setTitle("项目详情");
            // text是分享文本，所有平台都需要这个字段
            sp.setText("项目详情");

            sp.setImageUrl("");

            Platform sn = ShareSDK.getPlatform(SinaWeibo.NAME);
            sn.setPlatformActionListener(paListener);
            sn.share(sp);
        }
        else if(type.equals("4")){
            LogUtils.e("type---",""+type);

            QQ.ShareParams sp = new QQ.ShareParams();
            sp.setTitle("项目详情");
            // text是分享文本，所有平台都需要这个字段
            // titleUrl是标题的网络链接，QQ和QQ空间等使用
            sp.setTitleUrl("");
            sp.setText("项目详情");

            sp.setImageUrl("");


            LogUtils.e("sp---",""+sp);
            Platform qq = ShareSDK.getPlatform(QQ.NAME);
            qq.setPlatformActionListener(paListener);
            LogUtils.e("qq---",""+qq);
            qq.share(sp);
        }
        else if(type.equals("5")){
            LogUtils.e("type---",""+type);
            QZone.ShareParams sp = new QZone.ShareParams();
            sp.setTitle("项目详情");
            // text是分享文本，所有平台都需要这个字段
            sp.setText("项目详情");
            // titleUrl是标题的网络链接，QQ和QQ空间等使用
            sp.setTitleUrl("");
            // comment是我对这条分享的评论，仅在人人网和QQ空间使用
            sp.setComment("项目详情");
            // site是分享此内容的网站名称，仅在QQ空间使用
            sp.setSite(getString(R.string.app_name));
            // siteUrl是分享此内容的网站地址，仅在QQ空间使用
            sp.setSiteUrl("");


            sp.setImageUrl("");

            Platform qzone = ShareSDK.getPlatform(QZone.NAME);
            qzone.setPlatformActionListener(paListener);
            qzone.share(sp);
        }

    }

    PlatformActionListener paListener = new PlatformActionListener() {
        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
            //操作成功，在这里可以做后续的步骤
            //这里需要说明的一个参数就是HashMap<String, Object> arg2
            //这个参数在你进行登录操作的时候里面会保存有用户的数据，例如用户名之类的。
            LogUtils.e("platform---",""+platform);
        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {
            //操作失败啦，打印提供的错误，方便调试
            LogUtils.e("throwable---",""+throwable);
        }

        @Override
        public void onCancel(Platform platform, int i) {
            //用户取消操作会调用这里
        }
    };
}
