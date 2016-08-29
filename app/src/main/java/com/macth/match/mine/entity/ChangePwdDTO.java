package com.macth.match.mine.entity;

import com.macth.match.common.utils.SecurityUtils;

/**
 * Created by Administrator on 2016/8/29.
 * 参数：userid-------用户当前id
 useroldpwd---用户旧密码【md5加密后字符串】
 usernewpwd---用户新密码【md5加密后字符串】
 */
public class ChangePwdDTO {

    private String userid;
    private String useroldpwd;
    private String usernewpwd;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUseroldpwd() {
        return useroldpwd;
    }

    public void setUseroldpwd(String useroldpwd) {
        this.useroldpwd = SecurityUtils.md5(useroldpwd);
    }

    public String getUsernewpwd() {
        return usernewpwd;
    }

    public void setUsernewpwd(String usernewpwd) {
        this.usernewpwd = SecurityUtils.md5(usernewpwd);
    }
}
