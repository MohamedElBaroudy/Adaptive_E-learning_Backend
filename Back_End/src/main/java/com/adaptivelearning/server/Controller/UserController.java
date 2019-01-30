package com.adaptivelearning.server.Controller;

import com.adaptivelearning.server.Model.User;
import com.adaptivelearning.server.Repository.UserRepository;
import com.adaptivelearning.server.Security.JwtTokenProvider;
import com.adaptivelearning.server.constants.Mapping;
import com.adaptivelearning.server.constants.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping(Mapping.AUTH)
public class UserController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;


    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @GetMapping(Mapping.LOGIN)
    public ResponseEntity<?> authenticateUser(@Valid @RequestParam(value = Param.EMAIL,required = false) String email,
                                 @Valid @RequestParam(value = Param.USERNAME,required = false) String username,
                                 @Valid @RequestParam(Param.PASSWORD) String password) {
        if (email == null && username == null)
            return new ResponseEntity<>("you must enter email or user name",
                    HttpStatus.BAD_REQUEST);

        User user = userRepository.findByEmailOrUsername(email,username);

        if (user == null)
            return new ResponseEntity<>("user is not present",
                    HttpStatus.BAD_REQUEST);

        // already logged in
        if(user.getToken()!=null)
            return new ResponseEntity<>("already logged in"+", token is : " +user.getToken(),
                    HttpStatus.BAD_REQUEST);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken( user.getEmail(),password)
        
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);

        user.setToken(jwt);

        userRepository.save(user);

        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @GetMapping(Mapping.LOGOUT)
    public ResponseEntity<?> KickOutUser(@RequestParam(Param.ACCESSTOKEN) String token){

        User user = userRepository.findByToken(token);

        if (user == null)
            return new ResponseEntity<>("user isn't logged in",HttpStatus.BAD_REQUEST);

        if (!tokenProvider.validateToken(token))
            return new ResponseEntity<>("session expired",HttpStatus.BAD_REQUEST);

        user.setToken(null);
        userRepository.save(user);
        return new ResponseEntity<>("user logged out",HttpStatus.OK);
    }


    @PostMapping(Mapping.REGISTER)
    public ResponseEntity<?> registerUser(@Valid @RequestParam(Param.FIRSTNAME) String fname,
                             @Valid @RequestParam(Param.LASTNAME) String lname,
                             @Valid @RequestParam(Param.DATEOFBIRTH) String dob,
                             @Valid @RequestParam(Param.USERNAME) String username,
                             @Valid @RequestParam(Param.EMAIL) String email,
                             @Valid @RequestParam(Param.PASSWORD) String password,
                             @Valid @RequestParam(Param.GENDRE) short gender) throws ParseException {

        if (userRepository.existsByEmail(email))
            return new ResponseEntity<>("Email is used",HttpStatus.MULTIPLE_CHOICES);

        if (userRepository.existsByUsername(username))
            return new ResponseEntity<>("Username is used",HttpStatus.MULTIPLE_CHOICES);


        // Creating user's account
        DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        Date dateOfBirth = format.parse(dob);
        fname = fname.substring(0, 1).toUpperCase() + fname.substring(1);
        lname = lname.substring(0, 1).toUpperCase() + lname.substring(1);
        User user = new User(fname, lname, email, username, password, dateOfBirth, gender);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        return new ResponseEntity<>(user,HttpStatus.OK);
    }
}
