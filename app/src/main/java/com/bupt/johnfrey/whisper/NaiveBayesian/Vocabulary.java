package com.bupt.johnfrey.whisper.NaiveBayesian;

import java.util.ArrayList;
import java.util.List;

public class Vocabulary {
    private List<String> vocabulary;

    public void init(List<List<String>> datas) {
        vocabulary = new ArrayList<String>();
        for (List<String> data : datas) {
            for (String word : data) {
                if (!vocabulary.contains(word)) {
                    vocabulary.add(word);
                }
            }
        }
    }

    public void print() {
        System.out.print(vocabulary);
    }

    public List<String> get() {
        return vocabulary;
    }
}
