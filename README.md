# Adaptive_E-learning_Backend
Graduation project about adaptive e-learning. This repo is the backend of the app.
server : http://graduation-server.herokuapp.com/


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
>Health
fitness
>Physics
>Chemistry
>Economics
>Communication
>Architecture
>Biology


## Admin user
>email      admin@gmail.com
>username      Admin
>password      123456789123456789




## API Samples

>params template is:
key    value
>response template is:
body    status_code    hint_if_any

### user APIs
==
#### Register example (POST)
>pre : none

==
>https://graduation-server.herokuapp.com/auth/register?

##### params:

first_name    mohamed

last_name    adel

email    user1@gmail.com

username    keloi1

password    12345678

gender    1

date_of_birth    2006-07-27

##### response:

>Email is used"    409

>Username is used"    409
>fancyUser    200		

#### Login example (GET)
>pre : must have an account

==
>https://graduation-server.herokuapp.com/auth/login?

##### params:

email    user1@gmail.com

password    12345678

or

==
>https://graduation-server.herokuapp.com/auth/login?

##### params:

username    keloi

password    12345678

##### response:

>you must enter email or user name"	401

>user is not found"    404
>token    200    if_you_already_logged_in or new_log_in


#### Logout example (GET)
==
>https://graduation-server.herokuapp.com/auth/logout?

##### params:

token    eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaW....vc8kPCvCrCrk9LJEw

##### response:

>user isn't logged in"	401

>session expired"	401
>none    204

#### Retrieve user data (GET)
==
>https://graduation-server.herokuapp.com/profile?

##### params:

token    eyJhbGciOiJIUzUxMiJ9.eyJzd....WO-OSbvxAlG9n1li-pGnA

##### response:

>user isn't logged in"	401

>session expired"	401
>fancyuser    200

#### Get saved courses (GET)
==
>https://graduation-server.herokuapp.com/saved_courses?

##### params:

token    eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

##### response:

>user isn't logged in"	401

>session expired"	401
>courses    200

#### Add course to saved courses by course_id (POST)
==
>https://graduation-server.herokuapp.com/saved_courses?

##### params:

token    eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

course_id    26

##### response:

>user isn't logged in"	401

>session expired"	401

> course with this id is not found "    404

>course publisher can't save his courses"    403

>Already Saved "    403
>none    204

#### Remove course from saved courses bt course's id (DELETE)
==
>https://graduation-server.herokuapp.com/saved_courses?

##### params:

token    eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

course_id    26

##### response:

>user isn't logged in"	401

>session expired"	401

> course with this id is not found "    404

>course publisher can't save his courses"    403

>Already removed"    403
>none    204

#### Add profile picture(POST)
==
>https://graduation-server.herokuapp.com/profilePic?

##### params:

token    eyJhbGciOiJIUzUxMiJ9....QIKvFTMEJ4FK9ZZJLC_LvBQ

### Admin APIs
==
#### Retrieve teaching request (GET)
==
>https://graduation-server.herokuapp.com/admin/requests?

##### params:

token    eyJhbGciOiJIUzUxMiJ9....QIKvFTMEJ4FK9ZZJLC_LvBQ

##### response:

>user isn't logged in"	401

>session expired"	401

>Only Admin can show requests"    403
>fancyrequests    200

#### Approve teaching request (PUT)
==
>https://graduation-server.herokuapp.com/admin/approve_teaching?

##### params:

token    eyJhbGciOiJIUzUxMiJ9....QIKvFTMEJ4FK9ZZJLC_LvBQ

user_id    2

##### response:

>user isn't logged in"	401

>session expired"	401

>Only Admin can approve requests"    403

>Not found request"    404

>Already approved"    304
>none    204

#### Retrieve All categories (GET)
==
>https://graduation-server.herokuapp.com/admin/categories?

##### params:

token    eyJhbGciOiJIUzUxMiJ9....QIKvFTMEJ4FK9ZZJLC_LvBQ

##### response:

>user isn't logged in"	401

>session expired"	401

>Only Admin can show all categories"    403
>fancycategories    200

#### Add new category (POST)
==
>https://graduation-server.herokuapp.com/admin/categories?

##### params:

token    eyJhbGciOiJIUzUxMiJ9....QIKvFTMEJ4FK9ZZJLC_LvBQ

category    Test Category

##### response:

>user isn't logged in"	401

>session expired"	401

>Only Admin can add category"    403

>Already found category"    400
>none    201

#### Approve category by it's id
==
>https://graduation-server.herokuapp.com/admin/approve_category?

##### params:

token    eyJhbGciOiJIUzUxMiJ9....QIKvFTMEJ4FK9ZZJLC_LvBQ

category_id    21

##### response:

>user isn't logged in"	401

>session expired"	401

>Only Admin can approve category"    403

>not found category"    400

>Already approved"    304
>none    204



### general APIs
==
#### Get new courses (max 20 course if we have more and all courses if less) (GET)
==
>https://graduation-server.herokuapp.com/new_courses

#### Get hot courses (max 20 course if we have more and all courses if less) (GET)
==
>https://graduation-server.herokuapp.com/hot_courses

#### Get top rated courses (max 20 course if we have more and all courses if less) (GET)
==
>https://graduation-server.herokuapp.com/top_rated_courses

#### Get courses by category_id (GET)
==
>https://graduation-server.herokuapp.com/category_courses?

##### params:

category_id    3

##### response:

>Not found Category"    404

>Not approved Category yet"    400
>fancyCourses    200

#### Retrieve approved categories (GET)
==
>https://graduation-server.herokuapp.com/categories



### Course APIs
==
#### Get Course by id (GET)
==
>https://graduation-server.herokuapp.com/course?

##### params:

token    eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

course_id    1

##### response:

>user isn't logged in"    401

>session expired"    401

>course with this id is not found "    404
>fancyCourse    200

#### Add picture to course(POST)
==
>https://graduation-server.herokuapp.com/coursePic?

##### params:

token    eyJhbGciOi....B7VuDtgOLpFIRMyr2bs1Ie1_FcaFwu_QIKvFTMEJ4FK9ZZJLC_LvBQ

course_id    1



### Classroom APIs
==
#### Get classroom by id (GET)
==
>https://graduation-server.herokuapp.com/classroom?

##### params:

token    eyJhbGciOi....B7VuDtgOLpFIRMyr2bs1Ie1_FcaFwu_QIKvFTMEJ4FK9ZZJLC_LvBQ

classroom_id    1

##### response:

>user isn't logged in"    401

>session expired"    401

>classroom with this id is not found"    404

>you are not allowed to see this classroom"    403
>fancyClassroom    200

#### Add picture to classroom(POST)
==
>https://graduation-server.herokuapp.com/classroomPic?

##### params:

token    eyJhbGciOi....B7VuDtgOLpFIRMyr2bs1Ie1_FcaFwu_QIKvFTMEJ4FK9ZZJLC_LvBQ

classroom_id    1


### parent APIs
==
#### Add child for a parent (POST)
==
>https://graduation-server.herokuapp.com/parent/add_child?

##### params:

token    eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

first_name    nemo

date_of_birth    2001-02-05

email    child21@hotmail.com

password    12345678

username    keloi21

gender    1

grade    1'st grade

##### response:

>user isn't logged in"    401

>session expired"    401

>Username, Email or both of them are in use"	409

>Child added before"	409
>none	201

#### Join child into classroom (POST)
==
>https://graduation-server.herokuapp.com/parent/join_child_classroom?

##### params:

token    eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

first_name    nemo

passcode    Jb6xHKK

##### response:

>user isn't logged in"    401

>session expired"    401

>Child Is Not Found"	404

>Classroom Is Not Found"	404

>this child already enrolled to this classroom"	403
>none	201

#### Enroll Child into course (POST)
==
>https://graduation-server.herokuapp.com/parent/enroll_child_course?

##### params:

token    eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

first_name    nemo

course_id    1

##### response:

>user isn't logged in"    401

>session expired"    401

>Child Is Not Found"	404

>course Is Not Found"	404

>Already Enrolled"	403
>none	200

#### Retrieve children (GET)
==
>https://graduation-server.herokuapp.com/parent/children?

##### params:

token    eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

##### response:

>user isn't logged in"    401

>session expired"    401
>User List	200

#### Retrieve child (GET)
==
>https://graduation-server.herokuapp.com/parent/child?

##### params:

token    eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

user_id    4

##### response:

>user isn't logged in"    401

>session expired"    401

>Child is not found"	404

>User is not your child"	403
>user	200

#### Rate course for parent (POST)
==
>https://graduation-server.herokuapp.com/parent/rate_course?

##### params:

token    eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

course_id    1

first_name    nemo

rate    4

##### response:

>user isn't logged in"    401

>session expired"    401

>course with this id is not found"    404

>user is child it's not allowed"    403

>course publisher can't rate his courses"    403

>child with this name is not found"	404

>Your child isn't enrolled in this course"    403

>User cannot rate again"    403
>none	 201



### teacher APIs
==
#### Request teaching (POST)
==
>https://graduation-server.herokuapp.com/teacher/request_teaching?

##### params:

token    eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

##### response:

>user isn't logged in"    401

>session expired"    401

>user is child it's not allowed "    403

>user is already a teacher"    304

>user is already requested and not approved yet"    304
>none    204

#### Check request (GET)
==
>https://graduation-server.herokuapp.com/teacher/request_teaching?

##### params:

token    eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

##### response:

>user isn't logged in"    401

>session expired"    401

>request already sent and not approved yet"    200

>request approved"    200

>not found request for this user"    404

#### Request category (POST)
==
>https://graduation-server.herokuapp.com/teacher/request_category?

##### params:

token    eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

category    Test Category 2

##### response:

>user isn't logged in"    401

>session expired"    401

>user is not a teacher it's not allowed"	 403

>Already found and approved"	 304

>Already requested for approval"    304
>none    201

#### Create classroom (POST)
==
>https://graduation-server.herokuapp.com/teacher/classrooms?

##### params:

token    eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

classroom_name    CSE19 ComputerNetworks

##### response:

>user isn't logged in"    401

>session expired"    401

>user is not a teacher yet please make a request to be teacher"    403
>classroom pass    201

#### Create course (POST)
==
>https://graduation-server.herokuapp.com/teacher/courses?

##### params:

token    eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

title    java

detailed_title    spring

description    itsjavaspringcourse

category    it_and_software

level    1

##### response:

>user isn't logged in"    401

>session expired"    401

>user is not a teacher yet please make a request to be teacher"    403

>Category is not found"    404

>Category is not approved yet"    400
>none    201

#### Update course (PUT)
==
>https://graduation-server.herokuapp.com/teacher/courses?

##### params:

token    eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

course_id    22

title    test_update	(optional)

detailed_title    test_test_update	(optional)

description    blablablablabla	(optional)

category    software	(optional)

level    2	(optional)

##### response:

>user isn't logged in"    401

>session expired"    401

>course is not found "    404

>Not Allowed you are not a teacher or this is not your course to update"    403

>Category is not found"    404

>Category is not approved yet"    400
>none    201

#### Retrieve course students by course's id (GET)
==
>https://graduation-server.herokuapp.com/teacher/course/students?

##### params:

token    eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

course_id    2

##### response:

>user isn't logged in"    401

>session expired"    401

>course with this id is not found "    404

>This is not your course to show students"    403
>User List    200


#### Retrieve classrooms for teacher (GET)
==
>https://graduation-server.herokuapp.com/teacher/classrooms?

##### params:

token    eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

##### response:

>user isn't logged in"    401

>session expired"    401
>Classroom List    200

#### Retrieve courses for teacher (GET)
==
>https://graduation-server.herokuapp.com/teacher/courses?

##### params:

token    eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

##### response:

>user isn't logged in"    401

>session expired"    401
>Course List    200

#### Update the classroom passcode by it's Id (PUT)
==
>https://graduation-server.herokuapp.com/teacher/classroom?

##### params:

token    eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

classroom_id    1

##### response:

>user isn't logged in"    401

>session expired"    401

>classroom is not found"    404

>Not Allowed you are not a teacher or this is not your classroom to update"    403
>classroom pass    201

#### Delete classroom by it's Id (DELETE)
==
>https://graduation-server.herokuapp.com/teacher/classroom?

##### params:

token    eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

classroom_id    2

##### response:

>user isn't logged in"    401

>session expired"    401

>classroom is not found"    404

>Not Allowed you are not a teacher or this is not your classroom to delete"    403
>none    204

#### Create course in a classroom (POST)
==
>https://graduation-server.herokuapp.com/teacher/classroom_courses?

##### params:

token    eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

classroom_id    1

title    c++

detailed_title    datastructure

description    it's datastructure and algorithm course

category    it_and_software

level    2

##### response:

>user isn't logged in"    401

>session expired"    401

>classroom is not found"    404

>user is not the creator of this classroom"    403

>Category is not found"    404

>Category is not approved yet"    400
>none    201

#### Delete course by it's Id (DELETE)
==
>https://graduation-server.herokuapp.com/teacher/courses?

##### params:

token    eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

course_id    2

##### response:

>user isn't logged in"    401

>session expired"    401

>course is not found"    404

>Not Allowed you are not a teacher or this is not your course to delete"    403

>deleted"    204

#### Create section in a course (POST)
==
>https://graduation-server.herokuapp.com/teacher/section?

##### params:

token    eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

course_id    1

section_title    Introduction

##### response:

>user isn't logged in"    401

>session expired"    401

>course is not found"    404

>Not Allowed you are not a teacher or this is not your course to add section in"    403
>none    201

#### Update the section info by it's id (PUT)
==
>https://graduation-server.herokuapp.com/teacher/section?

##### params:

token    eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

section_id    1

section_title    COURSE_INTRO

##### response:

>user isn't logged in"    401

>session expired"    401

>Section is not found"    404

>Not Allowed you are not a teacher or this is not your section to update"    403
>none    201

#### Delete section by it's id (DELETE)
==
>https://graduation-server.herokuapp.com/teacher/section?

##### params:

token    eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

section_id    2

##### response:

>user isn't logged in"    401

>session expired"    401

>Section is not found"    404

>Not Allowed you are not a teacher or this is not your section to delete"    403

>section deleted"    204

#### Retrieve section by it's id (GET)
==
>https://graduation-server.herokuapp.com/teacher/section?

##### params:

token    eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

section_id    2

##### response:

>user isn't logged in"    401

>session expired"    401

>Section is not found"    404

>Not Allowed you are not a teacher or this is not your section to show"    403
>section    200

#### Create quiz by section id (POST)
==
>https://graduation-server.herokuapp.com/teacher/quiz?

##### params:

token    eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

section_id    1

quiz_title    Introduction

quiz_instructions    don't forget your calculator

quiz_time    30

##### response:

>user isn't logged in"    401

>session expired"    401

>Section is not found"    404

>Not Allowed you are not a teacher or this is not your section to add quiz in"    403
>none    200

#### Set number of selected questions (POST)
==
>https://graduation-server.herokuapp.com/teacher/quiz/no_questions?

##### params:

token    eyJhbGcNTUyNT2ODJ9.PlO-.....tJD_VBAGljcpDDvTFSKlWz9Es2NTD3H0e7Yk_Bj0w

quiz_id    1

no_of_questions    8

##### response:

>user isn't logged in"    401

>session expired"    401

>Not found quiz"    404

>Not Allowed you are not a teacher or this is not your quiz to set no of questions"    403

>not allowed to set no of selected questions by 0"    403

>number of selected questions more than the current number of questions"    403
>none    202

#### Update the quiz info by it's id (optional parameters)(PUT)
==
>https://graduation-server.herokuapp.com/teacher/quiz?

##### params:

token    eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

quiz_id    1

quiz_title    Introduction2

quiz_instructions    don't forget your calculator2

quiz_time    45


##### response:

>user isn't logged in"    401

>session expired"    401

>Not found quiz"    404

>Not Allowed you are not a teacher or this is not your quiz to update"    403

>time is less than 5 or more than 60"   400
>none    202

#### Delete quiz by it's id (DELETE)
==
>https://graduation-server.herokuapp.com/teacher/quiz?

##### params:

token    eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

quiz_id    2

##### response:

>user isn't logged in"    401

>session expired"    401

>Not found quiz"    404

>Not Allowed you are not a teacher or this is not your quiz to delete"    403
>none    202

#### Retrieve quiz by it's id (GET)
==
>https://graduation-server.herokuapp.com/quiz?

##### params:

token    eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

quiz_id    1

##### response:

>user isn't logged in"    401

>session expired"    401

>Not found quiz"    404

>Not Allowed you are not the creator of this quiz"    403
>Quiz    202

#### Add question to quiz by quiz's id (POST)
==
>https://graduation-server.herokuapp.com/teacher/question?

##### params:

token    eyJhbGciOiJIUzUx....SKEs2NTD3H0e7Yk_Bj0w

quiz_id    1

question_body    question1

is_multiple_choice    false

question_mark    1

question_level    1

question_reference    go to lecture 2

> easy level     1 , medium level    2 , hard level    3

##### response:

>user isn't logged in"    401

>session expired"    401

>Not found quiz"    404

>Not Allowed you are not the creator of this quiz"    403

>question level is not valid"   400
>none 201

#### Retrieve question by it's id (GET)
==
>https://graduation-server.herokuapp.com/question?

##### params:

token    eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

question_id    1

##### response:

>user isn't logged in"    401

>session expired"    401

>Not found question"    404

>Not Allowed you are not the creator of this quiz or a student of this course"    403
>Question    200

#### Update question by it's id (PUT)
==
>https://graduation-server.herokuapp.com/teacher/question?

##### params:

token    eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

question_id    1

question_body    question1

is_multiple_choice    false

question_mark    3

##### response:

>user isn't logged in"    401

>session expired"    401

>Not found question"    404

>Not Allowed you are not the creator of this quiz to update it's content"    403
>none    200

#### Delete question by it's id (DELETE)
==
>https://graduation-server.herokuapp.com/teacher/question?

##### params:

token    eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

question_id    3

##### response:

>user isn't logged in"    401

>session expired"    401

>Not found question"    404

>Not Allowed you are not the creator of this quiz to delete it's content"    403
>none    202

#### Add answer to question by question's id (POST)
==
>https://graduation-server.herokuapp.com/teacher/answer?

##### params:

token    eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

question_id    1

answer_body    answer 1a

is_correct    false

##### response:

>user isn't logged in"    401

>session expired"    401

>Not found question"    404

>Not Allowed you are not the creator of this quiz"    403

>Cannot have more than 1 correct answer update your question to multiple choice first"    400
>none    201

#### Update answer by it's id (PUT)
==
>https://graduation-server.herokuapp.com/teacher/answer?

##### params:

token    eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

answer_id    2

answer_body    answer 1b

is_correct    true

##### response:

>user isn't logged in"    401

>session expired"    401

>Not found answer"    404

>Not Allowed you are not the creator of this quiz to update it's content"    403

>Cannot have more than 1 correct answer update your question to multiple choice first"    400

>Cannot make the only correct answer incorrect. please add a correct answer first!"    400
>none    200

#### Delete answer by it's id (DELETE)
==
>https://graduation-server.herokuapp.com/teacher/answer?

##### params:

token    eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

answer_id    2

##### response:

>user isn't logged in"    401

>session expired"    401

>Not found answer"    404

>Not Allowed you are not the creator of this quiz to delete it's content"    403

>Cannot have more than 1 correct answer update your question to multiple choice first"    400

>Cannot remove the only correct answer. please add a correct answer first!"    400
>none    202

#### upload lecture content (POST)
==
>https://graduation-server.herokuapp.com/teacher/file?

##### params:

token    eyJhbGciOi....B7VuDtgOLpFIRMyr2bs1Ie1_FcaFwu_QIKvFTMEJ4FK9ZZJLC_LvBQ

section_id    1

#### get lecture by file id (GET)
==
>https://graduation-server.herokuapp.com/teacher/file?

##### params:

token    eyJhbGciOi....B7VuDtgOLpFIRMyr2bs1Ie1_FcaFwu_QIKvFTMEJ4FK9ZZJLC_LvBQ

file_id    4

#### delete lectue content (DELETE)
==
>https://graduation-server.herokuapp.com/teacher/file?

##### params:

token    eyJhbGciOi....B7VuDtgOLpFIRMyr2bs1Ie1_FcaFwu_QIKvFTMEJ4FK9ZZJLC_LvBQ

file_id    4

#### retrieve lecture by it's id (GET)
==
>https://graduation-server.herokuapp.com/lecture?

##### params:

token    eyJhbGciOi....B7VuDtgOLpFIRMyr2bs1Ie1_FcaFwu_QIKvFTMEJ4FK9ZZJLC_LvBQ

lecture_id    1

##### response:

>user isn't logged in"    401

>session expired"    401

>lecture is not found"    404

>Not Allowed you are not a teacher or student in this course"    403
>lecture    201


### student APIs
==
#### Join student in classroom with it's Passcode (POST)
==
>https://graduation-server.herokuapp.com/student/join_classroom?

##### params:

token    eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

passcode    Jb6xHKK

##### response:

>user isn't logged in"    401

>session expired"    401

>your parent has to join you"    403

>Classroom Is Not Found"    404

>Already Joined"    403

>classroom creator can't join to his classroom"    403
>none    200

#### Retrieve classrooms for students (GET)
==
>https://graduation-server.herokuapp.com/student/classrooms?

##### params:

token    eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

##### response:

>user isn't logged in"    401

>session expired"    401
>Classroom List    200

#### Enroll Student into course (POST)
==
>https://graduation-server.herokuapp.com/student/enroll_course?

##### params:

token    eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

course_id    1

##### response:

>user isn't logged in"    401

>session expired"    401

>your parent has to enroll you"    403

>course is not found"    404

>Already Enrolled"    403

>course publisher can't enroll in his courses"    403
>none    200

#### Retrieve courses for students (GET)
==
>https://graduation-server.herokuapp.com/student/courses?

##### params:

token    eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

##### response:

>user isn't logged in"    401

>session expired"    401
>Course List    200

#### Rate course for student (post)
==
>https://graduation-server.herokuapp.com/student/rate_course?

##### params:

token    eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

course_id    1

rate    4

##### response:

>user isn't logged in"    401

>session expired"    401

>User is child it's not allowed"    403

>course is not found"    404

>Course publisher can't rate his courses"    403

>User can't rate again"    403
>none    201

#### Retrieve lecture by it's id (GET)
==
>https://graduation-server.herokuapp.com/lecture?

##### params:

token    eyJhbGciOi....B7VuDtgOLpFIRMyr2bs1Ie1_FcaFwu_QIKvFTMEJ4FK9ZZJLC_LvBQ

lecture_id    1

##### response:

>user isn't logged in"    401

>session expired"    401

>lecture is not found"    404

>Not Allowed you are not a teacher or student in this course"    403
>lecture    201

#### Retrieve random quiz by it's id (GET)
https://graduation-server.herokuapp.com/student/quiz/generate?

##### params:

token    eyJhbGciOiJI..u4LSW2UyHvgyBoZInecZvrimKebVxo33y5A

quiz_id    1-

#### start quiz by it's id (POST)
==
>https://graduation-server.herokuapp.com/student/quiz/start?

##### params:

token    eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

quiz_id    3

#### Submit quiz by it's id and json of questions and student answers (POST)
==
>https://graduation-server.herokuapp.com/student/quiz/submit?

##### params:

token    eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

quiz_id    3

##### response:

>user isn't logged in"    401

>session expired"    401

>Quiz is not found"    404

>You are not enrolled in this course"    403

>You have not started this quiz yet"    403

>You have exceeded the time limit of the quiz"    403
>none    200

##### body :-
{
	"questions":[
	{
		"question_id": 1,
		"answers_ids": [3]
	},
	{
		"question_id": 2,
		"answers_ids": [6,9,7]
	}
	] 
}

#### Get quiz for student to know if he passed it and some other info by quiz id (GET)
>this is to get your score in the quiz. must take quiz first once at least

==
>https://graduation-server.herokuapp.com/student/quiz?

##### params:

token    eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

quiz_id    3

##### response:

>user isn't logged in"    401

>session expired"    401

>Quiz is not found"    404

>You are not enrolled in this course"    403

>Quiz has not taken yet"    404
>student with quiz info(mark,submitdate,..)    200
