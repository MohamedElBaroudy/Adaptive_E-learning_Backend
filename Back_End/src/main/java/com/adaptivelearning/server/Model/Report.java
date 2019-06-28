package com.adaptivelearning.server.Model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "Report",uniqueConstraints = {
        @UniqueConstraint(columnNames = "ID")})
@JsonIdentityInfo(
        scope= Report.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "reportId")
public class Report {
	 // id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Long reportId;
    
  
    private long childID;

    private long parentID;
    
    private long courseID;
    
    private long quizID;
    
    private boolean isPassed;
    
    // quiz total mark
    private Short totalMark;

    // student mark
    private Integer studentMark;

    // attempts
    private  Integer totalAttempts;
    public boolean isPassed() {
		return isPassed;
	}

	public void setPassed(boolean isPassed) {
		this.isPassed = isPassed;
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

	public Integer getTotalAttempts() {
		return totalAttempts;
	}

	public void setTotalAttempts(Integer totalAttempts) {
		this.totalAttempts = totalAttempts;
	}

	public Date getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

	
    private Date submitDate;


    
    public long getChildID() {
		return childID;
	}

	public void setChildID(long childID) {
		this.childID = childID;
	}

	public long getParentID() {
		return parentID;
	}

	public void setParentID(long parentID) {
		this.parentID = parentID;
	}

	public long getCourseID() {
		return courseID;
	}

	public void setCourseID(long courseID) {
		this.courseID = courseID;
	}

	public long getQuizID() {
		return quizID;
	}

	public void setQuizID(long quizID) {
		this.quizID = quizID;
	}

	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}

	    

}
