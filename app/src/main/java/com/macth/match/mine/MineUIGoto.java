package com.macth.match.mine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.macth.match.login.activity.LoginActivity;
import com.macth.match.mine.activity.ChangePwdActivity;
import com.macth.match.mine.activity.ModificationProjectActivity;
import com.macth.match.mine.activity.ModifyInformationActivity;
import com.macth.match.mine.activity.NewsActivity;
import com.macth.match.mine.activity.NewsDetailsActivity;
import com.macth.match.mine.activity.PersonalInformationActivity;
import com.macth.match.mine.activity.SettingActivity;
import com.macth.match.register.activity.AddInfoActivity;

/**
 * Created by Administrator on 2016/8/27.
 */
public class MineUIGoto {


    /**
     * 跳转到登录页(密码登录)
     * @param
     */
    public static final int LOFIN_REQUEST = 0x1100;
    public static void gotoLoginForPwd(Activity act) {
        Intent intent = new Intent(act, LoginActivity.class);
        act.startActivityForResult(intent, LOFIN_REQUEST);
    }

    /**
     * 跳转到消息页
     * @param
     */
    public static void gotoNews(Activity act){
        Intent intent = new Intent(act, NewsActivity.class);
        act.startActivity(intent);
    }

    /**
     * 跳转到消息详情
     * @param
     */
    public static void gotoNewsDetails(Activity act,String url) {
        Intent intent = new Intent(act, NewsDetailsActivity.class);
        intent.putExtra("newsDetailsUrl",url);
        act.startActivity(intent);
    }

    /**
     * 跳转到设置
     * @param
     */
    public static void gotoSetting(Activity act) {
        Intent intent = new Intent(act, SettingActivity.class);
        act.startActivity(intent);
    }

    /**
     * 跳转到个人信息页
     * @param
     */
    public static void gotoPersonal(Activity act) {
        Intent intent = new Intent(act, PersonalInformationActivity.class);
        act.startActivity(intent);
    }

    /**
     * 跳转到完善个人信息页
     * @param
     */
    public static void gotoAddInfo(Activity act) {
        Intent intent = new Intent(act, AddInfoActivity.class);
        act.startActivity(intent);
    }

    /**
     * 跳转到修改信息页
     * @param
     * @param bundle
     */
    public static final int MF_REQUEST = 0x1100;
    public static void gotoModifyinformation(Activity act, Bundle bundle) {
        Intent intent = new Intent(act, ModifyInformationActivity.class);
        intent.putExtra("bundle",bundle);
        act.startActivityForResult(intent,MF_REQUEST);
    }

    /**
     * 跳转到修改密码
     * @param
     */
    public static void gotoChangePwd(Activity act) {
        Intent intent = new Intent(act, ChangePwdActivity.class);
        act.startActivity(intent);


    }


    /**
     * 跳转到修改项目页
     * @param
     */

    public static void gotoModification(Context context, Bundle bundle) {
        Intent intent = new Intent(context, ModificationProjectActivity.class);
        intent.putExtra("bundle",bundle);
        context.startActivity(intent);
    }
}
