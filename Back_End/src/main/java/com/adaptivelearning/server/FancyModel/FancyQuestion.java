package com.adaptivelearning.server.FancyModel;

import com.adaptivelearning.server.Model.Question;

import java.util.LinkedList;
import java.util.List;

public class FancyQuestion {
    // id
    private Long questionId;

    // body
    private String body;

    // is multiple choice
    private boolean isMultipleChoice;

    // mark
    private short mark;

    // answers
    private List<FancyAnswer> answers;

    public FancyQuestion() {
    }

    public FancyQuestion toFancyQuestionMapping(Question question){
        FancyAnswer fancyAnswer = new FancyAnswer();
        this.questionId = question.getQuestionId();
        this.body = question.getBody();
        this.isMultipleChoice = question.isMultipleChoice();
        this.mark = question.getMark();
        this.answers = fancyAnswer.toFancyAnswerListMapping(question.getAnswers());
        return this;
    }

    public List<FancyQuestion> toFancyQuestionListMapping(List<Question> questions){
        LinkedList<FancyQuestion> fancyQuestionList = new LinkedList<>();
        for (Question question:
                questions) {
            FancyQuestion fancyQuestion = new FancyQuestion();
            fancyQuestionList.addLast(fancyQuestion.toFancyQuestionMapping(question));
        }
        return fancyQuestionList;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isMultipleChoice() {
        return isMultipleChoice;
    }

    public void setMultipleChoice(boolean multipleChoice) {
        isMultipleChoice = multipleChoice;
    }

    public short getMark() {
        return mark;
    }

    public void setMark(short mark) {
        this.mark = mark;
    }

    public List<FancyAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<FancyAnswer> answers) {
        this.answers = answers;
    }
}
