package com.macth.match.register.entity;

/**
 * Created by Administrator on 2016/8/26.
 * 参数：usermobile---用户手机号
 useryzm------短信验证码
 */
public class ForgetPwdDTO {

    private String usermobile;
    private String useryzm;

    public String getUsermobile() {
        return usermobile;
    }

    public void setUsermobile(String usermobile) {
        this.usermobile = usermobile;
    }

    public String getUseryzm() {
        return useryzm;
    }

    public void setUseryzm(String useryzm) {
        this.useryzm = useryzm;
    }
}
