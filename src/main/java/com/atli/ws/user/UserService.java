package com.atli.ws.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	
	public Page<User> getUsersWithPage(int currentPage,int pageSize){
		Pageable page = PageRequest.of(currentPage,pageSize);
		return userRepository.findAll(page);
	}
	
	public Page<User> getUsersWithPage(Pageable page){
		return userRepository.findAll(page);
	}
	
	public void  deleteUser(User user){
		userRepository.delete(user);
	}

}
