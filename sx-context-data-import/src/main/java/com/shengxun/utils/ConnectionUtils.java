package com.shengxun.utils;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by chenwei
 * on 2018/6/28 09:16.
 */
public class ConnectionUtils {
    // 将线程和连接绑定，保证事务能统一执行
    private static ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<Connection>();

    // 获得当前连接
    public static Connection getCurrentConnection(String sourcePath) throws SQLException {
        // 默认线程里面取
        Connection conn = connectionThreadLocal.get();
        if (conn == null || conn.isClosed()) {
            conn = getConnection(sourcePath);
        }
        return conn;
    }

    // 获得连接
    private static Connection getConnection(String sourcePath) throws SQLException {
        File sourceFile = new File(sourcePath);
        if (!sourceFile.exists()) {
            sourceFile.mkdirs();
        }
        File dbFile = new File(sourcePath + "aibeta.db");
        if (dbFile.exists()) {
            dbFile.delete();
        }
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.sqlite.JDBC");
        dataSourceBuilder.url("jdbc:sqlite:" + sourcePath + "aibeta.db");
        dataSourceBuilder.type(SQLiteDataSource.class);
        DataSource dataSource = dataSourceBuilder.build();
        Connection connection = dataSource.getConnection();
        connectionThreadLocal.set(connection);
        return connection;
    }

    /**
     * 提交
     */
    public static void commitConn() throws SQLException {
        Connection conn = connectionThreadLocal.get();
        try {
            conn.commit();
        } finally {
            conn.close();
            connectionThreadLocal.remove();
        }
    }

    /**
     * 回滚
     */
    public static void rollBackConn() {
        Connection conn = connectionThreadLocal.get();
        try {
            if (conn != null) {
                conn.rollback();
            }
        } catch (SQLException e) {
            e.getStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                    connectionThreadLocal.remove();
                }
            } catch (SQLException e) {
            }
        }
    }

    /**
     * 移除
     */
    public static void removeConn() {
        connectionThreadLocal.remove();
    }
}
