package com.adaptivelearning.server.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.hibernate.annotations.NaturalId;


import java.io.Serializable;
import java.util.Date;
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
public class User implements Serializable {
    // id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private int userId;

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
    @NotBlank
    @Size(max = 40)
    @Email
    @Column(name = "EMAIL")
    private String email;

    //username
    @NaturalId
    @NotBlank
    @Size(max = 30)
    @Column(name = "USERNAME")
    private String userName;

    // password
    @NotBlank
    @Size(max = 100)
    private String password;

    // DOB
    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name = "DATEOFBIRTH")
    private Date dateOfBirth;

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



    // Mapping
    @OneToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL},
            mappedBy = "parent")
    private List<User> children;

    @ManyToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "PARENT")
    private User parent;




    @OneToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL},
            mappedBy = "creator")
    private List<Classroom> classrooms;


    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.REFRESH},
            mappedBy = "students")
//    @JoinColumn(name = "CLASSROOMS")
    private List<Classroom> joins;

    // end of mapping



    public User() {

    }

    public User(@NotBlank @Size(max = 40) String firstName,
                @NotBlank @Size(max = 40) String lastName,
                @NotBlank @Size(max = 40) @Email String email,
                @NotBlank @Size(max = 30) String userName,
                @NotBlank @Size(max = 100) String password,
                Date dateOfBirth,
                @NotNull short gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
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
}