package com.atli.ws.hoax;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.atli.ws.user.User;
import com.atli.ws.user.UserService;

@Service
public class HoaxService {
	HoaxRepository hoaxRepository;
	UserService userService;
	
	public HoaxService(HoaxRepository hoaxRepository,UserService userService) {
		super();
		System.out.println("asd");
		this.hoaxRepository = hoaxRepository;
		this.userService = userService;
	}

	public void save(Hoax hoax,User user) {
		hoax.setTimestamp(new Date());
		hoax.setUser(user);
		hoaxRepository.save(hoax);
	}

	public Page<Hoax> getHoaxesWithPage(Pageable page) {
		return hoaxRepository.findAll(page);
	}
	
	public List<Hoax> getHoaxes() {
		return hoaxRepository.findAll();
	}

	public Page<Hoax> getuserHoaxesWithPage(Pageable page, String username) {
		User inDB = userService.getByUsername(username);
		return hoaxRepository.findByUser(inDB, page);
	}

	
}
