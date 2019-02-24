package com.adaptivelearning.server.Controller;

import com.adaptivelearning.server.FancyModel.FancyClassroom;
import com.adaptivelearning.server.FancyModel.FancyCourse;
import com.adaptivelearning.server.Model.Classroom;
import com.adaptivelearning.server.Model.Course;
import com.adaptivelearning.server.Model.User;
import com.adaptivelearning.server.Repository.ClassroomRepository;
import com.adaptivelearning.server.Repository.CourseRepository;
import com.adaptivelearning.server.Repository.UserRepository;
import com.adaptivelearning.server.Security.JwtTokenProvider;
import com.adaptivelearning.server.constants.Mapping;
import com.adaptivelearning.server.constants.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
//@RequestMapping(Mapping.STUDENT)
public class StudentController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CourseRepository courseRepository;
    
    @Autowired
    ClassroomRepository classroomRepository;

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
    
}
