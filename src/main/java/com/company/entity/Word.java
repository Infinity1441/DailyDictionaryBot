package com.company.entity;

import java.util.List;

public class Word {
    private String word;
    private String phonetic;
    private List<Phonetics> phonetics;
    private List<Meanings> meanings;

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
    }
}
