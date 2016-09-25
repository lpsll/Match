package com.macth.match.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
 * 我的项目之修改项目h5页
 */
public class ModificationProjectBrowserActivity extends BaseTitleActivity {

    protected ProgressWebView mWebView;
    protected String strUrl;

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

    }

    public class JavaScriptInterface {
        Context mContext;

        JavaScriptInterface(Context c) {
            mContext = c;
        }
        @JavascriptInterface
        public void UpdateItemComplete(String projectno) {
            Bundle b = new Bundle();
            b.putString("projectNo", projectno);
            b.putString("flag","0");
            UIHelper.showBundleFragment(ModificationProjectBrowserActivity.this, SimplePage.ADD_USE,b);//增加资金用途
        }
    }


    @Override
    public void initData() {
        LogUtils.e("initData---","initData");
        setTitleText("修改项目");
        mWebView.loadUrl(strUrl);

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
