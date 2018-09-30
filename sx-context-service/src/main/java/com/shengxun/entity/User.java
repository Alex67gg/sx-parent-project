package com.shengxun.entity;

import io.swagger.annotations.ApiModel;

@ApiModel(value="用户对象",description="user")
public class User {
    private Long id;
    /**
     * 合作商
     */
    private String cooperative_partner;
    /**
     * 登录名称
     */
    private String login_name;
    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 联系人
     */
    private String contect_person;

    /**
     *企业全称
     */
    private String company_name;

    /**
     * 用户模板状态
     */
    private Integer status;
    /**
     * 当前页
     */
    private Integer pageNum;
    /**
     * 页面大小
     */
    private Integer pageSize;
    /**
     * 模板id
     */
    private Long template_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCooperative_partner() {
        return cooperative_partner;
    }

    public void setCooperative_partner(String cooperative_partner) {
        this.cooperative_partner = cooperative_partner;
    }

    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContect_person() {
        return contect_person;
    }

    public void setContect_person(String contect_person) {
        this.contect_person = contect_person;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(Long template_id) {
        this.template_id = template_id;
    }

}