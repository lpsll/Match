package com.macth.match.mine.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.macth.match.AppConfig;
import com.macth.match.AppContext;
import com.macth.match.R;
import com.macth.match.common.base.BaseTitleActivity;
import com.macth.match.common.dto.BaseDTO;
import com.macth.match.common.http.CallBack;
import com.macth.match.common.http.CommonApiClient;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.common.widget.EmptyLayout;
import com.macth.match.mine.MineUIGoto;
import com.macth.match.mine.adapter.NewsAdapter;
import com.macth.match.mine.entity.NewsEntity;
import com.macth.match.mine.entity.NewsResult;
import com.qluxstory.ptrrecyclerview.PtrRecyclerView;

import java.util.List;

import butterknife.Bind;

public class NewsActivity extends BaseTitleActivity {


    @Bind(R.id.recyclerview_news)
    PtrRecyclerView recyclerviewNews;
    @Bind(R.id.error_layout)
    EmptyLayout errorLayout;

    private NewsAdapter newsAdapter;
    private List<NewsEntity> newsData;

    @Override
    protected int getContentResId() {
        return R.layout.activity_news;
    }

    @Override
    public void initView() {
        setTitleText("消息");
        showDialogLoading();

    }

    @Override
    public void initData() {
        getData();

    }

    /**
     * 获取我的项目数据  拼接uid默认为2
     */
    private void getData() {
        BaseDTO dto = new BaseDTO();
        //此处需要替换用户id
        dto.setUserid(AppContext.get("usertoken",""));
        CommonApiClient.newsList(NewsActivity.this, dto, new CallBack<NewsResult>() {
            @Override
            public void onSuccess(NewsResult result) {
                if (AppConfig.SUCCESS.equals(result.getCode())) {
                    LogUtils.e("消息列表获取成功");
                    errorLayout.setErrorMessage("暂无消息记录", errorLayout.FLAG_NODATA);
                    errorLayout.setErrorImag(R.drawable.page_icon_empty, errorLayout.FLAG_NODATA);
                    if (null == result.getData()) {
                        errorLayout.setErrorType(EmptyLayout.NODATA);
                    } else {
                        if(result.getData().getList() == null) {
                            errorLayout.setErrorType(EmptyLayout.NODATA);
                        }else {

                            newsData = result.getData().getList();

                            recyclerviewNews.setLayoutManager(new LinearLayoutManager(NewsActivity.this));
                            newsAdapter = new NewsAdapter(NewsActivity.this,newsData);
                            recyclerviewNews.setAdapter(newsAdapter);

                            newsAdapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
                                @Override
                                public void onClick(View v, int position, String url) {
                                    //跳转到消息详情页面
                                    MineUIGoto.gotoNewsDetails(NewsActivity.this,url);
                                }
                            });

                        }
                            hideDialogLoading();
                    }
                }
            }
        });
    }
}
