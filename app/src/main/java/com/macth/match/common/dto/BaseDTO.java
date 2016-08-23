package com.macth.match.common.dto;

import java.io.Serializable;

/**
 * Created by John_Libo on 2016/8/23.
 */
public class BaseDTO implements Serializable {
    private String userid;
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }


}
