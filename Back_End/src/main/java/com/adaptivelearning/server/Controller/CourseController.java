package com.adaptivelearning.server.Controller;

import com.adaptivelearning.server.FancyModel.FancyCourse;
import com.adaptivelearning.server.Model.Course;
import com.adaptivelearning.server.Model.User;
import com.adaptivelearning.server.Repository.CourseRepository;
import com.adaptivelearning.server.Repository.UserRepository;
import com.adaptivelearning.server.Security.JwtTokenProvider;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.adaptivelearning.server.constants.Mapping;
import com.adaptivelearning.server.constants.Param;



@RestController
public class CourseController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    JwtTokenProvider jwtTokenChecker;

    @GetMapping(Mapping.COURSE)
    public ResponseEntity<?> retrieveEnrolledCourses(@RequestParam(Param.ACCESS_TOKEN) String token,
    		                                  @Valid @RequestParam(Param.COURSE_ID) int courseId) {

        User user = userRepository.findByToken(token);
        Course course = courseRepository.findByCourseId(courseId);

        if(user == null){
         	 return new ResponseEntity<>("User Is Not Valid",HttpStatus.UNAUTHORIZED);
        }
        
        if (!jwtTokenChecker.validateToken(token)) {
       	 return new ResponseEntity<>("Session Expired",HttpStatus.UNAUTHORIZED);
        }
        
        if(course == null){
            return new ResponseEntity<>("course with this id is not found ",
                    HttpStatus.NOT_FOUND);
        }

        
        FancyCourse fancyCourse = new FancyCourse();
        return new ResponseEntity<>(fancyCourse.toFancyCourseMapping(course),
                HttpStatus.OK);
    }

    @PostMapping(Mapping.SAVED_COURSES)
    public ResponseEntity<?> saveCourse(@RequestParam(Param.ACCESS_TOKEN) String token,
                                        @Valid @RequestParam(Param.COURSE_ID) int courseId) {

        User user = userRepository.findByToken(token);
        Course course=courseRepository.findByCourseId(courseId);

        if(user == null){
            return new ResponseEntity<>("User is not present ",
                    HttpStatus.UNAUTHORIZED);
        }
        if (!jwtTokenChecker.validateToken(token)) {
            return new ResponseEntity<>("Invalid token ",
                    HttpStatus.UNAUTHORIZED);
        }
        if(course == null){
            return new ResponseEntity<>(" course with this id is not found ",
                    HttpStatus.NOT_FOUND);
        }

        /*if(user.getParent() != null){
        	 return new ResponseEntity<>("user is child it's not allowed ",
                     HttpStatus.FORBIDDEN);
        } */ // assuming that the child can save course

        if(course.getPublisher().getUserId()==user.getUserId()) {
            return new ResponseEntity<>("course publisher can't save his courses",
                    HttpStatus.FORBIDDEN);
        }
        if(user.getSavedCourses().contains(course)) {
            return new ResponseEntity<>("Already Saved ",
                    HttpStatus.FORBIDDEN);
        }
        course.getSavedBy().add(user);

        courseRepository.save(course);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(Mapping.SAVED_COURSES)
    public ResponseEntity<?> retrieveSavedCourses(@RequestParam(Param.ACCESS_TOKEN) String token) {

        User user = userRepository.findByToken(token);

        if(user == null){
            return new ResponseEntity<>("User Is Not Valid",
                    HttpStatus.UNAUTHORIZED);
        }
        if (!jwtTokenChecker.validateToken(token)) {
            return new ResponseEntity<>("Session Expired",
                    HttpStatus.UNAUTHORIZED);
        }
        FancyCourse fancyCourse = new FancyCourse();
        return new ResponseEntity<>(fancyCourse.toFancyCourseListMapping(user.getSavedCourses()),
                HttpStatus.OK);
    }
}
