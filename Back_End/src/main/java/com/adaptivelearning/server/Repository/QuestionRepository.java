package com.adaptivelearning.server.Repository;

import com.adaptivelearning.server.Model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question,Long> {
    Question findByQuestionId(Long questionId);
}
