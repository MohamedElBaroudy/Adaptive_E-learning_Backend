package com.adaptivelearning.server.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.hibernate.annotations.NaturalId;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Client_User" ,uniqueConstraints = {
        @UniqueConstraint(columnNames = "ID"),
        @UniqueConstraint(columnNames = "EMAIL"),
        @UniqueConstraint(columnNames = "USERNAME")})
@JsonIdentityInfo(
        scope=User.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "userId")
public class User {
    // id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Long userId;

    // fname
    @NotBlank
    @Size(max = 40)
    @Column(name = "FIRST_NAME")
    private String firstName;

    // lname
    @NotBlank
    @Size(max = 40)
    @Column(name = "LAST_NAME")
    private String lastName;

    // email
    @NaturalId
    @Size(max = 40)
    @Email
    @Column(name = "EMAIL")
    private String email;

    //username
    @NaturalId
    @NotBlank
    @Size(max = 30)
    @Column(name = "USERNAME")
    private String username;

    // password
    @NotBlank
    @Size(max = 100)
    private String password;

    // DOB
    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name = "DATEOFBIRTH")
    private LocalDate dateOfBirth;

    // token for rest api purpose
    @Ignore
    @Column(name = "TOKEN")
    private String token;

    // gender 1-> male , 2-> female 3-> other
    @NotNull
    @Column(name = "GENDER")
    private short gender;


    // grade   if child
    @Column(name = "GRADE")
    private String grade;

    // bool values

    @Column(name = "IS_TEACHER")
    private boolean isTeacher = false;

    @Column(name = "IS_CHILD")
    private boolean isChild = false;

    @Column(name = "IS_PARENT")
    private boolean isParent = false;


    // end of bool values



    // Mapping
    @OneToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.REMOVE},
            mappedBy = "parent")
    private List<User> children;

    @ManyToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "PARENT")
    private User parent;




    @OneToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.REMOVE},
            mappedBy = "creator")
    private List<Classroom> classrooms;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.REMOVE},
            mappedBy = "publisher")
    private List<Course> courses;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.REFRESH},
            mappedBy = "students")
    private List<Classroom> joins;



    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.REFRESH},
            mappedBy = "learners")
    private List<Course> enrolls;

//    @ManyToMany(fetch = FetchType.LAZY,
//            cascade = {CascadeType.REFRESH},
//            mappedBy = "raters")
//    private List<Course> rates;
    
   @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.REFRESH},
            mappedBy = "savedBy")
    private List<Course> savedCourses; 

   @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
   @JoinColumn(name = "pic_id")
   private MediaFile profile_picture;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "user",
            cascade = CascadeType.REMOVE)
    private List<StudentQuiz> studentQuizs;
    // end of mapping



    public User() {

    }

    public User(@NotBlank @Size(max = 40) String firstName,
                @NotBlank @Size(max = 40) String lastName,
                @NotBlank @Size(max = 40) @Email String email,
                @NotBlank @Size(max = 30) String username,
                @NotBlank @Size(max = 100) String password,
                LocalDate dateOfBirth,
                @NotNull short gender,
                String grade) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.grade=grade;
    }

    public User(@NotBlank @Size(max = 40) String firstName,
                @NotBlank @Size(max = 40) String lastName,
                @NotBlank @Size(max = 40) @Email String email,
                @NotBlank @Size(max = 30) String username,
                @NotBlank @Size(max = 100) String password,
                LocalDate dateOfBirth,
                @NotNull short gender
               ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public short getGender() {
        return gender;
    }

    public void setGender(short gender) {
        this.gender = gender;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public List<User> getChildren() {
        return children;
    }

    public List<Classroom> getClassrooms() {
        return classrooms;
    }

    public List<Classroom> getJoins() {
        return joins;
    }

    public User getParent() {
        return parent;
    }

    public void setParent(User parent) {
        this.parent = parent;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public List<Course> getEnrolls() {
        return enrolls;
    }

//    public List<Course> getRates() {
//        return rates;
//    }
//
//    public void setRates(List<Course> rates) {
//        this.rates = rates;
//    }

    public boolean isTeacher() {
        return isTeacher;
    }

    public void setTeacher(boolean teacher) {
        isTeacher = teacher;
    }

    public boolean isChild() {
        return isChild;
    }

    public void setChild(boolean child) {
        isChild = child;
    }

    public boolean isParent() {
        return isParent;
    }

    public void setParent(boolean parent) {
        isParent = parent;
    }

    public List<Course> getSavedCourses() {
		return savedCourses;
	}

	public void setSavedCourses(List<Course> savedCourses) {
		this.savedCourses = savedCourses;
	}

	public MediaFile getProfile_picture() {
		return profile_picture;
	}

	public void setProfile_picture(MediaFile profile_picture) {
		this.profile_picture = profile_picture;
	}

    public List<StudentQuiz> getStudentQuizs() {
        return studentQuizs;
    }

    public void setStudentQuizs(List<StudentQuiz> studentQuizs) {
        this.studentQuizs = studentQuizs;
    }
}