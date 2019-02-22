package com.adaptivelearning.server.Controller;

import com.adaptivelearning.server.FancyModel.FancyClassroom;
import com.adaptivelearning.server.FancyModel.FancyCourse;
import com.adaptivelearning.server.FancyModel.FancySection;
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
    CategoryRepository categoryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TeachingRequestRepository teachingRequestRepository;

    @Autowired
    JwtTokenProvider jwtTokenChecker;

    @PostMapping(Mapping.REQUEST_TEACHING)
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

        TeachingRequest teachingRequest = new TeachingRequest(user.getUserId(),
                user.getFirstName()+" "+user.getLastName(),
                user.getEmail());
        teachingRequestRepository.save(teachingRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(Mapping.REQUEST_CATEGORY)
    public  ResponseEntity<?> requestCategory(@RequestParam(Param.ACCESS_TOKEN) String token,
                                              @Valid @RequestParam(Param.CATEGORY) String categoryStr){
        User user = userRepository.findByToken(token);

        if(user == null){
            return new ResponseEntity<>(" user is not present ",
                    HttpStatus.UNAUTHORIZED);
        }
        if (!jwtTokenChecker.validateToken(token)) {
            return new ResponseEntity<>("invalid token ",
                    HttpStatus.UNAUTHORIZED);
        }

        if(!user.isTeacher()){
            return new ResponseEntity<>("user is not a teacher it's not allowed ",
                    HttpStatus.FORBIDDEN);
        }

        Category foundCategory = categoryRepository.findByName(categoryStr);

        if (foundCategory != null){
            if (foundCategory.isApproved())
                return new ResponseEntity<>("Already found and approved",
                        HttpStatus.FORBIDDEN);
            else
                return new ResponseEntity<>("Already requested for approval",
                        HttpStatus.NOT_MODIFIED);
        }

        Category category = new Category(categoryStr,false);
        categoryRepository.save(category);
        return new ResponseEntity<>(HttpStatus.CREATED);
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
                                  @Valid @RequestParam(value = Param.CLASSROOM_ID,required = false) Long classroomId) {
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
                @Valid @RequestParam(Param.CLASSROOM_ID) Long classroomId) {

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
                                          @Valid @RequestParam(Param.CATEGORY) String categoryName,
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

//        if(!user.isTeacher())
//            return new ResponseEntity<>("user is not a teacher yet please make a request to be teacher",
//                    HttpStatus.FORBIDDEN);

        Category category = categoryRepository.findByName(categoryName);

        if (category == null)
            return new ResponseEntity<>("Category is not found",
                    HttpStatus.NOT_FOUND);

        if (!category.isApproved())
            return new ResponseEntity<>("Category is not approved yet",
                    HttpStatus.BAD_REQUEST);

        Course course=new Course(courseTitle, detailed_title, description, true, level);
        course.setPublisher(user);
        course.setCategory(category);
        courseRepository.save(course);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    
    @PostMapping(Mapping.TEACHER_CLASSROOM_COURSES)
    public ResponseEntity<?> createClassroomCourse (@RequestParam(Param.ACCESS_TOKEN) String token,
                                                    @Valid @RequestParam(Param.CLASSROOM_ID) Long classroomId,
                                                    @Valid @RequestParam(Param.Title) String courseTitle,
                                                    @Valid @RequestParam(Param.Detailed_title) String detailed_title,
                                                    @Valid @RequestParam(Param.Description) String description,
                                                    @Valid @RequestParam(Param.CATEGORY) String categoryName,
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

        Category category = categoryRepository.findByName(categoryName);

        if (category == null)
            return new ResponseEntity<>("Category is not found",
                    HttpStatus.NOT_FOUND);
        if (!category.isApproved())
            return new ResponseEntity<>("Category is not approved yet",
                    HttpStatus.BAD_REQUEST);

        Course course=new Course(courseTitle, detailed_title, description, false , level);
        course.setPublisher(user);
        course.setCategory(category);
        
 //       course.getClassrooms().add(classroom);
        classroom.getCourses().add(course); // the mapping will do it automatically
        
        courseRepository.save(course);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
   
    @PutMapping(Mapping.TEACHER_COURSES)
    public ResponseEntity<?> updateCourseInformation(@RequestParam(Param.ACCESS_TOKEN) String token,
                                                     @Valid @RequestParam(Param.COURSE_ID) Long requiredCourseId ,
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
        if(newCategory != null && !newCategory.isEmpty()) {
            Category category = categoryRepository.findByName(newCategory);
            if (category == null)
                return new ResponseEntity<>("Category is not found",
                        HttpStatus.NOT_FOUND);
            if (!category.isApproved())
                return new ResponseEntity<>("Category is not approved yet",
                        HttpStatus.BAD_REQUEST);
            course.setCategory(category);
        }
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
            @Valid @RequestParam(Param.COURSE_ID) Long courseId) {

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
          	         @Valid @RequestParam(Param.COURSE_ID) Long courseId,
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
                                  @Valid @RequestParam(value = Param.SECTION_ID) Long sectionId,
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
                                 @Valid @RequestParam(value = Param.SECTION_ID) Long sectionId){
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

    @GetMapping(Mapping.SECTION)
    public ResponseEntity<?> retrieveSection(@RequestParam(Param.ACCESS_TOKEN) String token,
                                           @Valid @RequestParam(value = Param.SECTION_ID) Long sectionId){
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

        if (!section.getCourse().getPublisher().getUserId()
                .equals(user.getUserId()) && !section.getCourse().getLearners().contains(user)) {
            return new ResponseEntity<>("Not Allowed you are not a teacher or this is not your section to update",
                    HttpStatus.FORBIDDEN);
        }

        FancySection fancySection = new FancySection();
        return new ResponseEntity<>(fancySection.toFancySectionMapping(section),HttpStatus.OK);
    }
}
