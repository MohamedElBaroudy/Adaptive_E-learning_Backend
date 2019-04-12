package com.adaptivelearning.server.Repository;

import com.adaptivelearning.server.Model.Question;
import com.adaptivelearning.server.Model.Quiz;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuestionRepository extends JpaRepository<Question,Long> {
    Question findByQuestionId(Long questionId);
    List<Question> findByQuiz(Quiz quiz);
    
    List<Question> findByQuizAndLevel(Quiz quiz,short level);
    
    @Query(value="SELECT * FROM Question where  quiz= ?1  and level = ?2 ORDER BY random() LIMIT ?3 ", nativeQuery = true)	
    List<Question> findRandom(long quizid, int level , int limit);

}
