package com.company.trigger;

import com.company.container.Container;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class MyTrigger implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//        DBService dbService = new DBService();
//        List<WordEn> wordList = dbService.getAllAutomaticWords();
//        Collections.shuffle(wordList);
//        Collections.shuffle(wordList);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Trigger");
        sendMessage.setChatId("1269467656");
        //wordList.removeIf(wordEn1 -> wordEn1.getChatId().equals(chatId));
        Container.MYBOT.sendMsg(sendMessage);

    }

    public static void startSchedule() {
        try {
            JobDetail jobDetail = JobBuilder.newJob(MyTrigger.class).build();

            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("CroneTrigger")
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(60).repeatForever()).build();

            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            scheduler.start();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }
}
