package com.macth.match.recommend.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.macth.match.R;
import com.macth.match.common.base.BaseTitleActivity;
import com.macth.match.common.base.SimplePage;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.common.utils.UIHelper;
import com.macth.match.common.widget.ProgressWebView;

/**
 * 新增项目的h5页
 */
public class AddItemBrowserActivity extends BaseTitleActivity {
    protected ProgressWebView mWebView;
    protected String strUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        LogUtils.e("onCreate---", "onCreate");
        Intent mIntent = getIntent();
        if (mIntent != null) {
            strUrl = mIntent.getBundleExtra("bundle").getString("url");
            LogUtils.e("strUrl------------",strUrl);

        }
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initView() {
        LogUtils.e("initView---","initView");
        mWebView = (ProgressWebView) findViewById(R.id.browser_webview);
        mWebView.setWebViewClient(new MyWebViewClient());
        final JavaScriptInterface myJavaScriptInterface = new JavaScriptInterface(this);

        mWebView.addJavascriptInterface(myJavaScriptInterface, "click");
        setTitleText("新增项目");

    }

    @Override
    public void initData() {
        LogUtils.e("initData---","initData");
        mWebView.loadUrl(strUrl);
    }

    public class JavaScriptInterface {
        Context mContext;

        JavaScriptInterface(Context c) {
            mContext = c;
        }
        @JavascriptInterface
        public void AddItemComplete(String projectNo) {
            Bundle b = new Bundle();
            b.putString("projectNo", projectNo);
            b.putString("flag","1");
            UIHelper.showBundleFragment(AddItemBrowserActivity.this, SimplePage.ADD_USE,b);//增加资金用途
        }
    }



    @Override
    protected int getContentResId() {
        LogUtils.e("getContentResId---","getContentResId");
        return R.layout.browser;
    }

    @Override
    protected void baseGoBack() {
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

    class MyWebViewClient extends WebViewClient {
        // 重写shouldOverrideUrlLoading方法，使点击链接后不使用其他的浏览器打开。
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            LogUtils.e("shouldOverrideUrlLoading---url----",""+url);

            // 如果不需要其他对点击链接事件的处理返回true，否则返回false
            return true;
        }
    }
}
