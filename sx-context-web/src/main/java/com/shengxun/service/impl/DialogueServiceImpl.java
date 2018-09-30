package com.shengxun.service.impl;

import com.shengxun.entity.Dialogue;
import com.shengxun.enums.TemplateStatus;
import com.shengxun.enums.UserTemplateStatus;
import com.shengxun.mapper.DialogueMapper;
import com.shengxun.service.DialogueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by ldh on 2018/6/12.
 */
@Service
public class DialogueServiceImpl implements DialogueService {

    @Autowired
    private DialogueMapper dialogueMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return dialogueMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Dialogue record) {
        return dialogueMapper.insert(record);
    }

    @Override
    public Dialogue selectByPrimaryKey(Long id) {
        return dialogueMapper.selectByPrimaryKey(id);
    }

    @Override
    public ArrayList<Dialogue> selectDialogueListByTemplateId(Long templateId) {
        return dialogueMapper.selectDialogueListByTemplateId(templateId, TemplateStatus.getValue(TemplateStatus.del));
    }

    @Override
    public ArrayList<Dialogue> selectDialogueListByUserTemplateId(Long userTemplateId) {
        return dialogueMapper.selectDialogueListByUserTemplateId(userTemplateId, UserTemplateStatus.getValue(UserTemplateStatus.DEL));
    }

    @Override
    public int updateByPrimaryKey(Dialogue record) {
        return dialogueMapper.updateByPrimaryKey(record);
    }

    /**
     * 获取模板下所有对话的最后的一个seq值
     *
     * @param templateId
     */
    @Override
    public int selectLastSeqByTemplateId(Long templateId) {
        return dialogueMapper.selectLastSeqByTemplateId(templateId);
    }
}