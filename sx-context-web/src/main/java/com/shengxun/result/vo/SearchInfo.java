package com.shengxun.result.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * create by qq on 2018/7/5
 * 搜索用户信息对象封装
 */
public class SearchInfo {
    /**
     * 搜索关键字
     */
    @ApiModelProperty(
            value = "用户输入的搜索信息", name = "searchName",
            example = "用户输入信息,查询所有传null即可")
    private String searchName;
    /**
     * 页面大小
     */
    @ApiModelProperty(value = "页面大小",name = "pageSize",example = "10,如果不传值,默认也为10")
    private Integer pageSize;
    /**
     * 开始位置
     */
    @ApiModelProperty(value = "当前页面",name = "pageNum",example = "1,若不传值,默认为也为1")
    private Integer pageNum;
    /**
     * 用户模板状态
     */
    @ApiModelProperty(value = "用户模板状态",name = "status",example = "null")
    private Integer status;

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
