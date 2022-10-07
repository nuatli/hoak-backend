package com.atli.ws.auth;

import com.atli.ws.user.vm.UserVM;

import lombok.Data;

@Data
public class AuthResponse {
	
	private String token;
	
	private UserVM user;
}
