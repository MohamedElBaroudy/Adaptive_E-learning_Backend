package com.adaptivelearning.server.Repository;


import com.adaptivelearning.server.Model.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClassroomRepository extends JpaRepository<Classroom, Integer> {
    Classroom findByClassroomId(Integer classroomId);

    Iterable<Classroom> findByCreator(Integer creatorId);

    Classroom findByClassroomName(String classroomName);

    Boolean existsByClassroomName(String classroomName);
}
