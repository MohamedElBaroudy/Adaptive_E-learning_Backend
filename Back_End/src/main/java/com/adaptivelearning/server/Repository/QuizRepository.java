package com.adaptivelearning.server.Repository;

import com.adaptivelearning.server.Classes.CustomQuiz;
import com.adaptivelearning.server.Model.Lecture;
import com.adaptivelearning.server.Model.Quiz;
import com.adaptivelearning.server.Model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuizRepository extends JpaRepository<Quiz,Long> {
    Quiz findByQuizId(Long quizId);

    Quiz findBySection(Section section);

    @Query("SELECT new com.adaptivelearning.server.Classes.CustomQuiz(quizId,title,instructions,time,totalMark,no_of_questions) from Quiz where quizId = :x ")
    CustomQuiz getCustomQuiz(@Param("x") Long quizId);
}
