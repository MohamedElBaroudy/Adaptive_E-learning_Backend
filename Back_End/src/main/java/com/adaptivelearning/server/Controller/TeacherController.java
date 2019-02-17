package com.adaptivelearning.server.Controller;

import com.adaptivelearning.server.FancyModel.FancyClassroom;
import com.adaptivelearning.server.FancyModel.FancyCourse;
import com.adaptivelearning.server.Model.*;
import com.adaptivelearning.server.Repository.*;
import com.adaptivelearning.server.Security.JwtTokenProvider;
import com.adaptivelearning.server.constants.Mapping;
import com.adaptivelearning.server.constants.Param;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
//@RequestMapping(Mapping.TEACHER)
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
    TeachingRequestRepository teachingRequestRepository;

    @Autowired
    JwtTokenProvider jwtTokenChecker;

    @PostMapping(Mapping.REQUEST)
    public  ResponseEntity<?> requestToTeach(@RequestParam(Param.ACCESS_TOKEN) String token){
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

        if(user.isTeacher())
            return new ResponseEntity<>("user is already a teacher",
                    HttpStatus.NOT_MODIFIED);

        TeachingRequest teachingRequest = new TeachingRequest(user.getUserId());
        teachingRequestRepository.save(teachingRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(Mapping.TEACHER_CLASSROOMS)
    public ResponseEntity<?> createClassroom(@RequestParam(Param.ACCESS_TOKEN) String token,
                     @Valid @RequestParam(Param.CLASSROOM_NAME) String classroomName) {

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
            return new ResponseEntity<>("user is not a teacher yet please make a request to be teacher",
                    HttpStatus.FORBIDDEN);
        
        //generate random passcode
        String passcode = RandomStringUtils.randomAlphanumeric(6, 8);
        while(classroomRepository.existsByPassCode(passcode)) {
        	passcode=RandomStringUtils.randomAlphanumeric(6, 8);
        }
        
        
        Classroom classRoom = new Classroom(classroomName, passcode);
        classRoom.setCreator(user);
//        classRoom.setPassCode(passwordEncoder.encode(classRoom.getPassCode()));
        classroomRepository.save(classRoom);
        
        return new ResponseEntity<>(classRoom.getPassCode(),HttpStatus.CREATED);
    }


    @GetMapping(Mapping.TEACHER_CLASSROOMS)
    public ResponseEntity<?>retrieveCreatedClassrooms(@RequestParam(Param.ACCESS_TOKEN) String token) {

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
       return new ResponseEntity<>(fancyClassroom.toFancyClassroomListMapping(user.getClassrooms()),
                HttpStatus.OK);
    }


    @PutMapping(Mapping.TEACHER_CLASSROOM)
    public ResponseEntity<?> updateClassroomPassCode(@RequestParam(Param.ACCESS_TOKEN) String token,
                                  @Valid @RequestParam(value = Param.CLASSROOM_ID,required = false) int classroomId) {
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

        if (classroom.getCreator().getUserId()!=
                (user.getUserId())) {
        	return new ResponseEntity<>("Not Allowed you are not a teacher or this is not your classroom to update",
                    HttpStatus.FORBIDDEN);
        }

//        classroom.setPassCode(passwordEncoder.encode(passcode));
        String passcode = RandomStringUtils.randomAlphanumeric(6, 8);
        while(classroomRepository.existsByPassCode(passcode)) {
        	passcode=RandomStringUtils.randomAlphanumeric(6, 8);
        }
        classroom.setPassCode(passcode);
        classroomRepository.save(classroom);
        return new ResponseEntity<>(passcode,HttpStatus.CREATED);
    }


    @DeleteMapping(Mapping.TEACHER_CLASSROOM)
    public ResponseEntity<?> deleteClassroom(@RequestParam(Param.ACCESS_TOKEN) String token,
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
    
    @PostMapping(Mapping.TEACHER_COURSES)
    public ResponseEntity<?> createCourse(@RequestParam(Param.ACCESS_TOKEN) String token,
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
            return new ResponseEntity<>("user is not a teacher yet please make a request to be teacher",
                    HttpStatus.FORBIDDEN);

        Course course=new Course(courseTitle, detailed_title, description, true, level,category);
        course.setPublisher(user);
        courseRepository.save(course);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    
    @PostMapping(Mapping.TEACHER_CLASSROOM_COURSES)
    public ResponseEntity<?> createClassroomCourse (@RequestParam(Param.ACCESS_TOKEN) String token,
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
        
 //       course.getClassrooms().add(classroom);
        classroom.getCourses().add(course); // the mapping will do it automatically
        
        courseRepository.save(course);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
   
    @PutMapping(Mapping.TEACHER_COURSES)
    public ResponseEntity<?> updateCourseInformation(@RequestParam(Param.ACCESS_TOKEN) String token,
                                                     @Valid @RequestParam(Param.COURSE_ID) int requiredCourseId ,
                                                     @Valid @RequestParam(value = Param.Title,required = false) String newTitle,
                                                     @Valid @RequestParam(value = Param.Detailed_title,required = false) String newDetailedtilte,
                                                     @Valid @RequestParam(value = Param.Description,required = false) String newDescription,
                                                     @Valid @RequestParam(value = Param.CATEGORY,required = false) String newCategory,
                                                     @Valid @RequestParam(value = Param.Level,required = false) Short newLevel) {

        User user = userRepository.findByToken(token);
        Course course = courseRepository.findByCourseId(requiredCourseId);

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
            return new ResponseEntity<>("user is not a teacher yet please make a request to be teacher",
                    HttpStatus.FORBIDDEN);
        
        if (course == null) {
            return new ResponseEntity<>(" course is not present ",
                    HttpStatus.NOT_FOUND);
        }

        if(course.getPublisher().getUserId() != user.getUserId()) {
            return new ResponseEntity<>("Not Allowed you are not a teacher or this is not your course to update",
                    HttpStatus.FORBIDDEN);
        }
        if(newTitle != null && !newTitle.isEmpty())
            course.setTitle(newTitle);
        if(newDetailedtilte != null && !newDetailedtilte.isEmpty())
            course.setDetailedTitle(newDetailedtilte);
        if(newDescription != null && !newDescription.isEmpty())
            course.setDescription(newDescription);
        if(newCategory != null && !newCategory.isEmpty())
            course.setCategory(newCategory);
        if(newLevel != null)
            course.setLevel(newLevel);
        courseRepository.save(course);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    
    @GetMapping(Mapping.TEACHER_COURSES)
    public ResponseEntity<?> retrieveCreatedCourses(@RequestParam(Param.ACCESS_TOKEN) String token) {

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
       return new ResponseEntity<>(fancyCourse.toFancyCourseListMapping(
               user.getCourses()), HttpStatus.OK);
    }
   
    @DeleteMapping(Mapping.TEACHER_COURSES)
    public ResponseEntity<?> deleteCourse(@RequestParam(Param.ACCESS_TOKEN) String token,
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
        return new ResponseEntity<>("Not Allowed you are not a teacher or this is not your course to delete",
                HttpStatus.FORBIDDEN);
    }

    courseRepository.deleteById(courseId);
    return new ResponseEntity<>("deleted",HttpStatus.NO_CONTENT);
}

/////////////////////////////////////// Sections Functions //////////////////////////////////////////////////////
    
    @PostMapping(Mapping.SECTION)
    public ResponseEntity<?> createSection(@RequestParam(Param.ACCESS_TOKEN) String token,
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
    public ResponseEntity<?> updateSectionInfo(@RequestParam(Param.ACCESS_TOKEN) String token,
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
    public ResponseEntity<?> deleteSection(@RequestParam(Param.ACCESS_TOKEN) String token,
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
        	 return new ResponseEntity<>("Section Is Not Present ",
                     HttpStatus.NOT_FOUND);
        }

        if (section.getCourse().getPublisher().getUserId()!=
                (user.getUserId())) {
        	return new ResponseEntity<>("Not Allowed you are not a teacher or this is not your section to update",
                    HttpStatus.FORBIDDEN);
        }
        sectionRepository.deleteById(section.getSectionId());
        return new ResponseEntity<>("section deleted",HttpStatus.NO_CONTENT);
    }
}
