package com.macth.match.recommend.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.macth.match.R;
import com.macth.match.common.base.BaseTitleActivity;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.common.widget.ProgressWebView;
import com.macth.match.recommend.RecommendUiGoto;

/**
 * 项目详情h5页
 */
public class ProjectDetailsBrowserActivity extends BaseTitleActivity {
    protected ProgressWebView mWebView;
    protected String strUrl;
    protected String title;

    @Override
    protected int getContentResId() {
        LogUtils.e("getContentResId---","getContentResId");
        return R.layout.browser;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        LogUtils.e("onCreate---", "onCreate");
        Intent mIntent = getIntent();
        if (mIntent != null) {
            strUrl = mIntent.getBundleExtra("bundle").getString("url");
            title = mIntent.getBundleExtra("bundle").getString("title");
            LogUtils.e("strUrl------------",strUrl);

        }
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initView() {
        LogUtils.e("initView---","initView");
        mWebView = (ProgressWebView) findViewById(R.id.browser_webview);
        mWebView.setWebViewClient(new MyWebViewClient());
    }


    @Override
    public void initData() {
        LogUtils.e("initData---","initData");
        setTitleText(title);
        mWebView.loadUrl(strUrl);

    }

    class MyWebViewClient extends WebViewClient {
        // 重写shouldOverrideUrlLoading方法，使点击链接后不使用其他的浏览器打开。
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            LogUtils.e("shouldOverrideUrlLoading---url----",""+url);
            if(url.contains("http")){
                Bundle b = new Bundle();
                b.putString("url",url);
                RecommendUiGoto.gotoMilePost(ProjectDetailsBrowserActivity.this, b);
            }
            // 如果不需要其他对点击链接事件的处理返回true，否则返回false
            return true;
        }
    }

    @Override
    protected void baseGoBack() {
        LogUtils.e("mWebView.canGoBack()---",""+mWebView.canGoBack());
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

}
