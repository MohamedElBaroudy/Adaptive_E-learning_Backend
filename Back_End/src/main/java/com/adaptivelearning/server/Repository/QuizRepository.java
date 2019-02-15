package com.adaptivelearning.server.Repository;

import com.adaptivelearning.server.Model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz,Integer> {
}
