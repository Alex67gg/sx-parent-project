package com.shengxun.service;

import com.shengxun.entity.IndustryAll;

import java.util.List;

/**
 * Created by ldh on 2018/6/11.
 */
public interface IndustryAllService {

    int deleteByPrimaryKey(Integer id);

    int insert(IndustryAll record);

    IndustryAll selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(IndustryAll record);

    List<IndustryAll> selectList();

    List<IndustryAll> selectIndustryTreeList() throws Exception;


    void updateStatus(Long id, Integer status);

    void updateReNameById(Long id, String name);
}
