package com.adaptivelearning.server.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "Section",uniqueConstraints = {
        @UniqueConstraint(columnNames = "ID")})
@JsonIdentityInfo(
        scope=Section.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "sectionId")
public class Section {

    // id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private int sectionId;

    // title
    @NotBlank
    @Size(max = 40)
    @Column(name = "TITLE")
    private String title;



    // Mapping
    @ManyToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "COURSE")
    private Course course;

    @OneToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.REMOVE},
            mappedBy = "section")
    private List<Quiz> quizzes;

    // end of mapping


    public Section() {
    }

    public Section(@NotBlank @Size(max = 40) String title) {
        this.title = title;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<Quiz> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(List<Quiz> quizzes) {
        this.quizzes = quizzes;
    }
}
