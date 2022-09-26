package com.atli.ws.hoax;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atli.ws.shared.GenericResponse;

@RestController
@RequestMapping("/api/0.0.1")
public class HoaxController {

	@Autowired
	HoaxService hoaxService;
	
	@PostMapping("/hoaxes")
	public GenericResponse saveHoax(@Valid @RequestBody Hoax hoax) {
		hoaxService.save(hoax);
		return new GenericResponse("Hoax is saved");
	}
	
	/*
	@GetMapping("/hoaxes")
	Page<Hoax> getHoaxes(Pageable page){
		return hoaxService.getHoaxesWithPage(page);
	}
	*/
	
	@GetMapping("/hoaxes")
	Page<Hoax> getHoaxes(@PageableDefault(sort="id",direction= Direction.DESC) Pageable page){
		return hoaxService.getHoaxesWithPage(page);
	}

}
