package com.macth.match.recommend.entity;

import com.macth.match.common.entity.BaseEntity;

import java.util.List;

/**
 * Created by John_Libo on 2016/8/23.
 */
public class RecommendEntity extends BaseEntity {

        private String pid;
        private String companyname;
        private String price;
        private String project_termunit;
        private String project_termvalue;
        private String ctime;
        private String image;
        private String project_type;

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getCompanyname() {
            return companyname;
        }

        public void setCompanyname(String companyname) {
            this.companyname = companyname;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getProject_termunit() {
            return project_termunit;
        }

        public void setProject_termunit(String project_termunit) {
            this.project_termunit = project_termunit;
        }

        public String getProject_termvalue() {
            return project_termvalue;
        }

        public void setProject_termvalue(String project_termvalue) {
            this.project_termvalue = project_termvalue;
        }

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getProject_type() {
            return project_type;
        }

        public void setProject_type(String project_type) {
            this.project_type = project_type;
        }


}
