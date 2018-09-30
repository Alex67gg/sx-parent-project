package com.shengxun.service.impl;


import com.shengxun.domian.AnsData;
import com.shengxun.entity.Context;
import com.shengxun.entity.Dialogue;
import com.shengxun.entity.Plate;
import com.shengxun.enums.ResultEnum;
import com.shengxun.execption.DataImportException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by chenwei
 * on 2018/6/22 13:35.
 */
@Service
public class AnsDataService {
    /**
     * 查询mysql中 板块表,语境表,对话表 某些字段 导入到 sqlite中的主表对应字段.
     */

    @Autowired
    private WordToSoundService wordToSoundService;


    public void insertAnsData(Connection connection, List<Plate> plates, String sourcePath) throws SQLException, IOException {
        HashMap<Long, String> contextHashMap = new HashMap();
        HashMap<Long, String> dialogueHashMap = new HashMap();
        this.setMap(plates, contextHashMap, dialogueHashMap);
        List<AnsData> ansDataList = this.getAnsDataList(plates, sourcePath, contextHashMap, dialogueHashMap);
        this.insertReqAnsData(ansDataList, connection);
    }

    public List<AnsData> getAnsDataList(List<Plate> plates, String sourcePath,
                                        HashMap<Long, String> contextHashMap, HashMap<Long, String> dialogueHashMap
    ) throws SQLException, IOException {
        List<AnsData> ansDataList = new ArrayList<AnsData>();
        for (int i = 0; i < plates.size(); i++) {
            String keyWord = null;
            keyWord = plates.get(i).getKeyWord();
            List<Context> contexts = plates.get(i).getContexts();
            for (Context context : contexts) {
                String keyWord1 = context.getKeyWord();
                Integer type = context.getType();
                if ((type != 0) && (keyWord1 != null)) {
                    keyWord = keyWord1;
                }
                Long contextId = context.getId();
                List<Dialogue> dialogues = context.getDialogues();
                Long nextContextId = context.getNextContextId();
                Long nextPlateId = context.getNextPlateId();
                //判断语境下是否是多对话
                if ((dialogues != null) && (dialogues.size() > 1)) {
                    for (int j = 0; j < dialogues.size(); j++) {
                        String skipCondition = "0";
                        String skipTo = "0";
                        String record = null;
                        String videoUrl = null;
                        String nextName = null;
                        String word = null;
                        String name = null;
                        videoUrl = dialogues.get(j).getVideoUrl();
                        word = dialogues.get(j).getWord();
                        name = getNameByDialogueId(dialogues.get(j).getId(), dialogueHashMap);
                        record = wordToSoundService.getRecordByVideoUrlAndWord(sourcePath, videoUrl, word);
                        //判断当前是否是多对话中最后一个
                        if (j != (dialogues.size() - 1)) {
                            nextName = getNameByDialogueId(dialogues.get(j).getId(), dialogueHashMap);
                            skipCondition = getNameByDialogueId(dialogues.get(j).getId(), dialogueHashMap);
                            skipTo = getNameByDialogueId(dialogues.get(j + 1).getId(), dialogueHashMap);
                        } else {
                            if (type == 0) {
                                nextName = ".null";

                            } else {
                                nextName = "#";
                            }
                        }
                        ansDataList.add(new AnsData(name, keyWord, word, nextName, record, skipCondition, skipTo));
                    }
                } else {
                    String skipCondition = "0";
                    String skipTo = "0";
                    String record = null;
                    String videoUrl = null;
                    String nextName = null;
                    String word = null;
                    String name = null;
                    videoUrl = dialogues.get(1).getVideoUrl();
                    word = dialogues.get(1).getWord();
                    record = wordToSoundService.getRecordByVideoUrlAndWord(sourcePath, videoUrl, word);
                    name = getNameByCIdOrPid(contextId, contextHashMap);
                    if (i != (plates.size() - 1)) {
                        nextName = getNextNameByCIdAndPId(nextContextId, nextPlateId, contextHashMap);
                    } else {
                        nextName = "#";
                    }
                    ansDataList.add(new AnsData(name, keyWord, word, nextName, record, skipCondition, skipTo));
                }
            }

        }
        return ansDataList;
    }

    //将所有数据导入到map中
    public void setMap(List<Plate> plates, HashMap<Long, String> contextHashMap, HashMap<Long, String> dialogueHashMap) {
        for (int i = 0; i < plates.size(); i++) {
            setContextHashMap(plates.get(i).getContexts(), (i + 1), contextHashMap, dialogueHashMap);
        }
    }

    //设置next_name
    public String getNextNameByCIdAndPId(Long nextContextId, Long nextPlateId, HashMap<Long, String> contextHashMap) {
        do {
            if (nextContextId > 0) {
                //1.nextContextId 有值
                return getNameByCIdOrPid(nextContextId, contextHashMap);
            } else if (nextPlateId > 0) {
                //2.nextPlateId 有值
                return getNameByCIdOrPid(nextPlateId, contextHashMap);
            } else {
                //3.都没值,捕获异常.
                throw new DataImportException(ResultEnum.GET_NEXT_ID_WRONG);
            }
        } while (false);

    }

    /**
     * 获取单个对话当前的name.
     */
    public String getNameByCIdOrPid(Long contextId, HashMap<Long, String> contextHashMap) {
        String name = contextHashMap.get(contextId);
        if (StringUtils.isNotBlank(name)) {
            return name;
        } else {
            return ".null";
        }
    }

    /**
     * 获取多个对话的name
     */
    public String getNameByDialogueId(Long dialogueId, HashMap<Long, String> dialogueHashMap) {
        String dialogueName = dialogueHashMap.get(dialogueId);
        return dialogueName;
    }

    /**
     * 查询每个板块下的语境,并设置到map里面.(key 是 语境id + 对话的id, value是 板块索引+语境)
     *
     * @param contexts
     * @param plateIndex
     * @return
     */
    public HashMap<Long, String> setContextHashMap(List<Context> contexts, Integer plateIndex,
                                                   HashMap<Long, String> contextHashMap, HashMap<Long, String> dialogueHashMap) {
        for (Context context : contexts) {
            Long contextId = context.getId();
            Integer level = context.getLevel();
            String flag = contestContentType(context.getType());
            if (level == 1) {
                //1级语境
                String name = plateIndex + flag;
                contextHashMap.put(contextId, name);
            } else {
                //2级语境
                String name = plateIndex + "." + level + flag;
                contextHashMap.put(contextId, name);
            }
            setDialogueHashMap(context, contextHashMap, dialogueHashMap);
        }
        return contextHashMap;
    }

    /**
     * 查询每个语境下有多个对话,并设置到map里面(key 是 对话id ,value是 板块索引+语境+ 当前对话索引
     */
    public HashMap<Long, String> setDialogueHashMap(Context context, HashMap<Long, String> contextHashMap, HashMap<Long, String> dialogueHashMap) {
        Long contextId = context.getId();
        List<Dialogue> dialogues = context.getDialogues();
        if (dialogues != null && dialogues.size() > 0) {
            String contextName = getNameByCIdOrPid(contextId, contextHashMap);
            for (int i = 0; i < dialogues.size(); i++) {
                do {
                    if (i == 0) {
                        dialogueHashMap.put(dialogues.get(i).getId(), contextName);
                        break;
                    }
                    dialogueHashMap.put(dialogues.get(i).getId(), contextName + "." + i);
                } while (false);
            }
        }
        return dialogueHashMap;
    }


    /**
     * 判断语境内容 语境类型(0为破题,2为肯定,4为中性,6为否定,8为拒绝)
     *
     * @param type
     * @return
     */
    public String contestContentType(Integer type) {
        String flag;
        switch (type) {
            case 0:
                flag = "";
                break;
            case 2:
                flag = ".sure";
                break;
            case 4:
                flag = ".neuter";
                break;
            case 6:
                flag = ".negative";
                break;
            case 8:
                flag = ".refuse";
                break;
            default:
                flag = ".spec";
                break;
        }
        return flag;
    }


    /**
     * 插入某条项目信息表数据
     *
     * @param ansDataList
     * @param connection
     * @throws SQLException
     */
    private void insertReqAnsData(List<AnsData> ansDataList, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("insert into REQ_ANS_DATA VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?);");
        for (AnsData ansData : ansDataList) {
            //语境标识 统一为 板块id+.spec
            preparedStatement.setString(2, ansData.getName());
            //关键词
            preparedStatement.setString(3, ansData.getKeyWord());
            //是否可以邀约默认为0
            preparedStatement.setString(4, "0");
            //回答语句
            preparedStatement.setString(5, ansData.getWord());
            //下个语境或者板块Id
            preparedStatement.setString(6, ansData.getNextName());
            //权重
            preparedStatement.setString(7, "1");
            //分数
            preparedStatement.setString(8, "0");
            //音频编号
            preparedStatement.setString(9, ansData.getRecord());
            //打断优先级
            preparedStatement.setString(10, "5");
            //状态
            preparedStatement.setString(11, "0");
            //跳转条件语境
            preparedStatement.setString(12, ansData.getSkipCondition());
            //跳转目的语境
            preparedStatement.setString(13, ansData.getSkipTo());
            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();
    }
}
