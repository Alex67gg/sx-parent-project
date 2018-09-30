package com.shengxun.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.org.apache.xalan.internal.xsltc.compiler.Template;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 *话术目录实体
 */
import java.util.Date;
import java.util.List;

@ApiModel(value="用户话术对象",description="用户话术目录folder")
public class IndustryUser {
    private Long id;

    /**
     *目录名称
     */
    @ApiModelProperty(value="目录名称",name="folderName",required=true,example="房产销售目录")
    private String folderName;
    /**
     * 用户Id
     */
    @ApiModelProperty(value="用户Id",name="userId",required=true)
    private Long userId;

    /**
     * 排序
     */
    private String folderSort;
    /**
     * 状态
     */
    private String folderStatus;
    @ApiModelProperty(hidden=true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date updateTime;
    @ApiModelProperty(hidden=true)
    @JsonFormat( pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;
    /**
     * 层级
     */
    private Integer level;

    private List<TemplateUser> list;

    public void setList(List<TemplateUser> list) {
        this.list = list;
    }

    public List<TemplateUser> getList() {
        return list;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName == null ? null : folderName.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    public String getFolderSort() {
        return folderSort;
    }

    public void setFolderSort(String folderSeq) {
        this.folderSort = folderSort == null ? null : folderSeq.trim();
    }

    public String getFolderStatus() {
        return folderStatus;
    }

    public void setFolderStatus(String folderStatus) {
        this.folderStatus = folderStatus == null ? null : folderStatus.trim();
    }

    public Date getUpateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}