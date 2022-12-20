package com.company.controller;

import static com.company.container.Container.*;


import com.company.container.Container;
import com.company.db.Database;
import com.company.entity.WordEn;
import com.company.status.UserStatus;

import static com.company.utils.InlineButtonConstants.*;
import static com.company.utils.InlineKeyboardButtonUtil.*;
import static com.company.utils.KeyboardButtonConstants.*;

import com.company.utils.KeyboardButtonUtil;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.*;

import java.io.InputStream;

public class MainController {

    public static void handleMessage(Message message) {
        if (message.hasText()) {
            handleText(message);
//        } else if (message.hasVoice()) {
//            MYBOT.sendMsg(new SendMessage(String.valueOf(message.getChatId()), message.getVoice().getFileId() + "|| " + message.getVoice().getFileUniqueId()));
//            MYBOT.sendMsg(new SendMessage(String.valueOf(message.getChatId()), String.valueOf(message.getVoice().getDuration())));
//
//        } else if (message.hasAudio()) {
//            Audio audio = message.getAudio();
//            MYBOT.sendMsg(new SendMessage(String.valueOf(message.getChatId()), message.getAudio().getFileId() + "|| " + message.getAudio().getFileUniqueId()));
//            File file = new File(audio.getFileId(), audio.getFileUniqueId(), audio.getFileSize(), audio.getFileName());
//            System.out.println("file.getFilePath() = " + file.getFilePath());
//            System.out.println(audio.getFileId()+ " || "+ audio.getFileUniqueId() +" || "+ audio.getFileSize() +" || " + audio.getFileName());
//        } else if (message.hasPhoto()) {
//            MYBOT.sendMsg(new SendMessage(String.valueOf(message.getChatId()), message.getPhoto().get(message.getPhoto().size() - 1).getFileId()));
//            System.out.println(message.getPhoto().size());
//            File file = new File(message.getPhoto().get(message.getPhoto().size() - 1).getFileId(), message.getPhoto().get(message.getPhoto().size() - 1).getFileUniqueId(), message.getPhoto().get(message.getPhoto().size() - 1).getFileSize().longValue(), "src/rasm.jpg");
//                SendPhoto sendPhoto = new SendPhoto();
//            sendPhoto.setChatId(String.valueOf(message.getChatId()));
//                sendPhoto.setPhoto(new InputFile((message.getPhoto().get(message.getPhoto().size() - 1).getFileId())));
//                MYBOT.sendMsg(sendPhoto);
//            System.out.println("Path: "+message.getPhoto().get(message.getPhoto().size() - 1).getFilePath());
//                        MYBOT.sendMsg(new SendMessage(String.valueOf(message.getChatId()), new MainController().getFileUrl(message)));
//            for (PhotoSize photoSize : message.getPhoto()) {
//                System.out.println("photoSize.getFilePath() = " + photoSize.getFilePath());
//                sendPhoto.setChatId(String.valueOf(message.getChatId()));
//                MYBOT.sendMsg(photoSize.getFileId());
//
//                System.out.println("photoSize.getFileId() = " + photoSize.getFileId());
//            }
//
//            System.out.println("Rasm keldi!");
        }
    }

    public String getFileUrl(Message message){
        return File.getFileUrl(TOKEN, String.valueOf(message.getPhoto().get(message.getPhoto().size() - 1)));
    }

    private static void handleText(Message message) {
        String text = message.getText();

        String chatId = String.valueOf(message.getChatId());
        String firstName = message.getFrom().getFirstName();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

        if (text.equals("/start")) {
            sendMessage.setText("Hello " + firstName + " bratan🖐\nWelcome to daily dictionary bot\uD83D\uDCDA");
            sendMessage.setReplyMarkup(KeyboardButtonUtil.getSEND_WORDButton());
        } else if (text.equals(SEND_WORD)) {
            sendMessage.setText("Send your word in english: ");
            userStatusMap.put(chatId, UserStatus.SEND_WORD);
        } else if (userStatusMap.containsKey(chatId)) {
            switch (userStatusMap.get(chatId)) {
                case SEND_WORD -> {
                    sendMessage.setText(text);
                    sendMessage.setReplyMarkup(getManualOrAutomatic());
                    wordList.add(new WordEn(wordList.size() + 1, text, chatId, firstName, "", "", "", true));
                    userStatusMap.put(chatId, UserStatus.SELECT_MANUAL_AUTOMATIC);
                }
                case SEND_TRANSLATION -> {
                    wordList.stream().filter(wordEn -> wordEn.getChatId().equals(chatId)).findAny().ifPresent(wordEn -> wordEn.setWordUz(text));
                    sendMessage.setText("Send definition: ");
                    userStatusMap.put(chatId, UserStatus.SEND_DEFINITION);
                }
                case SEND_DEFINITION -> {
                    wordList.stream().filter(wordEn -> wordEn.getChatId().equals(chatId)).findAny().ifPresent(wordEn -> wordEn.setDefinition(text));
                    sendMessage.setText("Send example: ");
                    userStatusMap.put(chatId, UserStatus.SEND_EXAMPLE);
                }
                case SEND_EXAMPLE -> {
                    wordList.stream().filter(wordEn -> wordEn.getChatId().equals(chatId)).findAny().ifPresent(wordEn -> wordEn.setExample(text));
                    sendMessage.setText("Your word has been successfully saved!");
                    WordEn wordEn1 = wordList.stream().filter(wordEn -> wordEn.getChatId().equals(chatId)).findAny().get();
                    Database.writeWordTodatabase(wordEn1);
                    userStatusMap.remove(chatId);
                }
            }
        }
        MYBOT.sendMsg(sendMessage);
    }

    public static void handleCallback(CallbackQuery callbackQuery) {
        String chatId = String.valueOf(callbackQuery.getMessage().getChatId());

        deleteMessage(chatId, callbackQuery.getMessage().getMessageId());
        String data = callbackQuery.getData();


        if (userStatusMap.containsKey(chatId) && userStatusMap.get(chatId).equals(UserStatus.SELECT_MANUAL_AUTOMATIC)) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);

            if (data.equals(AUTOMATIC_CALLBACK)) {
                wordList.stream().filter(wordEn -> wordEn.getChatId().equals(chatId)).findAny().ifPresent(wordEn -> wordEn.setWordStatusAutomatic(false));

                sendMessage.setText("Your word has been successfully saved!");
            } else {
                sendMessage.setText("Send uzbek translation: ");
                userStatusMap.put(chatId, UserStatus.SEND_TRANSLATION);
            }
            MYBOT.sendMsg(sendMessage);
        }
    }

    private static void deleteMessage(String chatId, int messageId) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(chatId);
        deleteMessage.setMessageId(messageId);
        MYBOT.sendMsg(deleteMessage);
    }
}
