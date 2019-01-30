package com.adaptivelearning.server.Controller;

import com.adaptivelearning.server.Model.Classroom;
import com.adaptivelearning.server.Model.User;
import com.adaptivelearning.server.Repository.ClassroomRepository;
import com.adaptivelearning.server.Repository.UserRepository;

import com.adaptivelearning.server.Security.JwtTokenProvider;
import com.adaptivelearning.server.constants.Mapping;
import com.adaptivelearning.server.constants.Param;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientResponseException;

import javax.servlet.http.HttpServletResponse;
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
                       @Valid @RequestParam(Param.CLASSROOM_ID) Integer classroomId,
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

        Classroom classroom = classroomRepository.findByClassroomId(classroomId);

        if (classroom == null ) {
       	 return new ResponseEntity<>("Classroom Is Not Found   ",HttpStatus.MULTIPLE_CHOICES);
//          throw new RestClientResponseException("Classroom Not found ",
//                  404, "Notfound", HttpHeaders.EMPTY, null, null);
      }

        if (user.getUserId()!=
                classroom.getCreator().getUserId() || !classroom.getStudents().contains(user)) {
          	 return new ResponseEntity<>("Sorry You Are Not Teacher Nor Student In This Classroom ",HttpStatus.MULTIPLE_CHOICES);
//            throw new RestClientResponseException(
//                    "Not Allowed you are not a teacher or joined in this classroom",
//                    405, "NotAllowed", HttpHeaders.EMPTY, null, null);
        }

        return new ResponseEntity<>(classroom ,HttpStatus.OK);
    }
    
    @PostMapping(Mapping.CreateClassroom)
    public ResponseEntity<?> CreateClassroom(@RequestParam(Param.ACCESSTOKEN) String token,
                       @Valid @RequestParam(Param.CLASSROOMNAME) String classroomName,
                       @Valid @RequestParam(Param.PASSCODE) String passCode,
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
        
        Classroom classroom = classroomRepository.findByClassroomName(classroomName);
        
        if (classroom != null ) {
        	 return new ResponseEntity<>("Classroom Is Found Before  ",HttpStatus.MULTIPLE_CHOICES);
//           throw new RestClientResponseException("Classroom Not found ",
//                   404, "Notfound", HttpHeaders.EMPTY, null, null);
       }
       
        Classroom New_classroom = new Classroom(classroomName, passCode , user);
        classroomRepository.save(New_classroom);
        return new ResponseEntity<>(New_classroom ,HttpStatus.OK);

    }
}
