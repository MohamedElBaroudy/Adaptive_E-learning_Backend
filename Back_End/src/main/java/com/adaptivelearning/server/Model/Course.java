package com.adaptivelearning.server.Model;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Course",uniqueConstraints = {
        @UniqueConstraint(columnNames = "ID")})
@JsonIdentityInfo(
        scope=Classroom.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "courseId")
public class Course {
    // id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private int courseId;

    // title
    @NotBlank
    @Size(max = 40)
    @Column(name = "TITLE")
    private String title;

    // detailed title
    @NotBlank
    @Size(max = 100)
    @Column(name = "DETAILEDTITLE")
    private String detailedTitle;

    // description
    @NotBlank
    @Size(max = 1000)
    @Column(name = "DESCRIPTION")
    private String description;

    // rate 0->5
    @NotNull
    @Column(name = "RATE")
    private short rate;

    // privacy  1-> public  2-> secret (for classroom)
    @NotNull
    @Column(name = "ISPUBLIC")
    private boolean isPublic;

    // level 0->5 from easier to harder
    @NotNull
    @Column(name = "LEVEL")
    private short level;


    // Mapping
    @ManyToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "PUBLISHER")
    private User publisher;



    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.REFRESH})
    @JoinTable(name = "student_courses",
            joinColumns = {@JoinColumn(name = "classroom_id")},
            inverseJoinColumns = {@JoinColumn(name = "student_id")})
    private List<User> learners;




    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.REFRESH},
            mappedBy = "courses")
    private List<Classroom> classrooms=new ArrayList<Classroom>();




    @OneToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL},
            mappedBy = "course")
    private List<Section> sections;
    // end of mapping


    public Course() {
    }

    public Course(@NotBlank @Size(max = 40) String title,
                  @NotBlank @Size(max = 100) String detailedTitle,
                  @NotBlank @Size(max = 1000) String description,
                  @NotNull boolean isPublic,
                  @NotNull short level) {
        this.title = title;
        this.detailedTitle = detailedTitle;
        this.description = description;
        this.isPublic = isPublic;
        this.level = level;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetailedTitle() {
        return detailedTitle;
    }

    public void setDetailedTitle(String detailedTitle) {
        this.detailedTitle = detailedTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public short getRate() {
        return rate;
    }

    public void setRate(short rate) {
        this.rate = rate;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public short getLevel() {
        return level;
    }

    public void setLevel(short level) {
        this.level = level;
    }

    public User getPublisher() {
        return publisher;
    }

    public void setPublisher(User publisher) {
        this.publisher = publisher;
    }

    public List<User> getLearners() {
        return learners;
    }

    public void setLearners(List<User> learners) {
        this.learners = learners;
    }

    public List<Classroom> getClassrooms() {
        return classrooms;
    }

    public void setClassrooms(List<Classroom> classrooms) {
        this.classrooms = classrooms;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }
}
