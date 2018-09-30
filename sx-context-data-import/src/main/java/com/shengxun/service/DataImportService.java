package com.shengxun.service;

/**
 * Created by chenwei
 * on 2018/6/15 17:53.
 */
public interface DataImportService {
    /**
     * 导入数据
     * @param templateId
     * @param userId
     * @param isSys
     */
    void dataImport(Long templateId, String userId, Integer isSys);
}