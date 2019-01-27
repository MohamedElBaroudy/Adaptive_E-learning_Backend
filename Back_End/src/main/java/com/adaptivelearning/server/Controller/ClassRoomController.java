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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientResponseException;

import java.util.*;

import javax.validation.Valid;


@RestController
public class ClassRoomController {

    @Autowired
    ClassRoomRepository classRoomRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider jwtTokenChecker;

    @PostMapping(Mapping.TEACHERCLASSROOMS)
    Classroom create(@RequestParam(Param.ACCESSTOKEN) String token,
                     @Valid @RequestParam(Param.CLASSROOMNAME) String classroomName,
                     @Valid @RequestParam(Param.PASSCODE) String passcode) {

        if(!userRepository.findByToken(token).isPresent())
            throw new RestClientResponseException("Invalid token", 400, "BadRequest", HttpHeaders.EMPTY, null, null);

        if (!jwtTokenChecker.validateToken(token))
            throw new RestClientResponseException("Session expired", 400, "BadRequest", HttpHeaders.EMPTY, null, null);

        //        //Long user_id = jwtTokenChecker.getUserIdFromToken(token);
        User user = userRepository.findByToken(token).get();



        Classroom classRoom = new Classroom(classroomName, passcode);
        classRoom.setCreator(user);
        classRoom.setPassCode(passwordEncoder.encode(classRoom.getPassCode()));
        classRoomRepository.save(classRoom);

        return classRoom;
    }

    @GetMapping(Mapping.TEACHERCLASSROOMS)
    Iterable<Classroom> retrieveCreatedClassrooms(@RequestParam(Param.ACCESSTOKEN) String token) {

        if(!userRepository.findByToken(token).isPresent())
            throw new RestClientResponseException("Invalid token", 400, "BadRequest", HttpHeaders.EMPTY, null, null);

        if (!jwtTokenChecker.validateToken(token))
            throw new RestClientResponseException("Session expired", 400, "BadRequest", HttpHeaders.EMPTY, null, null);

        User user = userRepository.findByToken(token).get();

        return user.getClassrooms();
    }

    @GetMapping(Mapping.STUDENTCLASSROOMS)
    Iterable<Classroom> retrieveJoinedClassrooms(@RequestParam(Param.ACCESSTOKEN) String token) {

        if(!userRepository.findByToken(token).isPresent())
            throw new RestClientResponseException("Invalid token", 400, "BadRequest", HttpHeaders.EMPTY, null, null);

        if (!jwtTokenChecker.validateToken(token))
            throw new RestClientResponseException("Session expired", 400, "BadRequest", HttpHeaders.EMPTY, null, null);

        User user = userRepository.findByToken(token).get();

        return user.getJoins();
    }


    @PutMapping(Mapping.CLASSROOM)
    Classroom updatePasscode(@RequestParam(Param.ACCESSTOKEN) String token,
                     @Valid @RequestParam(Param.CLASSROOM_ID) Integer classRoomId,
                     @Valid @RequestParam(Param.PASSCODE) String passcode) {
        if (!userRepository.findByToken(token).isPresent())
            throw new RestClientResponseException("Invalid token", 400, "BadRequest", HttpHeaders.EMPTY, null, null);

        if (!jwtTokenChecker.validateToken(token))
            throw new RestClientResponseException("Session expired", 400, "BadRequest", HttpHeaders.EMPTY, null, null);

        User user = userRepository.findByToken(token).get();

        if (!classRoomRepository.findById(classRoomId).isPresent())
            throw new RestClientResponseException("Not found classroom", 404, "NotFound", HttpHeaders.EMPTY, null, null);

        if (classRoomRepository.findById(classRoomId).get().getCreator().getUserId()!=(user.getUserId()))
            throw new RestClientResponseException("Not Allowed you are not a teacher or this is not your classroom to update", 405, "NotAllowed", HttpHeaders.EMPTY, null, null);

        Classroom classRoom = classRoomRepository.findById(classRoomId).get();
        classRoom.setPassCode(passcode);
        classRoomRepository.save(classRoom);
        return classRoom;
    }

//    @PutMapping(Mapping.CLASSROOM)
//    Classroom updateName(@RequestParam(Param.ACCESSTOKEN) String token,
//                             @Valid @RequestParam(Param.CLASSROOM_ID) Integer classRoomId,
//                             @Valid @RequestParam(Param.CLASSROOMNAME) String classRoomName) {
//        if (!userRepository.findByToken(token).isPresent())
//            throw new RestClientResponseException("Invalid token", 400, "BadRequest", HttpHeaders.EMPTY, null, null);
//
//        if (!jwtTokenChecker.validateToken(token))
//            throw new RestClientResponseException("Session expired", 400, "BadRequest", HttpHeaders.EMPTY, null, null);
//
//        User user = userRepository.findByToken(token).get();
//
//        if (!classRoomRepository.findById(classRoomId).isPresent())
//            throw new RestClientResponseException("Not found classroom", 404, "NotFound", HttpHeaders.EMPTY, null, null);
//
//        if (classRoomRepository.findById(classRoomId).get().getCreator().getUserId()!=(user.getUserId()))
//            throw new RestClientResponseException("Not Allowed you are not a teacher or this is not your classroom to update", 405, "NotAllowed", HttpHeaders.EMPTY, null, null);
//
//        if (classRoomRepository.findByClassroomName(classRoomName).isPresent())
//            throw new RestClientResponseException("Classroom name in use", 405, "NotAllowed", HttpHeaders.EMPTY, null, null);
//
//        Classroom classRoom = classRoomRepository.findById(classRoomId).get();
//        classRoom.setClassroomName(classRoomName);
//        classRoomRepository.save(classRoom);
//        return classRoom;
//    }

    @DeleteMapping(Mapping.CLASSROOM)
    void delete(@RequestParam(Param.ACCESSTOKEN) String token,
                @Valid @RequestParam(Param.CLASSROOM_ID) Integer classRoomId) {

        if (!userRepository.findByToken(token).isPresent())
            throw new RestClientResponseException("Invalid token", 400, "BadRequest", HttpHeaders.EMPTY, null, null);

        if (!jwtTokenChecker.validateToken(token))
            throw new RestClientResponseException("Session expired", 400, "BadRequest", HttpHeaders.EMPTY, null, null);

        User user = userRepository.findByToken(token).get();

        if (!classRoomRepository.findById(classRoomId).isPresent())
            throw new RestClientResponseException("Not found classroom", 404, "NotFound", HttpHeaders.EMPTY, null, null);

        if(classRoomRepository.findById(classRoomId).get().getCreator().getUserId()!=(user.getUserId()))
            throw new RestClientResponseException("Only creator of this classroom is allowed", 405, "NotAllowed", HttpHeaders.EMPTY, null, null);

        classRoomRepository.deleteById(classRoomId);
    }

    @GetMapping(Mapping.CLASSROOM)
    Classroom findById(@RequestParam(Param.ACCESSTOKEN) String token,
                       @Valid @RequestParam(Param.CLASSROOM_ID) Integer classRoomId) {

        if (!userRepository.findByToken(token).isPresent())
            throw new RestClientResponseException("Invalid token", 400, "BadRequest", HttpHeaders.EMPTY, null, null);

        if (!jwtTokenChecker.validateToken(token))
            throw new RestClientResponseException("Session expired", 400, "BadRequest", HttpHeaders.EMPTY, null, null);

        //        //Long user_id = jwtTokenChecker.getUserIdFromToken(token);
        User user = userRepository.findByToken(token).get();

        if(!classRoomRepository.findById(classRoomId).isPresent())
            throw new RestClientResponseException("Not found classroom", 404, "NotFound", HttpHeaders.EMPTY, null, null);

        if (user.getUserId()!=
                classRoomRepository.findById(classRoomId).get().getCreator().getUserId()||
                !classRoomRepository.findById(classRoomId).get().getStudents().contains(user))
            throw new RestClientResponseException(
                    "Not Allowed you are not a teacher or joined in this classroom",
                    405, "NotAllowed", HttpHeaders.EMPTY, null, null);

        return classRoomRepository.findById(classRoomId).get();
    }
}
