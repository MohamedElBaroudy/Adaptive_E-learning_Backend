package com.adaptivelearning.server.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "Quiz",uniqueConstraints = {
        @UniqueConstraint(columnNames = "ID")})
@JsonIdentityInfo(
        scope= Quiz.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "quizId")
public class Quiz {

    // id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Long quizId;

    // title
    @NotBlank
    @Size(max = 40)
    @Column(name = "TITLE")
    private String title;


    // instructions
    @NotBlank
    @Size(max = 1000)
    @Column(name = "INSTRUCTIONS")
    private String instructions;

    // time in secs
    @NotNull
    @Column(name = "TIME")
    private short time;

    @NotNull
    @Column(name = "TOTAL_MARK")
    private Short totalMark=0;



    // mapping
    @OneToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "LECTURE")
    private Lecture lecture;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "quiz",
            cascade = CascadeType.REMOVE)
    private List<StudentQuiz> studentQuizs;

    @OneToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.REMOVE},
            mappedBy = "quiz")
    private List<Question> questions;
    // end of mapping


    public Quiz() {
    }

    public Quiz(@NotBlank @Size(max = 40) String title,
                @NotBlank @Size(max = 1000) String instructions,
                @NotNull short time,
                @NotNull Short totalMark) {
        this.title = title;
        this.instructions = instructions;
        this.time = time;
        this.totalMark = totalMark;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public short getTime() {
        return time;
    }

    public void setTime(short time) {
        this.time = time;
    }

    public Short getTotalMark() {
        return totalMark;
    }

    public void setTotalMark(Short totalMark) {
        this.totalMark = totalMark;
    }

    public Lecture getLecture() {
        return lecture;
    }

    public void setLecture(Lecture lecture) {
        this.lecture = lecture;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
