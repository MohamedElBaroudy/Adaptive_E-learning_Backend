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

    @Column(name = "rate")
    private float rate = -1;


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

	public void setRank(float rank) {
		this.rank = rank;
	}

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentCourse that = (StudentCourse) o;
        return Float.compare(that.rank, rank) == 0 &&
        Float.compare(that.rate, rate) == 0 &&
        Objects.equals(studentCourseId, that.studentCourseId) &&
        Objects.equals(user, that.user) &&
        Objects.equals(course, that.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentCourseId, user, course, rank, rate);
    }
    
 // function to put newRank between certain range 0-10
 	public float adjustedRank (float newRank) {
 		if(newRank > 10 )
 			return 10.0f ;
 		else if(newRank < 0 )
 			return 0.0f ;
 		else 
 			return newRank ;
 	}

 	public float updateRank(float oldRank, int no_of_attempts, float quiz_grade) {
 		// we must make the rank range between 0-10 , 0 <= rank <= 10
 		// 0-3 low level
 		// 4-7 medium level
 		// 7-10 advanced level
 		float newRank = 0.0f ;
         if (quiz_grade >= 80 && no_of_attempts >= 0 && no_of_attempts < 4) 
         {
         	newRank = oldRank + 3 ;
         	return adjustedRank(newRank) ;
         }
         else if (quiz_grade >= 80 && no_of_attempts >= 4 && no_of_attempts < 8 )
 		{
         	newRank = oldRank + 1.5f ;
         	return adjustedRank(newRank) ;
 		}
         else if (quiz_grade >= 80 && no_of_attempts >= 8)
 		{
         	newRank = oldRank + 0.5f ;
         	return adjustedRank(newRank) ;
 		}
         else if (quiz_grade >= 60 && quiz_grade < 80 && no_of_attempts >= 0 && no_of_attempts < 4)
         {
         	newRank = oldRank + 2 ;
         	return adjustedRank(newRank) ;
         }
         else if (quiz_grade >= 60 && quiz_grade < 80 && no_of_attempts >= 4 && no_of_attempts < 8)
         {
         	newRank = oldRank + 1 ;
         	return adjustedRank(newRank) ;
         }
         else if (quiz_grade >= 60 && quiz_grade < 80 && no_of_attempts >= 8)
         {
         	newRank = oldRank + 0.25f ;
         	return adjustedRank(newRank) ;
         }
         else if (quiz_grade < 60 && no_of_attempts >= 0 && no_of_attempts < 4)
         {
         	newRank = oldRank - 1 ;
         	return adjustedRank(newRank) ;
         }
         else if (quiz_grade < 60 && no_of_attempts >= 4 && no_of_attempts < 8)
         {
         	newRank = oldRank - 2 ;
         	return adjustedRank(newRank) ;
         }
         else // quiz_grade < 60 && no_of_attempts >= 8
         {
         	newRank = oldRank - 3 ;
         	return adjustedRank(newRank) ;
         }
 	}
    
}
