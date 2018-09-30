package com.shengxun.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 语境表
 */
@ApiModel(value="语境对象",description="context")
public class Context implements Serializable {

    private Long id;
    /**
     * 所属板块Id
     */
    @ApiModelProperty(value="对话词",name="word")
    private Long plateId;
    /**
     * 父节点Id
     */
    private Long parentId;
    /**
     * 停顿时间
     */
    @ApiModelProperty(value="停顿时间(毫秒)",name="pauseTime",example="100")
    private Double pauseTime;
    /**
     * 语境类型(0为破题,2为肯定,4为中性,6为否定,8为拒绝,10成功结束,12挽回不成功结束,14拒绝结束)
     */
    @ApiModelProperty(value="语境类型0为破题,2为肯定,4为中性,6为否定,8为拒绝,10成功结束,12挽回不成功结束,14拒绝结束",name="type",required=true,example="0")
    private Integer type;
    /**
     * 下个语境Id
     */
    private Long nextContextId;
    /**
     * 下个板块ID
     */
    private Long nextPlateId;
    /**
     *  下一个流程(0进入'下一板块名称', 2对话后进入'下一板块名称 ,4进入挽回语境 ,6挽回对话 ,8进入结束语境)
     */
    @ApiModelProperty(value="下一个流程(0进入'下一板块名称', 2对话后进入'下一板块名称 ,4进入挽回语境 ,6挽回对话 ,8进入结束语境)",name="type",required=true,example="0")
    private Integer nextType;
    /**
     * 层级
     */
    private Integer level;
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
     * 下一个语境
     */
    private Context context;

    /**
     * 下一个板块
     */
    private Plate plate;

    /**
     * 对话
     */
    private List<Dialogue> dialogues;

    /**
     * 二级语境
     */
    private List<Context> sonContext;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlateId() {
        return plateId;
    }

    public void setPlateId(Long plateId) {
        this.plateId = plateId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Double getPauseTime() {
        return pauseTime;
    }

    public void setPauseTime(Double pauseTime) {
        this.pauseTime = pauseTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getNextContextId() {
        return nextContextId;
    }

    public void setNextContextId(Long nextContextId) {
        this.nextContextId = nextContextId;
    }

    public Long getNextPlateId() {
        return nextPlateId;
    }

    public void setNextPlateId(Long nextPlateId) {
        this.nextPlateId = nextPlateId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
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

    public Context getContext() {
        return context;
    }

    public void setContext(Context sx_context_yj) {
        this.context = context;
    }

    public Plate getPlate() {
        return plate;
    }

    public void setSx_plate_bk(Plate sx_plate_bk) {
        this.plate = sx_plate_bk;
    }

    public List<Context> getSonContext() {
        return sonContext;
    }

    public void setSonContext(List<Context> sonContext) {
        this.sonContext = sonContext;
    }

    public void setPlate(Plate plate) {
        this.plate = plate;
    }

    public List<Dialogue> getDialogues() {
        return dialogues;
    }

    public void setDialogues(List<Dialogue> dialogues) {
        this.dialogues = dialogues;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Context() {
    }

    public Integer getNextType() {
        return nextType;
    }

    public void setNextType(Integer nextType) {
        this.nextType = nextType;
    }

    public Context(Long id, Long plateId, Long parentId, Double pauseTime, Integer type, Long nextContextId, Long nextPlateId, Integer nextType, Integer level, Date createTime, Date updateTime, String keyWord, Context context, Plate plate, List<Dialogue> dialogues, List<Context> sonContext) {
        this.id = id;
        this.plateId = plateId;
        this.parentId = parentId;
        this.pauseTime = pauseTime;
        this.type = type;
        this.nextContextId = nextContextId;
        this.nextPlateId = nextPlateId;
        this.nextType = nextType;
        this.level = level;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.keyWord = keyWord;
        this.context = context;
        this.plate = plate;
        this.dialogues = dialogues;
        this.sonContext = sonContext;
    }

    @Override
    public String toString() {
        return "Context{" +
                "id=" + id +
                ", plateId=" + plateId +
                ", parentId=" + parentId +
                ", pauseTime=" + pauseTime +
                ", type=" + type +
                ", nextContextId=" + nextContextId +
                ", nextPlateId=" + nextPlateId +
                ", nextType=" + nextType +
                ", level=" + level +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", keyWord='" + keyWord + '\'' +
                ", context=" + context +
                ", plate=" + plate +
                ", dialogues=" + dialogues +
                ", sonContext=" + sonContext +
                '}';
    }
}