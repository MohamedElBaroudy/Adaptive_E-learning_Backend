package com.adaptivelearning.server.Controller;

import com.adaptivelearning.server.FancyModel.FancyTeachingRequest;
import com.adaptivelearning.server.Model.TeachingRequest;
import com.adaptivelearning.server.Model.User;
import com.adaptivelearning.server.Repository.TeachingRequestRepository;
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
public class AdminController {

    @Autowired
    TeachingRequestRepository teachingRequestRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtTokenProvider jwtTokenChecker;

    @GetMapping(Mapping.REQUESTS)
    public ResponseEntity<?> retrieveRequests(@Valid @RequestParam(Param.ACCESS_TOKEN) String token){
        User user = userRepository.findByToken(token);

        if(user == null){
            return new ResponseEntity<>("User Is Not Valid",
                    HttpStatus.UNAUTHORIZED);
        }
        if (!jwtTokenChecker.validateToken(token)) {
            return new ResponseEntity<>("Session Expired",
                    HttpStatus.UNAUTHORIZED);
        }

        if (!user.getUsername().equals("Admin"))
            return new ResponseEntity<>("Only Admin can show requests",
                    HttpStatus.FORBIDDEN);

        FancyTeachingRequest fancyTeachingRequest = new FancyTeachingRequest();
        return new ResponseEntity<>(fancyTeachingRequest.toFancyTeachingRequestListMapping(teachingRequestRepository.findAll()),
                HttpStatus.OK);
    }

    @PutMapping(Mapping.APPROVE)
    public ResponseEntity<?> approveRequest(@Valid @RequestParam(Param.ACCESS_TOKEN) String token,
                                            @Valid @RequestParam(Param.USER_ID) Integer claimerId) {
        User user = userRepository.findByToken(token);
        TeachingRequest teachingRequest = teachingRequestRepository.findByClaimerId(claimerId);
        if(user == null){
            return new ResponseEntity<>("User Is Not Valid",
                    HttpStatus.UNAUTHORIZED);
        }
        if (!jwtTokenChecker.validateToken(token)) {
            return new ResponseEntity<>("Session Expired",
                    HttpStatus.UNAUTHORIZED);
        }

        if (!user.getUsername().equals("Admin"))
            return new ResponseEntity<>("Only Admin can show requests",
                    HttpStatus.FORBIDDEN);

        if (!teachingRequestRepository.existsByClaimerId(claimerId)){
            return new ResponseEntity<>("Not found request",
                    HttpStatus.NOT_FOUND);
        }
        if(teachingRequest.isApproved())
            return new ResponseEntity<>("Already approved",
                    HttpStatus.NOT_MODIFIED);

        User claimer = userRepository.findByUserId(claimerId);
        teachingRequest.setApproved(true);
        claimer.setTeacher(true);
        userRepository.save(claimer);
        teachingRequestRepository.deleteById(teachingRequest.getRequestId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
