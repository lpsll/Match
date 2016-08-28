package com.macth.match.mine.activity;

import android.content.Intent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.macth.match.R;
import com.macth.match.common.base.BaseTitleActivity;

import butterknife.Bind;

public class NewsDetailsActivity extends BaseTitleActivity {


    @Bind(R.id.webview_news_detail)
    WebView webviewNewsDetail;

    private String url;
    private WebSettings settings;

    @Override
    protected int getContentResId() {
        return R.layout.activity_news_details;
    }

    @Override
    public void initView() {
        setTitleText("消息详情");

        showDialogLoading();
        Intent intent = getIntent();
        url = intent.getStringExtra("newsDetailsUrl");
    }

    @Override
    public void initData() {
        addH5();

    }

    private void addH5() {
        //一定要加上下面的,防止页面出现太大的情况
        //设置支持JavaScript
        settings = webviewNewsDetail.getSettings();
        settings.setJavaScriptEnabled(true);
        //启用页面上放大缩小按钮
        settings.setBuiltInZoomControls(true);
        //启用页面双击缩放功能
        settings.setUseWideViewPort(true);

        webviewNewsDetail.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                hideDialogLoading();
            }
        });
        webviewNewsDetail.loadUrl(url);
    }


}
