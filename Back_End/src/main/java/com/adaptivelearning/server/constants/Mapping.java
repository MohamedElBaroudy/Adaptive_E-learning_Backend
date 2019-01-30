package com.adaptivelearning.server.constants;

public class Mapping {
    //Base
    public static final String TEACHER = "/teacher";
    public static final String STUDENT = "/student";
    public static final String PARENT = "/parent";
    public static final String COURSE = "/course";

    //authentication
    public static final String AUTH = "/auth"; // base
    public static final String LOGIN = "/login";
    public static final String REGISTER = "/register";
    public static final String LOGOUT = "/logout";


    //for classrooms & common for user and kids
    public static final String CLASSROOMS = "/classrooms";
    public static final String CLASSROOM = "/classroom";

    //Student
    public static final String EnrollStudent = "/enroll";

    //parent
    public static final String AddChild = "/addchild";
    public static final String ENROLLCHILD = "/enrollchild";
    public static final String CHILDREN = "/children";

}
