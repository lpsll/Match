package com.macth.match.login.entity;

/**
 * Created by Administrator on 2016/8/25.
 *
 * "username": "崔丹锋",---------用户名
 "usermobile": "18210013505",---用户手机号
 "useridentity": "内部用户",-----用户身份
 "usercompany": "醉美国际网络技术有限责任公司",
 "userwork": "IOS工程师",
 "usertoken": "3"-----------用户Token，
 "cooperativeid":"1"--------协同角色id
 */
public class Data {

    private String username;
    private String usermobile;
    private String useridentity;
    private String usercompany;
    private String userwork;
    private String usertoken;
    private String cooperativeid;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsermobile() {
        return usermobile;
    }

    public void setUsermobile(String usermobile) {
        this.usermobile = usermobile;
    }

    public String getUseridentity() {
        return useridentity;
    }

    public void setUseridentity(String useridentity) {
        this.useridentity = useridentity;
    }

    public String getUsercompany() {
        return usercompany;
    }

    public void setUsercompany(String usercompany) {
        this.usercompany = usercompany;
    }

    public String getUserwork() {
        return userwork;
    }

    public void setUserwork(String userwork) {
        this.userwork = userwork;
    }

    public String getUsertoken() {
        return usertoken;
    }

    public void setUsertoken(String usertoken) {
        this.usertoken = usertoken;
    }

    public String getCooperativeid() {
        return cooperativeid;
    }

    public void setCooperativeid(String cooperativeid) {
        this.cooperativeid = cooperativeid;
    }
}
