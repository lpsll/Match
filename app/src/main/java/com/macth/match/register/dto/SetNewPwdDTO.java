package com.macth.match.register.dto;

import com.macth.match.common.utils.SecurityUtils;

/**
 * Created by Administrator on 2016/8/26.
 * 参数：usermobile---用户账号
 * userpwd---用户密码【md5加密后字符串】
 */
public class SetNewPwdDTO {

    private String usermobile;
    private String userpwd;

    public String getUsermobile() {
        return usermobile;
    }

    public void setUsermobile(String usermobile) {
        this.usermobile = usermobile;
    }

    public String getUserpwd() {
        return userpwd;
    }

    public void setUserpwd(String userpwd) {
        this.userpwd = SecurityUtils.md5(userpwd);
    }
}
