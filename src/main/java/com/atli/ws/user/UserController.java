package com.atli.ws.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	/*
	@Autowired
	UserService userService; 

	@Autowired
	UserRepository userRepository;
		*/
	
	@PostMapping("/api/0.0.1/users")
	public void createUser(@RequestBody User user) {
		log.info(user.toString());
		//System.out.println(userRepository.toString());
		//userRepository.save(user);
	}


}
