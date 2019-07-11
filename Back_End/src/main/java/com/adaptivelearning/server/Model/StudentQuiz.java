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

    @Column(name = "student_best_mark")
    private Integer bestMark = 0;

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
    
    @Column(name = "notes")
    private  String notes;

    @Column(name = "retrieved_quiz_total_mark")
    private Integer retrievedQuizTotalMark = 0;

    public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

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

    public Integer getBestMark() {
        return bestMark;
    }

    public void setBestMark(Integer bestMark) {
        this.bestMark = bestMark;
    }

    public Integer getRetrievedQuizTotalMark() {
        return retrievedQuizTotalMark;
    }

    public void setRetrievedQuizTotalMark(Integer retrievedQuizTotalMark) {
        this.retrievedQuizTotalMark = retrievedQuizTotalMark;
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
                Objects.equals(getBestMark(), that.getBestMark()) &&
                Objects.equals(isPassed, that.isPassed) &&
                Objects.equals(getStartDate(), that.getStartDate()) &&
                Objects.equals(getSubmitDate(), that.getSubmitDate()) &&
                Objects.equals(getAttempts(), that.getAttempts()) &&
                Objects.equals(getTotalAttempts(), that.getTotalAttempts());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStudentQuizId(), getUser(), getQuiz(), getMark(), getBestMark(), isPassed, getStartDate(), getSubmitDate(), getAttempts(), getTotalAttempts());
    }
}
