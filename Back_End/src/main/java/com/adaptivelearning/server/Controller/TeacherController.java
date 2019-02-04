package com.adaptivelearning.server.Controller;

import com.adaptivelearning.server.FancyModel.FancyClassroom;
import com.adaptivelearning.server.FancyModel.FancyCourse;
import com.adaptivelearning.server.Model.Classroom;
import com.adaptivelearning.server.Model.Course;
import com.adaptivelearning.server.Model.Section;
import com.adaptivelearning.server.Model.User;
import com.adaptivelearning.server.Repository.ClassroomRepository;
import com.adaptivelearning.server.Repository.CourseRepository;
import com.adaptivelearning.server.Repository.SectionRepository;
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
@RequestMapping(Mapping.TEACHER)
public class TeacherController {
	
	@Autowired
	SectionRepository sectionRepository ;

    @Autowired
    ClassroomRepository classroomRepository;
   
    @Autowired
    CourseRepository courseRepository;

    
    @Autowired
    UserRepository userRepository;


    @Autowired
    JwtTokenProvider jwtTokenChecker;


    @PostMapping(Mapping.CLASSROOMS)
    public ResponseEntity<?> createClassroom(@RequestParam(Param.ACCESSTOKEN) String token,
                     @Valid @RequestParam(Param.CLASSROOMNAME) String classroomName,
                     @Valid @RequestParam(Param.PASSCODE) String passcode) {

        User user = userRepository.findByToken(token);

        if(user == null){
        	 return new ResponseEntity<>(" user is not present ",
                     HttpStatus.UNAUTHORIZED);
        }
        if (!jwtTokenChecker.validateToken(token)) {
        	 return new ResponseEntity<>("invalid token ",
                     HttpStatus.UNAUTHORIZED);
        }

        if(user.isChild()){
        	 return new ResponseEntity<>("user is child it's not allowed ",
                     HttpStatus.FORBIDDEN);
        }

        if(!user.isTeacher())
            user.setTeacher(true);

        Classroom classRoom = new Classroom(classroomName, passcode);
        classRoom.setCreator(user);
//        classRoom.setPassCode(passwordEncoder.encode(classRoom.getPassCode()));
        classroomRepository.save(classRoom);
        
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @GetMapping(Mapping.CLASSROOMS)
    public ResponseEntity<?>retrieveCreatedClassrooms(@RequestParam(Param.ACCESSTOKEN) String token) {

        User user = userRepository.findByToken(token);

        if(user == null){
        	 return new ResponseEntity<>(" user is not present ",
                     HttpStatus.UNAUTHORIZED);
        }
        if (!jwtTokenChecker.validateToken(token)) {
        	 return new ResponseEntity<>(" invalid token ",
                     HttpStatus.UNAUTHORIZED);
        }

        FancyClassroom fancyClassroom = new FancyClassroom();
       return new ResponseEntity<>(fancyClassroom.toClassroomIdListMapping(user.getClassrooms()),
                HttpStatus.OK);
    }


    @PutMapping(Mapping.CLASSROOM)
    public ResponseEntity<?> updateClassroomInfo(@RequestParam(Param.ACCESSTOKEN) String token,
                                  @Valid @RequestParam(value = Param.CLASSROOMNAME,required = false) String classroomName,
                                  @Valid @RequestParam(value = Param.PASSCODE,required = false) String passcode) {
        User user = userRepository.findByToken(token);

        if(user == null){
        	 return new ResponseEntity<>(" user is not present ",
                     HttpStatus.UNAUTHORIZED);
        }
        if (!jwtTokenChecker.validateToken(token)) {
        	 return new ResponseEntity<>(" invalid token ",
                     HttpStatus.UNAUTHORIZED);
        }

        Classroom classroom = classroomRepository.findByClassroomName(classroomName);

        if (classroom == null) {
        	 return new ResponseEntity<>(" classroom is not present ",
                     HttpStatus.NOT_FOUND);
        }

        if (classroom.getCreator().getUserId()!=
                (user.getUserId())) {
        	return new ResponseEntity<>("Not Allowed you are not a teacher or this is not your classroom to update",
                    HttpStatus.FORBIDDEN);
        }

//        classroom.setPassCode(passwordEncoder.encode(passcode));
        classroom.setPassCode(passcode);
        classroomRepository.save(classroom);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @DeleteMapping(Mapping.CLASSROOM)
    public ResponseEntity<?> deleteClassroom(@RequestParam(Param.ACCESSTOKEN) String token,
                @Valid @RequestParam(Param.CLASSROOM_ID) Integer classroomId) {

        User user = userRepository.findByToken(token);

        if(user == null){
            return new ResponseEntity<>(" user is not present ",
                    HttpStatus.UNAUTHORIZED);
        }
        if (!jwtTokenChecker.validateToken(token)) {
            return new ResponseEntity<>(" invalid token ",
                    HttpStatus.UNAUTHORIZED);
        }

        Classroom classroom = classroomRepository.findByClassroomId(classroomId);

        if (classroom == null) {
            return new ResponseEntity<>(" classroom is not present ",
                    HttpStatus.NOT_FOUND);
        }

        if(classroom.getCreator().getUserId() != user.getUserId()) {
            return new ResponseEntity<>("Not Allowed you are not a teacher or this is not your classroom to update",
                    HttpStatus.FORBIDDEN);
        }

        classroomRepository.deleteById(classroomId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    
    ////////////////////////////////////////////////////Courses Functions/////////////////////////////////////////////////////////////////////////
    
    @PostMapping(Mapping.COURSES)
    public ResponseEntity<?> createCourse(@RequestParam(Param.ACCESSTOKEN) String token,
                                          @Valid @RequestParam(Param.Title) String courseTitle,
                                          @Valid @RequestParam(Param.Detailed_title) String detailed_title,
                                          @Valid @RequestParam(Param.Description) String description,
                                          @Valid @RequestParam(Param.CATEGORY) String category,
                                          @Valid @RequestParam(Param.Level) short level) {

        User user = userRepository.findByToken(token);

        if(user == null){
        	  return new ResponseEntity<>("user is not present",
                      HttpStatus.UNAUTHORIZED);
        }
        if (!jwtTokenChecker.validateToken(token)) {
        	  return new ResponseEntity<>("invalid token",
                      HttpStatus.UNAUTHORIZED);
        }

        if(user.getParent() != null){
        	 return new ResponseEntity<>("user is child it's not allowed ",
                     HttpStatus.FORBIDDEN);
        }

        if(!user.isTeacher())
            user.setTeacher(true);

        Course course=new Course(courseTitle, detailed_title, description, true, level,category);
        course.setPublisher(user);
        courseRepository.save(course);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PostMapping(Mapping.CLASSROOM_COURSES)
    public ResponseEntity<?> createClassroomCourse (@RequestParam(Param.ACCESSTOKEN) String token,
                                                    @Valid @RequestParam(Param.CLASSROOM_ID) int classroomId,
                                                    @Valid @RequestParam(Param.Title) String courseTitle,
                                                    @Valid @RequestParam(Param.Detailed_title) String detailed_title,
                                                    @Valid @RequestParam(Param.Description) String description,
                                                    @Valid @RequestParam(Param.CATEGORY) String category,
                                                    @Valid @RequestParam(Param.Level) short level) {

        User user = userRepository.findByToken(token);
        Classroom classroom=classroomRepository.findByClassroomId(classroomId);
        if(user == null){
        	 return new ResponseEntity<>("user is not present",
                     HttpStatus.UNAUTHORIZED);
        }
        if (!jwtTokenChecker.validateToken(token)) {
            return new ResponseEntity<>("invalid token",
                    HttpStatus.UNAUTHORIZED);
        }

        if(classroom == null){
      	  return new ResponseEntity<>("classroom is not present",
                  HttpStatus.NOT_FOUND);
        }

        if(user.getParent() != null){
        	 return new ResponseEntity<>("user is child it's not allowed ",
                     HttpStatus.FORBIDDEN);
        }
        
        if(user.getUserId()!= classroom.getCreator().getUserId()) {
        	 return new ResponseEntity<>("this classroom not belongs to this user",
                     HttpStatus.FORBIDDEN);
        }

        Course course=new Course(courseTitle, detailed_title, description, false , level,category);
        course.setPublisher(user);
        
        course.getClassrooms().add(classroom);
//        classroom.getCourses().add(course); // the mapping will do it automatically
        
        courseRepository.save(course);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
   
    @GetMapping(Mapping.COURSES)
    public ResponseEntity<?>retrieveCreatedCourses(@RequestParam(Param.ACCESSTOKEN) String token) {

        User user = userRepository.findByToken(token);

        if(user == null){
        	 return new ResponseEntity<>(" user is not present ",
                     HttpStatus.UNAUTHORIZED);
        }
        if (!jwtTokenChecker.validateToken(token)) {
        	 return new ResponseEntity<>(" invalid token ",
                     HttpStatus.UNAUTHORIZED);
        }

        FancyCourse fancyCourse = new FancyCourse();
       return new ResponseEntity<>(fancyCourse.toCourseIdListMapping(user.getCourses()), HttpStatus.OK);
    }
   
    @DeleteMapping(Mapping.COURSES)
    public ResponseEntity<?> deleteCourse(@RequestParam(Param.ACCESSTOKEN) String token,
            @Valid @RequestParam(Param.COURSE_ID) Integer courseId) {

    User user = userRepository.findByToken(token);

    if(user == null){
        return new ResponseEntity<>(" user is not present ",
                HttpStatus.UNAUTHORIZED);
    }
    if (!jwtTokenChecker.validateToken(token)) {
        return new ResponseEntity<>(" invalid token ",
                HttpStatus.UNAUTHORIZED);
    }

    Course course = courseRepository.findByCourseId(courseId);

    if (course == null) {
        return new ResponseEntity<>(" course is not present ",
                HttpStatus.NOT_FOUND);
    }

    if(course.getPublisher().getUserId() != user.getUserId()) {
        return new ResponseEntity<>("Not Allowed you are not a teacher or this is not your course to update",
                HttpStatus.FORBIDDEN);
    }

    courseRepository.deleteById(courseId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
}

/////////////////////////////////////// FancySection Functions //////////////////////////////////////////////////////
    
    @PostMapping(Mapping.SECTION)
    public ResponseEntity<?> createSection(@RequestParam(Param.ACCESSTOKEN) String token,
          	         @Valid @RequestParam(Param.COURSE_ID) int courseId,
                     @Valid @RequestParam(Param.SECTION_TITLE) String sectionTitle) {

        User user = userRepository.findByToken(token);
        Course course = courseRepository.findByCourseId(courseId) ;

        if(user == null){
        	  return new ResponseEntity<>("user is not present",
                      HttpStatus.UNAUTHORIZED);
        }
        if (!jwtTokenChecker.validateToken(token)) {
        	  return new ResponseEntity<>("invalid token",
                      HttpStatus.UNAUTHORIZED);
        }
        
        if(user.getParent() != null){
       	 return new ResponseEntity<>("user is child it's not allowed ",
                    HttpStatus.FORBIDDEN);
       }

        if(course == null){
      	  return new ResponseEntity<>("The FancyCourse Is Not Present",
                    HttpStatus.NOT_FOUND);
      }

        Section section=new Section(sectionTitle);
        section.setCourse(course);
        sectionRepository.save(section);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(Mapping.SECTION)
    public ResponseEntity<?> updateSectionInfo(@RequestParam(Param.ACCESSTOKEN) String token,
                                  @Valid @RequestParam(value = Param.SECTION_ID) int sectionId,
                                  @Valid @RequestParam(value = Param.SECTION_TITLE) String NewsectionTitle) {
                                   
        User user = userRepository.findByToken(token);

        if(user == null){
        	 return new ResponseEntity<>(" user is not present ",
                     HttpStatus.UNAUTHORIZED);
        }
        if (!jwtTokenChecker.validateToken(token)) {
        	 return new ResponseEntity<>(" invalid token ",
                     HttpStatus.UNAUTHORIZED);
        }

        Section section = sectionRepository.findBySectionId(sectionId);

        if (section == null) {
        	 return new ResponseEntity<>(" FancySection Is Not Present ",
                     HttpStatus.NOT_FOUND);
        }

        if (section.getCourse().getPublisher().getUserId()!=
                (user.getUserId())) {
        	return new ResponseEntity<>("Not Allowed you are not a teacher or this is not your section to update",
                    HttpStatus.FORBIDDEN);
        }

        section.setTitle(NewsectionTitle);
        sectionRepository.save(section);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    

    @DeleteMapping(Mapping.SECTION)
    public ResponseEntity<?> deleteSection(@RequestParam(Param.ACCESSTOKEN) String token,
                                 @Valid @RequestParam(value = Param.SECTION_ID) Integer sectionId){
        User user = userRepository.findByToken(token);

        if(user == null){
        	 return new ResponseEntity<>(" user is not present ",
                     HttpStatus.UNAUTHORIZED);
        }
        if (!jwtTokenChecker.validateToken(token)) {
        	 return new ResponseEntity<>(" invalid token ",
                     HttpStatus.UNAUTHORIZED);
        }

        Section section = sectionRepository.findBySectionId(sectionId);

        if (section == null) {
        	 return new ResponseEntity<>(" FancySection Is Not Present ",
                     HttpStatus.NOT_FOUND);
        }

        if (section.getCourse().getPublisher().getUserId()!=
                (user.getUserId())) {
        	return new ResponseEntity<>("Not Allowed you are not a teacher or this is not your section to update",
                    HttpStatus.FORBIDDEN);
        }
        sectionRepository.deleteById(section.getSectionId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
