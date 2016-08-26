package com.macth.match.recommend.dto;

import com.macth.match.common.dto.BaseDTO;

/**
 * Created by John_Libo on 2016/8/26.
 */
public class MinestoneDetailsDTO extends BaseDTO {
    private String projectID;
    private String cooperativeID;
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


}
