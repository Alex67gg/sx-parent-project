package com.shengxun.result.vo;

import io.swagger.annotations.ApiModelProperty;

public class DataIndustryDelVo {
    @ApiModelProperty(value = "industryId",name = "industryId",example = "行业节点id")
    private Long industryId;
    @ApiModelProperty(value = "type",name = "type",example = "type=0全局行业，type=1用户行业")
    private Long type;


    public DataIndustryDelVo() {
    }

    public Long getIndustryId() {
        return industryId;
    }

    public void setIndustryId(Long industryId) {
        this.industryId = industryId;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }
}
