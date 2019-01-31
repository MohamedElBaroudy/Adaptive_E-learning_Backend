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
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping(Mapping.TEACHER)
public class TeacherController {

    @Autowired
    ClassroomRepository classroomRepository;
   
    @Autowired
    CourseRepository courseRepository;

    
    @Autowired
    UserRepository userRepository;

//    @Autowired
//    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider jwtTokenChecker;


    @PostMapping(Mapping.CLASSROOMS)
    ResponseEntity<?> createClassroom(@RequestParam(Param.ACCESSTOKEN) String token,
                     @Valid @RequestParam(Param.CLASSROOMNAME) String classroomName,
                     @Valid @RequestParam(Param.PASSCODE) String passcode) {

        User user = userRepository.findByToken(token);

        if(user == null){
        	 return new ResponseEntity<>(" user is not present ",
                     HttpStatus.UNAUTHORIZED);
        }
        if (!jwtTokenChecker.validateToken(token)) {
        	 return new ResponseEntity<>("invalid token ",
                     HttpStatus.UNAUTHORIZED);
        }

        if(user.getParent() != null){
        	 return new ResponseEntity<>("user is child it's not allowed ",
                     HttpStatus.FORBIDDEN);
        }

        Classroom classRoom = new Classroom(classroomName, passcode);
        classRoom.setCreator(user);
//        classRoom.setPassCode(passwordEncoder.encode(classRoom.getPassCode()));
        classroomRepository.save(classRoom);
        
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @GetMapping(Mapping.CLASSROOMS)
    ResponseEntity<?>retrieveCreatedClassrooms(@RequestParam(Param.ACCESSTOKEN) String token) {

        User user = userRepository.findByToken(token);

        if(user == null){
        	 return new ResponseEntity<>(" user is not present ",
                     HttpStatus.UNAUTHORIZED);
        }
        if (!jwtTokenChecker.validateToken(token)) {
        	 return new ResponseEntity<>(" invalid token ",
                     HttpStatus.UNAUTHORIZED);
        }

       return new ResponseEntity<>(user.getClassrooms(),
                HttpStatus.OK);
    }


    @PutMapping(Mapping.CLASSROOM)
    ResponseEntity<?> updateClassroomInfo(@RequestParam(Param.ACCESSTOKEN) String token,
                                  @Valid @RequestParam(value = Param.CLASSROOMNAME,required = false) String classroomName,
                                  @Valid @RequestParam(value = Param.PASSCODE,required = false) String passcode) {
        User user = userRepository.findByToken(token);

        if(user == null){
        	 return new ResponseEntity<>(" user is not present ",
                     HttpStatus.UNAUTHORIZED);
        }
        if (!jwtTokenChecker.validateToken(token)) {
        	 return new ResponseEntity<>(" invalid token ",
                     HttpStatus.UNAUTHORIZED);
        }

        Classroom classroom = classroomRepository.findByClassroomName(classroomName);

        if (classroom == null) {
        	 return new ResponseEntity<>(" classroom is not present ",
                     HttpStatus.NOT_FOUND);
        }

        if (classroom.getCreator().getUserId()!=
                (user.getUserId())) {
        	return new ResponseEntity<>("Not Allowed you are not a teacher or this is not your classroom to update",
                    HttpStatus.FORBIDDEN);
        }

//        classroom.setPassCode(passwordEncoder.encode(passcode));
        classroom.setPassCode(passcode);
        classroomRepository.save(classroom);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @DeleteMapping(Mapping.CLASSROOM)
    ResponseEntity<?> deleteClassroom(@RequestParam(Param.ACCESSTOKEN) String token,
                @Valid @RequestParam(Param.CLASSROOMNAME) String classroomName) {

        User user = userRepository.findByToken(token);

        if(user == null){
            return new ResponseEntity<>(" user is not present ",
                    HttpStatus.UNAUTHORIZED);
        }
        if (!jwtTokenChecker.validateToken(token)) {
            return new ResponseEntity<>(" invalid token ",
                    HttpStatus.UNAUTHORIZED);
        }

        Classroom classroom = classroomRepository.findByClassroomName(classroomName);

        if (classroom == null) {
            return new ResponseEntity<>(" classroom is not present ",
                    HttpStatus.NOT_FOUND);
        }

        if(classroom.getCreator().getUserId() != user.getUserId()) {
            return new ResponseEntity<>("Not Allowed you are not a teacher or this is not your classroom to update",
                    HttpStatus.FORBIDDEN);
        }

        classroomRepository.deleteById(classroom.getClassroomId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    
    ////////////////////////////////////////////////////Courses Functions/////////////////////////////////////////////////////////////////////////
    
    @PostMapping(Mapping.COURSES)
    ResponseEntity<?> createCourse(@RequestParam(Param.ACCESSTOKEN) String token,
                     @Valid @RequestParam(Param.Title) String courseTitle,
                     @Valid @RequestParam(Param.Detailed_title) String detailed_title,
                     @Valid @RequestParam(Param.Description) String description,
                     @Valid @RequestParam(Param.Level) short level) {

        User user = userRepository.findByToken(token);

        if(user == null){
        	  return new ResponseEntity<>("user is not present",
                      HttpStatus.UNAUTHORIZED);
        }
        if (!jwtTokenChecker.validateToken(token)) {
        	  return new ResponseEntity<>("invalid token",
                      HttpStatus.UNAUTHORIZED);
        }

        if(user.getParent() != null){
        	 return new ResponseEntity<>("user is child it's not allowed ",
                     HttpStatus.FORBIDDEN);
        }

        Course course=new Course(courseTitle, detailed_title, description, true, level);
        course.setPublisher(user);
        courseRepository.save(course);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PostMapping(Mapping.CLASSROOM_COURSES)
    ResponseEntity<?> createClassroomCourse (@RequestParam(Param.ACCESSTOKEN) String token,
    	           	 @Valid @RequestParam(Param.CLASSROOM_ID) int classroomId,
                     @Valid @RequestParam(Param.Title) String courseTitle,
                     @Valid @RequestParam(Param.Detailed_title) String detailed_title,
                     @Valid @RequestParam(Param.Description) String description,
                     @Valid @RequestParam(Param.Level) short level) {

        User user = userRepository.findByToken(token);
        Classroom classroom=classroomRepository.findByClassroomId(classroomId);
        if(user == null){
        	 return new ResponseEntity<>("user is not present",
                     HttpStatus.UNAUTHORIZED);
        }
        if (!jwtTokenChecker.validateToken(token)) {
            return new ResponseEntity<>("invalid token",
                    HttpStatus.UNAUTHORIZED);
        }

        if(classroom == null){
      	  return new ResponseEntity<>("classroom is not present",
                  HttpStatus.NOT_FOUND);
        }

        if(user.getParent() != null){
        	 return new ResponseEntity<>("user is child it's not allowed ",
                     HttpStatus.FORBIDDEN);
        }
        
        if(user.getUserId()!= classroom.getCreator().getUserId()) {
        	 return new ResponseEntity<>("this classroom not belongs to this user",
                     HttpStatus.FORBIDDEN);
        }

        Course course=new Course(courseTitle, detailed_title, description, false , level);
        course.setPublisher(user);
        
        course.getClassrooms().add(classroom);
//        classroom.getCourses().add(course); // the mapping will do it automatically
        
        courseRepository.save(course);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    
    
    
    
    
    
    
    
}
