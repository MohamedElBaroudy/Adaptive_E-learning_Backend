package com.adaptivelearning.server.Repository;

import com.adaptivelearning.server.Classes.CustomCourse;
import com.adaptivelearning.server.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import com.adaptivelearning.server.Model.Course;
import org.springframework.data.jpa.repository.Query;
import java.util.ArrayList;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

	Course findByCourseId(Long courseId);

	List<Course> findByCategoryAndIsPublic(Category category, boolean isPublic);

	@Query("SELECT a FROM Course a WHERE isPublic=true " +
			"ORDER BY numberOfStudents DESC ")
	List<Course> findHotestCourses();

	@Query("SELECT a FROM Course a WHERE isPublic=true " +
			"ORDER BY publishDate DESC")
	List<Course> findNewestCourses();

	@Query("SELECT a FROM Course a WHERE isPublic=true " +
			"ORDER BY rate DESC")
	List<Course> findTopRatedCourses();


	@Query(value = "SELECT new com.adaptivelearning.server.Classes.CustomCourse(courseId,title) from Course WHERE isPublic=true ")
	ArrayList<CustomCourse> findAllCourses();
}






