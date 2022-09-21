package com.atli.ws.hoax;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HoaxRepository extends JpaRepository<Hoax,Long>{
	// Hoax Entitysi ile Database arasındaki iliski
}
