package com.macth.match.notice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.macth.match.R;
import com.macth.match.common.base.BaseTitleActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NoticeDetailActivity extends BaseTitleActivity {


    @Bind(R.id.webview_notice_detail)
    WebView webviewNoticeDetail;
    
    private String url;
    private WebSettings settings;

    @Override
    protected int getContentResId() {
        return R.layout.activity_notice_detail;
    }

    @Override
    public void initView() {
        setTitleText("公告详情");
        showDialogLoading();
        Intent intent = getIntent();
        url = intent.getStringExtra("noticeUrl");
    }

    @Override
    public void initData() {
        addH5();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private void addH5() {
        //一定要加上下面的,防止页面出现太大的情况
        //设置支持JavaScript
        settings = webviewNoticeDetail.getSettings();
        settings.setJavaScriptEnabled(true);
        //启用页面上放大缩小按钮
        settings.setBuiltInZoomControls(true);
        //启用页面双击缩放功能
        settings.setUseWideViewPort(true);

        webviewNoticeDetail.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                hideDialogLoading();
            }
        });
        webviewNoticeDetail.loadUrl(url);
    }
}
