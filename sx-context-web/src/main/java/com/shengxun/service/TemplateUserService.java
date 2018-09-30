package com.shengxun.service;

import com.shengxun.entity.TemplateUser;

import java.util.List;

/**
 * Created by ldh on 2018/6/13.
 */
public interface TemplateUserService {

    int deleteByPrimaryKey(Long id);

    int insert(TemplateUser record);

    TemplateUser selectByPrimaryKey(Long id);

    TemplateUser selectFullByPrimaryKey(Long templateId);

    int updateByPrimaryKey(TemplateUser record);

    /**
     * 根据用户的行业id，查询该行业下的所有模板；
     *
     * @param id
     * @return
     */
    List<TemplateUser> selectByFoderId(Long id);
}
