package com.macth.match.group;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.macth.match.login.activity.LoginActivity;
import com.macth.match.recommend.activity.ProjectDetailsActivity;

/**
 * Created by John_Libo on 2016/9/19.
 */
public class GroupUiGoto {

    /**
     * 跳转到登录页面
     * @param context
     */

    public static void gotoLogin(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }
}
