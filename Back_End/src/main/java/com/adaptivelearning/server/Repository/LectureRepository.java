package com.adaptivelearning.server.Repository;

import com.adaptivelearning.server.Model.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<Lecture,Long> {
    Lecture findByLectureId(Long lectureId);
}
