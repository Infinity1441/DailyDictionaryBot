package com.company.entity;

import java.util.List;

public class Meanings {
    private String partOfSpeech;
    private List<Definitions> definitions;
    private List<String> synonyms;
    private List<String> antonyms;

    public Meanings(String partOfSpeech, List<Definitions> definitions, List<String> synonyms, List<String> antonyms) {
        this.partOfSpeech = partOfSpeech;
        this.definitions = definitions;
        this.synonyms = synonyms;
        this.antonyms = antonyms;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public List<Definitions> getDefinitions() {
        return definitions;
    }

    public List<String> getSynonyms() {
        return synonyms;
    }

    public List<String> getAntonyms() {
        return antonyms;
    }
}
