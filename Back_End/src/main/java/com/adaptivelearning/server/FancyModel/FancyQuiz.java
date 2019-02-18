package com.adaptivelearning.server.FancyModel;

import com.adaptivelearning.server.Model.Quiz;

import java.util.LinkedList;
import java.util.List;

public class FancyQuiz {
    // id
    private Long quizId;

    // title
    private String title;

    // instructions
    private String instructions;

    // time
    private short time;

    // total mark
    private short totalMark;

    // questions
    private List<FancyQuestion> questions;

    public FancyQuiz() {
    }

    public FancyQuiz toFancyQuizMapping(Quiz quiz){
        FancyQuestion fancyQuestion = new FancyQuestion();
        this.quizId = quiz.getQuizId();
        this.title = quiz.getTitle();
        this.instructions = quiz.getInstructions();
        this.time= quiz.getTime();
        this.totalMark = quiz.getTotalMark();
        this.questions = fancyQuestion.toFancyQuestionListMapping(quiz.getQuestions());
        return this;
    }

    public List<FancyQuiz> toFancyQuizListMapping(List<Quiz> quizzes){
        LinkedList<FancyQuiz> fancyQuizList = new LinkedList<>();
        for (Quiz quiz:
                quizzes) {
            FancyQuiz fancyQuiz = new FancyQuiz();
            fancyQuizList.addLast(fancyQuiz.toFancyQuizMapping(quiz));
        }
        return fancyQuizList;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public short getTime() {
        return time;
    }

    public void setTime(short time) {
        this.time = time;
    }

    public short getTotalMark() {
        return totalMark;
    }

    public void setTotalMark(short totalMark) {
        this.totalMark = totalMark;
    }

    public List<FancyQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<FancyQuestion> questions) {
        this.questions = questions;
    }
}
