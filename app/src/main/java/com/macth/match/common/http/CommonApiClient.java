package com.macth.match.common.http;


import android.app.Activity;
import android.support.v4.app.Fragment;

import com.macth.match.common.dto.BaseDTO;
import com.macth.match.common.entity.BaseEntity;
import com.macth.match.find.dto.SearchDTO;
import com.macth.match.find.entity.FindResult;
import com.macth.match.group.entity.GroupResult;
import com.macth.match.login.dto.LoginDTO;
import com.macth.match.login.entity.LoginEntity;
import com.macth.match.mine.dto.AddInfoDTO;
import com.macth.match.mine.dto.ChangePwdDTO;
import com.macth.match.mine.dto.CloseDTO;
import com.macth.match.mine.dto.DeleteNewDTO;
import com.macth.match.mine.dto.UpdataDTO;
import com.macth.match.mine.entity.MineProjectsResult;
import com.macth.match.mine.entity.NewsResult;
import com.macth.match.mine.entity.UpdateResult;
import com.macth.match.notice.entity.NoticeResult;
import com.macth.match.recommend.dto.CooperativeDTO;
import com.macth.match.recommend.dto.DownloadDTO;
import com.macth.match.recommend.dto.FundsDTO;
import com.macth.match.recommend.dto.MinestoneDetailsDTO;
import com.macth.match.recommend.dto.SubmitDTO;
import com.macth.match.recommend.entity.AddItemListResult;
import com.macth.match.recommend.entity.AttachmentsDTO;
import com.macth.match.recommend.entity.FundsResult;
import com.macth.match.recommend.entity.MilDetailsResult;
import com.macth.match.recommend.entity.ProjectDetailsResult;
import com.macth.match.recommend.entity.RecommendResult;
import com.macth.match.register.dto.ForgetPwdDTO;
import com.macth.match.register.dto.RegisterDTO;
import com.macth.match.register.dto.SetNewPwdDTO;
import com.macth.match.register.dto.VerifyDTO;
import com.macth.match.register.entity.ShenFenEntity;

/**
 * Created by John_Libo on 2016/8/15.12
 */
public class CommonApiClient extends BaseApiClient {

    /**
     * 推荐项目列表
     *
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
     *
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
     * 申请协同作业
     *
     * @param act
     * @param dto
     * @param callback
     */
    public static void cooperative(Activity act, CooperativeDTO
            dto, CallBack<AddItemListResult> callback) {
        AsyncCallBack<AddItemListResult> asyncCallBack = new AsyncCallBack<>(
                act, callback, AddItemListResult.class);
        post(getAbsoluteUrl("Home/Cooperatives/applyCooperatives"), dto,
                asyncCallBack);
    }

    /**
     * 项目下拉框列表
     *
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
     *
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
     *
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
     * 提交项目
     *
     * @param dto
     * @param callback
     */
    public static void submit(Activity act, SubmitDTO
            dto, CallBack<AddItemListResult> callback) {
        AsyncCallBack<AddItemListResult> asyncCallBack = new AsyncCallBack<>(
                act, callback, AddItemListResult.class);
        post(getAbsoluteUrl("Home/Projects/publishProject"), dto,
                asyncCallBack);
    }

    /**
     * 获取用户群
     *
     * @param dto
     * @param callback
     */
    public static void group(Activity act, BaseDTO
            dto, CallBack<GroupResult> callback) {
        AsyncCallBack<GroupResult> asyncCallBack = new AsyncCallBack<>(
                act, callback, GroupResult.class);
        get(getAbsoluteUrl("Home/Cooperatives/getMyChatGroup"), dto,
                asyncCallBack);
    }

    /**
     * 获取项目资金用途详情
     *
     * @param dto
     * @param callback
     */
    public static void funds(Activity act, FundsDTO
            dto, CallBack<FundsResult> callback) {
        AsyncCallBack<FundsResult> asyncCallBack = new AsyncCallBack<>(
                act, callback, FundsResult.class);
        post(getAbsoluteUrl("Home/Projects/getFundInfo"), dto,
                asyncCallBack);
    }

    /**
     * 里程碑详情
     *
     * @param dto
     * @param callback
     */
    public static void milestoneDetails(Activity act, MinestoneDetailsDTO
            dto, CallBack<MilDetailsResult> callback) {
        AsyncCallBack<MilDetailsResult> asyncCallBack = new AsyncCallBack<>(
                act, callback, MilDetailsResult.class);
        post(getAbsoluteUrl("Home/Cooperatives/getMilePostDetailInfo"), dto,
                asyncCallBack);
    }

    /**
     * 里程碑状态
     *
     * @param dto
     * @param callback
     */
    public static void milestoneStatus(Activity act, UpdataDTO
            dto, CallBack<MilDetailsResult> callback) {
        AsyncCallBack<MilDetailsResult> asyncCallBack = new AsyncCallBack<>(
                act, callback, MilDetailsResult.class);
        post(getAbsoluteUrl("Home/Cooperatives/updateMilePostStatus"), dto,
                asyncCallBack);
    }



    /**
     * 更新里程碑
     *
     * @param dto
     * @param callback
     */
    public static void updata(Activity act, UpdataDTO
            dto, CallBack<MilDetailsResult> callback) {
        AsyncCallBack<MilDetailsResult> asyncCallBack = new AsyncCallBack<>(
                act, callback, MilDetailsResult.class);
        post(getAbsoluteUrl("Home/Cooperatives/updateMilePostStatus?"), dto,
                asyncCallBack);
    }

    /**
     * 查看附件
     *
     * @param dto
     * @param callback
     */
    public static void attachments(Activity act, AttachmentsDTO
            dto, CallBack<MilDetailsResult> callback) {
        AsyncCallBack<MilDetailsResult> asyncCallBack = new AsyncCallBack<>(
                act, callback, MilDetailsResult.class);
        post(getAbsoluteUrl("Home/Cooperatives/getFilesByMfileID?"), dto,
                asyncCallBack);
    }
    /**
     * 里程碑上传文件
     *
     * @param dto
     * @param callback
     */
    public static void download(Activity act, UpdataDTO
            dto, CallBack<MilDetailsResult> callback) {
        AsyncCallBack<MilDetailsResult> asyncCallBack = new AsyncCallBack<>(
                act, callback, MilDetailsResult.class);
        post(getAbsoluteUrl("Home/Cooperatives/uploadMilePostFile"), dto,
                asyncCallBack);
    }

    /**
     * 查看里程碑
     *
     * @param dto
     * @param callback
     */
    public static void see(Activity act, UpdataDTO
            dto, CallBack<UpdateResult> callback) {
        AsyncCallBack<UpdateResult> asyncCallBack = new AsyncCallBack<>(
                act, callback, UpdateResult.class);
        post(getAbsoluteUrl("Home/Cooperatives/getMilePostList"), dto,
                asyncCallBack);
    }


    /**
     * 发现列表
     *
     * @param dto
     * @param callback
     */
    public static void mineProjects(Activity act, BaseDTO
            dto, CallBack<MineProjectsResult> callback) {
        AsyncCallBack<MineProjectsResult> asyncCallBack = new AsyncCallBack<>(
                act, callback, MineProjectsResult.class);
        post(getAbsoluteUrl("Home/Projects/getMyProjectsList?"), dto,
                asyncCallBack);
    }

    /**
     * 我的项目之申请关闭
     *
     * @param dto
     * @param callback
     */
    public static void close(Activity act, CloseDTO
            dto, CallBack<MilDetailsResult> callback) {
        AsyncCallBack<MilDetailsResult> asyncCallBack = new AsyncCallBack<>(
                act, callback, MilDetailsResult.class);
        get(getAbsoluteUrl("Home/Projects/applyCloseProject"), dto,
                asyncCallBack);
    }

    /**
     * 注册
     *
     * @param act
     * @param dto
     * @param callback
     */
    public static void register(Activity act, RegisterDTO
            dto, CallBack<BaseEntity> callback) {
        AsyncCallBack<BaseEntity> asyncCallBack = new AsyncCallBack<>(
                act, callback, BaseEntity.class);
        post(getAbsoluteUrl("Home/Login/register?"), dto,
                asyncCallBack);
    }

    /**
     * 获取验证码
     *
     * @param act
     * @param dto
     * @param callback
     */
    public static void verifyCode(Activity act, VerifyDTO
            dto, CallBack<BaseEntity> callback) {
        AsyncCallBack<BaseEntity> asyncCallBack = new AsyncCallBack<>(
                act, callback, BaseEntity.class);
        post(getAbsoluteUrl("Home/Login/getMobileCode?"), dto,
                asyncCallBack);
    }

    /**
     * 登录
     *
     * @param act
     * @param dto
     * @param callback
     */
    public static void login(Activity act, LoginDTO
            dto, CallBack<LoginEntity> callback) {
        AsyncCallBack<LoginEntity> asyncCallBack = new AsyncCallBack<>(
                act, callback, LoginEntity.class);
        post(getAbsoluteUrl("Home/Login/login?"), dto,
                asyncCallBack);
    }

    /**
     * 忘记密码  第一步
     *
     * @param act
     * @param dto
     * @param callback
     */
    public static void forgetPwd(Activity act, ForgetPwdDTO
            dto, CallBack<BaseEntity> callback) {
        AsyncCallBack<BaseEntity> asyncCallBack = new AsyncCallBack<>(
                act, callback, BaseEntity.class);
        post(getAbsoluteUrl("Home/Login/forgetUserPwdOne?"), dto,
                asyncCallBack);
    }

    /**
     * 重设密码(忘记密码第二步)
     *
     * @param act
     * @param dto
     * @param callback
     */
    public static void setNewPwd(Activity act, SetNewPwdDTO
            dto, CallBack<BaseEntity> callback) {
        AsyncCallBack<BaseEntity> asyncCallBack = new AsyncCallBack<>(
                act, callback, BaseEntity.class);
        post(getAbsoluteUrl("Home/Login/forgetUserPwdTwo?"), dto,
                asyncCallBack);
    }

    /**
     * 获取用户身份信息
     *
     * @param act
     * @param
     * @param callback
     */
    public static void getShenFen(Activity act, BaseDTO dto, CallBack<ShenFenEntity> callback) {
        AsyncCallBack<ShenFenEntity> asyncCallBack = new AsyncCallBack<>(
                act, callback, ShenFenEntity.class);
        post(getAbsoluteUrl("Home/Login/getUserIdentityList"), dto,
                asyncCallBack);
    }

    /**
     * 获取协同角色信息
     *
     * @param act
     * @param
     * @param callback
     */
    public static void getRole(Activity act, BaseDTO dto, CallBack<ShenFenEntity> callback) {
        AsyncCallBack<ShenFenEntity> asyncCallBack = new AsyncCallBack<>(
                act, callback, ShenFenEntity.class);
        post(getAbsoluteUrl("Home/Login/getUserCooperativeList"), dto,
                asyncCallBack);
    }

    /**
     * 完善用户信息信息
     *
     * @param act
     * @param
     * @param callback
     */
    public static void addInfo(Activity act, AddInfoDTO dto, CallBack<BaseEntity> callback) {
        AsyncCallBack<BaseEntity> asyncCallBack = new AsyncCallBack<>(
                act, callback, BaseEntity.class);
        post(getAbsoluteUrl("Home/Login/perfectUserData"), dto,
                asyncCallBack);
    }

    /**
     * 消息列表
     *
     * @param act
     * @param
     * @param callback
     */
    public static void  newsList(Activity act, BaseDTO dto, CallBack<NewsResult> callback) {
        AsyncCallBack<NewsResult> asyncCallBack = new AsyncCallBack<>(
                act, callback, NewsResult.class);
        get(getAbsoluteUrl("Home/Notices/getNoticeList"), dto,
                asyncCallBack);
    }

    /**
     * 删除消息
     *
     * @param act
     * @param
     * @param callback
     */
    public static void  deleteNew(Activity act, DeleteNewDTO dto, CallBack<BaseEntity> callback) {
        AsyncCallBack<BaseEntity> asyncCallBack = new AsyncCallBack<>(
                act, callback, BaseEntity.class);
        get(getAbsoluteUrl("Home/Notices/deleteMyNotice"), dto,
                asyncCallBack);
    }

    /**
     * 修改密码
     *
     * @param act
     * @param
     * @param callback
     */
    public static void ChangePwd(Activity act, ChangePwdDTO dto, CallBack<BaseEntity> callback) {
        AsyncCallBack<BaseEntity> asyncCallBack = new AsyncCallBack<>(
                act, callback, BaseEntity.class);
        post(getAbsoluteUrl("Home/Login/updateUserPwd?"), dto,
                asyncCallBack);
    }

    /**
     * 搜索
     *
     * @param dto
     * @param callback
     */
    public static void search(Activity act, SearchDTO
            dto, CallBack<FindResult> callback) {
        AsyncCallBack<FindResult> asyncCallBack = new AsyncCallBack<>(
                act, callback, FindResult.class);
        post(getAbsoluteUrl("Home/Projects/searchProject?"), dto,
                asyncCallBack);
    }

}
