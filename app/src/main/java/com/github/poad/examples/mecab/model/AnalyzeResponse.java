package com.github.poad.examples.mecab.model;

public class AnalyzeResponse {
    private final String text;

    public AnalyzeResponse(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
