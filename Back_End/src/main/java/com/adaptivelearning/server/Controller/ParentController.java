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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientResponseException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(Mapping.PARENT)
public class ParentController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

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
                         @Valid @RequestParam(Param.GENDRE) short gender,
                         HttpServletResponse response) throws ParseException {

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


        if (userRepository.existsByEmail(email)  ||  userRepository.existsByUsername(username)) {
       	 return new ResponseEntity<>("User Is Present Before",HttpStatus.MULTIPLE_CHOICES);
//            throw new RestClientResponseException("User present",
//                    400, "Badrequest", HttpHeaders.EMPTY, null, null);
        }

//        List<User> myChildren = user.getChildren();
//        for (User child:myChildren){
//            if (child.getFirstName().equals(name))
//                throw new RestClientResponseException("You added this child before", 400, "BadRequest", HttpHeaders.EMPTY, null, null);
//        }

        if(userRepository.findByFirstNameAndParent(name,user) != null){
          	 return new ResponseEntity<>("User Is Present",HttpStatus.MULTIPLE_CHOICES);

        }

        // Creating Child account
        DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        Date dateOfBirth = format.parse(dob);
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        User child = new User(name, user.getLastName(), email, username, password, dateOfBirth, gender);

        child.setPassword(passwordEncoder.encode(child.getPassword()));
        child.setParent(user);
        userRepository.save(child);
      	 return new ResponseEntity<>(child,HttpStatus.OK);

    }


    @PostMapping(Mapping.ENROLLCHILD)
     public ResponseEntity<?> enroll(@RequestParam(Param.ACCESSTOKEN) String token,
                        @Valid @RequestParam(Param.FIRSTNAME) String childName,
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

        User enrollChild = userRepository.findByFirstNameAndParent(childName,user);
        Classroom classroom = classroomRepository.findByClassroomName(classroomName);

        if (enrollChild == null) {
          	 return new ResponseEntity<>("Child Is Not Found",HttpStatus.MULTIPLE_CHOICES);
//            throw new RestClientResponseException("Child not found",
//                    400, "Badrequest", HttpHeaders.EMPTY, null, null);
        }

        if (classroom == null ) {
         	 return new ResponseEntity<>("Classroom Is Not Found   ",HttpStatus.MULTIPLE_CHOICES);
//            throw new RestClientResponseException("Classroom Not found ",
//                    404, "Notfound", HttpHeaders.EMPTY, null, null);
        }
        
       if (!classroom.getPassCode().equals(passCode) ) {

    	   return new ResponseEntity<>("Classroom Has Wrong Passcode ",HttpStatus.MULTIPLE_CHOICES);
//         throw new RestClientResponseException("passcode is wrong",
//                 404, "Notfound", HttpHeaders.EMPTY, null, null);
       }

        classroom.getStudents().add(enrollChild);

//        enrollChild.getJoins().add(classroom);

        classroomRepository.save(classroom);
       return new ResponseEntity<>(classroom ,HttpStatus.OK);


    }

    @GetMapping(Mapping.CHILDREN)
    ResponseEntity<?> retrieveChildren(@RequestParam(Param.ACCESSTOKEN) String token,
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
        return new ResponseEntity<>(user.getChildren() ,HttpStatus.OK);
    }

}