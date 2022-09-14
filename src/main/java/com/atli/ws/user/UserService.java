package com.atli.ws.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.atli.ws.error.NotFoundException;
import com.atli.ws.user.vm.UserUpdateVM;

@Service
public class UserService {
	UserRepository userRepository;
	PasswordEncoder passwordEncoder;
	
	@Autowired
	public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public void save(User user) {
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}
	
	public List<User> getUsers(){
		return userRepository.findAll();
	}
	
	/*
	public Page<User> getUsersWithPage(int currentPage,int pageSize){
		Pageable page = PageRequest.of(currentPage,pageSize);
		return userRepository.findAll(page);
	}
	*/
	
	public Page<User> getUsersWithPage(Pageable page,User user){
		if(user != null){
			return userRepository.findByUsernameNot(user.getUsername(), page);
		}
		return userRepository.findAll(page);
	}
	
	public void  deleteUser(User user){
		userRepository.delete(user);
	}

	public User getByUsername(String username) {
		User inDB = userRepository.findByUsername(username);
		if(inDB == null){
			throw new NotFoundException();
		}
		return inDB;

	}

	public User updateUser(String username, UserUpdateVM updatedUser) {
		User inDB = getByUsername(username);
		inDB.setDisplayName(updatedUser.getDisplayName());
		if(updatedUser.getImage() != null){
			inDB.setImage(updatedUser.getImage());
		}
		return userRepository.save(inDB);
	}

}
