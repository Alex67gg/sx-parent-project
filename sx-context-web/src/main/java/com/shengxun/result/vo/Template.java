package com.shengxun.result.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shengxun.entity.Plate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by ldh on 2018/7/4.
 */
@ApiModel(value="全局话术模板对象",description="全局话术模板template")
public class Template implements Serializable {

    /**
     * 模板ID
     */
    private Long id;
    /**
     * 模板名称
     */
    @ApiModelProperty(value="模板名称",name="name",required=true,example="房屋租赁")
    private String name;
    /**
     * 模板状态
     */
    private Integer status;
    /**
     * 行业Id或者是目录Id
     */
    @ApiModelProperty(value="行业Id或者是目录Id",name="industryId",example="1")
    private Long industryId;
    /**
     * 编辑时间
     */
    @ApiModelProperty(hidden=true)
    @JsonFormat( timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm")
    private Date updateTime;
    /**
     * 新增时间
     */
    @ApiModelProperty(hidden=true)
    @JsonFormat( timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;
    /**
     * 模板排序
     */
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
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<Plate> getPlates() {
        return plates;
    }

    public void setPlates(List<Plate> plates) {
        this.plates = plates;
    }

    public Long getIndustryId() {
        return industryId;
    }

    public void setIndustryId(Long industryId) {
        this.industryId = industryId;
    }

    public Template() {
    }

    public Template(Long id, String name, Integer status, Long industryId, Date updateTime, Date createTime, Integer sort, List<Plate> plates) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.industryId = industryId;
        this.updateTime = updateTime;
        this.createTime = createTime;
        this.sort = sort;
        this.plates = plates;
    }

    @Override
    public String toString() {
        return "Template{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", industryId=" + industryId +
                ", updateTime=" + updateTime +
                ", createTime=" + createTime +
                ", sort=" + sort +
                ", plates=" + plates +
                '}';
    }
}