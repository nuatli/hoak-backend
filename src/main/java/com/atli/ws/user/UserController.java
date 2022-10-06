package com.atli.ws.user;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atli.ws.shared.CurrentUser;
import com.atli.ws.shared.GenericResponse;
import com.atli.ws.user.vm.UserUpdateVM;
import com.atli.ws.user.vm.UserVM;

@RestController
@RequestMapping("/api/0.0.1")
public class UserController {
	
	@Autowired
	UserService userService;

	@PostMapping("/users")
	public GenericResponse createUser(@Valid @RequestBody User user) {
		userService.save(user);
		return new GenericResponse("User Created");
	}
	
	@GetMapping("/users")
	List<User> getUsers(){
		return userService.getUsers();
	}

	@GetMapping("/usersWithPage")
	Page<UserVM> getAllUsersProjection(Pageable page,@CurrentUser User user){
		return userService.getUsersWithPage(page,user).map(UserVM::new);

	}
	
	@GetMapping("/users/{username}")
	UserVM getUser(@PathVariable String username){
		User user = userService.getByUsername(username);
		return new UserVM(user);
	}
	
	/*
	@PutMapping("/users/{username}")
	@PreAuthorize("#username == #loggedInUser.username")
	ResponseEntity<?> updateUser(@RequestBody UserUpdateVM updatedUser, @PathVariable String username,@CurrentUser User loggedInUser) {
		if(loggedInUser != null){
			if(!loggedInUser.getUsername().equals(username)){
				ApiError error = new ApiError(403,"Cannot change another users data","/api/0.0.1/users/"+username);
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
			}
			User user = userService.updateUser(username,updatedUser);
			return ResponseEntity.ok(new UserVM(user));
		}
		ApiError error = new ApiError(401,"UNAUTHORIZED","/api/0.0.1/users/"+username);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
	}
	*/
	/*
	@PutMapping("/users/{username}")
	@PreAuthorize("isAuthenticated()")
	UserVM updateUser(@RequestBody UserUpdateVM updatedUser, @PathVariable String username) {
		User user = userService.updateUser(username,updatedUser);
		return new UserVM(user);
	}
	*/
	
	@PutMapping("/users/{username}")
	@PreAuthorize("#username == principal.username")
	UserVM updateUser(@Valid @RequestBody UserUpdateVM updatedUser, @PathVariable String username) throws IOException {
		User user = userService.updateUser(username,updatedUser);
		return new UserVM(user);
	}
	
	@DeleteMapping("/users/{username}")
	@PreAuthorize("#username == principal.username")
	GenericResponse deleteUser(@PathVariable String username) throws IOException {
		userService.deleteUser(username);
		return new GenericResponse("User is removed");
	}
	
}





