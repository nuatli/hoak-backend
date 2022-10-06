package com.atli.ws.hoax;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.atli.ws.configuration.AppConfiguration;
import com.atli.ws.file.FileAttachment;
import com.atli.ws.file.FileAttachmentRepository;
import com.atli.ws.file.FileService;
import com.atli.ws.hoax.vm.HoaxSubmitVM;
import com.atli.ws.user.User;
import com.atli.ws.user.UserService;

@Service
public class HoaxService {
	HoaxRepository hoaxRepository;
	UserService userService;
	FileAttachmentRepository fileAttachmentRepository;
	FileService fileService;
	AppConfiguration appConfiguration;
	
	public HoaxService(HoaxRepository hoaxRepository,FileAttachmentRepository fileAttachmentRepository,FileService fileService,AppConfiguration appConfiguration,UserService userService) {
		super();
		this.hoaxRepository = hoaxRepository;
		this.fileAttachmentRepository = fileAttachmentRepository;
		this.fileService = fileService;
		this.appConfiguration = appConfiguration;
		this.userService = userService;
	}

	public void save(HoaxSubmitVM hoaxSubmitVM,User user) {
		Hoax hoax = new Hoax();
		hoax.setContent(hoaxSubmitVM.getContent());
		hoax.setTimestamp(new Date());
		hoax.setUser(user);
		hoaxRepository.save(hoax);
		Optional<FileAttachment> optionalfileAttachment = fileAttachmentRepository.findById(hoaxSubmitVM.getAttachmentId());
		if(optionalfileAttachment.isPresent()) {//varsa databasede
			FileAttachment fileAttachment = optionalfileAttachment.get();
			fileAttachment.setHoax(hoax);
			fileAttachmentRepository.save(fileAttachment);
		}
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

	public void deleteHoax(long id) {
		Hoax inDB = hoaxRepository.getById(id);
		if(inDB.getFileAttachment() != null) {
			String fileName = inDB.getFileAttachment().getName();
			fileService.deleteFile(fileName, appConfiguration.getAttachmentStoragePath());
		}
		hoaxRepository.deleteById(id);
	}
	

	
}
