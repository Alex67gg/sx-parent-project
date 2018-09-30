package com.shengxun.mapper;

import com.shengxun.entity.IndustryUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IndustryUserMapper {
    public int deleteByPrimaryKey(Long id);

    public int insert(IndustryUser record);

    public IndustryUser selectByPrimaryKey(Long id);

    public List<IndustryUser> selectListByUserId(Long userId);

    public int update(IndustryUser record);

    public List<IndustryUser> selectSeqListByUserId (Long id);

}