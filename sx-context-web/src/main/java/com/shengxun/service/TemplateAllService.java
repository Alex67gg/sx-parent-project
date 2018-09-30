package com.shengxun.service;

import com.shengxun.entity.TemplateAll;

import java.util.List;

/**
 * Created by ldh on 2018/6/11.
 */
public interface TemplateAllService {

    int deleteByPrimaryKey(Long id);

    int insert(TemplateAll record);

    TemplateAll selectByPrimaryKey(Long id);

    TemplateAll selectFullByPrimaryKey(Long id);

    int updateByPrimaryKey(TemplateAll record);

    /**
     * 根据全局行业id获取 下面的所有模板
     *
     * @param id 全局行业id
     * @return
     */

    List<TemplateAll> selectByIndustry_idAll(Long id);

    /**
     * 根据id修改模板的状态；
     *
     * @param id
     */
    void updateStatusById(Long id, Integer status);


    void insertQJMB(TemplateAll record);

    void updateNameById(Long id, String name);
}
