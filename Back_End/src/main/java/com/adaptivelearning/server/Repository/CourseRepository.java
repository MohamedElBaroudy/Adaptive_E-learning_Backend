package com.adaptivelearning.server.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adaptivelearning.server.Model.Course;


public interface CourseRepository extends JpaRepository<Course, Integer> {
	Course findByCourseId (Integer sectionId);

}
