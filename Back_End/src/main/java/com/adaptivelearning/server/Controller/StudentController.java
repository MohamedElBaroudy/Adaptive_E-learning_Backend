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
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping(Mapping.STUDENT)
public class StudentController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ClassroomRepository classroomRepository;

    @Autowired
    JwtTokenProvider jwtTokenChecker;


    @SuppressWarnings("unchecked")
    @PostMapping(Mapping.EnrollStudent)
    public ResponseEntity<?> enrollStudent(@RequestParam(Param.ACCESSTOKEN) String token,
                              @Valid @RequestParam(Param.CLASSROOMNAME) String classroomName,
                              @Valid @RequestParam(Param.PASSCODE) String passcode) {

        User user = userRepository.findByToken(token);

        if(user == null){
          	 return new ResponseEntity<>("User Is Not Valid",HttpStatus.BAD_REQUEST);
        }
        if (!jwtTokenChecker.validateToken(token)) {
         	 return new ResponseEntity<>("Session Expired",HttpStatus.BAD_REQUEST);
        }

        if(user.getParent() != null){
         	 return new ResponseEntity<>("You Must Have Parent permission",
                     HttpStatus.UNAUTHORIZED);
        }

        Classroom classroom = classroomRepository.findByClassroomName(classroomName);

        if (classroom == null ) {
        	 return new ResponseEntity<>("Classroom Is Not Found   ",
                     HttpStatus.NOT_FOUND);
        }

        if (!classroom.getPassCode().equals(passcode) ) {
            return new ResponseEntity<>("Classroom Has Wrong Passcode ",
               HttpStatus.NOT_FOUND);
        }

        classroom.getStudents().add(user);
        classroomRepository.save(classroom);
        return new ResponseEntity<>(classroom ,HttpStatus.OK);
    }


    @GetMapping(Mapping.CLASSROOMS)
    ResponseEntity<?> retrieveJoinedClassrooms(@RequestParam(Param.ACCESSTOKEN) String token) {

        User user = userRepository.findByToken(token);

        if(user == null){
         	 return new ResponseEntity<>("User Is Not Valid",HttpStatus.BAD_REQUEST);
        }
        if (!jwtTokenChecker.validateToken(token)) {
        	 return new ResponseEntity<>("Session Expired",HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(user.getClassrooms() ,HttpStatus.OK);
    }
}