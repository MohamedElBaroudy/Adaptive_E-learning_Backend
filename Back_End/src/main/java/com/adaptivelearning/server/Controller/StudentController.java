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


import javax.servlet.http.HttpServletResponse;
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
                              @Valid @RequestParam(Param.PASSCODE) String passcode,
                              HttpServletResponse response) {

        User user = userRepository.findByToken(token);

        if(user == null){
          	 return new ResponseEntity<>("User Is Not Valid",HttpStatus.MULTIPLE_CHOICES);
//            throw new RestClientResponseException("Invalid token",
//                    400, "BadRequest", HttpHeaders.EMPTY, null, null);
        }
        if (!jwtTokenChecker.validateToken(token)) {
         	 return new ResponseEntity<>("Session Expired",HttpStatus.MULTIPLE_CHOICES);
//            throw new RestClientResponseException("Session expired",
//                    400, "BadRequest", HttpHeaders.EMPTY, null, null);
        }

        if(user.getParent() != null){
         	 return new ResponseEntity<>("You Must Have Parent",HttpStatus.MULTIPLE_CHOICES);
        }

        Classroom classroom = classroomRepository.findByClassroomName(classroomName);

        if (classroom == null ) {
        	 return new ResponseEntity<>("Classroom Is Not Found   ",HttpStatus.MULTIPLE_CHOICES);
//           throw new RestClientResponseException("Classroom Not found ",
//                   404, "Notfound", HttpHeaders.EMPTY, null, null);
       }
       
      if (!classroom.getPassCode().equals(passcode) ) {

   	   return new ResponseEntity<>("Classroom Has Wrong Passcode ",HttpStatus.MULTIPLE_CHOICES);
//        throw new RestClientResponseException("passcode is wrong",
//                404, "Notfound", HttpHeaders.EMPTY, null, null);
      }

        classroom.getStudents().add(user);
//        user.getJoins().add(classroom);//this code line may may may be unnecessary
        classroomRepository.save(classroom);
        return new ResponseEntity<>(classroom ,HttpStatus.OK);
        
    }


    @GetMapping(Mapping.CLASSROOMS)
    ResponseEntity<?> retrieveJoinedClassrooms(@RequestParam(Param.ACCESSTOKEN) String token,
                                                 HttpServletResponse response) {

        User user = userRepository.findByToken(token);

        if(user == null){
         	 return new ResponseEntity<>("User Is Not Valid",HttpStatus.MULTIPLE_CHOICES);
//            throw new RestClientResponseException("Invalid token",
//                    400, "BadRequest", HttpHeaders.EMPTY, null, null);
        }
        if (!jwtTokenChecker.validateToken(token)) {
        	 return new ResponseEntity<>("Session Expired",HttpStatus.MULTIPLE_CHOICES);
//            throw new RestClientResponseException("Session expired",
//                    400, "BadRequest", HttpHeaders.EMPTY, null, null);
        }

        return new ResponseEntity<>(user.getClassrooms() ,HttpStatus.OK);
    }





}