package com.macth.match.recommend.entity;

import com.macth.match.common.entity.BaseEntity;

import java.io.File;
import java.util.List;

/**
 * Created by John_Libo on 2016/8/25.
 */
public class FundsData extends BaseEntity{
    private String fundid;
    private String funds_desc;
    private String funds_companyaddrress;
    private String funds_lbs;
    private String funds_images;

    public String getFunds_lbsimage() {
        return funds_lbsimage;
    }

    public void setFunds_lbsimage(String funds_lbsimage) {
        this.funds_lbsimage = funds_lbsimage;
    }

    private String funds_lbsimage;
    String[] imageurl;

    public String[] getImageurl() {
        return imageurl;
    }

    public void setImageurl(String[] imageurl) {
        this.imageurl = imageurl;
    }








    public String getFundid() {
        return fundid;
    }

    public void setFundid(String fundid) {
        this.fundid = fundid;
    }

    public String getFunds_desc() {
        return funds_desc;
    }

    public void setFunds_desc(String funds_desc) {
        this.funds_desc = funds_desc;
    }

    public String getFunds_companyaddrress() {
        return funds_companyaddrress;
    }

    public void setFunds_companyaddrress(String funds_companyaddrress) {
        this.funds_companyaddrress = funds_companyaddrress;
    }

    public String getFunds_lbs() {
        return funds_lbs;
    }

    public void setFunds_lbs(String funds_lbs) {
        this.funds_lbs = funds_lbs;
    }

    public String getFunds_images() {
        return funds_images;
    }

    public void setFunds_images(String funds_images) {
        this.funds_images = funds_images;
    }



}
