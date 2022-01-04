package com.atli.ws.user;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String>{

	@Autowired
	UserRepository userRepository;
	
	@Override
	public boolean isValid(String username, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		User user = userRepository.findByUsername(username);
		System.out.println(username);
		System.out.println(userRepository.findByUsername(username).toString());
		if(user != null){
			return false;
		}
		return true;
	}

}