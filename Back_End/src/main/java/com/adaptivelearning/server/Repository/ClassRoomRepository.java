package com.adaptivelearning.server.Repository;


import com.adaptivelearning.server.Model.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ClassRoomRepository extends JpaRepository<Classroom, Integer> {
    Iterable<Classroom> findByCreator(Long creatorId);

    Optional<Classroom> findByClassroomName(String classroomName);
}
