package com.adaptivelearning.server.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.NaturalId;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "CLASSROOM",uniqueConstraints = {
        @UniqueConstraint(columnNames = "ID"),
        @UniqueConstraint(columnNames = "PASSCODE")})
@JsonIdentityInfo(
        scope=Classroom.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "classroomId")
public class Classroom {
    // id
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", unique = true, nullable = false)
    private Long classroomId;

    // classroom name   for searching purpose
    @NotBlank
    @Size(max = 40)
    @Column(name = "CLASSROOM_NAME")
    private String classroomName;

    // classroom passcode
   // @NaturalId      
    @NotBlank
    @Size(max = 255)
    @Column(name = "PASSCODE")
    private String passCode;


    // Mapping
    @ManyToOne(fetch = FetchType.EAGER,
    cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "CREATOR")
    private User creator;




    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.REFRESH})
    @JoinTable(name = "student_classrooms",
            joinColumns = {@JoinColumn(name = "classroom_id")},
            inverseJoinColumns = {@JoinColumn(name = "student_id")})
    private List<User> students;



    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.REFRESH})
    @JoinTable(name = "classrooms_courses",
            joinColumns = {@JoinColumn(name = "classroom_id")},
            inverseJoinColumns = {@JoinColumn(name = "course_id")})
    private List<Course> courses;
    
    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JoinColumn(name = "pic_id")
    private MediaFile classroom_picture;
	
    // end of mapping
    
    
    public Classroom() {
    }

    public Classroom(@NotBlank @Size(max = 40) String classroomName,
                     @NotBlank @Size(max = 50) String passCode) {
        this.classroomName = classroomName;
        this.passCode = passCode;
    }

    public Long getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Long classroomId) {
        this.classroomId = classroomId;
    }

    public String getClassroomName() {
        return classroomName;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }

    public String getPassCode() {
        return passCode;
    }

    public void setPassCode(String passCode) {
        this.passCode = passCode;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public List<User> getStudents() {
        return students;
    }

    public void setStudents(List<User> students) {
        this.students = students;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

	public MediaFile getClassroom_picture() {
		return classroom_picture;
	}

	public void setClassroom_picture(MediaFile classroom_picture) {
		this.classroom_picture = classroom_picture;
	}
}
