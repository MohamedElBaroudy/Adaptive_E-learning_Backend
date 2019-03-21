package com.adaptivelearning.server.Repository;

import com.adaptivelearning.server.Model.Question;
import com.adaptivelearning.server.Model.Quiz;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuestionRepository extends JpaRepository<Question,Long> {
    Question findByQuestionId(Long questionId);
    List<Question> findByQuiz(Quiz quiz);

}
