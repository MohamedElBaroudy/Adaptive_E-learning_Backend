package com.adaptivelearning.server.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    private Long questionId;


    // body
    @NotBlank
    @Size(max = 1000)
    @Column(name = "BODY")
    private String body;

    // is multiple choice
    @Column(name = "IS_MULTIPLE_CHOICE")
    private boolean isMultipleChoice = false;

    // mark
    @NotNull
    @Column(name = "MARK")
    private short mark;

    // level 1->3 from easier to harder -> easy=1 , medium(default)=2 , hard=3
    @NotNull
    @Column(name = "LEVEL")
    private short level = 2 ;

    @NotBlank
    @Size(max = 100)
    @Column(name = "Message")
    private String message;
    
    // mapping
    @ManyToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "QUIZ")
    private Quiz quiz;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.REMOVE},
            mappedBy = "question")
    private List<Answer> answers;
    // end of mapping


    public Question() {
    }

    public Question(@NotBlank @Size(max = 1000) String body,
                    boolean isMultipleChoice,
                    @NotNull short mark,
                    @NotNull short level, 
                    @NotBlank @Size(max = 100) String message ) {
        this.body = body;
        this.isMultipleChoice = isMultipleChoice;
        this.mark = mark;
        this.level=level;
        this.message=message;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
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

    public short getMark() {
        return mark;
    }

    public void setMark(short mark) {
        this.mark = mark;
    }

    public short getLevel() {
		return level;
	}

	public void setLevel(short level) {
		this.level = level;
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
