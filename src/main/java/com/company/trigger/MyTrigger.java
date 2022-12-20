package com.company.trigger;

import com.company.api.Translator;
import com.company.container.Container;
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
        //List<WordEn> wordList = dbService.getAllAutomaticWords();
        List<WordEn> wordList = new ArrayList<>();
        wordList.add(new WordEn(1,"book","609762012","Infinity","","","",true));
        wordList.add(new WordEn(1,"pen","164940659","Abdullo","","","",true));
        wordList.add(new WordEn(1,"make","164940659","Abdullo","","","",true));
        wordList.add(new WordEn(1,"laptop","609762012","Infinity","","","",true));

        Collections.shuffle(wordList);
        Collections.shuffle(wordList);

        Map<String, List<WordEn>> collect = wordList.stream().collect(Collectors.groupingBy(WordEn::getChatId));
        for (Map.Entry<String, List<WordEn>> entry : collect.entrySet()) {
            String chatId = entry.getKey();
            WordEn wordEn = entry.getValue().get(0);
            Translator.getEnglishWordsAndDefinitions(wordEn.getWordEn(),chatId);
        }

//        SendMessage sendMessage = new SendMessage();
//        sendMessage.setText("Trigger");
//        sendMessage.setChatId("1269467656");
//        //wordList.removeIf(wordEn1 -> wordEn1.getChatId().equals(chatId));
//        Container.MYBOT.sendMsg(sendMessage);

    }

    public static void startSchedule() {
        try {
            JobDetail jobDetail = JobBuilder.newJob(MyTrigger.class).build();

            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("CronTrigger")
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever()).build();

            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            scheduler.start();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }
}
