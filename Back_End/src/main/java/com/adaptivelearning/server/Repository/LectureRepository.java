package com.adaptivelearning.server.Repository;

import com.adaptivelearning.server.Model.Lecture;
import com.adaptivelearning.server.Model.Section;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<Lecture,Long> {
    Lecture findByLectureId(Long lectureId);
    List<Lecture> findBySectionAndIsQuiz(Section section ,boolean isquiz );
}
