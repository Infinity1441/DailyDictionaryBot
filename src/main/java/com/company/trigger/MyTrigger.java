package com.company.trigger;

import com.company.api.Translator;
import com.company.container.Container;
import com.company.entity.Word;
import com.company.entity.WordEn;
import com.company.service.DBService;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MyTrigger implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        DBService dbService = new DBService();
        List<WordEn> wordList = dbService.getAllAutomaticWords();

        Collections.shuffle(wordList);
        Collections.shuffle(wordList);

        Map<String, List<WordEn>> collect = wordList.stream().collect(Collectors.groupingBy(WordEn::getChatId));
        for (Map.Entry<String, List<WordEn>> entry : collect.entrySet()) {
            String chatId = entry.getKey();
            WordEn wordEn = entry.getValue().get(0);
            Word word = Translator.getEnglishWordsAndDefinitions(wordEn.getWordEn(), chatId);

            StringBuffer str = new StringBuffer();
            str.append("Word: ").append(word.getWord()).append("\n")
                    .append("Pronunciation: ").append(word.getPhonetic())
                    .append("Definition: ").append(word.getMeanings().get(0).getDefinitions().get(0).getDefinition());


        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Word: Book\n\n" +
                "Translation: Kitob\nBuyurtma bermoq\n\n" +
                "Definition:  A medium for recording information\nreserve, buy in advance\n\n" +
                "Example: A book of selected poems\nI have booked a table");
        sendMessage.setChatId("609762012");

        Container.MYBOT.sendMsg(sendMessage);

    }

    public static void startSchedule() {
        try {
            JobDetail jobDetail = JobBuilder.newJob(MyTrigger.class).build();

            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("CronTrigger")
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(30).repeatForever()).build();

            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            scheduler.start();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }
}
