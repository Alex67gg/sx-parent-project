package com.shengxun.service.impl;

import com.shengxun.entity.TemplateAll;
import com.shengxun.enums.TemplateStatus;
import com.shengxun.mapper.TemplateAllMapper;
import com.shengxun.service.TemplateAllService;
import com.shengxun.utils.GeneratorVideoVame;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ldh on 2018/6/11.
 */
@Service
public class TemplateAllServiceImpl implements TemplateAllService {

    @Autowired
    private TemplateAllMapper templateAllMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return templateAllMapper.deleteByPrimaryKey(id);
    }

    @Autowired
    private GeneratorVideoVame generatorVideoVame;

    /**
     * 保存话术模板
     */
    @Override
    public int insert(TemplateAll record) {
        return templateAllMapper.insert(record);//保存模板
    }

    @Override
    public TemplateAll selectByPrimaryKey(Long id) {
        return templateAllMapper.selectByPrimaryKey(id, TemplateStatus.getValue(TemplateStatus.del));
    }

    @Override
    public TemplateAll selectFullByPrimaryKey(Long id) {
        TemplateAll stq = templateAllMapper.selectFullByPrimaryKey(id);
        return stq;
    }

    @Override
    public int updateByPrimaryKey(TemplateAll record) {
        return templateAllMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<TemplateAll> selectByIndustry_idAll(Long id) {
        List<TemplateAll> TemplateAlls = templateAllMapper.selectByIndustryId(id);
        return TemplateAlls;
    }

    @Override
    public void updateStatusById(@Param(value = "id") Long id, @Param(value = "status") Integer status) {
        templateAllMapper.updateStatus(id, status);
    }

    @Override
    public void insertQJMB(TemplateAll record) {
        templateAllMapper.insert(record);
    }

    @Override
    public void updateNameById(Long id, String name) {
        templateAllMapper.updateReName(id, name);
    }


}