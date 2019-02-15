package com.adaptivelearning.server.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "Question",uniqueConstraints = {
        @UniqueConstraint(columnNames = "ID")})
@JsonIdentityInfo(
        scope= Quiz.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "questionId")
public class Question {
    // id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private int questionId;


    // body
    @NotBlank
    @Size(max = 1000)
    @Column(name = "BODY")
    private String body;

    // is multiple choice
    @Column(name = "IS_MULTIPLE_CHOICE")
    private boolean isMultipleChoice = false;



    // mapping
    @ManyToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "QUIZ")
    private Quiz quiz;

    @OneToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.ALL},
            mappedBy = "question")
    private List<Answer> answers;
    // end of mapping


    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isMultipleChoice() {
        return isMultipleChoice;
    }

    public void setMultipleChoice(boolean multipleChoice) {
        isMultipleChoice = multipleChoice;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
