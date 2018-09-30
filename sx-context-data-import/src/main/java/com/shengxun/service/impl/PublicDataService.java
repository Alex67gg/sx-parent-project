package com.shengxun.service.impl;

import com.shengxun.entity.ContextAll;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * 插入全局语境
 * Created by chenwei
 * on 2018/6/22 13:35.
 */
@Service
public class PublicDataService {
    /**
     * 将mysql的全局语境表数据导入到sqlite的全局语境表
     * @param connection
     * @param contexts
     * @throws SQLException
     */
    public void insertPublicData(Connection connection, List<ContextAll> contexts) throws SQLException {
        for (ContextAll globalContext : contexts) {
            PreparedStatement preparedStatement = connection.prepareStatement
                    ("insert into REQ_PUBLIC_DATA VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?);");
            preparedStatement.setInt(1, globalContext.getId().intValue());
            preparedStatement.setString(2, contextContent(globalContext.getFlag(), globalContext.getContext_Layer(), globalContext.getLayer_next()));
            preparedStatement.setString(3, "1");
            preparedStatement.setString(4, globalContext.getKey_word());
            preparedStatement.setString(5, globalContext.getContext());
            preparedStatement.setString(6, "0");
            preparedStatement.setString(7, "0");
            preparedStatement.setString(8, "0");
            preparedStatement.setString(9, "0");
            preparedStatement.setString(10, "0");
            preparedStatement.setString(11, globalContext.getVideo_url());
            preparedStatement.setString(12, "0");
            preparedStatement.setString(13, "0");
            preparedStatement.executeUpdate();
        }
    }

    //判断语境内容
    public String contextContent(int flag, int context_layer, int layer_next) {
        String contestContent = "";
        switch (flag) {
            case 1:
                contestContent = ".question_notfind_n";
                if (context_layer > 1) {
                    contestContent = contestContent + Integer.toString(context_layer);
                }
                break;
            case 2:
                contestContent = ".notfind";
                break;
            case 3:
                contestContent = ".repeat";
                break;
            case 4:
                contestContent = ".timeout";
                break;
            case 5:
                contestContent = ".refuse";
                break;
            default:
                contestContent = "";
                break;
        }
        return contestContent;
    }
}
