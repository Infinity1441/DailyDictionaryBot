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
    private String wordUz;
    private String defintion;
    private String example;
    private boolean wordStatusAutomatic;
}
