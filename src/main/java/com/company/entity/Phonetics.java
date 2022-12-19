package com.company.entity;

public class Phonetics {
    private String text;
    private String audio;
    private String sourceUrl;

    public Phonetics(String text, String audio, String sourceUrl) {
        this.text = text;
        this.audio = audio;
        this.sourceUrl = sourceUrl;
    }

    public String getText() {
        return text;
    }

    public String getAudio() {
        return audio;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }
}
