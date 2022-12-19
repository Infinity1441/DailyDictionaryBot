package com.company.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class WordEn {
    private int id;
    private String wordEn;
    private String chatId;
    private String userFirstName;
    private String wordUz;
    private String definition;
    private String example;
    private boolean wordStatusAutomatic;

//    private String audioUrl;
//    private String transcription;
//    private Meaning ;
}
