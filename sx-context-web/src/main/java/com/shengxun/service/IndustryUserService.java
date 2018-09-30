package com.shengxun.service;

import com.shengxun.entity.IndustryUser;

import java.util.List;

/**
 * Created by ASUS on 2018/6/9.
 */
public interface IndustryUserService {

    public int deleteByPrimaryKey(Long id);

    public int insert(IndustryUser record);

    public IndustryUser selectByPrimaryKey(Long id);

    public List<IndustryUser> selectListByUserId(Long userId);

    public int update(IndustryUser record);

    public List<IndustryUser> getUserTreeListById(Long id);

}
