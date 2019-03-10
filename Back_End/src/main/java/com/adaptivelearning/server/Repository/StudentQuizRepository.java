package com.adaptivelearning.server.Repository;

import com.adaptivelearning.server.Model.Quiz;
import com.adaptivelearning.server.Model.StudentQuiz;
import com.adaptivelearning.server.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentQuizRepository extends JpaRepository<StudentQuiz, Long> {
    StudentQuiz findByUserAndQuiz(User user, Quiz quiz);
}
