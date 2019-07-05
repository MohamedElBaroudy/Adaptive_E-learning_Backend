package com.adaptivelearning.server.FancyModel;

import com.adaptivelearning.server.Model.StudentQuiz;
import java.util.Date;

public class FancyStudentQuiz {
    // quiz id
    private Long quizId;

    // quiz total mark
    private Short totalMark;

    // student mark
    private Integer studentMark;

    // student best mark
    private Integer studentBestMark;

    // is passed
    private Boolean isPassed;

    // attempts
    private Integer attempts;

    // start date
    private Date startDate;

    // start date
    private Date submitDate;
    
 	//quiz notes
    private String notes;


	public FancyStudentQuiz() {
    }

    public FancyStudentQuiz toFancyStudentQuiz(StudentQuiz studentQuiz){
        this.quizId = studentQuiz.getStudentQuizId().getQuizId();
        this.totalMark = studentQuiz.getQuiz().getTotalMark();
        this.studentMark = studentQuiz.getMark();
        this.studentBestMark = studentQuiz.getBestMark();
        this.isPassed = studentQuiz.getPassed();
        this.attempts = studentQuiz.getAttempts();
        this.startDate = studentQuiz.getStartDate();
        this.submitDate = studentQuiz.getSubmitDate();
        this.notes=studentQuiz.getNotes();
        return this;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public Short getTotalMark() {
        return totalMark;
    }

    public void setTotalMark(Short totalMark) {
        this.totalMark = totalMark;
    }

    public Integer getStudentMark() {
        return studentMark;
    }

    public void setStudentMark(Integer studentMark) {
        this.studentMark = studentMark;
    }

    public Boolean getPassed() {
        return isPassed;
    }

    public void setPassed(Boolean passed) {
        isPassed = passed;
    }

    public Integer getAttempts() {
        return attempts;
    }

    public void setAttempts(Integer attempts) {
        this.attempts = attempts;
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

    public Integer getStudentBestMark() {
        return studentBestMark;
    }

    public void setStudentBestMark(Integer studentBestMark) {
        this.studentBestMark = studentBestMark;
    }

    public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
}
