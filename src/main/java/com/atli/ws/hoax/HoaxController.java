package com.atli.ws.hoax;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atli.ws.hoax.vm.HoaxVM;
import com.atli.ws.shared.CurrentUser;
import com.atli.ws.shared.GenericResponse;
import com.atli.ws.user.User;

@RestController
@RequestMapping("/api/0.0.1")
public class HoaxController {

	@Autowired
	HoaxService hoaxService;
	
	@PostMapping("/hoaxes")
	public GenericResponse saveHoax(@Valid @RequestBody Hoax hoax, @CurrentUser User user) {
		hoaxService.save(hoax,user);
		return new GenericResponse("Hoax is saved");
	}
	
	/*
	@GetMapping("/hoaxes")
	Page<Hoax> getHoaxes(Pageable page){
		return hoaxService.getHoaxesWithPage(page);
	}
	*/
	
	@GetMapping("/hoaxes")
	Page<HoaxVM> getHoaxes(@PageableDefault(sort="id",direction= Direction.DESC) Pageable page){
		return hoaxService.getHoaxesWithPage(page).map(HoaxVM::new);//Hoax dataları mapliyip HoaxVM'e dönüştürüp return ediyor.
	}
	
	@GetMapping("/users/{username}/hoaxes")
	Page<HoaxVM> getUserHoaxes(@PathVariable String username,@PageableDefault(sort="id",direction= Direction.DESC) Pageable page){
		return hoaxService.getuserHoaxesWithPage(page,username).map(HoaxVM::new);//Hoax dataları mapliyip HoaxVM'e dönüştürüp return ediyor.
	}
	
	@GetMapping("/hoaxes/{id:[0-9]+}") //id:[0-9]+ -> 0 ve 9 arasındaki sayılardan, bu sayılarda tekrar edebilir (+)
	Page<HoaxVM> getHoaxesRelative(@PageableDefault(sort="id",direction= Direction.DESC) Pageable page,@PathVariable long id){
		return hoaxService.getOldHoaxes(id,page).map(HoaxVM::new);//Hoax dataları mapliyip HoaxVM'e dönüştürüp return ediyor.
	}
	
	@GetMapping("/users/{username}/hoaxes/{id:[0-9]+}") //id:[0-9]+ -> 0 ve 9 arasındaki sayılardan, bu sayılarda tekrar edebilir (+)
	Page<HoaxVM> getUsersHoaxesRelative(@PageableDefault(sort="id",direction= Direction.DESC) Pageable page,@PathVariable String username,@PathVariable long id){
		return hoaxService.getOldHoaxesofUser(username,id,page).map(HoaxVM::new);//Hoax dataları mapliyip HoaxVM'e dönüştürüp return ediyor.
	}

}
