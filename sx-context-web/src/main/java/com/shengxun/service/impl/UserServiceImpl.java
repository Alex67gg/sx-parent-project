package com.shengxun.service.impl;

import com.shengxun.entity.User;
import com.shengxun.mapper.UserMapper;
import com.shengxun.service.UserService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * create by qq on 2018/6/13
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> selectUserByCondition(String searchName, Integer pageSize, Integer pageNum) {
        List<User> users = userMapper.selectUserByCondition(searchName, pageSize, pageNum);
        for (User user : users) {
            List<User> users1 = userMapper.selectByPrimaryKey(user.getId());
            Set<Integer> isRepeat = new HashSet<Integer>();
            boolean flag = false;
            for (User template : users1) {
                flag = isRepeat.add(template.getStatus());
                if (template.getStatus() != null && template.getStatus() != -1) {
                    if (flag) {
                        user.setTemplate_id(template.getTemplate_id());
                        user.setStatus(template.getStatus());
                    } else {
                        user.setStatus(1);
                    }
                } else {
                    user.setStatus(4);
                }
            }
        }
        return users;
    }

    @Override
    public List<User> selectUser() {
        return userMapper.selectUser();
    }

    @Override
    public Integer updateTemplateStatus(Long template_id, Integer status) {
        return userMapper.updateTemplateStatus(template_id, status);
    }

    @Override
    public List<User> selectByPrimaryKey(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<User> selectByStatus(Integer status, List<User> users) {
        List<User> list = new ArrayList<User>();
        for (User user : users) {
            if (user.getStatus() == status){
                list.add(user);
            }
        }
        return list;
    }

    @Override
    public List<User> selectCompanyName(String cooperative_partner) {
        return userMapper.selectCompanyName(cooperative_partner);
    }
}