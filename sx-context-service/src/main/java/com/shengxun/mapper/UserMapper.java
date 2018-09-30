package com.shengxun.mapper;

import com.shengxun.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(Long id);

    List<User> selectUser();

    List<User> selectByStatus(Integer status, List<User> users);

    List<User> selectUserByCondition(@Param(value = "searchName") String searchName,
                                     @Param(value = "pageSize") Integer pageSize,
                                     @Param(value = "pageNum") Integer pageNum);

    Integer updateTemplateStatus(@Param(value = "template_id") Long template_id,
                                 @Param(value = "status") Integer status);
    List<User> selectCompanyName(@Param(value = "cooperative_partner") String cooperative_partner);

}