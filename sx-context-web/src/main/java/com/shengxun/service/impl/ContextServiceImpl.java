package com.shengxun.service.impl;

import com.shengxun.entity.Context;
import com.shengxun.enums.ContextLevel;
import com.shengxun.mapper.ContextMapper;
import com.shengxun.mapper.DialogueMapper;
import com.shengxun.service.ContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ldh on 2018/6/12.
 */
@Service
public class ContextServiceImpl implements ContextService {

    @Autowired
    private ContextMapper contextMapper;

    @Autowired
    private DialogueMapper dialogueMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        dialogueMapper.deleteByContextId(id);
        return contextMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Context record) {
        return contextMapper.insert(record);
    }

    @Override
    public Context selectByPrimaryKey(Long id) {
        return contextMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(Context record) {
        return contextMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<Context> selectListByPlateId(Long plateId) {
        return contextMapper.selectListByPlateId(plateId, ContextLevel.getValue(ContextLevel.STAIR));
    }

    @Override
    public List<Context> selectListByContextId(Long contextId) {
        return contextMapper.selectListByContextId(contextId, ContextLevel.getValue(ContextLevel.ACCESS));
    }
}