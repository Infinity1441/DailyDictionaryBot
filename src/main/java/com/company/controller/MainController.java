package com.company.controller;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

public class MainController {

    public static void handleMessage(Message message) {
        if (message.hasText())
            handleText(message);
    }

    private static void handleText(Message message) {

    }

    public static void handleCallback(CallbackQuery callbackQuery) {

    }
}
