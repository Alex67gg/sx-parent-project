package com.shengxun.result.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by ldh on 2018/7/4.
 */
@ApiModel(value="话术模板包装对象",description="话术模板包装对象")
public class TemplateVo implements Serializable {

    @ApiModelProperty(value="userId（用户id,如果是操作全局行业则为null）",name = "userId",example="1")
    private Long userId;

    @ApiModelProperty(value="类型（类型 0为全局行业，1位用户行业）",name = "type",required=true,example="0")
    private Integer type;

    @ApiModelProperty(value="模板对象",name = "template",required=true)
    private Template template;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public TemplateVo(Long userId, Integer type, Template template) {
        this.userId = userId;
        this.type = type;
        this.template = template;
    }

    public TemplateVo() {
    }

    @Override
    public String toString() {
        return "TemplateVo{" +
                "userId=" + userId +
                ", type=" + type +
                ", template=" + template +
                '}';
    }
}
