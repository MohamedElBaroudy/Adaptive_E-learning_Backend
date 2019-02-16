package com.adaptivelearning.server.FancyModel;

import com.adaptivelearning.server.Model.Answer;

import java.util.LinkedList;
import java.util.List;

public class FancyAnswer {
    // id
    private int answerId;

    // body
    private String body;

    // is correct
    private boolean isCorrect;

    public FancyAnswer() {
    }

    public FancyAnswer toFancyAnswerMapping(Answer answer){
        this.answerId = answer.getAnswerId();
        this.body = answer.getBody();
        this.isCorrect = answer.isCorrect();
        return this;
    }

    public List<FancyAnswer> toFancyAnswerListMapping(List<Answer> answers){
        LinkedList<FancyAnswer> fancyAnswerList = new LinkedList<>();
        for (Answer answer:
                answers) {
            FancyAnswer fancyAnswer = new FancyAnswer();
            fancyAnswerList.addLast(fancyAnswer.toFancyAnswerMapping(answer));
        }
        return fancyAnswerList;
    }
    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}