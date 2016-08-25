package com.macth.match.recommend;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.macth.match.login.activity.LoginActivity;
import com.macth.match.recommend.activity.AddItemActivity;
import com.macth.match.recommend.activity.ChoiceCityActivity;
import com.macth.match.recommend.activity.ProjectDetailsActivity;

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
     * 跳转到新增项目
     * @param context
     */

    public static void gotoAddItem(Context context){
        Intent intent = new Intent(context, AddItemActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转到新增项目
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
}
