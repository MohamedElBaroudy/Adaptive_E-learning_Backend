package com.adaptivelearning.server.Repository;

import com.adaptivelearning.server.constants.Repos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.adaptivelearning.server.Model.Course;
import com.adaptivelearning.server.Model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Integer> {

	Course findByCourseId(Integer courseId);
	
	List<Course> findByCategoryAndIsPublic(String category,boolean isPublic);

	@Query("SELECT a FROM Course a WHERE isPublic=true " +
			"ORDER BY numberOfStudents DESC ")
	List<Course> findHotestCourses();

	@Query("SELECT a FROM Course a WHERE isPublic=true " +
			"ORDER BY publishDate DESC")
	List<Course> findNewestCourses();

	@Query("SELECT a FROM Course a WHERE isPublic=true " +
			"ORDER BY rate DESC")
	List<Course> findTopRatedCourses();
	
	/*@Modifying
	@Query("UPDATE Course c SET c.title = :newTitle , c.detailedTitle = :newDetailedtilte , c.description = :newDescription"
			+ "c.level = :newLevel , c.category = :newCategory WHERE c.courseId = :requiredCourseId ")
	 void updateCourseInfo(@Param("requiredCourseId") int requiredCourseId , @Param("newTitle") String newTitle, @Param("newDetailedtilte") String newDetailedtilte ,
    @Param("newDescription") String newDescription  , @Param("newLevel") Short newLevel , 
    @Param("newCategory") String newCategory);
	*/
}






