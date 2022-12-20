package com.company.container;

import com.company.bot.MyBot;
import com.company.entity.WordEn;
import com.company.status.UserStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Container {
    public static MyBot MYBOT = null;
    public static String TOKEN = "5919172095:AAGtsxGtz0iQOWN3rm_Vn7D4j_Muys2XAYI";
    public static String USERNAME = "Daily_dictionary_bot";

    public static ConcurrentHashMap<String, UserStatus> userStatusMap = new ConcurrentHashMap<>();
    public static List<WordEn> wordList = new ArrayList<>();
}
