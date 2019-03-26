package com.adaptivelearning.server.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adaptivelearning.server.Model.Course;
import com.adaptivelearning.server.Model.StudentCourse;
import com.adaptivelearning.server.Model.User;

public interface StudentCourseRepository extends JpaRepository<StudentCourse, Long> {
    StudentCourse findByUserAndCourse(User user, Course course);
}
