# Adaptive_E-learning_Backend
Graduation project about adaptive e-learning. This repo is the backend of the app.


## ERROR Codes
### 401 Unauthorized
> user isn't logged in.
### 409 Conflict
>unique name is token like username, email, classroom name, etc.

### 403 Forbidden
>this functionallity is not allowed to this user.

### 400 Bad Request
>the data stream sent by the client to the server didn't follow the rules.

### 304 Not Modified
>redirect without doing the request.

### 404 Not Found

### 204 No Content
>success without showing content. in delete functions.

### 201 Created
>post or put functions

## API Samples
### Login example (GET method)
>http://localhost:8080/auth/login?email=muhammed@gmail.com&password=12345678
or
>http://localhost:8080/auth/login?username=keloi&password=12345678

### Register example (POST method)
>http://localhost:8080/auth/register?firstname=mohamed&lastname=adel&email=user2@gmail.com&username=keloi&password=12345678&gender=1&date_of_birth=2001-07-27

### Retrieve user data (GET method)
>http://localhost:8080/auth/profile?token=eyJhbGciOiJIUzUxMiJ9....Na5P8wg

### Logout example (GET method)
>http://localhost:8080/auth/logout?token=eyJhbGciOiJIUzUxMiJ9.eyJzdWIi...

### Create classroom (POST method)
>http://localhost:8080/teacher/classrooms?token=eyJhbGciOiJIUzUxMiJ9....Na5P8wg&classroom_name=CSE19 ComputerNetworks&passcode=af39ns

### Retrieve classrooms for teacher (GET method)
>http://localhost:8080/teacher/classrooms?token={eyJhbGciOiJIUzUxMiJ9.eyJzdWIi...}

### Retrieve classrooms for students (GET method)
>http://localhost:8080/student/classrooms?token={eyJhbGciOiJIUzUxMiJ9.eyJzdWIi...}

### Update the classroom info by it's name (PUT method)
>http://localhost:8080/teacher/classroom?token=eyJhbGciOiJIUzU....VsOq8BNQ&classroom_name=CSE19_Security&passcode=fbg98u

### Delete classroom by it's Id (DELETE method)
>http://localhost:8080/teacher/classroom?token=eyJhbGciOiJIUzU....VsOq8BNQ&classroom_id=1

### Find classroom by it's id (GET method)
>http://localhost:8080/classroom?token={eyJhbGciOiJIUzUxMiJ9.eyJzdWIi...}&classroom_id=2

### Enroll student in classroom with it's id (POST method)
>http://localhost:8080/student/enroll?token=eyJhbGciOiJIUzU....V53Cqglc8dsUiAgZdrQSEBkA0KZnnhHcUumFQcg-EQ&classroom_name=CSE19_ComputerSecurity&passcode=fbg98u

### Add child for a parent (POST method)
>http://localhost:8080/parent/addchild?token={eyJhbGciOiJIUzUxMiJ9.eyJzdWIi...}&firstname=habebbaba&date_of_birth=2001-02-05&email=child1@hotmail.com&password=12345678&username=kelo1&gender=1

### Join child into classroom (POST method)
>http://localhost:8080/parent/enrollchild?token=eyJhbGciOiJIUzU....V53Cqglc8dsUiAgZdrQSEBkA0KZnnhHcUumFQcg-EQ&firstname=habebbaba&passcode=fbg98u

### Retrieve children (GET method)
>http://localhost:8080/parent/children?token=eyJhbGciOiJIUzU....V53Cqglc8dsUiAgZdrQSEBkA0KZnnhHcUumFQcg-EQ

### Retrieve child (GET method)
>http://localhost:8080/parent/child?token=eyJhbGciOiJIUzU....V53Cqglc8dsUiAgZdrQSEBkA0KZnnhHcUumFQcg-EQ&user_id=4

### Create course (POST method)
>http://localhost:8080/teacher/courses?token=eyJhbGciOiJIUzUxMi...gI17icUx9w&title=java&detailed_title=spring&description=it'sjavaspringcourse&category=it_and_software&level=1

### Create course in a classroom (POST method)
>http://localhost:8080/teacher/classroom_course?token=eyJhbGciOiJIUzUxMi...gI17icUx9w&classroom_id=1&title=c++&detailed_title=datastructure&description=it's datastructure and algorithm course&category=it_and_software&level=2

### Enroll Child into course (POST method)
>http://localhost:8080/parent/enrollchild_course?token=eyJhbGciOiJIUzUxMiJ9eyJ....17icUx9w&firstname=Habebbaba&courseID=2

### Enroll Student into course (POST method)
>http://localhost:8080/student/enroll_course?token=eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNTQ4NjEwODA4LCJleHAiOjE1NDkyMTU2MDh9....ZJgI17icUx9w&courseID=1

### Create section in a course (POST method)
>http://localhost:8080/teacher/section?token=eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIzIiwiaWF0IjoxNTQ5MDMxNTk2LCJleHAiOjE1NDk2MzYzOTV9....E5ubI3S7ARVtGA&course_id=1&section_title=Introduction

### Update the section info by it's id (PUT method)
>http://localhost:8080/teacher/section?token=eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIzIiwiaWF0IjoxNTQ5MDMxNTk2LCJleHAiOjE1NDk2MzYzOTV9....QMfE5ubI3S7ARVtGA&section_id=2&section_title=INTRODUCTION

### Delete section by it's id (DELETE method)
>http://localhost:8080/teacher/section?token=eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOi....1bgLQMfE5ubI3S7ARVtGA&section_id=3
