package com.company.bot;

import com.company.container.Container;
import com.company.trigger.MyTrigger;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {

    //8/9654
    public static void main(String[] args) {
        MyTrigger.startSchedule();
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            MyBot myBot = new MyBot();
            Container.MYBOT = myBot;
            telegramBotsApi.registerBot(myBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
