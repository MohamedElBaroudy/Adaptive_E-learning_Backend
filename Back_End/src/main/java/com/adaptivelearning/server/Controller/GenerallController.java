package com.adaptivelearning.server.Controller;

import com.adaptivelearning.server.Model.Course;
import com.adaptivelearning.server.Repository.CourseRepository;
import com.adaptivelearning.server.constants.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GenerallController {

    @Autowired
    CourseRepository courseRepository;

    @GetMapping(Mapping.NEW_COURSES)
    public ResponseEntity<?> retrieveNewestCourses(){
        List<Course> courses = courseRepository.findNewestCourses();
        if(courses.size()>20)
            return new ResponseEntity<>(courses.subList(0,20),
                    HttpStatus.OK);
        else
            return new ResponseEntity<>(courses,
                    HttpStatus.OK);
    }

    @GetMapping(Mapping.HOT_COURSES)
    public ResponseEntity<?> retrieveHotestCourses(){
        List<Course> courses = courseRepository.findHotestCourses();
        if(courses.size()>20)
            return new ResponseEntity<>(courses.subList(0,20),
                    HttpStatus.OK);
        else
            return new ResponseEntity<>(courses,
                    HttpStatus.OK);
    }
}
