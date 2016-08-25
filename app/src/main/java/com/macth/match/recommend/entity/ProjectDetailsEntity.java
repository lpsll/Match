package com.macth.match.recommend.entity;

import com.macth.match.common.entity.BaseEntity;

/**
 * Created by John_Libo on 2016/8/24.
 */
public class ProjectDetailsEntity extends BaseEntity {
    private String resolution_true;
    private String resolution_government;
    private String assure_companyname;
    private String assure_itemrate;
    private String assure_ztrate;
    private String mortgage_ratevalue;
    private String mortgage_desc;
    private String other_desc;

    public String getResolution_true() {
        return resolution_true;
    }

    public void setResolution_true(String resolution_true) {
        this.resolution_true = resolution_true;
    }

    public String getResolution_government() {
        return resolution_government;
    }

    public void setResolution_government(String resolution_government) {
        this.resolution_government = resolution_government;
    }

    public String getAssure_companyname() {
        return assure_companyname;
    }

    public void setAssure_companyname(String assure_companyname) {
        this.assure_companyname = assure_companyname;
    }

    public String getAssure_itemrate() {
        return assure_itemrate;
    }

    public void setAssure_itemrate(String assure_itemrate) {
        this.assure_itemrate = assure_itemrate;
    }

    public String getAssure_ztrate() {
        return assure_ztrate;
    }

    public void setAssure_ztrate(String assure_ztrate) {
        this.assure_ztrate = assure_ztrate;
    }

    public String getMortgage_ratevalue() {
        return mortgage_ratevalue;
    }

    public void setMortgage_ratevalue(String mortgage_ratevalue) {
        this.mortgage_ratevalue = mortgage_ratevalue;
    }

    public String getMortgage_desc() {
        return mortgage_desc;
    }

    public void setMortgage_desc(String mortgage_desc) {
        this.mortgage_desc = mortgage_desc;
    }

    public String getOther_desc() {
        return other_desc;
    }

    public void setOther_desc(String other_desc) {
        this.other_desc = other_desc;
    }
}
