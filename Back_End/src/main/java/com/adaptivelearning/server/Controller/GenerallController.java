package com.adaptivelearning.server.Controller;

import com.adaptivelearning.server.FancyModel.FancyCourse;
import com.adaptivelearning.server.Model.Course;
import com.adaptivelearning.server.Model.User;
import com.adaptivelearning.server.Repository.CourseRepository;
import com.adaptivelearning.server.Repository.UserRepository;
import com.adaptivelearning.server.Security.JwtTokenProvider;
import com.adaptivelearning.server.constants.Mapping;
import com.adaptivelearning.server.constants.Param;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.Valid;

@RestController
public class GenerallController {

    @Autowired
    CourseRepository courseRepository;
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    JwtTokenProvider jwtTokenChecker;



    @GetMapping(Mapping.NEW_COURSES)
    public ResponseEntity<?> retrieveNewestCourses(){
        List<Course> courses = courseRepository.findNewestCourses();
        FancyCourse fancyCourse = new FancyCourse();
        if(courses.size()>20)
            return new ResponseEntity<>(fancyCourse.toFancyCourseListMapping(
                    courses.subList(0,20)),
                    HttpStatus.OK);
        else
            return new ResponseEntity<>(fancyCourse.toFancyCourseListMapping(
                    courses),
                    HttpStatus.OK);
    }

    @GetMapping(Mapping.HOT_COURSES)
    public ResponseEntity<?> retrieveHotestCourses(){
        List<Course> courses = courseRepository.findHotestCourses();
        FancyCourse fancyCourse = new FancyCourse();
        if(courses.size()>20)
            return new ResponseEntity<>(fancyCourse.toFancyCourseListMapping(
                    courses.subList(0,20)),
                    HttpStatus.OK);
        else
            return new ResponseEntity<>(fancyCourse.toFancyCourseListMapping(
                    courses),
                    HttpStatus.OK);
    }

    @GetMapping(Mapping.TOP_RATED_COURSES)
    public ResponseEntity<?> retrieveTopRatedCourses(){
        List<Course> courses = courseRepository.findTopRatedCourses();
        FancyCourse fancyCourse = new FancyCourse();
        if(courses.size()>20)
            return new ResponseEntity<>(fancyCourse.toFancyCourseListMapping(
                    courses.subList(0,20)),
                    HttpStatus.OK);
        else
            return new ResponseEntity<>(fancyCourse.toFancyCourseListMapping(
                    courses),
                    HttpStatus.OK);
    }
    
    @GetMapping(Mapping.CATEGORY_COURSES)
    public ResponseEntity<?> retrieveCategoryCourses( @Valid @RequestParam(Param.CATEGORY) String category){
        
    	//get public courses
    	List<Course> courses = courseRepository.findByCategoryAndIsPublic(category,true); 
    	
        FancyCourse fancyCourse=new FancyCourse();
        
            return new ResponseEntity<>(fancyCourse.toFancyCourseListMapping(courses),
                    HttpStatus.OK);
    }
}
