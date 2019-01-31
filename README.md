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
>http://localhost:8080/login?email=muhammed@gmail.com&password=12345678
or
>http://localhost:8080/login?username=keloi&password=12345678

### Register example (POST method)
>http://localhost:8080/auth/register?firstname=mohamed&lastname=adel&email=user2@gmail.com&username=keloi&password=12345678&gender=1&date_of_birth=2001-07-27

### Retrieve user data (GET method)
>http://localhost:8080/profile?token=eyJhbGciOiJIUzUxMiJ9....Na5P8wg

### Logout example (GET method)
>http://localhost:8080/logout?token=eyJhbGciOiJIUzUxMiJ9.eyJzdWIi...

### Create classroom (POST method)
>http://localhost:8080/teacher/classrooms?token=eyJhbGciOiJIUzUxMiJ9....Na5P8wg&classroom_name=CSE19 ComputerNetworks&passcode=af39ns

### Retrieve classrooms for teacher (GET method)
>http://localhost:8080/teacher/classrooms?token={eyJhbGciOiJIUzUxMiJ9.eyJzdWIi...}

### Retrieve classrooms for students (GET method)
>http://localhost:8080/student/classrooms?token={eyJhbGciOiJIUzUxMiJ9.eyJzdWIi...}

### Update the classroom info by it's name (PUT method)
>http://localhost:8080/teacher/classroom?token=eyJhbGciOiJIUzU....VsOq8BNQ&classroom_name=CSE19_Security&passcode=fbg98u

### Delete classroom by it's name (DELETE method)
>http://localhost:8080/teacher/classroom?token=eyJhbGciOiJIUzU....VsOq8BNQ&classroom_name=CSE19 Security

### Find classroom by it's id (GET method)
>http://localhost:8080/classroom?token={eyJhbGciOiJIUzUxMiJ9.eyJzdWIi...}&classroom_id=2

### Enroll student in classroom with it's id (POST method)
>http://localhost:8080/student/enroll?token=eyJhbGciOiJIUzU....V53Cqglc8dsUiAgZdrQSEBkA0KZnnhHcUumFQcg-EQ&classroom_name=CSE19_ComputerSecurity&passcode=fbg98u

### Add child for a parent (POST method)
>http://localhost:8080/parent/addchild?token={eyJhbGciOiJIUzUxMiJ9.eyJzdWIi...}&firstname=habebbaba&date_of_birth=2001-02-05&email=child1@hotmail.com&password=12345678&username=kelo1&gender=1

### Join child into classroom (POST method)
>http://localhost:8080/parent/enrollchild?token=eyJhbGciOiJIUzU....V53Cqglc8dsUiAgZdrQSEBkA0KZnnhHcUumFQcg-EQ&firstname=habebbaba&classroom_name=CSE19_ComputerSecurity&passcode=fbg98u

### Retrieve children (GET method)
>http://localhost:8080/parent/children?token=eyJhbGciOiJIUzU....V53Cqglc8dsUiAgZdrQSEBkA0KZnnhHcUumFQcg-EQ

### Retrieve child (GET method)
>http://localhost:8080/parent/child?token=eyJhbGciOiJIUzU....V53Cqglc8dsUiAgZdrQSEBkA0KZnnhHcUumFQcg-EQ&firstname=habebbaba

### Create course (POST method)
>http://localhost:8080/teacher/course?token=eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNTQ4NjEwODA4LCJleHAiOjE1NDkyMTU2MDh9.YhyWPZfgKqbdUf9ofy2157lEYM3tlIZkYsNV37EtRnS78C1AvwSC1VSHJFdiNNFedcLDwxHzQ6ZJgI17icUx9w&title=java&detailed_title=spring&description=it'sjavaspringcourse&level=1

### Create course in a classroom (POST method)
>http://localhost:8080/teacher/classroom_course?token=eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNTQ4NjEwODA4LCJleHAiOjE1NDkyMTU2MDh9.YhyWPZfgKqbdUf9ofy2157lEYM3tlIZkYsNV37EtRnS78C1AvwSC1VSHJFdiNNFedcLDwxHzQ6ZJgI17icUx9w&classroom_id=1&title=c++&detailed_title=datastructure&description=it's datastructure and algorithm course&level=2
