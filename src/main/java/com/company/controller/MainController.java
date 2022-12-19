package com.company.controller;

import static com.company.container.Container.*;


import com.company.db.Database;
import com.company.entity.WordEn;
import com.company.status.UserStatus;

import static com.company.utils.InlineButtonConstants.*;
import static com.company.utils.InlineKeyboardButtonUtil.*;
import static com.company.utils.KeyboardButtonConstants.*;

import com.company.utils.KeyboardButtonUtil;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

public class MainController {

    public static void handleMessage(Message message) {
        if (message.hasText())
            handleText(message);
    }

    private static void handleText(Message message) {
        String chatId = String.valueOf(message.getChatId());
        String firstName = message.getContact().getFirstName();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

        String text = message.getText();
        if (text.equals("/start")){
            sendMessage.setText("Hello " + firstName + " bratan🖐\nWelcome to daily dictionary bot\uD83D\uDCDA");
            sendMessage.setReplyMarkup(KeyboardButtonUtil.getSendWordButton());
        } else if (text.equals(SENDWORD)) {
            sendMessage.setText("Send your word in english: ");
            userStatusMap.put(chatId,UserStatus.SEND_WORD);
        } else if (userStatusMap.containsKey(chatId)&&userStatusMap.get(chatId).equals(UserStatus.SEND_WORD)) {
            sendMessage.setText(text);
            sendMessage.setReplyMarkup(getManualOrAutomatic());
            wordList.add(new WordEn(wordList.size()+1,text,chatId,"","","",true));
            userStatusMap.put(chatId,UserStatus.SELECT_MANUAL_AUTOMATIC);
        } else if (userStatusMap.containsKey(chatId) && userStatusMap.get(chatId).equals(UserStatus.SEND_TRANSLATION)) {
            wordList.stream().filter(wordEn -> wordEn.getChatId().equals(chatId)).findAny().ifPresent(wordEn -> wordEn.setWordUz(text));
            sendMessage.setText("Send definition: ");
            userStatusMap.put(chatId,UserStatus.SEND_DEFINITION);
        } else if (userStatusMap.containsKey(chatId) && userStatusMap.get(chatId).equals(UserStatus.SEND_DEFINITION)) {
            wordList.stream().filter(wordEn -> wordEn.getChatId().equals(chatId)).findAny().ifPresent(wordEn -> wordEn.setDefintion(text));
            sendMessage.setText("Send example: ");
            userStatusMap.put(chatId,UserStatus.SEND_EXAMPLE);
        } else if (userStatusMap.containsKey(chatId) && userStatusMap.get(chatId).equals(UserStatus.SEND_EXAMPLE)) {
            wordList.stream().filter(wordEn -> wordEn.getChatId().equals(chatId)).findAny().ifPresent(wordEn -> wordEn.setExample(text));
            sendMessage.setText("Your word has been successfully saved!");
            WordEn wordEn1 = wordList.stream().filter(wordEn -> wordEn.getChatId().equals(chatId)).findAny().get();
            Database.writeWordTodatabase(wordEn1);
            userStatusMap.remove(chatId);
        }
        MYBOT.sendMsg(sendMessage);
    }

    public static void handleCallback(CallbackQuery callbackQuery) {
        String chatId = String.valueOf(callbackQuery.getMessage().getChatId());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        String data = callbackQuery.getData();

        if (data.equals(MANUAL_CALLBACK)&&userStatusMap.containsKey(chatId)&&userStatusMap.get(chatId).equals(UserStatus.SELECT_MANUAL_AUTOMATIC)){
            if (data.startsWith("automatic")){
                wordList.stream().filter(wordEn -> wordEn.getChatId().equals(chatId)).findAny().ifPresent(wordEn -> wordEn.setWordStatusAutomatic(false));
            }
            sendMessage.setText("Send uzbek translation: ");
            userStatusMap.put(chatId,UserStatus.SEND_TRANSLATION);
            MYBOT.sendMsg(sendMessage);
        }
    }
}
