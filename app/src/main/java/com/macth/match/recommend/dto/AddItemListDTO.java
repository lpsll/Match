package com.macth.match.recommend.dto;

import com.macth.match.common.dto.BaseDTO;

/**
 * Created by John_Libo on 2016/8/25.
 */
public class AddItemListDTO extends BaseDTO {
    private String constantType;
    public String getConstantType() {
        return constantType;
    }

    public void setConstantType(String constantType) {
        this.constantType = constantType;
    }


}
