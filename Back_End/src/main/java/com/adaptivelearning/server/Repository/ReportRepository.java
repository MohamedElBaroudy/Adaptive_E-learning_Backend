package com.adaptivelearning.server.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adaptivelearning.server.Model.Report;

public interface ReportRepository  extends JpaRepository<Report,Long> {

	Report findByReportId(Long reportId);
	List<Report> findByChildID(Long ChildId);
	List<Report> findByCourseIDAndChildID(Long courseId,Long ChildId);
}
