package com.shengxun.service;

import com.shengxun.entity.Context;

import java.util.List;

/**
 * Created by ldh on 2018/6/12.
 */
public interface ContextService {

    int deleteByPrimaryKey(Long id);

    int insert(Context record);

    Context selectByPrimaryKey(Long id);

    List<Context> selectListByPlateId(Long plateId);

    List<Context> selectListByContextId(Long contextId);

    int updateByPrimaryKey(Context record);
}
