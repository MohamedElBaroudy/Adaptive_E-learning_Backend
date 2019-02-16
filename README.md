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




### general APIs
#### Get new courses (max 20 course if we have more and all courses if less) (GET method)
>http://localhost:8080/new_courses

#### Get hot courses (max 20 course if we have more and all courses if less) (GET method)
>http://localhost:8080/hot_courses

#### Get top rated courses (max 20 course if we have more and all courses if less) (GET method)
>http://localhost:8080/top_rated_courses

#### Get courses by category (GET method)
>http://localhost:8080/category_courses?category=it_and_software



### Course APIs
#### Get Course by id (GET method)
>http://localhost:8080/course?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&course_id=1



### Classroom APIs
#### Get classroom by id (GET method)
>http://localhost:8080/classroom?token=eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNTQ5MjQ0MjUyLCJleHAiOjE1NDk4NDkwNTJ9.LYczc5lPxhEcdZ_dYJrQwuYcb7iPBTduB7VuDtgOLpFIRMyr2bs1Ie1_FcaFwu_QIKvFTMEJ4FK9ZZJLC_LvBQ&classroom_id=1




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
#### Create classroom (POST method)
>http://localhost:8080/teacher/classrooms?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&classroom_name=CSE19 ComputerNetworks

#### Create course (POST method)
>http://localhost:8080/teacher/courses?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&title=java&detailed_title=spring&description=itsjavaspringcourse&category=it_and_software&level=1

#### Update course (optional parameters)(PUT method)
>http://localhost:8080/teacher/courses?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&course_id=22&title=test_update&detailed_title=test_test_update&description=blablablablabla&category=software&level=2

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
