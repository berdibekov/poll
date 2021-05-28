package com.berdibekov.domain;

public enum AnswerType {
    SINGLE(0),
    MULTIPLE(1),
    TEXT(2);
    private int answerType;

    private AnswerType(int answerType) {
        this.answerType = answerType;
    }

    private AnswerType(String answerType) {
        this.answerType = AnswerType.valueOf(answerType).getAnswerType();
    }

    public int getAnswerType() {
        return answerType;
    }


}
