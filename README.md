# Adaptive_E-learning_Backend
Graduation project about adaptive e-learning. This repo is the backend of the app.


## API Samples
### Login example (GET method)
>http://localhost:8080/auth/login?email=muhammed@gmail.com&password=12345678
or
>http://localhost:8080/auth/login?username=keloi&password=12345678

### Register example (POST method)
>http://localhost:8080/auth/register?firstname=mohamed&lastname=adel&email=user2@gmail.com&username=keloi&password=12345678&gender=1&date_of_birth=2001-07-27

### Logout example (GET method)
>http://localhost:8080/api/auth/logout?access_token=eyJhbGciOiJIUzUxMiJ9.eyJzdWIi...

### Create classroom (POST method)
>http://localhost:8080/teacher/classrooms?token=eyJhbGciOiJIUzUxMiJ9....Na5P8wg&classroom_name=CSE19 ComputerNetworks&passcode=af39ns

### Retrieve classrooms for teacher (GET method)
>http://localhost:8080/teacher/classrooms?access_token={eyJhbGciOiJIUzUxMiJ9.eyJzdWIi...}

### Retrieve classrooms for students (GET method)
>http://localhost:8080/student/classrooms?access_token={eyJhbGciOiJIUzUxMiJ9.eyJzdWIi...}

### Update the classroom info by it's name (PUT method)
>http://localhost:8080/teacher/classroom?token=eyJhbGciOiJIUzU....VsOq8BNQ&classroom_name=CSE19_Security&passcode=fbg98u

### Delete classroom by it's name (DELETE method)
>http://localhost:8080/teacher/classroom?token=eyJhbGciOiJIUzU....VsOq8BNQ&classroom_name=CSE19 Security

### Find classroom by it's id (GET method)
>http://localhost:8080/classroom?access_token={eyJhbGciOiJIUzUxMiJ9.eyJzdWIi...}&classroom_id=2

### Enroll student in classroom with it's id (POST method)
>http://localhost:8080/student/enroll?token=eyJhbGciOiJIUzU....V53Cqglc8dsUiAgZdrQSEBkA0KZnnhHcUumFQcg-EQ&classroom_name=CSE19_ComputerSecurity&passcode=fbg98u

### Add child for a parent (POST method)
>http://localhost:8080/parent/addchild?token={eyJhbGciOiJIUzUxMiJ9.eyJzdWIi...}&firstname=habebbaba&date_of_birth=2001-02-05&email=child1@hotmail.com&password=12345678&username=kelo1&gender=1

### join child into classroom (POST method)
>http://localhost:8080/parent/enrollchild?token=eyJhbGciOiJIUzU....V53Cqglc8dsUiAgZdrQSEBkA0KZnnhHcUumFQcg-EQ&firstname=habebbaba&classroom_name=CSE19_ComputerSecurity&passcode=fbg98u
