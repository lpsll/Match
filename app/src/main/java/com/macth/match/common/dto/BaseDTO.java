package com.macth.match.common.dto;

import java.io.Serializable;

/**
 * Created by John_Libo on 2016/8/23.
 */
public class BaseDTO implements Serializable {
    private String userID;
    private String userid;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }


    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }





}
