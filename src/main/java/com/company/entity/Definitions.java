package com.company.entity;

import java.util.List;

public class Definitions {
    private String definition;
    private List<String> synonyms;
    private List<String> antonyms;
    private String example;

    public Definitions(String definition, List<String> synonyms, List<String> antonyms, String example) {
        this.definition = definition;
        this.synonyms = synonyms;
        this.antonyms = antonyms;
        this.example = example;
    }

    public String getDefinition() {
        return definition;
    }

    public List<String> getSynonyms() {
        return synonyms;
    }

    public List<String> getAntonyms() {
        return antonyms;
    }

    public String getExample() {
        return example;
    }
}
