package com.adaptivelearning.server.constants;

public class Mapping {
    public static final String LOGIN = "/login";
    public static final String REGISTER = "/register";
    public static final String LOGOUT = "/logout";
    public static final String AUTH = "/auth";

    public static final String PARENT = "/parent";


    //for classrooms & common for user and kids
    public static final String TEACHERCLASSROOMS = "/classrooms/created";
    public static final String STUDENTCLASSROOMS = "/classrooms/joined";
    public static final String CLASSROOM = "/classroom";

    //Student
    public static final String EnrollStudent = "/enroll";

    //parent
    public static final String AddChild = "/addchild";
    public static final String PARENTENROLL = "/parentenroll";
    public static final String PARENTCHILDREN = "/children";
}
