package com.macth.match.recommend.entity;

import com.macth.match.common.entity.BaseEntity;

/**
 * Created by John_Libo on 2016/8/24.
 */
public class ProjectDetailsResult extends BaseEntity {
    ProjectDetailsData data;
    public ProjectDetailsData getData() {
        return data;
    }

    public void setData(ProjectDetailsData data) {
        this.data = data;
    }


}
