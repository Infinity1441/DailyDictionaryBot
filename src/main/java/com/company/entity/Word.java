package com.company.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
@AllArgsConstructor
@Getter
public class Word {
    private String word;
    private String chatId;
    private String phonetic;
    private List<Phonetics> phonetics;
    private List<Meanings> meanings;
    private boolean wordStatusAutomatic;

    public Word(String word, String phonetic, List<Phonetics> phonetics, List<Meanings> meanings) {
        this.word = word;
        this.phonetic = phonetic;
        this.phonetics = phonetics;
        this.meanings = meanings;
    }


    public String getWord() {
        return word;
    }

    public String getPhonetic() {
        return phonetic;
    }

    public List<Phonetics> getPhonetics() {
        return phonetics;
    }

    public List<Meanings> getMeanings() {
        return meanings;
        ///
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public void setWordStatusAutomatic(boolean wordStatusAutomatic) {
        this.wordStatusAutomatic = wordStatusAutomatic;
    }
}