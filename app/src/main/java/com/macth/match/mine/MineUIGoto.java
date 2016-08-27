package com.macth.match.mine;

import android.app.Activity;
import android.content.Intent;

import com.macth.match.login.activity.LoginActivity;

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


}
