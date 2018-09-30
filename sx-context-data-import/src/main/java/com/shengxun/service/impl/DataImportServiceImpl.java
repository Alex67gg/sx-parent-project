package com.shengxun.service.impl;

import com.shengxun.service.DataImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by chenwei
 * on 2018/6/15 17:54.
 */
@Service
public class DataImportServiceImpl implements DataImportService {

    @Value("${data.path.prefix}")
    private String prefix;
    @Value("${data.path.source}")
    private String source;
    @Value("${data.path.target}")
    private String target;

    @Autowired
    private DataBaseService dataBaseService;

    @Autowired
    private PackageFileService packageFileService;


    public void dataImport(Long templateId, String userId, Integer isSys) {
        if (!prefix.endsWith("/")) {
            prefix = prefix + "/";
        }
        String userPath = prefix + userId + "/" + isSys + "/" + templateId + "/";
        String sourcePath = userPath + source;
        String targetPath = userPath + target;
        dataBaseService.createData(sourcePath, templateId, isSys);
        packageFileService.packAndCompress(sourcePath, targetPath, userId, templateId);
    }

}
