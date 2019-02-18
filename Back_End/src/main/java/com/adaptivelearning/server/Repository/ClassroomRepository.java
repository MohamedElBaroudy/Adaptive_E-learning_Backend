package com.adaptivelearning.server.Repository;


import com.adaptivelearning.server.Model.Classroom;
import com.adaptivelearning.server.Model.User;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
    Classroom findByClassroomId(Long classroomId);

    Iterable<Classroom> findByCreator(Long creatorId);

    Classroom findByClassroomName(String classroomName);

    Boolean existsByPassCode (String passCode);

    Classroom findByPassCode(String passCode);
}
