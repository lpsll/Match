package com.macth.match.recommend.dto;

import com.macth.match.common.dto.BaseDTO;

/**
 * Created by John_Libo on 2016/9/1.
 */
public class DownloadDTO extends BaseDTO {
    private String projectID;
    private String cooperativeID;
    private String milepostID;
    private String userID;

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public String getCooperativeID() {
        return cooperativeID;
    }

    public void setCooperativeID(String cooperativeID) {
        this.cooperativeID = cooperativeID;
    }

    public String getMilepostID() {
        return milepostID;
    }

    public void setMilepostID(String milepostID) {
        this.milepostID = milepostID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }


}
