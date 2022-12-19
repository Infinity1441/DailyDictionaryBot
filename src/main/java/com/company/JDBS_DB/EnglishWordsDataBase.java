package com.company.JDBS_DB;

import com.company.model.EnglishWords;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EnglishWordsDataBase extends BaseDataBase<EnglishWords>{

    public boolean addEnglishWords(
            int id,
            String text,
            int userId
    ){
        Connection connection = null;
        Statement statement = null;
        try {
            connection = getConnection();
            statement = connection.createStatement();
            ResultSet resultSet =
                    statement.executeQuery(
                            "select add_wordsEngs(" + id + ", " + forSql(text) + "," + userId + ")"
                    );
            resultSet.next();
            return resultSet.getBoolean(0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            closeConnections(connection,statement);
        }
    }
    @Override
    public List<EnglishWords> getList() {
        Connection connection = null;
        Statement statement = null;
        List<EnglishWords> englishWords = new ArrayList<>();
        try {
            connection = getConnection();
            statement = connection.createStatement();
            ResultSet resultSet =
                    statement.executeQuery(
                            "select * from wordsEng");

            while (resultSet.next()){
                englishWords.add(new EnglishWords(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            if (connection != null && statement != null){
                try {
                    connection.close();
                    statement.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return englishWords;
    }
}