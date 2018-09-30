package com.shengxun.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ApiModel(value="板块对象",description="plate")
public class Plate implements Serializable {
    private Long id;
    /**
     * 模板Id
     */
    private Long templateId;
    /**
     * 板块名称
     */
    @ApiModelProperty(value="板块名称",name="name",required=true,example="问候语")
    private String name;
    /**
     * 板块类型（0为流程对话板块2为结束对话板块4为挽回板块6为问题库）
     */
    @ApiModelProperty(value="板块类型0为流程对话板块2为结束对话板块4为挽回板块6为问题库",name="type",required=true,example="0")
    private Integer type;

    /**
     * 排序
     */
    private Long sort;
    /**
     * 0系统板块1为用户模块对应查询的是user_template'
     */
    @ApiModelProperty(value="0系统板块1为用户模块'",name="isSys",required=true,example="0")
    private Integer isSys;
    @ApiModelProperty(hidden=true)
    @JsonFormat( pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;
    @ApiModelProperty(hidden=true)
    @JsonFormat( pattern = "yyyy-MM-dd HH:mm")
    private Date updateTime;

    /**
     * 关键字
     */
    private String keyWord;

    /**
     * 语境集合
     */
    private List<Context> contexts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public Integer getIsSys() {
        return isSys;
    }

    public void setIsSys(Integer isSys) {
        this.isSys = isSys;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<Context> getContexts() {
        return contexts;
    }

    public void setContexts(List<Context> contexts) {
        this.contexts = contexts;
    }

    public Plate() {
    }

    public Plate(Long id, Long templateId, String name, Integer type, Long sort, Integer isSys, Date createTime, Date updateTime, String keyWord, List<Context> contexts) {
        this.id = id;
        this.templateId = templateId;
        this.name = name;
        this.type = type;
        this.sort = sort;
        this.isSys = isSys;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.keyWord = keyWord;
        this.contexts = contexts;
    }

    @Override
    public String toString() {
        return "Plate{" +
                "id=" + id +
                ", templateId=" + templateId +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", sort=" + sort +
                ", isSys=" + isSys +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", keyWord='" + keyWord + '\'' +
                ", contexts=" + contexts +
                '}';
    }
}