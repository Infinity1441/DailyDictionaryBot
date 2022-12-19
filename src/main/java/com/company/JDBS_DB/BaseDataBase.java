package com.company.JDBS_DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class BaseDataBase<R> {
    private final static String DATABASE_URL = "";
    private final static String DATABASE_USERNAME = "";
    private final static String DATABASE_PASSWORD = "";

    public abstract List<R> getList();
    protected Connection getConnection(){
        try {
            return DriverManager.getConnection(DATABASE_URL,DATABASE_USERNAME,DATABASE_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    protected void closeConnections(Connection connection, Statement statement){
        if (connection != null && statement != null){
            try {
                connection.close();
                statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected String forSql(String s){
        return "'" + s + "'";
    }

}
