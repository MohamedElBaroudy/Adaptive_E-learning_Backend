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
>graduation-server.herokuapp.com/auth/register?first_name=mohamed&last_name=adel&email=user1@gmail.com&username=keloi1&password=12345678&gender=1&date_of_birth=2006-07-27

#### Login example (GET method)
>graduation-server.herokuapp.com/auth/login?email=user1@gmail.com&password=12345678
or
>graduation-server.herokuapp.com/auth/login?username=keloi&password=12345678

#### Logout example (GET method)
>graduation-server.herokuapp.com/auth/logout?token=eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaW....vc8kPCvCrCrk9LJEw

#### Retrieve user data (GET method)
>graduation-server.herokuapp.com/profile?token=eyJhbGciOiJIUzUxMiJ9.eyJzd....WO-OSbvxAlG9n1li-pGnA

#### Get saved courses (GET method)
>graduation-server.herokuapp.com/saved_courses?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

#### Add course to saved courses by course_id (POST method)
>graduation-server.herokuapp.com/saved_courses?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&course_id=26

#### Remove course from saved courses bt course's id (DELETE method)
>graduation-server.herokuapp.com/saved_courses?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&course_id=26

#### Add profile picture(POST method)
>graduation-server.herokuapp.com/profilePic?token=eyJhbGciOiJIUzUxMiJ9....QIKvFTMEJ4FK9ZZJLC_LvBQ

### Admin APIs
#### Retrieve teaching request (GET method)
>graduation-server.herokuapp.com/admin/requests?token=eyJhbGciOiJIUzUxMiJ9....QIKvFTMEJ4FK9ZZJLC_LvBQ

#### Approve teaching request (PUT method)
>graduation-server.herokuapp.com/admin/approve_teaching?token=eyJhbGciOiJIUzUxMiJ9....QIKvFTMEJ4FK9ZZJLC_LvBQ&user_id=2

#### Retrieve All categories (GET method)
>graduation-server.herokuapp.com/admin/categories?token=eyJhbGciOiJIUzUxMiJ9....QIKvFTMEJ4FK9ZZJLC_LvBQ

#### Add new category (POST method)
>graduation-server.herokuapp.com/admin/categories?token=eyJhbGciOiJIUzUxMiJ9....QIKvFTMEJ4FK9ZZJLC_LvBQ&category=Test Category

#### Approve category by it's id
>graduation-server.herokuapp.com/admin/approve_category?token=eyJhbGciOiJIUzUxMiJ9....QIKvFTMEJ4FK9ZZJLC_LvBQ&category_id=21



### general APIs
#### Get new courses (max 20 course if we have more and all courses if less) (GET method)
>graduation-server.herokuapp.com/new_courses

#### Get hot courses (max 20 course if we have more and all courses if less) (GET method)
>graduation-server.herokuapp.com/hot_courses

#### Get top rated courses (max 20 course if we have more and all courses if less) (GET method)
>graduation-server.herokuapp.com/top_rated_courses

#### Get all courses (ids and titles only) (GET method)
>graduation-server.herokuapp.com/all_courses

#### Get courses by category_id (GET method)
>graduation-server.herokuapp.com/category_courses?category_id=3

#### Retrieve approved categories
>graduation-server.herokuapp.com/categories

#### Get courses by category (GET method)
>graduation-server.herokuapp.com/category_courses?category=it_and_software



### Course APIs
#### Get Course by id (GET method)
>graduation-server.herokuapp.com/course?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&course_id=1

#### Add picture to course(POST method)
>graduation-server.herokuapp.com/coursePic?token=eyJhbGciOi....B7VuDtgOLpFIRMyr2bs1Ie1_FcaFwu_QIKvFTMEJ4FK9ZZJLC_LvBQ&course_id=1



### Classroom APIs
#### Get classroom by id (GET method)
>graduation-server.herokuapp.com/classroom?token=eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNTQ5MjQ0MjUyLCJleHAiOjE1NDk4NDkwNTJ9.LYczc5lPxhEcdZ_dYJrQwuYcb7iPBTduB7VuDtgOLpFIRMyr2bs1Ie1_FcaFwu_QIKvFTMEJ4FK9ZZJLC_LvBQ&classroom_id=1

#### Add picture to classroom(POST method)
>graduation-server.herokuapp.com/classroomPic?token=eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiw.....JrQwuYcb7iPBTduB7VuDtgOLpFIRMyr2bs1Ie1_FcaFwu_QIKvFTMEJ4FK9ZZJLC_LvBQ&classroom_id=1


### parent APIs
#### Add child for a parent (POST method)
>graduation-server.herokuapp.com/parent/add_child?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&first_name=habebbaba&date_of_birth=2001-02-05&email=child21@hotmail.com&password=12345678&username=keloi21&gender=1&grade=1'st grade

#### Join child into classroom (POST method)
>graduation-server.herokuapp.com/parent/join_child_classroom?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&first_name=habebbaba&passcode=Jb6xHKK

#### Enroll Child into course (POST method)
>graduation-server.herokuapp.com/parent/enroll_child_course?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&first_name=Habebbaba&course_id=1

#### Retrieve children (GET method)
>graduation-server.herokuapp.com/parent/children?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

#### Retrieve child (GET method)
>graduation-server.herokuapp.com/parent/child?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&user_id=4

#### Rate course for parent (POST method)
>graduation-server.herokuapp.com/parent/rate_course?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&course_id=1&first_name=Habebbaba&rate=4

#### Retrieve child courses(GET method)
>graduation-server.herokuapp.com/parent/child/courses?token=eyJhbGciOiJIUzUxMiJ9.eykjq................e20-skCcVv0wYJby5smU9ZRvTSw&user_id=4

#### Retrieve child reports for each course(GET method)
>graduation-server.herokuapp.com/parent/course/reports?token=eyJhbGciOiJIUzUxMiJ9.eyJzdW..............skJby5smU9ZRvTSw&user_id=2&course_id=1

#### Retrieve child reports (GET method)
>graduation-server.herokuapp.com/parent/child/reports?token=eyJhbGciOiJIUzUxMiJ9.eyJzdW..............skJby5smU9ZRvTSw&user_id=2


### teacher APIs
#### Request teaching (POST method)
>graduation-server.herokuapp.com/teacher/request_teaching?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

#### Request teaching (GET method) 
>graduation-server.herokuapp.com/teacher/request_teaching?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

>request already sent and not approved yet / request approved  / not found request for this user

#### Request category (POST method)
>graduation-server.herokuapp.com/teacher/request_category?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&category=Test Category 2

#### Create classroom (POST method)
>graduation-server.herokuapp.com/teacher/classrooms?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&classroom_name=CSE19 ComputerNetworks

#### Create course (POST method)
>graduation-server.herokuapp.com/teacher/courses?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&title=java&detailed_title=spring&description=itsjavaspringcourse&category=it_and_software&level=1

#### Update course (optional parameters)(PUT method)
>graduation-server.herokuapp.com/teacher/courses?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&course_id=22&title=test_update&detailed_title=test_test_update&description=blablablablabla&category=software&level=2

#### Retrieve course students by course's id (GET method)
>graduation-server.herokuapp.com/teacher/course/students?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&course_id=2

#### Retrieve classrooms for teacher (GET method)
>graduation-server.herokuapp.com/teacher/classrooms?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

#### Retrieve courses for teacher (GET method)
>graduation-server.herokuapp.com/teacher/courses?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

#### Update the classroom passcode by it's Id (PUT method)
>graduation-server.herokuapp.com/teacher/classroom?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&classroom_id=1

#### Delete classroom by it's Id (DELETE method)
>graduation-server.herokuapp.com/teacher/classroom?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&classroom_id=2

#### Create course in a classroom (POST method)
>graduation-server.herokuapp.com/teacher/classroom_courses?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&classroom_id=1&title=c++&detailed_title=datastructure&description=it's datastructure and algorithm course&category=it_and_software&level=2

#### Add course into classroom (POST method)
>graduation-server.herokuapp.com/teacher/classroom/course?token=eyJhbGciOiJIUzUxMiJ9...............CcVv0wYJby5smU9ZRvTSw&classroom_id=1&course_id=1

#### Delete course by it's Id (DELETE method)
>graduation-server.herokuapp.com/teacher/courses?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&course_id=2

#### Create section in a course (POST method)
>graduation-server.herokuapp.com/teacher/section?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&course_id=1&section_title=Introduction

#### Update the section info by it's id (PUT method)
>graduation-server.herokuapp.com/teacher/section?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&section_id=1&section_title=COURSE_INTRO

#### Delete section by it's id (DELETE method)
>graduation-server.herokuapp.com/teacher/section?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&section_id=2

#### Retrieve section by it's id (GET method)
>graduation-server.herokuapp.com/teacher/section?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&section_id=2

#### Create quiz by section id (POST method)
>graduation-server.herokuapp.com/teacher/quiz?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&section_id=1&quiz_title=Introduction&quiz_instructions=don't forget your calculator&quiz_time=30

#### Set number of selected questions (POST method)
>graduation-server.herokuapp.com/teacher/quiz/no_questions?token=eyJhbGcNTUyNT2ODJ9.PlO-.....tJD_VBAGljcpDDvTFSKlWz9Es2NTD3H0e7Yk_Bj0w&quiz_id=1&no_of_questions=8

#### Update the quiz info by it's id (optional parameters)(PUT method)
>graduation-server.herokuapp.com/teacher/quiz?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&quiz_id=1&quiz_title=Introduction2&quiz_instructions=don't forget your calculator2&quiz_time=45

#### Delete quiz by it's id (DELETE method)
>graduation-server.herokuapp.com/teacher/quiz?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&quiz_id=2

#### Retrieve quiz by it's id (GET method)
>graduation-server.herokuapp.com/quiz?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&quiz_id=1

#### Add question to quiz by quiz's id (POST method)
>graduation-server.herokuapp.com/teacher/question?token=eyJhbGciOiJIUzUx....SKEs2NTD3H0e7Yk_Bj0w&quiz_id=1&question_body=question1&is_multiple_choice=false&question_mark=1&question_level=1&question_reference=go to lecture 2
> easy level =1 , medium level=2 , hard level=3

#### Retrieve question by it's id (GET method)
>graduation-server.herokuapp.com/question?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&question_id=1

#### Update question by it's id (PUT method)
>graduation-server.herokuapp.com/teacher/question?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&question_id=1&question_body=question1&is_multiple_choice=false&question_mark=3

#### Delete question by it's id (DELETE method)
>graduation-server.herokuapp.com/teacher/question?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&question_id=3

#### Add answer to question by question's id (POST method)
>graduation-server.herokuapp.com/teacher/answer?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&question_id=1&answer_body=answer 1a&is_correct=false

#### Update answer by it's id (PUT method)
>graduation-server.herokuapp.com/teacher/answer?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&answer_id=2&answer_body=answer 1b&is_correct=true

#### Delete answer by it's id (DELETE method)
>graduation-server.herokuapp.com/teacher/answer?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&answer_id=2

#### upload lecture content (POST method)
>graduation-server.herokuapp.com/teacher/file?token=eyJhbGciOiJIUzUxMiJ9.eyJzd...0iDEz7_E6hsYeVwJe7_FCnfMzUHxe9GNOPHRv-J731kO91lepTvn_pRplVnxawGcw&section_id=1&version_level=2

#### get lecture by file id (GET method)
>graduation-server.herokuapp.com/teacher/file?token=eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyI0IjoxNTUwNzY2NDg2LCJleHAiO......C_24VswUN1EXZ2FLLw81Pz5eMom4S84Ug&file_id=4

#### delete lectue content (DELETE method)
>graduation-server.herokuapp.com/teacher/file?token=eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyIiwiaWFTUwNzY2NDg2LCJleHA....VSpeC_24VswUN1EXZ2FLLw81Pz5eMom4S84Ug&file_id=4

#### retrieve lecture by it's id (GET method)
>graduation-server.herokuapp.com/lecture?token=eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyIiwiaWFTUwNzY2NDg2LCJleHA....VSpeC_24VswUN1EXZ2FLLw81Pz5eMom4S84Ug&lecture_id=1


### student APIs
#### Join student in classroom with it's Passcode (POST method)
>graduation-server.herokuapp.com/student/join_classroom?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&passcode=Jb6xHKK

#### Retrieve classrooms for students (GET method)
>graduation-server.herokuapp.com/student/classrooms?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

#### Enroll Student into course (POST method)
>graduation-server.herokuapp.com/student/enroll_course?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&course_id=1

#### Retrieve courses for students (GET method)
>graduation-server.herokuapp.com/student/courses?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA

#### Rate course for student (post method)
>graduation-server.herokuapp.com/student/rate_course?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&course_id=1&rate=4

#### Retrieve lecture by it's id (GET method)
>graduation-server.herokuapp.com/lecture?token=eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyIiwiaWFTUwNzY2NDg2LCJleHA....VSpeC_24VswUN1EXZ2FLLw81Pz5eMom4S84Ug&lecture_id=1

#### get quiz info before start by it's id (GET method)
>graduation-server.herokuapp.com/student/quiz/info?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&quiz_id=3

#### start quiz by it's id (GET method)
>graduation-server.herokuapp.com/student/quiz/start?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&quiz_id=3

#### Submit quiz by it's id and json of questions and student answers (POST method)
>graduation-server.herokuapp.com/student/quiz/submit?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&quiz_id=3
>body : 
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

#### Get quiz score for student to know if he passed it and some other info by quiz id (GET method)
>graduation-server.herokuapp.com/student/quiz/score?token=eyJhbGciOiJIUzUx....WO-OSbvxAlG9n1li-pGnA&quiz_id=3

#### Get Accomplished courses (GET method)
>graduation-server.herokuapp.com/student/accomplished?token=eyJhbGciOJ9.eyJz..........2nmastkqauCWLC5RmnRuCpUUBg
