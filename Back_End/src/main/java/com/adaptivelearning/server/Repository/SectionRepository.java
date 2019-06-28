package com.adaptivelearning.server.Repository;

import com.adaptivelearning.server.Model.Course;
import com.adaptivelearning.server.Model.Section;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionRepository extends JpaRepository<Section, Long>{
	
	Section findBySectionId(Long sectionId);
	
	Section findByTitle(String title);

	List<Section> findByCourse(Course course);

}
