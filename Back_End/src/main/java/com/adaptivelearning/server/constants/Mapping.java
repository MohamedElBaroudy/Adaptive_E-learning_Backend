package com.adaptivelearning.server.constants;

public class Mapping {
    //Base
    private static final String TEACHER = "/teacher";
    private static final String STUDENT = "/student";
    private static final String PARENT = "/parent";
    public static final String COURSE = "/course";
    private static final String AUTH = "/auth";
    public static final String CLASSROOM = "/classroom";
    private static final String ADMIN = "/admin";
    public static final String QUIZ = "/quiz";
    public static final String FILE = "/file";
    public static final String QUESTION = "/question";
    public static final String ANSWER = "/answer";
    public static final String CATEGORIES = "/categories";
    public static final String LECTURE = "/lecture";

    //Authentication
    public static final String LOGIN = AUTH + "/login";
    public static final String REGISTER = AUTH + "/register";
    public static final String LOGOUT = AUTH + "/logout";

    //General user
    public static final String PROFILE = "/profile";

    //Admin
    public static final String REQUESTS = ADMIN + "/requests";
    public static final String APPROVE_TEACHING_REQUEST = ADMIN + "/approve_teaching";
    public static final String ALL_CATEGORIES = ADMIN + "/categories";
    public static final String APPROVE_CATEGORY = ADMIN + "/approve_category";

    //For courses
    public static final String NEW_COURSES = "/new_courses";
    public static final String HOT_COURSES = "/hot_courses";
    public static final String TOP_RATED_COURSES = "/top_rated_courses";
    public static final String CATEGORY_COURSES = "/category_courses";
    public static final String SAVED_COURSES = "/saved_courses";
    public static final String COURSE_STUDENTS = TEACHER + COURSE + "/students";


    //Teacher
    public static final String TEACHER_CLASSROOMS = TEACHER + "/classrooms";
    public static final String TEACHER_CLASSROOM = TEACHER + "/classroom";
    public static final String TEACHER_COURSES = TEACHER + "/courses";
    public static final String TEACHER_CLASSROOM_COURSES = TEACHER + "/classroom_courses";
    public static final String REQUEST_TEACHING = TEACHER + "/request_teaching";
    public static final String REQUEST_CATEGORY = TEACHER + "/request_category";


    //Student
    public static final String JOIN_CLASSROOM = STUDENT + "/join_classroom";
    public static final String ENROLL_COURSE = STUDENT + "/enroll_course";
    public static final String STUDENT_CLASSROOMS = STUDENT + "/classrooms";
    public static final String STUDENT_COURSES = STUDENT + "/courses";
    public static final String STUDENT_RATE_COURSE = STUDENT + "/rate_course";
    public static final String STUDENT_START_QUIZ = STUDENT + QUIZ + "/start";
    public static final String STUDENT_SUBMIT_QUIZ = STUDENT + QUIZ + "/submit";
    public static final String STUDENT_QUIZ = STUDENT + QUIZ;
    public static final String STUDENT_GENERATE_QUIZ = STUDENT + QUIZ+"/generate";



    //Parent
    public static final String ADD_CHILD = PARENT + "/add_child";
    public static final String JOIN_CHILD_IN_CLASSROOM = PARENT + "/join_child_classroom";
    public static final String ENROLL_CHILD_IN_COURSE = PARENT + "/enroll_child_course";
    public static final String CHILDREN = PARENT + "/children";
    public static final String CHILD = PARENT + "/child";
    public static final String PARENT_RATE_COURSE = PARENT + "/rate_course";
    
    //For sections
    public static final String SECTION = TEACHER + "/section";
    public static final String TEACHER_QUIZ = TEACHER + QUIZ;
    public static final String TEACHER_MEDIA = TEACHER + FILE;
    public static final String TEACHER_QUESTIONS = TEACHER + QUIZ + "/no_questions";

    //For quiz
    public static final String QUIZ_QUESTION = TEACHER + QUESTION;

    //For Question
    public static final String QUESTION_ANSWER = TEACHER + ANSWER;


}
