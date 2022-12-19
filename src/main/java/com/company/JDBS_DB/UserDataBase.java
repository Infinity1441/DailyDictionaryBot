package com.company.JDBS_DB;

import com.company.model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDataBase extends BaseDataBase<User> {

    public boolean addUser(
            int id,
            String username,
            Long chatId
    ){
        Connection connection = null;
        Statement statement = null;
        try {
            connection = getConnection();
            statement = connection.createStatement();
            ResultSet resultSet =
                    statement.executeQuery(
                            "select add_user(" + id + ", " + forSql(username) + "," + chatId + ")"
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
    public List<User> getList() {
        {
            Connection connection = null;
            Statement statement = null;
            List<User> userList = new ArrayList<>();
            try {
                connection = getConnection();
                statement = connection.createStatement();
                ResultSet resultSet =
                        statement.executeQuery(
                                "select * from users");

                while (resultSet.next()){
                    userList.add(new User(resultSet));
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
            return userList;
        }
    }
}
