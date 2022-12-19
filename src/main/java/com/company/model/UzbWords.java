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
public class UzbWords extends Base{

    private int id;
    private String wordUzb;
    private int engWordsId;

    public UzbWords(ResultSet resultSet) {
        this.get(resultSet);
    }

    @Override
    protected void get(ResultSet resultSet) {
        try {
            this.id = resultSet.getInt("id");
            this.wordUzb = resultSet.getString("wordUzb");
            this.engWordsId = resultSet.getInt("engWordsId");
        }catch (SQLException e){
            throw new RuntimeException();
        }
    }

}
