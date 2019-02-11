package com.adaptivelearning.server.Controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.adaptivelearning.server.FancyModel.FancyClassroom;
import com.adaptivelearning.server.FancyModel.FancyCourse;
import com.adaptivelearning.server.Model.Classroom;
import com.adaptivelearning.server.Model.Course;
import com.adaptivelearning.server.Model.User;
import com.adaptivelearning.server.Repository.ClassroomRepository;
import com.adaptivelearning.server.Repository.UserRepository;
import com.adaptivelearning.server.Security.JwtTokenProvider;
import com.adaptivelearning.server.constants.Mapping;
import com.adaptivelearning.server.constants.Param;


@RestController
public class ClassRoomController {

    @Autowired
    ClassroomRepository classroomRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtTokenProvider jwtTokenChecker;

    @GetMapping(Mapping.SHOW_CLASSROOM)
    public ResponseEntity<?> retrieveEnrolledCourses(@RequestParam(Param.ACCESS_TOKEN) String token,
    		                                  @Valid @RequestParam(Param.CLASSROOM_ID) int classroomId) {

        User user = userRepository.findByToken(token);
        Classroom classroom = classroomRepository.findByClassroomId(classroomId);

        if(user == null){
         	 return new ResponseEntity<>("FancyUser Is Not Valid",HttpStatus.UNAUTHORIZED);
        }

//        if (!jwtTokenChecker.validateToken(token)) {
//        	 return new ResponseEntity<>("Session Expired",HttpStatus.UNAUTHORIZED);
//        }
        if(classroom == null){
            return new ResponseEntity<>(" classroom with this id is not found ",
                    HttpStatus.NOT_FOUND);
        }
        if(classroom.getCreator()!=user && !classroomRepository.existsByStudents(user)) {
        	 return new ResponseEntity<>("you are not allowed to see this classroom",
        			 HttpStatus.UNAUTHORIZED);
        }
        

        FancyClassroom fancyClassroom = new FancyClassroom();
        return new ResponseEntity<>(fancyClassroom.toFancyClassroomMapping(classroom),
                HttpStatus.OK);
    }


}
