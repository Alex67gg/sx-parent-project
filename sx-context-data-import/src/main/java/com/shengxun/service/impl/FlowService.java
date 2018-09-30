package com.shengxun.service.impl;

import com.shengxun.entity.Plate;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by chenwei
 * on 2018/6/27 18:26.
 */
@Service
public class FlowService {

    /**
     * 插入流程表数据
     *
     * @param plates
     * @param connection
     * @return
     */
    public void insertFlow(Connection connection, List<Plate> plates) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("insert into REQ_ANS_FLOW VALUES (?,?,?,?,?,?);");

        // 插入数据
        for (int i = 0; i < plates.size(); i++) {
            // ID顺序
            preparedStatement.setLong(1, i + 1);
            //Level类别 1.普通流程 2.邀约流程
            preparedStatement.setInt(2, 1);
            //Floor 流程层级数 默认 1
            preparedStatement.setInt(3, 1);
            //question 问题 1.字母,查询主表level对应内容 2.直接是内容,不查主表
            preparedStatement.setString(4, "A");
            //name 标识
            preparedStatement.setString(5, (i + 1) + "");
            //type 默认word
            preparedStatement.setString(6, "word");
            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();
    }
}
