package com.atli.ws.user;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.atli.ws.shared.CurrentUser;
import com.atli.ws.shared.GenericResponse;
import com.atli.ws.user.vm.UserVM;

@RestController
public class UserController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	UserService userService;

	@PostMapping("/api/0.0.1/users")
	public GenericResponse createUser(@Valid @RequestBody User user) {
		userService.save(user);
		return new GenericResponse("User Created");
	}
	@GetMapping("/api/0.0.1/users")
	List<User> getUsers(){
		return userService.getUsers();
	}
	
	/*
	@GetMapping("/api/0.0.1/usersWithPage")
	//@JsonView(Views.Base.class)
	Page<User> getUsersWithPage(@RequestParam int currentPage, @RequestParam int pageSize){
		return userService.getUsersWithPage(currentPage,pageSize);
	}
	*/
	/*
	@GetMapping("/api/0.0.1/usersWithPage")
	Page<User> getUsersWithPage(Pageable page){
		return userService.getUsersWithPage(page);
	}
	*/
	@GetMapping("/api/0.0.1/usersWithPage")
	Page<UserVM> getAllUsersProjection(Pageable page,@CurrentUser User user){
		return userService.getUsersWithPage(page,user).map(UserVM::new);
		/*
		  map(user) -> {
			return new UserVM(user);
			)}
		}
		 */
	}
	
}
