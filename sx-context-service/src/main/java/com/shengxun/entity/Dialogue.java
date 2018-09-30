package com.shengxun.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
@ApiModel(value="对话对象",description="dialogue")
public class Dialogue implements Serializable {
    private Long id;
    /**
     * 对话词
     */
    @ApiModelProperty(value="对话词",name="word",required=true,example="房产")
    private String word;
    /**
     * 音频路径
     */
    private String videoUrl;
    /**
     * 音频名称
     */
    private String videoName;

    private Long contextId;

    private Integer sort;
    @ApiModelProperty(hidden=true)
    @JsonFormat( pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;
    @ApiModelProperty(hidden=true)
    @JsonFormat( pattern = "yyyy-MM-dd HH:mm")
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word == null ? null : word.trim();
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl == null ? null : videoUrl.trim();
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName == null ? null : videoName.trim();
    }

    public Long getContextId() {
        return contextId;
    }

    public void setContextId(Long contextId) {
        this.contextId = contextId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Dialogue{" +
                "id=" + id +
                ", word='" + word + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", videoName='" + videoName + '\'' +
                ", contextId=" + contextId +
                ", sort=" + sort +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public Dialogue(Long id, String word, String videoUrl, String videoName, Long contextId, Integer sort, Date createTime, Date updateTime) {
        this.id = id;
        this.word = word;
        this.videoUrl = videoUrl;
        this.videoName = videoName;
        this.contextId = contextId;
        this.sort = sort;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Dialogue() {
    }
}