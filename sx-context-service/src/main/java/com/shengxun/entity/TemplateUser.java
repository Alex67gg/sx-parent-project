package com.shengxun.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ApiModel(value="用户话术模板",description="用户行业模板")
public class TemplateUser implements Serializable {
    private Long id;
    /**
     * 用户话术名称
     */
    @ApiModelProperty(value="用户话术名称",name="name",required=true,example="房屋租赁")
    private String name;
    /**
     * 用户话术状态(0为未审核2为审核通过4为审核不通过6停用8为启用)
     */
    private int status;
    /**
     * 所属用户目录ID
     */
    @ApiModelProperty(value="所属用户目录ID",name="industryUserId",required=true,example="1")
    private Long industryUserId;
    @ApiModelProperty(hidden=true)
    private Date createTime;
    @ApiModelProperty(hidden=true)
    private Date updateTime;

    private Integer sort;

    /**
     * 板块集合
     */
    private List<Plate> plates;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public List<Plate> getPlates() {
        return plates;
    }


    public TemplateUser() {
    }

    public TemplateUser(Long id, String name, int status, Long industryUserId, Date createTime, Date updateTime, Integer sort, List<Plate> plates) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.industryUserId = industryUserId;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.sort = sort;
        this.plates = plates;
    }

    public Long getIndustryUserId() {
        return industryUserId;
    }

    public void setIndustryUserId(Long industryUserId) {
        this.industryUserId = industryUserId;
    }

    public void setPlates(List<Plate> plates) {
        this.plates = plates;
    }

    @Override
    public String toString() {
        return "TemplateUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", industryUserId=" + industryUserId +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", sort=" + sort +
                ", plates=" + plates +
                '}';
    }
}