package com.adaptivelearning.server.Controller;

import com.adaptivelearning.server.Model.User;
import com.adaptivelearning.server.Repository.UserRepository;
import com.adaptivelearning.server.Security.JwtTokenProvider;
import com.adaptivelearning.server.constants.Mapping;
import com.adaptivelearning.server.constants.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.client.RestClientResponseException;

import javax.servlet.http.HttpServletResponse;
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
    public User authenticateUser(@Valid @RequestParam(value = Param.EMAIL,required = false) String email,
                                 @Valid @RequestParam(value = Param.USERNAME,required = false) String username,
                                 @Valid @RequestParam(Param.PASSWORD) String password,
                                 HttpServletResponse response) {
        if (email == null && username == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }

        User user = userRepository.findByEmailOrUsername(email,username);

        //Do like this instead of optional
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
//            throw new RestClientResponseException("User present", 300, "Unregistered", HttpHeaders.EMPTY, null, null);
        }

        // already logged in
        if(user.getToken()!=""){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        password
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);

        user.setToken(jwt);

        userRepository.save(user);

        return user;
    }

    @GetMapping(Mapping.LOGOUT)
    public void KickOutUser(@RequestParam(Param.ACCESSTOKEN) String token,
                            HttpServletResponse response){

        User user = userRepository.findByToken(token);

        if (user == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
//            throw new RestClientResponseException("Not found token",
//                    400, "BadRequest", HttpHeaders.EMPTY, null, null);
        }

        if (!tokenProvider.validateToken(token)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
//            throw new RestClientResponseException("Session expired",
//                    400, "BadRequest", HttpHeaders.EMPTY, null, null);
        }

        user.setToken("");
        userRepository.save(user);
    }


    @PostMapping(Mapping.REGISTER)
    public void registerUser(@Valid @RequestParam(Param.FIRSTNAME) String fname,
                             @Valid @RequestParam(Param.LASTNAME) String lname,
                             @Valid @RequestParam(Param.DATEOFBIRTH) String dob,
                             @Valid @RequestParam(Param.USERNAME) String username,
                             @Valid @RequestParam(Param.EMAIL) String email,
                             @Valid @RequestParam(Param.PASSWORD) String password,
                             @Valid @RequestParam(Param.GENDRE) short gender,
                             HttpServletResponse response) throws ParseException {

        if (userRepository.existsByEmail(email)) {
            response.setStatus(HttpServletResponse.SC_MULTIPLE_CHOICES);
            return;
//            throw new RestClientResponseException("Email is used",
//                    300, "Unregistered", HttpHeaders.EMPTY, null, null);
        }

        if (userRepository.existsByUsername(username)) {
            response.setStatus(HttpServletResponse.SC_MULTIPLE_CHOICES);
            return;
//            throw new RestClientResponseException("Username is used",
//                    300, "Unregistered", HttpHeaders.EMPTY, null, null);
        }


        // Creating user's account
        DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        Date dateOfBirth = format.parse(dob);
        fname = fname.substring(0, 1).toUpperCase() + fname.substring(1);
        lname = lname.substring(0, 1).toUpperCase() + lname.substring(1);
        User user = new User(fname, lname, email, username, password, dateOfBirth, gender);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

//        return ResponseEntity.ok().body(new ApiResponse(200, "User registered successfully"));
    }
}
