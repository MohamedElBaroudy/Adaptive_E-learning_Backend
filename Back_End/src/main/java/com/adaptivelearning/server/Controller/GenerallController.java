package com.adaptivelearning.server.Controller;

import com.adaptivelearning.server.FancyModel.FancyCategory;
import com.adaptivelearning.server.FancyModel.FancyCourse;
import com.adaptivelearning.server.Model.Category;
import com.adaptivelearning.server.Model.Course;
import com.adaptivelearning.server.Model.User;
import com.adaptivelearning.server.Repository.CategoryRepository;
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
    CategoryRepository categoryRepository;
    
    @Autowired
    JwtTokenProvider jwtTokenChecker;



    @GetMapping(Mapping.NEW_COURSES)
    public ResponseEntity<?> retrieveNewestCourses(){
        List<Course> courses = courseRepository.findNewestCourses();
        FancyCourse fancyCourse = new FancyCourse();
        if(courses.size()>10)
            return new ResponseEntity<>(fancyCourse.toFancyCourseListMapping(
                    courses.subList(0,10), null),
                    HttpStatus.OK);
        else
            return new ResponseEntity<>(fancyCourse.toFancyCourseListMapping(
                    courses, null),
                    HttpStatus.OK);
    }

    @GetMapping(Mapping.HOT_COURSES)
    public ResponseEntity<?> retrieveHotestCourses(){
        List<Course> courses = courseRepository.findHotestCourses();
        FancyCourse fancyCourse = new FancyCourse();
        if(courses.size()>10)
            return new ResponseEntity<>(fancyCourse.toFancyCourseListMapping(
                    courses.subList(0,10), null),
                    HttpStatus.OK);
        else
            return new ResponseEntity<>(fancyCourse.toFancyCourseListMapping(
                    courses, null),
                    HttpStatus.OK);
    }

    @GetMapping(Mapping.TOP_RATED_COURSES)
    public ResponseEntity<?> retrieveTopRatedCourses(){
        List<Course> courses = courseRepository.findTopRatedCourses();
        FancyCourse fancyCourse = new FancyCourse();
        if(courses.size()>10)
            return new ResponseEntity<>(fancyCourse.toFancyCourseListMapping(
                    courses.subList(0,10), null),
                    HttpStatus.OK);
        else
            return new ResponseEntity<>(fancyCourse.toFancyCourseListMapping(
                    courses, null),
                    HttpStatus.OK);
    }

    @GetMapping(Mapping.CATEGORIES)
    public ResponseEntity<?> retrieveCategories(){
        List<Category> categories = categoryRepository.findByIsApproved(true);
        FancyCategory fancyCategory = new FancyCategory();

        return new ResponseEntity<>(fancyCategory.toFancyCategoryListMapping(categories),
                HttpStatus.OK);
    }
    
    @GetMapping(Mapping.CATEGORY_COURSES)
    public ResponseEntity<?> retrieveCategoryCourses( @Valid @RequestParam(Param.CATEGORY_ID) Integer categoryid){

        Category category = categoryRepository.findByCategoryId(categoryid);

        if (category == null)
            return new ResponseEntity<>("Not found Category",
                    HttpStatus.NOT_FOUND);

        if (!category.isApproved())
            return new ResponseEntity<>("Not approved Category yet",
                    HttpStatus.BAD_REQUEST);
    	//get public courses
    	List<Course> courses = courseRepository.findByCategoryAndIsPublic(category,true); 
    	
        FancyCourse fancyCourse=new FancyCourse();
        
            return new ResponseEntity<>(fancyCourse.toFancyCourseListMapping(courses, null),
                    HttpStatus.OK);
    }
}
