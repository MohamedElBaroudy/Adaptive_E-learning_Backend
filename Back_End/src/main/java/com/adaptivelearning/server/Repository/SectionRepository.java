package com.adaptivelearning.server.Repository;

import com.adaptivelearning.server.Model.Section;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionRepository extends JpaRepository<Section, Integer>{
	
	Section findBySectionId(Integer sectionId);
	
	Section findByTitle(String title);


}
