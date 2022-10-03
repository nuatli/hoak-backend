package com.atli.ws.hoax;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import com.atli.ws.user.User;
import com.atli.ws.user.UserService;

@Service
public class HoaxService {
	HoaxRepository hoaxRepository;
	UserService userService;
	
	public HoaxService(HoaxRepository hoaxRepository,UserService userService) {
		super();
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

	public Page<Hoax> getOldHoaxes(long id, String username, Pageable page) {
		Specification<Hoax> specification = idLessThan(id);
		if(username != null) {
			User inDB = userService.getByUsername(username);
			specification  = specification.and(userIs(inDB));
			//return hoaxRepository.findByIdLessThanAndUser(id,inDB,page);
		}
		return hoaxRepository.findAll(specification, page);
	}

	public long getNewHoaxesCount(long id,String username) {
		Specification<Hoax> specification = idGreaterThan(id);
		if(username != null) {
			User inDB = userService.getByUsername(username);
			specification = specification.and(userIs(inDB));
		}
		return hoaxRepository.count(specification);
	}
	
	public List<Hoax> getNewHoaxes(long id, String username, Sort sort) {
		Specification<Hoax> specification = idGreaterThan(id);
		if(username != null) {
			User inDB = userService.getByUsername(username);
			specification = specification.and(userIs(inDB));
		}
		return hoaxRepository.findAll(specification, sort);
	}
	
	
	
	
	
	Specification<Hoax> idLessThan(long id){
		return (root,query, criteriaBuilder) -> {
			 return criteriaBuilder.lessThan(root.get("id"), id);
		};
	}
	
	Specification<Hoax> idGreaterThan(long id){
		return (root,query, criteriaBuilder) -> {
			 return criteriaBuilder.greaterThan(root.get("id"), id);
		};
	}
	
	Specification<Hoax> userIs(User user){
		return (root,query, criteriaBuilder) -> {
			 return criteriaBuilder.equal(root.get("user"), user);
		};
	}
	

	
}
