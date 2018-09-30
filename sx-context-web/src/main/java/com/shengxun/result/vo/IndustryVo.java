package com.shengxun.result.vo;

import io.swagger.annotations.ApiModelProperty;

public class IndustryVo {
    @ApiModelProperty(value="industryId",name = "industryId",required=true,example="操作的节点id")
    private Long industryId;
    @ApiModelProperty(value="name",name = "name",required=true,example="重命名后的新名称")
    private String name;
    @ApiModelProperty(value="type",name = "type",required=true,example="类型 0为全局行业，1位用户行业")
    private Long type;

    public IndustryVo() {
    }

    public Long getIndustryId() {
        return industryId;
    }

    public void setIndustryId(Long industryId) {
        this.industryId = industryId;
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
}
