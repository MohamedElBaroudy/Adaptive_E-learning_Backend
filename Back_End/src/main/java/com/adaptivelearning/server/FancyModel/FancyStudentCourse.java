package com.adaptivelearning.server.FancyModel;

import java.util.LinkedList;
import java.util.List;

import com.adaptivelearning.server.Model.StudentCourse;

public class FancyStudentCourse {
    // user id
    private Long userId;

    // course id
    private Long courseId;
   
    // student rank
    private float rank;

    // student rate
    private float rate;

    public FancyStudentCourse() {

    }

    public FancyStudentCourse toFancyStudentCourse(StudentCourse studentCourse){
        this.userId = studentCourse.getUser().getUserId();
        this.courseId = studentCourse.getCourse().getCourseId();
        this.rank = studentCourse.getRank();
        this.rate = studentCourse.getRate();

        return this;
    }

    public FancyStudentCourse toFancyStudentCoursedetailed(StudentCourse studentCourse){
        this.userId = studentCourse.getUser().getUserId();
        this.courseId = studentCourse.getCourse().getCourseId();
        this.rank = studentCourse.getRank();
        this.rate = studentCourse.getRate();

        return this;
    }

    public List<FancyStudentCourse> toFancyStudentCourseListMapping(List<StudentCourse> StudentCourses){
        List<FancyStudentCourse> FancyStudentCourseList = new LinkedList<>();
        for (StudentCourse studentCourse:
        	StudentCourses) {
        	FancyStudentCourse fancyStudentCourse = new FancyStudentCourse();
            ((LinkedList<FancyStudentCourse>) FancyStudentCourseList)
                    .addLast(fancyStudentCourse.toFancyStudentCoursedetailed(studentCourse));
        }
        return FancyStudentCourseList;
    }
    
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public float getRank() {
        return rank;
    }

    public void setRank(float rank) {
        this.rank = rank;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
}
