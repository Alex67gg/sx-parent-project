package com.shengxun.mapper;

import com.shengxun.entity.Plate;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PlateMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Plate record) ;

    Plate selectByPrimaryKey(Long id);

    List<Plate> selectListByTempId(Long tempId);

    int updateByPrimaryKey(Plate record);
}