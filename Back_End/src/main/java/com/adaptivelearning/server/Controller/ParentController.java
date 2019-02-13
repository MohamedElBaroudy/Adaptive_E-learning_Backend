package com.adaptivelearning.server.Controller;

import com.adaptivelearning.server.FancyModel.FancyUser;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.validation.Valid;

@RestController
//@RequestMapping(Mapping.PARENT)
public class ParentController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CourseRepository courseRepository;

    
    @Autowired
    ClassroomRepository classroomRepository;


    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider jwtTokenChecker;


    @PostMapping(Mapping.ADD_CHILD)
    public ResponseEntity<?> addChild(@RequestParam(Param.ACCESS_TOKEN) String token,
                         @Valid @RequestParam(Param.FIRST_NAME) String name,
                         @Valid @RequestParam(Param.DATE_OF_BIRTH) String dob,
                         @Valid @RequestParam(Param.USERNAME) String username,
                         @Valid @RequestParam(value = Param.EMAIL,required = false) String email,
                         @Valid @RequestParam(Param.PASSWORD) String password,
                         @Valid @RequestParam(Param.GENDER) short gender,
                         @Valid @RequestParam(value = Param.GRADE,required = false) String grade){

        User user = userRepository.findByToken(token);

        if(user == null){
        	 return new ResponseEntity<>("FancyUser Is Not Valid",
                     HttpStatus.UNAUTHORIZED);
        }
        if (!jwtTokenChecker.validateToken(token)) {
       	 return new ResponseEntity<>("Session Expired",
                 HttpStatus.UNAUTHORIZED);
        }


        if ((email!=null && !email.isEmpty()&&userRepository.existsByEmail(email))  ||  userRepository.existsByUsername(username)) {
       	 return new ResponseEntity<>("FancyUser, Email or both of them are in use",
                 HttpStatus.CONFLICT);
        }


        if(userRepository.findByFirstNameAndParent(name,user) != null){
          	 return new ResponseEntity<>("Child added before",
                     HttpStatus.CONFLICT);
        }

        // Creating Child account
        final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateOfBirth = LocalDate.parse(dob,dtf);
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        User child = new User(name, user.getLastName(), email, username, password, dateOfBirth, gender,grade);

        user.setParent(true);
        child.setChild(true);

        child.setPassword(passwordEncoder.encode(child.getPassword()));
        child.setParent(user);
        userRepository.save(child);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PostMapping(Mapping.JOIN_CHILD_IN_CLASSROOM)
     public ResponseEntity<?> joinChildIntoClassroom(@RequestParam(Param.ACCESS_TOKEN) String token,
                        @Valid @RequestParam(Param.FIRST_NAME) String childName,
                        @Valid @RequestParam(Param.PASSCODE) String passCode) {

        User user = userRepository.findByToken(token);

        if(user == null){
       	 return new ResponseEntity<>("FancyUser Is Not Valid",HttpStatus.UNAUTHORIZED);
        }
        if (!jwtTokenChecker.validateToken(token)) {
          	 return new ResponseEntity<>("Session Expired",HttpStatus.UNAUTHORIZED);
        }

        User enrollChild = userRepository.findByFirstNameAndParent(childName,user);
        Classroom classroom = classroomRepository.findByPassCode(passCode);

        if (enrollChild == null) {
          	 return new ResponseEntity<>("Child Is Not Found",HttpStatus.NOT_FOUND);
        }

        if (classroom == null ) {
         	 return new ResponseEntity<>("FancyClassroom Is Not Found",
                     HttpStatus.NOT_FOUND);
        }
        if (classroom.getStudents().contains(enrollChild)) {
        	 return new ResponseEntity<>("this child already enrolled to this classroom ",
                    HttpStatus.FORBIDDEN);
        }
        classroom.getStudents().add(enrollChild);
        classroomRepository.save(classroom);
       return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PostMapping(Mapping.ENROLL_CHILD_IN_COURSE)
    public ResponseEntity<?> enrollChildIntoCourse(@RequestParam(Param.ACCESS_TOKEN) String token,
                       @Valid @RequestParam(Param.FIRST_NAME) String childName,
                       @Valid @RequestParam(Param.COURSE_ID) int courseId) {

       User user = userRepository.findByToken(token);
       Course course = courseRepository.findByCourseId(courseId);
       User enrollChild = userRepository.findByFirstNameAndParent(childName,user);
       
       if(user == null){
      	 return new ResponseEntity<>("User Is Not Valid",HttpStatus.UNAUTHORIZED);
       }
       if (!jwtTokenChecker.validateToken(token)) {
         	 return new ResponseEntity<>("Session Expired",HttpStatus.UNAUTHORIZED);
       }

      
       if (enrollChild == null) {
         	 return new ResponseEntity<>("Child Is Not Found",HttpStatus.NOT_FOUND);
       }

       if (course == null ) {
        	 return new ResponseEntity<>("course Is Not Found",
                    HttpStatus.NOT_FOUND);
       }
       if(course.getLearners().contains(enrollChild)) {
      	 return new ResponseEntity<>("Already Enrolled ",
                   HttpStatus.FORBIDDEN); 
      }
     
      course.getLearners().add(enrollChild);
      course.increamentStudents();
      courseRepository.save(course);
      return new ResponseEntity<>(HttpStatus.OK);
   }


    @GetMapping(Mapping.CHILDREN)
    ResponseEntity<?> retrieveChildren(@RequestParam(Param.ACCESS_TOKEN) String token) {

        User user = userRepository.findByToken(token);

        if(user == null){
          	 return new ResponseEntity<>("User Is Not Valid",HttpStatus.UNAUTHORIZED);
        }
        if (!jwtTokenChecker.validateToken(token)) {
         	 return new ResponseEntity<>("Session Expired",HttpStatus.UNAUTHORIZED);
        }

        FancyUser fancyUser = new FancyUser();
        return new ResponseEntity<>(fancyUser.toFancyUserListMapping(user.getChildren()),
                HttpStatus.OK);
    }

    @GetMapping(Mapping.CHILD)
    ResponseEntity<?> retrieveChild(@RequestParam(Param.ACCESS_TOKEN) String token,
                                    @Valid @RequestParam(Param.USER_ID) Integer childId) {

        User user = userRepository.findByToken(token);

        if(user == null){
            return new ResponseEntity<>("User Is Not Valid",HttpStatus.UNAUTHORIZED);
        }
        if (!jwtTokenChecker.validateToken(token)) {
            return new ResponseEntity<>("Session Expired",HttpStatus.UNAUTHORIZED);
        }

        User child = userRepository.findByUserId(childId);

        if (child == null)
            return new ResponseEntity<>("Child is not found",
                    HttpStatus.NOT_FOUND);
        if (!user.equals(child.getParent()))
            return new ResponseEntity<>("User is not your child",
                    HttpStatus.FORBIDDEN);

        FancyUser fancyUserChild = new FancyUser();
        fancyUserChild.toFancyUserMapper(child);

        return new ResponseEntity<>(fancyUserChild ,HttpStatus.OK);
    }


    @PostMapping(Mapping.PARENT_RATE_COURSE)
    public ResponseEntity<?> parentRateCourse(@RequestParam(Param.ACCESS_TOKEN) String token,
                                               @Valid @RequestParam(Param.COURSE_ID) int courseId,
                                               @Valid @RequestParam(Param.FIRST_NAME) String childName,
                                               @Valid @RequestParam(Param.Rate) short parentRate){

        User user = userRepository.findByToken(token);
        Course course = courseRepository.findByCourseId(courseId);
        User child = userRepository.findByFirstNameAndParent(childName,user);

        if(user == null){
            return new ResponseEntity<>("user is not present ",
                    HttpStatus.UNAUTHORIZED);
        }
        if (!jwtTokenChecker.validateToken(token)) {
            return new ResponseEntity<>("invalid token ",
                    HttpStatus.UNAUTHORIZED);
        }

        if(course == null){
            return new ResponseEntity<>("course with this id is not found ",
                    HttpStatus.NOT_FOUND);
        }

        if(user.getParent() != null){
            return new ResponseEntity<>("user is child it's not allowed ",
                    HttpStatus.FORBIDDEN);
        }

        if(course.getPublisher().getUserId()==user.getUserId()) {
            return new ResponseEntity<>("course publisher can't rate his courses",
                    HttpStatus.FORBIDDEN);
        }

        if(child == null){
            return new ResponseEntity<>("child with this name is not found ",
                    HttpStatus.NOT_FOUND);
        }

        if(!course.getLearners().contains(child)){
            return new ResponseEntity<>("Your child isn't enrolled in this course",
                    HttpStatus.FORBIDDEN);
        }
        if(course.getRaters().contains(user)){
            return new ResponseEntity<>("User cannot rate again",
                    HttpStatus.FORBIDDEN);
        }

        /*

        validate that the child has finished the course

         */

        int old_raters_num = course.getNumberOfRaters();
        float new_rate = ((course.getRate() * old_raters_num) + parentRate)/(old_raters_num + 1);
        course.increamentRaters();
        course.setRate(new_rate);
        course.getRaters().add(user);
        courseRepository.save(course);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
