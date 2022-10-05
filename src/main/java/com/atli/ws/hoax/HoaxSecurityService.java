package com.atli.ws.hoax;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atli.ws.user.User;

@Service(value="hoaxSecurity")
public class HoaxSecurityService {
	
	@Autowired
	HoaxRepository hoaxRepository;
	
	public boolean isAllowedToDelete(long id, User loggedInUser) {
		Hoax hoax = hoaxRepository.getById(id);
		if(hoax.getUser().getId() != loggedInUser.getId()) {
			//throw new AuthorizationException();
			return false;
		}
		return true;
	}
}
