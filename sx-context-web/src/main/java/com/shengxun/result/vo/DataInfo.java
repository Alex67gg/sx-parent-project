package com.shengxun.result.vo;

import io.swagger.annotations.ApiModelProperty;

public class DataInfo {

    @ApiModelProperty(value = "用户的id",name = "userId",example = "用户的id,全局行业则userId为null即可")
    private Long userId;

    @ApiModelProperty(value="类型",name = "type",required=true,example="类型 0为全局行业，1位用户行业")
    private Long type;

    public DataInfo() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }
}
