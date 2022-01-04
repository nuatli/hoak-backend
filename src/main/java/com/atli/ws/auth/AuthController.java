package com.atli.ws.auth;

import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.atli.ws.error.ApiError;
import com.atli.ws.shared.Views;
import com.atli.ws.user.User;
import com.atli.ws.user.UserRepository;
import com.fasterxml.jackson.annotation.JsonView;


@RestController
public class AuthController {

	private static final Logger log = LoggerFactory.getLogger(AuthController.class);
	/*
	@PostMapping("/api/0.0.1/auth")
	void handleAuthentication(@RequestHeader(name="Authorization",required=false) String authorization){
		log.info(authorization);
	}
	*/
	@Autowired
	UserRepository userRepository;
	
	PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	
	@PostMapping("/api/0.0.1/auth")
	@JsonView(Views.Sensitive.class)
	ResponseEntity<?> handleAuthentication(@RequestHeader(name="Authorization",required=false) String authorization){
		if(authorization == null){
			ApiError error = new ApiError(401,"Unauthorization request","api/0.0.1/auth");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
		}
		String base64encoded = authorization.split("Basic ")[1]; //89as7d9a7sd1023123
		String decoded = new String(Base64.getDecoder().decode(base64encoded));//user1:P4ssword
		String[] parts = decoded.split(":");//["user1","P4ssword"]
		String username = parts[0];//user1
		String password = parts[1];//P4ssword
		User inDB = userRepository.findByUsername(username);
		if(inDB == null){
			ApiError error = new ApiError(401,"Unauthorization request","api/0.0.1/auth");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
		}
		String hashedPassword = inDB.getPassword();
		if(!passwordEncoder.matches(password, hashedPassword)){
			ApiError error = new ApiError(401,"Unauthorization request","api/0.0.1/auth");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
		}
		
		
		log.info(authorization);
		//return ResponseEntity.ok().build();
		return ResponseEntity.ok(inDB);
	}
}
