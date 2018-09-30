package com.shengxun.service.impl;

import com.shengxun.entity.TemplateUser;
import com.shengxun.enums.UserTemplateStatus;
import com.shengxun.mapper.TemplateUserMapper;
import com.shengxun.service.TemplateUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ldh on 2018/6/13.
 */
@Service
public class TemplateUserServiceImpl implements TemplateUserService {


    @Autowired
    private TemplateUserMapper templateUserMapper;


    @Override
    public int deleteByPrimaryKey(Long id) {
        return templateUserMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(TemplateUser record) {
        return templateUserMapper.insert(record);
    }

    @Override
    public TemplateUser selectByPrimaryKey(Long id) {
        return templateUserMapper.selectByPrimaryKey(id, UserTemplateStatus.getValue(UserTemplateStatus.DEL));
    }

    @Override
    public TemplateUser selectFullByPrimaryKey(Long templateId) {
        return templateUserMapper.selectFullByPrimaryKey(templateId);
    }

    @Override
    public int updateByPrimaryKey(TemplateUser record) {
        return templateUserMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<TemplateUser> selectByFoderId(Long id) {
        return templateUserMapper.selectByFoderId(id);
    }
}