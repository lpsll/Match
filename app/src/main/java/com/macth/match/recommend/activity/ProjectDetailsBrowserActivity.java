package com.macth.match.recommend.activity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.macth.match.AppConfig;
import com.macth.match.R;
import com.macth.match.common.base.BaseTitleActivity;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.common.utils.TextViewUtils;
import com.macth.match.common.widget.ProgressWebView;
import com.macth.match.recommend.RecommendUiGoto;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * 项目详情h5页
 */
public class ProjectDetailsBrowserActivity extends BaseTitleActivity {
    protected ProgressWebView mWebView;
    protected String strUrl;
    protected String title;
    protected String isflag;
    protected String pid_url;
    protected String pid;

    private TextView mBaseEnsure;

    @Override
    protected int getContentResId() {
        LogUtils.e("getContentResId---", "getContentResId");
        return R.layout.browser;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        LogUtils.e("onCreate---", "onCreate");
        Intent mIntent = getIntent();
        if (mIntent != null) {
            strUrl = mIntent.getBundleExtra("bundle").getString("url");
            title = mIntent.getBundleExtra("bundle").getString("title");
            isflag = mIntent.getBundleExtra("bundle").getString("isflag");
            pid = mIntent.getBundleExtra("bundle").getString("pid");
            pid_url  = AppConfig.FX_URL+"pid="+pid;
            LogUtils.e("strUrl------------", strUrl);

        }
        super.onCreate(savedInstanceState);


    }

    @Override
    public void initView() {
        LogUtils.e("initView---", "initView");
        mWebView = (ProgressWebView) findViewById(R.id.browser_webview);
        mWebView.setWebViewClient(new MyWebViewClient());

        mBaseEnsure = (TextView) findViewById(R.id.base_titlebar_ensure);
        if(isflag.equals("0")){
            // 初始化返回按钮图片大小
            TextViewUtils.setTextViewIcon(this, mBaseEnsure, R.drawable.fenxiangxdpi_03,
                    R.dimen.common_titlebar_right_width,
                    R.dimen.common_titlebar_icon_height, TextViewUtils.DRAWABLE_LEFT);
        }

    }


    @Override
    public void initData() {
        LogUtils.e("initData---", "initData");
        setTitleText(title);
        mWebView.loadUrl(strUrl);

    }

    class MyWebViewClient extends WebViewClient {
        // 重写shouldOverrideUrlLoading方法，使点击链接后不使用其他的浏览器打开。
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            LogUtils.e("shouldOverrideUrlLoading---url----", "" + url);
            if (url.contains("http")) {
                Bundle b = new Bundle();
                b.putString("url", url);
                if(url.contains("Milestone")){  //里程碑
                    b.putString("title","里程碑");
                }else {   //资金用途详情
                    b.putString("title","资金用途详情");
                }
                RecommendUiGoto.gotoMilePost(ProjectDetailsBrowserActivity.this, b);
            }
            // 如果不需要其他对点击链接事件的处理返回true，否则返回false
            return true;
        }
    }

    @Override
    protected void baseGoBack() {
        LogUtils.e("mWebView.canGoBack()---", "" + mWebView.canGoBack());
        if (mWebView.canGoBack()) {
            mWebView.goBack();// 返回前一个页面
        } else {
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
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
            case R.id.share_qq:
                type = "4";
                showShare();
                break;
            case R.id.pop_share_text:
                backgroundAlpha(1f);
                popWindow.dismiss();
                break;
        }
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

        qq = (LinearLayout) view
                .findViewById(R.id.share_qq);


        text = (TextView) view
                .findViewById(R.id.pop_share_text);
        weixin.setOnClickListener(this);
        friend.setOnClickListener(this);
        qq.setOnClickListener(this);
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
        LogUtils.e("type---",""+type);
        if(type.equals("1")){
            WechatMoments.ShareParams sp = new WechatMoments.ShareParams();
            sp.setShareType(Platform.SHARE_WEBPAGE);
            sp.setTitle("撮合");
            // text是分享文本，所有平台都需要这个字段
            sp.setText("项目详情");
            // url仅在微信（包括好友和朋友圈）中使用11111
            sp.setUrl(pid_url);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.juesehxdpi_03);
            sp.setImageData(bitmap);
            LogUtils.e("sp---",""+sp);
            Platform wm = ShareSDK.getPlatform(WechatMoments.NAME);
            wm.setPlatformActionListener(paListener);
            wm.share(sp);
        }
        else if(type.equals("2")){
            Wechat.ShareParams sp = new Wechat.ShareParams();
            sp.setShareType(Platform.SHARE_WEBPAGE);
            sp.setTitle("撮合");
            // text是分享文本，所有平台都需要这个字段
            sp.setText("项目详情");
            // url仅在微信（包括好友和朋友圈）中使用
            sp.setUrl(pid_url);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.juesehxdpi_03);
            sp.setImageData(bitmap);
            LogUtils.e("sp---",""+sp);
            Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
            wechat.setPlatformActionListener(paListener);
            wechat.share(sp);
        }
        else if(type.equals("4")){
            QQ.ShareParams sp = new QQ.ShareParams();
            sp.setTitle("撮合");
            // text是分享文本，所有平台都需要这个字段
            // titleUrl是标题的网络链接，QQ和QQ空间等使用
            sp.setTitleUrl(pid_url);
            sp.setText("项目详情");
//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.juesehxdpi_03);
//            sp.setImageData(bitmap);
//
//            Resources resources = getResources();
//            Uri uri =  Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
//                    + resources.getResourcePackageName(R.drawable.juesehxdpi_03) + "/"
//                    + resources.getResourceTypeName(R.drawable.juesehxdpi_03) + "/"
//                    + resources.getResourceEntryName(R.drawable.juesehxdpi_03));
//            LogUtils.e("uri---",""+uri);
//            LogUtils.e("getPath---",""+uri.toString());
//            sp.setImageUrl(convertIconToString(bitmap));
//            sp.setImagePath(uri.toString());
            LogUtils.e("sp---",""+sp);
            Platform qq = ShareSDK.getPlatform(QQ.NAME);
            qq.setPlatformActionListener(paListener);
            qq.share(sp);
        }


    }

    /**
     * 图片转成string
     *
     * @param bitmap
     * @return
     */
    public static String convertIconToString(Bitmap bitmap)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] appicon = baos.toByteArray();// 转为byte数组
        return Base64.encodeToString(appicon, Base64.DEFAULT);

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
