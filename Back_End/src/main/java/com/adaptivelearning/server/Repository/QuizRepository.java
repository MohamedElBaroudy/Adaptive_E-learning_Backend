package com.adaptivelearning.server.Repository;

import com.adaptivelearning.server.Model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz,Long> {
    Quiz findByQuizId(Long quizId);
}
