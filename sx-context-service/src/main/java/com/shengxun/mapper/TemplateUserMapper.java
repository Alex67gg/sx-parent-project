package com.shengxun.mapper;

import com.shengxun.entity.TemplateUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TemplateUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TemplateUser record);

    TemplateUser selectByPrimaryKey(@Param("id")Long id,@Param("status") Integer status);

    TemplateUser selectFullByPrimaryKey(@Param("templateId") Long templateId);

    int updateByPrimaryKey(TemplateUser record);

    List<TemplateUser>  selectSeqByFolderId (Long id);

    List<TemplateUser> selectByFoderId(Long id);
}