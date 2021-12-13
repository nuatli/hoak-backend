package com.atli.ws.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.atli.ws.shared.GenericResponse;

@RestController
public class UserController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	/*
	@Autowired
	UserService userService; 
	*/
	@Autowired
	UserService userService;
	
	@PostMapping("/api/0.0.1/users")
	public GenericResponse createUser(@RequestBody User user) {
		log.info(user.toString());
		//System.out.println(userRepository.toString());
		userService.save(user);
		return new GenericResponse("User Created");
	}

}