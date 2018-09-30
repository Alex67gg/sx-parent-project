package com.shengxun.mapper;

import com.shengxun.entity.Context;
import com.shengxun.entity.Context;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ContextMapper {

    int deleteByPlateId(Long plateId);

    int deleteByPrimaryKey(Long id);

    int insert(Context record);

    Context selectByPrimaryKey(Long id);

    List<Context> selectListByPlateId(@Param("plateId")Long plateId ,@Param("level")Integer level);

    List<Context> selectListByContextId(@Param("contextId")Long contextId,@Param("level")int level);

    int updateByPrimaryKey(Context record);
}