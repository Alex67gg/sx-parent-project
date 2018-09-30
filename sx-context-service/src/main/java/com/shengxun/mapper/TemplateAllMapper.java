package com.shengxun.mapper;

import com.shengxun.entity.TemplateAll;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TemplateAllMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TemplateAll template);

    TemplateAll selectByPrimaryKey(@Param("id")Long id,@Param("status") Integer status);

    TemplateAll selectFullByPrimaryKey(@Param(value = "templateId") Long templateId);

    int updateByPrimaryKey(TemplateAll record);

    List<TemplateAll> selectByIndustryId(Long id);

    void updateStatus(@Param(value = "id") Long id , @Param(value = "status") Integer status);

    void updateReName(@Param(value = "id") Long id,  @Param(value = "name") String newName);

}