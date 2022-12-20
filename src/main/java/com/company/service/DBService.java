package com.company.service;

import com.company.entity.WordEn;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DBService {
    private final static String DATABASE_USERNAME = "postgres";
    private final static String DATABASE_PASSWORD = "123";
    private final static String DATABASE_URL = "jdbc:postgresql://localhost:5432/DailyDictionaryBot";


    public List<WordEn> getAllAutomaticWordsByChatId(WordEn wordEn , String chatId) {
        List<WordEn> wordEnList = new ArrayList<>();

        try {
            Class.forName("org.postgresql.Driver");

            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);

            Statement statement = connection.createStatement();

            String query = "select automatic.id , automatic.chat_id , automatic.text , \"firstName\" from automatic inner join public.users u on automatic.chat_id = u.chat_id where automatic.chat_id='"+chatId+"'";
//            PreparedStatement preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setString(1, chatId);
            ResultSet rs = statement.executeQuery(query);


            while (rs.next()) {
                int id = rs.getInt("id");
                String chat_id = rs.getString("chat_id");
                String text = rs.getString("text");
                String firstName = rs.getString("firstName");


                wordEnList.add(new WordEn(id, text, chat_id, firstName,
                        wordEn.getWordUz(), wordEn.getDefinition(), wordEn.getExample(), wordEn.isWordStatusAutomatic()));
            }

            wordEnList.forEach(System.out::println);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return wordEnList;
    }

    public static void main(String[] args) {
        DBService dbService = new DBService();
        dbService.getAllAutomaticWordsByChatId(new WordEn(),"123");
    }


}
