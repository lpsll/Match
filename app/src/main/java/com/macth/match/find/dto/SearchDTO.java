package com.macth.match.find.dto;

/**
 * Created by Administrator on 2016/8/29.
 * 参数：search-------搜索字段
 * page--------当前页码
 */
public class SearchDTO {
    private String search;
    private String page;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
