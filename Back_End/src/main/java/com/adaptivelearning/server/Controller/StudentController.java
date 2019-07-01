package com.adaptivelearning.server.Controller;

import com.adaptivelearning.server.FancyModel.*;
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
    SectionRepository sectionRepository;
    
    @Autowired
    LectureRepository lectureRepository;
    
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
    StudentCourseRepository studentCourseRepository;
    
    @Autowired
    ReportRepository reportRepository;

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

        FancyClassroom fancyClassroom = new FancyClassroom(studentCourseRepository);
        fancyClassroom.toFancyClassroomMapping(classroom, user);
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
        FancyClassroom fancyClassroom = new FancyClassroom(studentCourseRepository);
        return new ResponseEntity<>(fancyClassroom.toFancyClassroomListMapping(user.getJoins(),
                user),
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
        FancyCourse fancyCourse = new FancyCourse(studentCourseRepository);
        return new ResponseEntity<>(fancyCourse.toFancyCourseListMapping(user.getEnrolls(),
                user),
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
       StudentCourse studentCourse=new StudentCourse(user, course);
       courseRepository.save(course);
       studentCourseRepository.save(studentCourse);
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

        // if(course.getRaters().contains(user)){
        //     return new ResponseEntity<>("User cannot rate again",
        //             HttpStatus.FORBIDDEN);
        // }

        /*

        validate that the student has finished the course

         */

        StudentCourse studentCourse = studentCourseRepository.findByUserAndCourse(user, course);

        if (studentCourse.getRate() == -1){
            int old_raters_num = course.getNumberOfRaters();
            float new_rate =  ((course.getRate() * old_raters_num) + studentRate)/(old_raters_num + 1);
            course.increamentRaters();
            course.setRate(new_rate);
        }
        else {
            int raters_num = course.getNumberOfRaters();
            float new_rate =  (((course.getRate() * raters_num)-studentCourse.getRate()) + studentRate)/raters_num;
            course.setRate(new_rate);
        }

        studentCourse.setRate(studentRate);
        courseRepository.save(course);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(Mapping.STUDENT_COURSE)
    public ResponseEntity<?> getStudentCourseRelatedInfo(@RequestParam(Param.ACCESS_TOKEN) String token,
                                                         @Valid @RequestParam(Param.COURSE_ID) Long courseId){
        User user = userRepository.findByToken(token);
        Course course=courseRepository.findByCourseId(courseId);

        if(user == null){
            return new ResponseEntity<>("user isn't logged in",
                    HttpStatus.UNAUTHORIZED);
        }
        if (!jwtTokenChecker.validateToken(token)) {
            user.setToken("");
            userRepository.save(user);
            return new ResponseEntity<>("session expired",
                    HttpStatus.UNAUTHORIZED);
        }
        if(course == null){
            return new ResponseEntity<>("Course is not found",
                    HttpStatus.NOT_FOUND);
        }

        StudentCourse studentCourse = studentCourseRepository.findByUserAndCourse(user,course);

        if (studentCourse == null)
            return new ResponseEntity<>("User is not enrolled in this course",
                    HttpStatus.BAD_REQUEST);

        FancyStudentCourse fancyStudentCourse = new FancyStudentCourse();
        return new ResponseEntity<>(fancyStudentCourse.toFancyStudentCourse(studentCourse),
                HttpStatus.OK);
    }
    
    // @PostMapping(Mapping.STUDENT_START_QUIZ)
    // public ResponseEntity<?> studentStartQuiz(@RequestParam(Param.ACCESS_TOKEN) String token,
    //                                            @Valid @RequestParam(Param.QUIZ_ID) Long quizId){
    //     User user = userRepository.findByToken(token);
    //     Quiz quiz = quizRepository.findByQuizId(quizId);

    //     if (user == null) {
    //         return new ResponseEntity<>("User is not present ",
    //                 HttpStatus.UNAUTHORIZED);
    //     }
    //     if (!jwtTokenChecker.validateToken(token)) {
    //         user.setToken("");
    //         userRepository.save(user);
    //         return new ResponseEntity<>("session expired",
    //                 HttpStatus.UNAUTHORIZED);
    //     }
    //     if (quiz == null) {
    //         return new ResponseEntity<>("Quiz with this id is not found ",
    //                 HttpStatus.NOT_FOUND);
    //     }

    //     if (!quiz.getLecture().getSection().getCourse().getLearners().contains(user))
    //         return new ResponseEntity<>("You are not enrolled in this course",
    //                 HttpStatus.FORBIDDEN);

    //     StudentQuiz studentQuiz = studentQuizRepository.findByUserAndQuiz(user,quiz);

    //     if (studentQuiz == null) {
    //         studentQuiz = new StudentQuiz(user, quiz);
    //         Date date = new Date(new Date().getTime() + quiz.getTime()*60000);
    //         studentQuiz.setSubmitDate(date);
    //     }
    //     else {
    //         Date date = new Date();
    //         if (studentQuiz.getSubmitDate().after(date) && studentQuiz.getStartDate() != null)
    //             return new ResponseEntity<>("Already started.",
    //                     HttpStatus.FORBIDDEN);
    //         Long diffInHours = (date.getTime() - studentQuiz.getSubmitDate().getTime())/(1000*60*60);
    //         if (diffInHours < 4 && studentQuiz.getAttempts()==3)
    //             return new ResponseEntity<>("3 attempts every 4 hours",
    //                     HttpStatus.FORBIDDEN);
    //         if (diffInHours >= 4){
    //             studentQuiz.setAttempts(0);
    //         }
    //         studentQuiz.setStartDate(date);
    //         studentQuiz.setSubmitDate(new Date(date.getTime() + quiz.getTime()*60000));
    //         studentQuiz.setPassed(false);
    //         studentQuiz.setMark(0);
    //     }
    //     studentQuizRepository.save(studentQuiz);
    //     return new ResponseEntity<>(HttpStatus.OK);
    // }

    @GetMapping(Mapping.STUDENT_QUIZ_INFO)
    public ResponseEntity<?> studentGetQuizInfo(@RequestParam(Param.ACCESS_TOKEN) String token,
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

        if (!quiz.getSection().getCourse().getLearners().contains(user))
            return new ResponseEntity<>("You are not enrolled in this course.",
                    HttpStatus.FORBIDDEN);

        return new ResponseEntity<>(quizRepository.getCustomQuiz(quizId),
                HttpStatus.OK);
    }

    @PostMapping(Mapping.STUDENT_SUBMIT_QUIZ)
    public ResponseEntity<?> studentSubmitQuiz(@RequestParam(Param.ACCESS_TOKEN) String token,
                                               @Valid @RequestParam(Param.QUIZ_ID) Long quizId,
                                               HttpEntity<String> httpEntity) throws JSONException {

        User user = userRepository.findByToken(token);
        Quiz quiz = quizRepository.findByQuizId(quizId);
        Course course = courseRepository.findByCourseId(quiz.getSection().getCourse().getCourseId());
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

        if (!quiz.getSection().getCourse().getLearners().contains(user))
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
        studentQuiz.setTotalAttempts(studentQuiz.getTotalAttempts()+1);
        studentQuizRepository.save(studentQuiz);
        // from here the updating of student rank according to quiz mark and number of
 		// attempts and old rank
 		StudentCourse studentCourse = studentCourseRepository.findByUserAndCourse(user, course);
 		float old_rank = studentCourse.getRank();
 		int no_of_attempts = studentQuiz.getAttempts();
 		float quiz_grade = ( studentQuiz.getMark() / studentQuiz.getQuiz().getTotalMark() ) * 100 ;
 		float new_rank = studentCourse.updateRank(old_rank, no_of_attempts, quiz_grade);
 		studentCourse.setRank(new_rank);
 		studentCourseRepository.save(studentCourse);
        
        if(user.isChild()) {
        Report childReport = new Report();
        childReport.setChildID(user.getUserId());
        childReport.setParentID(user.getParent().getUserId());
        childReport.setQuizID(quizId);
        childReport.setPassed(studentQuiz.getPassed());
        childReport.setStudentMark(studentQuiz.getMark());
        childReport.setSubmitDate(studentQuiz.getSubmitDate());
        childReport.setTotalMark(quiz.getTotalMark());
        childReport.setTotalAttempts(studentQuiz.getTotalAttempts());
        childReport.setCourseID(quiz.getSection().getCourse().getCourseId());
        
        reportRepository.save(childReport);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(Mapping.STUDENT_START_QUIZ)
    public ResponseEntity<?> studentStartRandomQuiz(@RequestParam(Param.ACCESS_TOKEN) String token,
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

        if (!quiz.getSection().getCourse().getLearners().contains(user))
            return new ResponseEntity<>("You are not enrolled in this course.",
                    HttpStatus.FORBIDDEN);
      
        StudentQuiz studentQuiz = studentQuizRepository.findByUserAndQuiz(user,quiz);
        
        if (studentQuiz == null) {
            studentQuiz = new StudentQuiz(user, quiz);
            Date date = new Date(new Date().getTime() + quiz.getTime()*60000);
            studentQuiz.setSubmitDate(date);
        }
        else {
            Date date = new Date();
            if (studentQuiz.getSubmitDate() != null && studentQuiz.getSubmitDate().after(date) && studentQuiz.getStartDate() != null)
                return new ResponseEntity<>("Already started.",
                        HttpStatus.FORBIDDEN);
            if (studentQuiz.getSubmitDate() != null){
                Long diffInHours = (date.getTime() - studentQuiz.getSubmitDate().getTime())/(1000*60*60);
                if (diffInHours < 4 && studentQuiz.getAttempts()==3)
                    return new ResponseEntity<>("3 attempts every 4 hours",
                            HttpStatus.FORBIDDEN);
                if (diffInHours >= 4){
                    studentQuiz.setAttempts(0);
                }
            }
            studentQuiz.setStartDate(date);
            studentQuiz.setSubmitDate(new Date(date.getTime() + quiz.getTime()*60000));
            studentQuiz.setPassed(false);
            studentQuiz.setMark(0);
        }
 
          short selectedQuestions= quiz.getNo_of_questions();
          List totalQuestions= questionRepository.findByQuiz(quiz);
        
          if(totalQuestions.size() == selectedQuestions) {
        	quiz.setQuestions(totalQuestions);
       //     StudentQuiz studentQuiz = new StudentQuiz(user, quiz);
            studentQuizRepository.save(studentQuiz);
            FancyQuiz fancyquiz=new FancyQuiz();
            return new ResponseEntity<>(fancyquiz.toFancyQuizMapping(quiz, user.isTeacher()),
                 HttpStatus.OK);
        }
        else {
        StudentCourse studentCourse=studentCourseRepository.findByUserAndCourse(user, quiz.getSection().getCourse());
        float studentRank=studentCourse.getRank();
        
        if(studentRank<4) {
        	 List easyQuestions= questionRepository.findByQuizAndLevel(quiz, (short) 1);
        	 if(easyQuestions.size() >= selectedQuestions) {
        		 List questions= questionRepository.findRandom(quizId, 1 ,selectedQuestions );
                 quiz.setQuestions(questions);
             //    StudentQuiz studentQuiz = new StudentQuiz(user, quiz);
                 studentQuizRepository.save(studentQuiz);
                 FancyQuiz fancyquiz=new FancyQuiz();
                 return new ResponseEntity<>(fancyquiz.toFancyQuizMapping(quiz, user.isTeacher()),
                      HttpStatus.OK);
        	     }
                 else {
                	int remender= selectedQuestions - easyQuestions.size();
                	
                	List mediumQuestions= questionRepository.findByQuizAndLevel(quiz, (short) 2);
                	if(mediumQuestions.size() >= remender) {
               		    List questions= questionRepository.findRandom(quizId, 2 ,remender );
               		  
               		    questions.addAll(easyQuestions);
               		
                        quiz.setQuestions(questions);
                 //       StudentQuiz studentQuiz = new StudentQuiz(user, quiz);
                        studentQuizRepository.save(studentQuiz);
                        FancyQuiz fancyquiz=new FancyQuiz();
                        return new ResponseEntity<>(fancyquiz.toFancyQuizMapping(quiz, user.isTeacher()),
                             HttpStatus.OK);
               	     }
                	else {
                		int remender2= selectedQuestions - easyQuestions.size()- mediumQuestions.size();
                		List hardQuestions= questionRepository.findByQuizAndLevel(quiz, (short) 3);
                		List questions= questionRepository.findRandom(quizId, 3 ,remender2 );
               		    questions.addAll(easyQuestions);
               		    questions.addAll(mediumQuestions);
                        quiz.setQuestions(questions);
                   //     StudentQuiz studentQuiz = new StudentQuiz(user, quiz);
                        studentQuizRepository.save(studentQuiz);
                        FancyQuiz fancyquiz=new FancyQuiz();
                        return new ResponseEntity<>(fancyquiz.toFancyQuizMapping(quiz, user.isTeacher()),
                             HttpStatus.OK);
                	}
        	 }
        	
        }
        else if(studentRank>4 && studentRank<7) {
        	 List mediumQuestions= questionRepository.findByQuizAndLevel(quiz, (short) 2);
        	 if(mediumQuestions.size() >= selectedQuestions) {
        		 List questions= questionRepository.findRandom(quizId, 2 ,selectedQuestions );
                 quiz.setQuestions(questions);
               //  StudentQuiz studentQuiz = new StudentQuiz(user, quiz);
                 studentQuizRepository.save(studentQuiz);
                 FancyQuiz fancyquiz=new FancyQuiz();
                 return new ResponseEntity<>(fancyquiz.toFancyQuizMapping(quiz, user.isTeacher()),
                      HttpStatus.OK);
        	     }
                 else {
                	int remender= selectedQuestions - mediumQuestions.size();                	
                	List hardQuestions= questionRepository.findByQuizAndLevel(quiz, (short) 3);
                	if(hardQuestions.size() >= remender) {
               		    List questions= questionRepository.findRandom(quizId, 3 ,remender );
               		 
               		    questions.addAll(mediumQuestions);
                        quiz.setQuestions(questions);
                    //    StudentQuiz studentQuiz = new StudentQuiz(user, quiz);
                        studentQuizRepository.save(studentQuiz);
                        FancyQuiz fancyquiz=new FancyQuiz();
                        return new ResponseEntity<>(fancyquiz.toFancyQuizMapping(quiz, user.isTeacher()),
                             HttpStatus.OK);
               	     }
                	else {
                		int remender2= selectedQuestions - hardQuestions.size()- mediumQuestions.size();
                		List easyQuestions= questionRepository.findByQuizAndLevel(quiz, (short) 1);
                		List questions= questionRepository.findRandom(quizId, 3 ,remender2 );
               		    questions.addAll(hardQuestions);
               		    questions.addAll(mediumQuestions);
                        quiz.setQuestions(questions);
                    //    StudentQuiz studentQuiz = new StudentQuiz(user, quiz);
                        studentQuizRepository.save(studentQuiz);
                        FancyQuiz fancyquiz=new FancyQuiz();
                        return new ResponseEntity<>(fancyquiz.toFancyQuizMapping(quiz, user.isTeacher()),
                             HttpStatus.OK);
                	}
        	 }
        
        }
        else {
        	 List hardQuestions= questionRepository.findByQuizAndLevel(quiz, (short) 3);
        	 if(hardQuestions.size() >= selectedQuestions) {
        		 List questions= questionRepository.findRandom(quizId, 3 ,selectedQuestions );
                 quiz.setQuestions(questions);
                 //StudentQuiz studentQuiz = new StudentQuiz(user, quiz);
                 studentQuizRepository.save(studentQuiz);
                 FancyQuiz fancyquiz=new FancyQuiz();
                 return new ResponseEntity<>(fancyquiz.toFancyQuizMapping(quiz, user.isTeacher()),
                      HttpStatus.OK);
        	     }
                 else {
                	int remender= selectedQuestions - hardQuestions.size();
                	 System.out.println("//////////// "+remender+" ////////////");
                	List mediumQuestions= questionRepository.findByQuizAndLevel(quiz, (short) 2);
                	if(mediumQuestions.size() >= remender) {
               		    List questions= questionRepository.findRandom(quizId, 2 ,remender );
               		  System.out.println("//////////// "+questions.size()+" ////////////");
               		    questions.addAll(hardQuestions);
               		    System.out.println("//////////// "+questions.size()+" ////////////");
                        quiz.setQuestions(questions);
                   //     StudentQuiz studentQuiz = new StudentQuiz(user, quiz);
                        studentQuizRepository.save(studentQuiz);
                        FancyQuiz fancyquiz=new FancyQuiz();
                        return new ResponseEntity<>(fancyquiz.toFancyQuizMapping(quiz, user.isTeacher()),
                             HttpStatus.OK);
               	     }
                	else {
                		int remender2= selectedQuestions - hardQuestions.size()- mediumQuestions.size();
                		List easyQuestions= questionRepository.findByQuizAndLevel(quiz, (short) 1);
                		List questions= questionRepository.findRandom(quizId, 1 ,remender2 );
               		    questions.addAll(hardQuestions);
               		    questions.addAll(mediumQuestions);
                        quiz.setQuestions(questions);
                     //   StudentQuiz studentQuiz = new StudentQuiz(user, quiz);
                        studentQuizRepository.save(studentQuiz);
                        FancyQuiz fancyquiz=new FancyQuiz();
                        return new ResponseEntity<>(fancyquiz.toFancyQuizMapping(quiz, user.isTeacher()),
                             HttpStatus.OK);
                	}
        	 }
        
        }
       
        }
    }
    
    @GetMapping(Mapping.STUDENT_QUIZ_SCORE)
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

        if (!quiz.getSection().getCourse().getLearners().contains(user))
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
    
//    @GetMapping(Mapping.ACCOMPLISHED_COURSES)
//    public ResponseEntity<?> AccomplishedCourses(@RequestParam(Param.ACCESS_TOKEN) String token){
//        User user = userRepository.findByToken(token);
//
//        if (user == null) {
//            return new ResponseEntity<>("User is not present.",
//                    HttpStatus.UNAUTHORIZED);
//        }
//        if (!jwtTokenChecker.validateToken(token)) {
//            user.setToken("");
//            userRepository.save(user);
//            return new ResponseEntity<>("session expired.",
//                    HttpStatus.UNAUTHORIZED);
//        }
//        List <Course> accomplishedCourses= new LinkedList<>();
//        List<StudentCourse> Courses=studentCourseRepository.findByUser(user);
//        for(int i=0;i<Courses.size();i++) {
//        	boolean accomplished=true;
//        	List<Section> sections=sectionRepository.findByCourse(Courses.get(i).getCourse());
//        	for(int j=0;j<sections.size();j++) {
////        		if(accomplished=false) {
////        			break;
////        		}
//        		List<Lecture> quizes= lectureRepository.findBySectionAndIsQuiz(sections.get(j),true);
//
//        		for(int k=0;k<quizes.size();k++) {
//
//        			Quiz quiz=quizRepository.findByLecture(quizes.get(k));
//        			StudentQuiz studentquiz=studentQuizRepository.findByUserAndQuiz(user, quiz);
//        		    if (studentquiz==null || studentquiz.getPassed()==false) {
//        		    	 accomplished=false;
//        		    	 j=sections.size();
//
//        		    	break;
//        		    	}
//        		    else if(j==sections.size()-1){
//        		    	accomplishedCourses.add(Courses.get(i).getCourse());
//
//        		    }
//
//        		}
//        	}
//        }
//        FancyCourse fancyCourse = new FancyCourse();
//               return new ResponseEntity<>(fancyCourse.toFancyCourseListMapping(accomplishedCourses, user),
//                HttpStatus.OK);
//    }
//
//
//}


    @GetMapping(Mapping.ACCOMPLISHED_COURSES)
    public ResponseEntity<?> AccomplishedCourses(@RequestParam(Param.ACCESS_TOKEN) String token){
        User user = userRepository.findByToken(token);

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
        List <Course> accomplishedCourses= new LinkedList<>();
        List<StudentCourse> Courses=studentCourseRepository.findByUser(user);
        for(int i=0;i<Courses.size();i++) {
            boolean accomplished=true;
            List<Section> sections=sectionRepository.findByCourse(Courses.get(i).getCourse());
            for(int j=0;j<sections.size();j++) {
                Quiz quiz = sections.get(j).getQuiz();
                StudentQuiz studentquiz = studentQuizRepository.findByUserAndQuiz(user, quiz);
                if (studentquiz==null || studentquiz.getPassed()==false) {
                    accomplished=false;
                    j=sections.size();
                    break;
                }
                else if(j==sections.size()-1){
                    accomplishedCourses.add(Courses.get(i).getCourse());
                }
            }
        }
        FancyCourse fancyCourse = new FancyCourse(studentCourseRepository);
        return new ResponseEntity<>(fancyCourse.toFancyCourseListMapping(accomplishedCourses, user),
                HttpStatus.OK);
    }

    public Float getRank(User student, Course course){
        StudentCourse studentCourse = studentCourseRepository.findByUserAndCourse(student,course);
        if (studentCourse != null)
            return studentCourse.getRank();
        else
            return null;
    }

}


