# server
SERVER = {
    "local": "http://localhost:8080",
    "web": "https://graduation-server.herokuapp.com"
}

# base
TEACHER = "/teacher"
STUDENT = "/student"
PARENT = "/parent"
COURSE = "/course"
AUTH = "/auth"
CLASSROOM = "/classroom"
ADMIN = "/admin"
QUIZ = "/quiz"
FILE = "/file"
QUESTION = "/question"
ANSWER = "/answer"
CATEGORIES = "/categories"
LECTURE = "/lecture"

# Authentication
LOGIN = AUTH + "/login"
REGISTER = AUTH + "/register"
LOGOUT = AUTH + "/logout"


# General user
PROFILE = "/profile"

# Admin
REQUESTS = ADMIN + "/requests"
APPROVE_TEACHING_REQUEST = ADMIN + "/approve_teaching"
ALL_CATEGORIES = ADMIN + "/categories"
APPROVE_CATEGORY = ADMIN + "/approve_category"

# For courses
NEW_COURSES = "/new_courses"
HOT_COURSES = "/hot_courses"
ALL_COURSES = "/all_courses"
TOP_RATED_COURSES = "/top_rated_courses"
CATEGORY_COURSES = "/category_courses"
SAVED_COURSES = "/saved_courses"
COURSE_STUDENTS = TEACHER + COURSE + "/students"

# Teacher
TEACHER_CLASSROOMS = TEACHER + "/classrooms"
TEACHER_CLASSROOM = TEACHER + "/classroom"
TEACHER_COURSES = TEACHER + "/courses"
TEACHER_CLASSROOM_COURSES = TEACHER + "/classroom_courses"
REQUEST_TEACHING = TEACHER + "/request_teaching"
REQUEST_CATEGORY = TEACHER + "/request_category"

# Student
JOIN_CLASSROOM = STUDENT + "/join_classroom"
ENROLL_COURSE = STUDENT + "/enroll_course"
STUDENT_CLASSROOMS = STUDENT + "/classrooms"
STUDENT_COURSES = STUDENT + "/courses"
STUDENT_COURSE = STUDENT + "/course"
STUDENT_RATE_COURSE = STUDENT + "/rate_course"
STUDENT_START_QUIZ = STUDENT + QUIZ + "/start"
STUDENT_SUBMIT_QUIZ = STUDENT + QUIZ + "/submit"
STUDENT_QUIZ_SCORE = STUDENT + QUIZ + "/score"
STUDENT_QUIZ_INFO = STUDENT + QUIZ + "/info"

# Parent
ADD_CHILD = PARENT + "/add_child"
JOIN_CHILD_IN_CLASSROOM = PARENT + "/join_child_classroom"
ENROLL_CHILD_IN_COURSE = PARENT + "/enroll_child_course"
CHILDREN = PARENT + "/children"
CHILD = PARENT + "/child"
PARENT_RATE_COURSE = PARENT + "/rate_course"

# For sections
SECTION = TEACHER + "/section"
TEACHER_QUIZ = TEACHER + QUIZ
TEACHER_MEDIA = TEACHER + FILE
TEACHER_QUESTIONS = TEACHER + QUIZ + "/no_questions"

# For quiz
QUIZ_QUESTION = TEACHER + QUESTION

# For Question
QUESTION_ANSWER = TEACHER + ANSWER
