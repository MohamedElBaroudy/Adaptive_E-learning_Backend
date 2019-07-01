package com.adaptivelearning.server.Controller;

import com.adaptivelearning.server.FancyModel.FancyUser;
import com.adaptivelearning.server.Model.MediaFile;
import com.adaptivelearning.server.Model.User;
import com.adaptivelearning.server.Repository.MediafileRepository;
import com.adaptivelearning.server.Repository.UserRepository;
import com.adaptivelearning.server.Security.JwtTokenProvider;
import com.adaptivelearning.server.constants.Mapping;
import com.adaptivelearning.server.constants.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
//@RequestMapping(Mapping.AUTH)
public class UserController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
	MediafileRepository MediaFileRepository;

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
                    HttpStatus.UNAUTHORIZED);

        User user = userRepository.findByEmailOrUsername(email,username);

        if (user == null)
            return new ResponseEntity<>("user is not present",
                    HttpStatus.UNAUTHORIZED);


        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken( user.getEmail(),password)
        );

        // already logged in
        String token = user.getToken();
        if(token != null && !token.isEmpty() && tokenProvider.validateToken(token))
            return new ResponseEntity<>(token,
                    HttpStatus.OK);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);

        user.setToken(jwt);

        userRepository.save(user);

        return new ResponseEntity<>(jwt,HttpStatus.OK);
    }

    @GetMapping(Mapping.PROFILE)
    public ResponseEntity<?> getUserProfile(@RequestParam(Param.ACCESS_TOKEN) String token){

        User user = userRepository.findByToken(token);

        if (user == null)
            return new ResponseEntity<>("user isn't logged in",
                    HttpStatus.UNAUTHORIZED);

        if (!tokenProvider.validateToken(token))
            return new ResponseEntity<>("session expired",
                    HttpStatus.UNAUTHORIZED);

        FancyUser fancyUser = new FancyUser();
        fancyUser.toFancyUserMapper(user);

        return new ResponseEntity<>(fancyUser,HttpStatus.OK);
    }

    @GetMapping(Mapping.LOGOUT)
    public ResponseEntity<?> kickOutUser(@RequestParam(Param.ACCESS_TOKEN) String token){

        User user = userRepository.findByToken(token);

        if (user == null)
            return new ResponseEntity<>("user isn't logged in",
                    HttpStatus.UNAUTHORIZED);

        if (!tokenProvider.validateToken(token))
            return new ResponseEntity<>("session expired",
                    HttpStatus.UNAUTHORIZED);

        user.setToken(null);
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PostMapping(Mapping.REGISTER)
    public ResponseEntity<?> registerUser(@Valid @RequestParam(Param.FIRST_NAME) String fname,
                             @Valid @RequestParam(Param.LAST_NAME) String lname,
                             @Valid @RequestParam(Param.DATE_OF_BIRTH) String dob,
                             @Valid @RequestParam(Param.USERNAME) String username,
                             @Valid @RequestParam(Param.EMAIL) String email,
                             @Valid @RequestParam(Param.PASSWORD) String password,
                             @Valid @RequestParam(Param.GENDER) short gender){

        if (userRepository.existsByEmail(email))
            return new ResponseEntity<>("Email is used",HttpStatus.CONFLICT);

        if (userRepository.existsByUsername(username))
            return new ResponseEntity<>("Username is used",HttpStatus.CONFLICT);


        // Creating user's account
        final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateOfBirth = LocalDate.parse(dob,dtf);
        fname = fname.substring(0, 1).toUpperCase() + fname.substring(1);
        lname = lname.substring(0, 1).toUpperCase() + lname.substring(1);
        User user = new User(fname, lname, email, username, password, dateOfBirth, gender);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        FancyUser fancyUser = new FancyUser();
        fancyUser.toFancyUserMapper(user);

        return new ResponseEntity<>(fancyUser,HttpStatus.OK);
    }
    
    @PostMapping("/profilePic")
    public ResponseEntity<?> SetProfilePicture(@RequestParam(Param.ACCESS_TOKEN) String token,
    		                                   @RequestParam("file") MultipartFile file) throws IOException {
    	
      
      User user = userRepository.findByToken(token);

      if (user == null)
          return new ResponseEntity<>("user isn't logged in",
                  HttpStatus.UNAUTHORIZED);

      if (!tokenProvider.validateToken(token))
    	  return new ResponseEntity<>("session expired",
                  HttpStatus.UNAUTHORIZED);
      
      // Normalize file name
      String fileName = StringUtils.cleanPath(file.getOriginalFilename());
      
      if(fileName.contains("..")) {
      	return new ResponseEntity("Sorry! Filename contains invalid path sequence ",HttpStatus.BAD_REQUEST);
      }
      MediaFile image = new MediaFile(fileName, file.getContentType(), file.getBytes());

      MediaFileRepository.save(image);
      user.setProfile_picture(image);
      image.setProfile(user);
      userRepository.save(user);
      return ResponseEntity.ok()
              .contentType(MediaType.parseMediaType(image.getFileType()))
              .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
              .body(new ByteArrayResource(image.getData()));
    }
    
}
