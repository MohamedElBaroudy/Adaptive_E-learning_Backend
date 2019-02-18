# Adaptive_E-learning_Backend
Graduation project about adaptive e-learning. This repo is the backend of the app.


## Codes
### Error codes
#### 401 Unauthorized
> user isn't logged in.
#### 409 Conflict
>unique name is token like username, email, classroom name, etc.

#### 403 Forbidden
>this functionallity is not allowed to this user.

#### 400 Bad Request
>the data stream sent by the client to the server didn't follow the rules.

#### 304 Not Modified
>redirect without doing the request.

#### 404 Not Found

### Success codes
#### 204 No Content
>success without showing content. in delete functions.

#### 201 Created
>post or put functions

#### 200 Ok
>get functions



## Categories
>Development
>IT and Software
>Personal Development
>Design
>Marketing
>Music
>Math
>Science
>Social Science
>Language
>Engineering
>Business
>History
>Health&fitness
>Physics
>Chemistry
>Economics
>Communication
>Architecture
>Biology


## Admin user
>email = admin@gmail.com
>username = Admin
>password = 123456789123456789


## API Samples

### user APIs
#### Register example (POST method)
>http://localhost:8080/auth/register?first_name=mohamed&last_name=adel&email=user1@gmail.com&username=keloi1&password=12345678&gender=1&date_of_birth=2006-07-27

#### Login example (GET method)
>http://localhost:8080/auth/login?email=user1@gmail.com&password=12345678
or
>http://localhost:8080/auth/login?username=keloi&password=12345678

#### Logout example (GET method)
>http://localhost:8080/auth/logout?token=eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaW....vc8kPCvCrCrk9LJEw

#### Retrieve user data (GET method)
>http://localhost:8080/profile?token=eyJhbGciOiJIUzUxMiJ9.eyJzd....WO-OSbvxAlG9n1li-pGnA

#### Get saved courses (GET method)
>http://localhost:8080/saved_courses?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

#### Add course to saved courses by course_id (POST method)
>http://localhost:8080/saved_courses?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&course_id=26

#### Remove course from saved courses bt course's id (DELETE method)
>http://localhost:8080/saved_courses?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&course_id=26

#### Add profile picture(POST method)
>http://localhost:8080/profilePic?token=eyJhbGciOiJIUzUxMiJ9....QIKvFTMEJ4FK9ZZJLC_LvBQ



### Admin APIs
#### Retrieve teaching request (GET method)
>http://localhost:8080/admin/requests?token=eyJhbGciOiJIUzUxMiJ9....QIKvFTMEJ4FK9ZZJLC_LvBQ

#### Approve teaching request (PUT method)
>http://localhost:8080/admin/approve_teaching?token=eyJhbGciOiJIUzUxMiJ9....QIKvFTMEJ4FK9ZZJLC_LvBQ&user_id=2

#### Retrieve All categories (GET method)
>http://localhost:8080/admin/categories?token=eyJhbGciOiJIUzUxMiJ9....QIKvFTMEJ4FK9ZZJLC_LvBQ

#### Add new category (POST method)
>http://localhost:8080/admin/categories?token=eyJhbGciOiJIUzUxMiJ9....QIKvFTMEJ4FK9ZZJLC_LvBQ&category=Test Category

#### Approve category by it's id
>http://localhost:8080/admin/approve_category?token=eyJhbGciOiJIUzUxMiJ9....QIKvFTMEJ4FK9ZZJLC_LvBQ&category_id=21



### General APIs
#### Get new courses (max 20 course if we have more and all courses if less) (GET method)
>http://localhost:8080/new_courses

#### Get hot courses (max 20 course if we have more and all courses if less) (GET method)
>http://localhost:8080/hot_courses

#### Get top rated courses (max 20 course if we have more and all courses if less) (GET method)
>http://localhost:8080/top_rated_courses

#### Get courses by category_id (GET method)
>http://localhost:8080/category_courses?category_id=3

#### Retrieve approved categories
>http://localhost:8080/categories



### Course APIs
#### Get Course by id (GET method)
>http://localhost:8080/course?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&course_id=1

#### Add picture to course(POST method)
>http://localhost:8080/coursePic?token=eyJhbGciOi....B7VuDtgOLpFIRMyr2bs1Ie1_FcaFwu_QIKvFTMEJ4FK9ZZJLC_LvBQ&course_id=1



### Classroom APIs
#### Get classroom by id (GET method)
>http://localhost:8080/classroom?token=eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNTQ5MjQ0MjUyLCJleHAiOjE1NDk4NDkwNTJ9.LYczc5lPxhEcdZ_dYJrQwuYcb7iPBTduB7VuDtgOLpFIRMyr2bs1Ie1_FcaFwu_QIKvFTMEJ4FK9ZZJLC_LvBQ&classroom_id=1

#### Add picture to classroom(POST method)
>http://localhost:8080/classroomPic?token=eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiw.....JrQwuYcb7iPBTduB7VuDtgOLpFIRMyr2bs1Ie1_FcaFwu_QIKvFTMEJ4FK9ZZJLC_LvBQ&classroom_id=1


### parent APIs
#### Add child for a parent (POST method)
>http://localhost:8080/parent/add_child?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&first_name=habebbaba&date_of_birth=2001-02-05&email=child21@hotmail.com&password=12345678&username=keloi21&gender=1&grade=1'st grade

#### Join child into classroom (POST method)
>http://localhost:8080/parent/join_child_classroom?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&first_name=habebbaba&passcode=Jb6xHKK

#### Enroll Child into course (POST method)
>http://localhost:8080/parent/enroll_child_course?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&first_name=Habebbaba&course_id=1

#### Retrieve children (GET method)
>http://localhost:8080/parent/children?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

#### Retrieve child (GET method)
>http://localhost:8080/parent/child?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&user_id=4

#### Rate course for parent (POST method)
>http://localhost:8080/parent/rate_course?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&course_id=1&first_name=Habebbaba&rate=4



### teacher APIs
#### Request teaching (POST method)
>http://localhost:8080/teacher/request_teaching?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

#### Request category (POST method)
>http://localhost:8080/teacher/request_category?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&category=Test Category 2

#### Create classroom (POST method)
>http://localhost:8080/teacher/classrooms?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&classroom_name=CSE19 ComputerNetworks

#### Create course (POST method)
>http://localhost:8080/teacher/courses?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&title=java&detailed_title=spring&description=itsjavaspringcourse&category=it_and_software&level=1

#### Update course (optional parameters)(PUT method)
>http://localhost:8080/teacher/courses?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&course_id=22&title=test_update&detailed_title=test_test_update&description=blablablablabla&category=software&level=2

#### Retrieve course students by course's id (GET method)
>http://localhost:8080/teacher/course/students?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&course_id=2

#### Retrieve classrooms for teacher (GET method)
>http://localhost:8080/teacher/classrooms?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

#### Retrieve courses for teacher (GET method)
>http://localhost:8080/teacher/courses?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

#### Update the classroom passcode by it's Id (PUT method)
>http://localhost:8080/teacher/classroom?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&classroom_id=1

#### Delete classroom by it's Id (DELETE method)
>http://localhost:8080/teacher/classroom?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&classroom_id=2

#### Create course in a classroom (POST method)
>http://localhost:8080/teacher/classroom_courses?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&classroom_id=1&title=c++&detailed_title=datastructure&description=it's datastructure and algorithm course&category=it_and_software&level=2

#### Delete course by it's Id (DELETE method)
>http://localhost:8080/teacher/courses?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&course_id=2

#### Create section in a course (POST method)
>http://localhost:8080/teacher/section?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&course_id=1&section_title=Introduction

#### Update the section info by it's id (PUT method)
>http://localhost:8080/teacher/section?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&section_id=1&section_title=COURSE_INTRO

#### Delete section by it's id (DELETE method)
>http://localhost:8080/teacher/section?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&section_id=2

#### Retrieve section by it's id (GET method)
>http://localhost:8080/teacher/section?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&section_id=2

#### Create quiz by section id (POST method)
>http://localhost:8080/teacher/quiz?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&section_id=1&quiz_title=Introduction&quiz_instructions=don't forget your calculator&quiz_time=30

#### Update the quiz info by it's id (optional parameters)(PUT method)
>http://localhost:8080/teacher/quiz?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&quiz_id=1&quiz_title=Introduction2&quiz_instructions=don't forget your calculator2&quiz_time=45

#### Delete quiz by it's id (DELETE method)
>http://localhost:8080/teacher/quiz?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&quiz_id=2

#### Retrieve quiz by it's id (GET method)
>http://localhost:8080/quiz?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&quiz_id=1

#### Add question to quiz by quiz's id (POST method)
>http://localhost:8080/teacher/question?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&quiz_id=1&question_body=question1&is_multiple_choice=false&question_mark=2

#### Retrieve question by it's id (GET method)
>http://localhost:8080/question?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&question_id=1

#### Update question by it's id (PUT method)
>http://localhost:8080/teacher/question?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&question_id=1&question_body=question1&is_multiple_choice=false&question_mark=3

#### Delete question by it's id (DELETE method)
>http://localhost:8080/teacher/question?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&question_id=3

#### Add answer to question by question's id (POST method)
>http://localhost:8080/teacher/answer?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&question_id=1&answer_body=answer 1a&is_correct=false

#### Update answer by it's id (PUT method)
>http://localhost:8080/teacher/answer?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&answer_id=2&answer_body=answer 1b&is_correct=true

#### Delete answer by it's id (DELETE method)
>http://localhost:8080/teacher/answer?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&answer_id=2



### student APIs
#### Join student in classroom with it's Passcode (POST method)
>http://localhost:8080/student/join_classroom?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&passcode=Jb6xHKK

#### Retrieve classrooms for students (GET method)
>http://localhost:8080/student/classrooms?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

#### Enroll Student into course (POST method)
>http://localhost:8080/student/enroll_course?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&course_id=1

#### Retrieve courses for students (GET method)
>http://localhost:8080/student/courses?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

#### Rate course for student (post method)
>http://localhost:8080/student/rate_course?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&course_id=1&rate=4

#### Retrieve quiz by it's id (GET method)
>http://localhost:8080/quiz?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&quiz_id=1
