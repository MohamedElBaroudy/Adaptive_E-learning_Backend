package com.adaptivelearning.server.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adaptivelearning.server.Model.Course;
import com.adaptivelearning.server.Model.StudentCourse;
import com.adaptivelearning.server.Model.User;

public interface StudentCourseRepository extends JpaRepository<StudentCourse, Long> {
    StudentCourse findByUserAndCourse(User user, Course course);
    List<StudentCourse> findByUser(User user);
    
}
