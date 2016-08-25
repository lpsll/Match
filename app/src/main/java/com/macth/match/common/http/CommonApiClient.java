package com.macth.match.common.http;


import android.app.Activity;
import android.support.v4.app.Fragment;

import com.macth.match.common.dto.BaseDTO;
import com.macth.match.find.entity.FindResult;
import com.macth.match.mine.fragment.entity.MineProjectsResult;
import com.macth.match.notice.entity.NoticeResult;
import com.macth.match.recommend.entity.AddItemListResult;
import com.macth.match.recommend.entity.ProjectDetailsResult;
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
     * 项目详情
     * @param act
     * @param dto
     * @param callback
     */
    public static void projectDetails(Activity act, BaseDTO
            dto, CallBack<ProjectDetailsResult> callback) {
        AsyncCallBack<ProjectDetailsResult> asyncCallBack = new AsyncCallBack<>(
                act, callback, ProjectDetailsResult.class);
        post(getAbsoluteUrl("Home/Projects/getProjectInfo"), dto,
                asyncCallBack);
    }

    /**
     * 项目下拉框列表
     * @param act
     * @param dto
     * @param callback
     */
    public static void addList(Activity act, BaseDTO
            dto, CallBack<AddItemListResult> callback) {
        AsyncCallBack<AddItemListResult> asyncCallBack = new AsyncCallBack<>(
                act, callback, AddItemListResult.class);
        post(getAbsoluteUrl("Home/Projects/getProjectContantsList"), dto,
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

    /**
     * 发现列表
     * @param dto
     * @param callback
     */
    public static void mineProjects(Activity act, BaseDTO
            dto, CallBack<MineProjectsResult> callback, String uid) {
        AsyncCallBack<MineProjectsResult> asyncCallBack = new AsyncCallBack<>(
                act, callback, MineProjectsResult.class);
        post(getAbsoluteUrl("Home/Projects/getMyProjectsList?userid="+uid), dto,
                asyncCallBack);
    }
}
