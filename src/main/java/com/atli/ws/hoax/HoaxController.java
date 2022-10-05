package com.atli.ws.hoax;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.atli.ws.hoax.vm.HoaxSubmitVM;
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
	public GenericResponse saveHoax(@Valid @RequestBody HoaxSubmitVM hoax, @CurrentUser User user) {
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
	
	@GetMapping({"/hoaxes/{id:[0-9]+}","/users/{username}/hoaxes/{id:[0-9]+}"}) //id:[0-9]+ -> 0 ve 9 arasındaki sayılardan, bu sayılarda tekrar edebilir (+)
	ResponseEntity<?> getHoaxesRelative(
			@PageableDefault(sort="id",direction= Direction.DESC) Pageable page,
			@PathVariable long id,
			@PathVariable(required=false) String username,
			@RequestParam(name="count",required=false, defaultValue="false") boolean count,
			@RequestParam(name="direction", defaultValue="before") String direction
	){
		if(count) {
			long newHoaxCount = hoaxService.getNewHoaxesCount(id,username);
			Map<String,Long> response = new HashMap<>();
			response.put("count",newHoaxCount);
			return ResponseEntity.ok(response);
		}
		if(direction.equalsIgnoreCase("after")) {
			List<HoaxVM> newHoaxes = hoaxService.getNewHoaxes(id,username,page.getSort())
				.stream().map(HoaxVM::new).collect(Collectors.toList());
			return ResponseEntity.ok(newHoaxes);
		}
		return ResponseEntity.ok(hoaxService.getOldHoaxes(id,username,page).map(HoaxVM::new));//Hoax dataları mapliyip HoaxVM'e dönüştürüp return ediyor.
	}
	
	/*
	@GetMapping() //id:[0-9]+ -> 0 ve 9 arasındaki sayılardan, bu sayılarda tekrar edebilir (+)
	ResponseEntity<?> getUsersHoaxesRelative(
			@PageableDefault(sort="id",direction= Direction.DESC) Pageable page,
			@PathVariable String username,
			@PathVariable long id,
			@RequestParam(name="count",required=false, defaultValue="false") boolean count,
			@RequestParam(name="direction", defaultValue="before") String direction
	){
		if(count) {
			long newHoaxCount = hoaxService.getNewHoaxesCountOfUser(id,username);
			Map<String,Long> response = new HashMap<>();
			response.put("count",newHoaxCount);
			return ResponseEntity.ok(response);
		}
		if(direction.equalsIgnoreCase("after")) {
			List<HoaxVM> newHoaxes = hoaxService.getNewHoaxesofUser(id,username,page.getSort())
				.stream().map(HoaxVM::new).collect(Collectors.toList());
			return ResponseEntity.ok(newHoaxes);
		}
		
		return ResponseEntity.ok(hoaxService.getOldHoaxesofUser(username,id,page).map(HoaxVM::new));//Hoax dataları mapliyip HoaxVM'e dönüştürüp return ediyor.
	}
	*/
	
	@DeleteMapping("/hoaxes/{id:[0-9]+}")
	@PreAuthorize("@hoaxSecurity.isAllowedToDelete(#id,principal)")
	GenericResponse deleteHoax(@PathVariable long id) {
		hoaxService.deleteHoax(id);
		return new GenericResponse("Hoax Removed");
	}
	
}
