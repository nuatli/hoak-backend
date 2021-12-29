package com.atli.ws.user;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.atli.ws.error.ApiError;
import com.atli.ws.shared.GenericResponse;

@RestController
public class UserController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	UserService userService;
	
	/*
	@PostMapping("/api/0.0.1/users")
	public GenericResponse createUser(@RequestBody User user) {
		log.info(user.toString());
		userService.save(user);
		return new GenericResponse("User Created");
	}
	*/
	/*
	 	@PostMapping("/api/0.0.1/users")
	public ResponseEntity<?> createUser(@RequestBody User user) {
		ApiError error = new ApiError(400, "Validation Error", "/api/0.0.1/users");
		Map<String,String> validationErrors = new HashMap<>();
		String username = user.getUsername();
		String displayName = user.getDisplayName();
		
		if(username == null || username.isEmpty()){
			validationErrors.put("username", "Username Cannot Be Empty");
		}
		
		if(displayName == null || displayName.isEmpty()){
			validationErrors.put("displayName", "Display Name Cannot Be Empty");

		}
		if(validationErrors.size() > 0){
			error.setValidationErrors(validationErrors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
		}
		
		
		userService.save(user);
		return new ResponseEntity(new GenericResponse("User Created"), HttpStatus.CREATED);
	}
	 */
	@PostMapping("/api/0.0.1/users")
	public GenericResponse createUser(@Valid @RequestBody User user) {
		userService.save(user);
		return new GenericResponse("User Created");
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiError handleValidationException(MethodArgumentNotValidException exception){
		ApiError error = new ApiError(400, "Validation Error", "/api/0.0.1/users");
		Map<String,String> validationErrors = new HashMap<>();
		for(FieldError fieldError : exception.getBindingResult().getFieldErrors()){//bu exception bindingResult validation result ile alakalı obje veriyor
			validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
		}
		error.setValidationErrors(validationErrors);
		return error;
	}
	
	
}
