package com.adaptivelearning.server.Classes;


import java.util.Objects;

public class CustomCourse {
    public Long courseId;
    public String title;

    public CustomCourse(Long courseId, String title) {
        this.courseId = courseId;
        this.title = title;
    }

    public Long getCourseId() {
        return courseId;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomCourse)) return false;
        CustomCourse that = (CustomCourse) o;
        return getCourseId().equals(that.getCourseId()) &&
                getTitle().equals(that.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCourseId(), getTitle());
    }
}
