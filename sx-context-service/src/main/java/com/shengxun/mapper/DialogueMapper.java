package com.shengxun.mapper;

import com.shengxun.entity.Dialogue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface DialogueMapper {
    int deleteByPrimaryKey(Long id);

    int deleteByContextId(Long contextId);

    int insert(Dialogue record);

    Dialogue selectByPrimaryKey(Long id);

    ArrayList<Dialogue> selectDialogueListByTemplateId(@Param("templateId") Long templateId, @Param("templateStatus") Integer templateStatus);

    ArrayList<Dialogue> selectDialogueListByUserTemplateId(@Param("userTemplateId") Long userTemplateId,@Param("userTemplateStatus") Integer userTemplateStatus);

    int updateByPrimaryKey(Dialogue record);

    int selectLastSeqByTemplateId(@Param("templateId") Long templateId);
}