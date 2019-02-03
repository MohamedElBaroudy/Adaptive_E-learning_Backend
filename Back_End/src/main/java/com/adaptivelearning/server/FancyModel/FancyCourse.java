package com.adaptivelearning.server.FancyModel;

import com.adaptivelearning.server.Model.Course;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class FancyCourse {
    // id
    private int courseId;

    // title
    private String title;

    // detailed title
    private String detailedTitle;

    // description
    private String description;

    // rate 0->5
    private short rate;

    // privacy  1-> public  2-> secret (for classroom)
    private boolean isPublic;

    // level 0->5 from easier to harder
    private short level;

    // category
    private String category;

    // publish date
    private LocalDate publishDate;

    // number of students
    private Integer numberOfStudents=0;

    // publisher id
    private Integer publisherId;

    public FancyCourse() {
    }

    public FancyCourse toFancyCourseMapping(Course course){
        this.courseId = course.getCourseId();
        this.title = course.getTitle();
        this.detailedTitle = course.getDetailedTitle();
        this.description = course.getDescription();
        this.publishDate = course.getPublishDate();
        this.level = course.getLevel();
        this.category = course.getCategory();
        this.numberOfStudents = course.getNumberOfStudents();
        this.isPublic = course.isPublic();
        this.rate = course.getRate();
        this.publisherId = course.getPublisher().getUserId();
        return this;
    }

    public List<Integer> toCourseIdListMapping(List<Course> courses){
        List<Integer> courseIdList = new LinkedList<>();
        for (Course course:
                courses) {
            ((LinkedList<Integer>) courseIdList).addLast(course.getCourseId());
        }
        return courseIdList;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetailedTitle() {
        return detailedTitle;
    }

    public void setDetailedTitle(String detailedTitle) {
        this.detailedTitle = detailedTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public short getRate() {
        return rate;
    }

    public void setRate(short rate) {
        this.rate = rate;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public short getLevel() {
        return level;
    }

    public void setLevel(short level) {
        this.level = level;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    public Integer getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(Integer numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public Integer getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Integer publisherId) {
        this.publisherId = publisherId;
    }
}
