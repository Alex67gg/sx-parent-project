package com.shengxun.service.impl;

import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by chenwei
 * on 2018/6/22 13:36.
 */
@Service
public class InfoService {

    /**
     * 插入项目信息表数据
     *
     * @param industryName
     * @param templateName
     * @param connection
     * @return
     * @throws SQLException
     */
    public void insertInfo(Connection connection, String industryName, String templateName) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("insert into DATABASE_INFO VALUES (1,?,?);");
        preparedStatement.setString(1, "ProjectName");
        preparedStatement.setString(2, templateName);
        preparedStatement.executeUpdate();
        PreparedStatement preparedStatement1 = connection.prepareStatement("insert into DATABASE_INFO VALUES (2,?,?);");
        preparedStatement1.setString(1, "VersionNumber");
        preparedStatement1.setString(2, "2.0");
        preparedStatement1.executeUpdate();
        PreparedStatement preparedStatement2 = connection.prepareStatement("insert into DATABASE_INFO VALUES (3,?,?);");
        preparedStatement2.setString(1, "Area");
        preparedStatement2.setString(2, "shanghai");
        preparedStatement2.executeUpdate();
        PreparedStatement preparedStatement3 = connection.prepareStatement("insert into DATABASE_INFO VALUES (4,?,?);");
        preparedStatement3.setString(1, "Industry");
        preparedStatement3.setString(2, industryName);
        preparedStatement3.executeUpdate();
    }
}
