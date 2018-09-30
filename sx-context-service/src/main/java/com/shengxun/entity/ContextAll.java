package com.shengxun.entity;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * 全局语境表
 */
public class ContextAll implements Serializable{

    private Long id;

    @ApiModelProperty(value = "用户id,如果是全局模板中的全局语境此值为null")
    private Long user_Id;

    @ApiModelProperty(value = "模板类型（0全局模板，1用户模板）")
    private Integer type;

    @ApiModelProperty(value = "1问题未识别 2一般语句未识别 3要求重复 4超时 5委婉拒绝")
    private Integer flag;

    @ApiModelProperty(value = "所属语境中的第几层")
    private Integer context_layer;

    @ApiModelProperty(value = "第几次")
    private Integer layer_next;

    @ApiModelProperty(value="循环次数只有要求重复时候字段有值")
    private Integer for_count;

    @ApiModelProperty(value = "所属模板id")
    private Long template_id;

    private Date create_times; //创建时间；

    private Date update_time; //修改时间；
    private Integer status;     //状态-1为删除；

    @ApiModelProperty(value = "关键字使用逗号分割")
    private String key_word;   //关键字 只有3要求重复  时字段有值

    private String video_url;  //音频文件的url

    private String video_name;  //和语境内容对应的音频名称；

    private Date video_create_time;  //音频文件上传时间

    private Date video_update_time;  //音频修改时间；

    private String context; //语境内容

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getVideo_name() {
        return video_name;
    }

    public void setVideo_name(String video_name) {
        this.video_name = video_name;
    }

    public Date getVideo_create_time() {
        return video_create_time;
    }

    public void setVideo_create_time(Date video_create_time) {
        this.video_create_time = video_create_time;
    }

    public Date getVideo_update_time() {
        return video_update_time;
    }

    public void setVideo_edit_time(Date video_edit_time) {
        this.video_update_time = video_edit_time;
    }

    public String getKey_word() {
        return key_word;
    }

    public void setKey_word(String key_word) {
        this.key_word = key_word;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getContext_Layer() {
        return context_layer;
    }

    public void setContext_Layer(Integer context_layer) {
        this.context_layer = context_layer;
    }

    public Integer getLayer_next() {
        return layer_next;
    }

    public void setLayer_next(Integer layer_next) {
        this.layer_next = layer_next;
    }


    public Integer getFor_count() {
        return for_count;
    }

    public void setFor_count(Integer for_count) {
        this.for_count = for_count;
    }

    public Long getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(Long template_id) {
        this.template_id = template_id;
    }

    public Date getCreate_times() {
        return create_times;
    }

    public void setCreate_times(Date create_times) {
        this.create_times = create_times;
    }

    public Date getUpate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getUser_Id() {
        return user_Id;
    }

    public void setUser_Id(Long user_Id) {
        this.user_Id = user_Id;
    }

    public ContextAll() {
    }

}
