package com.adaptivelearning.server.FancyModel;

import com.adaptivelearning.server.Model.User;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

public class FancyUser {
    // id
    private int userId;

    // fname
    private String firstName;

    // lname
    private String lastName;

    // email
    private String email;

    //username
    private String username;

    // DOB
    private String dateOfBirth;

    // age
    private short age;

    // token for rest api purpose
//    private String token;

    // gender 1-> male , 2-> female 3-> other
    private String gender;

    // grade   if child
    private String grade;

    // bool values
    private boolean isTeacher = false;

    private boolean isChild = false;

    private boolean isParent = false;
    // end of bool values



    public FancyUser(){
    }


    public FancyUser toFancyUserMapper(User user){
        short userAge=0;

        if (user.getDateOfBirth() != null)
            userAge = (short)Period.between(user.getDateOfBirth(), LocalDate.now()).getYears();

        String userGender = "other";

        if(user.getGender()==1)
            userGender = "male";
        else if (user.getGender()==2)
            userGender = "female";

        this.userId = user.getUserId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.dateOfBirth = user.getDateOfBirth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.age = userAge;
//        this.token = user.getToken();
        this.gender = userGender;
        this.grade = user.getGrade();
        this.isTeacher = user.isTeacher();
        this.isChild = user.isChild();
        this.isParent = user.isParent();

        return this;

    }
    
    public FancyUser toTeacherMapper(User user){
        short userAge=0;

        if (user.getDateOfBirth() != null)
            userAge = (short)Period.between(user.getDateOfBirth(), LocalDate.now()).getYears();

        String userGender = "other";

        if(user.getGender()==1)
            userGender = "male";
        else if (user.getGender()==2)
            userGender = "female";

        this.userId = user.getUserId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.dateOfBirth = user.getDateOfBirth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.age = userAge;
        this.gender = userGender;

        return this;

    }


    public List<Integer> toUserIdListMapping(List<User> users){
        List<Integer> userIdList = new LinkedList<>();
        for (User user:
                users) {
            ((LinkedList<Integer>) userIdList).addLast(user.getUserId());
        }
        return userIdList;
    }

    public List<FancyUser> toFancyUserListMapping(List<User> users){
        List<FancyUser> fancyUserList = new LinkedList<>();
        for (User user:
                users) {
            FancyUser fancyUser = new FancyUser();
            ((LinkedList<FancyUser>) fancyUserList)
                    .addLast(fancyUser.toFancyUserMapper(user));
        }
        return fancyUserList;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public short getAge() {
        return age;
    }

    public void setAge(short age) {
        this.age = age;
    }

//    public String getToken() {
//        return token;
//    }

//    public void setToken(String token) {
//        this.token = token;
//    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

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
}
