package com.macth.match.login.entity;

import com.macth.match.common.utils.SecurityUtils;

/**
 * Created by Administrator on 2016/8/25.
 * 参数：account---用户账号
 userpwd---用户密码【md5加密后字符串】
 */
public class LoginDTO {

    private  String account;
    private String userpwd;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUserpwd() {
        return userpwd;
    }

    public void setUserpwd(String userpwd) {
        this.userpwd = SecurityUtils.md5(userpwd);
    }
}
