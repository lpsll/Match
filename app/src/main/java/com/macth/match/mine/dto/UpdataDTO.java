package com.macth.match.mine.dto;

import com.macth.match.common.dto.BaseDTO;

/**
 * Created by John_Libo on 2016/9/20.
 */
public class UpdataDTO extends BaseDTO {
    private String projectID;
    private String milepostID;
    private String cooperativeID;
    public String getMilepostID() {
        return milepostID;
    }

    public void setMilepostID(String milepostID) {
        this.milepostID = milepostID;
    }

    public String getCooperativeID() {
        return cooperativeID;
    }

    public void setCooperativeID(String cooperativeID) {
        this.cooperativeID = cooperativeID;
    }


    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }




}
