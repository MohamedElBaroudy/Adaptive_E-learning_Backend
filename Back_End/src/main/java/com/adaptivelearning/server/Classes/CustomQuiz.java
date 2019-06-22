package com.adaptivelearning.server.Classes;

import java.util.Objects;

public class CustomQuiz {

    private Long quizId;

    private String title;

    private String instructions;

    private short time;

    private Short totalMark=0;

    private Short no_of_questions = 0;

    public CustomQuiz(Long quizId,
                      String title,
                      String instructions,
                      short time,
                      Short totalMark,
                      Short no_of_questions) {
        this.quizId = quizId;
        this.title = title;
        this.instructions = instructions;
        this.time = time;
        this.totalMark = totalMark;
        this.no_of_questions = no_of_questions;
    }

    public Long getQuizId() {
        return quizId;
    }

    public String getTitle() {
        return title;
    }

    public String getInstructions() {
        return instructions;
    }

    public short getTime() {
        return time;
    }

    public Short getTotalMark() {
        return totalMark;
    }

    public Short getNo_of_questions() {
        return no_of_questions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomQuiz)) return false;
        CustomQuiz that = (CustomQuiz) o;
        return getTime() == that.getTime() &&
                Objects.equals(getQuizId(), that.getQuizId()) &&
                Objects.equals(getTitle(), that.getTitle()) &&
                Objects.equals(getInstructions(), that.getInstructions()) &&
                Objects.equals(getTotalMark(), that.getTotalMark()) &&
                Objects.equals(getNo_of_questions(), that.getNo_of_questions());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getQuizId(), getTitle(), getInstructions(), getTime(), getTotalMark(), getNo_of_questions());
    }
}
