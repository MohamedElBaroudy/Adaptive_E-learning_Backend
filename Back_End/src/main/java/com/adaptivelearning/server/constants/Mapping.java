package com.adaptivelearning.server.constants;

public class Mapping {
    //Base
    private static final String TEACHER = "/teacher";
    private static final String STUDENT = "/student";
    private static final String PARENT = "/parent";
    public static final String COURSE = "/course";
    private static final String AUTH = "/auth";

    //Authentication
    public static final String LOGIN = AUTH + "/login";
    public static final String REGISTER = AUTH + "/register";
    public static final String LOGOUT = AUTH + "/logout";

    //General user
    public static final String PROFILE = "/profile";


    //For courses
    public static final String NEW_COURSES = "/new_courses";
    public static final String HOT_COURSES = "/hot_courses";
    public static final String SHOW_COURSE = "/show_course";


    //Teacher
    public static final String TEACHER_CLASSROOMS = TEACHER + "/classrooms";
    public static final String TEACHER_CLASSROOM = TEACHER + "/classroom";
    public static final String TEACHER_COURSES = TEACHER + "/courses";
    public static final String TEACHER_CLASSROOM_COURSES = TEACHER + "/classroom_courses";



    //Student
    public static final String JOIN_CLASSROOM = STUDENT + "/join_classroom";
    public static final String ENROLL_COURSE = STUDENT + "/enroll_course";
    public static final String STUDENT_CLASSROOMS = STUDENT + "/classrooms";
    public static final String STUDENT_COURSES = STUDENT + "/courses";
    public static final String STUDENT_RATE_COURSE = STUDENT + "/rate_course";


    //Parent
    public static final String ADD_CHILD = PARENT + "/add_child";
    public static final String JOIN_CHILD_IN_CLASSROOM = PARENT + "/join_child_classroom";
    public static final String ENROLL_CHILD_IN_COURSE = PARENT + "/enroll_child_course";
    public static final String CHILDREN = PARENT + "/children";
    public static final String CHILD = PARENT + "/child";
    public static final String PARENT_RATE_COURSE = PARENT + "/rate_course";
    
    //For sections
    public static final String SECTION = TEACHER + "/section";

    //classrooms
    public static final String SHOW_CLASSROOM = "/show_classroom";

}
