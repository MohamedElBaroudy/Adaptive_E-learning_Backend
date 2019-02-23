package com.adaptivelearning.server.Model;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Course",uniqueConstraints = {
        @UniqueConstraint(columnNames = "ID")})
@JsonIdentityInfo(
        scope=Course.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "courseId")
public class Course {
    // id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Long courseId;

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
    private float rate;

    // privacy  1-> public  2-> secret (for classroom)
    @NotNull
    @Column(name = "ISPUBLIC")
    private boolean isPublic;

    // level 0->5 from easier to harder
    @NotNull
    @Column(name = "LEVEL")
    private short level;

    // publish date
    @NotNull
    @Column(name = "PUBLISH_DATE")
    private Date publishDate;

    @NotNull
    @Column(name = "NUMBER_OF_STUDENTS")
    private Integer numberOfStudents=0;

    @NotNull
    @Column(name = "NUMBER_OF_RATERS")
    private Integer numberOfRaters=0;

    // Mapping
    @ManyToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "PUBLISHER")
    private User publisher;

    @ManyToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "CATEGORY")
    private Category category;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.REFRESH})
    @JoinTable(name = "student_courses",
            joinColumns = {@JoinColumn(name = "course_id")},
            inverseJoinColumns = {@JoinColumn(name = "student_id")})
    private List<User> learners;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.REFRESH})
    @JoinTable(name = "student_courses_rates",
            joinColumns = {@JoinColumn(name = "course_id")},
            inverseJoinColumns = {@JoinColumn(name = "student_id")})
    private List<User> raters;



    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.REFRESH})
    @JoinTable(name = "user_courses_saved",
            joinColumns = {@JoinColumn(name = "course_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private List<User> savedBy ; 


    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.REFRESH},
            mappedBy = "courses")
    private List<Classroom> classrooms=new ArrayList<Classroom>();

    @OneToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.REMOVE},
            mappedBy = "course")
    private List<Section> sections;
    
    
    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JoinColumn(name = "pic_id")
    private MediaFile course_picture;
	
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
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date localdate = new Date();
        this.publishDate = localdate;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
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

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
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

    public List<User> getRaters() {
        return raters;
    }

    public void setRaters(List<User> raters) {
        this.raters = raters;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public Integer getNumberOfStudents() {
        return numberOfStudents;
    }

    public void increamentStudents() { this.numberOfStudents += 1; }

    public Integer getNumberOfRaters() { return numberOfRaters; }

    public void increamentRaters() { this.numberOfRaters += 1; }

	public List<User> getSavedBy() {
		return savedBy;
	}

	public void setSavedBy(List<User> savedBy) {
		this.savedBy = savedBy;
	}

	public MediaFile getCourse_picture() {
		return course_picture;
	}

	public void setCourse_picture(MediaFile course_picture) {
		this.course_picture = course_picture;
	}
}
