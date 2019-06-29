package com.adaptivelearning.server.FancyModel;

import java.util.LinkedList;
import java.util.List;

import com.adaptivelearning.server.Model.Course;
import com.adaptivelearning.server.Model.Report;
import com.adaptivelearning.server.Model.Section;
import com.adaptivelearning.server.Model.User;
import com.adaptivelearning.server.Model.Quiz;


public class FancyReport {
	
	 private Long reportId;
     private boolean isPassed;
	 private Short totalMark;
     private Integer studentMark;
     private Integer totalAttempts;
 	 private FancyUser parent;
 	 private FancyUser child;
 	 private FancyQuiz Quiz;
 	 private FancyCourse course;
     
 	public FancyReport toFancyReportMapping(Report report,User parent,User child,Quiz quiz,Course course){
        FancyUser fancyparent= new FancyUser();
        FancyUser fancychild= new FancyUser();
        FancyQuiz fancyquiz = new FancyQuiz();
        FancyCourse fancycourse = new FancyCourse();
        
        this.reportId =report.getReportId();
        this.isPassed=report.isPassed();
        this.totalMark=report.getTotalMark();
        this.totalAttempts=report.getTotalAttempts();
        this.studentMark=report.getStudentMark();
       
        this.child = fancychild.toFancyUserMapper(child);
        this.parent = fancyparent.toFancyUserMapper(parent);
        this.course = fancycourse.toFancyCourseMapping(course, child);
        this.Quiz=fancyquiz.toFancyQuizMapping(quiz, false);
        return this;
    }
 	
 	
     // setters & getters
     public Long getReportId() {
		return reportId;
	}
	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}
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
	public FancyUser getParent() {
		return parent;
	}
	public void setParent(FancyUser parent) {
		this.parent = parent;
	}
	public FancyUser getChild() {
		return child;
	}
	public void setChild(FancyUser child) {
		this.child = child;
	}
	public FancyQuiz getQuiz() {
		return Quiz;
	}
	public void setQuiz(FancyQuiz quiz) {
		Quiz = quiz;
	}
	public FancyCourse getCourse() {
		return course;
	}
	public void setCourse(FancyCourse course) {
		this.course = course;
	}
	 
	 
	 
}
