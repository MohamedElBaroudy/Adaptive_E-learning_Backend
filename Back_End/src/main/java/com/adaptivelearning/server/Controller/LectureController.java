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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.adaptivelearning.server.FancyModel.FancyLecture;
import com.adaptivelearning.server.FancyModel.FancyMediaFile;
import com.adaptivelearning.server.FancyModel.FancyQuiz;
import com.adaptivelearning.server.Model.Lecture;
import com.adaptivelearning.server.Model.MediaFile;
import com.adaptivelearning.server.Model.Quiz;
import com.adaptivelearning.server.Model.Section;
import com.adaptivelearning.server.Model.User;
import com.adaptivelearning.server.Repository.LectureRepository;
import com.adaptivelearning.server.Repository.MediafileRepository;
import com.adaptivelearning.server.Repository.SectionRepository;
import com.adaptivelearning.server.Repository.UserRepository;
import com.adaptivelearning.server.Security.JwtTokenProvider;
import com.adaptivelearning.server.Service.FileStorageService;
import com.adaptivelearning.server.constants.Mapping;
import com.adaptivelearning.server.constants.Param;

@RestController
public class LectureController {
	
	 @Autowired
	    UserRepository userRepository;

	    @Autowired
	    SectionRepository sectionRepository;

	    @Autowired
	    LectureRepository lectureRepository;
	    
	    @Autowired
		MediafileRepository MediaFileRepository;

	    @Autowired
	    JwtTokenProvider jwtTokenChecker;
	    
	    @Autowired
		  private FileStorageService fileStorageService;
	     
	 
	    @PostMapping(Mapping.TEACHER_MEDIA)
	    public ResponseEntity<?> uploadfile(@RequestParam(Param.ACCESS_TOKEN) String token,
	                                        @Valid @RequestParam(Param.SECTION_ID) Long sectionId,
	                                        @RequestParam("file") MultipartFile file) throws IOException{
	        User user = userRepository.findByToken(token);

	        if(user == null){
	            return new ResponseEntity<>("user isn't logged in",
	                    HttpStatus.UNAUTHORIZED);
	        }
			if (!jwtTokenChecker.validateToken(token)) {
				user.setToken("");
				userRepository.save(user);
				return new ResponseEntity<>("session expired",
						HttpStatus.UNAUTHORIZED);
			}

	        Section section = sectionRepository.findBySectionId(sectionId);

	        if (section == null) {
	            return new ResponseEntity<>("Section Is Not Present ",
	                    HttpStatus.NOT_FOUND);
	        }

	        if (section.getCourse().getPublisher().getUserId()!=
	                (user.getUserId())) {
	            return new ResponseEntity<>("Not Allowed you are not a teacher or this is not your section to add quiz in",
	                    HttpStatus.FORBIDDEN);
	        }
	        
	        String fileName = fileStorageService.storeFile(file);

	         String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
	                 .path("/downloadFile/")
	                 .path(fileName)
	                 .toUriString();
	         MediaFile material=new MediaFile(fileName, file.getContentType(), fileDownloadUri, file.getSize());
	        
	        String mediaType=material.getFileType();
	        int index=mediaType.indexOf("/");
	        mediaType= mediaType.substring(0, index);
	        if (mediaType.equals("video")){ 
	        	 MediaFileRepository.save(material);
	        	Lecture lecture = new Lecture(false,true,false);
	            lecture.setSection(section);
	            material.setLecture(lecture);
	            lectureRepository.save(lecture);
	            return new ResponseEntity<>("video added ",HttpStatus.CREATED);
	         }
	        else if (mediaType.equals("application")) {
	          MediaFileRepository.save(material);
	          Lecture lecture = new Lecture(false,false,true);
	           lecture.setSection(section);
	           material.setLecture(lecture);
	           lectureRepository.save(lecture);
	           return new ResponseEntity<>("read file added",HttpStatus.CREATED);
	        }
	       
	        else {
	        	 return new ResponseEntity<>("this type of files not allowed ",HttpStatus.FORBIDDEN);
	        }
	       
	    }
	    @GetMapping(Mapping.TEACHER_MEDIA)
	    public ResponseEntity<?> retrieveFile(@RequestParam(Param.ACCESS_TOKEN) String token,
	                                          @Valid @RequestParam(Param.FILE_ID) Long fileId){
	        User user = userRepository.findByToken(token);

	        if(user == null){
	            return new ResponseEntity<>("user isn't logged in",
	                    HttpStatus.UNAUTHORIZED);
	        }
			if (!jwtTokenChecker.validateToken(token)) {
				user.setToken("");
				userRepository.save(user);
				return new ResponseEntity<>("session expired",
						HttpStatus.UNAUTHORIZED);
			}

	        MediaFile file = MediaFileRepository.findByFileId(fileId);
	       
	        if (file == null)
	            return new ResponseEntity<>("Not found file",HttpStatus.NOT_FOUND);
	        if(file.getLecture()==null) {
	        	 return new ResponseEntity<>("Not course file",HttpStatus.BAD_REQUEST);
	        }

	        if (!file.getLecture().getSection().getCourse().getPublisher().getUserId()
	                .equals(user.getUserId()) && !file.getLecture().getSection().getCourse().getLearners().contains(user))
	            return new ResponseEntity<>("Not Allowed you are not the creator of this file or a student of this course",
	                    HttpStatus.FORBIDDEN);
	        
	        FancyMediaFile fancyfile=new FancyMediaFile();
	        fancyfile= fancyfile.toFancyFileMapping(file);
	        return new ResponseEntity<>(fancyfile , HttpStatus.OK);
	    }

	    @DeleteMapping(Mapping.TEACHER_MEDIA)
	    public ResponseEntity<?> deleteFile(@RequestParam(Param.ACCESS_TOKEN) String token,
	                                        @Valid @RequestParam(Param.FILE_ID) Long fileId){
	        User user = userRepository.findByToken(token);

	        if(user == null){
	            return new ResponseEntity<>("user isn't logged in",
	                    HttpStatus.UNAUTHORIZED);
	        }
			if (!jwtTokenChecker.validateToken(token)) {
				user.setToken("");
				userRepository.save(user);
				return new ResponseEntity<>("session expired",
						HttpStatus.UNAUTHORIZED);
			}

	        MediaFile file = MediaFileRepository.findByFileId(fileId);

	        if (file == null)
	            return new ResponseEntity<>("Not found file",HttpStatus.NOT_FOUND);
	        
	        if(file.getLecture()==null) {
	        	 return new ResponseEntity<>("Not course file",HttpStatus.BAD_REQUEST);
	        }

	        if (!file.getLecture().getSection().getCourse().getPublisher().getUserId()
	                .equals(user.getUserId()))
	            return new ResponseEntity<>("Not Allowed you are not a teacher or this is not your file to delete",
	                    HttpStatus.FORBIDDEN);

	        MediaFileRepository.deleteById(fileId);

	        return new ResponseEntity<>(HttpStatus.ACCEPTED);
	    }

	    @GetMapping(Mapping.LECTURE)
	    public ResponseEntity<?> retrieveLecture(@RequestParam(Param.ACCESS_TOKEN) String token,
	    		                                  @Valid @RequestParam(Param.LECTURE_ID) Long lectureId) {

	        User user = userRepository.findByToken(token);
	        Lecture lecture = lectureRepository.findByLectureId(lectureId);

	        if(user == null){
	         	 return new ResponseEntity<>("user isn't logged in",HttpStatus.UNAUTHORIZED);
	        }

			if (!jwtTokenChecker.validateToken(token)) {
				user.setToken("");
				userRepository.save(user);
				return new ResponseEntity<>("session expired",
						HttpStatus.UNAUTHORIZED);
			}
	       
	        if(lecture == null){
	            return new ResponseEntity<>("lecture is not found",
	                    HttpStatus.NOT_FOUND);
	        }
	        
	        if (!lecture.getSection().getCourse().getPublisher().getUserId().equals(user.getUserId()) && 
	        		!lecture.getSection().getCourse().getLearners().contains(user) )
	        	return new ResponseEntity<>("Not Allowed you are not a teacher or student in this course",
	                    HttpStatus.FORBIDDEN);
	        
	        FancyLecture fancyLecture = new FancyLecture();
	        return new ResponseEntity<>(fancyLecture.toFancyLectureMapping(lecture),
	                HttpStatus.OK);
	    }
}
