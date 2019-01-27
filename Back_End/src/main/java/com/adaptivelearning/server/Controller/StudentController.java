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

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientResponseException;



import javax.validation.Valid;

@RestController
//@RequestMapping(Mapping.BASE_AUTH)
public class StudentController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ClassRoomRepository classRoomRepository;
    @Autowired
    JwtTokenProvider jwtTokenChecker;

    @SuppressWarnings("unchecked")
    @PostMapping(Mapping.EnrollStudent)
    public void enrollStudent(@RequestParam(Param.ACCESSTOKEN) String token,
                              @Valid @RequestParam(Param.CLASSROOMNAME) String classroomName,
                              @Valid @RequestParam(Param.PASSCODE) String passcode) {

        if (!userRepository.findByToken(token).isPresent())
            throw new RestClientResponseException("Invalid token", 400, "BadRequest", HttpHeaders.EMPTY, null, null);

        if (!jwtTokenChecker.validateToken(token))
            throw new RestClientResponseException("Session expired", 400, "BadRequest", HttpHeaders.EMPTY, null, null);

        User user = userRepository.findByToken(token).get();


        if (!classRoomRepository.findByClassroomName(classroomName).isPresent())
            throw new RestClientResponseException("Not found classroom", 404, "NotFound", HttpHeaders.EMPTY, null, null);


        Classroom classRoom = classRoomRepository.findByClassroomName(classroomName).get();

        if(!classRoom.getPassCode().equals(passcode))
            throw new RestClientResponseException("Passcode is wrong", 400, "Invalid", HttpHeaders.EMPTY, null, null);

        classRoom.getStudents().add(user);
        user.getJoins().add(classRoom);//this code line may may may be unnecessary
        classRoomRepository.save(classRoom);
    }

}