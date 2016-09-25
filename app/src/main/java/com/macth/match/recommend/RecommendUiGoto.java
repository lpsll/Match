package com.macth.match.recommend;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.macth.match.common.base.BrowserActivity;
import com.macth.match.login.activity.LoginActivity;
import com.macth.match.mine.activity.ModificationProjectBrowserActivity;
import com.macth.match.mine.activity.MyMilepostActivityBrowserActivity;
import com.macth.match.recommend.activity.AddItemActivity;
import com.macth.match.recommend.activity.AddItemBrowserActivity;
import com.macth.match.recommend.activity.ChoiceCityActivity;
import com.macth.match.recommend.activity.DetailsFundsActivity;
import com.macth.match.recommend.activity.IncreaseCapitalActivity;
import com.macth.match.recommend.activity.MilepostActivityBrowserActivity;
import com.macth.match.recommend.activity.ModifyCapitalActivity;
import com.macth.match.recommend.activity.ProjectDetailsActivity;
import com.macth.match.recommend.activity.ProjectDetailsBrowserActivity;

/**
 * Created by John_Libo on 2016/8/24.
 */
public class RecommendUiGoto {
    /**
     * 跳转到项目详情
     * @param context
     * @param b
     */

    public static void gotoProject(Context context, Bundle b){
        Intent intent = new Intent(context, ProjectDetailsActivity.class);
        intent.putExtra("bundle",b);
        context.startActivity(intent);
    }

    /**
     * 跳转到新增项目h5
     * @param context
     * @param b
     */

    public static void gotoAdded(Context context, Bundle b){
        Intent intent = new Intent(context, AddItemBrowserActivity.class);
        intent.putExtra("bundle",b);
        context.startActivity(intent);
    }

    /**
     * 跳转到项目详情h5
     * @param context
     * @param b
     */

    public static void gotoPdb(Context context, Bundle b){
        Intent intent = new Intent(context, ProjectDetailsBrowserActivity.class);
        intent.putExtra("bundle",b);
        context.startActivity(intent);
    }

    /**
     * 跳转到里程碑h5
     * @param context
     * @param b
     */

    public static void gotoMilePost(Context context, Bundle b){
        Intent intent = new Intent(context, MilepostActivityBrowserActivity.class);
        intent.putExtra("bundle",b);
        context.startActivity(intent);
    }

    /**
     * 跳转到我的里程碑h5
     * @param context
     * @param b
     */

    public static void gotoMyMilePost(Context context, Bundle b){
        Intent intent = new Intent(context, MyMilepostActivityBrowserActivity.class);
        intent.putExtra("bundle",b);
        context.startActivity(intent);
    }

    /**
     * 跳转到我的项目修改h5
     * @param context
     * @param b
     */

    public static void gotoModificationPj(Context context, Bundle b){
        Intent intent = new Intent(context, ModificationProjectBrowserActivity.class);
        intent.putExtra("bundle",b);
        context.startActivity(intent);
    }

    /**
     * 跳转到项目详情(加载h5页面)
     * @param context
     */

    public static void gotoBrowser(Context context){
        Intent intent = new Intent(context, BrowserActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转到新增项目
     * @param context
     */

    public static void gotoAddItem(Context context){
        Intent intent = new Intent(context, AddItemActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转到登录页面
     * @param context
     */

    public static void gotoLogin(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转到选择省，市页
     * @param act
     */
    public static final int CITY_REQUEST = 0201;
    public static void city(Activity act){
        Intent intent = new Intent(act, ChoiceCityActivity.class);
        act.startActivityForResult(intent, CITY_REQUEST);
    }

    /**
     * 跳转到添加资金用途
     * @param context
     * @param b
     */
    public static void increase(Context context, Bundle b){
        Intent intent = new Intent(context, IncreaseCapitalActivity.class);
        intent.putExtra("bundle",b);
        context.startActivity(intent);
    }

    /**
     * 跳转到修改资金用途
     * @param context
     * @param b
     */
    public static void mdCapital(Context context, Bundle b){
        Intent intent = new Intent(context, ModifyCapitalActivity.class);
        intent.putExtra("bundle",b);
        context.startActivity(intent);
    }


    /**
     * 跳转到资金用途详情
     * @param context
     * @param b
     */

    public static void gotoDetailsFunds(Context context, Bundle b){
        Intent intent = new Intent(context, DetailsFundsActivity.class);
        intent.putExtra("bundle",b);
        context.startActivity(intent);
    }

}
