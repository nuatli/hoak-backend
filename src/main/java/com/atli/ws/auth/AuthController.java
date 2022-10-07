package com.atli.ws.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.atli.ws.shared.CurrentUser;
import com.atli.ws.user.User;
import com.atli.ws.user.UserRepository;
import com.atli.ws.user.vm.UserVM;


@RestController
public class AuthController {
	
	@Autowired
	AuthService authService;
	
	@PostMapping("/api/0.0.1/auth")
	AuthResponse handleAuthentication(@RequestBody Credentials credentials){
		return authService.authenticate(credentials);
	}

}
