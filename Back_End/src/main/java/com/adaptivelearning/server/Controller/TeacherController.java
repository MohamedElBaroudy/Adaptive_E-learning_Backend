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
import org.springframework.web.client.RestClientResponseException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(Mapping.TEACHER)
public class TeacherController {

    @Autowired
    ClassroomRepository classroomRepository;

    @Autowired
    UserRepository userRepository;

//    @Autowired
//    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider jwtTokenChecker;


    @PostMapping(Mapping.CLASSROOMS)
    Classroom create(@RequestParam(Param.ACCESSTOKEN) String token,
                     @Valid @RequestParam(Param.CLASSROOMNAME) String classroomName,
                     @Valid @RequestParam(Param.PASSCODE) String passcode,
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

        if(user.getParent() != null){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }

        Classroom classRoom = new Classroom(classroomName, passcode);
        classRoom.setCreator(user);
//        classRoom.setPassCode(passwordEncoder.encode(classRoom.getPassCode()));
        classroomRepository.save(classRoom);

        return classRoom;
    }


    @GetMapping(Mapping.CLASSROOMS)
    Iterable<Classroom> retrieveCreatedClassrooms(@RequestParam(Param.ACCESSTOKEN) String token,
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

        return user.getClassrooms();
    }


    @PutMapping(Mapping.CLASSROOM)
    Classroom updateClassroomInfo(@RequestParam(Param.ACCESSTOKEN) String token,
                                  @Valid @RequestParam(value = Param.CLASSROOMNAME,required = false) String classroomName,
                                  @Valid @RequestParam(value = Param.PASSCODE,required = false) String passcode,
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

        Classroom classroom = classroomRepository.findByClassroomName(classroomName);

        if (classroom == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
//            throw new RestClientResponseException("Not found classroom",
//                    404, "NotFound", HttpHeaders.EMPTY, null, null);
        }

        if (classroom.getCreator().getUserId()!=
                (user.getUserId())) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return null;
//            throw new RestClientResponseException(
//                    "Not Allowed you are not a teacher or this is not your classroom to update",
//                    405, "NotAllowed", HttpHeaders.EMPTY, null, null);
        }

//        classroom.setPassCode(passwordEncoder.encode(passcode));
        classroom.setPassCode(passcode);
        classroomRepository.save(classroom);
        return classroom;
    }


    @DeleteMapping(Mapping.CLASSROOM)
    void delete(@RequestParam(Param.ACCESSTOKEN) String token,
                @Valid @RequestParam(Param.CLASSROOMNAME) String classroomName,
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

        Classroom classroom = classroomRepository.findByClassroomName(classroomName);

        if (classroom == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
//            throw new RestClientResponseException("Not found classroom",
//                    404, "NotFound", HttpHeaders.EMPTY, null, null);
        }

        if(classroom.getCreator().getUserId() != user.getUserId()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
//            throw new RestClientResponseException("Only creator of this classroom is allowed",
//                    405, "NotAllowed", HttpHeaders.EMPTY, null, null);
        }

        classroomRepository.deleteById(classroom.getClassroomId());
    }
}
