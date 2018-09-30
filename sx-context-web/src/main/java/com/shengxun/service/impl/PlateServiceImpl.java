package com.shengxun.service.impl;

import com.shengxun.entity.Context;
import com.shengxun.entity.Dialogue;
import com.shengxun.entity.Plate;
import com.shengxun.enums.ContextLevel;
import com.shengxun.mapper.ContextMapper;
import com.shengxun.mapper.DialogueMapper;
import com.shengxun.mapper.PlateMapper;
import com.shengxun.service.PlateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ldh on 2018/6/12.
 */
@Service
public class PlateServiceImpl implements PlateService {

    @Autowired
    private PlateMapper plateMapper;

    @Autowired
    private ContextMapper contextMapper;

    @Autowired
    private DialogueMapper dialogueMapper;

    /**
     * 删除
     * 板块
     * 板块下的语境
     * 语境下的对话
     *
     * @param id
     * @return
     */
    public int deleteByPrimaryKey(Long id) {
        List<Context> Contexts = contextMapper.selectListByPlateId(id, ContextLevel.getValue(ContextLevel.STAIR));
        if (!CollectionUtils.isEmpty(Contexts)) {
            for (Context scy : Contexts) {
                Long contextId = scy.getId();
                dialogueMapper.deleteByContextId(contextId);//语境下的对话
            }
        }
        contextMapper.deleteByPlateId(id);//板块下的语境
        return plateMapper.deleteByPrimaryKey(id);//删除板块
    }

    @Override
    public int insert(Plate record) {
        return plateMapper.insert(record);
    }

    @Override
    public Plate selectByPrimaryKey(Long id) {
        return plateMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(Plate record) {
        return plateMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<Plate> selectListByTempId(Long tempId) {
        return plateMapper.selectListByTempId(tempId);
    }

    @Override
    public void InsertPlates(List<Plate> plates, Integer isSys, Long templateId, AtomicInteger ai, String videoName) {
        if (!CollectionUtils.isEmpty(plates)) {
            for (Plate plate : plates) {
                plate.setIsSys(isSys);
                plate.setTemplateId(templateId);
                plateMapper.insert(plate);
                Long plateId = plate.getId();
                /**
                 * 新增语境
                 */
                insertContext(plate.getContexts(), plateId, ai, videoName);
            }
        }
    }

    @Override
    public List<Plate> updateOrInsertPlates(List<Plate> plates, Integer isSys, Long templateId, AtomicInteger ai, String videoName) {
        for (Plate plate : plates) {
            plate.setIsSys(isSys);
            Long plateId = plate.getId();
            /**
             * 修改板块
             */
            if (plateId != null) {
                updatePlant(plate, ai, videoName);
            } else {
                /**
                 * 新增板块
                 */
                plate.setTemplateId(templateId);
                plateMapper.insert(plate);
                plateId = plate.getId();
                /**
                 * 新增语境
                 */
                insertContext(plate.getContexts(), plateId, ai, videoName);
            }
        }
        return plates;
    }


    /**
     * 修改板块
     */
    public void updatePlant(Plate plate, AtomicInteger ai, String videoName) {
        plateMapper.updateByPrimaryKey(plate);
        List<Context> contextList = plate.getContexts();
        updateOrInsertContext(contextList, plate.getId(), ai, videoName);
    }

    /**
     * 修改语境
     */
    public void updateOrInsertContext(List<Context> contextList, Long plateId, AtomicInteger ai, String videoName) {
        if (!CollectionUtils.isEmpty(contextList)) {
            for (Context context : contextList) {
                context.setPlateId(plateId);
                context.setLevel(1);
                if (context.getId() == null) {
                    contextMapper.insert(context);
                } else {
                    contextMapper.updateByPrimaryKey(context);
                }
                updateOrInsertDialogue(context.getDialogues(), context.getId(), ai, videoName);
                updateOrInsertSonContext(context.getSonContext(), plateId, context.getId(), ai, videoName);
            }
        }
    }

    /**
     * 修改二级语境
     */
    public void updateOrInsertSonContext(List<Context> sonContextList, Long plateId, Long parentId, AtomicInteger ai, String videoName) {
        if (!CollectionUtils.isEmpty(sonContextList)) {
            for (Context context : sonContextList) {
                context.setPlateId(plateId);
                context.setParentId(parentId);
                context.setLevel(2);
                if (context.getId() == null) {
                    contextMapper.insert(context);
                } else {
                    contextMapper.updateByPrimaryKey(context);
                }
                updateOrInsertDialogue(context.getDialogues(), context.getId(), ai, videoName);
            }
        }
    }

    /**
     * 修改对话
     */
    public void updateOrInsertDialogue(List<Dialogue> dialogueList, Long contextId, AtomicInteger ai, String videoName) {
        if (!CollectionUtils.isEmpty(dialogueList)) {
            for (Dialogue dialogue : dialogueList) {
                ai.getAndIncrement();
                dialogue.setContextId(contextId);
                dialogue.setSort(ai.get());
                dialogue.setVideoName(videoName + ai.get());
                if (dialogue.getId() == null) {
                    dialogueMapper.insert(dialogue);
                } else {
                    dialogueMapper.updateByPrimaryKey(dialogue);
                }
            }
        }
    }

    /**
     * 判断一级语境是否存在,若是则新增一级语境及二级语境
     */
    public void insertContext(List<Context> contexts, Long plateId, AtomicInteger ai, String videoName) {
        if (!CollectionUtils.isEmpty(contexts)) {
            for (Context context : contexts) {
                context.setPlateId(plateId);
                context.setLevel(1);
                contextMapper.insert(context);           //新增一级语境
                this.insertDialogue(context.getDialogues(), context.getId(), ai, videoName);         //新增对话
                this.insertSonContext(context.getSonContext(), plateId, context.getId(), ai, videoName);     //新增二级语境及二级语境的对话
            }
        }
    }

    /**
     * 新增二级语境
     */
    public void insertSonContext(List<Context> sonContexts, Long plateId, Long parentId, AtomicInteger ai, String videoName) {
        if (!CollectionUtils.isEmpty(sonContexts)) {
            for (Context sonContext : sonContexts) {
                sonContext.setPlateId(plateId);
                sonContext.setParentId(parentId);
                sonContext.setLevel(2);
                contextMapper.insert(sonContext);       //新增二级语境
                this.insertDialogue(sonContext.getDialogues(), sonContext.getId(), ai, videoName);      //二级语境的对话
            }
        }
    }

    /**
     * 添加对话
     */
    public void insertDialogue(List<Dialogue> dialogues, Long contextId, AtomicInteger ai, String videoName) {
        if (!CollectionUtils.isEmpty(dialogues)) {
            for (Dialogue dialogue : dialogues) {
                ai.getAndIncrement();
                dialogue.setContextId(contextId);
                dialogue.setSort(ai.get());
                dialogue.setVideoName(videoName + ai.get());
                dialogueMapper.insert(dialogue);
            }
        }
    }


}