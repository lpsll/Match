package com.macth.match.mine.dto;

import com.macth.match.common.dto.BaseDTO;

/**
 * Created by Administrator on 2016/8/28.
 * 参数：userid-------------------用户id
 * noticeid-----------------消息id
 */
public class DeleteNewDTO extends BaseDTO {

    private String noticeid;

    public String getNoticeid() {
        return noticeid;
    }

    public void setNoticeid(String noticeid) {
        this.noticeid = noticeid;
    }
}
