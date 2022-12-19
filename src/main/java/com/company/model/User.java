package com.company.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.ResultSet;
import java.sql.SQLException;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User extends Base {

    private int id;
    private String username;
    private Long chatId;

    public User(ResultSet resultSet) {
        this.get(resultSet);
    }

    @Override
    protected void get(ResultSet resultSet) {
        try{
            this.id = resultSet.getInt("id");
            this.username = resultSet.getString("username");
            this.chatId = resultSet.getLong("chatId");
        }catch (SQLException e){
            throw new RuntimeException();
        }
    }
}
