package com.shengxun.service.impl;

import com.shengxun.entity.*;
import com.shengxun.enums.ResultEnum;
import com.shengxun.execption.DataImportException;
import com.shengxun.mapper.*;
import com.shengxun.utils.ConnectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ChenWei
 * on 2018/7/5 13:57.
 */
@Service
public class DataBaseService {
    /**
     * 整合其他的导入service类,做事务处理.
     */
    @Autowired
    private TemplateAllMapper templateAllMapper;
    @Autowired
    private TemplateUserMapper templateUserMapper;
    @Autowired
    private ContextAllMapper contextAllMapper;
    @Autowired
    private IndustryAllMapper industryAllMapper;
    @Autowired
    private IndustryUserMapper industryUserMapper;
    @Autowired
    private AnsDataService ansDataService;
    @Autowired
    private CmpNoService cmpNoService;
    @Autowired
    private FlowService flowService;
    @Autowired
    private InfoService infoService;
    @Autowired
    private PublicDataService publicDataService;
    @Autowired
    private WordToSoundService wordToSoundService;

    /**
     * 生成数据
     *
     * @param sourcePath
     * @param templateId
     * @param isSys
     */
    public void createData(String sourcePath, Long templateId, Integer isSys) {
        try {
            Connection connection = ConnectionUtils.getCurrentConnection(sourcePath);
            HashMap<String, Object> templateMap = getPlatesAndTemplateNameByTemplateIdAndIsSys(templateId, isSys);
            List<Plate> plates = (List<Plate>) templateMap.get("plates");
            String templateName = (String) templateMap.get("templateName");
            Long industryId = (Long) templateMap.get("industryId");
            String industryName = getIndustryNameByIndustryId(industryId, isSys);
            List<ContextAll> contextAllList = getContextAllByTemplateId(templateId, isSys);
            //把Auto commit设置为false,不让它自动提交
            connection.setAutoCommit(false);
            createTables(connection);
            ansDataService.insertAnsData(connection, plates, sourcePath);//A类  --> b类 map
            cmpNoService.insertCmpNo(connection, plates);
            flowService.insertFlow(connection, plates);
            infoService.insertInfo(connection, industryName, templateName);
            publicDataService.insertPublicData(connection, contextAllList);
            wordToSoundService.insertWordToSound(connection);// B类 -->map
            ConnectionUtils.commitConn();
        } catch (Exception e) {
            ConnectionUtils.rollBackConn();
            throw new DataImportException(ResultEnum.CREATE_DATA_WRONG, e);
        }
    }

    /**
     * 创建表
     *
     * @param connection
     * @throws SQLException
     */
    public void createTables(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        //创建流程表 <模版表>
        statement.executeUpdate("CREATE TABLE REQ_ANS_FLOW (ID INTEGER PRIMARY KEY AUTOINCREMENT,LEVEL INTEGER,FLOOR INTEGER,QUESTION VARCHAR2 (520),NAME VARCHAR2 (128),TYPE VARCHAR2 (10));");
        //创建主表 <语境和对话>
        statement.executeUpdate("CREATE TABLE REQ_ANS_DATA (ID INTEGER UNIQUE PRIMARY KEY AUTOINCREMENT,NAME VARCHAR2 (128),CONDITION VARCHAR2 (520),QUESTION VARCHAR2 (520),ANSWER VARCHAR2 (520),NEXTNAME VARCHAR2 (128),WEIGHT VARCHAR2 (32),SCORE VARCHAR2 (128),RECORD VARCHAR (64),PRIORITY VARCHAR (4),LEVEL VARCHAR2 (128),SKIP_CONDITION VARCHAR (128),SKIP_TO VARCHAR2 (126));");
        //创建全局语境表 <全局语境表>
        statement.executeUpdate("CREATE TABLE REQ_PUBLIC_DATA (ID INTEGER UNIQUE PRIMARY KEY AUTOINCREMENT,NAME VARCHAR2 (128),WEIGHT VARCHAR2 (32),CONDITION VARCHAR2 (520),ANSWER VARCHAR2 (520),SCORE VARCHAR2 (128),NEXTNAME VARCHAR2 (128),QUESTION VARCHAR (520),LEVEL VARCHAR,PRIORITY VARCHAR (4),RECORD VARCHAR (64),SKIP_CONDITION VARCHAR2 (64),SKIP_TO VARCHAR (64));");
        //创建词性表 <板块,语境的 关键字>
        statement.executeUpdate("CREATE TABLE CMP_NO (ID INTEGER UNIQUE PRIMARY KEY AUTOINCREMENT,NAME VARCHAR2 (128),TYPE VARCHAR2 (32),ITYPE INTEGER);");
        //创建行业表 <行业表,模版表>
        statement.executeUpdate("CREATE TABLE DATABASE_INFO (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME VARCHAR (128),INFO VARCHAR (128));");
        //创建录音文件表
        statement.executeUpdate("CREATE TABLE WORD_TO_SOUND (ID INTEGER PRIMARY KEY AUTOINCREMENT,SPATH VARCHAR2 (128),WORD  VARCHAR2 (520));");
    }

    /**
     * 查询板块集合
     *
     * @param templateId
     * @param isSys
     * @return
     */
    public HashMap<String, Object> getPlatesAndTemplateNameByTemplateIdAndIsSys(Long templateId, Integer isSys) {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        List<Plate> plates = null;
        String templateName = null;
        Long industryId = null;
        if (isSys == 0) {
            TemplateAll templateAll = templateAllMapper.selectFullByPrimaryKey(templateId);
            if (templateAll != null) {
                plates = templateAll.getPlates();
                templateName = templateAll.getName();
                industryId = templateAll.getIndustryAllId();
            }
        } else {
            TemplateUser templateUser = templateUserMapper.selectFullByPrimaryKey(templateId);
            if (templateUser != null) {
                plates = templateUser.getPlates();
                templateName = templateUser.getName();
                industryId = templateUser.getIndustryUserId();
            }
        }
        if (plates == null) {
            throw new DataImportException(ResultEnum.PLATES_IS_NULL);
        }
        if (industryId == null || industryId == 0) {
            throw new DataImportException(ResultEnum.INDUSTRY_ID_IS_NULL);
        }
        if (StringUtils.isBlank(templateName)) {
            templateName = "没有模版名";
        }
        hashMap.put("plates", plates);
        hashMap.put("templateName", templateName);
        hashMap.put("industryId", industryId);
        return hashMap;
    }

    /**
     * 查询全局语境集合
     *
     * @param templateId
     * @param type
     * @return
     */
    public List<ContextAll> getContextAllByTemplateId(Long templateId, Integer type) {
        List<ContextAll> contextAllList = contextAllMapper.selectContextAllByTemplateIdAndType(templateId, type);
        return contextAllList;
    }

    /**
     * 获取industryName
     */
    public String getIndustryNameByIndustryId(Long industryId, Integer isSys) {
        String industryName = null;
        if (isSys == 0) {
            IndustryAll industryAll = industryAllMapper.selectByPrimaryKey(industryId.intValue());
            industryName = industryAll.getIndustryName();
        } else {
            IndustryUser industryUser = industryUserMapper.selectByPrimaryKey(industryId);
            industryName = industryUser.getFolderName();
        }
        if (StringUtils.isBlank(industryName)) {
            industryName = "没有项目名";
        }
        return industryName;
    }
}
