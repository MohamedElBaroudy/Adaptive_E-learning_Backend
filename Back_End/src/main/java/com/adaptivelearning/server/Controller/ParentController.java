package com.adaptivelearning.server.Controller;

import com.adaptivelearning.server.Model.Classroom;
import com.adaptivelearning.server.Model.User;
import com.adaptivelearning.server.Repository.ClassRoomRepository;
import com.adaptivelearning.server.Repository.UserRepository;
import com.adaptivelearning.server.Security.JwtTokenProvider;
import com.adaptivelearning.server.constants.Mapping;
import com.adaptivelearning.server.constants.Param;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientResponseException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

@RestController
//@RequestMapping(Mapping.BASE_AUTH)
@RequestMapping(Mapping.PARENT)
public class ParentController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ClassRoomRepository classRoomRepository;


    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider jwtTokenChecker;


    @SuppressWarnings("unchecked")
    @PostMapping(Mapping.AddChild)
    public void addChild(@RequestParam(Param.ACCESSTOKEN) String token,
                         @Valid @RequestParam(Param.FIRSTNAME) String name,
                         @Valid @RequestParam(Param.DATEOFBIRTH) Date dob,
                         @Valid @RequestParam(Param.USERNAME) String username,
                         @Valid @RequestParam(Param.EMAIL) String email,
                         @Valid @RequestParam(Param.PASSWORD) String password,
                         @Valid @RequestParam(Param.GENDRE) short gender) {

        if(!userRepository.findByToken(token).isPresent())
            throw new RestClientResponseException("Invalid token", 400, "BadRequest", HttpHeaders.EMPTY, null, null);

        if (!jwtTokenChecker.validateToken(token))
            throw new RestClientResponseException("Session expired", 400, "BadRequest", HttpHeaders.EMPTY, null, null);

        //        //Long user_id = jwtTokenChecker.getUserIdFromToken(token);
        User parent = userRepository.findByToken(token).get();


        if (userRepository.existsByEmail(email))
            throw new RestClientResponseException("User present", 400, "Badrequest", HttpHeaders.EMPTY, null, null);

        List<User> myChildren = parent.getChildren();
        for (User child:myChildren){
            if (child.getFirstName().equals(name))
                throw new RestClientResponseException("You added this child before", 400, "BadRequest", HttpHeaders.EMPTY, null, null);
        }


        // Creating Child account
        User child = new User(name, parent.getLastName(), email, username, password, dob, gender);

        child.setPassword(passwordEncoder.encode(child.getPassword()));
        child.setParent(parent);

        parent.getChildren().add(child);
        userRepository.save(child);
    }


    @PostMapping(Mapping.PARENTENROLL)
     public void enroll(@RequestParam(Param.ACCESSTOKEN) String token,
                        @Valid @RequestParam(Param.USER_ID) long childId,
                        @Valid @RequestParam(Param.CLASSROOM_ID) Integer classId,
                        @Valid @RequestParam(Param.PASSCODE) String passCode) {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String user_email = ((UserPrincipal) principal).getEmail();
//        User parent = userRepository.findByEmail(user_email);

        //// the new way
        if(!userRepository.findByToken(token).isPresent())
            throw new RestClientResponseException("Invalid token", 400, "BadRequest", HttpHeaders.EMPTY, null, null);

        if (!jwtTokenChecker.validateToken(token))
            throw new RestClientResponseException("Session expired", 400, "BadRequest", HttpHeaders.EMPTY, null, null);

        //        //Long user_id = jwtTokenChecker.getUserIdFromToken(token);
        User parent = userRepository.findByToken(token).get();

        Optional<User> enrollChild = userRepository.findById(childId);
        Optional<Classroom> classRoomSearch = classRoomRepository.findById(classId);


        if (!classRoomSearch.isPresent())
            throw new RestClientResponseException("Classroom Not found", 404, "Notfound", HttpHeaders.EMPTY, null, null);


        if (!enrollChild.isPresent())
            throw new RestClientResponseException("Child not found", 400, "Badrequest", HttpHeaders.EMPTY, null, null);


        if (enrollChild.get().getParent().getUserId()!=(parent.getUserId()))
            throw new RestClientResponseException("Not Allowed you are not the parent of this child", 405, "Forbidden", HttpHeaders.EMPTY, null, null);

        if(!classRoomSearch.get().getPassCode().equals(passCode))
            throw new RestClientResponseException("Passcode is wrong", 403, "Forbidden", HttpHeaders.EMPTY, null, null);

        Classroom classRoom = classRoomSearch.get();

        classRoom.getStudents().add(enrollChild.get());

        User child = enrollChild.get();

        child.getJoins().add(classRoom);

        userRepository.save(child);

        classRoomRepository.save(classRoom);

    }

    @GetMapping(Mapping.PARENTCHILDREN)
    List<User> retrieveChildren(@RequestParam(Param.ACCESSTOKEN) String token) {
        //// the new way
        if (!userRepository.findByToken(token).isPresent())
            throw new RestClientResponseException("Invalid token", 400, "BadRequest", HttpHeaders.EMPTY, null, null);

        if (!jwtTokenChecker.validateToken(token))
            throw new RestClientResponseException("Session expired", 400, "BadRequest", HttpHeaders.EMPTY, null, null);

        //        //Long user_id = jwtTokenChecker.getUserIdFromToken(token);
        User user = userRepository.findByToken(token).get();



//        return userRepository.findAllByParent(user);

        return user.getChildren();

    }

}