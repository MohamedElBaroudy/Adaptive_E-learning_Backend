package com.adaptivelearning.server.Model;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Answer",uniqueConstraints = {
        @UniqueConstraint(columnNames = "ID")})
@JsonIdentityInfo(
        scope= Answer.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "answerId")
public class Answer {
    // id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Long answerId;

    // body
    @NotBlank
    @Size(max = 1000)
    @Column(name = "BODY")
    private String body;

    // is correct
    @Column(name = "IS_CORRECT")
    private boolean isCorrect;

    //mapping
    @ManyToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "QUESTION")
    private Question question;
    //end of mapping


    public Answer() {
    }

    public Answer(@NotBlank @Size(max = 1000) String body,
                  boolean isCorrect) {
        this.body = body;
        this.isCorrect = isCorrect;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}
