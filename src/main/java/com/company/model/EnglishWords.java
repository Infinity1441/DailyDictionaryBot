package com.company.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.ResultSet;
import java.sql.SQLException;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EnglishWords extends Base {

    private int id;
    private String wordEng;
    private int userId;

    public EnglishWords(ResultSet resultSet) {
        this.get(resultSet);
    }

    @Override
    protected void get(ResultSet resultSet) {
        try {
            this.id = resultSet.getInt("id");
            this.wordEng = resultSet.getString("word");
            this.userId = resultSet.getInt("userId");
        }catch (SQLException e){
            throw new RuntimeException();
        }
    }
}
