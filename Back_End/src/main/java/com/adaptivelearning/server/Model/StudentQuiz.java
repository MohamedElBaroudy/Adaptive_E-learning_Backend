package com.adaptivelearning.server.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "Student_Quiz")
public class StudentQuiz{
    @EmbeddedId
    private StudentQuizId studentQuizId;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.REFRESH})
    @JoinColumn
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.REFRESH})
    @JoinColumn
    @MapsId("quizId")
    private Quiz quiz;

    @Column(name = "student_mark")
    private Integer mark = 0;

    @Column(name = "isPassed")
    private Boolean isPassed = false;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "submit_date")
    private Date submitDate;

    @Column(name = "Attempts")
    private Integer attempts = 0;

    @Column(name = "TotalAttempts")
    private  Integer totalAttempts = 0;

    public StudentQuiz() {
    }

    public StudentQuiz(User user,Quiz quiz) {
        this.user = user;
        this.quiz = quiz;
        Date localDate = new Date();
        this.startDate = localDate;
        this.studentQuizId = new StudentQuizId(user.getUserId(),quiz.getQuizId());
    }

    public StudentQuizId getStudentQuizId() {
        return studentQuizId;
    }

    public void setStudentQuizId(StudentQuizId studentQuizId) {
        this.studentQuizId = studentQuizId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public Boolean getPassed() {
        return isPassed;
    }

    public void setPassed(Boolean passed) {
        isPassed = passed;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    public Integer getAttempts() {
        return attempts;
    }

    public void setAttempts(Integer attempts) {
        this.attempts = attempts;
    }

    public Integer getTotalAttempts() {
        return totalAttempts;
    }

    public void setTotalAttempts(Integer totalAttempts) {
        this.totalAttempts = totalAttempts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudentQuiz)) return false;
        StudentQuiz that = (StudentQuiz) o;
        return Objects.equals(getStudentQuizId(), that.getStudentQuizId()) &&
                Objects.equals(getUser(), that.getUser()) &&
                Objects.equals(getQuiz(), that.getQuiz()) &&
                Objects.equals(getMark(), that.getMark()) &&
                Objects.equals(isPassed, that.isPassed) &&
                Objects.equals(getStartDate(), that.getStartDate()) &&
                Objects.equals(getSubmitDate(), that.getSubmitDate()) &&
                Objects.equals(getAttempts(), that.getAttempts()) &&
                Objects.equals(totalAttempts, that.totalAttempts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStudentQuizId(), getUser(), getQuiz(), getMark(), isPassed, getStartDate(), getSubmitDate(), getAttempts(), totalAttempts);
    }
}
