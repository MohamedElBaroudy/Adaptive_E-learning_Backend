package com.adaptivelearning.server.Controller;

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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.validation.Valid;

@RestController
@RequestMapping(Mapping.PARENT)
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


    @PostMapping(Mapping.AddChild)
    public ResponseEntity<?> addChild(@RequestParam(Param.ACCESSTOKEN) String token,
                         @Valid @RequestParam(Param.FIRSTNAME) String name,
                         @Valid @RequestParam(Param.DATEOFBIRTH) String dob,
                         @Valid @RequestParam(Param.USERNAME) String username,
                         @Valid @RequestParam(Param.EMAIL) String email,
                         @Valid @RequestParam(Param.PASSWORD) String password,
                         @Valid @RequestParam(Param.GENDRE) short gender) throws ParseException {

        User user = userRepository.findByToken(token);

        if(user == null){
        	 return new ResponseEntity<>("User Is Not Valid",
                     HttpStatus.UNAUTHORIZED);
        }
        if (!jwtTokenChecker.validateToken(token)) {
       	 return new ResponseEntity<>("Session Expired",
                 HttpStatus.UNAUTHORIZED);
        }


        if (userRepository.existsByEmail(email)  ||  userRepository.existsByUsername(username)) {
       	 return new ResponseEntity<>("User, Email or both of them are in use",
                 HttpStatus.CONFLICT);
        }


        if(userRepository.findByFirstNameAndParent(name,user) != null){
          	 return new ResponseEntity<>("Child added before",
                     HttpStatus.CONFLICT);
        }

        // Creating Child account
        DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        Date dateOfBirth = format.parse(dob);
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        User child = new User(name, user.getLastName(), email, username, password, dateOfBirth, gender);

        child.setPassword(passwordEncoder.encode(child.getPassword()));
        child.setParent(user);
        userRepository.save(child);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PostMapping(Mapping.ENROLLCHILD)
     public ResponseEntity<?> joinChildIntoClassroom(@RequestParam(Param.ACCESSTOKEN) String token,
                        @Valid @RequestParam(Param.FIRSTNAME) String childName,
                        @Valid @RequestParam(Param.CLASSROOMNAME) String classroomName,
                        @Valid @RequestParam(Param.PASSCODE) String passCode) {

        User user = userRepository.findByToken(token);

        if(user == null){
       	 return new ResponseEntity<>("User Is Not Valid",HttpStatus.UNAUTHORIZED);
        }
        if (!jwtTokenChecker.validateToken(token)) {
          	 return new ResponseEntity<>("Session Expired",HttpStatus.UNAUTHORIZED);
        }

        User enrollChild = userRepository.findByFirstNameAndParent(childName,user);
        Classroom classroom = classroomRepository.findByClassroomName(classroomName);

        if (enrollChild == null) {
          	 return new ResponseEntity<>("Child Is Not Found",HttpStatus.NOT_FOUND);
        }

        if (classroom == null ) {
         	 return new ResponseEntity<>("Classroom Is Not Found",
                     HttpStatus.NOT_FOUND);
        }
        
       if (!classroom.getPassCode().equals(passCode) ) {

    	   return new ResponseEntity<>("Wrong Passcode or Classroom Is Not Found",
                   HttpStatus.NOT_FOUND);
       }

        classroom.getStudents().add(enrollChild);
        classroomRepository.save(classroom);
       return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PostMapping(Mapping.ENROLLCHILDInCourse)
    public ResponseEntity<?> enrollChildIntoCourse(@RequestParam(Param.ACCESSTOKEN) String token,
                       @Valid @RequestParam(Param.FIRSTNAME) String childName,
                       @Valid @RequestParam(Param.CourseID) int courseId) {

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
       if(courseRepository.existsByLearners(enrollChild)) {
      	 return new ResponseEntity<>("Already Enrolled ",
                   HttpStatus.FORBIDDEN); 
      }
     
      course.getLearners().add(enrollChild);
      
      courseRepository.save(course);
      return new ResponseEntity<>(HttpStatus.OK);
   }


    @GetMapping(Mapping.CHILDREN)
    ResponseEntity<?> retrieveChildren(@RequestParam(Param.ACCESSTOKEN) String token) {

        User user = userRepository.findByToken(token);

        if(user == null){
          	 return new ResponseEntity<>("User Is Not Valid",HttpStatus.UNAUTHORIZED);
        }
        if (!jwtTokenChecker.validateToken(token)) {
         	 return new ResponseEntity<>("Session Expired",HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(user.getChildren() ,HttpStatus.OK);
    }

    @GetMapping(Mapping.CHILD)
    ResponseEntity<?> retrieveChild(@RequestParam(Param.ACCESSTOKEN) String token,
                                    @Valid @RequestParam(Param.FIRSTNAME) String childName) {

        User user = userRepository.findByToken(token);

        if(user == null){
            return new ResponseEntity<>("User Is Not Valid",HttpStatus.UNAUTHORIZED);
        }
        if (!jwtTokenChecker.validateToken(token)) {
            return new ResponseEntity<>("Session Expired",HttpStatus.UNAUTHORIZED);
        }

        User child = userRepository.findByFirstNameAndParent(childName,user);

        if (child == null)
            return new ResponseEntity<>("Child is not found",
                    HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(child ,HttpStatus.OK);
    }
}