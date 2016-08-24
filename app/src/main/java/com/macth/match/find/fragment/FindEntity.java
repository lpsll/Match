package com.macth.match.find.fragment;

import com.macth.match.common.entity.BaseEntity;

/**
 * Created by Administrator on 2016/8/24.
 */
public class FindEntity extends BaseEntity {

/**
 * "pid": "6",----------------------------项目id
 "companyname": "北京用友科技",---------公司名称
 "price": "1000",-----------------------金额
 "project_termunit": "1",---------------1 年 2 月
 "project_termvalue": "20",-------------对应数值这个就是20年
 "ctime": "2016-08-08",-----------------项目发布日期
 "image": "http://cuohe.damaimob.com/upload/projects/57a8239dc8a36.jpg",-----图片
 "project_type":"公司债",
 "project_status": "1"-----------------1 进行中 2 关闭
 }
 ],
 */

    private int pid;
    private String companyname;
    private String  price;
    private String project_termunit;
    private String project_termvalue;
    private String ctime;
    private String image;
    private String project_type;
    private int project_status;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
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

    public int getProject_status() {
        return project_status;
    }

    public void setProject_status(int project_status) {
        this.project_status = project_status;
    }

    @Override
    public String toString() {
        return "FindEntity{" +
                "pid=" + pid +
                ", companyname='" + companyname + '\'' +
                ", price='" + price + '\'' +
                ", project_termunit='" + project_termunit + '\'' +
                ", project_termvalue='" + project_termvalue + '\'' +
                ", ctime='" + ctime + '\'' +
                ", image='" + image + '\'' +
                ", project_type='" + project_type + '\'' +
                ", project_status=" + project_status +
                '}';
    }
}
