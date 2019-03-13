package com.adaptivelearning.server.Controller;

import com.adaptivelearning.server.FancyModel.FancyClassroom;
import com.adaptivelearning.server.FancyModel.FancyCourse;
import com.adaptivelearning.server.FancyModel.FancyStudentQuiz;
import com.adaptivelearning.server.Model.*;
import com.adaptivelearning.server.Repository.*;
import com.adaptivelearning.server.Security.JwtTokenProvider;
import com.adaptivelearning.server.constants.Mapping;
import com.adaptivelearning.server.constants.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.*;

@RestController
public class StudentController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CourseRepository courseRepository;
    
    @Autowired
    ClassroomRepository classroomRepository;

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    StudentQuizRepository studentQuizRepository;

    @Autowired
    JwtTokenProvider jwtTokenChecker;

    @PostMapping(Mapping.JOIN_CLASSROOM)
    public ResponseEntity<?> joinStudentIntoClassroom(@RequestParam(Param.ACCESS_TOKEN) String token,
                              @Valid @RequestParam(Param.PASSCODE) String passcode) {

        User user = userRepository.findByToken(token);

        if(user == null){
          	 return new ResponseEntity<>("User Is Not Valid",
                     HttpStatus.UNAUTHORIZED);
        }
        if (!jwtTokenChecker.validateToken(token)) {
            user.setToken("");
            userRepository.save(user);
            return new ResponseEntity<>("session expired",
                    HttpStatus.UNAUTHORIZED);
        }

        if(user.getParent() != null){
         	 return new ResponseEntity<>("You Must Have Parent permission",
                     HttpStatus.FORBIDDEN);
        }

        Classroom classroom = classroomRepository.findByPassCode(passcode);

        if (classroom == null ) {
        	 return new ResponseEntity<>("Classroom Is Not Found",
                     HttpStatus.NOT_FOUND);
        }
        
        if(classroom.getStudents().contains(user)) {
        	 return new ResponseEntity<>("Already Joined ",
                     HttpStatus.FORBIDDEN); 
        }
        
        // i don't know if the creator can join to his classroom or not
        
//        if(classroom.getCreator().getUserId()==user.getUserId()) {
//       	    return new ResponseEntity<>("classroom creator can't join to his classroom",
//                    HttpStatus.FORBIDDEN); 
//       }

        classroom.getStudents().add(user);
        classroomRepository.save(classroom);

        FancyClassroom fancyClassroom = new FancyClassroom();
        fancyClassroom.toFancyClassroomMapping(classroom);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping(Mapping.STUDENT_CLASSROOMS)
    public ResponseEntity<?> retrieveJoinedClassrooms(@RequestParam(Param.ACCESS_TOKEN) String token) {

        User user = userRepository.findByToken(token);

        if(user == null){
         	 return new ResponseEntity<>("User Is Not Valid",
                     HttpStatus.UNAUTHORIZED);
        }
        if (!jwtTokenChecker.validateToken(token)) {
            user.setToken("");
            userRepository.save(user);
            return new ResponseEntity<>("session expired",
                    HttpStatus.UNAUTHORIZED);
        }
        FancyClassroom fancyClassroom = new FancyClassroom();
        return new ResponseEntity<>(fancyClassroom.toFancyClassroomListMapping(user.getJoins()),
                HttpStatus.OK);
    }
    
    @GetMapping(Mapping.STUDENT_COURSES)
    public ResponseEntity<?> retrieveEnrolledCourses(@RequestParam(Param.ACCESS_TOKEN) String token) {

        User user = userRepository.findByToken(token);

        if(user == null){
         	 return new ResponseEntity<>("User Is Not Valid",
                     HttpStatus.UNAUTHORIZED);
        }
        if (!jwtTokenChecker.validateToken(token)) {
            user.setToken("");
            userRepository.save(user);
            return new ResponseEntity<>("session expired",
                    HttpStatus.UNAUTHORIZED);
        }
        FancyCourse fancyCourse = new FancyCourse();
        return new ResponseEntity<>(fancyCourse.toFancyCourseListMapping(user.getEnrolls()),
                HttpStatus.OK);
    }
    

    @PostMapping(Mapping.ENROLL_COURSE)
    public ResponseEntity<?> EnrollCourse(@RequestParam(Param.ACCESS_TOKEN) String token,
                                   @Valid @RequestParam(Param.COURSE_ID) Long courseId) {

        User user = userRepository.findByToken(token);
        Course course=courseRepository.findByCourseId(courseId);
        
        if(user == null){
        	 return new ResponseEntity<>("User is not present ",
                     HttpStatus.UNAUTHORIZED); 
        }
        if (!jwtTokenChecker.validateToken(token)) {
            user.setToken("");
            userRepository.save(user);
            return new ResponseEntity<>("session expired",
                    HttpStatus.UNAUTHORIZED);
        }
        if(course == null){
       	 return new ResponseEntity<>(" course with this id is not found ",
                    HttpStatus.NOT_FOUND); 
       }

        if(user.getParent() != null){
        	 return new ResponseEntity<>("user is child it's not allowed ",
                     HttpStatus.FORBIDDEN); 
        }
        if(course.getPublisher().getUserId()==user.getUserId()) {
        	 return new ResponseEntity<>("course publisher can't enroll in his courses",
                     HttpStatus.FORBIDDEN); 
        }
        if(course.getLearners().contains(user)) {
        	 return new ResponseEntity<>("Already Enrolled ",
                     HttpStatus.FORBIDDEN); 
        }
       course.getLearners().add(user);
        course.increamentStudents();
   
       courseRepository.save(course);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping(Mapping.STUDENT_RATE_COURSE)
    public ResponseEntity<?> studentRateCourse(@RequestParam(Param.ACCESS_TOKEN) String token,
                                               @Valid @RequestParam(Param.COURSE_ID) Long courseId,
                                               @Valid @RequestParam(Param.Rate) short studentRate){

        User user = userRepository.findByToken(token);
        Course course=courseRepository.findByCourseId(courseId);

        if(user == null){
            return new ResponseEntity<>("User is not present ",
                    HttpStatus.UNAUTHORIZED);
        }
        if (!jwtTokenChecker.validateToken(token)) {
            user.setToken("");
            userRepository.save(user);
            return new ResponseEntity<>("session expired",
                    HttpStatus.UNAUTHORIZED);
        }
        if(course == null){
            return new ResponseEntity<>("Course with this id is not found ",
                    HttpStatus.NOT_FOUND);
        }

        if(user.getParent() != null){
            return new ResponseEntity<>("User is child it's not allowed ",
                    HttpStatus.FORBIDDEN);
        }

        if(course.getPublisher().getUserId()==user.getUserId()) {
            return new ResponseEntity<>("Course publisher can't rate his courses",
                    HttpStatus.FORBIDDEN);
        }

        if(course.getRaters().contains(user)){
            return new ResponseEntity<>("User cannot rate again",
                    HttpStatus.FORBIDDEN);
        }

        /*

        validate that the student has finished the course

         */

        int old_raters_num = course.getNumberOfRaters();
        float new_rate =  ((course.getRate() * old_raters_num) + studentRate)/(old_raters_num + 1);
        course.increamentRaters();
        course.setRate(new_rate);
        course.getRaters().add(user);
        courseRepository.save(course);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(Mapping.STUDENT_START_QUIZ)
    public ResponseEntity<?> studentStartQuiz(@RequestParam(Param.ACCESS_TOKEN) String token,
                                               @Valid @RequestParam(Param.QUIZ_ID) Long quizId){
        User user = userRepository.findByToken(token);
        Quiz quiz = quizRepository.findByQuizId(quizId);

        if (user == null) {
            return new ResponseEntity<>("User is not present ",
                    HttpStatus.UNAUTHORIZED);
        }
        if (!jwtTokenChecker.validateToken(token)) {
            user.setToken("");
            userRepository.save(user);
            return new ResponseEntity<>("session expired",
                    HttpStatus.UNAUTHORIZED);
        }
        if (quiz == null) {
            return new ResponseEntity<>("Quiz with this id is not found ",
                    HttpStatus.NOT_FOUND);
        }

        if (!quiz.getLecture().getSection().getCourse().getLearners().contains(user))
            return new ResponseEntity<>("You are not enrolled in this course",
                    HttpStatus.FORBIDDEN);

        StudentQuiz studentQuiz = studentQuizRepository.findByUserAndQuiz(user,quiz);

        if (studentQuiz == null) {
            studentQuiz = new StudentQuiz(user, quiz);
            Date date = new Date(new Date().getTime() + quiz.getTime()*60000);
            studentQuiz.setSubmitDate(date);
        }
        else {
            Date date = new Date();
            if (studentQuiz.getSubmitDate().after(date) && studentQuiz.getStartDate() != null)
                return new ResponseEntity<>("Already started.",
                        HttpStatus.FORBIDDEN);
            Long diffInHours = (date.getTime() - studentQuiz.getSubmitDate().getTime())/(1000*60*60);
            if (diffInHours < 4 && studentQuiz.getAttempts()==3)
                return new ResponseEntity<>("3 attempts every 4 hours",
                        HttpStatus.FORBIDDEN);
            if (diffInHours >= 4){
                studentQuiz.setAttempts(0);
            }
            studentQuiz.setStartDate(date);
            studentQuiz.setSubmitDate(new Date(date.getTime() + quiz.getTime()*60000));
            studentQuiz.setPassed(false);
            studentQuiz.setMark(0);
        }
        studentQuizRepository.save(studentQuiz);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(Mapping.STUDENT_SUBMIT_QUIZ)
    public ResponseEntity<?> studentSubmitQuiz(@RequestParam(Param.ACCESS_TOKEN) String token,
                                               @Valid @RequestParam(Param.QUIZ_ID) Long quizId,
                                               HttpEntity<String> httpEntity) throws JSONException {

        User user = userRepository.findByToken(token);
        Quiz quiz = quizRepository.findByQuizId(quizId);
        String json = httpEntity.getBody();
        JSONObject obj = new JSONObject(json);


        if (user == null) {
            return new ResponseEntity<>("User is not present.",
                    HttpStatus.UNAUTHORIZED);
        }
        if (!jwtTokenChecker.validateToken(token)) {
            user.setToken("");
            userRepository.save(user);
            return new ResponseEntity<>("session expired.",
                    HttpStatus.UNAUTHORIZED);
        }
        if (quiz == null) {
            return new ResponseEntity<>("Quiz with this id is not found.",
                    HttpStatus.NOT_FOUND);
        }

        if (!quiz.getLecture().getSection().getCourse().getLearners().contains(user))
            return new ResponseEntity<>("You are not enrolled in this course.",
                    HttpStatus.FORBIDDEN);

        StudentQuiz studentQuiz = studentQuizRepository.findByUserAndQuiz(user,quiz);

        if (studentQuiz == null || studentQuiz.getStartDate() == null)
            return new ResponseEntity<>("You have not started this quiz yet.",
                    HttpStatus.FORBIDDEN);

        Date now = new Date();

        if (now.after(studentQuiz.getSubmitDate())){
            studentQuiz.setStartDate(null);
            studentQuiz.setSubmitDate(null);
            return new ResponseEntity<>("You have exceeded the time limit of the quiz.",
                    HttpStatus.FORBIDDEN);
        }

        JSONArray questions = obj.getJSONArray("questions");
        for (int i =0;i< questions.length();i++){
            JSONObject questionObj = questions.getJSONObject(i);
            Long questionId = questionObj.getLong("question_id");
            JSONArray answersObj = questionObj.getJSONArray("answers_ids");
            List<Long> studentAnswers = new LinkedList<>();
            for (int j =0;j< answersObj.length();j++){
                studentAnswers.add(answersObj.getLong(j));
            }
            // remove duplicates
            Set<Long> set = new LinkedHashSet<>(studentAnswers);
            studentAnswers.clear();
            studentAnswers.addAll(set);

            Question question = questionRepository.findByQuestionId(questionId);
            List<Answer> answers = question.getAnswers();
            List<Answer> modelAnswers = new LinkedList<>();
            for (Answer answer:
                    answers) {
                if (answer.isCorrect())
                    modelAnswers.add(answer);
            }
            List<Answer> studentAnswerList = new LinkedList<>();
            for (Long studentAnswerId:
                    studentAnswers) {
                Answer studentAnswer = answerRepository.findByAnswerId(studentAnswerId);
                studentAnswerList.add(studentAnswer);
            }
            if (new LinkedHashSet<>(studentAnswerList).equals(new LinkedHashSet<>(modelAnswers)))
                studentQuiz.setMark(studentQuiz.getMark() + question.getMark());
        }
        if (studentQuiz.getMark() >= 0.7 * quiz.getTotalMark())
            studentQuiz.setPassed(true);
        studentQuiz.setSubmitDate(new Date());
        studentQuiz.setStartDate(null);
        studentQuiz.setAttempts(studentQuiz.getAttempts() + 1);
        studentQuizRepository.save(studentQuiz);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(Mapping.STUDENT_QUIZ)
    public ResponseEntity<?> studentGetQuiz(@RequestParam(Param.ACCESS_TOKEN) String token,
                                               @Valid @RequestParam(Param.QUIZ_ID) Long quizId){
        User user = userRepository.findByToken(token);
        Quiz quiz = quizRepository.findByQuizId(quizId);

        if (user == null) {
            return new ResponseEntity<>("User is not present.",
                    HttpStatus.UNAUTHORIZED);
        }
        if (!jwtTokenChecker.validateToken(token)) {
            user.setToken("");
            userRepository.save(user);
            return new ResponseEntity<>("session expired.",
                    HttpStatus.UNAUTHORIZED);
        }
        if (quiz == null) {
            return new ResponseEntity<>("Quiz with this id is not found.",
                    HttpStatus.NOT_FOUND);
        }

        if (!quiz.getLecture().getSection().getCourse().getLearners().contains(user))
            return new ResponseEntity<>("You are not enrolled in this course.",
                    HttpStatus.FORBIDDEN);

        StudentQuiz studentQuiz = studentQuizRepository.findByUserAndQuiz(user,quiz);

        if (studentQuiz == null)
            return new ResponseEntity<>("Quiz has not taken yet.",
                    HttpStatus.NOT_FOUND);

        FancyStudentQuiz fancyStudentQuiz= new FancyStudentQuiz();
        return new ResponseEntity<>(fancyStudentQuiz.toFancyStudentQuiz(studentQuiz),
                HttpStatus.OK);
    }
}
