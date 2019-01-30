package com.adaptivelearning.server.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adaptivelearning.server.Repository.UserRepository;
import com.adaptivelearning.server.constants.Mapping;

@RestController
@RequestMapping(Mapping.COURSE)
public class CourseController {
	 @Autowired
	    UserRepository userRepository;
	
}
