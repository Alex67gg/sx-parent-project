package com.shengxun.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ApiModel(value="行业对象",description="行业industry")
public class IndustryAll implements Serializable{
    private Long id;
    /**
     * 行业名称
     */
    @ApiModelProperty(value="行业名称",name="industryName",required=true,example="房产")
    private String industryName;
    /**
     * 行业排序
     */
    private Integer industrySort;
    /**
     * 创建时间
     */
    @JsonFormat( timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(hidden=true)
    @JsonFormat( timezone = "GMT+8" ,pattern = "yyyy-MM-dd HH:mm")
    private Date updateTime;
    /**
     * 行业层级
     */
    @ApiModelProperty(hidden=true)
    private Integer level;
    /**
     * 状态
     */
    private Integer status;

    private List<TemplateAll> templateAlls;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public Integer getIndustrySort() {
        return industrySort;
    }

    public void setIndustrySort(Integer industrySort) {
        this.industrySort = industrySort;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<TemplateAll> getTemplateAlls() {
        return templateAlls;
    }

    public void setTemplateAlls(List<TemplateAll> templateAlls) {
        this.templateAlls = templateAlls;
    }
}