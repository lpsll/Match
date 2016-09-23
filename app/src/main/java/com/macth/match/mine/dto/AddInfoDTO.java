package com.macth.match.mine.dto;

import java.io.File;

/**
 * Created by Administrator on 2016/8/27.
 * 参数：usermobile-----用户手机号----必填
 * username-------用户名称------必填
 * identity-------用户身份
 * company--------公司名称
 * work-----------职务
 * cooperative----协同角色
 * userimg--------用户名片
 */
public class AddInfoDTO {

    private String usermobile;
    private String username;
    private String identity;
    private String company;
    private String work;
    private String cooperative;
    private File userimg;

    public File getUserimg() {
        return userimg;
    }

    public void setUserimg(File userimg) {
        this.userimg = userimg;
    }

    public String getUsermobile() {
        return usermobile;
    }

    public void setUsermobile(String usermobile) {
        this.usermobile = usermobile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getCooperative() {
        return cooperative;
    }

    public void setCooperative(String cooperative) {
        this.cooperative = cooperative;
    }

}
