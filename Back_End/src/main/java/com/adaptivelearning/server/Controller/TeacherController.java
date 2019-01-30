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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientResponseException;

import javax.servlet.http.HttpServletResponse;
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
    ResponseEntity<?> create(@RequestParam(Param.ACCESSTOKEN) String token,
                     @Valid @RequestParam(Param.CLASSROOMNAME) String classroomName,
                     @Valid @RequestParam(Param.PASSCODE) String passcode,
                     HttpServletResponse response) {

        User user = userRepository.findByToken(token);

        if(user == null){
        	 return new ResponseEntity<>(" user is not present ",
                     HttpStatus.BAD_REQUEST); 
        }
        if (!jwtTokenChecker.validateToken(token)) {
        	 return new ResponseEntity<>("invalid token ",
                     HttpStatus.BAD_REQUEST); 
        }

        if(user.getParent() != null){
        	 return new ResponseEntity<>("user is child it's not allowed ",
                     HttpStatus.BAD_REQUEST); 
        }

        Classroom classRoom = new Classroom(classroomName, passcode);
        classRoom.setCreator(user);
//        classRoom.setPassCode(passwordEncoder.encode(classRoom.getPassCode()));
        classroomRepository.save(classRoom);
        
        return new ResponseEntity<>(classRoom,
                HttpStatus.OK); 
    }


    @GetMapping(Mapping.CLASSROOMS)
    ResponseEntity<?>retrieveCreatedClassrooms(@RequestParam(Param.ACCESSTOKEN) String token,
                                                  HttpServletResponse response) {

        User user = userRepository.findByToken(token);


        if(user == null){
        	 return new ResponseEntity<>(" user is not present ",
                     HttpStatus.BAD_REQUEST); 
        }
        if (!jwtTokenChecker.validateToken(token)) {
        	 return new ResponseEntity<>(" invalid token ",
                     HttpStatus.BAD_REQUEST); 
        }

       return new ResponseEntity<>(user.getClassrooms(),
                HttpStatus.OK);
    }


    @PutMapping(Mapping.CLASSROOM)
    ResponseEntity<?> updateClassroomInfo(@RequestParam(Param.ACCESSTOKEN) String token,
                                  @Valid @RequestParam(value = Param.CLASSROOMNAME,required = false) String classroomName,
                                  @Valid @RequestParam(value = Param.PASSCODE,required = false) String passcode,
                                  HttpServletResponse response) {
        User user = userRepository.findByToken(token);


        if(user == null){
        	 return new ResponseEntity<>(" user is not present ",
                     HttpStatus.BAD_REQUEST); 
        }
        if (!jwtTokenChecker.validateToken(token)) {
        	 return new ResponseEntity<>(" invalid token ",
                     HttpStatus.BAD_REQUEST); 
        }

        Classroom classroom = classroomRepository.findByClassroomName(classroomName);

        if (classroom == null) {
        	 return new ResponseEntity<>(" classroom is not present ",
                     HttpStatus.BAD_REQUEST); 
        }

        if (classroom.getCreator().getUserId()!=
                (user.getUserId())) {
        	return new ResponseEntity<>("Not Allowed you are not a teacher or this is not your classroom to update",
                    HttpStatus.BAD_REQUEST);            
        }

//        classroom.setPassCode(passwordEncoder.encode(passcode));
        classroom.setPassCode(passcode);
        classroomRepository.save(classroom);
         return new ResponseEntity<>(classroom,
                HttpStatus.OK);
    }


    @DeleteMapping(Mapping.CLASSROOM)
    void delete(@RequestParam(Param.ACCESSTOKEN) String token,
                @Valid @RequestParam(Param.CLASSROOMNAME) String classroomName,
                HttpServletResponse response) {

        User user = userRepository.findByToken(token);


        if(user == null){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
//            throw new RestClientResponseException("Invalid token",
//                    400, "BadRequest", HttpHeaders.EMPTY, null, null);
        }
        if (!jwtTokenChecker.validateToken(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
//            throw new RestClientResponseException("Session expired",
//                    400, "BadRequest", HttpHeaders.EMPTY, null, null);
        }

        Classroom classroom = classroomRepository.findByClassroomName(classroomName);

        if (classroom == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
//            throw new RestClientResponseException("Not found classroom",
//                    404, "NotFound", HttpHeaders.EMPTY, null, null);
        }

        if(classroom.getCreator().getUserId() != user.getUserId()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
//            throw new RestClientResponseException("Only creator of this classroom is allowed",
//                    405, "NotAllowed", HttpHeaders.EMPTY, null, null);
        }

        classroomRepository.deleteById(classroom.getClassroomId());
    }
    
    
    ////////////////////////////////////////////////////Courses Functions/////////////////////////////////////////////////////////////////////////
    
    @PostMapping(Mapping.Course)
    ResponseEntity<?> createCourse(@RequestParam(Param.ACCESSTOKEN) String token,
                     @Valid @RequestParam(Param.Title) String courseTitle,
                     @Valid @RequestParam(Param.Detailed_title) String detailed_title,
                     @Valid @RequestParam(Param.Description) String description,
                     @Valid @RequestParam(Param.Level) short level,
                     HttpServletResponse response) {

        User user = userRepository.findByToken(token);

        if(user == null){
        	  return new ResponseEntity<>("user is not present",
                      HttpStatus.BAD_REQUEST);
        }
        if (!jwtTokenChecker.validateToken(token)) {
        	  return new ResponseEntity<>("invalid token",
                      HttpStatus.BAD_REQUEST); 
        }

        if(user.getParent() != null){
        	 return new ResponseEntity<>("user is child it's not allowed ",
                     HttpStatus.BAD_REQUEST); 
        }

        Course course=new Course(courseTitle, detailed_title, description, true, level);
        course.setPublisher(user);
        courseRepository.save(course);

        return new ResponseEntity<>(course,HttpStatus.OK); 
    }


    @PostMapping(Mapping.Classroom_course)
    ResponseEntity<?> createClassroom_Course (@RequestParam(Param.ACCESSTOKEN) String token,
    	           	 @Valid @RequestParam(Param.CLASSROOM_ID) int classroomId,
                     @Valid @RequestParam(Param.Title) String courseTitle,
                     @Valid @RequestParam(Param.Detailed_title) String detailed_title,
                     @Valid @RequestParam(Param.Description) String description,
                     @Valid @RequestParam(Param.Level) short level,
                     HttpServletResponse response) {

        User user = userRepository.findByToken(token);
        Classroom classroom=classroomRepository.findByClassroomId(classroomId);
        if(user == null){
        	 return new ResponseEntity<>("user is not present",
                     HttpStatus.BAD_REQUEST); 
        }
        if(classroom == null){
      	  return new ResponseEntity<>("classroom is not present",
                  HttpStatus.BAD_REQUEST);
        }
        if (!jwtTokenChecker.validateToken(token)) {
        	 return new ResponseEntity<>("invalid token",
                     HttpStatus.BAD_REQUEST);
        }

        if(user.getParent() != null){
        	 return new ResponseEntity<>("user is child it's not allowed ",
                     HttpStatus.BAD_REQUEST); 
        }
        
        if(user.getUserId()!= classroom.getCreator().getUserId()) {
        	 return new ResponseEntity<>("this classroom not belongs to this user",
                     HttpStatus.BAD_REQUEST); 
        }

        Course course=new Course(courseTitle, detailed_title, description, false , level);
        course.setPublisher(user);
        
        course.getClassrooms().add(classroom);
        classroom.getCourses().add(course);
        
        courseRepository.save(course);

        return new ResponseEntity<>(course,
                HttpStatus.OK); 
    }


    
    
    
    
    
    
    
    
}
