package com.atli.ws.hoax;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.atli.ws.user.User;

public interface HoaxRepository extends JpaRepository<Hoax,Long>{
	// Hoax Entitysi ile Database arasındaki iliski
	Page<Hoax> findByUser(User user, Pageable page );
}
