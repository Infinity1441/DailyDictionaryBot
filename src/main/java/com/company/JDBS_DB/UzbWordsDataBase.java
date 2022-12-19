package com.company.JDBS_DB;

import com.company.model.User;
import com.company.model.UzbWords;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UzbWordsDataBase extends BaseDataBase<UzbWords>{

    public boolean addUzb(
            int id,
            String word,
            int engWordsId
    ){
        Connection connection = null;
        Statement statement = null;
        try {
            connection = getConnection();
            statement = connection.createStatement();
            ResultSet resultSet =
                    statement.executeQuery(
                            "select add_uzbWords("+id+","+forSql(word)+","+engWordsId+")"
                    );
            resultSet.next();
            return resultSet.getBoolean(0);
        }catch (SQLException e){
            throw new RuntimeException();
        }finally {
            closeConnections(connection,statement);
        }
    }
    @Override
    public List<UzbWords> getList() {
        {
            Connection connection = null;
            Statement statement = null;
            List<UzbWords> uzbWordsList = new ArrayList<>();
            try {
                connection = getConnection();
                statement = connection.createStatement();
                ResultSet resultSet =
                        statement.executeQuery(
                                "select * from wordsUzb");

                while (resultSet.next()){
                    uzbWordsList.add(new UzbWords(resultSet));
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
            return uzbWordsList;
        }
    }
}
