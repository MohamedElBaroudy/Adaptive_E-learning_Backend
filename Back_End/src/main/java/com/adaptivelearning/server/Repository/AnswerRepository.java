package com.adaptivelearning.server.Repository;

import com.adaptivelearning.server.Model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer,Long> {
    Answer findByAnswerId(Long answerId);
}
