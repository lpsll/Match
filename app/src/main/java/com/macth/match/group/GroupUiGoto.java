package com.macth.match.group;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.macth.match.login.activity.LoginActivity;

/**
 * Created by John_Libo on 2016/9/19.
 */
public class GroupUiGoto {

    /**
     * 跳转到登录页面
     * @param context
     */
    public static final int LG_REQUEST = 0x3100;
    public static void gotoLogin(Activity activity){
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivityForResult(intent,LG_REQUEST);
    }
}
