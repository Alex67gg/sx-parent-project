package com.shengxun.service;

import com.shengxun.entity.Dialogue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ldh on 2018/6/12.
 */
public interface DialogueService {

    int deleteByPrimaryKey(Long id);

    int insert(Dialogue record);

    Dialogue selectByPrimaryKey(Long id);

    ArrayList<Dialogue> selectDialogueListByTemplateId(Long templateId);

    ArrayList<Dialogue> selectDialogueListByUserTemplateId(Long userTemplateId);

    int updateByPrimaryKey(Dialogue record);

    int selectLastSeqByTemplateId(Long templateId);
}
