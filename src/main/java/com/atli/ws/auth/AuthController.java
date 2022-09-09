package com.atli.ws.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atli.ws.shared.CurrentUser;
import com.atli.ws.user.User;
import com.atli.ws.user.UserRepository;
import com.atli.ws.user.vm.UserVM;


@RestController
public class AuthController {

	//private static final Logger log = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	UserRepository userRepository;
	
	
	@PostMapping("/api/0.0.1/auth")
	UserVM handleAuthentication(@CurrentUser User user){
		return new UserVM(user);
	}

}
