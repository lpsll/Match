package com.macth.match.recommend.dto;

import com.macth.match.common.dto.BaseDTO;

/**
 * Created by John_Libo on 2016/9/21.
 */
public class UploadDTO extends BaseDTO {
    private String projectno;
    private String desc;
    private String address;
    private String lbs;
    private String lbsimg;
    private String pimg;
    public String getProjectno() {
        return projectno;
    }

    public void setProjectno(String projectno) {
        this.projectno = projectno;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLbs() {
        return lbs;
    }

    public void setLbs(String lbs) {
        this.lbs = lbs;
    }

    public String getLbsimg() {
        return lbsimg;
    }

    public void setLbsimg(String lbsimg) {
        this.lbsimg = lbsimg;
    }

    public String getPimg() {
        return pimg;
    }

    public void setPimg(String pimg) {
        this.pimg = pimg;
    }


}
