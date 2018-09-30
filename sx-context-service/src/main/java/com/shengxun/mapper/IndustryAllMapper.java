package com.shengxun.mapper;

import com.shengxun.entity.IndustryAll;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IndustryAllMapper {
    public int deleteByPrimaryKey(Integer id);

    public   int insert(IndustryAll record);

    public IndustryAll selectByPrimaryKey(Integer id);

    public  int updateByPrimaryKey(IndustryAll record);

    public List<IndustryAll> selectList();

    /**
     * 根据id修改状态；
     * @param id
     * @param status
     */
    public void updateStatus(@Param(value = "id") Long id,@Param(value = "status") Integer status);

    /**
     * 根据id修改全局行业的名称；
     * @param id
     * @param name
     */
    public void updateNameById(@Param(value = "id") Long id,@Param(value = "name") String name);


}