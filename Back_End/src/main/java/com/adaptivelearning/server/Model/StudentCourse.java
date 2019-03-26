package com.adaptivelearning.server.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "Student_Course")
public class StudentCourse{
    @EmbeddedId
    private StudentCourseId studentCourseId;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.REFRESH})
    @JoinColumn
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.REFRESH})
    @JoinColumn
    @MapsId("courseId")
    private Course course;

    @Column(name = "student_rank")
    private float rank = 0;


    public StudentCourse() {
    }

    public StudentCourse(User user,Course course) {
        this.user = user;
        this.course = course;
        this.studentCourseId = new StudentCourseId(user.getUserId(),course.getCourseId());
    }



    public StudentCourseId getStudentCourseId() {
		return studentCourseId;
	}

	public void setStudentCourseId(StudentCourseId studentCourseId) {
		this.studentCourseId = studentCourseId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public float getRank() {
		return rank;
	}

	public void setMark(float rank) {
		this.rank = rank;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentCourse that = (StudentCourse) o;
        return Objects.equals(user, that.user) &&
                Objects.equals(course, that.course) &&
                Objects.equals(rank, that.rank) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, course, rank);
    }
}
