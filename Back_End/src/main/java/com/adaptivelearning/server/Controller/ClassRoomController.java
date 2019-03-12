package com.adaptivelearning.server.Controller;

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

import com.adaptivelearning.server.FancyModel.FancyClassroom;
import com.adaptivelearning.server.FancyModel.FancyMediaFile;
import com.adaptivelearning.server.Model.Classroom;
import com.adaptivelearning.server.Model.MediaFile;
import com.adaptivelearning.server.Model.User;
import com.adaptivelearning.server.Repository.ClassroomRepository;
import com.adaptivelearning.server.Repository.MediafileRepository;
import com.adaptivelearning.server.Repository.UserRepository;
import com.adaptivelearning.server.Security.JwtTokenProvider;
import com.adaptivelearning.server.Service.FileStorageService;
import com.adaptivelearning.server.constants.Mapping;
import com.adaptivelearning.server.constants.Param;


@RestController
public class ClassRoomController {

    @Autowired
    ClassroomRepository classroomRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
   	MediafileRepository MediaFileRepository;
    
    @Autowired
	  private FileStorageService fileStorageService;
 
    @Autowired
    JwtTokenProvider jwtTokenChecker;

    @GetMapping(Mapping.CLASSROOM)
    public ResponseEntity<?> retrieveEnrolledCourses(@RequestParam(Param.ACCESS_TOKEN) String token,
    		                                  @Valid @RequestParam(Param.CLASSROOM_ID) Long classroomId) {

        User user = userRepository.findByToken(token);
        Classroom classroom = classroomRepository.findByClassroomId(classroomId);

        if(user == null){
         	 return new ResponseEntity<>("User Is Not Valid",HttpStatus.UNAUTHORIZED);
        }

        if (!jwtTokenChecker.validateToken(token)) {
            user.setToken("");
            userRepository.save(user);
            return new ResponseEntity<>("Session Expired",
                    HttpStatus.UNAUTHORIZED);
        }
        if(classroom == null){
            return new ResponseEntity<>("classroom with this id is not found ",
                    HttpStatus.NOT_FOUND);
        }
        if(classroom.getCreator()!=user && !classroom.getStudents().contains(user)) {
        	 return new ResponseEntity<>("you are not allowed to see this classroom",
        			 HttpStatus.FORBIDDEN);
        }
        

        FancyClassroom fancyClassroom = new FancyClassroom();
        return new ResponseEntity<>(fancyClassroom.toFancyClassroomMapping(classroom),
                HttpStatus.OK);
    }
    @PostMapping("/classroomPic")
    public ResponseEntity<?> SetProfilePicture(@RequestParam(Param.ACCESS_TOKEN) String token,
    		                            @Valid @RequestParam(Param.CLASSROOM_ID) Long classroomId,
    		                                   @RequestParam("file") MultipartFile file)  {
    	
      
      User user = userRepository.findByToken(token);
      Classroom classroom=classroomRepository.findByClassroomId(classroomId);
      if (user == null)
          return new ResponseEntity<>("user isn't logged in",
                  HttpStatus.UNAUTHORIZED);

      if (!jwtTokenChecker.validateToken(token)) {
          user.setToken("");
          userRepository.save(user);
          return new ResponseEntity<>("session expired",
                  HttpStatus.UNAUTHORIZED);
      }
      
      if(classroom == null){
          return new ResponseEntity<>("classroom with this id is not found ",
                  HttpStatus.NOT_FOUND);
      }

      if(classroom.getCreator()!=user)
    	  return new ResponseEntity<>("only classroom creator can set the image",
                  HttpStatus.UNAUTHORIZED);
      
      String mediaType=file.getContentType();
      int index=mediaType.indexOf("/");
      mediaType= mediaType.substring(0, index);
      if (!mediaType.equals("image")){ 
       	  return new ResponseEntity<>("this file is not image ",
                     HttpStatus.FORBIDDEN);
         }
     
     else {
    	 if(classroom.getClassroom_picture()!=null) {
    		 MediaFileRepository.deleteById(classroom.getClassroom_picture().getFileId());
    	 }
     String fileName = fileStorageService.storeFile(file);
     String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
    	              .path("/downloadFile/")
    	              .path(fileName)
    	              .toUriString();
     MediaFile image=new MediaFile(fileName, file.getContentType(), fileDownloadUri, file.getSize());    	    
     MediaFileRepository.save(image);
      classroom.setClassroom_picture(image);
      classroomRepository.save(classroom);
      FancyMediaFile fancyfile=new FancyMediaFile();
      fancyfile= fancyfile.toFancyFileMapping(image);
      return new ResponseEntity<>(fancyfile , HttpStatus.OK);
    }
      }



}
