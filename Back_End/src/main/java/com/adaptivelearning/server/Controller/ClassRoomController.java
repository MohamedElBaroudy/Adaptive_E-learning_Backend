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
    Classroom findById(@RequestParam(Param.ACCESSTOKEN) String token,
                       @Valid @RequestParam(Param.CLASSROOM_ID) Integer classroomId,
                       HttpServletResponse response) {

        User user = userRepository.findByToken(token);


        if(user == null){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
//            throw new RestClientResponseException("Invalid token",
//                    400, "BadRequest", HttpHeaders.EMPTY, null, null);
        }
        if (!jwtTokenChecker.validateToken(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
//            throw new RestClientResponseException("Session expired",
//                    400, "BadRequest", HttpHeaders.EMPTY, null, null);
        }

        Classroom classroom = classroomRepository.findByClassroomId(classroomId);

        if (classroom == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
//            throw new RestClientResponseException("Not found classroom",
//                    404, "NotFound", HttpHeaders.EMPTY, null, null);
        }

        if (user.getUserId()!=
                classroom.getCreator().getUserId()||
                !classroom.getStudents().contains(user)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return null;
//            throw new RestClientResponseException(
//                    "Not Allowed you are not a teacher or joined in this classroom",
//                    405, "NotAllowed", HttpHeaders.EMPTY, null, null);
        }

        return classroom;
    }
}
