package com.shengxun.mapper;
import com.shengxun.entity.ContextAll;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ContextAllMapper {
    /**
     * 查询所有的全局语境
     * @return
     */
    List<ContextAll> selectAll();

    /**
     * 插入全局语境
     * @param contextAll
     */
    void insertContextAll(ContextAll contextAll);

    /**
     * 删除全局语境
     * @param contextAll
     */
    void delContextAll(ContextAll contextAll);

    /**
     * 修改全局语境
     * @param contextAll
     */
    void updateByPrimaryKey(ContextAll contextAll);

    /**
     * 根据不同的用户和模板和查询所有的全局语境；
     * @param
     * @return
     */
    List<ContextAll> selectContextAllByFlag(@Param(value = "template_id") Long template_id, @Param(value = "type") Integer type, @Param(value = "flag") Integer flag);

    /**
     * 根据id查询全局语境；
     * @param id
     * @return
     */
    ContextAll selectContextAllById(Long id);
    /**
     * 上传文件更新全局语境表
     * @param contextAll
     */
    void updateFileName(ContextAll contextAll);

    /**
     * 根据模版id 和 用户类型查询所有的全局语境
     * @param template_id
     * @param type
     * @return
     */
    List<ContextAll> selectContextAllByTemplateIdAndType(@Param(value = "template_id") Long template_id, @Param(value = "type") Integer type);

}