package com.adaptivelearning.server.Controller;

import com.adaptivelearning.server.Model.Classroom;
import com.adaptivelearning.server.Model.User;
import com.adaptivelearning.server.Repository.ClassroomRepository;
import com.adaptivelearning.server.Repository.UserRepository;

import com.adaptivelearning.server.Security.JwtTokenProvider;
import com.adaptivelearning.server.constants.Mapping;
import com.adaptivelearning.server.constants.Param;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


@RestController
@RequestMapping(Mapping.AUTH)
public class ClassRoomController {

    @Autowired
    ClassroomRepository classroomRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider jwtTokenChecker;



    @GetMapping(Mapping.CLASSROOM)
    ResponseEntity<?> findById(@RequestParam(Param.ACCESSTOKEN) String token,
                       @Valid @RequestParam(Param.CLASSROOM_ID) Integer classroomId) {

        User user = userRepository.findByToken(token);


        if(user == null){
       	 return new ResponseEntity<>("User Is Not Valid",HttpStatus.BAD_REQUEST);
        }
        if (!jwtTokenChecker.validateToken(token)) {
          	 return new ResponseEntity<>("Session Expired",HttpStatus.BAD_REQUEST);
        }

        Classroom classroom = classroomRepository.findByClassroomId(classroomId);

        if (classroom == null ) {
       	 return new ResponseEntity<>("Classroom Is Not Found   ",
                 HttpStatus.NOT_FOUND);
      }

        if (user.getUserId()!=
                classroom.getCreator().getUserId() || !classroom.getStudents().contains(user)) {
          	 return new ResponseEntity<>("Sorry You Are Not Teacher Nor Student In This Classroom ",
                     HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(classroom ,HttpStatus.OK);
    }
}
