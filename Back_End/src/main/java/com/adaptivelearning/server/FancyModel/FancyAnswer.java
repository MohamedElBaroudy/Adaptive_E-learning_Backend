package com.adaptivelearning.server.FancyModel;

import com.adaptivelearning.server.Model.Answer;

import java.util.LinkedList;
import java.util.List;

public class FancyAnswer {
    // id
    private Long answerId;

    // body
    private String body;

    // is correct
    private boolean isCorrect;

    public FancyAnswer() {
    }

    public FancyAnswer toFancyAnswerMapping(Answer answer, Boolean isTeacher){
        this.answerId = answer.getAnswerId();
        this.body = answer.getBody();
        if (isTeacher)
            this.isCorrect = answer.isCorrect();
        else
            this.isCorrect = false;
        return this;
    }

    public List<FancyAnswer> toFancyAnswerListMapping(List<Answer> answers, Boolean isTeacher){
        LinkedList<FancyAnswer> fancyAnswerList = new LinkedList<>();
        for (Answer answer:
                answers) {
            FancyAnswer fancyAnswer = new FancyAnswer();
            fancyAnswerList.addLast(fancyAnswer.toFancyAnswerMapping(answer,isTeacher));
        }
        return fancyAnswerList;
    }
    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
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
