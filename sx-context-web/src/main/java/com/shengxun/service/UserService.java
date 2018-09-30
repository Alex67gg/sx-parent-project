package com.shengxun.service;

import com.shengxun.entity.User;

import java.util.List;

/**
 * create by qq on 2018/6/12
 */
public interface UserService {

    List<User> selectUser();

    List<User> selectUserByCondition(String searchName, Integer pageSize, Integer pageNum);

    Integer updateTemplateStatus(Long template_id, Integer status);

    List<User> selectByPrimaryKey(Long id);

    List<User> selectByStatus(Integer status, List<User> users);

    List<User> selectCompanyName(String cooperative_partner);

}