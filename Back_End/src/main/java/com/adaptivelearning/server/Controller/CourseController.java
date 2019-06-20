package com.adaptivelearning.server.Controller;

import com.adaptivelearning.server.FancyModel.FancyCourse;
import com.adaptivelearning.server.FancyModel.FancyMediaFile;
import com.adaptivelearning.server.FancyModel.FancyUser;
import com.adaptivelearning.server.Model.Course;
import com.adaptivelearning.server.Model.MediaFile;
import com.adaptivelearning.server.Model.User;
import com.adaptivelearning.server.Repository.CourseRepository;
import com.adaptivelearning.server.Repository.MediafileRepository;
import com.adaptivelearning.server.Repository.UserRepository;
import com.adaptivelearning.server.Security.JwtTokenProvider;
import com.adaptivelearning.server.Service.FileStorageService;

import java.io.IOException;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.adaptivelearning.server.constants.Mapping;
import com.adaptivelearning.server.constants.Param;



@RestController
public class CourseController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    CourseRepository courseRepository;
    
    @Autowired
	MediafileRepository MediaFileRepository;

    @Autowired
    JwtTokenProvider jwtTokenChecker;

    @Autowired
	  private FileStorageService fileStorageService;

    @GetMapping(Mapping.COURSE)
    public ResponseEntity<?> retrieveCourse(@RequestParam(Param.ACCESS_TOKEN) String token,
    		                                  @Valid @RequestParam(Param.COURSE_ID) Long courseId) {

        User user = userRepository.findByToken(token);
        Course course = courseRepository.findByCourseId(courseId);

        if(user == null){
         	 return new ResponseEntity<>("User Is Not Valid",HttpStatus.UNAUTHORIZED);
        }

        if (!jwtTokenChecker.validateToken(token)) {
            user.setToken("");
            userRepository.save(user);
            return new ResponseEntity<>("session expired",
                    HttpStatus.UNAUTHORIZED);
        }
        
        if(course == null){
            return new ResponseEntity<>("course with this id is not found ",
                    HttpStatus.NOT_FOUND);
        }

        
        FancyCourse fancyCourse = new FancyCourse();
        return new ResponseEntity<>(fancyCourse.toFancyCourseMapping(course, user),
                HttpStatus.OK);
    }

    @GetMapping(Mapping.COURSE_STUDENTS)
    public ResponseEntity<?> retrieveCourseStudents(@RequestParam(Param.ACCESS_TOKEN) String token,
                                            @Valid @RequestParam(Param.COURSE_ID) Long courseId) {

        User user = userRepository.findByToken(token);
        Course course = courseRepository.findByCourseId(courseId);

        if(user == null){
            return new ResponseEntity<>("User Is Not Valid",HttpStatus.UNAUTHORIZED);
        }

        if (!jwtTokenChecker.validateToken(token)) {
            user.setToken("");
            userRepository.save(user);
            return new ResponseEntity<>("session expired",
                    HttpStatus.UNAUTHORIZED);
        }

        if(course == null){
            return new ResponseEntity<>("course with this id is not found ",
                    HttpStatus.NOT_FOUND);
        }

        if(course.getPublisher() != user)
            return new ResponseEntity<>("This is not your course to show students",
                    HttpStatus.FORBIDDEN);

        FancyUser fancyUser = new FancyUser();
        return new ResponseEntity<>(fancyUser.toFancyUserListMapping(course.getLearners()),
                HttpStatus.OK);
    }

    @PostMapping(Mapping.SAVED_COURSES)
    public ResponseEntity<?> saveCourse(@RequestParam(Param.ACCESS_TOKEN) String token,
                                        @Valid @RequestParam(Param.COURSE_ID) Long courseId) {

        User user = userRepository.findByToken(token);
        Course course = courseRepository.findByCourseId(courseId);

        if (user == null) {
            return new ResponseEntity<>("User is not present ",
                    HttpStatus.UNAUTHORIZED);
        }
        if (!jwtTokenChecker.validateToken(token)) {
            user.setToken("");
            userRepository.save(user);
            return new ResponseEntity<>("session expired",
                    HttpStatus.UNAUTHORIZED);
        }
        if (course == null) {
            return new ResponseEntity<>(" course with this id is not found ",
                    HttpStatus.NOT_FOUND);
        }

        /*if(user.getParent() != null){
        	 return new ResponseEntity<>("user is child it's not allowed ",
                     HttpStatus.FORBIDDEN);
        } */ // assuming that the child can save course

        if (course.getPublisher().getUserId() == user.getUserId()) {
            return new ResponseEntity<>("course publisher can't save his courses",
                    HttpStatus.FORBIDDEN);
        }
        if (user.getSavedCourses().contains(course)) {
            return new ResponseEntity<>("Already Saved ",
                    HttpStatus.FORBIDDEN);
        }
        course.getSavedBy().add(user);
        courseRepository.save(course);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(Mapping.SAVED_COURSES)
    public ResponseEntity<?> removeSavedCourse(@RequestParam(Param.ACCESS_TOKEN) String token,
                                        @Valid @RequestParam(Param.COURSE_ID) Long courseId) {

        User user = userRepository.findByToken(token);
        Course course=courseRepository.findByCourseId(courseId);

        if(user == null){
            return new ResponseEntity<>("User is not present ",
                    HttpStatus.UNAUTHORIZED);
        }
        if (!jwtTokenChecker.validateToken(token)) {
            user.setToken("");
            userRepository.save(user);
            return new ResponseEntity<>("session expired",
                    HttpStatus.UNAUTHORIZED);
        }
        if(course == null){
            return new ResponseEntity<>(" course with this id is not found ",
                    HttpStatus.NOT_FOUND);
        }

        /*if(user.getParent() != null){
        	 return new ResponseEntity<>("user is child it's not allowed ",
                     HttpStatus.FORBIDDEN);
        } */ // assuming that the child can save course

        if(course.getPublisher().getUserId()==user.getUserId()) {
            return new ResponseEntity<>("course publisher can't save his courses",
                    HttpStatus.FORBIDDEN);
        }
        if(!user.getSavedCourses().contains(course)) {
            return new ResponseEntity<>("Already removed",
                    HttpStatus.FORBIDDEN);
        }

        course.getSavedBy().remove(user);
        courseRepository.save(course);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(Mapping.SAVED_COURSES)
    public ResponseEntity<?> retrieveSavedCourses(@RequestParam(Param.ACCESS_TOKEN) String token) {

        User user = userRepository.findByToken(token);

        if(user == null){
            return new ResponseEntity<>("User Is Not Valid",
                    HttpStatus.UNAUTHORIZED);
        }
        if (!jwtTokenChecker.validateToken(token)) {
            user.setToken("");
            userRepository.save(user);
            return new ResponseEntity<>("session expired",
                    HttpStatus.UNAUTHORIZED);
        }
        FancyCourse fancyCourse = new FancyCourse();
        return new ResponseEntity<>(fancyCourse.toFancyCourseListMapping(user.getSavedCourses(), user),
                HttpStatus.OK);
    }
    
    @PostMapping("/coursePic")
    public ResponseEntity<?> SetProfilePicture(@RequestParam(Param.ACCESS_TOKEN) String token,
    		                            @Valid @RequestParam(Param.COURSE_ID) Long courseId,
    		                                   @RequestParam("file") MultipartFile file) {


      User user = userRepository.findByToken(token);
      Course course=courseRepository.findByCourseId(courseId);
      if (user == null)
          return new ResponseEntity<>("user isn't logged in",
                  HttpStatus.UNAUTHORIZED);

      if (!jwtTokenChecker.validateToken(token)) {
          user.setToken("");
          userRepository.save(user);
          return new ResponseEntity<>("session expired",
                  HttpStatus.UNAUTHORIZED);
      }
      if(course == null){
          return new ResponseEntity<>("course with this id is not found ",
                  HttpStatus.NOT_FOUND);
      }

      if(course.getPublisher()!=user)
    	  return new ResponseEntity<>("only course publisher can set the image",
                  HttpStatus.UNAUTHORIZED);
      
      String mediaType=file.getContentType();
      int index=mediaType.indexOf("/");
      mediaType= mediaType.substring(0, index);
      if (!mediaType.equals("image")){ 
       	  return new ResponseEntity<>("this file is not image ",
                     HttpStatus.FORBIDDEN);
         }
     
     else {
    	 if(course.getCourse_picture()!=null) {
    		 MediaFileRepository.deleteById(course.getCourse_picture().getFileId());
    	 }
     String fileName = fileStorageService.storeFile(file);
     String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
    	              .path("/downloadFile/")
    	              .path(fileName)
    	              .toUriString();
     MediaFile image=new MediaFile(fileName, file.getContentType(), fileDownloadUri, file.getSize());    	    
      MediaFileRepository.save(image);
      course.setCourse_picture(image);
      courseRepository.save(course);
      FancyMediaFile fancyfile=new FancyMediaFile();
      fancyfile= fancyfile.toFancyFileMapping(image);
      return new ResponseEntity<>(fancyfile , HttpStatus.OK);
     }
   }
}
