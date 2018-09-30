package com.shengxun.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shengxun.domian.CmpNo;
import com.shengxun.entity.Plate;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CmpNoService {
    /**
     * 将项目中词性文件(cmp2.json),导入到sqlite词性表
     */
    public void insertCmpNo(Connection connection, List<Plate> plates) throws IOException, SQLException {
        List<CmpNo> cmpNos = dictionary();
        PreparedStatement preparedStatement = connection.prepareStatement("insert into CMP_NO VALUES (?,?,?,?);");
        for (CmpNo cmpNo : cmpNos) {
            preparedStatement.setInt(1, cmpNo.getId());
            preparedStatement.setString(2, cmpNo.getName());
            preparedStatement.setString(3, cmpNo.getType());
            preparedStatement.setInt(4, cmpNo.getItype());
            // 2) 将一组参数添加到此 PreparedStatement 对象的批处理命令中。
            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();
    }

    private static List<CmpNo> dictionary() throws IOException {
        List<CmpNo> cmpNos = new ArrayList<CmpNo>();
        String dataString = FileUtils.readFileToString(ResourceUtils.getFile("classpath:cmp2.json"), Charset.forName("UTF-8"));
        JSONObject jsonObject = JSONObject.parseObject(dataString);
        //最外层数组
        JSONArray rows = jsonObject.getJSONArray("rows");
        for (int i = 0; i < rows.size(); i++) {
            CmpNo cmpNo = new CmpNo();
            //里层数组
            JSONArray row = rows.getJSONArray(i);
            Integer id = row.getInteger(0);
            String name = row.getString(1);
            String type = row.getString(2);
            Integer itype = row.getInteger(3);
            cmpNo.setId(id);
            cmpNo.setName(name);
            cmpNo.setType(type);
            cmpNo.setItype(itype);
            cmpNos.add(cmpNo);
        }
        return cmpNos;
    }

    public int contextIType(int flag) {
        int contextType;
        switch (flag) {
            case 2:
                contextType = 2;
                break;
            case 4:
                contextType = 3;
                break;
            case 6:
                contextType = 1;
                break;
            case 8:
                contextType = 7;
                break;
            default:
                contextType = 4;
                break;
        }
        return contextType;
    }

    //判断语境类型
    public String contextType(int flag) {
        String contextContent = "";
        switch (flag) {
            case 2:
                contextContent = "sure";
                break;
            case 4:
                contextContent = "neuter";
                break;
            case 6:
                contextContent = "negative";
                break;
            case 8:
                contextContent = "refuse";
                break;
            default:
                contextContent = "question";
                break;
        }
        return contextContent;
    }
}
