package com.macth.match.recommend.entity;

import com.macth.match.common.entity.BaseEntity;

import java.util.List;

/**
 * Created by John_Libo on 2016/8/24.
 */
public class ProjectDetailsData extends BaseEntity {
    private String project_no;
    private String project_user;
    private String project_companyname;
    private String project_province;
    private String project_city;
    private String project_projecttype;
    private String project_nature;
    private String project_price;
    private String project_termunit;
    private String project_termvalue;
    private String project_sincerity;
    private String project_bond;
    private String project_status;
    private String project_ctime;
    private String project_usertype;
    private String project_cost;
    ProjectDetailsEntity project_signceritynode;
    List<ProjectDetailsListEntity> project_fundslist;


    public List<ProjectDetailsListEntity> getProject_fundslist() {
        return project_fundslist;
    }

    public void setProject_fundslist(List<ProjectDetailsListEntity> project_fundslist) {
        this.project_fundslist = project_fundslist;
    }

    public ProjectDetailsEntity getProject_signceritynode() {
        return project_signceritynode;
    }

    public void setProject_signceritynode(ProjectDetailsEntity project_signceritynode) {
        this.project_signceritynode = project_signceritynode;
    }

    public String getProject_no() {
        return project_no;
    }

    public void setProject_no(String project_no) {
        this.project_no = project_no;
    }

    public String getProject_user() {
        return project_user;
    }

    public void setProject_user(String project_user) {
        this.project_user = project_user;
    }

    public String getProject_companyname() {
        return project_companyname;
    }

    public void setProject_companyname(String project_companyname) {
        this.project_companyname = project_companyname;
    }

    public String getProject_province() {
        return project_province;
    }

    public void setProject_province(String project_province) {
        this.project_province = project_province;
    }

    public String getProject_city() {
        return project_city;
    }

    public void setProject_city(String project_city) {
        this.project_city = project_city;
    }

    public String getProject_projecttype() {
        return project_projecttype;
    }

    public void setProject_projecttype(String project_projecttype) {
        this.project_projecttype = project_projecttype;
    }

    public String getProject_nature() {
        return project_nature;
    }

    public void setProject_nature(String project_nature) {
        this.project_nature = project_nature;
    }

    public String getProject_price() {
        return project_price;
    }

    public void setProject_price(String project_price) {
        this.project_price = project_price;
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

    public String getProject_sincerity() {
        return project_sincerity;
    }

    public void setProject_sincerity(String project_sincerity) {
        this.project_sincerity = project_sincerity;
    }

    public String getProject_bond() {
        return project_bond;
    }

    public void setProject_bond(String project_bond) {
        this.project_bond = project_bond;
    }

    public String getProject_status() {
        return project_status;
    }

    public void setProject_status(String project_status) {
        this.project_status = project_status;
    }

    public String getProject_ctime() {
        return project_ctime;
    }

    public void setProject_ctime(String project_ctime) {
        this.project_ctime = project_ctime;
    }

    public String getProject_usertype() {
        return project_usertype;
    }

    public void setProject_usertype(String project_usertype) {
        this.project_usertype = project_usertype;
    }

    public String getProject_cost() {
        return project_cost;
    }

    public void setProject_cost(String project_cost) {
        this.project_cost = project_cost;
    }




}


