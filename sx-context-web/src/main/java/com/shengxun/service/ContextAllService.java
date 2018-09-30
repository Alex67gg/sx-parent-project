package com.shengxun.service;

import com.shengxun.entity.ContextAll;
import com.shengxun.result.vo.ContextAllVo;
import com.shengxun.result.vo.ContextAllsVo;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ldh on 2018/6/12.
 */
public interface ContextAllService {


    /**
     * 查询所有的全局语境
     *
     * @return
     */
    List<ContextAll> selectAll();

    /**
     * 修改全局语境
     *
     * @param contextAll
     */
    void updateContextAll(ContextAll contextAll) throws Exception;

    /**
     * 修改单个全局语境
     *
     * @param contextAlls
     */
    void updateContextAlls(HashMap<Integer, ContextAllsVo> contextAlls) throws Exception;

    /**
     * 添加全局语境
     *
     * @param contextAllVo
     */
    void addContextAll(HashMap<Integer, ContextAllsVo> contextAllVo) throws Exception;

    /**
     * 根据id删除全局语境
     *
     * @param contextAll
     */
    void delContextAll(ContextAll contextAll);

    /**
     * @param temp_id 模板id
     * @param type    类型   0全局模板，1用户模板
     * @param flag    标记 1问题未识别 2一般语句未识别 3要求重复 4超时 5委婉拒绝
     * @return
     */
    HashMap<Integer, Object> selectContextAllByAll(Long temp_id, Integer type, Integer flag);

    /**
     * 根据语境id查询语境；
     *
     * @param id
     * @return
     */
    ContextAll selectContextById(Long id);

    /**
     * 上传文件后修改文件路径
     *
     * @param
     */
    void updateFileName(Long id, String path, String filename);
}
