package com.atli.ws.hoax;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.atli.ws.user.User;


public interface HoaxRepository extends JpaRepository<Hoax,Long>,JpaSpecificationExecutor<Hoax>{
	// Hoax Entitysi ile Database arasındaki iliski
	Page<Hoax> findByUser(User user, Pageable page );
	
	//Page<Hoax> findByIdLessThan(long id,Pageable page);
	
	//Page<Hoax> findByIdLessThanAndUser(long id,User user,Pageable page);
	
	//long countByIdGreaterThan(long id);
	
	//long countByIdGreaterThanAndUser(long i,User user);

	//List<Hoax> findByIdGreaterThan(long id,Sort sort);

	//List<Hoax> findByIdGreaterThanAndUser(long id, User user, Sort sort);

}
