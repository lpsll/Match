package com.macth.match.common.widget;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.macth.match.R;
import com.macth.match.common.utils.TDevice;


public class EmptyLayout extends LinearLayout implements
        View.OnClickListener {

    public static final int HIDE_LAYOUT = 4;
    public static final int NETWORK_ERROR = 1;
    public static final int NETWORK_LOADING = 2;
    public static final int NODATA = 3;

    private ProgressBar animProgress;
    private boolean clickEnable = true;
    private final Context context;
    public ImageView img;
    private OnClickListener listener;
    private int mErrorState;

    private TextView tv,mTv;

    public static final int FLAG_HTTP_FAIL=0;
    public static final int FLAG_NONET=1;
    public static final int FLAG_NODATA=2;

    //内容加载失败，没网，没数据
    int[] imgResId ={R.drawable.pagefailed_bg, R.drawable.page_icon_network,R.drawable.page_icon_empty};
    String[] tvMsgResId={getResources().getString(R.string.error_view_load_error_click_to_refresh),
            getResources().getString(R.string.error_view_network_error_click_to_refresh) ,
            getResources().getString(R.string.error_view_no_data)
    };
    public EmptyLayout(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public EmptyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        View view = View.inflate(context, R.layout.view_error_layout, null);
        img = (ImageView) view.findViewById(R.id.img_error_layout);
        tv = (TextView) view.findViewById(R.id.tv_error_layout);
        mTv = (TextView) view.findViewById(R.id.tv_tv_error_layout);
        animProgress = (ProgressBar) view.findViewById(R.id.animProgress);
        setBackgroundColor(-1);
        setOnClickListener(this);
        img.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (clickEnable) {
                    if (listener != null)
                        listener.onClick(v);
                }
            }
        });
        addView(view);

    }


    public void dismiss() {
        mErrorState = HIDE_LAYOUT;
        setVisibility(View.GONE);
    }

    public int getErrorState() {
        return mErrorState;
    }

    public boolean isLoadError() {
        return mErrorState == NETWORK_ERROR;
    }

    public boolean isLoading() {
        return mErrorState == NETWORK_LOADING;
    }

    @Override
    public void onClick(View v) {
        if (clickEnable) {
            if (listener != null)
                listener.onClick(v);
        }
    }


    public void setErrorMessage(String msg,int type) {
        tvMsgResId[type]=msg;
    }


    public void setErrorImag(int imgResource,int type) {
        try {
            imgResId[type]=imgResource;
        } catch (Exception e) {
        }
    }

    public void setErrorType(int i) {
        setVisibility(View.VISIBLE);
        switch (i) {
        case NETWORK_ERROR:
            mErrorState = NETWORK_ERROR;
            if (TDevice.hasInternet(context)) {
                tv.setText(tvMsgResId[FLAG_HTTP_FAIL]);
                img.setBackgroundResource(imgResId[FLAG_HTTP_FAIL]);
            } else {
                tv.setText(tvMsgResId[FLAG_NONET]);
                img.setBackgroundResource(imgResId[FLAG_NONET]);
            }
            img.setVisibility(View.VISIBLE);
            animProgress.setVisibility(View.GONE);
//            mTv.setVisibility(View.GONE);
            clickEnable = true;
            break;
        case NETWORK_LOADING:
            mErrorState = NETWORK_LOADING;
            animProgress.setVisibility(View.VISIBLE);
            img.setVisibility(View.GONE);
//            mTv.setVisibility(View.GONE);
            tv.setText(R.string.error_view_loading);
            clickEnable = false;
            break;
        case NODATA:
            mErrorState = NODATA;
            img.setBackgroundResource(imgResId[FLAG_NODATA]);
            img.setVisibility(View.VISIBLE);
            animProgress.setVisibility(View.GONE);
            tv.setText(tvMsgResId[FLAG_NODATA]);
//            mTv.setVisibility(View.VISIBLE);
            clickEnable = true;
            break;
        case HIDE_LAYOUT:
            setVisibility(View.GONE);
            break;
        default:
            break;
        }
    }

    public void setOnLayoutClickListener(OnClickListener listener) {
        this.listener = listener;
    }


    @Override
    public void setVisibility(int visibility) {
        if (visibility == View.GONE)
            mErrorState = HIDE_LAYOUT;
        super.setVisibility(visibility);
    }
}
