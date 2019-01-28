package com.adaptivelearning.server.Controller;

import com.adaptivelearning.server.Model.Classroom;

import com.adaptivelearning.server.Model.User;
import com.adaptivelearning.server.Repository.ClassroomRepository;

import com.adaptivelearning.server.Repository.UserRepository;
import com.adaptivelearning.server.Security.JwtTokenProvider;
import com.adaptivelearning.server.constants.Mapping;
import com.adaptivelearning.server.constants.Param;

import org.springframework.beans.factory.annotation.Autowired;

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
    public void enrollStudent(@RequestParam(Param.ACCESSTOKEN) String token,
                              @Valid @RequestParam(Param.CLASSROOMNAME) String classroomName,
                              @Valid @RequestParam(Param.PASSCODE) String passcode,
                              HttpServletResponse response) {

        User user = userRepository.findByToken(token);

        if(user == null){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
//            throw new RestClientResponseException("Invalid token",
//                    400, "BadRequest", HttpHeaders.EMPTY, null, null);
        }
        if (!jwtTokenChecker.validateToken(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
//            throw new RestClientResponseException("Session expired",
//                    400, "BadRequest", HttpHeaders.EMPTY, null, null);
        }

        if(user.getParent() != null){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        Classroom classroom = classroomRepository.findByClassroomName(classroomName);

        if (classroom == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
//            throw new RestClientResponseException("Not found classroom",
//                    404, "NotFound", HttpHeaders.EMPTY, null, null);
        }

        if(!classroom.getPassCode().equals(passcode)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
//            throw new RestClientResponseException("Passcode is wrong",
//                    400, "Invalid", HttpHeaders.EMPTY, null, null);
        }

        classroom.getStudents().add(user);
//        user.getJoins().add(classroom);//this code line may may may be unnecessary
        classroomRepository.save(classroom);
    }


    @GetMapping(Mapping.CLASSROOMS)
    Iterable<Classroom> retrieveJoinedClassrooms(@RequestParam(Param.ACCESSTOKEN) String token,
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

        return user.getJoins();
    }
}