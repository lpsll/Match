package com.macth.match.common.http;


import android.support.v4.app.Fragment;

import com.macth.match.common.dto.BaseDTO;
import com.macth.match.find.fragment.FindResult;
import com.macth.match.notice.entity.NoticeResult;
import com.macth.match.recommend.entity.RecommendResult;

/**
 * Created by John_Libo on 2016/8/15.
 */
public class CommonApiClient extends BaseApiClient{

    /**
     * 推荐项目列表
     * @param dto
     * @param callback
     */
    public static void recommend(Fragment fragment, BaseDTO
            dto, CallBack<RecommendResult> callback) {
        AsyncCallBack<RecommendResult> asyncCallBack = new AsyncCallBack<>(
                fragment, callback, RecommendResult.class);
        post(getAbsoluteUrl("Home/Projects/getHomeProjectsList"), dto,
                asyncCallBack);
    }

    /**
     * 公告列表
     * @param dto
     * @param callback
     */
    public static void notice(Fragment fragment, BaseDTO
            dto, CallBack<NoticeResult> callback) {
        AsyncCallBack<NoticeResult> asyncCallBack = new AsyncCallBack<>(
                fragment, callback, NoticeResult.class);
        post(getAbsoluteUrl("Home/Messages/getMessageList"), dto,
                asyncCallBack);
    }

    /**
     * 发现列表
     * @param dto
     * @param callback
     */
    public static void find(Fragment fragment, BaseDTO
            dto, CallBack<FindResult> callback) {
        AsyncCallBack<FindResult> asyncCallBack = new AsyncCallBack<>(
                fragment, callback, FindResult.class);
        post(getAbsoluteUrl("Home/Projects/getProjectListFromFind"), dto,
                asyncCallBack);
    }
}
