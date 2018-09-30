package com.shengxun.result.vo;

import io.swagger.annotations.ApiModelProperty;

public class DataVo {

    @ApiModelProperty(value="userId",name = "userId",required=true,example="用户id,如果是操作全局行业则为null")
    private Long userId;
    @ApiModelProperty(value="name",name = "name",example="名称")
    private String name;
    @ApiModelProperty(value="类型",name = "type",required=true,example="类型 0为全局行业，1位用户行业")
    private Long type;

    public DataVo() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public DataVo(Long userId, String name, Long type) {
        this.userId = userId;
        this.name = name;
        this.type = type;
    }
}
